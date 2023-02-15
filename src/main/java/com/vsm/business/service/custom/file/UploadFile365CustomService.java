package com.vsm.business.service.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.itextpdf.text.DocumentException;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.ItemPreviewInfo;
import com.microsoft.graph.models.Permission;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.AppConstant;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.AttachmentFileService;
import com.vsm.business.service.custom.AttachmentFileCustomService;
import com.vsm.business.service.custom.file.bo.Office365.*;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.PDFUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class UploadFile365CustomService {

    public Logger log = LoggerFactory.getLogger(UploadFile365CustomService.class);

    @Value("${graph-api.time-wait-copy:3000}")
    private String TIME_WAIT_COPY;


    private final String PDF_FORMAT = "pdf";
    private final String PDF_EXTENSION = "pdf";
    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private TemplateFormRepository templateFormRepository;

    @Autowired
    private RequestDataRepository requestDataRepository;

    @Autowired
    private StepProcessDocRepository stepProcessDocRepository;

    @Autowired
    private ReqdataProcessHisRepository reqdataProcessHisRepository;

    @Autowired
    private OfficialDispatchRepository officialDispatchRepository;

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AttachmentPermisitionRepository attachmentPermisitionRepository;

    @Autowired
    private PDFUtils pdfUtils;

    @Autowired
    private GraphService graphService;

    @Autowired
    private Folder365CustomService folder365CustomService;

    @Value("${temp.upload.folder}")
    private String tempFolder;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Long ID_FOLDER_TEMPLATE = null;             // id của folder biểu mẫu
    private String NAME_FOLDER_TEMPLATE = "Biểu Mẫu";

    //@Value("${system.folder.ROOT:ROOT}")
    public static String ROOT_FOLDER_NAME;

    @Autowired
    public Environment environment;

    @Autowired
    public AuthenticateUtils authenticateUtils;

    public static String ROOT_FOLDER_ITEM_ID = null;

    public AttachmentFile ROOT_FOLDER;
    public AttachmentFile getROOT_FOLDER() {
        return ROOT_FOLDER;
    }
    public void setROOT_FOLDER(AttachmentFile ROOT_FOLDER) {
        this.ROOT_FOLDER = ROOT_FOLDER;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ROOT_FOLDER() {
        try {
            String ROOT_FOLDER_NAME = this.environment.getProperty("system.folder.ROOT", "ROOT");
            if(ROOT_FOLDER_ITEM_ID == null || ROOT_FOLDER_ITEM_ID.isEmpty()){
                List<AttachmentFile> root_folder_list = this.attachmentFileRepository.findAllByFileName(ROOT_FOLDER_NAME);
                AttachmentFile root_folder = root_folder_list != null && !root_folder_list.isEmpty() ? root_folder_list.get(0) : null;
                if(root_folder == null){        // nếu chưa có ROOT folder
                    CreateFolder365Option createFolder365Option = new CreateFolder365Option();
                    createFolder365Option.setFolderName(ROOT_FOLDER_NAME);
                    AttachmentFileDTO root_folder_create = this.folder365CustomService.createFolder(createFolder365Option);
                    ROOT_FOLDER_ITEM_ID = root_folder_create.getItemId365();
                    setROOT_FOLDER(this.attachmentFileMapper.toEntity(root_folder_create));
                }else{
                    ROOT_FOLDER_ITEM_ID = root_folder.getItemId365();
                    setROOT_FOLDER(root_folder);
                }
                log.info("ROOT_FOLDER: {}", getROOT_FOLDER());

                // cấu hình quyền cho ADMIN USER (tham số chỉnh trong config được)
                this.authenticateUtils.initRoleToAdminUser(getROOT_FOLDER());

            }
        }catch (Exception e){
            log.error("Error When create ROOT FOLDER: {}", e);
        }
        log.info("ROOT_FOLDER_ITEM_ID: {}", ROOT_FOLDER_ITEM_ID);
    }

    private String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    @Autowired
    private CacheManager cacheManager;
    @Transactional
    public AttachmentFileDTO uploadFile(MultipartFile multipartFile, Upload365Option uploadOption) throws Exception {
        if(uploadOption.getParentItemId() != null) uploadOption.setParentItemId(uploadOption.getParentItemId().replace("\"",""));
        UserInfo created = null;
        if(uploadOption.getUserId() != null)
            created = this.userInfoRepository.findById(uploadOption.getUserId()).orElse(null);

        // TH không truyền parentID, parentItemID và có templateFormId -> tìm kiếm xem có folder biểu mẫu trên hệ thống chưa nếu chưa có thì tạo mới rồi gắn làm parentId
        if(uploadOption.getParentId() == null && Strings.isNullOrEmpty(uploadOption.getParentItemId()) && uploadOption.getTemplateFormId() != null){
            if(this.ID_FOLDER_TEMPLATE == null){
                List<AttachmentFile> BIEU_MAU_FOLDER = this.attachmentFileRepository.findAllByFileName(this.NAME_FOLDER_TEMPLATE).stream().filter(ele -> ele.getParentId() == null).collect(Collectors.toList());
                if(BIEU_MAU_FOLDER != null && BIEU_MAU_FOLDER.size() > 0){
                    this.ID_FOLDER_TEMPLATE = BIEU_MAU_FOLDER.get(0).getId();
                }
                else{
                    CreateFolder365Option createFolder365Option = new CreateFolder365Option();
                    createFolder365Option.setFolderName(this.NAME_FOLDER_TEMPLATE);
                    createFolder365Option.setCreatedId(uploadOption.getUserId());
                    AttachmentFileDTO folderTemplateForm = this.folder365CustomService.createFolder(createFolder365Option);
                    this.ID_FOLDER_TEMPLATE = folderTemplateForm.getId();
                    uploadOption.setParentItemId(folderTemplateForm.getItemId365());
                }
                    // Th là biểu mẫu -> xóa hết các file biểu mẫu cũ đi \\
                List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(uploadOption.getTemplateFormId(), null);
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    attachmentFileList.forEach(ele -> {
                        try {
                            deleteFile(ele.getId());
                        }catch (Exception e){log.error("{}", e);}
                    });
                }
            }
            uploadOption.setParentId(this.ID_FOLDER_TEMPLATE);
            // Tránh TH Bị Trùng tên file biểu mẫu -> tạm thời thêm thời gian vào để tránh trùng
            String names[] = multipartFile.getOriginalFilename().split("\\.");
            String fileExtension = names[names.length - 1];
            String fileName = String.join("", Arrays.copyOfRange(names, 0, names.length - 1)) +  + System.currentTimeMillis() + "." + fileExtension;
            uploadOption.setFileName(fileName);

            // nếu là upload cho biểu mẫu -> clear cache
            try {
                cacheManager.getCache("/template-form/{templateFormId}/_all/attachment-files").clear();
            }catch (Exception e){log.error("{}",e);}
        }
        //

        // Th có truyền parentId lên
        if(uploadOption.getParentId() != null){
            AttachmentFile parentFolder = this.attachmentFileRepository.findById(uploadOption.getParentId()).orElse(new AttachmentFile());
            uploadOption.setParentItemId(parentFolder.getItemId365());
        }else if( uploadOption.getParentItemId() != null && !"".equals(uploadOption.getParentItemId().trim())){
            AttachmentFile parentFolder = this.attachmentFileRepository.findAllByItemId365(uploadOption.getParentItemId()).get(0);
            uploadOption.setParentId(parentFolder.getId());
        }else if( uploadOption.getRequestDataId() != null){
            RequestData requestData = this.requestDataRepository.findById(uploadOption.getRequestDataId()).orElse(null);
            if(requestData != null){
                AttachmentFile parentFolder;
                try {
                    parentFolder = this.attachmentFileRepository.findAllByItemId365(uploadOption.getParentItemId()).get(0);
                }catch (Exception e){
                    log.error("{}", e);
                    parentFolder = this.attachmentFileRepository.findAllByItemId365(requestData.getIdDirectoryPath()).get(0);
                }
                uploadOption.setParentId(parentFolder.getId());
                uploadOption.setParentItemId(parentFolder.getItemId365());
            }
        }

        LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFile((Strings.isNullOrEmpty(uploadOption.getFileName()) ? multipartFile.getOriginalFilename() : uploadOption.getFileName()), uploadOption.getParentItemId(), multipartFile.getBytes());
        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setItemId365(uploadResult.responseBody.id);
        attachmentFile.setPathInHandBook(uploadOption.getPathInHandBook());
        attachmentFile.setFileName( (Strings.isNullOrEmpty(uploadOption.getFileName()) ? multipartFile.getOriginalFilename() : uploadOption.getFileName()));
        attachmentFile.setParentId(uploadOption.getParentId());
        attachmentFile.setFileExtension(FilenameUtils.getExtension((Strings.isNullOrEmpty(uploadOption.getFileName()) ? multipartFile.getOriginalFilename() : uploadOption.getFileName())));
        attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
        attachmentFile.setFileSize(uploadResult.responseBody.size);
        attachmentFile.setContentType(uploadResult.responseBody.oDataType);
        attachmentFile.setIsActive(true);
        attachmentFile.setIsFolder(false);
        attachmentFile.setIsDelete(false);
        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
        addUserInFo(attachmentFile, created);
        attachmentFile.setCreatedDate(Instant.now());
        attachmentFile.setModifiedDate(Instant.now());
        if(uploadOption.getTemplateFormId() != null)
            attachmentFile.setTemplateForm(this.templateFormRepository.findById(uploadOption.getTemplateFormId()).orElse(null));
        if(uploadOption.getRequestDataId() != null)
            attachmentFile.setRequestData(this.requestDataRepository.findById(uploadOption.getRequestDataId()).orElse(null));
        if(uploadOption.getFileTypeId() != null){
            FileType fileType = this.fileTypeRepository.findById(uploadOption.getFileTypeId()).orElse(null);
            attachmentFile.setFileType(fileType);
            attachmentFile.setFileTypeName(fileType == null ? null : fileType.getFileTypeName());
            attachmentFile.setFileTypeCode(fileType == null ? null : fileType.getFileTypeCode());
        }
        if(uploadOption.getStepProcessId() != null)
            attachmentFile.setStepProcessDoc(this.stepProcessDocRepository.findById(uploadOption.getStepProcessId()).orElse(null));
        if(uploadOption.getRequestProcessHisId() != null)
            attachmentFile.setReqdataProcessHis(this.reqdataProcessHisRepository.findById(uploadOption.getRequestProcessHisId()).orElse(null));
        if(uploadOption.getOfficialDispatchId() != null){
            attachmentFile.setOfficialDispatch(this.officialDispatchRepository.findById(uploadOption.getOfficialDispatchId()).orElse(null));
        }
//        if(uploadOption.getIncomingDocId() != null)
//            attachmentFile.setIncomingDoc(this.incomingDocRepository.findById(uploadOption.getIncomingDocId()).orElse(null));
//        if(uploadOption.getOutgoingDocId() != null)
//            attachmentFile.setOutgoingDoc(this.outgoingDocRepository.findById(uploadOption.getOutgoingDocId()).orElse(null));

        attachmentFile = this.attachmentFileRepository.save(attachmentFile);
        return this.attachmentFileMapper.toDto(attachmentFile);
    }

    @Transactional
    public Boolean deleteFile(Long id){
//        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
//        attachmentFile.setIsDelete(true);
//        this.attachmentFileRepository.save(attachmentFile);
            // sửa lại xóa thẳng đi ko sửa cờ IsDelete nữa
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
        this.graphService.deleteItem(attachmentFile.getItemId365());
        this.attachmentFileRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Boolean restoreFile(Long id){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
        attachmentFile.setIsDelete(false);
        this.attachmentFileRepository.save(attachmentFile);
        return true;
    }

    public Boolean renameFile(Long id, String fileName){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
        attachmentFile.setFileName(fileName);
        attachmentFile.setFileExtension(FilenameUtils.getExtension(fileName));
        this.graphService.reNameItem(attachmentFile.getItemId365(), fileName);
        this.attachmentFileRepository.save(attachmentFile);
        return true;
    }

    public AttachmentFileDTO detailFile(Long id){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
        return this.attachmentFileMapper.toDto(attachmentFile);
    }

    @Transactional
    public boolean copyFile(Edit365Option edit365Option) throws IOException {
        UserInfo created = null;
        if(edit365Option.getUserId() != null)
            created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(edit365Option.getSourceId()).get();
        AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();

        String nameOfCopy = (edit365Option.getFileName() == null || edit365Option.getFileName().trim().isEmpty()) ? attachmentFile.getFileName() : edit365Option.getFileName();
        DriveItem copyResult = this.graphService.copyFile(attachmentFile.getItemId365(), folderTarget.getItemId365(), nameOfCopy);


        AttachmentFile attachmentFileNew = new AttachmentFile();
        BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
        attachmentFileNew.setId(null);
        attachmentFileNew.setParentId(edit365Option.getFolderTargetId());
        attachmentFileNew.setFileName(nameOfCopy);
        attachmentFileNew.setItemId365(copyResult.id);
        attachmentFileNew.setOfice365Path(copyResult.webUrl);
        attachmentFileNew.setAttachmentPermisitions(null);
        attachmentFileNew.setChangeFileHistories(null);
        attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
        addUserInFo(attachmentFile, created);

        for(AttachmentPermisition ele : attachmentFile.getAttachmentPermisitions()){
            AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
            BeanUtils.copyProperties(ele, attachmentPermisition);
            attachmentPermisition.setId(null);
            attachmentPermisition.setAttachmentFile(attachmentFileNew);
            attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
            attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
        }
        return true;
    }

    @Transactional
    public AttachmentFile cloneFileWithMono(Edit365Option edit365Option, RequestData requestData, UserInfo created, AttachmentFile sourceAttachmentFile, String itemId365FolderTarget, String nameOfCopy) throws IOException {
        DriveItem copyResult = this.graphService.copyFile(sourceAttachmentFile.getItemId365(), itemId365FolderTarget, nameOfCopy);

        AttachmentFile attachmentFileNew = new AttachmentFile();
        BeanUtils.copyProperties(sourceAttachmentFile, attachmentFileNew);
        attachmentFileNew.setId(null);
        attachmentFileNew.setParentId(edit365Option.getFolderTargetId());
        attachmentFileNew.setFileName(nameOfCopy);
        attachmentFileNew.setItemId365(copyResult.id);
        attachmentFileNew.setOfice365Path(copyResult.webUrl);
        attachmentFileNew.setAttachmentPermisitions(null);
        attachmentFileNew.setChangeFileHistories(null);
        attachmentFileNew.setRequestData(requestData);
        attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
        addUserInFo(sourceAttachmentFile, created);

        for(AttachmentPermisition ele : sourceAttachmentFile.getAttachmentPermisitions()){
            AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
            BeanUtils.copyProperties(ele, attachmentPermisition);
            attachmentPermisition.setId(null);
            attachmentPermisition.setAttachmentFile(attachmentFileNew);
            attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
            attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
        }
        return attachmentFileNew;
    }

    @Transactional
    public AttachmentFile cloneFile(Edit365Option edit365Option, RequestData requestData) throws IOException {
        UserInfo created = null;
        if(edit365Option.getUserId() != null)
            created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(edit365Option.getSourceId()).get();
        AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();

        String nameOfCopy = (edit365Option.getFileName() == null || edit365Option.getFileName().trim().isEmpty()) ? attachmentFile.getFileName() : edit365Option.getFileName();
        String nameOfCopyInOffice365 = nameOfCopy.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        nameOfCopyInOffice365 = nameOfCopyInOffice365.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        DriveItem copyResult = this.graphService.copyFile(attachmentFile.getItemId365(), folderTarget.getItemId365(), nameOfCopyInOffice365);


        AttachmentFile attachmentFileNew = new AttachmentFile();
        BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
        attachmentFileNew.setId(null);
        attachmentFileNew.setParentId(edit365Option.getFolderTargetId());
        attachmentFileNew.setFileName(nameOfCopy);
        attachmentFileNew.setItemId365(copyResult.id);
        attachmentFileNew.setOfice365Path(copyResult.webUrl);
        attachmentFileNew.setAttachmentPermisitions(null);
        attachmentFileNew.setChangeFileHistories(null);
        attachmentFileNew.setRequestData(requestData);
        attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
        addUserInFo(attachmentFile, created);

        for(AttachmentPermisition ele : attachmentFile.getAttachmentPermisitions()){
            AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
            BeanUtils.copyProperties(ele, attachmentPermisition);
            attachmentPermisition.setId(null);
            attachmentPermisition.setAttachmentFile(attachmentFileNew);
            attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
            attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
        }
        return attachmentFileNew;
    }

    @Transactional
    public List<AttachmentFile> cloneListFile(List<Edit365Option> edit365OptionList, RequestData requestData) throws IOException {
        if(edit365OptionList != null && !edit365OptionList.isEmpty()){
            List<AttachmentFile> attachmentFileList = new ArrayList<>();
            for(Edit365Option edit365Option : edit365OptionList){
                UserInfo created = null;
                if(edit365Option.getUserId() != null)
                    created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);
                AttachmentFile attachmentFile = this.attachmentFileRepository.findById(edit365Option.getSourceId()).get();
                AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();

                String nameOfCopy = (edit365Option.getFileName() == null || edit365Option.getFileName().trim().isEmpty()) ? attachmentFile.getFileName() : edit365Option.getFileName();
                String nameOfCopyInOffice365 = nameOfCopy.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
                nameOfCopyInOffice365 = nameOfCopyInOffice365.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
                DriveItem copyResult = this.graphService.copyFileWaitResult(attachmentFile.getItemId365(), folderTarget.getItemId365(), nameOfCopyInOffice365);

                AttachmentFile attachmentFileNew = new AttachmentFile();
                BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
                attachmentFileNew.setId(null);
                attachmentFileNew.setParentId(edit365Option.getFolderTargetId());
                attachmentFileNew.setFileName(nameOfCopy);
                attachmentFileNew.setAttachmentPermisitions(null);
                attachmentFileNew.setChangeFileHistories(null);
                attachmentFileNew.setRequestData(requestData);
                attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
                addUserInFo(attachmentFile, created);

                // link get result coppy
                JsonElement graphResponseHeaders = copyResult.additionalDataManager().get("graphResponseHeaders");
                JsonElement location = graphResponseHeaders.getAsJsonObject().get("location");
                String linkResultCopy = location.getAsJsonArray().get(0).getAsString();
                attachmentFileNew.setUrlView(linkResultCopy);

                for(AttachmentPermisition ele : attachmentFile.getAttachmentPermisitions()){
                    AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
                    BeanUtils.copyProperties(ele, attachmentPermisition);
                    attachmentPermisition.setId(null);
                    attachmentPermisition.setAttachmentFile(attachmentFileNew);
                    attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
                    attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
                }
                attachmentFileList.add(attachmentFileNew);
            }

            // lấy kết quả và gắn quyền cho file \\
            try {Thread.sleep(Long.valueOf(TIME_WAIT_COPY));} catch (Exception e) {try {Thread.sleep(3000);} catch (Exception ex) {}}
            List<AttachmentFile> attachmentFileResult = new ArrayList<>();
            for(AttachmentFile attachmentFile : attachmentFileList){
                if(attachmentFile.getUrlView() != null){
                    try {

                        DriveItem resultCopyTemp = this.graphService.getResultCopyV3(attachmentFile.getUrlView());
                            // gán quyền \\
                        Permission permission = this.graphService.grantAccess(resultCopyTemp.id);
                        resultCopyTemp.webUrl = permission.link.webUrl;

                        attachmentFile.setItemId365(resultCopyTemp.id);
                        attachmentFile.setOfice365Path(resultCopyTemp.webUrl);
                        attachmentFile.setUrlView(null);
                        attachmentFile = this.attachmentFileRepository.save(attachmentFile);
                        attachmentFileResult.add(attachmentFile);
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
            }
            return attachmentFileResult;
        }else{
            return null;
        }
    }


    @Value("${executor.thread-pool:5}")
    private int THREAD_POOL;

//    @Transactional
    public void cloneListFile_v2(List<Edit365Option> edit365OptionList, RequestData requestData) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
        List<CompletableFuture<AttachmentFile>> completableFutureList = new ArrayList<>();
        try {

            if(edit365OptionList != null && !edit365OptionList.isEmpty()){
                List<AttachmentFile> attachmentFileList = new ArrayList<>();
                for(Edit365Option edit365Option : edit365OptionList){
                    CompletableFuture<AttachmentFile> completableFuture = CompletableFuture.supplyAsync(() -> {
                        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(edit365Option.getSourceId()).get();
                        AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();

                        UserInfo created = null;
                        if(edit365Option.getUserId() != null)
                            created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);

                        String nameOfCopy = (edit365Option.getFileName() == null || edit365Option.getFileName().trim().isEmpty()) ? attachmentFile.getFileName() : edit365Option.getFileName();
                        String nameOfCopyInOffice365 = nameOfCopy.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
                        nameOfCopyInOffice365 = nameOfCopyInOffice365.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
                        DriveItem copyResult = null;
                        try {
                            copyResult = this.graphService.copyFileWaitResult(attachmentFile.getItemId365(), folderTarget.getItemId365(), nameOfCopyInOffice365);
                        } catch (IOException e) {throw new RuntimeException(e);}

                        AttachmentFile attachmentFileNew = new AttachmentFile();
                        BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
                        attachmentFileNew.setId(null);
                        attachmentFileNew.setParentId(edit365Option.getFolderTargetId());
                        attachmentFileNew.setFileName(nameOfCopy);
                        attachmentFileNew.setAttachmentPermisitions(null);
                        attachmentFileNew.setChangeFileHistories(null);
                        attachmentFileNew.setRequestData(requestData);
                        attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
                        addUserInFo(attachmentFile, created);

                        // link get result coppy
                        JsonElement graphResponseHeaders = copyResult.additionalDataManager().get("graphResponseHeaders");
                        JsonElement location = graphResponseHeaders.getAsJsonObject().get("location");
                        String linkResultCopy = location.getAsJsonArray().get(0).getAsString();
                        attachmentFileNew.setUrlView(linkResultCopy);

                        for(AttachmentPermisition ele : attachmentFile.getAttachmentPermisitions()){
                            AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
                            BeanUtils.copyProperties(ele, attachmentPermisition);
                            attachmentPermisition.setId(null);
                            attachmentPermisition.setAttachmentFile(attachmentFileNew);
                            attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
                            attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
                        }
                        attachmentFileList.add(attachmentFileNew);
                        return attachmentFileNew;
                    }, executor);

                    completableFutureList.add(completableFuture);
                }

                CompletableFuture<Void> allCompletable = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]));
                allCompletable.thenRunAsync(() -> {
                    // lấy kết quả và gắn quyền cho file \\
                    try {Thread.sleep(Long.valueOf(TIME_WAIT_COPY));} catch (Exception e) {try {Thread.sleep(3000);} catch (Exception ex) {}}
                    List<AttachmentFile> attachmentFileResult = new ArrayList<>();
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        if(attachmentFile.getUrlView() != null){
                            try {

                                DriveItem resultCopyTemp = this.graphService.getResultCopyV3(attachmentFile.getUrlView());
                                // gán quyền \\
                                Permission permission = this.graphService.grantAccess(resultCopyTemp.id);
                                resultCopyTemp.webUrl = permission.link.webUrl;

                                attachmentFile.setItemId365(resultCopyTemp.id);
                                attachmentFile.setOfice365Path(resultCopyTemp.webUrl);
                                attachmentFile.setUrlView(null);
                                attachmentFile = this.attachmentFileRepository.save(attachmentFile);
                                attachmentFileResult.add(attachmentFile);
                            }catch (Exception e){
                                log.error("{}", e);
                            }
                        }
                    }
                });
                allCompletable.get();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }


    public Boolean moveFile(Edit365Option edit365Option){
        UserInfo created = null;
        if(edit365Option.getUserId() != null)
            created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(edit365Option.getSourceId()).get();
        AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();

        DriveItem resultMove = this.graphService.moveItem(attachmentFile.getItemId365(), folderTarget.getItemId365(), "");
        attachmentFile.setParentId(folderTarget.getId());
        attachmentFile.setModified(created);
        attachmentFile.setModifiedName(created != null ? null : created.getFullName());
        attachmentFile.setModifiedDate(Instant.now());
        this.attachmentFileRepository.save(attachmentFile);

        return true;
    }

//    private Boolean saveResultCopy(DriveItem driveItem, DriveItem parent){
//        DriveItemCollectionPage children = driveItem.children;
//        if(children == null || children.getCount() == 0) return true;
//        else{
//            Boolean result = true;
//            while(children.getCurrentPage() != null && !children.getCurrentPage().isEmpty()){
//                children = children.getNextPage().buildRequest().get();
//                for(DriveItem ele : children.getCurrentPage()){
//                    result = result && saveResultCopy(ele, driveItem);
//                }
//            }
//            return result;
//        }
//    }

    public void downloadFile(HttpServletResponse response, String fileName, Download365Option download365Option) throws Exception {
        AttachmentFile attachmentFile = new AttachmentFile();
        if(download365Option.getItemId() == null || download365Option.getItemId().trim().isEmpty()){
            attachmentFile = this.attachmentFileRepository.findById(download365Option.getAttachmentFileId()).orElse(null);
            if(attachmentFile == null) throw new RuntimeException("Not Exists File");
            download365Option.setItemId(attachmentFile.getItemId365());
        }
        String format = null;
        if(download365Option.isPDF()) format = this.PDF_FORMAT;
        if(PDF_EXTENSION.equalsIgnoreCase(attachmentFile.getFileExtension())) format = "";                  // nếu là pdf sẵn rồi -> chuyển format về rỗng tránh lỗi
        InputStream inputStream = this.graphService.getFile(download365Option.getItemId(), format);
        try {
            inputStream = this.getFileContentWithOption(inputStream, download365Option, attachmentFile);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName +"\"");
            response.getOutputStream().write(IOUtils.toByteArray(inputStream));
        }finally {
            inputStream.close();
        }
    }

    @Value("${system.qr-code.link:https://vcr.mobifone.ai/phieu-yeu-cau/chi-tiet/{{id}}}")
    public String QRCODE_LINK;

    public InputStream getFileContentWithOption(InputStream source, Download365Option download365Option, AttachmentFile attachmentFile) throws DocumentException, IOException {
        InputStream result = source;
        RequestData requestData = attachmentFile.getRequestData();
        if(download365Option.isAddQrCode()){
            Long requestDataId = requestData == null ? 0 : requestData.getId();
            String requestDataCode = requestData == null ? "" : requestData.getRequestDataCode();
            String status = requestData == null ? "" : requestData.getStatus().getStatusName();
//            String link = "https://eoffice.2bsystem.com.vn/phieu-yeu-cau/" + requestDataId + "/chi-tiet";
            String link = QRCODE_LINK.replace("{{id}}", String.valueOf(requestDataId));
            result = this.pdfUtils.addQRCode(source, link, requestDataCode, status);
        }
        if(download365Option.isAddWatermark()){
            UserInfo userInfo = null;
            if(download365Option.getUserId() != null){
                userInfo = this.userInfoRepository.findById(download365Option.getUserId()).orElse(null);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S").withZone(ZoneId.of(ZoneOffset.UTC.getId()));
            String timeExport = formatter.format(Instant.now());
            String watermarkText = (requestData == null ? "" : requestData.getRequestDataCode()) + " " + (userInfo == null ? "" : userInfo.getFullName()) + " " + timeExport;
            result = this.pdfUtils.addWatermark(result, watermarkText);
        }
        return result;
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

    /**
     * hàm lấy dữ liệu file từ Ofice 365
     * @param attachmentFile    : thông tin attachmentFile ở DB
     * @param path              : đường dẫn muốn lưu file khi lấy về
     * @return                  : file sau khi lấy về
     * @throws Exception
     */
    public File getFileInOffice365(AttachmentFile attachmentFile, Path path) throws Exception {
        String format = null;
        InputStream inputStream = this.graphService.getFile(attachmentFile.getItemId365(), format);
        File file = path.resolve(attachmentFile.getFileName()).toFile();
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }

    /**
     * Hàm lấy dường dẫn previewFile
     * @param attachmentFileId: id của AttachmentFile cần view
     * @return thông tin preview của File tương ứng
     */
    public ItemPreviewInfo previewFile(Long attachmentFileId){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(attachmentFileId).get();
        ItemPreviewInfo result = this.graphService.previewFile(attachmentFile.getItemId365());
        return result;
    }

    /**
     * Hàm thực hiện xoá file trên office 365
     * @param attachmentFileId: ID của attachment File cần xoá ở trên Office 365
     */
    public void deleteItemFile(Long attachmentFileId){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(attachmentFileId).get();
        if(attachmentFile.getTemplateForm() != null && attachmentFile.getRequestData() == null){     // TH nếu là file biểu mẫu -> xóa file bằng hàm khác
            if(!this.graphService.deleteItemLocked(attachmentFile.getItemId365())){     // nếu TH ko xóa được -> update lại để bỏ file biểu mẫu đó đi
                attachmentFile.setTemplateForm(null);
                this.attachmentFileRepository.save(attachmentFile);
            }
            return;
        }
        this.graphService.deleteItem(attachmentFile.getItemId365());
    }

    /**
     * Hàm thực hiện lấy dữ liệu file trên Office 365(dạng pdf) và hash file với Base64
     * @param attachmentFileId
     * @return trả về dạng hash Base 64 của file
     */
    public String hashFile(Long attachmentFileId) throws Exception {
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(attachmentFileId).get();
        InputStream pdfFile = null;
        if(PDF_EXTENSION.equals(attachmentFile.getFileExtension())){
            pdfFile = this.graphService.getFile(attachmentFile.getItemId365(), null);
        }else{
            pdfFile = this.graphService.getFile(attachmentFile.getItemId365(), PDF_FORMAT);
        }

        // thêm QRCODE
        if(Strings.isNullOrEmpty(attachmentFile.getSignOfFile())){
            String link = QRCODE_LINK.replace("{{id}}", String.valueOf(attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getId() : ""));
            String requestDataCode = attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getRequestDataCode() : "";
            String status = attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getStatus().getStatusName() : "";
            pdfFile = this.pdfUtils.addQRCode(pdfFile, link, requestDataCode, status);
        }

        byte[] bytes = IOUtils.toByteArray(pdfFile);
        String result = Base64.encodeBase64String(bytes);
        return result;
    }

    public void deCodeFile(HashFileOption hashFileOption) throws IOException {
        if(Strings.isNullOrEmpty(hashFileOption.getBase64Data())) throw new RuntimeException("No data");
        byte[] dataFile = Base64.decodeBase64(hashFileOption.getBase64Data());
        try {
            OutputStream outputStream = new FileOutputStream("C:\\Users\\Admin\\Desktop\\DuowngToraSigned.pdf");
            outputStream.write(dataFile);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            log.error("{}", e);
        }
    }

}
