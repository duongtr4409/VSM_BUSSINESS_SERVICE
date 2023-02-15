package com.vsm.business.utils;

import com.vsm.business.config.sercurity.SecurityInterceptor;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.auth.bo.MyUserDetail;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthenticateUtils {

    private final Logger log = LoggerFactory.getLogger(AuthenticateUtils.class);

    public static final String VIEW = "view";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    public static final String DOWNLOAD = "download";

    @Value("${login.max-number-login-fail:5}")
    public Long max_login_fail;

    @Value("${email_admin_user_init_role:administrator}")
    public String ADMIN_EMAIL;

    @Value("${feature_role.file_init_feature:./temp/feature/adminFeature.json}")
    public String ADMIN_FEATURE_DATA_FILE;

    @Value("${feature_role.role_code_full_feature:SYSTEM_FULL_FEATURE}")
    public String ROLE_CODE_FULL_FEATURE;

    public final String NOT_AUTHORITY_MESS = "NOT AUTHORITY";

    public final Long millis_need_restart = 3600000L;                               // thời gian chênh lệch để restart lại số lần login fail (tính bằng ms: để là 1h = 1000 * 60 * 60)

    private boolean isFirstCheck = true;                                      // biến đánh dấu đây là lần đầu tiên check -> lấy thông tin những user có số lần đăng nhập quá số lần để put vào MAP_LOGIN_INFO

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RequestDataRepository requestDataRepository;

    @Autowired
    private UserUtils userUtils;

//    @Autowired
//    private SecurityInterceptor securityInterceptor;

    @Autowired
    private ConditionUtils conditionUtils;

    @Autowired
    private AttachmentPermisitionRepository attachmentPermisitionRepository;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Map<Long, Instant> MAP_LOGIN_INFO = new HashMap<>();                // map chứa thông tin login của user (số lần login faild)


    /**
     * Hàm thực hiện kiểm tra xem user đã đang nhập sai quá số lần cho phép chưa
     * @param userName  : userName của tài khoản đang kiểm tra
     * @return : true nếu chưa | false nếu đã quá
     */
    public boolean checkLogin(String userName){
        if(this.isFirstCheck) this.init();     // nếu là lần đầu -> init
        if(userName == null) return false;
        List<UserInfo> userInfoList = this.userInfoRepository.findAllByUserName(userName);
        if(userInfoList != null){
            for(UserInfo userInfo : userInfoList){
                if(userInfo != null){
                    Long numberLoginFail = userInfo.getNumberOfLoginFailed() == null ? 0L : userInfo.getNumberOfLoginFailed();
                    if(numberLoginFail > this.max_login_fail){
                        userInfo.setNumberOfLoginFailed(numberLoginFail + 1);
                        this.userInfoRepository.save(userInfo);
                        this.MAP_LOGIN_INFO.put(userInfo.getId(), Instant.now());
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    /**
     * Hàm thực hiện lấy thông tin các user có số lần đăng nhập thất bại quá số lần cho phép đẩy vào MAP_LOGIN_INFO
     */
    private void init(){
        Instant now = Instant.now();
        try {
            List<UserInfo> userInfoListLoginFail = this.userInfoRepository.findAllByNumberOfLoginFailedGreaterThan(this.max_login_fail);
            if(userInfoListLoginFail != null && !userInfoListLoginFail.isEmpty()){
                userInfoListLoginFail.forEach(ele -> {
                    if(ele != null)
                        this.MAP_LOGIN_INFO.put(ele.getId(), now);
                });
            }
            this.isFirstCheck = false;
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    /**
     * Hàm thực hiện update lại thông tin số lần login fail của user về 0
     * @param userId
     * @return : true nếu update thành công | false nếu update thất bại
     */
    public boolean resetLoginFail(Long userId){
        try {
            UserInfo userInfo = this.userInfoRepository.findById(userId).get();
            userInfo.setNumberOfLoginFailed(0L);
            this.userInfoRepository.save(userInfo);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
        return true;
    }

    /**
     * Hàm thực hiện update thông tin số lần login fail theo userName
     * @param userName: userName của tài khoản đang update
     */
    public void updateLoginFail(String userName){
        try {
            List<UserInfo> userInfoList = this.userInfoRepository.findAllByUserName(userName);
            if(userInfoList != null){
                userInfoList.forEach(ele -> {
                    Long numberLoginFail = ele.getNumberOfLoginFailed() == null ? 0L : ele.getNumberOfLoginFailed();
                    numberLoginFail++;
                    ele.setNumberOfLoginFailed(numberLoginFail);
                    this.userInfoRepository.save(ele);
                    this.MAP_LOGIN_INFO.put(ele.getId(), Instant.now());
                });
            }
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    @Scheduled(fixedDelay = 960000)                // 1000 * 60 * 16 -> 16p chạy 1 lần
    void resetLoginInfo(){
        if(this.isFirstCheck) this.init();     // nếu là lần đầu -> init
        log.info("ResetLoginInfo()");
        if(MAP_LOGIN_INFO != null && !MAP_LOGIN_INFO.isEmpty()){
            Set<Long> keySet = this.MAP_LOGIN_INFO.keySet();
            Instant now = Instant.now();
            for(Long userId : keySet){
                if(userId != null){
                    try {
                        Instant timeUpdateLate = this.MAP_LOGIN_INFO.get(userId);
                        Duration timeElapsed = Duration.between(timeUpdateLate, now);
                        if(timeElapsed.toMillis() > this.millis_need_restart){
                            UserInfo userInfo = this.userInfoRepository.findById(userId).get();
                            userInfo.setNumberOfLoginFailed(0L);
                            this.userInfoRepository.save(userInfo);
                        }
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
            }
        }
    }

    /**
     * Hàm kiểm tra user có quyền thay đổi (thêm sửa sóa dữ liệu liên quan đến RequestData (stepData, req_process_his, ...))
     * @param requestDataId: Id của phiếu
     * @return: true: nếu có quyền | false: nếu không có quyền
     */
    public boolean checkPermisionForDataOfUser(Long requestDataId){
        if(requestDataId == null) return true;      // TH nếu ko có requestData -> không cần check trả về true luôn
        MyUserDetail currentUser = userUtils.getCurrentUser();
        if(currentUser == null) return false;       // Th nếu ko có thông tin đăng nhập -> trả về false luôn

        if(requestDataId != null){
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElseThrow(() -> new RuntimeException("No Data"));
            // lấy danh sách những người được truy cập dữ liệu của requestData \\
            Set<Long> userInfoIdList = new HashSet<>();
            if(requestData.getUserInfos() != null)      // danh sách người được chia sẻ
                userInfoIdList.addAll(requestData.getUserInfos().stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
            if(requestData.getCreated() != null)        // người tạo phiếu
                userInfoIdList.add(requestData.getCreated().getId());
            if(requestData.getModified() != null)       // người sửa phiếu
                userInfoIdList.add(requestData.getModified().getId());
            if(requestData.getStepData() != null) {      // người trong quy trình
                requestData.getStepData().forEach(ele -> {
                    if(ele.getUserInfos() != null)
                        userInfoIdList.addAll(ele.getUserInfos().stream().map(ele1 -> ele1.getId()).collect(Collectors.toSet()));
                });
            }
            return userInfoIdList.stream().anyMatch(ele -> ele != null && ele.equals(currentUser.getId()));
        }
        return true;
    }

    /**
     * Hàm kiểm tra xem user Hiện tại có quyền ADMIN không
     * @return
     */
    public boolean checkPermisionADMIN(){
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(currentUser == null) return false;
        return currentUser.getRolesString().stream().anyMatch(ele -> SecurityInterceptor.ADMIN_ROLE.equalsIgnoreCase(ele));
    }

    /**
     * Hàm thực hiện lấy dánh sách request mà user có quyền xem, sửa, xóa, download, ...
     * @param userId : id của user
     * @param action : hành động muốn thực hiện: VIEW: xem, EDIT: chỉnh sửa, DELETE: xóa, DOWNLOAD: tải
     * @return: List request Id mà user có quyền
     */
    public List<Long> getRequestForUserWithHasAction(Long userId, String action){
        if(userId == null) userId = this.userUtils.getCurrentUser().getId();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        Set<RoleRequest> listRoleRequest = new HashSet<>();
        if(userInfo.getRoles() != null){
            userInfo.getRoles().forEach(ele -> listRoleRequest.addAll(ele.getRoleRequests()));
            switch (action){
                case VIEW:
                    return listRoleRequest.stream().filter(ele -> ele.getIsView() != null && ele.getIsView().equals(true)).map(ele -> ele.getRequest().getId()).collect(Collectors.toList());
                case EDIT:
                    return listRoleRequest.stream().filter(ele -> ele.getIsEdit() != null && ele.getIsEdit().equals(true)).map(ele -> ele.getRequest().getId()).collect(Collectors.toList());
                case DELETE:
                    return listRoleRequest.stream().filter(ele -> ele.getIsDelete() != null && ele.getIsDelete().equals(true)).map(ele -> ele.getRequest().getId()).collect(Collectors.toList());
                case DOWNLOAD:
                    return listRoleRequest.stream().filter(ele -> ele.getIsDownload() != null && ele.getIsDownload().equals(true)).map(ele -> ele.getRequest().getId()).collect(Collectors.toList());
                default:
                    return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Hàm thực hiện lấy thông tin phòng ban mà User được quyền (xem, sửa, xóa, tải dữ liệu (action tùy thuộc tham số truyền vào))
     * @param userId    : id của user muốn lấy thông tin
     * @param action    : quyền cần lấy (VIEW: xem, EDIT: sửa, DELETE: xóa, DOWNLOAD: tải)
     * @return          : trả về danh sách phòng ban mà user có quyền
     */
    public List<Organization> getOrganizationForUserWithHasAction(Long userId, String action){
        if(userId == null) userId = this.userUtils.getCurrentUser().getId();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        Set<RoleOrganization> listOrganization = new HashSet<>();
        if(userInfo.getRoles() != null){
            userInfo.getRoles().forEach(ele -> listOrganization.addAll(ele.getRoleOrganizations()));
            switch (action){
                case VIEW:
                    return listOrganization.stream().filter(ele -> ele.getIsView() != null && ele.getIsView().equals(true)).map(ele -> ele.getOrganization()).collect(Collectors.toList());
                case EDIT:
                    return listOrganization.stream().filter(ele -> ele.getIsEdit() != null && ele.getIsEdit().equals(true)).map(ele -> ele.getOrganization()).collect(Collectors.toList());
                case DELETE:
                    return listOrganization.stream().filter(ele -> ele.getIsDelete() != null && ele.getIsDelete().equals(true)).map(ele -> ele.getOrganization()).collect(Collectors.toList());
                case DOWNLOAD:
                    return listOrganization.stream().filter(ele -> ele.getIsDownload() != null && ele.getIsDownload().equals(true)).map(ele -> ele.getOrganization()).collect(Collectors.toList());
                default:
                    return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Hàm kiểm tra user hiện tại có được phân quyền để xem thông tin dữ liệu liên quan đến loại phiếu yêu cầu, (phiếu của đơn vị) hay không (chỉ được xem (viết kiểm tra ở nhưng phương thức GET)) (Xem ở màn hình thống kê)
     * @param requestDataId: Id của phiếu
     * @return: true: nếu có quyền | false: nếu không có quyền
     */
    public boolean checkPermisionForDataStatisticToUser(Long requestDataId){
        if(requestDataId == null) return true;      // TH nếu ko có requestData -> không cần check trả về true luôn
        MyUserDetail currentUser = userUtils.getCurrentUser();
        if(currentUser == null) return false;       // Th nếu ko có thông tin đăng nhập -> trả về false luôn

        if(requestDataId != null){
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElseThrow(() -> new RuntimeException("No Data"));
            // lấy danh sách những người được truy cập dữ liệu của requestData \\
            UserInfo currentUserInfo = this.userInfoRepository.findById(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, this.NOT_AUTHORITY_MESS));
            return currentUserInfo.getRoles().stream().anyMatch(ele ->
                ele.getRoleRequests().stream().anyMatch(ele1 -> {
                    try { return ele1.getRequest().getId().equals(requestData.getRequest().getId());}catch (Exception e){return false;}
                })
                    ||
                ele.getRoleOrganizations().stream().anyMatch(ele1 -> {
                    try {
                        return requestData.getCreated().getOrganizations().stream().anyMatch(ele2 -> ele2.getId().equals(ele1.getOrganization().getId()));
                    }catch (Exception e){return false;}
                })
            );
        }else{
            return false;
        }
    }


    /**
     * ROOT_FOLDER: thông tin folder ROOT
     * Hàm thực hiện khởi tạo quyền cho user (ADMIN: được cấu hình trong config)
     * thêm full quyền với file
     * thêm full với tính năng
     */
    public void initRoleToAdminUser(AttachmentFile ROOT_FOLDER){
        String SYSTEM = "SYSTEM";
        Instant now = Instant.now();
        this.initFeature();         // khởi tạo feature
        List<UserInfo> userInfoADMINList =this.userInfoRepository.findAllByEmail(this.ADMIN_EMAIL);

        if(userInfoADMINList != null && !userInfoADMINList.isEmpty()){
            List<AttachmentPermisition> attachmentPermisitionList = this.attachmentPermisitionRepository.findAllByAttachmentFileId(ROOT_FOLDER.getId());

            Boolean isFirst = !attachmentPermisitionList.stream().anyMatch(ele -> {
               return userInfoADMINList.stream().allMatch(ele1 -> {
                   return ele1.getId().equals(ele.getUserInfo().getId()) && ele.getIsDelete() == true && ele.getIsView() == true && ele.getIsEdit() == true && ele.getIsActive() == true;
               });
            });
            if(isFirst){
                userInfoADMINList.stream().filter(ele -> !this.conditionUtils.checkDelete(ele.getIsDelete() && this.conditionUtils.checkTrueFalse(ele.getIsActive()))).forEach(ele -> {
                    AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
                    attachmentPermisition.setUserInfo(ele);
                    attachmentPermisition.setAttachmentFile(ROOT_FOLDER);
                    attachmentPermisition.setIsActive(true);
                    attachmentPermisition.setIsView(true);
                    attachmentPermisition.setIsEdit(true);
                    attachmentPermisition.setIsDelete(true);
                    attachmentPermisition.setCreatedDate(now);
                    attachmentPermisition.setCreatedName(SYSTEM);
                    attachmentPermisition.setModifiedDate(now);
                    attachmentPermisition.setModifiedName(SYSTEM);
                    this.attachmentPermisitionRepository.save(attachmentPermisition);

                    // gắn feature cho ADMIN
                    List<Role> roleFullFeature = this.roleRepository.findALlByRoleCode(this.ROLE_CODE_FULL_FEATURE);
                    if(roleFullFeature == null || roleFullFeature.isEmpty()){       // nếu chưa có quyền trong DB
                        Role role = new Role();
                        role.setRoleCode(this.ROLE_CODE_FULL_FEATURE);
                        role.setRoleName(this.ROLE_CODE_FULL_FEATURE);
                        role.setIsDelete(true);
                        role.setIsActive(false);
                        role.setRoleType("Admin");
                        role.setModifiedDate(now);
                        role.setModifiedName(SYSTEM);
                        role.setCreatedDate(now);
                        role.setCreatedName(SYSTEM);
                        role.setIsAutoAdd(false);
                        role.setIsHaveView(true);
                        role.setIsHaveEdit(true);
                        role.setIsHaveDownload(true);
                        role.setIsHaveDelete(true);
                        role.setFeatures(this.featureRepository.findAll().stream().collect(Collectors.toSet()));
                        role = this.roleRepository.save(role);
                        Set<Role> roleOfUser = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
                        roleOfUser.add(role);
                        ele.setRoles(roleOfUser);
                        this.userInfoRepository.save(ele);
                    }else{                                                      // th đã có quyền trong DB
                        Set<Role> roleOfUser = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
                        roleOfUser.addAll(roleFullFeature);
                        ele.setRoles(roleOfUser);
                        this.userInfoRepository.save(ele);
                    }
                });
            }

            userInfoADMINList.stream().filter(ele -> !this.conditionUtils.checkDelete(ele.getIsDelete() && this.conditionUtils.checkTrueFalse(ele.getIsActive()))).forEach(ele -> {
                // gắn feature cho ADMIN
                List<Role> roleFullFeature = this.roleRepository.findALlByRoleCode(this.ROLE_CODE_FULL_FEATURE);
                if(roleFullFeature == null || roleFullFeature.isEmpty()){       // nếu chưa có quyền trong DB
                    Role role = new Role();
                    role.setRoleCode(this.ROLE_CODE_FULL_FEATURE);
                    role.setRoleName(this.ROLE_CODE_FULL_FEATURE);
                    role.setIsDelete(true);
                    role.setIsActive(false);
                    role.setRoleType("Admin");
                    role.setModifiedDate(now);
                    role.setModifiedName(SYSTEM);
                    role.setCreatedDate(now);
                    role.setCreatedName(SYSTEM);
                    role.setIsAutoAdd(false);
                    role.setIsHaveView(true);
                    role.setIsHaveEdit(true);
                    role.setIsHaveDownload(true);
                    role.setIsHaveDelete(true);
                    role.setRequestList("[]");
                    role.setOrganizationList("[]");
                    role.setFeatures(this.featureRepository.findAll().stream().collect(Collectors.toSet()));
                    role = this.roleRepository.save(role);
                    Set<Role> roleOfUser = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
                    roleOfUser.add(role);
                    ele.setRoles(roleOfUser);
                    this.userInfoRepository.save(ele);
                }else{                                                      // th đã có quyền trong DB
                    Set<Feature> allFeature = this.featureRepository.findAll().stream().collect(Collectors.toSet());
                    roleFullFeature.forEach(ele1 -> {
                        Set<Feature> featuresSet = ele1.getFeatures() == null ? new HashSet<>() : ele1.getFeatures();
                        featuresSet.addAll(allFeature);
                        this.roleRepository.save(ele1);
                    });
                    Set<Role> roleOfUser = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
                    roleOfUser.addAll(roleFullFeature);
                    ele.setRoles(roleOfUser);
                    this.userInfoRepository.save(ele);
                }
            });
        }
    }

    private boolean initFeature(){
        try {
            List<Feature> features = this.featureRepository.findAll();
            if(features == null || features.isEmpty()){
                Instant now = Instant.now();
                String data = new String(Files.readAllBytes(Paths.get(this.ADMIN_FEATURE_DATA_FILE)));
                JSONArray jsonArray = new JSONArray(data);
                int n = jsonArray.length();
                for(int i=0; i<n; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Feature feature = new Feature();
                    feature.setIsDelete(false);
                    feature.setIsActive(true);
                    feature.setFeatureType("ADMIN");
                    feature.setFeatureTypeName("ADMIN");
                    feature.setFeatureTypeCode("ADMIN");
                    feature.setLink(jsonObject.getString("link"));
                    feature.setFeatureCode(jsonObject.getString("title"));
                    feature.setFeatureName(jsonObject.getString("title_name"));
                    feature.setTennantCode(jsonObject.getString("link_api"));
                    feature.setModifiedDate(now);
                    feature.setModifiedName("SYSTEM");
                    feature.setCreatedDate(now);
                    feature.setCreatedName("SYSTEM");
                    feature.setDescription(jsonObject.toString());
                    this.featureRepository.save(feature);
                }
            }
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
        return true;
    }

}
