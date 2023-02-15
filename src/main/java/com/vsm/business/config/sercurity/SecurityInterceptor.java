package com.vsm.business.config.sercurity;

//import com.vsm.business.config.sercurity.utils.GlobalWrapFilter;
//import com.vsm.business.config.sercurity.utils.MultiReadRequest;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.UserUtils;
import org.elasticsearch.common.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    private final String SIZE = "size";

    private final String PAGE = "page";

    @Value("${security.query-string.max-size:100}")
    private String MAX_SIZE;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private AuthenticateUtils authenticateUtils;

    // message \\
    private final String BAD_REQUEST_MESS = "Bad request param!";
    private final String NOT_AUTHORITY = "NOT AUTHORITY !";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String queryString = request.getQueryString();
        Map<String, String> queryParams = this.getParamFormRequest(queryString);

        if(!this.checkSizeOfRequest(queryParams)) throw new RuntimeException(this.BAD_REQUEST_MESS);

        if(this.checkAPICustomer(request)) return super.preHandle(request, response, handler);      // nếu là API của màn khách hàng -> cho qua luôn

        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(!this.checkPermissionOfRequest(request, currentUser)) throwAuthorityException();
        if(!this.checkPermissionOfRequestData(request, currentUser)) throwAuthorityException();
//        if(!this.checkPermissionChangeDataRequestData(request, handler, currentUser)) throwAuthorityException();
        if(!this.checkPermissionChangeDataAdmin(request, response, handler, currentUser)) throwAuthorityException();

        // lấy được thông tin function halder API \\
//        if(handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Parameter[] parameters = handlerMethod.getMethod().getParameters();
//            for(Parameter parameter : parameters){
//                System.out.println(parameter.getName());
//            }
//        }

//        writeLogHeap("feature_log_start: " + request.getRequestURL().toString());

        return super.preHandle(request, response, handler);
    }

    //
//@Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        writeLogHeap("feature_log_end: " + request.getRequestURL().toString());
//        super.afterCompletion(request, response, handler, ex);
//    }

    private void writeLogHeap(String prefix){
        try {
            log.info(prefix);
            log.info("feature_log_ Heap Size: total: {}, max: {}, free: {}", Runtime.getRuntime().totalMemory()/1048576, Runtime.getRuntime().maxMemory()/1048576, Runtime.getRuntime().freeMemory()/1048576);
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    private Map<String, String> getParamFormRequest(String queryString){
        Map<String, String> result = new HashMap<>();
        if(Strings.isNullOrEmpty(queryString)) return result;
        String[] params = queryString.split(Pattern.quote("&"));
        for(String param : params){
            try {
                String[] paramValues = param.split(Pattern.quote("="));
                String key = paramValues[0];
                String value = paramValues[1];
                result.put(key, value);
            }catch (Exception e){}
        }
        return result;
    }

    private boolean checkSizeOfRequest(Map<String, String> queryParams){
        Long maxSize = 100L;
        try {
            maxSize = Long.valueOf(this.MAX_SIZE);
        }catch (Exception e){maxSize = -1L;};
        if(maxSize > 0L){
            try {
                Long size = Long.valueOf(queryParams.get(this.SIZE));
                if(size > maxSize) return false;
                return true;
            }catch (Exception e){return true;}
        }else{
            return true;
        }
    }


    private final String ATTRIBUTE_USERID = "userId";
    private final List<String> USER_MANAGER_API = Arrays.asList("signature-infomations");   // api liênqun đến quản trị user
    /**
     * Hàm thực hiện kiểm tra call API hiện tại có truyền đúng ID với User đang đăng nhập hay không
     * @param request       : thông tin request
     * @param currentUser   : user hiện đang đăng nhập
     * @return: true: nếu đúng thông tin | false: nếu sai thông tin
     */
    private boolean checkPermissionOfRequest(HttpServletRequest request, MyUserDetail currentUser){
        Object attributes = request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
        if(attributes != null && attributes.getClass().isAssignableFrom(LinkedHashMap.class)){
            try {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) attributes;
                if(map != null && !map.isEmpty()){
                    Long userIdData = Long.valueOf(map.get(this.ATTRIBUTE_USERID).toString());
                    if(currentUser == null) return false;
                    if(currentUser.getRolesString().stream().anyMatch(ele -> this.ADMIN_ROLE.equalsIgnoreCase(ele))){   // nếu User hiện tại là ADMIN -> kiểm tra xem nếu đang truy cập vào đữ liệu quản trị ngừoi dùng -> cho qua (Thông tin chữ ký (signature-infomations), thông tin quyền(roles), thông tin đơn vị(signature-infomations))
                        String url = request.getRequestURL().toString();
                        if(this.USER_MANAGER_API.stream().anyMatch(ele -> url.contains(ele))) return true;
                    }
                    if(userIdData != null && !userIdData.equals(currentUser.getId())) return false;
                    return true;
                }
                return true;
            }catch (Exception e){return true;}
        }
        return true;
    }

    private final String ATTRIBUTE_REQUESTDATAID = "requestDataId";
    @Autowired
    private RequestDataRepository requestDataRepository;

    /**
     * Hàm thực hiện kiểm tra user có quyền xem dữ liệu của requestData (requestData, stepData, consult, examine, ...)
     * @param request       : request gửi đến
     * @param currentUser   : user đang đăng nhập
     * @return: true nếu có quyền xem, false nếu ko có quyền
     */
    private boolean checkPermissionOfRequestData(HttpServletRequest request, MyUserDetail currentUser){
        Object attributes = request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
        if(attributes != null && attributes.getClass().isAssignableFrom(LinkedHashMap.class)){
            try {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) attributes;
                if(map != null && !map.isEmpty()){
                    Long requestDataId = Long.valueOf(map.get(this.ATTRIBUTE_REQUESTDATAID).toString());
                    if(currentUser == null) return false;
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

//                        return userInfoIdList.stream().anyMatch(ele -> ele != null && ele.equals(currentUser.getId()));
                        // 21/11/2022: cho phép thêm người được phân quyền (xem phiếu yêu cầu của loại yêu cầu, đơn vị)
                        return userInfoIdList.stream().anyMatch(ele -> ele != null && ele.equals(currentUser.getId()))
                            || this.authenticateUtils.checkPermisionForDataStatisticToUser(requestDataId);

                    }
                    return true;
                }
            }catch (Exception e){return true;}
        }
        return true;
    }

    /**
     * hàm thực hiện kiểm tra user đang đăng nhập có quyền call vào các API (Thêm sửa xóa) của dữ liệu ADMIN (Request, MailTemplate, ProcessInfo, Step)
     * @param request       : request
     * @param response      : response
     * @param handler       : Object thực hiện xử lý
     * @param currentUser   : thông tin user đang đăng nhập
     * @return : true nếu có quyền | false nếu ko có quyền
     */
    private final List<String> ADMIN_API = Arrays.asList("category-data", "category-groups", "centralized-shoppings", "dispatch-books", "fields", "field-in-forms", "forms", "mail-logs", "mail-templates", "organizations", "otps", "requests", "request-groups", "request-types", "roles", "signature-infomations", "status", "steps", "step-in-processes", "template-forms", "user-groups", "user-infos", "user-in-steps"/*, "role-organizations", "role-requests"*/);
    private final List<String> CHANGE_API = Arrays.asList("_save", "_delete", "_save_all", "_custom_save", "custom-save","");
    private final String API_PREFIX = "/api";
    public final static String ADMIN_ROLE = "Admin";
    private final List<String> METHOD_CHANGE_DATA = Arrays.asList("POST", "PUT", "PATCH", "DELETE");
    private boolean checkPermissionChangeDataAdmin(HttpServletRequest request, HttpServletResponse response, Object handler, MyUserDetail currentUser){
        try {
            if(!this.METHOD_CHANGE_DATA.stream().anyMatch(ele -> ele.equalsIgnoreCase(request.getMethod()))) return true;               // nếu ko phải là phương thức thay đổi dữ liệu -> trả về true

            String url = request.getRequestURL().toString();
            if(url != null){
                if(this.ADMIN_API.stream().anyMatch(ele -> url.contains(ele))){

                    List<String> apiMatchs = this.ADMIN_API.stream().filter(ele -> url.contains(ele)).collect(Collectors.toList());     // lấy các API có tồn tại trong đường dẫn
                    if(apiMatchs != null && !apiMatchs.isEmpty()){
                        for(String apiMatch : apiMatchs){
                            for(String regex : this.CHANGE_API){
                                String tempRegex = this.API_PREFIX + "/" + (!Strings.isNullOrEmpty(regex) ? regex + "/" : "") + apiMatch;
                                Pattern pattern = Pattern.compile(tempRegex, Pattern.LITERAL);
                                Matcher matcher = pattern.matcher(url);
                                boolean matchFound = matcher.find();
                                if(matchFound){     // Th nếu là API thay đổi dữ liệu của ADMIN -> kiểm tra xem user có quyền ADMIN
                                    if(currentUser == null) return false;
                                    //return currentUser.getRoles().stream().anyMatch(ele -> ADMIN_ROLE.equalsIgnoreCase(ele.getRoleType()));
                                    // 11/11/2022: thêm kiểm tra cấu hình quyền sử dụng các tính năng
                                    //return currentUser.getRolesString().stream().anyMatch(ele -> ADMIN_ROLE.equalsIgnoreCase(ele));
                                    return  currentUser.getRolesString().stream().anyMatch(ele -> ADMIN_ROLE.equalsIgnoreCase(ele))
                                        && currentUser.getApiFeature().stream().anyMatch(ele1 -> {
                                        return url.contains(ele1);
                                    });
                                }
                            }
                        }
                        return true;
                    }

                }else{      // nếu đường dẫn ko chứa các link API của ADMIN
                    return true;
                }
            }
            return true;
        }catch (Exception e){return true;}
    }

    /**
     * Hàm thực hiện kiểm tra xem API có phải là của Khách hàng không
     * @param request   : request
     * @return : true nếu đúng là của khách hàng | false nếu không phải
     */
    private final String CUSTOMER_API = "/customer/api";
    private boolean checkAPICustomer(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        if(url == null) return false;
        return url.contains(this.CUSTOMER_API);
    }

    private void throwAuthorityException(){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.NOT_AUTHORITY);
    }

//    /**
//     * Hàm thực hiện kiểm tra quyền thêm, sửa, xóa dữ liệu con của phiếu yêu cầu (vd: buóc quy trình, trao đổi, lịch sử, xoát xét, yêu cầu xin ý kiến, ...)
//     * @param request
//     * @param handler
//     * @param currentUser
//     * @return: true: có quyền tạo | false: ko có quyền tạo
//     */
//    private final List<String> METHOD_CHANGE = Arrays.asList("POST", "PUT", "DELETE", "PATCH");
//    private final List<String> CHILD_DATA_OF_REQUESTDATA = Arrays.asList("CONSULT", "CONSULTREPLY", "EXAMINE", "EXAMINEREPLY", "FIELDDATA", "FORMDATA", "MANAGESTAMPINFO", "PROCESSDATA", "REQDATACHANGEHIS", "REQDATAPROCESSHIS"/*, "REQUESTDATA"*/, "REQUESTRECALL", "SIGNDATA", "STEPDATA", "TRANSFERHANDLE");
//    private final String CREATED_IN_BODY = "created";
//    private final String MODIFIED_IN_BODY = "modified";
//    private final String REQUEST_DATA_IN_BODY = "requestData";
//    private final String ID = "id";
//    private boolean checkPermissionChangeDataRequestData(HttpServletRequest request, Object handler, MyUserDetail currentUser){
//        String method = request.getMethod().toUpperCase();
//        if(!this.METHOD_CHANGE.stream().anyMatch(ele -> ele.equals(method))) return true;       // TH nếu không phải API thêm sửa xóa -> trả về true
//
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        String className = Strings.isNullOrEmpty(handlerMethod.getBeanType().getSimpleName()) ? null : handlerMethod.getBeanType().getSimpleName().toUpperCase();
//        if(className != null){
//            if(this.CHILD_DATA_OF_REQUESTDATA.stream().anyMatch(ele -> className.startsWith(ele))){
//                try {
//
//                    if(currentUser == null) return false;
//
//                    HttpServletRequest req = (HttpServletRequest) request;
//                    MultiReadRequest multiReadRequest = new MultiReadRequest(req);
//                    String jsonBobyString = multiReadRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//                    JSONObject jsonObjectBody = new JSONObject(jsonBobyString);
//
////                    Long createdId = 0l;
////                    try {
////                        JSONObject createdJSON = jsonObjectBody.getJSONObject(this.CREATED_IN_BODY);
////                        createdId = createdJSON.getLong(this.ID);
////                    }catch (Exception e){}
////                    Long modifiedId = 0l;
////                    try {
////                        JSONObject modifiedJSON = jsonObjectBody.getJSONObject(this.MODIFIED_IN_BODY);
////                        modifiedId = modifiedJSON.getLong(this.ID);
////                    }catch (Exception e){}
////                    if(!currentUser.getId().equals(createdId) && !currentUser.getId().equals(modifiedId)) return false;        // nếu thông tin người tạo hoặc ngừoi cập nhật ko giống với user đăng nhập -> trả về false
//
//                    JSONObject requestDataJSON = jsonObjectBody.getJSONObject(this.REQUEST_DATA_IN_BODY);
//                    Long requestDataId = requestDataJSON.getLong(this.ID);
//                    if(requestDataId != null){
//                        RequestData requestData = this.requestDataRepository.findById(requestDataId).orElseThrow(() -> new RuntimeException("No Data"));
//                        // lấy danh sách những người được truy cập dữ liệu của requestData \\
//                        Set<Long> userInfoIdList = new HashSet<>();
//                        if(requestData.getUserInfos() != null)      // danh sách người được chia sẻ
//                            userInfoIdList.addAll(requestData.getUserInfos().stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//                        if(requestData.getCreated() != null)        // người tạo phiếu
//                            userInfoIdList.add(requestData.getCreated().getId());
//                        if(requestData.getModified() != null)       // người sửa phiếu
//                            userInfoIdList.add(requestData.getModified().getId());
//                        if(requestData.getStepData() != null) {      // người trong quy trình
//                            requestData.getStepData().forEach(ele -> {
//                                if(ele.getUserInfos() != null)
//                                    userInfoIdList.addAll(ele.getUserInfos().stream().map(ele1 -> ele1.getId()).collect(Collectors.toSet()));
//                            });
//                        }
//
//                        return userInfoIdList.stream().anyMatch(ele -> ele != null && ele.equals(currentUser.getId()));
//
//                    }
//                    return true;
//
//                } catch (Exception e) {log.error("{}", e);return true;}
//            }else{      // TH nếu ko phải dữ liệu liên quan đến RequestData -> trả về true
//                return true;
//            }
//        }else{
//            return true;
//        }
//    }
}
