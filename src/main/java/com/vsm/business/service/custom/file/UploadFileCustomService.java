package com.vsm.business.service.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.TemplateForm;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.custom.file.bo.*;
import com.vsm.business.service.custom.file.bo.response.BaseClientRp;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.CallAPIUtils;
import com.vsm.business.utils.PDFUtils;
import org.apache.commons.io.FilenameUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Instant;
import java.util.*;

@Service
public class UploadFileCustomService {

    @Value("${fileServer.url.baseUrl}")
    private String urlFileSercice;

    @Value("${fileServer.url.file.uploadFile:/file/uploadFile}")
    private String urlUploadFile;

    @Value("${fileServer.url.file.downloadFile:/file/download-file/}")
    private String urlDownloadFile;

    @Value("${fileServer.url.file.deleteFile:/file/delete-file/}")
    private String urlDeleteFile;

    @Value("${fileServer.url.file.restoreFile:/file/undo-delete-file/}")
    private String urlRestoreFile;

    @Value("${fileServer.url.file.renameFile:/file/change-fileName/}")
    private String urlRenameFile;

    @Value("${fileServer.url.file.detailFile:/file/detail-file/}")
    private String urlDetailFile;

    @Value("${fileServer.url.file.copyFile:/file/copy-file}")
    private String urlCopyFile;

    @Value("${fileServer.url.file.moveFile:/file/move-file}")
    private String urlMoveFile;

    @Value("${fileServer.url.file.updateFile:/file/update-file/}")
    private String urlUpdateFile;

    @Value("${temp.upload.folder}")
    private String tempFolder;

    @Autowired
    private CallAPIUtils callAPIUtils;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Autowired
    private FolderCustomService folderCustomService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String ID_FOLDER_TEMPLATE = "";             // id của folder biểu mẫu
    private String NAME_FOLDER_TEMPLATE = "Biểu Mẫu";

    private String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    public AttachmentFileDTO upLoadFile(MultipartFile multipartFile, Long templateFormId, UploadOption option) throws Exception {
        String urlUploadFile = this.urlFileSercice + this.urlUploadFile;

        UserInfo created = null;
        if(option.getCreatedId() != null)
            this.userInfoRepository.findById(option.getCreatedId()).orElse(null);

        // TH không truyền parentID lên -> tìm kiếm xem có folder biểu mẫu trên hệ thống chưa nếu chưa có thì tạo mới rồi gắn làm parentId
            if(Strings.isNullOrEmpty(option.getParentId())){
               if(this.ID_FOLDER_TEMPLATE == null || this.ID_FOLDER_TEMPLATE.isEmpty()){
                   List<AttachmentFile> BIEU_MAU_FOLDER = this.attachmentFileRepository.findAllByFileName(this.NAME_FOLDER_TEMPLATE);
                   if(BIEU_MAU_FOLDER != null && BIEU_MAU_FOLDER.size() > 0){
                       this.ID_FOLDER_TEMPLATE = BIEU_MAU_FOLDER.get(0).getIdInFileService();
                   }
                   else{
                       CreateFolderOption createFolderOption = new CreateFolderOption();
                       createFolderOption.setFileName(this.NAME_FOLDER_TEMPLATE);
                       createFolderOption.setCreatedId(option.getCreatedId());
                       createFolderOption.setIsFolder(1);
                       BaseClientRp baseClientRp = this.folderCustomService.createFolder_v1(createFolderOption);
                       if(baseClientRp.getState()){
                           this.ID_FOLDER_TEMPLATE = this.objectMapper.convertValue(baseClientRp.getData(), AttachDocument.class).getId();
                       }
                   }
                }
                option.setParentId(this.ID_FOLDER_TEMPLATE);
            }
        //

        Map<String, Object> params = new HashMap<>();
        Field[] fields = option.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object data = field.get(option);
            if(data != null)
                params.put(field.getName(), field.get(option));
        }
        File fileUpload = getFileFromMultipartFile(multipartFile);
        if(params.get("contentType") == null)
            params.put("contentType", Files.probeContentType(fileUpload.toPath()));
        try {
            AttachDocument resultAttachDocument = callAPIUtils.createRestTemplateUploadFile(urlUploadFile, null, fileUpload ,HttpMethod.POST, params, AttachDocument.class);
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setIdInFileService(resultAttachDocument.getId());
            attachmentFile.setFileName(multipartFile.getOriginalFilename());
            attachmentFile.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            attachmentFile.setPath(resultAttachDocument.getFilePath());
            attachmentFile.setOfice365Path("");
            attachmentFile.setDescription(new ObjectMapper().writeValueAsString(resultAttachDocument));
            attachmentFile.setCreatedName(created != null ? created.getFullName() : null);
            attachmentFile.setCreated(created);
            attachmentFile.setCreatedDate(Instant.now());
            attachmentFile.setModifiedName(created != null ? created.getFullName() : null);
            attachmentFile.setModified(created);
            attachmentFile.setModifiedDate(Instant.now());
            attachmentFile.setIsActive(true);

            List<AttachmentFile> attachmentFileParent = this.attachmentFileRepository.findAllByIdInFileService(option.getParentId());
            if(attachmentFileParent != null && attachmentFileParent.size() > 0){
                attachmentFile.setParentId(attachmentFileParent.get(0).getId());
            }

            if(templateFormId != null)
                attachmentFile.setTemplateForm(new TemplateForm().id(templateFormId));
            else
                this.attachmentFileRepository.save(attachmentFile);
            return attachmentFileMapper.toDto(attachmentFile);
        }catch (Exception e){
            throw  e;
        }finally {
            fileUpload.delete();
        }
    }

    public AttachmentFileDTO upLoadFile_v1(MultipartFile multipartFile, Long templateFormId, UploadOption option) throws Exception {
        String urlUploadFile = this.urlFileSercice + this.urlUploadFile;

        UserInfo created = null;
        if(option.getCreatedId() != null)
            this.userInfoRepository.findById(option.getCreatedId()).orElse(null);

        // TH không truyền parentID lên -> tìm kiếm xem có folder biểu mẫu trên hệ thống chưa nếu chưa có thì tạo mới rồi gắn làm parentId
        if(Strings.isNullOrEmpty(option.getParentId())){
            if(this.ID_FOLDER_TEMPLATE == null || this.ID_FOLDER_TEMPLATE.isEmpty()){
                List<AttachmentFile> BIEU_MAU_FOLDER = this.attachmentFileRepository.findAllByFileName(this.NAME_FOLDER_TEMPLATE);
                if(BIEU_MAU_FOLDER != null && BIEU_MAU_FOLDER.size() > 0){
                    this.ID_FOLDER_TEMPLATE = BIEU_MAU_FOLDER.get(0).getIdInFileService();
                }
                else{
                    CreateFolderOption createFolderOption = new CreateFolderOption();
                    createFolderOption.setFileName(this.NAME_FOLDER_TEMPLATE);
                    createFolderOption.setCreatedId(option.getCreatedId());
                    createFolderOption.setIsFolder(1);
                    BaseClientRp baseClientRp = this.folderCustomService.createFolder_v1(createFolderOption);
                    if(baseClientRp.getState()){
                        this.ID_FOLDER_TEMPLATE = this.objectMapper.convertValue(baseClientRp.getData(), AttachDocument.class).getId();
                    }
                }
            }
            option.setParentId(this.ID_FOLDER_TEMPLATE);
        }
        //

        Map<String, Object> params = new HashMap<>();
        Field[] fields = option.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object data = field.get(option);
            if(data != null)
                params.put(field.getName(), field.get(option));
        }
        File fileUpload = getFileFromMultipartFile(multipartFile);
        if(params.get("contentType") == null)
            params.put("contentType", Files.probeContentType(fileUpload.toPath()));
        try {
            AttachDocument resultAttachDocument = callAPIUtils.createRestTemplateUploadFile(urlUploadFile, null, fileUpload ,HttpMethod.POST, params, AttachDocument.class);
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setIdInFileService(resultAttachDocument.getId());
            attachmentFile.setFileName(multipartFile.getOriginalFilename());
            attachmentFile.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            attachmentFile.setPath(resultAttachDocument.getFilePath());
            attachmentFile.setOfice365Path("");
            attachmentFile.setDescription(new ObjectMapper().writeValueAsString(resultAttachDocument));
            attachmentFile.setCreatedName(created != null ? created.getFullName() : null);
            attachmentFile.setCreated(created);
            attachmentFile.setCreatedDate(Instant.now());
            attachmentFile.setModifiedName(created != null ? created.getFullName() : null);
            attachmentFile.setModified(created);
            attachmentFile.setModifiedDate(Instant.now());
            attachmentFile.setIsActive(true);

            List<AttachmentFile> attachmentFileParent = this.attachmentFileRepository.findAllByIdInFileService(option.getParentId());
            if(attachmentFileParent != null && attachmentFileParent.size() > 0){
                attachmentFile.setParentId(attachmentFileParent.get(0).getId());
            }

            if(templateFormId != null)
                attachmentFile.setTemplateForm(new TemplateForm().id(templateFormId));
            else
                this.attachmentFileRepository.save(attachmentFile);
            return attachmentFileMapper.toDto(attachmentFile);
        }catch (Exception e){
            throw  e;
        }finally {
            fileUpload.delete();
        }
    }

//    public byte[] downloadFile_v1(AttachmentFileDTO attachmentFileDTO, DownloadOption downloadOption) throws Exception {
//        AttachmentFile attachmentFile = attachmentFileRepository.findById(attachmentFileDTO.getId()).orElse(null);
//        if(attachmentFile != null){
//            String urlDownloadFile = this.urlFileSercice + this.urlDownloadFile + "/" + attachmentFile.getIdInFileService();
//            Map<String, Object> params = this.objectToParams(downloadOption);
//            byte[] result = callAPIUtils.createRestTemplateDownloadFile(urlDownloadFile, downloadOption, HttpMethod.GET, params, byte[].class);
//            return result;
//        }
//        return new byte[1];
//    }

    public void downloadFile(HttpServletResponse response, String fileNameId, DownloadOption downloadOption) throws Exception {
        List<AttachmentFile> attachmentFileList = attachmentFileRepository.findAllByIdInFileService(fileNameId);
        AttachmentFile attachmentFile = (attachmentFileList != null && !attachmentFileList.isEmpty()) ? attachmentFileList.get(0) : null;
        if(attachmentFile != null){
            String urlDownloadFile = this.urlFileSercice + this.urlDownloadFile + "/" + attachmentFile.getIdInFileService();
            Map<String, Object> params = this.objectToParams(downloadOption);
            ResponseEntity<byte[]> result = callAPIUtils.createRestTemplateDownloadFile(urlDownloadFile, downloadOption, HttpMethod.GET, params, byte[].class);
            response.setContentType(result.getHeaders().getContentType().toString());
            response.getOutputStream().write(result.getBody());
        }
    }

    public void downloadFile_v1(HttpServletResponse response, String fileNameId, DownloadOption downloadOption) throws Exception {
        List<AttachmentFile> attachmentFileList = attachmentFileRepository.findAllByIdInFileService(fileNameId);
        AttachmentFile attachmentFile = (attachmentFileList != null && !attachmentFileList.isEmpty()) ? attachmentFileList.get(0) : null;
        if(attachmentFile != null){
            String urlDownloadFile = this.urlFileSercice + this.urlDownloadFile + "/" + attachmentFile.getIdInFileService();
            Map<String, Object> params = this.objectToParams(downloadOption);
            ResponseEntity<byte[]> result = callAPIUtils.createRestTemplateDownloadFile(urlDownloadFile, downloadOption, HttpMethod.GET, params, byte[].class);
            response.setContentType(result.getHeaders().getContentType().toString());
            String fileName = attachmentFile.getFileName().split("\\.").length > 1 ? attachmentFile.getFileName() : attachmentFile.getFileName() + "." + attachmentFile.getFileExtension();
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName +"\"");
            response.getOutputStream().write(result.getBody());
        }
    }

    public BaseClientRp deleteFile(String id) throws Exception {
        String url = this.urlFileSercice + this.urlDeleteFile + id;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFile(url, null, HttpMethod.PUT, null, BaseClientRp.class);
        List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(id);
        if(attachmentFileList != null && attachmentFileList.size() > 0){
            attachmentFileList.get(0).setIsActive(false);
            this.attachmentFileRepository.save(attachmentFileList.get(0));
        }
        return result;
    }

    public BaseClientRp restoreFile(String id) throws Exception {
        String url = this.urlFileSercice + this.urlRestoreFile + id;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFile(url, null, HttpMethod.PUT, null, BaseClientRp.class);
        return result;
    }

    public BaseClientRp renameFile(String id, String fileName) throws Exception {
        String url = this.urlFileSercice + this.urlRenameFile + id;
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", fileName);
        BaseClientRp result = this.callAPIUtils.createRestTemplateFile(url, null, HttpMethod.PUT, params, BaseClientRp.class);
        return result;
    }

    public AttachDocument detailFile(String id) throws Exception {
        String url = this.urlFileSercice + this.urlDetailFile + id;
        AttachDocument result = this.callAPIUtils.createRestTemplateFile(url, null, HttpMethod.GET, null, AttachDocument.class);
        return result;
    }

    public AttachmentFileDTO copyFile(FileRq fileRq) throws Exception {
        AttachmentFile attachmentFile = attachmentFileRepository.findAllByIdInFileService(fileRq.getFileId()).get(0);
        String url = this.urlFileSercice + this.urlCopyFile;
        BaseClientRp resultBaseClientRp = this.callAPIUtils.createRestTemplateFile(url, fileRq, HttpMethod.POST, null, BaseClientRp.class);
        AttachDocument resultAttachDocument = new ObjectMapper().convertValue(resultBaseClientRp.getData(), AttachDocument.class);
        attachmentFile.setIdInFileService(resultAttachDocument.getId());
        attachmentFile.setFileName(attachmentFile.getFileName());
        attachmentFile.setPath(resultAttachDocument.getFilePath());
        attachmentFile.setDescription(new ObjectMapper().writeValueAsString(resultAttachDocument));
        attachmentFile.setCreatedDate(Instant.now());
        attachmentFile.setModifiedDate(Instant.now());
        attachmentFile.setIsActive(true);
        return attachmentFileMapper.toDto(attachmentFile);
    }

    public BaseClientRp copyFile_v1(FileRq fileRq) throws Exception {
        AttachmentFile attachmentFile = attachmentFileRepository.findAllByIdInFileService(fileRq.getFileId()).get(0);
        String url = this.urlFileSercice + this.urlCopyFile;
        BaseClientRp resultBaseClientRp = this.callAPIUtils.createRestTemplateFile(url, fileRq, HttpMethod.POST, null, BaseClientRp.class);
        return resultBaseClientRp;
    }

    public BaseClientRp moveFile(FileRq fileRq) throws Exception {
        String url = this.urlFileSercice + this.urlMoveFile;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFile(url, fileRq, HttpMethod.POST, null, BaseClientRp.class);
        return result;
    }

    public AttachmentFileDTO updateFile(String id, String fileName, MultipartFile multipartFile) throws Exception {
        String urlUploadFile = this.urlFileSercice + this.urlUpdateFile + id;
        Map<String, Object> params = new HashMap<>();
        File fileUpload = getFileFromMultipartFile(multipartFile);
            params.put("fileName", fileName);
        try {
            List<AttachmentFile> listAttachmentFile = this.attachmentFileRepository.findAllByIdInFileService(id);
            if(listAttachmentFile.size() != 1) throw new Exception("Error findAllByIdInFileService != 1 record: " + listAttachmentFile.size());
            AttachmentFile attachmentFile = listAttachmentFile.get(0);
            BaseClientRp resultBaseClientRp = callAPIUtils.createRestTemplateUploadFile(urlUploadFile, null, fileUpload ,HttpMethod.PUT, params, BaseClientRp.class);
            if(!resultBaseClientRp.getState()) throw new Exception("Error in FileService: " + resultBaseClientRp.getMessage());
            AttachDocument resultAttachDocument = new ObjectMapper().convertValue(resultBaseClientRp.getData(), AttachDocument.class);
            attachmentFile.setIdInFileService(resultAttachDocument.getId());
            attachmentFile.setFileName(multipartFile.getOriginalFilename());
            attachmentFile.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            attachmentFile.setPath(resultAttachDocument.getFilePath());
            attachmentFile.setOfice365Path("");
            attachmentFile.setDescription(new ObjectMapper().writeValueAsString(resultAttachDocument));
            attachmentFile.setModifiedDate(Instant.now());
            attachmentFile.setIsActive(true);
            return attachmentFileMapper.toDto(attachmentFile);
        }catch (Exception e){
            throw  e;
        }finally {
            fileUpload.delete();
        }
    }

    private File getFileFromMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = new File(this.tempFolder);
        if(!file.exists()){
            file.mkdir();
        }
        file = new File(this.tempFolder + this.PATH_SEPARATOR + System.currentTimeMillis() + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

    private Map<String, Object> objectToParams(Object object) throws IllegalAccessException {
        Map<String, Object> params = new HashMap<>();
        if(object == null) return params;
        for(Field field : object.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object data = field.get(object);
            if(data != null){
                params.put(field.getName(), data);
            }
        }
        return params;
    }

    @Autowired
    private PDFUtils qrCodeUtils;
    public void getFileWithQRCode(HttpServletResponse response, String fileNameId, DownloadOption downloadOption) throws Exception {
        List<AttachmentFile> attachmentFileList = attachmentFileRepository.findAllByIdInFileService(fileNameId);
        AttachmentFile attachmentFile = (attachmentFileList != null && !attachmentFileList.isEmpty()) ? attachmentFileList.get(0) : null;
        if(attachmentFile != null){
            String urlDownloadFile = this.urlFileSercice + this.urlDownloadFile + "/" + attachmentFile.getIdInFileService();
            Map<String, Object> params = this.objectToParams(downloadOption);
            ResponseEntity<byte[]> result = callAPIUtils.createRestTemplateDownloadFile(urlDownloadFile, downloadOption, HttpMethod.GET, params, byte[].class);
            response.setContentType(result.getHeaders().getContentType().toString());
            String fileName = attachmentFile.getFileName().split("\\.").length > 1 ? attachmentFile.getFileName() : attachmentFile.getFileName() + "." + attachmentFile.getFileExtension();

            File fileTemp = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);
            fileOutputStream.write(result.getBody());
            fileOutputStream.flush();
            fileOutputStream.close();
            File fileResult = this.qrCodeUtils.addQRCode(fileTemp, "https://eoffice.2bsystem.com.vn/phieu-yeu-cau/22203/chi-tiet");

            response.setHeader("Content-disposition", "attachment; filename=\"" + fileResult.getName() +"\"");
            response.getOutputStream().write(Files.readAllBytes(fileResult.getAbsoluteFile().toPath()));

            fileTemp.deleteOnExit();
        }
    }

    public byte[] getByteOfFile(String fileNameId, DownloadOption downloadOption) throws Exception {
        List<AttachmentFile> attachmentFileList = attachmentFileRepository.findAllByIdInFileService(fileNameId);
        AttachmentFile attachmentFile = (attachmentFileList != null && !attachmentFileList.isEmpty()) ? attachmentFileList.get(0) : null;
        if(attachmentFile != null){
            String urlDownloadFile = this.urlFileSercice + this.urlDownloadFile + "/" + attachmentFile.getIdInFileService();
            if(downloadOption.getType() == null) downloadOption.setType("DuowngTora");
            Map<String, Object> params = this.objectToParams(downloadOption);
            ResponseEntity<byte[]> result = callAPIUtils.createRestTemplateDownloadFile(urlDownloadFile, downloadOption, HttpMethod.GET, params, byte[].class);
            return result.getBody();
        }
        return new byte[0];
    }

    public InputStream getFileContentWithOption(InputStream source, DownloadOption downloadOption) throws DocumentException, IOException {
        InputStream result = source;
        if(downloadOption.getAddQrCode()) result = this.qrCodeUtils.addQRCode(source, "https://eoffice.2bsystem.com.vn/phieu-yeu-cau/22203/chi-tiet", "", "");
        return result;
    }
}
