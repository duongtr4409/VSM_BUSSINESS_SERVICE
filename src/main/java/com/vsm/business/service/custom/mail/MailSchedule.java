package com.vsm.business.service.custom.mail;

import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import io.swagger.models.auth.In;
import org.elasticsearch.common.Strings;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MailSchedule {

    private final Logger log = LoggerFactory.getLogger(MailSchedule.class);

    private final RequestDataRepository requestDataRepository;

    private final MailTemplateRepository mailTemplateRepository;

    private final ManageStampInfoRepository manageStampInfoRepository;

    private final SignDataRepository signDataRepository;

    private final StepDataRepository stepDataRepository;

    private final ProcessDataRepository processDataRepository;

    private MailCustomService mailCustomService;

    @Value("${mail_warning.day_work}")
    private String[] DAY_WORK;

    @Value("${mail_warning.limit_day:2}")
    private int LIMIT_DAY_WARNING;

    @Value("${mail_warning.mail_template_code_waring:CANHBAOQUAHAN}")
    private String MAIL_TEMPLATE_CODE_CANH_BAO_QUA_HAN;

    public MailSchedule(RequestDataRepository requestDataRepository, MailTemplateRepository mailTemplateRepository, ManageStampInfoRepository manageStampInfoRepository, SignDataRepository signDataRepository, StepDataRepository stepDataRepository, ProcessDataRepository processDataRepository, MailCustomService mailCustomService) {
        this.requestDataRepository = requestDataRepository;
        this.mailTemplateRepository = mailTemplateRepository;
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.signDataRepository = signDataRepository;
        this.stepDataRepository = stepDataRepository;
        this.processDataRepository = processDataRepository;
        this.mailCustomService = mailCustomService;
    }

    /**
     * Hàm thực hiện gửi mail cảnh báo các phiếu yêu cầu quá hạn xử lý
     */
//    @Scheduled(cron = "${cron.tab-send-mail:0 00 08 * * ?}")
    public void sendMailWarningOutOfDate(){
        List<RequestData> requestDataList = requestDataRepository.getAllRequestDataWarningOutOfDate(2L);
        if(requestDataList != null && !requestDataList.isEmpty()){
            Instant now = Instant.now();
            int n = requestDataList.size();
            for(int i=0; i<n; i++){
                RequestData requestData = requestDataList.get(i);
                if(requestData.getExpiredTime() != null){
                    if(countDayWork(requestData.getExpiredTime(), now) >= LIMIT_DAY_WARNING){       // nếu số ngày làm việc quá 2(số ngày quá hạn cần cảnh báo) ngày -> gửi mail cảnh báo
                        try {
                            log.info("Send Mail cảnh báo: phiếu yêu cầu: {}({}). hạn xử lý: {}", requestData.getRequestDataCode(), requestData.getId(), requestData.getExpiredTime());
                            sendMailCanhBao(requestData);
                        }catch (Exception e){log.error("{}", e);}
                    }
                }
            }
        }
    }

    @Scheduled(cron = "${cron.tab-send-mail:0 00 08 * * ?}")
    public void sendMailWarningOutOfDateV2(){
        List<RequestData> requestDataList = requestDataRepository.getAllRequestDataWarningOutOfDate(2L);
        if(requestDataList != null && !requestDataList.isEmpty()){
            Map<Long, UserInfo> mapUser = new HashMap<>();                      // thông tin User;
            Map<Long, List<RequestData>> mapRequestData = new HashMap<>();      // map chứa thông tin phiếu yêu cầu quá hạn cần gửi mail (nhóm theo User)
            Instant now = Instant.now();
            int n = requestDataList.size();
            for(int i=0; i<n; i++){
                RequestData requestData = requestDataList.get(i);
                if(requestData.getExpiredTime() != null){
                    if(countDayWork(requestData.getExpiredTime(), now) >= LIMIT_DAY_WARNING){       // nếu số ngày làm việc quá 2(số ngày quá hạn cần cảnh báo) ngày -> gửi mail cảnh báo
                        List<UserInfo> userNeedProcess = this.getUserNeedProcessOfRequestData(requestData);
                        for(UserInfo userInfo : userNeedProcess){
                            try {mapUser.put(userInfo.getId(), userInfo);}catch (Exception e){log.error("{}", e);}
                            try {
                                List<RequestData> requestDataListTemp = null;
                                if(mapRequestData.containsKey(userInfo.getId())){
                                    requestDataListTemp = mapRequestData.get(userInfo.getId());
                                    if(requestDataListTemp == null) requestDataListTemp = new ArrayList<>();
                                }else{
                                    requestDataListTemp = new ArrayList<>();
                                }
                                requestDataListTemp.add(requestData);
                                mapRequestData.put(userInfo.getId(), requestDataListTemp);
                            }catch (Exception e){
                                log.error("{}", e);
                            }
                        }

                    }
                }
            }

            if(!mapRequestData.isEmpty()){
               for(Long userId : mapRequestData.keySet()){
                   try {
                       if(userId != null){
                           UserInfo userInfo = mapUser.get(userId);
                           List<RequestData> requestDataListTemp = mapRequestData.get(userId);
                           if(userInfo != null && requestDataListTemp != null){
                               log.info("Send Mail cảnh báo: phiếu yêu cầu cho {}: {}", userInfo.getEmail(), requestDataListTemp.stream().map(ele -> ele.getRequestDataCode() + "(" + ele.getExpiredTime() + ")").collect(Collectors.joining(" | ")));
                               sendMailCanhBaoV2(userInfo, requestDataListTemp);
                           }
                       }
                   }catch (Exception e){
                       log.error("{}", e);
                   }
               }
            }
        }
    }



                            // utils \\
    /**
     * Hàm thực hiện đếm số ngày làm việc giữa 2 khoảng thời gian
     * @param startDate : thời gian bắt đầu
     * @param endDate   : thời gian kết thúc
     * @return: số ngày làm việc giữa 2 khoảng thời gian
     */
    public int countDayWork(Instant startDate, Instant endDate){
        if(startDate == null || endDate == null) return 0;        // nếu 1 trong 2 giá trị null -> trả về 0

        List<String> DayWork = Arrays.asList("Monday", "TueDay", "Wednesday", "Thursday", "Friday", "Saturday");
        try {
            DayWork = Arrays.stream(DAY_WORK).collect(Collectors.toList());
        }catch (Exception e){
            log.error("{}", e);
        }
        int result = 0;
        Instant startDateTemp = startDate.truncatedTo(ChronoUnit.DAYS);
        Instant endDateTemp = endDate.truncatedTo(ChronoUnit.DAYS);
        Instant temp = endDateTemp;
        while(!temp.isBefore(startDateTemp)){
            String dayName = temp.atOffset(ZoneOffset.UTC).getDayOfWeek().name();
            if(DayWork.stream().anyMatch(ele -> ele.equalsIgnoreCase(dayName))){
                result++;
            }
            temp = temp.plus(-1, ChronoUnit.DAYS);
        }
        return result;
    }

    /**
     * hàm thực hiện khởi tạo mail template cho email cảnh báo phiếu yêu cầu quá hạn (nếu chưa có trong DB)
     * @return
     */
    public MailTemplate initMailTemplateCanhBaoQuaHan(String mailCode, String mailName, String mailContent, String mailSubject){
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setMailTemplateCode(Strings.isNullOrEmpty(mailCode) ? "CANHBAOQUAHAN" : mailCode);
        mailTemplate.setMailTemplateName(Strings.isNullOrEmpty(mailName) ? "EOFFICE: MAIL CẢNH BÁO PHIẾU YÊU CẦU QUÁ HẠN XỬ LÝ." : mailName);

        mailTemplate.setBccerDefault("[]");
        mailTemplate.setCcerDefault("[]");
        mailTemplate.setReceiverDefault("[]");
        mailTemplate.setCreatedName("ADMIN");
        mailTemplate.setCreatedDate(Instant.now());
        mailTemplate.setModifiedName("ADMIN");
        mailTemplate.setModifiedDate(Instant.now());
        mailTemplate.setIsActive(true);
        mailTemplate.setIsDelete(false);
        mailTemplate.setDescription("");
        mailTemplate.setPathFile("");
        mailTemplate.setMappingInfo("");
        mailTemplate.setItemId365("");

//        mailTemplate.setSubject(Strings.isNullOrEmpty(mailSubject) ? "VCR: CẢNH BÁO PHIẾU QUÁ HẠN XỬ LÝ" : mailContent);
//        mailTemplate.setContentFile(Strings.isNullOrEmpty(mailContent) ? "<p><strong>Dear anh/chị</strong></p><p>Phiếu yêu cầu : [rfa_code] đã quá thời hạn xử lý.</p><p><em><strong>Thông tin yêu cầu / Request information</strong></em></p><p>&nbsp; - Loại tờ trình / Request type: [request_type_name]</p><p>&nbsp; - Số tờ trình/ Code: [rfa_code]</p><p>&nbsp; - Tên tờ trình/Title: [rfa_name]</p><p>- Người trình/ Requestor: [requester_name] (Fullname)</p><p>- Bộ phận/Department: [requester_department] (Tên bộ phận)</p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>" : mailContent);
//        mailTemplate.setContent(Strings.isNullOrEmpty(mailContent) ? "<p><strong>Dear anh/chị</strong></p><p>Phiếu yêu cầu : [rfa_code] đã quá thời hạn xử lý.</p><p><em><strong>Thông tin yêu cầu / Request information</strong></em></p><p>&nbsp; - Loại tờ trình / Request type: [request_type_name]</p><p>&nbsp; - Số tờ trình/ Code: [rfa_code]</p><p>&nbsp; - Tên tờ trình/Title: [rfa_name]</p><p>- Người trình/ Requestor: [requester_name] (Fullname)</p><p>- Bộ phận/Department: [requester_department] (Tên bộ phận)</p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>": mailContent);
//        mailTemplate.setFooter("");

        // mẫu mail V2
        mailTemplate.setSubject(Strings.isNullOrEmpty(mailSubject) ? "VCR: CẢNH BÁO PHIẾU QUÁ HẠN XỬ LÝ" : mailSubject);
        mailTemplate.setContentFile(Strings.isNullOrEmpty(mailContent) ? "<p><strong>Dear anh/chị</strong></p><p>Anh/chị có một số phiếu yêu cầu quá hạn xử lý. Anh/chị vui lòng truy cập hệ thống để thực hiện xử lý phiếu đã quá hạn.</p><p><em><strong>Danh sách phiếu yêu cầu quá hạn:</strong></em></p><p>[rfa_link_list]</p><p></p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>" : mailContent);
        mailTemplate.setContent(Strings.isNullOrEmpty(mailContent) ? "<p><strong>Dear anh/chị</strong></p><p>Anh/chị có một số phiếu yêu cầu quá hạn xử lý. Anh/chị vui lòng truy cập hệ thống để thực hiện xử lý phiếu đã quá hạn.</p><p><em><strong>Danh sách phiếu yêu cầu quá hạn:</strong></em></p><p>[rfa_link_list]</p><p></p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>": mailContent);
        mailTemplate.setFooter("");

        mailTemplate = this.mailTemplateRepository.save(mailTemplate);
        return mailTemplate;
    }

    private final String TIEU_DE = "[Tiêu đề]";
    private final String MA_PHIEU = "[rfa_code]";
    private final String TEN_PHIEU = "[rfa_name]";
    private final String LOAI_PHIEU_YEU_CAU_TEN = "[request_type_name]";
    private final String NGUOI_TRINH_TEN = "[requester_name]";
    private final String TEN_DON_VI_TRINH = "[requester_department]";
    private final String LINK_PHIEU = "[rfa_link_tai_day]";
    private final String BRAND_LIST= "[rfa_brand_list]";
    @Value("${system.mailtemplate.link-auto:https://uat-eoffice.vincom.com.vn/phieu-yeu-cau/{{id}}/phe-duyet}")
    public String LINK;
    private String bindingDataToMailContent(String content, RequestData requestData, List<UserInfo> userInfoList){
        String link_phieu = LINK.replace("{{id}}", String.valueOf(requestData.getId()));
        String link = "<a href='{{REQUESTDATA_LINK}}' rel='noopener noreferrer nofollow'>Phiếu yêu cầu:&nbsp;" + requestData.getRequestDataCode() + "</a>";
        link = link.replace("{{REQUESTDATA_LINK}}", link_phieu);
        content = content.replace(MA_PHIEU, requestData.getRequestDataCode());
        content = content.replace(TEN_PHIEU, requestData.getTitle());
        content = content.replace(LOAI_PHIEU_YEU_CAU_TEN, requestData.getRequestType().getRequestTypeName());
        try {
            if(userInfoList == null || userInfoList.isEmpty()) {
                content = content.replace(NGUOI_TRINH_TEN, "");
                content.replace(TEN_DON_VI_TRINH, "");
            }else{
                content = content.replace(NGUOI_TRINH_TEN, userInfoList.stream().map(ele -> ele.getFullName()).collect(Collectors.joining(", ")));
                content = content.replace(TEN_DON_VI_TRINH, userInfoList.stream().map(ele -> {
                    if(ele.getOrganizations() == null || ele.getOrganizations().isEmpty()) return "";
                    return ele.getOrganizations().stream().map(ele1 -> ele1.getOrganizationName()).collect(Collectors.joining(", "));
                }).collect(Collectors.joining(", ")));
            }
        }catch (Exception e){log.error("{}", e);}
        try{
            List<ManageStampInfo> manageStampInfoList = this.manageStampInfoRepository.findAllByRequestDataId(requestData.getId());
            List<SignData> signDataList = this.signDataRepository.findAllByRequestDataId(requestData.getId());
            CharSequence[] all = Stream.concat(signDataList.stream().map(e->e.getSignName()), manageStampInfoList.stream().map(e->e.getName())).toArray(CharSequence[]::new);
            content = content.replace(BRAND_LIST, String.join("</br>", all));
        }catch (Exception e){
            content = content.replace(BRAND_LIST, "");
            log.error("FEATURE_FILL_SIGN_DATA: {}", e);
        }
        content = content.replace(LINK_PHIEU, link);
        return content;
    }

    private String createLink(RequestData requestData){
        try {
            String link_phieu = LINK.replace("{{id}}", String.valueOf(requestData.getId()));
            String link = "<a href='{{REQUESTDATA_LINK}}' rel='noopener noreferrer nofollow'>Phiếu yêu cầu:&nbsp;" + requestData.getRequestDataCode() + "</a>";
            link = link.replace("{{REQUESTDATA_LINK}}", link_phieu);
            return link;
        }catch (Exception e){
            log.error("{}", e);
            return "";
        }
    }

    private String bindingDataToMailTitle(String title, RequestData requestData){
        title = title.replace(TIEU_DE, requestData.getTitle());
        return title;
    }

    private void sendMailCanhBao(RequestData requestData){
        List<MailTemplate> mailTemplateList = mailTemplateRepository.findAllByMailTemplateCode(MAIL_TEMPLATE_CODE_CANH_BAO_QUA_HAN);
        MailTemplate mailCanhBaoQuaHan;
        if(mailTemplateList != null && mailTemplateList.size() > 0){
            mailCanhBaoQuaHan = mailTemplateList.get(0);
        }else{
            mailCanhBaoQuaHan = initMailTemplateCanhBaoQuaHan(MAIL_TEMPLATE_CODE_CANH_BAO_QUA_HAN, "", "", "");
        }

        Long currentRound = requestData.getCurrentRound();
        StepData currentStepData = requestData.getStepData().stream().filter(ele -> {
            return ele.getIsActive() == true && ele.getRoundNumber().equals(currentRound);
        }).findFirst().orElse(null);
        if(currentStepData == null || currentStepData.getUserInfos() == null || currentStepData.getUserInfos().isEmpty()) return;

        List<UserInfo> receiveMail = currentStepData.getUserInfos().stream().collect(Collectors.toList());

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(mailCanhBaoQuaHan.getMailTemplateName());
        mailInfoDTO.setProps(new HashMap<>());
        String content = mailCanhBaoQuaHan.getContent();
        mailInfoDTO.setContent(bindingDataToMailContent(content, requestData, receiveMail));
        String sucbject = mailCanhBaoQuaHan.getSubject();
        mailInfoDTO.setSubject(bindingDataToMailTitle(sucbject, requestData));

        List<String> mailReceive = new ArrayList<>();
        List<String> mailCC = new ArrayList<>();

        if(receiveMail != null)
            mailReceive.addAll(receiveMail.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));

        if(mailCanhBaoQuaHan.getReceiverDefault() != null){
            try {
                JSONArray receiveDefaults = new JSONArray(Strings.isNullOrEmpty(mailCanhBaoQuaHan.getReceiverDefault()) ? "[]" : mailCanhBaoQuaHan.getReceiverDefault());
                for(int j=0; j<receiveDefaults.length(); j++){
                    Object userDefault = receiveDefaults.get(j);
                    if(userDefault instanceof String){
                        mailReceive.add(userDefault.toString());
                    }else{
                        String emailDefault = receiveDefaults.getJSONObject(j).getString("email");
                        mailReceive.add(emailDefault);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }

        if(mailCanhBaoQuaHan.getCcerDefault() != null){
            try {
                JSONArray ccDefaults = new JSONArray(Strings.isNullOrEmpty(mailCanhBaoQuaHan.getCcerDefault()) ? "[]" : mailCanhBaoQuaHan.getCcerDefault());
                for(int j=0; j<ccDefaults.length(); j++){
                    Object ccDefault = ccDefaults.get(j);
                    if(ccDefault instanceof  String){
                        mailCC.add(ccDefault.toString());
                    }else{
                        String emailDefault = ccDefaults.getJSONObject(j).getString("email");
                        mailCC.add(emailDefault);
                    }
                }
            }catch (Exception e){ log.error("{}", e); }
        }

        mailInfoDTO.setEmailAddressTo(mailReceive);
        mailInfoDTO.setEmailAddressCC(mailCC);

        try {
            this.mailCustomService.sendMailWithTemplate365(mailInfoDTO, requestData.getId());
        }catch (Exception e){
            log.error("{}", e);
        }
    }


    private final String LINK_LIST = "[rfa_link_list]";
    private String bindingDataToMailContentV2(String content, List<RequestData> requestDataList, UserInfo userInfo){
        try {
            if(userInfo != null) {
                content = content.replace(NGUOI_TRINH_TEN, userInfo.getFullName());
                content.replace(TEN_DON_VI_TRINH, (userInfo.getOrganizations() != null) ? userInfo.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : "");
            }
        }catch (Exception e){log.error("{}", e);}
        try{
            if(requestDataList != null){
                String link = requestDataList.stream().map(ele -> this.createLink(ele)).collect(Collectors.joining("</br>"));
                content = content.replace(LINK_LIST, link);
            }else{
                content = content.replace(LINK_LIST, "");
            }
        }catch (Exception e){
            content = content.replace(LINK_LIST, "");
            log.error("FEATURE_FILL_SIGN_DATA: {}", e);
        }
        return content;
    }

    /**
     * Hàm thực hiện lấy dánh sách người đang cần sử lý của phiếu yêu cầu
     * @param requestData : phiếu yêu cầu
     * @return              : danh sách người đang cần xử lý phiếu yêu cầu
     */
    private List<UserInfo> getUserNeedProcessOfRequestData(RequestData requestData){
        List<UserInfo> result = new ArrayList<>();
        if(requestData == null) return result;
        try {
            Long currentRound = requestData.getCurrentRound();
            StepData currentStepData = requestData.getStepData().stream().filter(ele -> {
                return (ele.getIsActive() == true && (ele.getRoundNumber() != null && ele.getRoundNumber().equals(currentRound)) || (ele.getRoundNumber() == null && currentRound == null));
            }).findFirst().orElse(null);
            if(currentStepData == null) return result;
            return currentStepData.getUserInfos().stream().collect(Collectors.toList());
        }catch (Exception e){
            log.error("{}", e);
            return result;
        }
    }

    private void sendMailCanhBaoV2(UserInfo userInfo, List<RequestData> requestDataList){
        List<MailTemplate> mailTemplateList = mailTemplateRepository.findAllByMailTemplateCode(MAIL_TEMPLATE_CODE_CANH_BAO_QUA_HAN);
        MailTemplate mailCanhBaoQuaHan;
        if(mailTemplateList != null && mailTemplateList.size() > 0){
            mailCanhBaoQuaHan = mailTemplateList.get(0);
        }else{
            mailCanhBaoQuaHan = initMailTemplateCanhBaoQuaHan(MAIL_TEMPLATE_CODE_CANH_BAO_QUA_HAN, "", "", "");
        }

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(mailCanhBaoQuaHan.getMailTemplateName());
        mailInfoDTO.setProps(new HashMap<>());
        String content = mailCanhBaoQuaHan.getContent();
        mailInfoDTO.setContent(bindingDataToMailContentV2(content, requestDataList, userInfo));
        String sucbject = mailCanhBaoQuaHan.getSubject();
        mailInfoDTO.setSubject(sucbject);

        List<String> mailReceive = new ArrayList<>();
        List<String> mailCC = new ArrayList<>();

        if(userInfo != null)
            mailReceive.add(userInfo.getEmail());

        if(mailCanhBaoQuaHan.getReceiverDefault() != null){
            try {
                JSONArray receiveDefaults = new JSONArray(Strings.isNullOrEmpty(mailCanhBaoQuaHan.getReceiverDefault()) ? "[]" : mailCanhBaoQuaHan.getReceiverDefault());
                for(int j=0; j<receiveDefaults.length(); j++){
                    Object userDefault = receiveDefaults.get(j);
                    if(userDefault instanceof String){
                        mailReceive.add(userDefault.toString());
                    }else{
                        String emailDefault = receiveDefaults.getJSONObject(j).getString("email");
                        mailReceive.add(emailDefault);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }

        if(mailCanhBaoQuaHan.getCcerDefault() != null){
            try {
                JSONArray ccDefaults = new JSONArray(Strings.isNullOrEmpty(mailCanhBaoQuaHan.getCcerDefault()) ? "[]" : mailCanhBaoQuaHan.getCcerDefault());
                for(int j=0; j<ccDefaults.length(); j++){
                    Object ccDefault = ccDefaults.get(j);
                    if(ccDefault instanceof  String){
                        mailCC.add(ccDefault.toString());
                    }else{
                        String emailDefault = ccDefaults.getJSONObject(j).getString("email");
                        mailCC.add(emailDefault);
                    }
                }
            }catch (Exception e){ log.error("{}", e); }
        }

        mailInfoDTO.setEmailAddressTo(mailReceive);
        mailInfoDTO.setEmailAddressCC(mailCC);

        try {
            this.mailCustomService.sendMailWithTemplate365(mailInfoDTO, null);
        }catch (Exception e){
            log.error("{}", e);
        }
    }
}
