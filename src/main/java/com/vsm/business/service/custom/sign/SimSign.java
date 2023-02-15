package com.vsm.business.service.custom.sign;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.Sign.Itext7.ItextSignService_v2;
import com.vsm.business.common.Sign.sim.MobiSignService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.sign.bo.SignDTO;
import com.vsm.business.service.custom.sign.bo.SignResponseDTO;
import com.vsm.business.service.custom.sign.utils.SignUtils;
import com.vsm.business.utils.PDFUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimSign {

    public final Logger log = LoggerFactory.getLogger(SoftSign.class);

    @Value("${system.qr-code.link:https://vcr.mobifone.ai/phieu-yeu-cau/chi-tiet/{{id}}}")
    public String QRCODE_LINK;

    @Value("${system.folder.SIGN_TEMP_FOLDER:./temp/signfile/}")
    public String FOLDER_SIGNFILE;
    @Value("${system.folder.SIGN_TEMP_FOLDER:./temp/signfile/temp}")
    private String FOLDER_RESULT_TEMP;
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    private final String PDF_FORMAT = "pdf";

    private final String PDF_EXTENSION = "pdf";


    public final RequestDataRepository requestDataRepository;
    public final UserInfoRepository userInfoRepository;
    public final AttachmentFileRepository attachmentFileRepository;
    public final SignDataRepository signDataRepository;
    public final UploadFile365CustomService uploadFile365CustomService;
    public final GraphService graphService;
    public final OTPRepository otpRepository;

    public final MobiSignService mobiSignService;

    public final SignUtils signUtils;

    public final com.vsm.business.utils.FileUtils fileUtils;

    public final PDFUtils pdfUtils;

    public SimSign(RequestDataRepository requestDataRepository, UserInfoRepository userInfoRepository, AttachmentFileRepository attachmentFileRepository, UploadFile365CustomService uploadFile365CustomService, GraphService graphService, MobiSignService mobiSignService, SignUtils signUtils, SignDataRepository signDataRepository, com.vsm.business.utils.FileUtils fileUtils, PDFUtils pdfUtils, OTPRepository otpRepository) {
        this.requestDataRepository = requestDataRepository;
        this.userInfoRepository = userInfoRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.graphService = graphService;
        this.mobiSignService = mobiSignService;
        this.signUtils = signUtils;
        this.signDataRepository = signDataRepository;
        this.fileUtils = fileUtils;
        this.pdfUtils = pdfUtils;
        this.otpRepository = otpRepository;
    }


    public boolean signOne(SignDTO signDTO) throws Exception {
        if (signDTO.getRequestDataList() != null) {
            for (Long requestDataId : signDTO.getRequestDataList()) {
                if (!this.signOne(signDTO.getMsisdn(), signDTO.getPrompt(), signDTO.getReason(), signDTO.getUserId(), requestDataId)) {
                    return false;
                }
                writeSignHis(requestDataId, signDTO);
                // ký thành công -> ẩn file tài liệu chính đã có file ký
                this.signUtils.hideFilePrimary(requestDataId);
            }
        }
        return true;
    }

    public boolean anonymousSignOne(SignDTO signDTO, String otpCode) throws Exception {
        Boolean result = true;
        if (signDTO.getRequestDataList() != null) {
            for (Long requestDataId : signDTO.getRequestDataList()) {
                result = this.signOne(signDTO.getMsisdn(), signDTO.getPrompt(), signDTO.getReason(), requestDataId);

                if (result) {     // nếu kí thành công -> ghi lịch sử
                    // ký thành công -> ẩn file tài liệu chính đã có file ký
                    this.signUtils.hideFilePrimary(requestDataId);

                    // ký thành công -> cập nhật thông tin ký của khách hàng: sửa lại để chỉ cập nhật thông tin ký của signData tương ứng với mã OTP (20230131: nếu có truyền OTP còn ko thì như cũ )
                    if(otpCode != null){
                        OTP otp = this.otpRepository.customGetAllByRequestDataIdAndOTPCode(requestDataId, otpCode).stream().filter(ele -> {
                            return (ele.getIsActive() != null && ele.getIsActive() == true) && (ele.getIsDelete() == null || ele.getIsDelete() == false);
                        }).findFirst().get();
                        SignData signData = this.signDataRepository.findById(otp.getSignData().getId()).get();
                        signData.setNumberSign(signData.getNumberSign() + 1);
                        this.signDataRepository.save(signData);
                        writeSignHisAnonymous(requestDataId, signDTO, true, signData);
                    }else{
                        List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestDataId);
                        for (SignData signData: signDataList){
                            signData.setNumberSign(signData.getNumberSign() + 1);
                        }
                        signDataRepository.saveAll(signDataList);
                        writeSignHis(requestDataId, signDTO, true);
                    }

                }

            }
        }
        return result;
    }

    private boolean signOne(String msisdn, String prompt, String reason, Long requestDataId) throws Exception {
        if (Strings.isNullOrEmpty(prompt)) prompt = "Eoffice: " + reason;
        else if ("Eoffice".equalsIgnoreCase(prompt)) prompt += ": " + reason;

        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;
        attachmentFileList = attachmentFileList.stream().filter(ele -> {        // lấy danh sách tài liệu chính
            return ele.getTemplateForm() != null;
        }).collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;

        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_RESULT_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if (!folderTemp.exists()) folderTemp.mkdirs();

        try {

            List<String> listFileName = attachmentFileList.stream().map(ele -> ele.getFileName()).collect(Collectors.toList());

            for (AttachmentFile ele : attachmentFileList) {
                if (signUtils.checkHasFileSign(ele, attachmentFileList))
                    continue;        // TH đã có file ký rồi -> bỏ qua
                if (signUtils.checkFileViewCustomer(ele))
                    continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký

                InputStream inputStream = this.graphService.getFile(ele.getItemId365(), (PDF_EXTENSION.equals(ele.getFileExtension())) ? null : PDF_FORMAT);
                // thêm QRCode xong mới ký
                if(Strings.isNullOrEmpty(ele.getSignOfFile())){
                    String link = QRCODE_LINK.replace("{{id}}", String.valueOf(requestDataId));
                    String requestDataCode = ele.getRequestData() != null ? ele.getRequestData().getRequestDataCode() : "";
                    String status = ele.getRequestData() != null ? ele.getRequestData().getStatus().getStatusName() : "";
                    inputStream = this.pdfUtils.addQRCode(inputStream, link, requestDataCode, status);
                }

                String[] names = ele.getFileName().split("\\.");
                names = Arrays.copyOfRange(names, 0, names.length - 1);
                String newFileName = String.join("", names) + "." + PDF_FORMAT;
                String fileNameTemp = this.fileUtils.checkFileName(newFileName);
                File fileTemp = new File(folderTemp.getAbsolutePath() + this.PATH_SEPARATOR + fileNameTemp);
                if (!fileTemp.exists()) fileTemp.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                FileInputStream fileInputStream = new FileInputStream(new File(fileTemp.getAbsolutePath()));

                byte[] resultSign = this.mobiSignService.sign(fileInputStream, msisdn, prompt, reason);

                if (PDF_EXTENSION.equals(ele.getFileExtension())) {      // nếu là file pdf -> update lại file cũ trên Office 365
                    if(!Strings.isNullOrEmpty(ele.getSignOfFile())){
                        DriveItem driveItem = this.graphService.updateFile(ele.getItemId365(), resultSign);
                        ele.setFileSize(driveItem.size);
                        ele.setModifiedDate(Instant.now());

    //                    ele.setSignOfFile(ele.getFileName() + "_" + ele.getId());

                        attachmentFileRepository.save(ele);
                    }else{
                        newFileName = String.join("", names) + "_sign." + PDF_FORMAT;
                        newFileName = this.signUtils.getFileName(listFileName, newFileName);
                        LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                        AttachmentFile attachmentFile = new AttachmentFile();
                        BeanUtils.copyProperties(ele, attachmentFile);

                        attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                        attachmentFile.setId(null);
                        attachmentFile.setItemId365(uploadResult.responseBody.id);
                        attachmentFile.setFileName(newFileName);
                        attachmentFile.setParentId(ele.getParentId());
                        attachmentFile.setFileExtension(PDF_FORMAT);
                        attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                        attachmentFile.setFileSize(uploadResult.responseBody.size);
                        attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                        attachmentFile.setIsActive(true);
                        attachmentFile.setIsFolder(false);
                        attachmentFile.setIsDelete(false);
                        attachmentFile = resetOneToManyValue(attachmentFile);
                        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                        this.attachmentFileRepository.save(attachmentFile);
                    }
                } else {
//                String[] names = ele.getFileName().split("\\.");
//                names = Arrays.copyOfRange(names, 0, names.length-1);
//                String newFileName = String.join("", names) + "." + PDF_FORMAT;
                    LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                    AttachmentFile attachmentFile = new AttachmentFile();
                    BeanUtils.copyProperties(ele, attachmentFile);

                    attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                    attachmentFile.setId(null);
                    attachmentFile.setItemId365(uploadResult.responseBody.id);
                    attachmentFile.setFileName(newFileName);
                    attachmentFile.setParentId(ele.getParentId());
                    attachmentFile.setFileExtension(PDF_FORMAT);
                    attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                    attachmentFile.setFileSize(uploadResult.responseBody.size);
                    attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                    attachmentFile.setIsActive(true);
                    attachmentFile.setIsFolder(false);
                    attachmentFile.setIsDelete(false);
                    attachmentFile = resetOneToManyValue(attachmentFile);
                    //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                    this.attachmentFileRepository.save(attachmentFile);
                }
            }
        } catch (Exception e) {
            log.debug("{}", e);
            return false;
        } finally {
            folderTemp.delete();
            try {
                FileUtils.deleteDirectory(folderTemp);
            } catch (IOException e) {
                log.error("{}", e);
            }
        }

        return true;
    }

    private boolean signOne(String msisdn, String prompt, String reason, Long userId, Long requestDataId) throws Exception {
        if (Strings.isNullOrEmpty(prompt)) prompt = "Eoffice: " + reason;
        else if ("Eoffice".equalsIgnoreCase(prompt)) prompt += ": " + reason;

        UserInfo userInfo = userInfoRepository.findById(userId).get();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;
        attachmentFileList = attachmentFileList.stream().filter(ele -> {        // lấy danh sách tài liệu chính
            return ele.getTemplateForm() != null;
        }).collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;

        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_RESULT_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if (!folderTemp.exists()) folderTemp.mkdirs();

        try {

            List<String> listFileName = attachmentFileList.stream().map(ele -> ele.getFileName()).collect(Collectors.toList());

            for (AttachmentFile ele : attachmentFileList) {

                if (signUtils.checkHasFileSign(ele, attachmentFileList))
                    continue;        // TH đã có file ký rồi -> bỏ qua
                if (signUtils.checkFileViewCustomer(ele))
                    continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký

                InputStream inputStream = this.graphService.getFile(ele.getItemId365(), (PDF_EXTENSION.equals(ele.getFileExtension())) ? null : PDF_FORMAT);
                // thêm QRCode xong mới ký
                if(Strings.isNullOrEmpty(ele.getSignOfFile())){
                    String link = QRCODE_LINK.replace("{{id}}", String.valueOf(requestDataId));
                    String requestDataCode = ele.getRequestData() != null ? ele.getRequestData().getRequestDataCode() : "";
                    String status = ele.getRequestData() != null ? ele.getRequestData().getStatus().getStatusName() : "";
                    inputStream = this.pdfUtils.addQRCode(inputStream, link, requestDataCode, status);
                }

                String[] names = ele.getFileName().split("\\.");
                names = Arrays.copyOfRange(names, 0, names.length - 1);
                String newFileName = String.join("", names) + "." + PDF_FORMAT;
                String fileNameTemp = this.fileUtils.checkFileName(newFileName);
                File fileTemp = new File(folderTemp.getAbsolutePath() + this.PATH_SEPARATOR + fileNameTemp);
                if (!fileTemp.exists()) fileTemp.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                FileInputStream fileInputStream = new FileInputStream(fileTemp.getAbsolutePath());

                byte[] resultSign = this.mobiSignService.sign(fileInputStream, msisdn, prompt, reason);

                if (PDF_EXTENSION.equals(ele.getFileExtension())) {      // nếu là file pdf -> update lại file cũ trên Office 365
                    if(!Strings.isNullOrEmpty(ele.getSignOfFile())){
                        DriveItem driveItem = this.graphService.updateFile(ele.getItemId365(), resultSign);
                        ele.setFileSize(driveItem.size);
                        if (userInfo != null) {
                            ele.setModifiedName(userInfo.getFullName());
                            ele.setModified(userInfo);
                        }
                        ele.setModifiedDate(Instant.now());

    //                    ele.setSignOfFile(ele.getFileName() + "_" + ele.getId());

                        attachmentFileRepository.save(ele);
                    }else{
                        newFileName = String.join("", names) + "_sign." + PDF_FORMAT;
                        newFileName = this.signUtils.getFileName(listFileName, newFileName);
                        LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                        AttachmentFile attachmentFile = new AttachmentFile();
                        BeanUtils.copyProperties(ele, attachmentFile);

                        attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                        attachmentFile.setId(null);
                        attachmentFile.setItemId365(uploadResult.responseBody.id);
                        attachmentFile.setFileName(newFileName);
                        attachmentFile.setParentId(ele.getParentId());
                        attachmentFile.setFileExtension(PDF_FORMAT);
                        attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                        attachmentFile.setFileSize(uploadResult.responseBody.size);
                        attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                        attachmentFile.setIsActive(true);
                        attachmentFile.setIsFolder(false);
                        attachmentFile.setIsDelete(false);
                        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                        addUserInFo(attachmentFile, userInfo);
                        attachmentFile = resetOneToManyValue(attachmentFile);

                        this.attachmentFileRepository.save(attachmentFile);
                    }

                } else {

//                String[] names = ele.getFileName().split("\\.");
//                names = Arrays.copyOfRange(names, 0, names.length-1);
//                String newFileName = String.join("", names) + "." + PDF_FORMAT;
                    newFileName = this.signUtils.getFileName(listFileName, newFileName);
                    LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                    AttachmentFile attachmentFile = new AttachmentFile();
                    BeanUtils.copyProperties(ele, attachmentFile);

                    attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                    attachmentFile.setId(null);
                    attachmentFile.setItemId365(uploadResult.responseBody.id);
                    attachmentFile.setFileName(newFileName);
                    attachmentFile.setParentId(ele.getParentId());
                    attachmentFile.setFileExtension(PDF_FORMAT);
                    attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                    attachmentFile.setFileSize(uploadResult.responseBody.size);
                    attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                    attachmentFile.setIsActive(true);
                    attachmentFile.setIsFolder(false);
                    attachmentFile.setIsDelete(false);
                    //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                    addUserInFo(attachmentFile, userInfo);
                    attachmentFile = resetOneToManyValue(attachmentFile);

                    this.attachmentFileRepository.save(attachmentFile);
                }

            }
            return true;
        } catch (Exception e) {
            log.debug("{}", e);
            return false;
        } finally {
            folderTemp.delete();
            try {
                FileUtils.deleteDirectory(folderTemp);
            } catch (IOException e) {
                log.error("{}", e);
            }
        }
    }


    public List<SignResponseDTO> sign(SignDTO signDTO) throws Exception {
        List<SignResponseDTO> result = new ArrayList<>();
        if (signDTO.getRequestDataList() != null) {
            for (Long requestDataId : signDTO.getRequestDataList()) {
                SignResponseDTO signResponseDTO = new SignResponseDTO();
                signResponseDTO.setRequestDataId(requestDataId);
                if (!checkKYSO(signDTO, requestDataId)) {
                    signResponseDTO.setResultSign(false);
                } else {
                    signResponseDTO.setResultSign(this.sign(signDTO.getMsisdn(), signDTO.getPrompt(), signDTO.getReason(), signDTO.getUserId(), requestDataId));
                }

                result.add(signResponseDTO);

                if (signResponseDTO.isResultSign()) {     // nếu kí thành công -> ghi lịch sử
                    writeSignHis(requestDataId, signDTO);
                    // ký thành công -> ẩn file tài liệu chính đã có file ký
                    this.signUtils.hideFilePrimary(requestDataId);
                }

            }
        }
        return result;
    }

    private boolean sign(String msisdn, String prompt, String reason, Long userId, Long requestDataId) throws Exception {
        if (Strings.isNullOrEmpty(prompt)) prompt = "Eoffice: " + reason;
        else if ("Eoffice".equalsIgnoreCase(prompt)) prompt += ": " + reason;

        UserInfo userInfo = userInfoRepository.findById(userId).get();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;
        attachmentFileList = attachmentFileList.stream().filter(ele -> {        // lấy danh sách tài liệu chính
            return ele.getTemplateForm() != null;
        }).collect(Collectors.toList());
        if (attachmentFileList == null || attachmentFileList.isEmpty()) return false;

        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_RESULT_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if (!folderTemp.exists()) folderTemp.mkdirs();

        try {

            List<String> listFileName = attachmentFileList.stream().map(ele -> ele.getFileName()).collect(Collectors.toList());

            for(AttachmentFile ele : attachmentFileList){

                if(signUtils.checkHasFileSign(ele, attachmentFileList)) continue;        // TH đã có file ký rồi -> bỏ qua
                if(signUtils.checkFileViewCustomer(ele)) continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký

                InputStream inputStream = this.graphService.getFile(ele.getItemId365(), (PDF_EXTENSION.equals(ele.getFileExtension())) ? null : PDF_FORMAT);
                // thêm QRCode xong mới ký
                if(Strings.isNullOrEmpty(ele.getSignOfFile())){
                    String link = QRCODE_LINK.replace("{{id}}", String.valueOf(requestDataId));
                    String requestDataCode = ele.getRequestData() != null ? ele.getRequestData().getRequestDataCode() : "";
                    String status = ele.getRequestData() != null ? ele.getRequestData().getStatus().getStatusName() : "";
                    inputStream = this.pdfUtils.addQRCode(inputStream, link, requestDataCode, status);
                }

                String[] names = ele.getFileName().split("\\.");
                names = Arrays.copyOfRange(names, 0, names.length - 1);
                String newFileName = String.join("", names) + "_Đã Ký" + "." + PDF_FORMAT;
                String fileNameTemp = this.fileUtils.checkFileName(newFileName);
                File fileTemp = new File(folderTemp.getAbsolutePath() + this.PATH_SEPARATOR + fileNameTemp);
                if (!fileTemp.exists()) fileTemp.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                FileInputStream fileInputStream = new FileInputStream(new File(fileTemp.getAbsolutePath()));

                byte[] resultSign = this.mobiSignService.sign(fileInputStream, msisdn, prompt, reason);

                if (PDF_EXTENSION.equals(ele.getFileExtension())) {      // nếu là file pdf -> update lại file cũ trên Office 365
                    if(!Strings.isNullOrEmpty(ele.getSignOfFile())){
                        DriveItem driveItem = this.graphService.updateFile(ele.getItemId365(), resultSign);
                        ele.setFileSize(driveItem.size);
                        ele.setModifiedName(userInfo.getFullName());
                        ele.setModified(userInfo);
                        ele.setModifiedDate(Instant.now());

    //                    ele.setSignOfFile(ele.getFileName() + "_" + ele.getId());

                        attachmentFileRepository.save(ele);
                    }else {
                        newFileName = String.join("", names) + "_sign." + PDF_FORMAT;
                        newFileName = this.signUtils.getFileName(listFileName, newFileName);
                        LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                        AttachmentFile attachmentFile = new AttachmentFile();
                        BeanUtils.copyProperties(ele, attachmentFile);

                        attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                        attachmentFile.setId(null);
                        attachmentFile.setItemId365(uploadResult.responseBody.id);
                        attachmentFile.setFileName(newFileName);
                        attachmentFile.setParentId(ele.getParentId());
                        attachmentFile.setFileExtension(PDF_FORMAT);
                        attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                        attachmentFile.setFileSize(uploadResult.responseBody.size);
                        attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                        attachmentFile.setIsActive(true);
                        attachmentFile.setIsFolder(false);
                        attachmentFile.setIsDelete(false);
                        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                        addUserInFo(attachmentFile, userInfo);
                        attachmentFile = resetOneToManyValue(attachmentFile);
                        this.attachmentFileRepository.save(attachmentFile);
                    }
                } else {
//                String[] names = ele.getFileName().split("\\.");
//                names = Arrays.copyOfRange(names, 0, names.length-1);
//                String newFileName = String.join("", names) + "." + PDF_FORMAT;
                    newFileName = this.signUtils.getFileName(listFileName, newFileName);
                    LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), resultSign);
                    AttachmentFile attachmentFile = new AttachmentFile();
                    BeanUtils.copyProperties(ele, attachmentFile);

                    attachmentFile.setSignOfFile(attachmentFile.getFileName() + "_" + attachmentFile.getId());      // đánh dấu file này đã tạo file ký rồi

                    attachmentFile.setId(null);
                    attachmentFile.setItemId365(uploadResult.responseBody.id);
                    attachmentFile.setFileName(newFileName);
                    attachmentFile.setParentId(ele.getParentId());
                    attachmentFile.setFileExtension(PDF_FORMAT);
                    attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
                    attachmentFile.setFileSize(uploadResult.responseBody.size);
                    attachmentFile.setContentType(uploadResult.responseBody.oDataType);
                    attachmentFile.setIsActive(true);
                    attachmentFile.setIsFolder(false);
                    attachmentFile.setIsDelete(false);
                    //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                    addUserInFo(attachmentFile, userInfo);
                    attachmentFile = resetOneToManyValue(attachmentFile);
                    this.attachmentFileRepository.save(attachmentFile);
                }
            }
        } catch (Exception e) {
            log.debug("{}", e);
            return false;
        } finally {
            folderTemp.delete();
            try {
                FileUtils.deleteDirectory(folderTemp);
            } catch (IOException e) {
                log.error("{}", e);
            }
        }

        return true;
    }

    private AttachmentFile addUserInFo(AttachmentFile attachmentFile, UserInfo userInfo) {
        if (userInfo != null) {
            attachmentFile.setCreated(userInfo);
            attachmentFile.setCreatedName(userInfo.getFullName());
            attachmentFile.setCreatedDate(Instant.now());
            attachmentFile.setModified(userInfo);
            attachmentFile.setModifiedName(userInfo.getFullName());
            attachmentFile.setModifiedDate(Instant.now());
            attachmentFile.setCreatedOrgName(userInfo.getOrganizations().stream().findFirst().orElse(new Organization()).getOrganizationName());
            attachmentFile.setCreatedRankName(userInfo.getRanks().stream().findFirst().orElse(new Rank()).getRankName());
        }
        return attachmentFile;
    }

    /**
     * Hàm thực hiện kiểm tra xem phiếu có được phép ký số hay không
     *
     * @return
     */
    private boolean checkKYSO(SignDTO signDTO, Long requestDataId) {
        try {
            RequestData requestData = requestDataRepository.findById(requestDataId).get();
            UserInfo userInfo = userInfoRepository.findById(signDTO.getUserId()).get();
            return signUtils.checkCondition(requestData, userInfo);
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
    }

    /**
     * Hàm thực hiện ghi lịch sửa ký
     */
    private void writeSignHis(Long requestDataId, SignDTO signDTO) {
        UserInfo userInfo = new UserInfo();
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        if(signDTO.getUserId() != null) {
            userInfo = userInfoRepository.findById(signDTO.getUserId()).orElse(null);
            if (userInfo == null) {
                userInfo = new UserInfo();
                try {
                    SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
                    userInfo.setFullName(signData.getSignName());
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()) {
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    /**
     * Hàm thực hiện ghi lịch sửa ký
     */
    private void writeSignHis(Long requestDataId, SignDTO signDTO, boolean skipUser){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = null;
//        if(!skipUser){
//            userInfo = new UserInfo();
//            try {
//                SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
//                userInfo.setFullName(signData.getSignName());
//            }catch (Exception e){
//                log.info("{}", e);
//            }
//        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()){
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    private void writeSignHisAnonymous(Long requestDataId, SignDTO signDTO, boolean skipUser, SignData signData){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = null;
//        if(!skipUser){
//            userInfo = new UserInfo();
//            try {
//                SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
//                userInfo.setFullName(signData.getSignName());
//            }catch (Exception e){
//                log.info("{}", e);
//            }
//        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()){
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        if(signData != null) signType = (signData.getSignName() != null ? signData.getSignName().trim() + " " : "") + signType;
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    private AttachmentFile resetOneToManyValue(AttachmentFile attachmentFile){
        if(attachmentFile != null){
            attachmentFile.setAttachmentPermisitions(new HashSet<>());
            attachmentFile.setChangeFileHistories(new HashSet<>());
        }
        return attachmentFile;
    }
}
