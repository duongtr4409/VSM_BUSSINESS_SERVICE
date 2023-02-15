package com.vsm.business.service.sysn.user365;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.*;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.UserInfoRepository;
import joptsimple.internal.Strings;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class SyncUser365Schedule {

    private final Logger log = LoggerFactory.getLogger(SyncUser365Schedule.class);

    @Value("${system.sync-ad.FEATURE_CREATE_ORG_CHART:false}")
    public String FEATURE_CREATE_ORG_CHART;

    @Value("${system.sync-ad.ORG_SYNC:VCR}")
    public String ORG_SYNC;
    @Value("${system.sync-ad.HAS_CHECK_ORG_SYNC:TRUE}")
    public String HAS_CHECK_ORG_SYNC;

    @Value("${system.sync-ad.UNCATEGORY:Khác}")
    public String UNCATEGORY_ORG;

    @Autowired
    private GraphService graphService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RankRepository rankRepository;

    private final String USER_TYPE = "AD 365";
    private final String USER_UPDATER = "SYNC 365";

    private final String SYNC_365 = "SYNC365";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(cron = "${cron.tab:0 15 03 * * ?}")
    public void syncUser(){
        List<User> allUser365 = this.getAllUser();
        List<Organization> allOrg = this.organizationRepository.findAll();
        allOrg = allOrg == null ? new ArrayList<>() : allOrg;
        List<Rank> allRank = this.rankRepository.findAll();
        allRank = allRank == null ? new ArrayList<>() : allRank;
        if(allUser365 != null){
            //allUser365.forEach(ele ->{
            for(User ele : allUser365) {
                try {
                    List<UserInfo> sameUsers = this.userInfoRepository.findAllByIdInMicrosoft(ele.id);
//                    if(!Strings.isNullOrEmpty(ele.mail) && (sameUsers == null || sameUsers.isEmpty())) sameUsers = this.userInfoRepository.findAllByEmail(ele.mail);
                    if(!Strings.isNullOrEmpty(ele.userPrincipalName) && (sameUsers == null || sameUsers.isEmpty())) sameUsers = this.userInfoRepository.findAllByUserName(ele.userPrincipalName);
                    if(sameUsers == null || sameUsers.isEmpty()){            // TH chưa có trong DB -> insert
                        insertUser(ele, null, allOrg, allRank);
                    }else{                                                      // TH đã có trong DB -> update
                        for (UserInfo sameUser : sameUsers) {
                            updateUser(ele, sameUser, null, allOrg, allRank);
                        }
                    }
                }catch (Exception e){log.error("{}", e);}
            }
//            });
        }

        // cập nhật lại cho các user bị xóa (chuyển cờ IsDelete)
        List<User> allUserDeleted365 = this.getAllUserDeleted(allUser365);
        for(User ele : allUserDeleted365){
            try {
                List<UserInfo> sameUsers = this.userInfoRepository.findAllByIdInMicrosoft(ele.id);
                if(sameUsers != null && !sameUsers.isEmpty()){
                    for(UserInfo sameUser : sameUsers){
                        updateDeleteUser(sameUser, null);
                    }
                }
            }catch (Exception e){
                log.error("{}", e);
            }
        }
    }

    public boolean syncUserManual(Long userId){
        UserInfo updater = this.userInfoRepository.findById(userId).orElse(null);
        List<Organization> allOrg = this.organizationRepository.findAll();
        allOrg = allOrg == null ? new ArrayList<>() : allOrg;
        List<Rank> allRank = this.rankRepository.findAll();
        allRank = allRank == null ? new ArrayList<>() : allRank;
        List<User> allUser365 = this.getAllUser();
        if(allUser365 != null){
            //allUser365.forEach(ele ->{
            for(User ele : allUser365) {
                try {
                    List<UserInfo> sameUsers = this.userInfoRepository.findAllByIdInMicrosoft(ele.id);
//                    if(!Strings.isNullOrEmpty(ele.mail) && (sameUsers == null || sameUsers.isEmpty())) sameUsers = this.userInfoRepository.findAllByEmail(ele.mail);
                    if(!Strings.isNullOrEmpty(ele.userPrincipalName) && (sameUsers == null || sameUsers.isEmpty())) sameUsers = this.userInfoRepository.findAllByUserName(ele.userPrincipalName);
                    if (sameUsers == null || sameUsers.isEmpty()) {            // TH chưa có trong DB -> insert
                        insertUser(ele, updater, allOrg, allRank);
                    } else {                                                      // TH đã có trong DB -> update
                        for (UserInfo sameUser : sameUsers) {
                            updateUser(ele, sameUser, updater, allOrg, allRank);
                        }
                    }
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
//            });
        }

        // cập nhật lại cho các user bị xóa (chuyển cờ IsDelete)
        List<User> allUserDeleted365 = this.getAllUserDeleted(allUser365);
        for(User ele : allUserDeleted365){
            try {
                List<UserInfo> sameUsers = this.userInfoRepository.findAllByIdInMicrosoft(ele.id);
                if(sameUsers != null && !sameUsers.isEmpty()){
                    for(UserInfo sameUser : sameUsers){
                        updateDeleteUser(sameUser, updater);
                    }
                }
            }catch (Exception e){
                log.error("{}", e);
            }
        }

        return true;
    }

    public void insertUser(User user, UserInfo updater, List<Organization> allOrg, List<Rank> allRank){
        if(user == null) return;
        if(!checkSync(user)) return;
        Instant now = Instant.now();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(USER_TYPE);
        userInfo.setIdInMicrosoft(user.id);
        userInfo.setEmail(user.mail);
        userInfo.setFullName(user.displayName);
        userInfo.setGivenName(user.givenName);
        userInfo.setDisplayName(user.displayName);
        userInfo.setName(user.displayName);
        userInfo.setSurname(user.surname);
        userInfo.setAddress(user.streetAddress);
        userInfo.setBusinessPhones(user.businessPhones != null ? user.businessPhones.stream().collect(Collectors.joining(", ")) : "");
        userInfo.setContactAddress(user.streetAddress);
        userInfo.setCreatedDate(now);
        userInfo.setModifiedDate(now);
        userInfo.setFax(user.faxNumber);
        userInfo.setMobilePhone(user.mobilePhone);
        userInfo.setIsActive(true);
        userInfo.setIsDelete(false);
        userInfo.setIsLocked(false);
        userInfo.setJobTitle(user.jobTitle);
        userInfo.setUserName(user.userPrincipalName);

        try {userInfo.setInfoInMicrosoft(objectMapper.writeValueAsString(user));} catch (JsonProcessingException e) {log.error("{}", e);}
        if(updater != null){
            userInfo.setCreatedName(updater.getFullName());
            userInfo.setDescription("Sync with office 365. Updater : " + updater.getFullName());
        }else{
            userInfo.setCreatedName(USER_UPDATER);
            userInfo.setDescription("Sync with office 365");
        }

        userInfo.setTennantCode(this.SYNC_365);     // đánh dấu là đồng bộ từ AD về
        // cập nhật thông tin phòng ban \\
        updateOrgAndRank(user, userInfo, allOrg, allRank);

        this.userInfoRepository.save(userInfo);
    }

    public void updateUser(User user, UserInfo userInfo, UserInfo updater, List<Organization> allOrg, List<Rank> allRank){
        boolean same = false;
        try {
            String infoUsse365 = this.objectMapper.writeValueAsString(user);
            if(infoUsse365.equals(userInfo.getInfoInMicrosoft())){
                same = true;
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
        if(user == null || same == true) return;
        if(!checkSync(user)) return;
        Instant now = Instant.now();
        userInfo.setUserType(USER_TYPE);
        userInfo.setIdInMicrosoft(user.id);
        userInfo.setEmail(user.mail);
        userInfo.setFullName(user.displayName);
        userInfo.setGivenName(user.givenName);
        userInfo.setDisplayName(user.displayName);
        userInfo.setName(user.displayName);
        userInfo.setSurname(user.surname);
        userInfo.setAddress(user.streetAddress);
        userInfo.setBusinessPhones(user.businessPhones != null ? user.businessPhones.stream().collect(Collectors.joining(", ")) : "");
        userInfo.setContactAddress(user.streetAddress);
        //userInfo.setCreatedDate(now);
        userInfo.setModifiedDate(now);
        userInfo.setFax(user.faxNumber);
        userInfo.setMobilePhone(user.mobilePhone);
        userInfo.setIsActive(true);
        userInfo.setIsDelete(false);
        userInfo.setIsLocked(false);
        userInfo.setJobTitle(user.jobTitle);
        userInfo.setMail(user.mail);
        userInfo.setUserName(user.userPrincipalName);
        try {userInfo.setInfoInMicrosoft(objectMapper.writeValueAsString(user));} catch (JsonProcessingException e) {log.error("{}", e);}
        if(updater != null){
            userInfo.setCreatedName(updater.getFullName());
            userInfo.setDescription("Sync with office 365. Updater : " + updater.getFullName());
        }else{
            userInfo.setCreatedName(USER_UPDATER);
            userInfo.setDescription("Sync with office 365");
        }

        userInfo.setTennantCode(this.SYNC_365);     // đánh dấu là đồng bộ từ AD về

        // cập nhật thông tin phòng ban \\
        updateOrgAndRank(user, userInfo, allOrg, allRank);

        this.userInfoRepository.save(userInfo);
    }


    public void updateDeleteUser(UserInfo userInfo, UserInfo updater){
        userInfo.setIsDelete(true);
        userInfo.setModifiedDate(Instant.now());
        try {
            JSONObject jsonObject = new JSONObject(userInfo.getInfoInMicrosoft());
            jsonObject.put("isDelete", true);
            userInfo.setInfoInMicrosoft(jsonObject.toString());
        }catch (Exception e){log.error("{}", e);}
        if(updater != null){
            userInfo.setCreatedName(updater.getFullName());
            userInfo.setDescription("Sync with office 365. Updater : " + updater.getFullName());
        }else{
            userInfo.setCreatedName(USER_UPDATER);
            userInfo.setDescription("Sync with office 365");
        }
        this.userInfoRepository.save(userInfo);
    }

    /**
     * Hầm thực hiện lấy các user đang còn hoạt động
     * @return
     */
    private List<User> getAllUser(){
        List<User> result = new ArrayList<>();
        UserCollectionPage graphServiceAllUser = graphService.getAllUser();
        do {
            List<User> currentPageUser = graphServiceAllUser.getCurrentPage();
            result.addAll(currentPageUser);
            UserCollectionRequestBuilder nextPage = graphServiceAllUser.getNextPage();
            graphServiceAllUser = nextPage == null ? null : nextPage.buildRequest().get();
        }while (graphServiceAllUser != null);
        return result;
    }


    /**
     * Hàm thực hiện lấy các User bị xóa
     * @param allUser365    : danh sách user đang còn được hoạt động (ko bị xóa)
     * @return              : danh sách user bị xóa (trong đối tượng trả về đang chỉ thấy có ID ???)
     */
    private List<User> getAllUserDeleted(List<User> allUser365){
        List<User> result = new ArrayList<>();
        UserDeltaCollectionPage graphServiceAllUserDeleted = graphService.getAllUserDelta();
        do {
            List<User> currentPage = graphServiceAllUserDeleted.getCurrentPage();
            result.addAll(currentPage);
            UserDeltaCollectionRequestBuilder nextPage = graphServiceAllUserDeleted.getNextPage();
            graphServiceAllUserDeleted = nextPage == null ? null : nextPage.buildRequest().get();
        }while (graphServiceAllUserDeleted != null);
        if(allUser365 == null) return result;
        return result.stream().filter(ele -> !allUser365.stream().anyMatch(ele1 -> ele.id.equals(ele1.id))).collect(Collectors.toList());
    }

    private UserInfo updateOrgAndRank(User user, UserInfo userInfo, List<Organization> allOrg, List<Rank> allRank){
        // cập nhập phòng ban của user \\
        String orgName = user.department;
        if(Strings.isNullOrEmpty(orgName)) orgName = user.officeLocation;
        if(!"TRUE".equalsIgnoreCase(this.HAS_CHECK_ORG_SYNC) && !"ALL".equalsIgnoreCase(this.ORG_SYNC)){
            orgName = this.UNCATEGORY_ORG;
        }
        if(!Strings.isNullOrEmpty(orgName)){
            String finalOrgName = orgName;
            Organization org = allOrg.stream().filter(ele -> {
                return finalOrgName.equalsIgnoreCase(ele.getOrganizationName());
            }).findFirst().orElse(null);
            if(org != null){            // TH đã tồn tại phòng ban
                Set<Organization> organizationSet = userInfo.getOrganizations() == null ? new HashSet<>() : userInfo.getOrganizations();
                organizationSet.add(org);
                userInfo.setOrganizations(organizationSet);
            }else{                       // Th chưa có phòng ban -> tạo thêm phòng ban
                if("TRUE".equalsIgnoreCase(this.FEATURE_CREATE_ORG_CHART) && !orgName.equals(UNCATEGORY_ORG)){
                    Organization parentOrg = null;
                    if(user.manager != null){
                        User userWithManager = this.graphService.getUserWithManager(user.id);
                        if(userWithManager != null){
                            String parentOrgName = userWithManager.department;
                            if(Strings.isNullOrEmpty(parentOrgName)) parentOrgName = userWithManager.officeLocation;
                            if(!Strings.isNullOrEmpty(parentOrgName)){
                                String finalParentOrgName = parentOrgName;
                                parentOrg = allOrg.stream().filter(ele -> {
                                    return finalParentOrgName.equalsIgnoreCase(ele.getOrganizationName());
                                }).findFirst().orElse(null);
                                if(parentOrg == null){
                                    parentOrg = createOrg(parentOrgName, null);
                                    if(parentOrg != null) allOrg.add(parentOrg);
                                }
                            }
                        }
                    }
                    org = createOrg(orgName, parentOrg);
                }else{
                    org = createOrg(orgName, null);
                }
                allOrg.add(org);
                Set<Organization> organizationSet = userInfo.getOrganizations() == null ? new HashSet<>() : userInfo.getOrganizations();
                organizationSet.add(org);
                userInfo.setOrganizations(organizationSet);
            }
        }

        // cập nhật chứa rank \\
        String rankName = user.jobTitle;
        if(!Strings.isNullOrEmpty(rankName)){
            Rank rank = allRank.stream().filter(ele -> {
                return rankName.equalsIgnoreCase(ele.getRankName());
            }).findFirst().orElse(null);
            if(rank != null){                   // th nếu đã có chức danh này trên DB
                Set<Rank> rankSet = userInfo.getRanks() == null ? new HashSet<>() : userInfo.getRanks();
                rankSet.add(rank);
                userInfo.setRanks(rankSet);
            }else {                             // th chưa có chức danh -> tạo thêm chức danh
                rank = createRank(rankName);
                allRank.add(rank);
                Set<Rank> rankSet = userInfo.getRanks() == null ? new HashSet<>() : userInfo.getRanks();
                rankSet.add(rank);
                userInfo.setRanks(rankSet);
            }
        }
        return userInfo;
    }

    private Organization createOrg(String orgName, Organization organizationParent){
        Instant now = Instant.now();
        Organization organization = new Organization();
        organization.setOrganizationName(orgName);
        organization.setCreatedDate(now);
        organization.setCreatedName(USER_UPDATER);
        organization.setModifiedDate(now);
        organization.setModifiedName(USER_UPDATER);
        organization.setDescription("Sync with office 365");
        organization.setIsActive(true);
        organization.setIsDelete(false);
        organization.setIsSyncAD(true);
        organization.setOrgParent(organizationParent);

        organization.setTennantCode(this.SYNC_365);     // đánh dấu là đồng bộ từ AD về

        organization = this.organizationRepository.save(organization);
        return organization;
    }

    private Rank createRank(String rankName){
        Instant now = Instant.now();
        Rank rank = new Rank();
        rank.setRankName(rankName);
        rank.setRankCode("RANK AD: " + rankName);
        rank.setCreatedDate(now);
        rank.setCreatedName(USER_UPDATER);
        rank.setModifiedDate(now);
        rank.setModifiedName(USER_UPDATER);
        rank.setIsActive(true);
        rank.setIsDelete(false);

        rank.setTennantCode(this.SYNC_365);     // đánh dấu là đồng bộ từ AD về

        rank = this.rankRepository.save(rank);
        return rank;
    }

    /**
     * Hàm kiểm tra xem user có cần đồng bộ hay không (nếu có chứa VCR trong officeLocation hoặc trong department thì mới đồng bộ)
     * @param user
     * @return: true nếu có đồng bộ, false nếu không đồng bộ
     */
    private boolean checkSync(User user){
        if(!"TRUE".equalsIgnoreCase(HAS_CHECK_ORG_SYNC)) return true;                   // nếu không cần kiểm tra đơn vị -> trả về true để đồng bộ tất cả
        String department = user.department == null ? "" : user.department;
        String officeLocation = user.officeLocation == null ? "" : user.officeLocation;
        if(department.isEmpty() && officeLocation.isEmpty()) return false;
        if(department.toUpperCase().contains(ORG_SYNC)) return true;
        if(officeLocation.toUpperCase().contains(ORG_SYNC)) return true;
        return false;
    }
}
