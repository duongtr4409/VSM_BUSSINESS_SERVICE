package com.vsm.business.service.custom.sign;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.Sign.Itext7.ItextSignService_v2;
import com.vsm.business.domain.*;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.repository.UserInfoRepository;
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
public class SoftSign {

    public final Logger log = LoggerFactory.getLogger(SoftSign.class);

    @Value("${system.qr-code.link:https://vcr.mobifone.ai/phieu-yeu-cau/chi-tiet/{{id}}}")
    public String QRCODE_LINK;

    private final String PDF_FORMAT = "pdf";
    @Value("${system.folder.SIGN_TEMP_FOLDER:/temp/signfile/}")
    public String FOLDER_SIGNFILE;
    @Value("${system.folder.SIGN_TEMP_FOLDER:./temp/signfile/temp}")
    private String FOLDER_RESULT_TEMP;
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();


    public final RequestDataRepository requestDataRepository;
    public final UserInfoRepository userInfoRepository;
    public final AttachmentFileRepository attachmentFileRepository;
    public final SignDataRepository signDataRepository;
    public final UploadFile365CustomService uploadFile365CustomService;

    public final ItextSignService_v2 itextSignService;

    public final GraphService graphService;

    private final SignUtils signUtils;

    private final com.vsm.business.utils.FileUtils fileUtils;

    private final PDFUtils pdfUtils;

    public SoftSign(RequestDataRepository requestDataRepository, UserInfoRepository userInfoRepository, AttachmentFileRepository attachmentFileRepository, UploadFile365CustomService uploadFile365CustomService, ItextSignService_v2 itextSignService, GraphService graphService, SignUtils signUtils, SignDataRepository signDataRepository, com.vsm.business.utils.FileUtils fileUtils, PDFUtils pdfUtils) {
        this.requestDataRepository = requestDataRepository;
        this.userInfoRepository = userInfoRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.itextSignService = itextSignService;
        this.graphService = graphService;
        this.signUtils = signUtils;
        this.signDataRepository = signDataRepository;
        this.fileUtils = fileUtils;
        this.pdfUtils = pdfUtils;
    }

    public boolean signOne(SignDTO signDTO){
        Boolean result = false;;
        if(signDTO.getRequestDataList() != null){
            for(Long requestDataId : signDTO.getRequestDataList()){
                result = signOne(signDTO.getPassword(), signDTO.getReason(), signDTO.getLocation(), signDTO.getSymbol(), signDTO.getUserId(), requestDataId);
                if(result){
                    writeSignHis(requestDataId, signDTO);
                    // ký thành công -> ẩn file tài liệu chính đã có file ký
                    this.signUtils.hideFilePrimary(requestDataId);
                }
            }
        }
        return result;
    }

    /**
     * Hàm thực hiện lấy dữ liệu (lấy các file tài liệu chính của phiếu yêu cầu) để gọi service ký mềm và thực hiện update file đã ký lên Office 365(nếu là file pdf), tạo file mới dạng pdf kèm chữ ký lên Office 365(nếu không phải là pdf)
     * @param password          : password xác thực để xử dụng file pfx(file thông tin chữ ký)
     * @param reason            : lý do ký
     * @param location          : địa điểm ký
     * @param symbol            : ký tự trong văn bản sẽ thay đổi thành chữ ký
     * @param userId            : id của người thực hiện ký
     * @param requestDataId     : id của phiếu yêu cầu lấy tài liệu ký
     * @return                  : true nếu ký thành công, false nếu ký thất bại
     */
    public boolean signOne(String password, String reason, String location, String symbol,Long userId, Long requestDataId){
        UserInfo userInfo = userInfoRepository.findById(userId).get();
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        List<AttachmentFile> attachmentFileList =  requestData.getAttachmentFiles().stream().collect(Collectors.toList());
        if(attachmentFileList == null || attachmentFileList.isEmpty()) return false;
        attachmentFileList = attachmentFileList.stream().filter(ele -> {
            return ele.getTemplateForm() != null;           // tài liệu chính thì có template form
        }).collect(Collectors.toList());
        if(attachmentFileList == null || attachmentFileList.isEmpty()) return false;

        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_RESULT_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if(!folderTemp.exists()) folderTemp.mkdirs();
        try{

            List<String> listFileName = attachmentFileList.stream().map(ele -> ele.getFileName()).collect(Collectors.toList());

            for(AttachmentFile ele : requestData.getAttachmentFiles()){
                if(ele.getTemplateForm() == null) continue;
                if(signUtils.checkHasFileSign(ele, attachmentFileList)) continue;        // TH đã có file ký rồi -> bỏ qua
                if(signUtils.checkFileViewCustomer(ele)) continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký

                File fileTemp = null;
                InputStream inputStream = null;
                InputStream inputStreamRead = null;
                try {
                    inputStream = graphService.getFile(ele.getItemId365(),  ele.getFileExtension().equals(PDF_FORMAT) ? null : PDF_FORMAT);
                    inputStreamRead = graphService.getFile(ele.getItemId365(),  ele.getFileExtension().equals(PDF_FORMAT) ? null : PDF_FORMAT);
                    // thêm QRCode xong mới ký
                    if(Strings.isNullOrEmpty(ele.getSignOfFile())) {
                        String link = QRCODE_LINK.replace("{{id}}", String.valueOf(requestDataId));
                        String requestDataCode = ele.getRequestData() != null ? ele.getRequestData().getRequestDataCode() : "";
                        String status = ele.getRequestData() != null ? ele.getRequestData().getStatus().getStatusName() : "";
                        inputStream = this.pdfUtils.addQRCode(inputStream, link, requestDataCode, status);
                        inputStreamRead = this.pdfUtils.addQRCode(inputStreamRead, link, requestDataCode, status);
                    }

                    // tạo file tạm để lưu file sau khi đã ký xong
                    String[] tempNameArray = ele.getFileName().split("\\.");
                    String fileNameTemp = String.join("", Arrays.copyOfRange(tempNameArray, 0, tempNameArray.length-1)) + "." + PDF_FORMAT;
                    fileNameTemp = fileUtils.checkFileName(fileNameTemp);
                    fileTemp = new File(pathTemp + PATH_SEPARATOR + fileNameTemp);
                    if(!fileTemp.exists()) fileTemp.createNewFile();

                    FileOutputStream fileOutputStream = itextSignService.sign(fileTemp, inputStream, inputStreamRead, password, userInfo.getFullName(), reason, location, "SignData", symbol, userInfo.getSignFolder());
                    if(ele.getFileExtension().equals(PDF_FORMAT)){      // nếu là file pdf -> đè lên file cũ
                        if(!Strings.isNullOrEmpty(ele.getSignOfFile())){
                            DriveItem driveItem = graphService.updateFile(ele.getItemId365(), Files.readAllBytes(fileTemp.toPath()));
                            ele.setFileSize(driveItem.size);
                            ele.setModifiedDate(Instant.now());
                            ele.setModified(userInfo);
                            ele.setModifiedName(userInfo.getFullName());

//                        ele.setSignOfFile(ele.getFileName() + "_" + ele.getId());

                            attachmentFileRepository.save(ele);
                        }else{
                            String[] names = ele.getFileName().split("\\.");
                            names = Arrays.copyOfRange(names, 0, names.length-1);
                            String newFileName = String.join("", names) + "_sign." + PDF_FORMAT;
                            newFileName = this.signUtils.getFileName(listFileName, newFileName);
                            LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), Files.readAllBytes(fileTemp.toPath()));
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
                    }else{      // nếu không phải là file pdf -> tạo file mới
                        String[] names = ele.getFileName().split("\\.");
                        names = Arrays.copyOfRange(names, 0, names.length-1);
                        String newFileName = String.join("", names) + "." + PDF_FORMAT;
                        newFileName = this.signUtils.getFileName(listFileName, newFileName);
                        LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), Files.readAllBytes(fileTemp.toPath()));
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
                } catch (Exception e) {log.error("{}", e);throw new RuntimeException(e);}
                finally {
                    inputStream.close();
                    inputStreamRead.close();
                    if(fileTemp != null) fileTemp.delete();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            folderTemp.delete();
            try {FileUtils.deleteDirectory(folderTemp);} catch (IOException e) {log.error("{}", e);}
        }
        return true;
    }

    public List<SignResponseDTO> sign(SignDTO signDTO){
        List<SignResponseDTO> result = new ArrayList<>();
        if(signDTO.getRequestDataList() != null){
            for(Long requestDataId : signDTO.getRequestDataList()){
                SignResponseDTO signResponseDTO = new SignResponseDTO();
                signResponseDTO.setRequestDataId(requestDataId);
                if(!checkKYSO(signDTO, requestDataId)){
                    signResponseDTO.setResultSign(false);
                }else{
                    signResponseDTO.setResultSign(sign(signDTO.getPassword(), signDTO.getReason(), signDTO.getLocation(), signDTO.getSymbol(), signDTO.getUserId(), requestDataId));
                }

                result.add(signResponseDTO);

                if(signResponseDTO.isResultSign()){     // nếu kí thành công -> ghi lịch sử
                    writeSignHis(requestDataId, signDTO);
                    // ký thành công -> ẩn file tài liệu chính đã có file ký
                    this.signUtils.hideFilePrimary(requestDataId);
                }
            }
        }
        return result;
    }

    /**
     * Hàm thực hiện lấy dữ liệu (lấy các file tài liệu chính của phiếu yêu cầu) để gọi service ký mềm và thực hiện update file đã ký lên Office 365(nếu là file pdf), tạo file mới dạng pdf kèm chữ ký lên Office 365(nếu không phải là pdf)
     * @param password          : password xác thực để xử dụng file pfx(file thông tin chữ ký)
     * @param reason            : lý do ký
     * @param location          : địa điểm ký
     * @param symbol            : ký tự trong văn bản sẽ thay đổi thành chữ ký
     * @param userId            : id của người thực hiện ký
     * @param requestDataId     : id của phiếu yêu cầu lấy tài liệu ký
     * @return                  : true nếu ký thành công, false nếu ký thất bại
     */
    public boolean sign(String password, String reason, String location, String symbol,Long userId, Long requestDataId){
        UserInfo userInfo = userInfoRepository.findById(userId).get();
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        List<AttachmentFile> attachmentFileList =  requestData.getAttachmentFiles().stream().collect(Collectors.toList());
        if(attachmentFileList == null || attachmentFileList.isEmpty()) return false;
        attachmentFileList = attachmentFileList.stream().filter(ele -> {
            return ele.getTemplateForm() != null;           // tài liệu chính thì có template form
        }).collect(Collectors.toList());
        if(attachmentFileList == null || attachmentFileList.isEmpty()) return false;

            String timeString = String.valueOf(System.currentTimeMillis());
            String pathTemp = FOLDER_RESULT_TEMP + timeString;
            pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
            File folderTemp = new File(pathTemp);
            if(!folderTemp.exists()) folderTemp.mkdirs();
        try{

            List<String> listFileName = attachmentFileList.stream().map(ele -> ele.getFileName()).collect(Collectors.toList());

            for(AttachmentFile ele : requestData.getAttachmentFiles()){
                if(ele.getTemplateForm() == null) continue;
                if(signUtils.checkHasFileSign(ele, attachmentFileList)) continue;        // TH đã có file ký rồi -> bỏ qua
                if(signUtils.checkFileViewCustomer(ele)) continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký

                try {
                    InputStream inputStream = graphService.getFile(ele.getItemId365(),  ele.getFileExtension().equals(PDF_FORMAT) ? null : PDF_FORMAT);
                    InputStream inputStreamRead = graphService.getFile(ele.getItemId365(),  ele.getFileExtension().equals(PDF_FORMAT) ? null : PDF_FORMAT);
                    // tạo file tạm để lưu file sau khi đã ký xong
                    String[] tempNameArray = ele.getFileName().split("\\.");
                    String fileNameTemp = String.join("", Arrays.copyOfRange(tempNameArray, 0, tempNameArray.length-1)) + "." + PDF_FORMAT;
                    String filenameTempTemp = this.fileUtils.checkFileName(fileNameTemp);
                    File fileTemp = new File(pathTemp + PATH_SEPARATOR + filenameTempTemp);
                    if(!fileTemp.exists()) fileTemp.createNewFile();

                    FileOutputStream fileOutputStream = itextSignService.sign(fileTemp, inputStream, inputStreamRead, password, userInfo.getFullName(), reason, location, "DuowngTora", symbol, userInfo.getSignFolder());
                    if(ele.getFileExtension().equals(PDF_FORMAT)){      // nếu là file pdf -> đè lên file cũ
                        if(!Strings.isNullOrEmpty(ele.getSignOfFile())){
                            DriveItem driveItem = graphService.updateFile(ele.getItemId365(), Files.readAllBytes(fileTemp.toPath()));
                            ele.setFileSize(driveItem.size);
                            ele.setModifiedDate(Instant.now());
                            ele.setModified(userInfo);
                            ele.setModifiedName(userInfo.getFullName());

//                        ele.setSignOfFile(ele.getFileName() + "_" + ele.getId());

                            attachmentFileRepository.save(ele);
                        }else{
                            String[] names = ele.getFileName().split("\\.");
                            names = Arrays.copyOfRange(names, 0, names.length-1);
                            String newFileName = String.join("", names) + "_sign." + PDF_FORMAT;
                            newFileName = this.signUtils.getFileName(listFileName, newFileName);
                            LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), Files.readAllBytes(fileTemp.toPath()));
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
                    }else{      // nếu không phải là file pdf -> tạo file mới
                        String[] names = ele.getFileName().split("\\.");
                        names = Arrays.copyOfRange(names, 0, names.length-1);
                        String newFileName = String.join("", names) + "." + PDF_FORMAT;
                        newFileName = this.signUtils.getFileName(listFileName, newFileName);
                        LargeFileUploadResult<DriveItem> uploadResult = graphService.createFile(newFileName, requestData.getIdDirectoryPath(), Files.readAllBytes(fileTemp.toPath()));
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
                } catch (Exception e) {throw new RuntimeException(e);}
            }
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }finally {
            folderTemp.delete();
            try {FileUtils.deleteDirectory(folderTemp);} catch (IOException e) {log.error("{}", e);}
        }
        return true;
    }

    private AttachmentFile addUserInFo(AttachmentFile attachmentFile, UserInfo userInfo){
        attachmentFile.setCreatedDate(Instant.now());
        attachmentFile.setModifiedDate(Instant.now());
        if(userInfo != null){
            attachmentFile.setCreated(userInfo);
            attachmentFile.setCreatedName(userInfo.getFullName());
            attachmentFile.setModified(userInfo);
            attachmentFile.setModifiedName(userInfo.getFullName());
            attachmentFile.setCreatedOrgName(userInfo.getOrganizations().stream().findFirst().orElse(new Organization()).getOrganizationName());
            attachmentFile.setCreatedRankName(userInfo.getRanks().stream().findFirst().orElse(new Rank()).getRankName());
        }
        return attachmentFile;
    }

    public List<String> getAllSoftSign(){
        File folderSignFile = new File(FOLDER_SIGNFILE);
        if(folderSignFile.exists()){
            return Arrays.stream(folderSignFile.list()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Hàm thực hiện kiểm tra xem phiếu có được phép ký số hay không
     * @return
     */
    private boolean checkKYSO(SignDTO signDTO, Long requestDataId){
        try {
            RequestData requestData = requestDataRepository.findById(requestDataId).get();
            UserInfo userInfo = userInfoRepository.findById(signDTO.getUserId()).get();
            return signUtils.checkCondition(requestData, userInfo);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
    }


    /**
     * Hàm thực hiện ghi lịch sửa ký
     */
    private void writeSignHis(Long requestDataId, SignDTO signDTO){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = userInfoRepository.findById(signDTO.getUserId()).orElse(null);
        if(userInfo == null) {
            userInfo = new UserInfo();
            try {
                SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
                userInfo.setFullName(signData.getSignName());
            }catch (Exception e){
                log.error("{}", e);
            }
        }
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

    private AttachmentFile resetOneToManyValue(AttachmentFile attachmentFile){
        if(attachmentFile != null){
            attachmentFile.setAttachmentPermisitions(new HashSet<>());
            attachmentFile.setChangeFileHistories(new HashSet<>());
        }
        return attachmentFile;
    }
}
