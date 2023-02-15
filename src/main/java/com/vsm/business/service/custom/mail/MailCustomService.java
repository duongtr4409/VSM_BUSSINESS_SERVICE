package com.vsm.business.service.custom.mail;

import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.config.Constants;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.OTP;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.SignData;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import com.vsm.business.service.custom.mail.bo.response.MailServiceResposeDTO;
import com.vsm.business.utils.CallAPIUtils;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.HashUtils;
import com.vsm.business.utils.OTPUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailCustomService {

    private final Logger log = LoggerFactory.getLogger(MailCustomService.class);

    @Value("${system.otp.link:https://uat-eoffice.vincom.com.vn/phieu-yeu-cau/view-customer/{{id}}}")
    private String OTP_LINK;
    @Value("${system.otp.htmlCodeOTP:<br><br><p>Mã OTP: <strong>{{OPT_CODE}}</strong></p><p><a href='{{REQUESTDATA_LINK}}' rel='noopener noreferrer nofollow'>Phiếu yêu cầu: {{REQUEST_DATA_CODE}}</a></p>}")
    private String HTML_CODE_OTP;

    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    private String TEMP_FOLDER;
    private static final String PROJECT_FOLDER = new File("").getAbsolutePath();
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    private final String PDF_FORMAT = "pdf";
    private final String PDF_EXTENTION = "pdf";
    @Value("${mailService.url.baseUrl:http://127.0.0.1:9899/api}")
    private String urlMailSercice;

    @Value("${mailService.url.mail.sendMail:/mail}")
    private String urlSendMail;

    @Value("${mailService.url.mail.sendMailWithTemplate:/mail-with-template}")
    private String urlSendMailWithTemplate;

    @Value("${primary-document-code:TAULIEUCHINH}")
    private String MA_TAI_LIEU_CHINH;   // mã tàu liệu chính


    @Value("${temp.upload.folder}")
    private String tempFolder;

    private final CallAPIUtils callAPIUtils;

    private final UploadFile365CustomService uploadFile365CustomService;

    private final AttachmentFileRepository attachmentFileRepository;

    private final OTPRepository otpRepository;

    private final RequestDataRepository requestDataRepository;

    @Autowired
    private ConditionUtils conditionUtils;

    @Autowired
    private SendMailCustomService sendMailCustomService;

    @Autowired
    private GraphService graphService;

    @Value("${feature.hash-request:TRUE}")
    private String FEATURE_HASH_REQUEST;

    @Value("${customer.otp.length:6}")
    private Integer OTP_LENGTH;

    @Value("${customer.otp.expired.day:30}")
    private String OTP_EXPIRED_DAY;

    @Value("${customer.otp.expired.hour:12}")
    private String OTP_EXPIRED_HOUR;

    @Value("${customer.otp.expired.minute:30}")
    private String OTP_EXPIRED_MINUTE;


    public MailCustomService(CallAPIUtils callAPIUtils, UploadFile365CustomService uploadFile365CustomService, AttachmentFileRepository attachmentFileRepository, OTPRepository otpRepository, RequestDataRepository requestDataRepository) {
        this.callAPIUtils = callAPIUtils;
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.attachmentFileRepository = attachmentFileRepository;
        this.otpRepository = otpRepository;
        this.requestDataRepository = requestDataRepository;
    }

    /**
     * hàm thực hiện call MailService để thực hiện chức năng gửi mail
     * @param multipartFiles: danh sách file đính kèm
     * @param mailInfoDTO: thông tin mail (người nhận, cc, bcc, nội dung mail, ...)
     * @return
     * @throws Exception
     */
    public IResponseMessage sendMail(MultipartFile multipartFiles[], MailInfoDTO mailInfoDTO) throws Exception {
        String url = this.urlMailSercice + urlSendMail;
        HttpMethod method = HttpMethod.POST;

        Map<String, Object> params = new HashMap<>();
        Field[] fields = mailInfoDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object data = field.get(mailInfoDTO);
            if (data != null)
                params.put(field.getName(), field.get(mailInfoDTO));
        }

        List<File> files = new ArrayList();
        if(multipartFiles != null){
            for (MultipartFile multipartFile : multipartFiles) {
                File file = getFileFromMultipartFile(multipartFile);
                files.add(file);
            }
        }

        MailServiceResposeDTO result;
        try {

            result = this.callAPIUtils.createRestTemplateMailService(url, null, files, method, params, MailServiceResposeDTO.class);
        } finally {
            files.forEach(ele -> ele.delete());
        }
        if(result.isState()) return new LoadedMessage(result.getData());
        else return new FailLoadMessage(result.getData());
    }

    public IResponseMessage sendMailOffice365(MultipartFile multipartFiles[], MailInfoDTO mailInfoDTO) throws Exception {
        List<File> files = new ArrayList();
        try {
            if(multipartFiles != null){
                for (MultipartFile multipartFile : multipartFiles) {
                    File file = getFileFromMultipartFile(multipartFile);
                    files.add(file);
                }
            }
            if(mailInfoDTO.getAddFile() && mailInfoDTO.getRequestDataId() != null){
                List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(mailInfoDTO.getRequestDataId());
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        if(attachmentFile.getTemplateForm() != null)
                            files.add(this.inputStreamToFile(this.graphService.getFile(attachmentFile.getItemId365(), PDF_EXTENTION.equals(attachmentFile.getFileExtension()) ? null:PDF_FORMAT), attachmentFile.getFileName()));
                    }
                }
            }
            if(mailInfoDTO.getAddOTP() != null && mailInfoDTO.getAddOTP() == true && mailInfoDTO.getRequestDataId() != null){
                Optional<RequestData> requestData = this.requestDataRepository.findById(mailInfoDTO.getRequestDataId());
                mailInfoDTO.setContent(mailInfoDTO.getContent() + this.generateOTP(requestData.get()));
            }
            this.sendMailCustomService.sendMail(files, mailInfoDTO);
            return new LoadedMessage(true);
        }catch (Exception e){
            log.error("{}", e);
            return new FailLoadMessage(false);
        }finally {
            for(File file : files){
                file.delete();
            }
        }
    }

    public IResponseMessage sendMailWithTemplate365(MailInfoDTO mailInfoDTO, Long requestDataId) throws Exception {
        List<File> files = new ArrayList();
        try {
            if(mailInfoDTO.getAddFile() != null && mailInfoDTO.getAddFile() == true && mailInfoDTO.getRequestDataId() != null){     // lấy danh sách file đính kèm
                List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(mailInfoDTO.getRequestDataId());
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        if(attachmentFile.getTemplateForm() != null)
                            files.add(this.inputStreamToFile(this.graphService.getFile(attachmentFile.getItemId365(), PDF_EXTENTION.equals(attachmentFile.getFileExtension()) ? null:PDF_FORMAT), attachmentFile.getFileName()));
                    }
                }
            }
            // mail tự động ko cần mã OTP
            this.sendMailCustomService.sendMail(files, mailInfoDTO);
            return new LoadedMessage(true);
        }catch (Exception e){
            log.error("{}", e);
            return new FailLoadMessage(false);
        }finally {
            for(File file : files){
                file.delete();
            }
        }
    }

    public IResponseMessage sendMailWithTemplate365ToCustomer(MailInfoDTO mailInfoDTO, Long requestDataId) throws Exception {
        List<File> files = new ArrayList();
        try {
            if(mailInfoDTO.getAddFile() != null && mailInfoDTO.getAddFile() == true && mailInfoDTO.getRequestDataId() != null){     // lấy danh sách file đính kèm
                List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(mailInfoDTO.getRequestDataId());
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        if(attachmentFile.getTemplateForm() != null)
                            files.add(this.inputStreamToFile(this.graphService.getFile(attachmentFile.getItemId365(), PDF_EXTENTION.equals(attachmentFile.getFileExtension()) ? null:PDF_FORMAT), attachmentFile.getFileName()));
                    }
                }
            }
            // mail tự động ko cần mã OTP
            if(mailInfoDTO.getAddOTP() != null && mailInfoDTO.getAddOTP() == true && mailInfoDTO.getRequestDataId() != null){
                Optional<RequestData> requestData = this.requestDataRepository.findById(mailInfoDTO.getRequestDataId());
                mailInfoDTO.setContent(mailInfoDTO.getContent() + this.generateOTP(requestData.get()));
            }

            this.sendMailCustomService.sendMail(files, mailInfoDTO);
            return new LoadedMessage(true);
        }catch (Exception e){
            log.error("{}", e);
            return new FailLoadMessage(false);
        }finally {
            for(File file : files){
                file.delete();
            }
        }
    }

    public IResponseMessage sendMailWithTemplate365ToOneCustomer(MailInfoDTO mailInfoDTO, Long requestDataId, SignData signData) throws Exception {
        List<File> files = new ArrayList();
        try {
            if(mailInfoDTO.getAddFile() != null && mailInfoDTO.getAddFile() == true && mailInfoDTO.getRequestDataId() != null){     // lấy danh sách file đính kèm
                List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(mailInfoDTO.getRequestDataId());
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        if(attachmentFile.getTemplateForm() != null)
                            files.add(this.inputStreamToFile(this.graphService.getFile(attachmentFile.getItemId365(), PDF_EXTENTION.equals(attachmentFile.getFileExtension()) ? null:PDF_FORMAT), attachmentFile.getFileName()));
                    }
                }
            }
            // mail tự động ko cần mã OTP
            if(mailInfoDTO.getAddOTP() != null && mailInfoDTO.getAddOTP() == true && mailInfoDTO.getRequestDataId() != null){
                Optional<RequestData> requestData = this.requestDataRepository.findById(mailInfoDTO.getRequestDataId());
                mailInfoDTO.setContent(mailInfoDTO.getContent() + this.generateOTP(requestData.get(), signData));
            }

            this.sendMailCustomService.sendMail(files, mailInfoDTO);
            return new LoadedMessage(true);
        }catch (Exception e){
            log.error("{}", e);
            return new FailLoadMessage(false);
        }finally {
            for(File file : files){
                file.delete();
            }
        }
    }

    public IResponseMessage sendMailWithTemplate(MailInfoDTO mailInfoDTO, Long requestDataId) throws Exception {
        String url = this.urlMailSercice + this.urlSendMailWithTemplate;
        HttpMethod method = HttpMethod.POST;

        // lấy danh sách attachment file tương ứng với phiếu yêu cầu (là loại tài liệu chính và ko bị xóa)
        List<AttachmentFile> attachmentFiles = this.attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> {
            if(ele.getFileType() == null) return false;
            if(this.MA_TAI_LIEU_CHINH.equals(ele.getFileType().getFileTypeCode()) && !conditionUtils.checkTrueFalse(ele.getIsDelete())) return true;
            return false;
        }).collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        Field[] fields = mailInfoDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object data = field.get(mailInfoDTO);
            if (data != null)
                params.put(field.getName(), field.get(mailInfoDTO));
        }

        List<File> files = new ArrayList();
        if(attachmentFiles != null){
            attachmentFiles.forEach(ele -> {
                try {
                    files.add(this.uploadFile365CustomService.getFileInOffice365(ele, Paths.get(this.tempFolder)));
                } catch (Exception e) {log.error("{}", e);}
            });
        }

        MailServiceResposeDTO result;
        try {
            result = this.callAPIUtils.createRestTemplateMailService(url, null, files, method, params, MailServiceResposeDTO.class);
        } finally {
            files.forEach(ele -> ele.delete());
        }
        if(result.isState()) return new LoadedMessage(result.getData());
        else return new FailLoadMessage(result.getData());
    }

    public Boolean sendMailWithTemplate(MailInfoDTO mailInfoDTO, ArrayList<AttachmentFile> attachmentFiles) throws Exception {
        String url = this.urlMailSercice + this.urlSendMailWithTemplate;
        HttpMethod method = HttpMethod.POST;

        Map<String, Object> params = new HashMap<>();
        Field[] fields = mailInfoDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object data = field.get(mailInfoDTO);
            if (data != null)
                params.put(field.getName(), field.get(mailInfoDTO));
        }

        List<File> files = new ArrayList();
        if(attachmentFiles != null){
            attachmentFiles.forEach(ele -> {
                try {
                    files.add(this.uploadFile365CustomService.getFileInOffice365(ele, Paths.get(this.tempFolder)));
                } catch (Exception e) {log.error("{}", e);}
            });
        }

        MailServiceResposeDTO result;
        try {
            result = this.callAPIUtils.createRestTemplateMailService(url, null, files, method, params, MailServiceResposeDTO.class);
        } finally {
            files.forEach(ele -> ele.delete());
        }
        return result.isState();
    }

    // Utils \\
    /**
     * hàm thực hiện clone dữ liệu trong MultipartFile thành File
     * @param multipartFile: MultipartFile
     * @return  File tạm trên hệ thống
     * @throws IOException
     */
    private File getFileFromMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = new File(this.tempFolder);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(this.tempFolder + this.PATH_SEPARATOR + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

    /**
     *  Hàm thực hiện coppy dữ liệu từ INputStream sang file
     * @param inputStream: InputStream cần lấy dữ liệu
     * @return: File đã copy dữ liệu từ InputStream
     */
    private File inputStreamToFile(InputStream inputStream, String fileName) throws IOException {
        String[] names = fileName.split("\\.");
        if(!PDF_FORMAT.equals(names[names.length-1]))
            fileName = String.join("", Arrays.copyOfRange(names, 0, names.length-1)) + "." + PDF_EXTENTION;
        File tempFolder = new File(this.TEMP_FOLDER);
        if(!tempFolder.exists()) tempFolder.mkdirs();
        File file = new File((this.TEMP_FOLDER + this.PATH_SEPARATOR + fileName).replace("//", this.PATH_SEPARATOR));
        if(!file.exists()) file.createNewFile();
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }

    /**
     *
     * @param requestData
     * @return
     */
    private String generateOTP(RequestData requestData){
        Long otp_expired_day = 30L;
        try {
            otp_expired_day = Long.parseLong(this.OTP_EXPIRED_DAY);
        }catch (Exception e){log.error("{}", e); otp_expired_day = 30L;}
        Long otp_expired_hour = 30L;
        try {
            otp_expired_hour = Long.parseLong(this.OTP_EXPIRED_HOUR);
        }catch (Exception e){log.error("{}", e); otp_expired_hour = 30L;}
        Long otp_expired_minute = 30L;
        try {
            otp_expired_minute = Long.parseLong(this.OTP_EXPIRED_MINUTE);
        }catch (Exception e){log.error("{}", e); otp_expired_minute = 30L;}


        OTP otp = new OTP();
        // DuowngTora: 15/01/2023: bỏ đi do khi gửi mail cho khách hàng sẽ bị generate lại mã OTP -> ko vào được bằng OTP cũ
//        List<OTP> otps = otpRepository.findAllByRequestDataId(requestData.getId());
//        if(!otps.isEmpty()){
//            otps.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
//            otp = otps.stream().findFirst().get();
//        }
        // end DuowngTora: 15/01/2023
//        int random_int = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        String random_int = OTPUtils.generate(OTP_LENGTH);
        otp.createdDate(Instant.now());
        otp.setoTPCode(String.valueOf(random_int));
        otp.expiryDate(Instant.now().plus(otp_expired_day, ChronoUnit.DAYS).plus(otp_expired_hour, ChronoUnit.HOURS).plus(otp_expired_minute, ChronoUnit.MINUTES));
        otp.setIsDelete(false);
        otp.setIsActive(true);
        otp.setIsCustomerSign(false);
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            try {
                otp.setLink(OTP_LINK.replace("{{id}}", URLEncoder.encode(HashUtils.encrypt(requestData.getId().toString()), StandardCharsets.UTF_8.toString())));
            } catch (UnsupportedEncodingException e) {log.error("{}", e);}
        }else{
            otp.setLink(OTP_LINK.replace("{{id}}", requestData.getId().toString()));
        }
        otp.setNumberDownload(0L);
        otp.setNumberPrint(0L);
        otp.setNumberView(0L);
        otp.setStatus("Đã gửi");
        otp.modifiedate(Instant.now());
        otp.setRequestData(requestData);
        otp.setNumberFail(0L);
        otp.setNumberGenerate(otp.getNumberGenerate() != null ? otp.getNumberGenerate() : 0 + 1);
        this.otpRepository.save(otp);
        StringBuilder stringBuilder = new StringBuilder(HTML_CODE_OTP);
        String result = stringBuilder.toString();
        result = result.replace("{{OPT_CODE}}", String.valueOf(random_int));
        result = result.replace("{{REQUESTDATA_LINK}}", otp.getLink());
        result = result.replace("{{REQUEST_DATA_CODE}}", requestData.getRequestDataCode());

        return result;
    }

    private String generateOTP(RequestData requestData, SignData signData){
        Long otp_expired_day = 30L;
        try {
            otp_expired_day = Long.parseLong(this.OTP_EXPIRED_DAY);
        }catch (Exception e){log.error("{}", e); otp_expired_day = 30L;}
        Long otp_expired_hour = 30L;
        try {
            otp_expired_hour = Long.parseLong(this.OTP_EXPIRED_HOUR);
        }catch (Exception e){log.error("{}", e); otp_expired_hour = 30L;}
        Long otp_expired_minute = 30L;
        try {
            otp_expired_minute = Long.parseLong(this.OTP_EXPIRED_MINUTE);
        }catch (Exception e){log.error("{}", e); otp_expired_minute = 30L;}


        OTP otp = new OTP();
        // DuowngTora: 15/01/2023: bỏ đi do khi gửi mail cho khách hàng sẽ bị generate lại mã OTP -> ko vào được bằng OTP cũ
//        List<OTP> otps = otpRepository.findAllByRequestDataId(requestData.getId());
//        if(!otps.isEmpty()){
//            otps.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
//            otp = otps.stream().findFirst().get();
//        }
        // end DuowngTora: 15/01/2023
//        int random_int = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        String random_int = OTPUtils.generate(OTP_LENGTH);
        while(random_int.equals(otp.getoTPCode())){
            random_int = OTPUtils.generate(OTP_LENGTH);
        }

        otp.createdDate(Instant.now());
        otp.setoTPCode(String.valueOf(random_int));
        otp.expiryDate(Instant.now().plus(otp_expired_day, ChronoUnit.DAYS).plus(otp_expired_hour, ChronoUnit.HOURS).plus(otp_expired_minute, ChronoUnit.MINUTES));
        otp.setIsDelete(false);
        otp.setIsActive(true);
        otp.setIsCustomerSign(false);
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            try {
                otp.setLink(OTP_LINK.replace("{{id}}", URLEncoder.encode(HashUtils.encrypt(requestData.getId().toString()), StandardCharsets.UTF_8.toString())));
            } catch (UnsupportedEncodingException e) {log.error("{}", e);}
        }else{
            otp.setLink(OTP_LINK.replace("{{id}}", requestData.getId().toString()));
        }
        otp.setNumberDownload(0L);
        otp.setNumberPrint(0L);
        otp.setNumberView(0L);
        otp.setStatus("Đã gửi");
        otp.modifiedate(Instant.now());
        otp.setRequestData(requestData);
        otp.setSignData(signData);
        otp.setNumberFail(0L);
        otp.setNumberGenerate(otp.getNumberGenerate() != null ? otp.getNumberGenerate() : 0 + 1);
        this.otpRepository.save(otp);
        StringBuilder stringBuilder = new StringBuilder(HTML_CODE_OTP);
        String result = stringBuilder.toString();
        result = result.replace("{{OPT_CODE}}", String.valueOf(random_int));
        result = result.replace("{{REQUESTDATA_LINK}}", otp.getLink());
        result = result.replace("{{REQUEST_DATA_CODE}}", requestData.getRequestDataCode());

        return result;
    }
}
