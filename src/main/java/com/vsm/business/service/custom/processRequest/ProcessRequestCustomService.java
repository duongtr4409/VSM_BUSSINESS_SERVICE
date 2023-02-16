package com.vsm.business.service.custom.processRequest;

import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.custom.FieldDataCustomService;
import com.vsm.business.service.custom.dataroom.DataRoomCustomService;
import com.vsm.business.service.custom.file.Folder365CustomService;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.mail.MailCustomService;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import com.vsm.business.service.custom.processRequest.bo.ApproveOption;
import com.vsm.business.service.custom.processRequest.bo.CustomerApproveOption;
import com.vsm.business.service.custom.processRequest.bo.ReSendOption;
import com.vsm.business.service.custom.sign.utils.SignUtils;
import com.vsm.business.service.mapper.SignDataMapper;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.ObjectUtils;
import org.elasticsearch.common.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProcessRequestCustomService {


    private final Logger log = LoggerFactory.getLogger(ProcessRequestCustomService.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    private final String NOT_PERMISSION_ERROR = "Can not permision process";

    @Autowired
    private ConditionUtils conditionUtils;

    @Autowired
    private MailCustomService mailCustomService;

    @Autowired
    private Folder365CustomService folder365CustomService;

    @Autowired
    private UploadFile365CustomService uploadFile365CustomService;

    @Autowired
    private RequestDataRepository requestDataRepository;

    @Autowired
    private ProcessDataRepository processDataRepository;

    @Autowired
    private StepDataRepository stepDataRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private MailTemplateRepository mailTempmailTemplateRepositoryate;

    @Autowired
    private ReqdataProcessHisRepository reqdataProcessHisRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RequestRecallRepository requestRecallRepository;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private SignDataRepository signDataRepository;

    @Autowired
    private SignDataMapper signDataMapper;

    @Autowired
    private DataRoomCustomService dataRoomCustomService;

    @Autowired
    private ManageStampInfoRepository manageStampInfoRepository;

    @Autowired
    private ResultOfStepRepository resultOfStepRepository;

    @Autowired
    private SignUtils signUtils;

    @Autowired
    private FieldDataCustomService fieldDataCustomService;

    @Autowired
    private FieldDataRepository fieldDataRepository;

    @Autowired
    private FormDataRepository formDataRepository;

    @Autowired
    private OTPRepository otpRepository;


    @Value("${system.category.status.KHOITAO:KHOITAO}")
    public String KHOITAO;
    @Value("${system.category.status.DANGSOAN:DANGSOAN}")
    public String DANGSOAN;
    @Value("${system.category.status.CHOXULY:CHOXULY}")
    public String CHOXULY;
    @Value("${system.category.status.DAPHEDUYET:DAPHEDUYET}")
    public String DAPHEDUYET;
    @Value("${system.category.status.DAHOANTHANH:DAHOANTHANH}")
    public String DAHOANTHANH;
    @Value("${system.category.status.TUCHOI:TUCHOI}")
    public String TUCHOI;
    @Value("${system.category.status.TRALAI:TRALAI}")
    public String TRALAI;

    @Value("${system.category.status.HUYPHEDUYET:HUYPHEDUYET}")
    public String HUYPHEDUYET;

    @Value("${system.category.status.DONGY:DONGY}")
    public String DONGY;

    @Value("${system.mailtemplate.reject-id:1}")
    private Long REJECT_MAILTEMPLATE_ID;

    @Value("${system.mailtemplate.da-phe-duyet-code:DAPHEDUYET}")
    private String MAIL_DA_PHE_DUYET_CODE;

    @Value("${system.mailtemplate.nguoi-tao-phieu-thu-hoi:NGUOITAOPHIEUTHUHOI}")
    private String MAIL_NGUOI_TAO_PHIEU_THU_HOI_CODE;

    @Value("${system.mailtemplate.thu-hoi:THUHOI}")
    private String MAIL_THU_HOI_CODE;

    @Value("${system.mailtemplate.tra-lai:TRALAI}")
    private String MAIL_TRA_LAI_CODE;

    public ProcessRequestCustomService() {
    }

    private final Map<ApproveOption.Action, Function<ApproveOption, Boolean>> handlerAction = new HashMap<>();
    private void loadHandlerAction(){
        //if(this.ALL_STATUS == null || this.ALL_STATUS.isEmpty())
            this.getAllStatus();
        this.handlerAction.put(ApproveOption.Action.Agree, this.functionAgree());
        this.handlerAction.put(ApproveOption.Action.Send, this.functionSend());
        this.handlerAction.put(ApproveOption.Action.Reject, this.functionReject());
        this.handlerAction.put(ApproveOption.Action.Refuse, this.functionRefuse());
        this.handlerAction.put(ApproveOption.Action.Approve, this.functionApprove());
        this.handlerAction.put(ApproveOption.Action.CancelApprove, this.functionCancelApprove());
        this.handlerAction.put(ApproveOption.Action.Recall, this.functionRecall());
        this.handlerAction.put(ApproveOption.Action.CreaterReall, this.functionCreaterRecall());
    }

    private final Map<CustomerApproveOption.Action, Function<CustomerApproveOption, Boolean>> handlerActionCustomer = new HashMap<>();
    private void loadHandlerActionCustomer(){
        this.getAllStatus();
        this.handlerActionCustomer.put(CustomerApproveOption.Action.Agree, this.functionAgreeCustomer());
        this.handlerActionCustomer.put(CustomerApproveOption.Action.Reject, this.functionRejectCustomer());
    }

    /**
     *
     */
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
    private String bindingDataToMailContent(String content, RequestData requestData, UserInfo requester, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList){
        String link_phieu = LINK.replace("{{id}}", String.valueOf(requestData.getId()));
        String link = "<a href='{{REQUESTDATA_LINK}}' rel='noopener noreferrer nofollow'>Phiếu yêu cầu:&nbsp;" + requestData.getRequestDataCode() + "</a>";
        link = link.replace("{{REQUESTDATA_LINK}}", link_phieu);
        content = content.replace(MA_PHIEU, requestData.getRequestDataCode());
        content = content.replace(TEN_PHIEU, requestData.getTitle());
        content = content.replace(LOAI_PHIEU_YEU_CAU_TEN, requestData.getRequestType().getRequestTypeName());

                        // đổi thông tin của người trình (từ người tạo phiếu -> người thực hiện phê duyệt ở bước hiện tại) \\
//        content = content.replace(NGUOI_TRINH_TEN, requestData.getCreatedName());
//        content = content.replace(TEN_DON_VI_TRINH, requestData.getCreatedOrgName());
        content = content.replace(NGUOI_TRINH_TEN, requester.getFullName());
        try{
            CharSequence[] all = Stream.concat(signDataList.stream().map(e->e.getSignName()), manageStampInfoList.stream().map(e->e.getName())).toArray(CharSequence[]::new);
            content = content.replace(BRAND_LIST, String.join("</br>", all));
        }catch (Exception e){
            content = content.replace(BRAND_LIST, "");
            log.error("FEATURE_FILL_SIGN_DATA: {}", e);
        }
        try {
            content = content.replace(TEN_DON_VI_TRINH, (requester.getOrganizations() == null || requester.getOrganizations().isEmpty()) ? "" : requester.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")));
        }catch (Exception e){log.debug("{}", e);}
                        // end đổi thông tin người trình \\

        content = content.replace(LINK_PHIEU, link);

                        // binding data trong form
        content = bindingDataFormToMailContent(content, requestData);

        return content;
    }

    private String bindingDataToMailTitle(String title, RequestData requestData){
        title = title.replace(TIEU_DE, requestData.getTitle());
        return title;
    }

    private List<String> LIST_NO_BINDING = Arrays.asList("truong_file", "truong_bang");       // danh sách kiểu các trường dữ liệu ko cần bindding: đang là trường bảng và trường file
    private String bindingDataFormToMailContent(String mailContent, RequestData requestData){
        try {
            List<FieldData> listFieldData = this.fieldDataRepository.findAllByRequestDataId(requestData.getId());
            if(listFieldData != null && !listFieldData.isEmpty()){
                for(FieldData fieldData : listFieldData){
                    try {
                        if(!Strings.isNullOrEmpty(fieldData.getFieldDataCode()) && fieldData.getObjectModel() != null && !LIST_NO_BINDING.stream().anyMatch(ele -> ele.equals(fieldData.getTennantCode()))){
                            String olderChar = "[" + fieldData.getFieldDataCode() + "]";
                            mailContent = mailContent.replace(olderChar, fieldData.getObjectModel());
                        }
                    }catch (Exception ex){
                        log.error("{}", ex);
                    }
                }
            }
            mailContent = mailContent.replaceAll("\\[[A-Za-z0-9_]+\\]", "");
        }catch (Exception e){
            log.error("{}", e);
        }
        return mailContent;
    }




    private List<Status> ALL_STATUS = new ArrayList<>();
    @Scheduled(cron = "${cron.tab:0 15 03 * * ?}")
    public void getAllStatus(){
        this.ALL_STATUS = this.statusRepository.findAll().stream().filter(ele -> {        // lấy tất cả trạng thái được sử dụng và không bị xóa
            return this.conditionUtils.checkTrueFalse(ele.getIsActive()) && !this.conditionUtils.checkTrueFalse(ele.getIsDelete());
        }).collect(Collectors.toList());
    }

    /**
     * hàm thực hiện gửi gửi mail theo template
     * @param requestDataId : id của phiếu cần gửi mail
     * @param templateName  : tên biểu mẫu mail
     * @param subject       : tiêu đề mail
     * @param userTo        : thông tin danh sách ngưiò nhận mail
     * @param userFrom      : thông tin người gửi mail
     * @param userCC        : thông tin danh sách người cc
     * @param userBCC       : thông tin danh sách ngừoi bcc
     * @param props         : thông tin dữ liệu binding vào biểu mẫu
     */
    private void autoSendMail(Long requestDataId, String templateName, String subject, List<UserInfo> userTo, UserInfo userFrom, List<UserInfo> userCC, List<UserInfo> userBCC, Map<String, Object> props){
        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(templateName);
        mailInfoDTO.setSubject(subject);
        mailInfoDTO.setProps(props);
        if(userTo != null) mailInfoDTO.setEmailAddressTo(userTo.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(userFrom != null) mailInfoDTO.setEmailAddressFrom(userFrom.getEmail());
        if(userBCC != null) mailInfoDTO.setEmailAddressBCC(userBCC.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(userCC != null) mailInfoDTO.setEmailAddressCC(userCC.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        try {
            this.mailCustomService.sendMailWithTemplate(mailInfoDTO, requestDataId);
        } catch (Exception e) {
            log.error("Send mail fail: {}", e);
        }
    }

    /**
     *
     */
    private void autoSendMailToCustomer_bak(Long requestDataId, MailTemplate mailTemplate,List<UserInfo> userTo, UserInfo userFrom, List<UserInfo> userCC, List<UserInfo> userBCC, Map<String, Object> props, UserInfo requester){
        // binding data
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        //mailTemplate.setContentFile(bindingDataToMailContent(mailTemplate.getContentFile(), requestData));
        //mailTemplate.setSubject(bindingDataToMailTitle(mailTemplate.getSubject(), requestData));

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(mailTemplate.getMailTemplateName());
//        mailInfoDTO.setContent(mailTemplate.getContentFile());
//        mailInfoDTO.setSubject(mailTemplate.getSubject());
        List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestDataId);
        List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestDataId);
        mailInfoDTO.setContent(bindingDataToMailContent(mailTemplate.getContentFile(), requestData, requester, signDataList, manageStampInfoList));
        mailInfoDTO.setSubject(bindingDataToMailTitle(mailTemplate.getSubject(), requestData));
        mailInfoDTO.setProps(props);
        if(userTo != null) mailInfoDTO.setEmailAddressTo(userTo.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(userCC != null) mailInfoDTO.setEmailAddressCC(userCC.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(mailTemplate.getReceiverDefault() != null){
            try {
                JSONArray userToDefaults = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getReceiverDefault()) ? "[]" : mailTemplate.getReceiverDefault());
                for(int i=0; i<userToDefaults.length(); i++){
                    Object userToDefault = userToDefaults.get(i);
                    if(userToDefault instanceof  String) {
                        mailInfoDTO.getEmailAddressTo().add((userToDefault.toString()));
                    }else {
                        String email = userToDefaults.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressTo().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }
        if(mailInfoDTO.getEmailAddressCC() != null){
            try {
                JSONArray userCCDefauls = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getCcerDefault()) ? "[]" : mailTemplate.getCcerDefault());
                for(int i=0; i<userCCDefauls.length(); i++){
                    Object userCCDefaul = userCCDefauls.get(i);
                    if(userCCDefaul instanceof String){
                        mailInfoDTO.getEmailAddressCC().add(userCCDefaul.toString());
                    }else{
                        String email = userCCDefauls.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressCC().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }


        List<String> emailAddressTo = mailInfoDTO.getEmailAddressTo() == null ? new ArrayList<>() : mailInfoDTO.getEmailAddressTo();
        try {
                        // DuowngTora: thay đổi thông tin ký số sẽ là nhiều thay vì 1 \\
//            SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
//            String emailCustomerSignData = signData.getEmail();
//            if(!Strings.isNullOrEmpty(emailCustomerSignData)) emailAddressTo.add(emailCustomerSignData);
            List<String> emailCustomerSignData = this.signDataRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> ele.getEmail()).collect(Collectors.toList());
            if(emailCustomerSignData != null && !emailCustomerSignData.isEmpty())  emailAddressTo.addAll(emailCustomerSignData);
        }catch (Exception e){log.error("{}", e);}
        try {
                        // DuowngTora: thay đổi thông tin quản lý đóng dấu là nhiều thay vì 1 \\
//            ManageStampInfo manageStampInfo = this.manageStampInfoRepository.findAllByRequestDataId(requestDataId).get(0);
//            String emailCustomerManageStampInfo = manageStampInfo.getEmail();
//            if(!Strings.isNullOrEmpty(emailCustomerManageStampInfo)) emailAddressTo.add(emailCustomerManageStampInfo);
            List<String> emailCustomerManageStampInfo = this.manageStampInfoRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> ele.getEmail()).collect(Collectors.toList());
            if(emailCustomerManageStampInfo != null && !emailCustomerManageStampInfo.isEmpty()) emailAddressTo.addAll(emailCustomerManageStampInfo);
        }catch (Exception e){log.error("{}", e);}
//        mailInfoDTO.setEmailAddressTo(Arrays.asList(emailCustomer));

        mailInfoDTO.setEmailAddressTo(emailAddressTo);
        mailInfoDTO.setRequestDataId(requestDataId);
        mailInfoDTO.setAddOTP(true);
        mailInfoDTO.setAddFile(false);

        try {
            this.mailCustomService.sendMailWithTemplate365ToCustomer(mailInfoDTO, requestDataId);
        } catch (Exception e) {
            log.error("Send mail to customer fail: {}", e);
        }
    }

    private void autoSendMailToCustomer(Long requestDataId, MailTemplate mailTemplate,List<UserInfo> userTo, UserInfo userFrom, List<UserInfo> userCC, List<UserInfo> userBCC, Map<String, Object> props, UserInfo requester){
        // binding data
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(mailTemplate.getMailTemplateName());
        List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestDataId);
        List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestDataId);
        mailInfoDTO.setContent(bindingDataToMailContent(mailTemplate.getContentFile(), requestData, requester, signDataList, manageStampInfoList));
        String mailContent = mailInfoDTO.getContent();              // lại thông tin mail để tránh thêm mã otp liên tục
        mailInfoDTO.setSubject(bindingDataToMailTitle(mailTemplate.getSubject(), requestData));
        mailInfoDTO.setProps(props);
        if(userTo != null) mailInfoDTO.setEmailAddressTo(userTo.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(userCC != null) mailInfoDTO.setEmailAddressCC(userCC.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(mailTemplate.getReceiverDefault() != null){
            try {
                JSONArray userToDefaults = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getReceiverDefault()) ? "[]" : mailTemplate.getReceiverDefault());
                for(int i=0; i<userToDefaults.length(); i++){
                    Object userToDefault = userToDefaults.get(i);
                    if(userToDefault instanceof  String) {
                        mailInfoDTO.getEmailAddressTo().add((userToDefault.toString()));
                    }else {
                        String email = userToDefaults.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressTo().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }
        if(mailInfoDTO.getEmailAddressCC() != null){
            try {
                JSONArray userCCDefauls = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getCcerDefault()) ? "[]" : mailTemplate.getCcerDefault());
                for(int i=0; i<userCCDefauls.length(); i++){
                    Object userCCDefaul = userCCDefauls.get(i);
                    if(userCCDefaul instanceof String){
                        mailInfoDTO.getEmailAddressCC().add(userCCDefaul.toString());
                    }else{
                        String email = userCCDefauls.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressCC().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }

        // gửi mail nội bộ và mail cho người nhận default
        if(mailInfoDTO.getEmailAddressTo() != null && !mailInfoDTO.getEmailAddressTo().isEmpty()){
            mailInfoDTO.setRequestDataId(requestDataId);
            mailInfoDTO.setAddOTP(true);
            mailInfoDTO.setAddFile(false);
            try {
                this.mailCustomService.sendMailWithTemplate365ToCustomer(mailInfoDTO, requestDataId);
            } catch (Exception e) {
                log.error("Send mail to customer fail: {}", e);
            }
        }

                // gửi mail cho từng khách hàng (signData)
        try {
            if(signDataList != null && !signDataList.isEmpty()){
                signDataList.forEach(ele -> {
                    try {
                        MailInfoDTO mailInfoDTOOneCustomer = new MailInfoDTO();
                        mailInfoDTOOneCustomer.setContent(mailContent);
                        mailInfoDTOOneCustomer.setSubject(mailInfoDTO.getSubject());
                        mailInfoDTOOneCustomer.setProps(props);
                        mailInfoDTOOneCustomer.setTemplateName(mailTemplate.getMailTemplateName());
                        mailInfoDTOOneCustomer.setEmailAddressCC(mailInfoDTO.getEmailAddressCC());
                        List<String> emailAddressTo = new ArrayList<>();
                        emailAddressTo.add(ele.getEmail());
                        mailInfoDTOOneCustomer.setEmailAddressTo(emailAddressTo);
                        mailInfoDTOOneCustomer.setAddFile(false);
                        mailInfoDTOOneCustomer.setAddOTP(true);
                        mailInfoDTOOneCustomer.setRequestDataId(requestDataId);
                        this.mailCustomService.sendMailWithTemplate365ToOneCustomer(mailInfoDTOOneCustomer, requestDataId, ele);
                    }catch (Exception ex){
                        log.error("{}", ex);
                    }
                });
            }
        }catch (Exception e){
            log.error("{}", e);
        }

            // gửi mail cho từng khách hàng (manageStampInfo)
        try {
            if(manageStampInfoList != null && !manageStampInfoList.isEmpty()){
                manageStampInfoList.forEach(ele -> {
                    try {
                        MailInfoDTO mailInfoDTOOneCustomer = new MailInfoDTO();
                        mailInfoDTOOneCustomer.setContent(mailContent);
                        mailInfoDTOOneCustomer.setSubject(mailInfoDTO.getSubject());
                        mailInfoDTOOneCustomer.setProps(props);
                        mailInfoDTOOneCustomer.setTemplateName(mailTemplate.getMailTemplateName());
                        mailInfoDTOOneCustomer.setEmailAddressCC(mailInfoDTO.getEmailAddressCC());
                        List<String> emailAddressTo = new ArrayList<>();
                        emailAddressTo.add(ele.getEmail());
                        mailInfoDTOOneCustomer.setEmailAddressTo(emailAddressTo);
                        mailInfoDTOOneCustomer.setAddFile(false);
                        mailInfoDTOOneCustomer.setAddOTP(true);
                        this.mailCustomService.sendMailWithTemplate365ToCustomer(mailInfoDTOOneCustomer, requestDataId);
                    }catch (Exception ex){
                        log.error("{}", ex);
                    }
                });
            }
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    public void autoSendMail(Long requestDataId, MailTemplate mailTemplate,List<UserInfo> userTo, UserInfo userFrom, List<UserInfo> userCC, List<UserInfo> userBCC, Map<String, Object> props, UserInfo requester){
            // binding data
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        //mailTemplate.setContentFile(bindingDataToMailContent(mailTemplate.getContentFile(), requestData));
        //mailTemplate.setSubject(bindingDataToMailTitle(mailTemplate.getSubject(), requestData));

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTemplateName(mailTemplate.getMailTemplateName());
//        mailInfoDTO.setContent(mailTemplate.getContentFile());
//        mailInfoDTO.setSubject(mailTemplate.getSubject());
        List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestDataId);
        List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestDataId);
        mailInfoDTO.setContent(bindingDataToMailContent(mailTemplate.getContentFile(), requestData, requester, signDataList, manageStampInfoList));
        mailInfoDTO.setSubject(bindingDataToMailTitle(mailTemplate.getSubject(), requestData));
        mailInfoDTO.setProps(props);
        if(userTo != null) mailInfoDTO.setEmailAddressTo(userTo.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(userCC != null) mailInfoDTO.setEmailAddressCC(userCC.stream().map(ele -> ele.getEmail()).collect(Collectors.toList()));
        if(mailTemplate.getReceiverDefault() != null){
            try {
                JSONArray userToDefaults = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getReceiverDefault()) ? "[]" : mailTemplate.getReceiverDefault());
                for(int i=0; i<userToDefaults.length(); i++){
                    Object userToDefault = userToDefaults.get(i);
                    if(userToDefault instanceof  String) {
                        mailInfoDTO.getEmailAddressTo().add((userToDefault.toString()));
                    }else {
                        String email = userToDefaults.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressTo().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }
        if(mailInfoDTO.getEmailAddressCC() != null){
            try {
                JSONArray userCCDefauls = new JSONArray(Strings.isNullOrEmpty(mailTemplate.getCcerDefault()) ? "[]" : mailTemplate.getCcerDefault());
                for(int i=0; i<userCCDefauls.length(); i++){
                    Object userCCDefaul = userCCDefauls.get(i);
                    if(userCCDefaul instanceof String){
                        mailInfoDTO.getEmailAddressCC().add(userCCDefaul.toString());
                    }else{
                        String email = userCCDefauls.getJSONObject(i).getString("email");
                        mailInfoDTO.getEmailAddressCC().add(email);
                    }
                }
            }catch (Exception e){log.error("{}", e);}
        }
        try {
            this.mailCustomService.sendMailWithTemplate365(mailInfoDTO, requestDataId);
        } catch (Exception e) {
            log.error("Send mail fail: {}", e);
        }
    }

    /**
     * hàm thực hiện lấy dữ liệu trong đối tượng tương ứng với thông tin mapping
     * @param object        : đối tượng cần lấy dữ liệu
     * @param mappingInfo   : thông tin mapping
     * @return              : trả về dạng MAP<String, Object>
     */
    private Map<String, Object> getDataFromObject(Object object, String mappingInfo){
        Map<String, Object> result = new HashMap<>();
        if(mappingInfo == null) return result;
        try {
            JSONObject jsonObject = new JSONObject(mappingInfo);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                try{
                    result.put(key, getFieldFromObject(object, jsonObject.getString(key)));
                }catch (NoSuchFieldException | IllegalAccessException e){ log.error("{}", e); }
            }
        }catch(Exception e){
            return result;
        }
        return result;
    }

    private Object getFieldFromObject(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        String[] strings = fieldName.split("\\.");
        int n = strings.length;
        for(int i=1; i<n-1; i++){
            Class<?> clazz = object.getClass();
            java.lang.reflect.Field field = clazz.getDeclaredField(strings[i]);
            field.setAccessible(true);
            object = field.get(object);
        }
        Class<?> finalClazz = object.getClass();
        Field finalField = finalClazz.getDeclaredField(strings[n-1]);
        Object finalObject = finalField.get(object);
        return finalObject;
    }
//
//    public static void main(String[] args) {
//        RequestDataCustomService.getDataFromObject(new RequestData(), "{\n" +
//            "\t\"rfa_code\" : \"requestData.requestDataCode\",\n" +
//            "\t\"request_type_name\": \"requestData.requestTypeName\",\n" +
//            "\t\"rfa_name\": \"requestData.requestDataName\",\n" +
//            "\t\"requester_name\": \"requestData.createdName\",\n" +
//            "\t\"requester_department\": \"requestData.createdOrgName\"\n" +
//            "}" +
//            "{" +
//            "}");
//    }

    /**
     * Hàm thực hiện lấy dữ liệu trong Object
     * @param jsonObject
     * @param field
     * @return
     */
    private String getFieldFromObject(JSONObject jsonObject, String field){
        JSONObject jsonObjectTemp = jsonObject;
        String[] listField = field.split("\\.");
        int n = listField.length;
        for(int i=1; i<n-1; i++){
            jsonObjectTemp = jsonObjectTemp.getJSONObject(listField[i]);
        }
        return String.valueOf(jsonObjectTemp.get(listField[n-1]));
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionAgree(){        // hàm xử lý chức năng đồng ý phiếu
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if (requestData == null)
                return false;                                                                       // th không có requestData tương ứng -> lỗi

            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();

            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if (stepDataList == null || stepDataList.isEmpty())
                return false;                                                                       // th không có bước thực hiện nào -> lỗi
            StepData currentStepData = stepDataList.stream().filter(ele -> this.conditionUtils.checkTrueFalse(ele.getIsActive())).findFirst().orElse(null);
            if (currentStepData == null)
                return false;                                                                       // Th không có bước nào đang active -> lỗi

            List<UserInfo> listUserCanPermission = currentStepData.getUserInfos().stream().collect(Collectors.toList());        // lấy danh sách những người được phép xử lý phiếu
            if(!this.checkPermisionProcess(processer, listUserCanPermission))                        // TH không có quyền xử lý phiếu (không có trong bước hiện tại)
                throw new RuntimeException(this.NOT_PERMISSION_ERROR);

            if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.Agree)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                this.reqdataProcessHisRepository.save(reqdataProcessHis);
                return true;
            }

            long nextStepOrder = currentStepData.getStepOrder() + 1;
            StepData nextStepData = stepDataList.stream().filter(ele -> ele.getStepOrder() == nextStepOrder).findFirst().orElse(null);
            if (nextStepData == null)
                return false;                                                                      // th không có bước tiếp theo -> lỗi

            currentStepData.setIsActive(false);
            this.stepDataRepository.save(currentStepData);

            nextStepData.setIsActive(true);
            nextStepData.setTimeActive(Instant.now());
            if (nextStepData.getProcessingTerm() != null && nextStepData.getProcessingTerm() > 0L) {
                Double secondPlus = (nextStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                nextStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
            } else {
                nextStepData.setProcessingTermTime(null);
            }
            this.stepDataRepository.save(nextStepData);

            Status STATUS_CHOXULY = null;
            if (approveOption.getStatusId() != null) {            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                STATUS_CHOXULY = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_CHOXULY);
                requestData.setStatusName(STATUS_CHOXULY.getStatusName());
            } else {                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (đồng ý thì sẽ chuyển về trạng thái 'Đồng ý')
                STATUS_CHOXULY = this.ALL_STATUS.stream().filter(ele -> this.CHOXULY.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_CHOXULY);
                requestData.setStatusName(STATUS_CHOXULY.getStatusName());
            }

            requestData.setExpiredTime(nextStepData.getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // Thực hiện gửi mail (gửi mail theo mẫu 'can-xu-ly' đến những cán bộ ở bước tiếp theo)
            MailTemplate mailTemplate = nextStepData.getMailTemplate();
            if (mailTemplate != null) {
                Map<String, Object> props_CHOXULY = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, nextStepData.getUserInfos().stream().collect(Collectors.toList()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_CHOXULY, processer);
            }
            // gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = nextStepData.getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }

                    // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            return true;
        };
    }

    @Transactional
    public Function<ApproveOption, Boolean> functionSend(){         // xử lý chức năng gửi phiếu
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người thực hiện xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;                                                                       // Th không có requestData tương ứng

            List<UserInfo> listUserCanPermision = new ArrayList<>();                            // kiểm tra user có được xử lý phiếu không (dó là bước gửi -> là người tạo phiếu mới được gửi)
            listUserCanPermision.add(requestData.getCreated());
            if(!this.checkPermisionProcess(processer, listUserCanPermision))
                throw new RuntimeException(this.NOT_PERMISSION_ERROR);

            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào ko
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;                                            // kiểm tra xem có bước nào trong quy trình ko
            stepDataList.sort((st1, st2) -> (int)(st1.getStepOrder() - st2.getStepOrder()));                            // cần kiểm tra xem đã sort đúng chưa
            StepData nextStepData = stepDataList.get(0);
            nextStepData.setIsActive(true);
            nextStepData.setTimeActive(Instant.now());
            if(nextStepData.getProcessingTerm() != null && nextStepData.getProcessingTerm() > 0L){
                Double secondPlus = (nextStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                nextStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
            }else{
                nextStepData.setProcessingTermTime(null);
            }
            if(stepDataList == null && stepDataList.isEmpty()) return false;

            Status STATUS_CHOXULY = null;
            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                STATUS_CHOXULY = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_CHOXULY);
                requestData.setStatusName(STATUS_CHOXULY.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                STATUS_CHOXULY = this.ALL_STATUS.stream().filter(ele -> this.CHOXULY.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_CHOXULY);
                requestData.setStatusName(STATUS_CHOXULY.getStatusName());
            }

            requestData.setExpiredTime(nextStepData.getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // Thực hiện gửi mail (gửi mail theo mẫu 'can-xu-ly' đến những cán bộ ở bước tiếp theo)
            MailTemplate mailTemplate = nextStepData.getMailTemplate();
            if(mailTemplate != null){
                String TEMPLATE_NAME = mailTemplate.getMailTemplateName();
                String SUBJECT = mailTemplate.getSubject();
                Map<String, Object> props_CHOXULY = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                //this.autoSendMail(requestDataId, TEMPLATE_NAME, SUBJECT, nextStepData.getUserInfos().stream().collect(Collectors.toList()), null, Arrays.asList(requestData.getCreated()), null, props_CHOXULY);
                this.autoSendMail(requestDataId, mailTemplate, nextStepData.getUserInfos().stream().collect(Collectors.toList()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_CHOXULY, processer);
            }
            // gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = nextStepData.getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            return true;
        };
    }

    @Transactional
    public Function<ApproveOption, Boolean> functionReject(){                   // xử lý chức năng trả lại phiếu
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý phiếu

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào ko
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;
            //List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2) -> { return (int) (ele1.getStepOrder() - ele2.getStepOrder());}).collect(Collectors.toList());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);

            if(currentStepData != null){                                              // kiểm tra user có quyền xử lý phiếu không (có trong bược hiện tại không)
                List<UserInfo> listUserCanPermission = currentStepData.getUserInfos().stream().collect(Collectors.toList());
                if(!this.checkPermisionProcess(processer, listUserCanPermission))
                    throw new RuntimeException(this.NOT_PERMISSION_ERROR);

                if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.Reject)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                    //String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                    //String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                    //ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                    //this.reqdataProcessHisRepository.save(reqdataProcessHis);
                        // Từ chối, Trả lại, thu hồi, người tạo thu hồi -> không ghi lịch sử ở đây vì chỉ có 1 người xử lý thì sẽ chuyển bước
                    return true;
                }
            }

            Long nextRoundNumber = requestData.getCurrentRound() + 1;

//            ProcessData processData = this.processDataRepository.findAllByRequestDataId(requestDataId).get(0);
//            processData.setId(null);
//            processData.setRoundNumber(nextRoundNumber);
//            this.processDataRepository.save(processData);
            ProcessData processDataNew = new ProcessData();
            BeanUtils.copyProperties(currentProcessData, processDataNew, "stepData");
            processDataNew.setId(null);
            processDataNew.setIsActive(true);
            processDataNew.setRoundNumber(nextRoundNumber);
            processDataNew.setCreatedDate(Instant.now());
            processDataNew.setModifiedDate(Instant.now());
            processDataNew.setStepData(new HashSet<>());
            processDataNew = this.processDataRepository.save(processDataNew);

            currentProcessData.setIsActive(false);
            this.processDataRepository.save(currentProcessData);

            for(StepData stepData : stepDataList){
                StepData stepDataNew = new StepData();
                BeanUtils.copyProperties(stepData, stepDataNew);
                stepDataNew.setId(null);
                stepDataNew.setRoundNumber(nextRoundNumber);
                stepDataNew.setIsActive(false);
                stepDataNew.setTimeActive(null);
                stepDataNew.setProcessingTermTime(null);
                stepDataNew.setCreatedDate(Instant.now());
                stepDataNew.setModifiedDate(Instant.now());
                stepDataNew.setProcessData(processDataNew);
                stepDataNew.setAttachmentInSteps(new HashSet<>());
                stepDataNew.setReqdataProcessHis(new HashSet<>());
                stepDataNew.setRequestRecalls(new HashSet<>());
                stepDataNew.setUserInfos(new HashSet<>(stepData.getUserInfos().stream().map(ele -> {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(ele.getId());
                    return userInfo;
                }).collect(Collectors.toSet())));
                stepDataNew.setConsults(new HashSet<>());
                stepDataNew.setExamines(new HashSet<>());
                stepDataNew.setTransferHandles(new HashSet<>());
                stepDataNew.setResultOfSteps(new HashSet<>());
                stepDataNew = this.stepDataRepository.save(stepDataNew);
            }

            currentStepData.setIsActive(false);
            this.stepDataRepository.save(currentStepData);

            requestData.setCurrentRound(nextRoundNumber);

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_TRALAI = this.ALL_STATUS.stream().filter(ele -> this.TRALAI.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_TRALAI);
                requestData.setStatusName(STATUS_TRALAI.getStatusName());
            }
            //Optional<MailTemplate> mailTemplate = this.mailTempmailTemplateRepositoryate.findById(1L);        // template mặc định của trả lại phiếu
            MailTemplate mailTemplate = this.mailTempmailTemplateRepositoryate.findAllByMailTemplateCode(this.MAIL_TRA_LAI_CODE).get(0);
            if(mailTemplate != null){
                Map<String, Object> props_TRALAI = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, Arrays.asList(requestData.getCreated()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_TRALAI, processer);
            }

            requestData.setExpiredTime(null);
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            // Khi trả lại thành công -> revert lại các file tài liệu chính đã ẩn đi khi ký thành công
            this.signUtils.revertFilePrimary(requestDataId);

            // trả lại -> reset lại thông tin trong signData + không xóa OTP
            this.resetDataCustomer(requestDataId);

            return true;
        };
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionRefuse(){              // xử lý chức năng từ chối
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findAny().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;                                            // kiểm tra xem có bước nào trong quy trình không
//            List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2)  -> { return (int) (ele1.getStepOrder() - ele2.getStepOrder());}).collect(Collectors.toList());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);

            if(currentStepData != null){                                                // kiểm tra user có quyền xử lý phiếu không (có trong bược hiện tại không)
                List<UserInfo> listUserCanPermission = currentStepData.getUserInfos().stream().collect(Collectors.toList());
                if(!this.checkPermisionProcess(processer, listUserCanPermission))
                    throw new RuntimeException(this.NOT_PERMISSION_ERROR);

                if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.Refuse)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                    //String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                    //String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                    //ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                    //this.reqdataProcessHisRepository.save(reqdataProcessHis);
                    // Từ chối, Trả lại, thu hồi, người tạo thu hồi -> không ghi lịch sử ở đây vì chỉ có 1 người xử lý thì sẽ chuyển bước
                    return true;
                }
            }

            StepData nextStepData = null;
            if(currentStepData.getOptionDeny().equals(-1L)) {      // nếu = -1 -> về bước đầu
                nextStepData = stepDataList.get(0);
            }else if(currentStepData.getOptionDeny().equals(-2L)){     // nếu = -2 -> đến bước tiếp theo
                nextStepData = stepDataList.stream().filter(ele -> currentStepData.getStepOrder() + 1 == ele.getStepOrder()).findFirst().orElse(null);
            }else{
                nextStepData = stepDataList.stream().filter(ele -> currentStepData.getOptionDeny() == ele.getStepOrder()).findFirst().orElse(null);
            }
            if(nextStepData == null) return false;
            currentStepData.setIsActive(false);
//            currentStepData.setTimeActive(null);
//            currentStepData.setProcessingTermTime(null);
            this.stepDataRepository.save(currentStepData);

            nextStepData.setIsActive(true);
            nextStepData.setTimeActive(Instant.now());
            if (nextStepData.getProcessingTerm() != null && nextStepData.getProcessingTerm() > 0L) {
                Double secondPlus = (nextStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                nextStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
            } else {
                nextStepData.setProcessingTermTime(null);
            }
            this.stepDataRepository.save(nextStepData);

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_TUCHOI = this.ALL_STATUS.stream().filter(ele -> this.TUCHOI.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_TUCHOI);
                requestData.setStatusName(STATUS_TUCHOI.getStatusName());
            }
            MailTemplate mailTemplate = nextStepData.getMailTemplate();        // template mặc định của trả lại phiếu
            if(mailTemplate != null){
                Map<String, Object> props_TRALAI = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, Arrays.asList(requestData.getCreated()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_TRALAI, processer);
            }
            // gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = nextStepData.getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }


            requestData.setExpiredTime(nextStepData.getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            return true;
        };
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionApprove(){
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;                                            // kiểm tra xem có bước nào không
//            List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2) -> { return (int) (ele1.getStepOrder() - ele2.getStepOrder());}).collect(Collectors.toList());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);

            if(currentStepData != null){                                            // kiểm tra user có quyền xử lý phiếu không (có trong bược hiện tại không)
                List<UserInfo> listUserCanPermission = currentStepData.getUserInfos().stream().collect(Collectors.toList());
                if(!this.checkPermisionProcess(processer, listUserCanPermission))
                    throw new RuntimeException(this.NOT_PERMISSION_ERROR);

                if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.Approve)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                    String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                    String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                    ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                    this.reqdataProcessHisRepository.save(reqdataProcessHis);
                    return true;
                }
            }

            StepData nextStepData = stepDataList.stream().filter(ele -> ele.getStepOrder() == currentStepData.getStepOrder() + 1).findFirst().orElse(null);
            if(nextStepData != null){       //th != null -> đây là chưa là bước cuối -> vẫn phải chuyển bước tiếp theo . th = null -> đây là bước cuối rồi không cần chuyển bước tiếp theo
                currentStepData.setIsActive(false);
                this.stepDataRepository.save(currentStepData);
                nextStepData.setIsActive(true);
                nextStepData.setTimeActive(Instant.now());
                if (nextStepData.getProcessingTerm() != null && nextStepData.getProcessingTerm() > 0L) {
                    Double secondPlus = (nextStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                    nextStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
                } else {
                    nextStepData.setProcessingTermTime(null);
                }
                this.stepDataRepository.save(nextStepData);
            }else{                  // th == null -> đây là bước cuối cùng ko cần chuyển bước
                currentStepData.setIsActive(false);
                this.stepDataRepository.save(currentStepData);
                        // đánh dấu requestData đánh dấu là đã hoàn thành phiếu
                requestData.setIsDone(true);
                requestData.setTimeDone(Instant.now());
            }

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_DAPHEDUYET = this.ALL_STATUS.stream().filter(ele -> this.DAPHEDUYET.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_DAPHEDUYET);
                requestData.setStatusName(STATUS_DAPHEDUYET.getStatusName());
            }

            // Khi khi phệ ở bước cuối cùng -> sẽ lấy mail cứng DAPHEDUYET
            Optional<MailTemplate> mailTemplate = this.mailTempmailTemplateRepositoryate.findById(1L);        // template mặc định của trả lại phiếu
            if(nextStepData == null){   // nếu là bước cuối cùng rồi -> gửi mail kết thúc phiếu
                try {
                    MailTemplate mailTemplateDAPHEDUYET = this.mailTempmailTemplateRepositoryate.findAllByMailTemplateCode(this.MAIL_DA_PHE_DUYET_CODE).get(0);
                    Map<String, Object> props_DAPHEDUYET = this.getDataFromObject(requestData, mailTemplate.get().getMappingInfo());
                    List<UserInfo> allUserOfRequestData = new ArrayList<>();
                    stepDataList.forEach(ele -> {
                        allUserOfRequestData.addAll(ele.getUserInfos());
                    });
                    this.autoSendMail(requestDataId, mailTemplateDAPHEDUYET, Arrays.asList(requestData.getCreated()), null, Arrays.asList(requestData.getCreated()), allUserOfRequestData, props_DAPHEDUYET, processer);

                    // gửi mail cho khách hàng
                    // this.autoSendMailToCustomer(requestDataId, mailTemplateDAPHEDUYET, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);

                }catch (Exception e){
                    log.error("{}", e);
                }
            }else{
                if(nextStepData.getMailTemplate() != null){
                    Map<String, Object> props_TRALAI = this.getDataFromObject(requestData, mailTemplate.get().getMappingInfo());
                    this.autoSendMail(requestDataId, mailTemplate.get(), Arrays.asList(requestData.getCreated()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_TRALAI, processer);
                }
                // gửi mail cho khách hàng
                MailTemplate mailTemplateCustomer = nextStepData.getMailTemplateCustomer();
                if(mailTemplateCustomer != null){
                    this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
                }
            }

            // phê duyệt thành công
            Optional<UserInfo> approver = this.userInfoRepository.findById(approveOption.getUserId());
            requestData.setIsApprove(true);
            if(approver.isPresent()){
                requestData.setApprover(approver.get());
                requestData.setApproverName(approver.get().getFullName());
                requestData.setApproverRankName(approver.get().getRanks() != null ? approver.get().getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null);
                requestData.setApproverOrgName(approver.get().getOrganizations() != null ? approver.get().getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null);
            }

            requestData.setExpiredTime(nextStepData == null ? null : nextStepData.getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            if(nextStepData == null){       // th đã là bước cuối -> đồng bộ sang DataRoom
                try {
                    this.dataRoomCustomService.syncToDataRoom(requestData.getId());
                } catch (Exception e) {
                    throw new RuntimeException("Error when sync to DATA-ROOM");
                }
            }

            return true;
        };
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionCancelApprove(){                           // Xử lý chức năng hủy phê duyệt
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;                                            // kiểm tra xem có bước nào không
//            List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());

//            StepData currentStepData = stepDataList.get(0);                                                             // lấy bước cuối cùng
//            if(currentStepData.getIsActive() != true){          // kiểm tra xem có đúng là đang ở bước cuối cùng không (không phải -> lỗi)
//                return false;
//            }
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive().equals(true)).findFirst().orElse(null);        // kiểm tra xem có bước nào đang active ko -> nếu còn -> lỗi
            if(currentStepData != null){
                return false;
            }

            StepData finalStepData = stepDataList.get(0);
            List<UserInfo> listUserCanPermission = finalStepData.getUserInfos().stream().collect(Collectors.toList());        // kiểm tra user có quyền xử lý phiếu không (có trong bược hiện tại không)
            if(!this.checkPermisionProcess(processer, listUserCanPermission))
                throw new RuntimeException(this.NOT_PERMISSION_ERROR);

            if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.CancelApprove)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                //String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                //String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                //ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                //this.reqdataProcessHisRepository.save(reqdataProcessHis);
                // Từ chối, Trả lại, thu hồi, người tạo thu hồi -> không ghi lịch sử ở đây vì chỉ có 1 người xử lý thì sẽ chuyển bước
                return true;
            }

            // reset lại bước cuối cùng này
            finalStepData.setIsActive(true);
            finalStepData.setTimeActive(Instant.now());
            if (finalStepData.getProcessingTerm() != null && finalStepData.getProcessingTerm() > 0L) {
                Double secondPlus = (finalStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                finalStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
            } else {
                finalStepData.setProcessingTermTime(null);
            }
            this.stepDataRepository.save(finalStepData);

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_HUYPHEDUYET = this.ALL_STATUS.stream().filter(ele -> this.HUYPHEDUYET.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_HUYPHEDUYET);
                requestData.setStatusName(STATUS_HUYPHEDUYET.getStatusName());
            }

            requestData.setExpiredTime(finalStepData.getProcessingTermTime());
            requestData.setIsDone(false);
            requestData.setTimeDone(null);
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // Thực hiện gửi mail (gửi mail đến những cán bộ ở bước tiếp theo)
            MailTemplate mailTemplate = finalStepData.getMailTemplate();
            if (mailTemplate != null) {
                Map<String, Object> props_CHOXULY = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, finalStepData.getUserInfos().stream().collect(Collectors.toList()), null, Arrays.asList(requestData.getCreated()), null, props_CHOXULY, processer);
            }
            // Gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = finalStepData.getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }

            // hủy phê duyệt

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            return true;
        };
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionRecall(){                      // xử lý chức năng thu hồi
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();                     // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<RequestRecall> requestRecallList = this.requestRecallRepository.findAllByRequestDataId(requestDataId);
            requestRecallList.stream().sorted((ele1, ele2) -> { return (int) (ele2.getId() - ele1.getId());}).collect(Collectors.toList());
            RequestRecall requestRecall = requestRecallList.get(0);             // lấy yêu cầu thu hồi cuối cùng -> để lấy được bước yêu cầu thu hồi
            StepData stepDataRecall = requestRecall.getStepData();              // lấy ra bước yêu cầu thu hồi

            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(stepDataRecall.getProcessData().getId());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().get();
            stepDataList = stepDataList.stream().filter(ele -> ele.getStepOrder() >= stepDataRecall.getStepOrder()).collect(Collectors.toList());       // lấy các bước sau bước yêu cầu thu hồi

            List<UserInfo> listUserCanPermission = currentStepData.getUserInfos().stream().collect(Collectors.toList());
            if(!this.checkPermisionProcess(processer, listUserCanPermission))
                throw new RuntimeException(this.NOT_PERMISSION_ERROR);

            if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.Recall)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                //String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                //String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                //ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                //this.reqdataProcessHisRepository.save(reqdataProcessHis);
                // Từ chối, Trả lại, thu hồi, người tạo thu hồi -> không ghi lịch sử ở đây vì chỉ có 1 người xử lý thì sẽ chuyển bước
                return true;
            }

            AtomicReference<StepData> nextStepData = new AtomicReference<>();
            //  refest các bước
            stepDataList.forEach(ele -> {
                if(ele.getStepOrder().equals(stepDataRecall.getStepOrder())){
                    ele.setIsActive(true);
                    ele.setTimeActive(Instant.now());
                    if (ele.getProcessingTerm() != null && ele.getProcessingTerm() > 0L) {
                        Double secondPlus = (ele.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                        ele.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
                    } else {
                        ele.setProcessingTermTime(null);
                    }
                    nextStepData.set(ele);
                }else{
                    ele.setIsActive(false);
                    ele.setProcessingTermTime(null);
                }
                this.stepDataRepository.save(ele);
            });

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }       // ko có else do không cần đổi trạng thái của phiếu

            requestData.setExpiredTime(nextStepData.get().getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);


            // Thực hiện gửi mail (gửi mail đến những cán bộ ở bước tiếp theo)
            MailTemplate mailTemplate = nextStepData.get().getMailTemplate();
            if (mailTemplate != null) {
                Map<String, Object> props_CHOXULY = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, nextStepData.get().getUserInfos().stream().collect(Collectors.toList()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_CHOXULY, processer);
            }
            // Gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = nextStepData.get().getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }

            // khi thành công -> cập nhật lại thông tin thu hồi (requestRecall)
            requestRecall.setProcesser(processer);
            requestRecall.setProcessTime(Instant.now());
            this.requestRecallRepository.save(requestRecall);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
            parentHistory.setIsChild(true);         // Th thu hồi -> cho là action con
            this.reqdataProcessHisRepository.save(parentHistory);

            return true;
        };
    }

    @Transactional
    private Function<ApproveOption, Boolean> functionCreaterRecall(){                   // hàm xử lý yêu cầu thu hồi phiếu từ người tạo phiếu (thu hồi luôn không cần ai đồng ý cả)
        return (approveOption) -> {
            UserInfo processer = this.userInfoRepository.findById(approveOption.getUserId()).get();             // lấy thông tin người xử lý

            Long requestDataId = approveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;

            ProcessData currentProcessData = this.processDataRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().orElse(null);
            if(currentProcessData == null) return false;

            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;

            if(!requestData.getCreated().getId().equals(processer.getId())){                            // kiểm tra xem người yêu cầu có phải là người tạo phiếu không -> (nếu không -> lỗi)
                throw new RuntimeException(this.NOT_PERMISSION_ERROR);
            }

            // Th người tạo thu hồi -> truyền vào bước hiện tại vào làm tham số
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().get();
            if(!this.checkProcessTogether(processer, currentStepData, ApproveOption.Action.CreaterReall)){           // nếu chưa là người cuối -> chỉ ghi lịch sử
                //String processerRankName = processer.getRanks() == null ? "" : processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
                //String processerOrganiztionName = processer.getOrganizations() == null ? "" : processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
                //ReqdataProcessHis reqdataProcessHis = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
                //this.reqdataProcessHisRepository.save(reqdataProcessHis);
                // Từ chối, Trả lại, thu hồi, người tạo thu hồi, hủy phê duyệt -> không ghi lịch sử ở đây vì chỉ có 1 người xử lý thì sẽ chuyển bước
                return true;
            }

            stepDataList.forEach(ele -> {
                ele.setIsActive(false);
                ele.setProcessingTermTime(null);
                this.stepDataRepository.save(ele);
            });

            if(approveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(approveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Đang Soạn')
                Status STATUS_DANGSOAN = this.ALL_STATUS.stream().filter(ele -> this.DANGSOAN.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_DANGSOAN);
                requestData.setStatusName(STATUS_DANGSOAN.getStatusName());
            }

            requestData.setExpiredTime(null);
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // Thực hiện gửi mail (gửi mail đến những cán bộ ở bước tiếp theo)
            MailTemplate mailTemplate = this.mailTempmailTemplateRepositoryate.findAllByMailTemplateCode(this.MAIL_NGUOI_TAO_PHIEU_THU_HOI_CODE).get(0);       // template mặc định của thu hồi phiếu
            // lấy toàn bộ người trong quy trình để gửi mail
            List<UserInfo> toUserInfos = new ArrayList<>();
            stepDataList.forEach(ele -> {
                toUserInfos.addAll(ele.getUserInfos());
            });
            List<UserInfo> ccUserInfos = new ArrayList<>();
            ccUserInfos.addAll(requestData.getUserInfos());

            if(mailTemplate != null){
                Map<String, Object> props_THUHOI = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                this.autoSendMail(requestDataId, mailTemplate, toUserInfos, null, ccUserInfos, null, props_THUHOI, processer);
            }
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(approveOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            // Khi người tạo thu hồi phiếu thành công -> revert lại các file tài liệu chính đã ẩn đi khi ký thành công
            this.signUtils.revertFilePrimary(requestDataId);

            return true;
        };
    }

    @Transactional
    public Boolean resendMail(ReSendOption reSendOption){
        UserInfo processer = this.userInfoRepository.findById(reSendOption.getUserId()).get();             // lấy thông tin người xử lý

        Long requestDataId = reSendOption.getRequestDataId();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
        if(requestData == null) return false;

        ProcessData currentProcessData = this.processDataRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().orElse(null);
        if(currentProcessData == null) return false;

        List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
        if(stepDataList == null || stepDataList.isEmpty()) return false;

        // lấy thông tin bước hiện tại
        StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().get();

        // xóa OTP cũ đi
        List<Long> otpIds = this.otpRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> ele.getId()).collect(Collectors.toList());
        this.otpRepository.deleteAllById(otpIds);

        // cập nhật thông tin signData và fill in data vào các file tài liệu
        List<Long> signDataIds = this.signDataRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> ele.getId()).collect(Collectors.toList());
        this.signDataRepository.deleteAllById(signDataIds);
        List<SignData> signDataList = this.signDataRepository.saveAll(reSendOption.getSignDataList().stream().map(ele -> this.signDataMapper.toEntity(ele)).collect(Collectors.toList()));


        if (currentStepData.getMailTemplateCustomer() != null){

            List<FieldData> listDFieldData = this.fieldDataRepository.findAllByRequestDataId(requestDataId);
            boolean useTemplate = true;
            FormData formData = this.formDataRepository.findAllByRequestDataId(requestDataId).get(0);
            List<ManageStampInfo> manageStampInfoList = this.manageStampInfoRepository.findAllByRequestDataId(requestDataId);
            this.fieldDataCustomService.fillIntoFile(listDFieldData, formData, useTemplate, requestDataId, signDataList, manageStampInfoList);

            // gửi mail cho khách hàng
            MailTemplate mailTemplateCustomer = currentStepData.getMailTemplateCustomer();
            if(mailTemplateCustomer != null){
                this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);
            }

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = processer.getRanks() != null ? processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null;
            String processerOrganiztionName = processer.getOrganizations() != null ? processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null;
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistory(reSendOption.getReason(), requestData, processer, null, processerRankName, processerOrganiztionName, null);
            this.reqdataProcessHisRepository.save(parentHistory);

            //revert lại các file tài liệu chính đã ẩn đi khi ký thành công
            this.signUtils.revertFilePrimary(requestDataId);

        }

        return true;
    }

    public boolean checkReSendMail(Long requestDataId, Long userId){
        try {
            RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
            if(!userId.equals(requestData.getCreated().getId()) && (requestData.getModified() != null && !userId.equals(requestData.getModified().getId()))){      // nếu ko phải là người tạo -> trả về false
                return false;
            }

            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();

            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if (stepDataList == null || stepDataList.isEmpty())
                return false;                                                                       // th không có bước thực hiện nào -> lỗi
            StepData currentStepData = stepDataList.stream().filter(ele -> this.conditionUtils.checkTrueFalse(ele.getIsActive())).findFirst().orElse(null);
            if (currentStepData == null)
                return false;

            return !this.signDataRepository.findAllByRequestDataId(requestDataId).stream().anyMatch(ele -> ele.getNumberSign() != null &&  ele.getNumberSign() > 0);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
    }
    private ReqdataProcessHis createHistory(String description, RequestData requestData, UserInfo processer, StepData stepData, String rankName, String organizationName, List<AttachmentFile> attachmentFiles){
        ReqdataProcessHis reqdataProcessHis = new ReqdataProcessHis();
        reqdataProcessHis.setDescription(description);
        reqdataProcessHis.setProcessDate(Instant.now());
        reqdataProcessHis.setRequestData(requestData);
        reqdataProcessHis.setStatus(requestData!=null ? requestData.getStatusName() : null);
        reqdataProcessHis.setProcesser(processer);
        reqdataProcessHis.setStepData(stepData);
        reqdataProcessHis.setCreateDate(Instant.now());
        reqdataProcessHis.setProcesserName(processer != null ? processer.getFullName() : null);
        reqdataProcessHis.setRankName(rankName);
        reqdataProcessHis.setOrganizationName(organizationName);
        reqdataProcessHis.setAttachmentFiles(attachmentFiles != null ? attachmentFiles.stream().collect(Collectors.toSet()) : null);
//        reqdataProcessHis.setIsChild(false);
        reqdataProcessHis.setIsChild(true);
        return reqdataProcessHis;
    }

    private ReqdataProcessHis createHistoryParent(String description, RequestData requestData, UserInfo processer, StepData stepData, String rankName, String organizationName, List<AttachmentFile> attachmentFiles){
        ReqdataProcessHis reqdataProcessHis = new ReqdataProcessHis();
        reqdataProcessHis.setDescription(description);
        reqdataProcessHis.setProcessDate(Instant.now());
        reqdataProcessHis.setRequestData(requestData);
        reqdataProcessHis.setStatus(requestData!=null ? requestData.getStatusName() : null);
        if(stepData != null && stepData.getUserInfos() != null && stepData.getUserInfos().size() > 1){
            reqdataProcessHis.setProcesser(null);
            this.reqdataProcessHisRepository.save(this.createHistory(description, requestData, processer, stepData, rankName, organizationName, attachmentFiles));
        }else{
            reqdataProcessHis.setProcesser(processer);
        }
        reqdataProcessHis.setStepData(stepData);
        reqdataProcessHis.setCreateDate(Instant.now());
        reqdataProcessHis.setProcesserName(processer != null ? processer.getFullName() : null);
        reqdataProcessHis.setRankName(rankName);
        reqdataProcessHis.setOrganizationName(organizationName);
        reqdataProcessHis.setAttachmentFiles(attachmentFiles != null ? attachmentFiles.stream().collect(Collectors.toSet()) : null);
        reqdataProcessHis.setIsChild(false);
        return reqdataProcessHis;
    }

    /**
     * Hàm thực hiện kiểm tra user đang thực hiện có quyền xử lý hay không
     * @param userInfoProcess       : user đang thực hiện xử lý
     * @param listUserCanPermision  : danh sách user có quyền xử lý
     * @return
     */
    private boolean checkPermisionProcess(UserInfo userInfoProcess, List<UserInfo> listUserCanPermision){
        if(listUserCanPermision == null || userInfoProcess == null) return false;
        boolean result = listUserCanPermision.stream().anyMatch(ele -> ele.getId().equals(userInfoProcess.getId()));
        return result;
    }

    /**
     * Hàm thực hiện xử lý đồng thời (kiểm tra đã là người cuối cùng xử lý bước này hay chưa)
     * @param userInfoProcess   : người xử lý
     * @param stepData          : bước quy trình cần xử lý
     * @param action            : hành động                 : nếu là Reject, Refuse, CancelApprove, Recall, CreaterReall -> trả về true luôn để update thông tin luôn
     * @return: nếu là true -> đã là người cuối rồi -> update thông tin như bình thường, nếu là false -> chưa là người cuối -> trả về là xử lý thành công và ko thực hiện update thông tin
     */
    private final List<ApproveOption.Action> ACTION_PROCESS_NOW = Arrays.asList(ApproveOption.Action.Refuse, ApproveOption.Action.Reject, ApproveOption.Action.CancelApprove, ApproveOption.Action.Recall, ApproveOption.Action.CreaterReall);        // những hành động mà sẽ thực hiện update luôn thông tin
    private final Map<ApproveOption.Action, String> ACTION_NAME = new HashMap<>();
    private boolean checkProcessTogether(UserInfo excutor, StepData stepData, ApproveOption.Action action){
        this.loadActionName();
        Set<UserInfo> userInfoInStepData = new HashSet<>();
        if(stepData != null) userInfoInStepData = stepData.getUserInfos();
        List<ResultOfStep> resultOfSteps = new ArrayList<>();
        if(stepData != null) resultOfSteps = this.resultOfStepRepository.findAllByStepDataId(stepData.getId());

        if(ACTION_PROCESS_NOW.stream().anyMatch(ele -> ele.equals(action))){
            if(ApproveOption.Action.CreaterReall.equals(action)){
                    // nếu là TH người tạo phiếu yêu cầu thu hồi -> reset lại toàn bộ kết quả của tất cả các bước trong quy trình của phiếu
                List<StepData> allByProcessDataId = this.stepDataRepository.findAllByProcessDataId(stepData.getProcessData().getId());
                allByProcessDataId.forEach(ele -> {
                    List<ResultOfStep> allByStepDataId = this.resultOfStepRepository.findAllByStepDataId(ele.getId());
                    this.resultOfStepRepository.deleteAll(allByStepDataId);
                });
            }else if(ApproveOption.Action.Recall.equals(action)){
                    // nếu là TH người xử lý yêu cầu thu hồi -> reset lại các kết quả của các bước trước   .... tạm thời chưa biết xử lý thế nào -> làm giống như CreaterRecall
                List<StepData> allByProcessDataId = this.stepDataRepository.findAllByProcessDataId(stepData.getProcessData().getId());
                allByProcessDataId.forEach(ele -> {
                    List<ResultOfStep> allByStepDataId = this.resultOfStepRepository.findAllByStepDataId(ele.getId());
                    this.resultOfStepRepository.deleteAll(allByStepDataId);
                });
            }else if(ApproveOption.Action.CancelApprove.equals(action)){
                    // nếu là TH người xử lý yêu cầu hủy phê duyệt -> bỏ kết quả ở bước cuối (TH này tham số truyền vào stepData sẽ là FinalStepDaTa)
                this.resultOfStepRepository.deleteAll(resultOfSteps);
            }else if(ApproveOption.Action.Refuse.equals(action)){               // TH TỪ CHỐI
                List<StepData> listStepDataResetResult = new ArrayList<>();             // list stepData cần resest lại kết quả xử lý
                if(stepData.getOptionDeny().equals(-1L)) {      // nếu = -1 -> về bước đầu -> reset lại tất cả của quy trình hiện tại
                    listStepDataResetResult = this.stepDataRepository.findAllByProcessDataId(stepData.getProcessData().getId()).stream().collect(Collectors.toList());
                }else if(stepData.getOptionDeny().equals(-2L)){     // nếu = -2 -> đến bước tiếp theo -> ko cần rest
                    listStepDataResetResult = new ArrayList<>();
                }else{              // nếu > 0 -> đến bước n -> reset lại kết quả của các bước từ bước n đến bước hiện tại
                    listStepDataResetResult = this.stepDataRepository.findAllByProcessDataId(stepData.getProcessData().getId()).stream().filter(ele -> ele.getStepOrder() >= stepData.getOptionDeny() && ele.getStepOrder() <= stepData.getStepOrder()).collect(Collectors.toList());
                }
                if(listStepDataResetResult != null && !listStepDataResetResult.isEmpty()){
                    listStepDataResetResult.forEach(ele -> {
                        List<ResultOfStep> allByStepDataId = this.resultOfStepRepository.findAllByStepDataId(ele.getId());
                        this.resultOfStepRepository.deleteAll(allByStepDataId);
                    });
                }
            }else{
                this.resultOfStepRepository.deleteAll(resultOfSteps);
                List<StepData> allByProcessDataId = this.stepDataRepository.findAllByProcessDataId(stepData.getProcessData().getId());
                allByProcessDataId.forEach(ele -> {
                    List<ResultOfStep> allByStepDataId = this.resultOfStepRepository.findAllByStepDataId(ele.getId());
                    this.resultOfStepRepository.deleteAll(allByStepDataId);
                });
            }
            return true;
        }
        if((resultOfSteps == null || resultOfSteps.isEmpty()) && (userInfoInStepData != null && userInfoInStepData.size() > 1)){
            this.createResultOfResult(excutor, stepData, action);
            return false;
        }

        List<UserInfo> userInfoProcessed = resultOfSteps.stream().map(ele -> ele.getExcutor()).collect(Collectors.toList());

        // compare 2 list (userInfoInStepData: danh sách người trong bước (cần phải filter để bỏ excutor(người đang thực hiện)), userInfoProcessed: danh sách người đã xử lý)
        // nếu trong userInfoInStepData vẫn còn người trong ko có trong userInfoProcessed -> chưa phải là người cuối cùng -> trả về false
        Boolean result = userInfoInStepData.stream().filter(ele -> !ele.getId().equals(excutor.getId())).anyMatch(ele -> {              // result: true: -> vẫn còn người chưa có kết quả -> chưa là người cuối
           return !userInfoProcessed.stream().anyMatch(ele1 -> ele.getId().equals(ele1.getId()));
        });

        if(result == true){         // nếu chưa là người cuối -> tạo kết quả
            this.createResultOfResult(excutor, stepData, action);
        }
        return !result;
    }

    /**
     * Hàm khởi tạo giá action và tên tương ứng
     */
    private void loadActionName(){
        if(this.ACTION_NAME == null || this.ACTION_NAME.isEmpty()){
            this.ACTION_NAME.put(ApproveOption.Action.Approve, "Phê Duyệt");
            this.ACTION_NAME.put(ApproveOption.Action.Agree, "Đồng Ý");
            this.ACTION_NAME.put(ApproveOption.Action.Reject, "Trả Lại");
            this.ACTION_NAME.put(ApproveOption.Action.Refuse, "Từ Chối");
            this.ACTION_NAME.put(ApproveOption.Action.Recall, "Thu Hồi");
            this.ACTION_NAME.put(ApproveOption.Action.CreaterReall, "Người Tạo Phiếu Thu Hồi");
            this.ACTION_NAME.put(ApproveOption.Action.CancelApprove, "Hủy Phê Duyệt");
            this.ACTION_NAME.put(ApproveOption.Action.Send, "Trình Phiếu Yêu Cầu");
        }
    }

    private ResultOfStep createResultOfResult(UserInfo excutor, StepData stepData, ApproveOption.Action action){
        ResultOfStep resultOfStep = new ResultOfStep();
        resultOfStep.setExcutor(excutor);
        String rankName = excutor.getRanks() == null ? "" : excutor.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
        resultOfStep.setExcutorRankName(rankName);
        String orgName = excutor.getOrganizations() == null ? "" : excutor.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
        resultOfStep.setExecutorOrgName(orgName);
        resultOfStep.setAction(action.toString());
        resultOfStep.setActionName(this.ACTION_NAME.get(action));
        resultOfStep.setStepData(stepData);
        resultOfStep.setExcuteDate(Instant.now());
        resultOfStep = this.resultOfStepRepository.save(resultOfStep);
        return resultOfStep;
    }


    // xử lý của khách hàng \\
    private final String CUSTOMER_SIGN_STATUS = "Đã Ký";
    @Transactional
    private Function<CustomerApproveOption, Boolean> functionAgreeCustomer(){
        return (customerApproveOption) -> {
            Long requestDataId = customerApproveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào không
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;                                            // kiểm tra xem có bước nào không
//            List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2) -> { return (int) (ele1.getStepOrder() - ele2.getStepOrder());}).collect(Collectors.toList());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);
            if(currentStepData.getMailTemplateCustomer() == null) return false;                                         // nếu ko phải bước của khách hàng -> trả về false

            // cập nhật thông tin là Customer này đã ký vào trong signData tương ứng  với mã OTP
            String otpCode = customerApproveOption.getOtp() != null ? customerApproveOption.getOtp() : "";
            OTP otp = this.otpRepository.customGetAllByRequestDataIdAndOTPCode(requestDataId, otpCode).stream().filter(ele -> {
                return (ele.getIsActive() != null && ele.getIsActive() == true) && (ele.getIsDelete() == null || ele.getIsDelete() == false);
            }).findFirst().orElse(null);

            if(otp == null || otp.getSignData() == null) return false;                                                  // không có OTP hoặc signData tương ứng -> false
            SignData signData = otp.getSignData();
            signData.setStatus(this.CUSTOMER_SIGN_STATUS);                                                              // đánh dấu lại khách hàng này đã ký
            this.signDataRepository.save(signData);


            // kiểm tra xem toàn bộ khách hàng đã ký chưa | nếu rồi -> thực hiện chuyển bước | nếu chưa thì return
            List<SignData> signDataList = this.signDataRepository.findAllByRequestDataId(requestDataId);
            if(signDataList != null){
                if(signDataList.stream().anyMatch(ele -> !Strings.isNullOrEmpty(ele.getEmail()) && !this.CUSTOMER_SIGN_STATUS.equals(ele.getStatus()))){
                    return true;
                }


                // không ghi lịch sử ở đây vì kí số đã thực hiện ghi rồi (frontend đang tích hợp đồng ý và ký số vào 1 nút) (NOTE: cần ghi lịch sử cha khi là người cuối cùng xử lý)
                String processerRankName = "";
                String processerOrganiztionName = "";
                ReqdataProcessHis parentHistory = this.createHistoryParent(customerApproveOption.getReason(), requestData, null, currentStepData, processerRankName, processerOrganiztionName, null);
                this.reqdataProcessHisRepository.save(parentHistory);
            }

            StepData nextStepData = stepDataList.stream().filter(ele -> ele.getStepOrder() == currentStepData.getStepOrder() + 1).findFirst().orElse(null);
            if(nextStepData != null){       //th != null -> đây là chưa là bước cuối -> vẫn phải chuyển bước tiếp theo . th = null -> đây là bước cuối rồi không cần chuyển bước tiếp theo
                currentStepData.setIsActive(false);
                this.stepDataRepository.save(currentStepData);
                nextStepData.setIsActive(true);
                nextStepData.setTimeActive(Instant.now());
                if (nextStepData.getProcessingTerm() != null && nextStepData.getProcessingTerm() > 0L) {
                    Double secondPlus = (nextStepData.getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                    nextStepData.setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
                } else {
                    nextStepData.setProcessingTermTime(null);
                }
                this.stepDataRepository.save(nextStepData);
            }else{                  // th == null -> đây là bước cuối cùng ko cần chuyển bước
                currentStepData.setIsActive(false);
                this.stepDataRepository.save(currentStepData);
                // đánh dấu requestData đánh dấu là đã hoàn thành phiếu
                requestData.setIsDone(true);
                requestData.setTimeDone(Instant.now());
            }

            if(customerApproveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(customerApproveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_;
                if(nextStepData == null){   // nếu là bước cuối -> chuyển trạng thái về đã phê duyệt
                    STATUS_ = this.ALL_STATUS.stream().filter(ele -> this.DAPHEDUYET.equals(ele.getStatusCode())).findFirst().get();
                }else{      // nếu ko là bước cuối -> chuyển trạng thái về chờ xử lý
                    STATUS_ = this.ALL_STATUS.stream().filter(ele -> this.CHOXULY.equals(ele.getStatusCode())).findFirst().get();
                }
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_);
                requestData.setStatusName(STATUS_.getStatusName());
            }

            // Khi khi phệ ở bước cuối cùng -> sẽ lấy mail cứng DAPHEDUYET
            Optional<MailTemplate> mailTemplate = this.mailTempmailTemplateRepositoryate.findById(1L);        // template mặc định của trả lại phiếu
            if(nextStepData == null){   // nếu là bước cuối cùng rồi -> gửi mail kết thúc phiếu
                requestData.setIsApprove(true);
                try {
                    MailTemplate mailTemplateDAPHEDUYET = this.mailTempmailTemplateRepositoryate.findAllByMailTemplateCode(this.MAIL_DA_PHE_DUYET_CODE).get(0);
                    Map<String, Object> props_DAPHEDUYET = this.getDataFromObject(requestData, mailTemplate.get().getMappingInfo());
                    List<UserInfo> allUserOfRequestData = new ArrayList<>();
                    stepDataList.forEach(ele -> {
                        allUserOfRequestData.addAll(ele.getUserInfos());
                    });
                    UserInfo KhachHang = new UserInfo();
                    KhachHang.setFullName("Khách hàng");
                    this.autoSendMail(requestDataId, mailTemplateDAPHEDUYET, Arrays.asList(requestData.getCreated()), null, Arrays.asList(requestData.getCreated()), allUserOfRequestData, props_DAPHEDUYET, KhachHang);

                    // gửi mail cho khách hàng
                    // this.autoSendMailToCustomer(requestDataId, mailTemplateDAPHEDUYET, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), processer);

                }catch (Exception e){
                    log.error("{}", e);
                }
            }else{
                if(nextStepData.getMailTemplate() != null){
                    Map<String, Object> props_TRALAI = this.getDataFromObject(requestData, mailTemplate.get().getMappingInfo());
                    UserInfo KhachHang = new UserInfo();
                    KhachHang.setFullName("Khách hàng");
                    this.autoSendMail(requestDataId, mailTemplate.get(), Arrays.asList(requestData.getCreated()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_TRALAI, KhachHang);
                }
                // gửi mail cho khách hàng
                MailTemplate mailTemplateCustomer = nextStepData.getMailTemplateCustomer();
                if(mailTemplateCustomer != null){
                    UserInfo KhachHang = new UserInfo();
                    KhachHang.setFullName("Khách hàng");
                    this.autoSendMailToCustomer(requestDataId, mailTemplateCustomer, new ArrayList<>(), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), KhachHang);
                }
            }

            // phê duyệt thành công
//            if(approver.isPresent()){
//                requestData.setApprover(approver.get());
//                requestData.setApproverName(approver.get().getFullName());
//                requestData.setApproverRankName(approver.get().getRanks() != null ? approver.get().getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : null);
//                requestData.setApproverOrgName(approver.get().getOrganizations() != null ? approver.get().getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : null);
//            }

            requestData.setExpiredTime(nextStepData == null ? null : nextStepData.getProcessingTermTime());
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
//            String processerRankName = "";
//            String processerOrganiztionName = "";
////            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
////            this.reqdataProcessHisRepository.save(history);
//
//            ReqdataProcessHis parentHistory = this.createHistoryParent(customerApproveOption.getReason(), requestData, null, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(parentHistory);

            if(nextStepData == null){       // th đã là bước cuối -> đồng bộ sang DataRoom
                try {
                    this.dataRoomCustomService.syncToDataRoom(requestData.getId());
                } catch (Exception e) {
                    throw new RuntimeException("Error when sync to DATA-ROOM");
                }
            }

            return true;
        };
    }


    @Transactional
    public Function<CustomerApproveOption, Boolean> functionRejectCustomer(){                   // xử lý chức năng trả lại phiếu
        return (customerApproveOption) -> {

            Long requestDataId = customerApproveOption.getRequestDataId();
            RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
            if(requestData == null) return false;
            List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
            if(processDataList == null || processDataList.isEmpty()) return false;                                      // kiểm tra xem có quy trình nào ko
            ProcessData currentProcessData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().get();
//            List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
            List<StepData> stepDataList = this.stepDataRepository.findAllByProcessDataId(currentProcessData.getId());
            if(stepDataList == null || stepDataList.isEmpty()) return false;
            //List<StepData> stepDataListCurrent = stepDataList.stream().filter(ele -> ele.getRoundNumber() == requestData.getCurrentRound()).sorted( (ele1, ele2) -> { return (int) (ele2.getStepOrder() - ele1.getStepOrder());}).collect(Collectors.toList());
            stepDataList = stepDataList.stream().sorted((ele1, ele2) -> { return (int) (ele1.getStepOrder() - ele2.getStepOrder());}).collect(Collectors.toList());
            StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);
            if(currentStepData.getMailTemplateCustomer() == null) return false; // nếu ko phải bước của khách hàng -> trả về false

            Long nextRoundNumber = requestData.getCurrentRound() + 1;

//            ProcessData processData = this.processDataRepository.findAllByRequestDataId(requestDataId).get(0);
//            processData.setId(null);
//            processData.setRoundNumber(nextRoundNumber);
//            this.processDataRepository.save(processData);
            ProcessData processDataNew = new ProcessData();
            BeanUtils.copyProperties(currentProcessData, processDataNew, "stepData");
            processDataNew.setId(null);
            processDataNew.setIsActive(true);
            processDataNew.setRoundNumber(nextRoundNumber);
            processDataNew.setCreatedDate(Instant.now());
            processDataNew.setModifiedDate(Instant.now());
            processDataNew.setStepData(new HashSet<>());
            processDataNew = this.processDataRepository.save(processDataNew);

            currentProcessData.setIsActive(false);
            this.processDataRepository.save(currentProcessData);

            for(StepData stepData : stepDataList){
                StepData stepDataNew = new StepData();
                BeanUtils.copyProperties(stepData, stepDataNew);
                stepDataNew.setId(null);
                stepDataNew.setRoundNumber(nextRoundNumber);
                stepDataNew.setIsActive(false);
                stepDataNew.setTimeActive(null);
                stepDataNew.setProcessingTermTime(null);
                stepDataNew.setCreatedDate(Instant.now());
                stepDataNew.setModifiedDate(Instant.now());
                stepDataNew.setProcessData(processDataNew);
                stepDataNew.setAttachmentInSteps(new HashSet<>());
                stepDataNew.setReqdataProcessHis(new HashSet<>());
                stepDataNew.setRequestRecalls(new HashSet<>());
                stepDataNew.setUserInfos(new HashSet<>(stepData.getUserInfos().stream().map(ele -> {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(ele.getId());
                    return userInfo;
                }).collect(Collectors.toSet())));
                stepDataNew.setConsults(new HashSet<>());
                stepDataNew.setExamines(new HashSet<>());
                stepDataNew.setTransferHandles(new HashSet<>());
                stepDataNew.setResultOfSteps(new HashSet<>());
                stepDataNew = this.stepDataRepository.save(stepDataNew);
            }

            currentStepData.setIsActive(false);
            this.stepDataRepository.save(currentStepData);

            requestData.setCurrentRound(nextRoundNumber);

            if(customerApproveOption.getStatusId() != null){            // TH có gửi trạng thái mong muốn lên -> set theo trạng thái mong muốn
                Status status = this.statusRepository.findById(customerApproveOption.getStatusId()).orElse(new Status());
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(status);
                requestData.setStatusName(status.getStatusName());
            }else{                                              // Th ko gửi trạng thái mong muốn -> set theo trạng thái mặc định (gửi thì sẽ chuyển về trạng thái 'Chờ Xử Lý')
                Status STATUS_TRALAI = this.ALL_STATUS.stream().filter(ele -> this.TRALAI.equals(ele.getStatusCode())).findFirst().get();
                Long oldStatus = requestData.getStatus() != null ? requestData.getStatus().getId() : null;
                requestData.setOldStatus(oldStatus);
                requestData.setStatus(STATUS_TRALAI);
                requestData.setStatusName(STATUS_TRALAI.getStatusName());
            }
            //Optional<MailTemplate> mailTemplate = this.mailTempmailTemplateRepositoryate.findById(1L);        // template mặc định của trả lại phiếu
            MailTemplate mailTemplate = this.mailTempmailTemplateRepositoryate.findAllByMailTemplateCode(this.MAIL_TRA_LAI_CODE).get(0);
            if(mailTemplate != null){
                Map<String, Object> props_TRALAI = this.getDataFromObject(requestData, mailTemplate.getMappingInfo());
                UserInfo KhachHang = new UserInfo();
                KhachHang.setFullName("Khách hàng");
                this.autoSendMail(requestDataId, mailTemplate, Arrays.asList(requestData.getCreated()), null, null/*Arrays.asList(requestData.getCreated())*/, null, props_TRALAI, KhachHang);
            }

            requestData.setExpiredTime(null);
            requestData.setModifiedDate(Instant.now());
            this.requestDataRepository.save(requestData);

            // khi thành công -> tạo lịch sử xử lý
            String processerRankName = "";
            String processerOrganiztionName = "";
//            ReqdataProcessHis history = this.createHistory(approveOption.getReason(), requestData, processer, currentStepData, processerRankName, processerOrganiztionName, null);
//            this.reqdataProcessHisRepository.save(history);

            ReqdataProcessHis parentHistory = this.createHistoryParent(customerApproveOption.getReason(), requestData, null, currentStepData, processerRankName, processerOrganiztionName, null);
                // thêm thông tin người từ chối vào trong lịch sử \\
            String otpCode = customerApproveOption.getOtp() != null ? customerApproveOption.getOtp() : "";
            OTP otp = this.otpRepository.customGetAllByRequestDataIdAndOTPCode(requestDataId, otpCode).stream().filter(ele -> {
                return (ele.getIsActive() != null && ele.getIsActive() == true) && (ele.getIsDelete() == null || ele.getIsDelete() == false);
            }).findFirst().orElse(null);
            if(otp != null && otp.getSignData() != null)
                parentHistory.setProcesserName(otp.getSignData().getSignName());
            this.reqdataProcessHisRepository.save(parentHistory);

            // Khi trả lại thành công -> revert lại các file tài liệu chính đã ẩn đi khi ký thành công
            this.signUtils.revertFilePrimary(requestDataId);

            // khách hàng trả lại -> reset lại thông tin trong signData
            this.resetDataCustomer(requestDataId);

            return true;
        };
    }

    /**
     * Hàm thực hiện reset lại thông tin các object của khách hàng (xóa mã OTP, reset lại số lần xem, lần ký, lần tải, ... trong signData)
     * @param requestDataId : id của phiếu
     */
    @Transactional
    private void resetDataCustomer(Long requestDataId){
        this.signDataRepository.findAllByRequestDataId(requestDataId).forEach(ele -> {
            ele.setNumberSign(0L);
            ele.setNumberView(0L);
            ele.setNumberDownload(0L);
            ele.setNumberPrint(0L);
            ele.setStatus(null);
            this.signDataRepository.save(ele);
        });
        List<Long> otpIds = this.otpRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> ele.getId()).collect(Collectors.toList());
        this.otpRepository.deleteAllById(otpIds);
    }


    @Transactional
    public Boolean actionRequestData(ApproveOption approveOption){
        if(this.handlerAction.size() == 0) this.loadHandlerAction();
        Boolean result = this.handlerAction.get(approveOption.getAction()).apply(approveOption);
        return result;
    }

    @Transactional
    public Boolean customerActionRequestData(CustomerApproveOption customerApproveOption){
        if(this.handlerActionCustomer.size() == 0) this.loadHandlerActionCustomer();
        Boolean result = this.handlerActionCustomer.get(customerApproveOption.getAction()).apply(customerApproveOption);
        return result;
    }
}
