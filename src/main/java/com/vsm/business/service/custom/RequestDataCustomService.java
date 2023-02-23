package com.vsm.business.service.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.AppConstant;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.config.Constants;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.RequestDataSearchRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.RequestDataSpecification;
import com.vsm.business.repository.specification.bo.ConditionQuery;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.file.Folder365CustomService;
import com.vsm.business.service.custom.file.FolderCustomService;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.file.UploadFileCustomService;
import com.vsm.business.service.custom.file.bo.AttachDocument;
import com.vsm.business.service.custom.file.bo.CreateFolderOption;
import com.vsm.business.service.custom.file.bo.FileRq;
import com.vsm.business.service.custom.file.bo.Office365.CreateFolder365Option;
import com.vsm.business.service.custom.file.bo.Office365.Download365Option;
import com.vsm.business.service.custom.file.bo.Office365.Edit365Option;
import com.vsm.business.service.custom.file.bo.response.BaseClientRp;
import com.vsm.business.service.custom.mail.MailCustomService;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import com.vsm.business.service.custom.processRequest.bo.ApproveOption;
import com.vsm.business.service.dto.*;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.service.mapper.RequestDataMapper;
import com.vsm.business.utils.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.elasticsearch.common.Strings;
import org.hibernate.exception.GenericJDBCException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RequestDataCustomService {
    private static final Logger log = LoggerFactory.getLogger(RequestDataCustomService.class);

    private final RequestDataRepository requestDataRepository;

    private final RequestDataMapper requestDataMapper;

    private final RequestDataSearchRepository requestDataSearchRepository;

    private final FormDataRepository formDataRepository;

    private final ProcessDataRepository processDataRepository;

    private final ProcessInfoRepository processInfoRepository;

    private final StepDataRepository stepDataRepository;

    private final StepInProcessRepository stepInProcessRepository;

    private final UserInStepRepository userInStepRepository;

    private final GenerateCodeUtils generateCodeUtils;

    private final UploadFileCustomService uploadFileCustomService;

    private final FolderCustomService folderCustomService;

    private final AttachmentFileRepository attachmentFileRepository;

    private final TemplateFormRepository templateFormRepository;

    private final StatusRepository statusRepository;

    private final MailTemplateRepository mailTemplateRepository;

    private final AttachmentFileMapper attachmentFileMapper;

    private final SignDataRepository signDataRepository;

    private final ManageStampInfoRepository manageStampInfoRepository;

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;


    private ObjectMapper objectMapper = new ObjectMapper();


//    @Autowired
//    private ConditionUtils conditionUtils;

//    @Autowired
//    private MailCustomService mailCustomService;

    @Autowired
    private Folder365CustomService folder365CustomService;

    @Autowired
    private UploadFile365CustomService uploadFile365CustomService;

    @Autowired
    private GraphService graphService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private VNCharacterUtils vnCharacterUtils;

    @Autowired
    private TemplateFormCustomService templateFormCustomService;

    @Autowired
    private FieldDataRepository fieldDataRepository;

    @Autowired
    private AuthenticateUtils authenticateUtils;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private ObjectUtils objectUtil;

    private final String PDF_FORMAT = "pdf";


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
    @Value("${feature.generate-file-name:FALSE}")
    private String FEATURE_GENERATE_FILE_NAME;

    public RequestDataCustomService(
        RequestDataRepository requestDataRepository,
        RequestDataMapper requestDataMapper,
        RequestDataSearchRepository requestDataSearchRepository,
        FormDataRepository formDataRepository,
        ProcessDataRepository processDataRepository,
        ProcessInfoRepository processInfoRepository,
        StepDataRepository stepDataRepository,
        StepInProcessRepository stepInProcessRepository,
        UserInStepRepository userInStepRepository,
        GenerateCodeUtils generateCodeUtils,
        UploadFileCustomService uploadFileCustomService,
        FolderCustomService folderCustomService,
        AttachmentFileRepository attachmentFileRepository,
        TemplateFormRepository templateFormRepository,
        RequestRepository requestRepository,
        StatusRepository statusRepository,
        MailTemplateRepository mailTemplateRepository,
        AttachmentFileMapper attachmentFileMapper,
        SignDataRepository signDataRepository,
        ManageStampInfoRepository manageStampInfoRepository,
        ReqdataProcessHisRepository reqdataProcessHisRepository
    ) {
        this.requestDataRepository = requestDataRepository;
        this.requestDataMapper = requestDataMapper;
        this.requestDataSearchRepository = requestDataSearchRepository;
        this.formDataRepository = formDataRepository;
        this.processDataRepository = processDataRepository;
        this.processInfoRepository = processInfoRepository;
        this.stepDataRepository = stepDataRepository;
        this.stepInProcessRepository = stepInProcessRepository;
        this.userInStepRepository = userInStepRepository;
        this.generateCodeUtils = generateCodeUtils;
        this.uploadFileCustomService = uploadFileCustomService;
        this.folderCustomService = folderCustomService;
        this.attachmentFileRepository = attachmentFileRepository;
        this.templateFormRepository = templateFormRepository;
        this.statusRepository = statusRepository;
        this.mailTemplateRepository = mailTemplateRepository;
        this.attachmentFileMapper = attachmentFileMapper;
        this.signDataRepository = signDataRepository;
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
    }

    @Transactional(readOnly = true)
    public Optional<RequestDataDTO> customFindOne(Long id) {
        log.debug("Request to get RequestData : {}", id);
        Optional<RequestData> result = requestDataRepository.findOneWithEagerRelationships(id);
        if(result.isPresent()){
//            List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.get().getId()).stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());
//            RequestDataDTO requestDataDTO = result.map(requestDataMapper::toDto).get();
//            requestDataDTO.setRequestDataList(requestDataDTOList);
//            return Optional.of(requestDataDTO);
            List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.get().getId()).stream().map(ele ->
                this.convertToDTOMegaIgnoreField(ele)
            ).collect(Collectors.toList());
            RequestDataDTO requestDataDTO = this.convertToDTOFindOne(result.get());
            requestDataDTO.setRequestDataList(requestDataDTOList);
            return Optional.of(requestDataDTO);
        }
        return result.map(requestDataMapper::toDto);
    }
    // cắt giảm dữ liệu của requestData khi findone \\
    private RequestDataDTO convertToDTOFindOne(RequestData requestData){
        if(requestData == null) return null;
        RequestDataDTO result = this.requestDataMapper.toDto(requestData);
        if(requestData.getReqDataConcerned() != null) {
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            requestDataDTO.setId(requestData.getReqDataConcerned().getId());
            result.setReqDataConcerned(requestDataDTO);
        }
        if(requestData.getAttachmentFiles() != null){
            result.setAttachmentFileList(requestData.getAttachmentFiles().stream().map(ele -> {
                if(ele == null) return null;
                AttachmentFileDTO attachmentFileDTO = new AttachmentFileDTO();
                attachmentFileDTO.setId(ele.getId());
                attachmentFileDTO.setItemId365(ele.getItemId365());
                attachmentFileDTO.setOfice365Path(ele.getOfice365Path());
                attachmentFileDTO.setFullPath(ele.getFullPath());
                attachmentFileDTO.setFileName(ele.getFileName());
                return attachmentFileDTO;
            }).collect(Collectors.toList()));
        }
        if(requestData.getRequestType() != null){
            RequestTypeDTO requestTypeDTO = new RequestTypeDTO();
            requestTypeDTO.setId(requestData.getRequestType().getId());
            requestTypeDTO.setRequestTypeName(requestData.getRequestType().getRequestTypeName());
            requestTypeDTO.setRequestTypeCode(requestData.getRequestType().getRequestTypeCode());
            result.setRequestType(requestTypeDTO);
        }
        if(requestData.getRequestGroup() != null){
            RequestGroupDTO requestGroupDTO = new RequestGroupDTO();
            requestGroupDTO.setId(requestData.getRequestGroup().getId());
            requestGroupDTO.setRequestGroupName(requestData.getRequestGroup().getRequestGroupName());
            requestGroupDTO.setRequestGroupCode(requestData.getRequestGroup().getRequestGroupCode());
            result.setRequestGroup(requestGroupDTO);
        }
        if(requestData.getRequest() != null){
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setId(requestData.getRequest().getId());
            requestDTO.setRequestName(requestData.getRequest().getRequestName());
            requestDTO.setRequestCode(requestData.getRequest().getRequestCode());
            result.setRequest(requestDTO);
        }
        if(requestData.getCreated() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(requestData.getCreated().getId());
            userInfoDTO.setFullName(requestData.getCreated().getFullName());
            userInfoDTO.setEmail(requestData.getCreated().getEmail());
            result.setCreated(userInfoDTO);
        }
        if(requestData.getModified() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(requestData.getModified().getId());
            userInfoDTO.setFullName(requestData.getModified().getFullName());
            userInfoDTO.setEmail(requestData.getModified().getEmail());
            result.setModified(userInfoDTO);
        }
        if(requestData.getApprover() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(requestData.getApprover().getId());
            userInfoDTO.setFullName(requestData.getApprover().getFullName());
            userInfoDTO.setEmail(requestData.getApprover().getEmail());
            result.setApprover(userInfoDTO);
        }
        if(requestData.getRevoker() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(requestData.getRevoker().getId());
            userInfoDTO.setFullName(requestData.getRevoker().getFullName());
            userInfoDTO.setEmail(requestData.getRevoker().getEmail());
            result.setRevoker(userInfoDTO);
        }
        return result;
    }


    public Boolean delete(Long id){
        log.info("RequestCustomService delete({})", id);
        RequestData requestData = this.requestDataRepository.findById(id).orElse(null);
        // bỏ các phiếu liên quan
        List<RequestData> requestDataList = this.requestDataRepository.findAllByReqDataConcernedId(id);
        if(requestDataList != null && !requestDataList.isEmpty()){
            requestDataList = requestDataList.stream().map(ele -> {
                ele.setReqDataConcerned(null);
                return ele;
            }).collect(Collectors.toList());
            this.requestDataRepository.saveAll(requestDataList);
        }

        requestDataRepository.deleteById(id);
        //if(requestData != null) {try {folderCustomService.deleteFolder(requestData.getIdDirectoryPath());} catch (Exception e) {e.printStackTrace();}}
        return true;
    }

    public Boolean deleteAll_v1(List<RequestDataDTO> requestDataDTOList){
        try {
            if(requestDataDTOList != null && !requestDataDTOList.isEmpty()){
                requestDataDTOList.forEach(ele -> {
                    this.delete(ele.getId());
                });
            }
        }catch (Exception e){
            log.error("{}", e);
            throw  e;
        }
        return true;
    }

    public List<RequestDataDTO> findAll() {
        List<RequestDataDTO> result = requestDataRepository.findAll().stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        log.debug("RequestCustomService findAll(): {}", result);
        return result;
    }

//    public RequestDataDTO customSave(RequestDataDTO requestDataDTO) throws Exception {
//        log.debug("RequestCustomService customSave: {}", requestDataDTO);
//        // generate code
//        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
//        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
//        requestData = requestDataRepository.save(requestData);
//        Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();
//        Set<TemplateForm> temp = request.getTemplateForms();
//
//        // save success -> clone templateForm (AttachmentFile);
//        // create new folder with code of RequestDataDto
//        CreateFolderOption createFolderOption = new CreateFolderOption();
//        createFolderOption.setIsFolder(1);
//        createFolderOption.setFileName(requestData.getRequestDataCode());
//        if(request.getDescription() != null && !request.getDescription().trim().isEmpty())
//            createFolderOption.setParentId(request.getDescription());
//        BaseClientRp baseClientRp = this.folderCustomService.createFolder(createFolderOption);
//        if(baseClientRp.getState()){
//            AttachDocument attachDocument = this.objectMapper.convertValue(baseClientRp.getData(), AttachDocument.class);
//            AttachmentFile folderCreated = this.convertAttachDocumentToFoder(attachDocument, requestData);
////            requestData.setDescription(folderCreated.getIdInFileService());
//            requestData.setIdDirectoryPath(folderCreated.getIdInFileService());
//            this.attachmentFileRepository.save(folderCreated);
//
//            for(TemplateForm templateForm : request.getTemplateForms()){
//                for(AttachmentFile attachmentFile : (templateForm.getAttachmentFiles() != null) ? templateForm.getAttachmentFiles().stream().filter(ele -> ele.getRequestData() == null).collect(Collectors.toSet()) : new HashSet<AttachmentFile>()){
//                    FileRq fileRq = new FileRq();
//                    fileRq.setFileId(attachmentFile.getIdInFileService());
//                    fileRq.setFolderId(folderCreated.getIdInFileService());
//                    fileRq.setFileName(attachmentFile.getFileName());
//                    BaseClientRp baseClientRpAttachmentFile = this.uploadFileCustomService.copyFile_v1(fileRq);
//
//                    AttachDocument attachDocument1 = this.objectMapper.convertValue(baseClientRpAttachmentFile.getData(), AttachDocument.class);
//                    AttachmentFile attachmentFileCreate = new AttachmentFile();
//                    BeanUtils.copyProperties(attachmentFile, attachmentFileCreate);
//                    attachmentFileCreate.setId(null);
//                    attachmentFileCreate.setIdInFileService(attachDocument1.getId());
//                    attachmentFileCreate.setPath(attachDocument1.getFilePath());
//                    attachmentFileCreate.setDescription(this.objectMapper.writeValueAsString(attachDocument1));
//                    attachmentFileCreate.setIsActive(true);
//                    attachmentFileCreate.setParentId(folderCreated.getParentId());
//                    attachmentFileCreate.setRequestData(requestData);
//                    attachmentFileCreate.setCreated(requestData.getCreated());
//                    attachmentFileCreate.setCreatedName(requestData.getCreated().getFullName());
//                    attachmentFileCreate.setCreatedDate(Instant.now());
//                    attachmentFileCreate.setModified(requestData.getModified());
//                    attachmentFileCreate.setModifiedName(requestData.getModified().getFullName());
//                    attachmentFileCreate.setModifiedDate(Instant.now());
//
//
//                    attachmentFileRepository.save(attachmentFileCreate);
//                }
//            }
//        }else{
//            throw new Exception("Error when create folder: " + baseClientRp.getMessage());
//        }
//
//        requestData = this.requestDataRepository.save(requestData);
//        // nối với các phiếu liên quan
//        saveReqDataConcerned(requestData, requestDataDTO);
//
//        RequestDataDTO result = requestDataMapper.toDto(requestData);
//
//        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());
//        result.setRequestDataList(requestDataDTOList);
//
//        try {
//            requestDataSearchRepository.save(requestData);
//        }catch (StackOverflowError e){
//            log.debug(e.getMessage());
//        }
//        return result;
//    }


    @Transactional
    public RequestDataDTO customSave(RequestDataDTO requestDataDTO) throws Exception {
        log.debug("RequestCustomService customSave: {}", requestDataDTO);
        List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
        // generate code
        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);
        Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();

        // save success -> clone templateForm (AttachmentFile);
        // create new folder with code of RequestDataDto
        CreateFolder365Option createFolder365Option = new CreateFolder365Option();
        createFolder365Option.setCreatedId( requestData.getCreated() != null ? requestData.getCreated().getId() : null);
        createFolder365Option.setFolderName( requestData.getRequestDataCode() );
        createFolder365Option.setParentItemId( Strings.isNullOrEmpty(request.getIdDirectoryPath()) ?  this.uploadFile365CustomService.ROOT_FOLDER_ITEM_ID : request.getIdDirectoryPath());
        //createFolder365Option.setParentItemId(request.getIdDirectoryPath());
        AttachmentFileDTO folder = this.folder365CustomService.createFolder(createFolder365Option);
        AttachmentFile folderCreated = this.attachmentFileMapper.toEntity(folder);
//            requestData.setDescription(folderCreated.getIdInFileService());
        requestData.setIdDirectoryPath(folderCreated.getItemId365());
        folderCreated.setRequestData(requestData);
        if(!Strings.isNullOrEmpty(request.getIdDirectoryPath())){            // cập nhật lại folder cha cho folder vừa tạo (folder của phiếu yêu cầu)
            try {
                AttachmentFile folderParent = this.attachmentFileRepository.findAllByItemId365(request.getIdDirectoryPath()).get(0);
                folderCreated.setParentId(folderParent.getId());
            }catch (Exception e){
                log.error("{}", e);
                folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
            }
        }else{
            folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
        }
        this.attachmentFileRepository.save(folderCreated);

        int stt = 1;
        for(AttachmentFileDTO attachmentFileDTO : requestDataDTO.getAttachmentFileList()){
            AttachmentFile attachmentFile = this.attachmentFileMapper.toEntity(attachmentFileDTO);

            Edit365Option edit365Option = new Edit365Option();
            edit365Option.setUserId(requestData.getUserInfos() != null ? requestData.getCreated().getId() : null);

            // sửa quy tắc đặt tên file thành : Số PYC_Tiêu đề (Tiếng việt không đâu)_STT.extension \\
//            edit365Option.setFileName(attachmentFile.getFileName());
            String fileName = requestData.getRequestDataCode() + "_" + vnCharacterUtils.removeAccent(requestData.getTitle() == null ? "" : requestData.getTitle()).replaceAll("\\s", "") + "_" + stt + "." + attachmentFile.getFileExtension();
            if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestData.getId());
                fileName = generateCodeUtils.v2_generateFileName(stt, attachmentFile.getFileExtension(), requestData.getRuleGenerateAttachName(), request, requestData, fieldDataList);
            }
            edit365Option.setFileName(fileName);

            edit365Option.setSourceId(attachmentFile.getId());
            edit365Option.setFolderTargetId(folderCreated.getId());

            AttachmentFile attachmentFileResult = this.uploadFile365CustomService.cloneFile(edit365Option, requestData);
            attachmentFileResult.parentId(folderCreated.getId());
            this.attachmentFileRepository.save(attachmentFileResult);
            attachmentFileDTOList.add(this.attachmentFileMapper.toDto(attachmentFileResult));
            stt++;
        }


//        if(requestDataDTO.getAttachmentFileList() != null && !requestDataDTO.getAttachmentFileList().isEmpty()){
//        if(requestDataDTO.getAttachmentFileList() != null && !requestDataDTO.getAttachmentFileList().isEmpty()){
//            for(AttachmentFileDTO attachmentFileDTO : requestDataDTO.getAttachmentFileList()){
//                try {
//                    AttachmentFile attachmentFile = this.attachmentFileMapper.toEntity(attachmentFileDTO);
//                    RequestData finalRequestData = requestData;
//                    Flux.just(attachmentFile).flatMap(ele -> {
//                        Mono<AttachmentFile> mono = Mono.just(ele).map(ele1 -> {
//                            System.out.println("DuowngTora: " + ele1.getFileName());
//                            Edit365Option edit365Option = new Edit365Option();
//                            edit365Option.setUserId( finalRequestData.getCreated() != null ? finalRequestData.getCreated().getId() : null);
//                            edit365Option.setFileName(ele1.getFileName());
//                            edit365Option.setSourceId(ele1.getId());
//                            edit365Option.setFolderTargetId(folderCreated.getId());
//                            AttachmentFile attachmentFileResult = null;
//                            try {
//                                String nameOfCopy = edit365Option.getFileName();
//                                attachmentFileResult = this.uploadFile365CustomService.cloneFileWithMono(edit365Option, finalRequestData, finalRequestData.getCreated(), ele, folderCreated.getItemId365(), nameOfCopy);
//                            } catch (IOException e) {
//                                log.error("{}", e);
//                                return null;
//                            }
//                            attachmentFileResult.setParentId(folderCreated.getId());
//                            attachmentFileResult = this.attachmentFileRepository.save(attachmentFileResult);
//                            return attachmentFileResult;
//                        });
//                        return mono;
//                    }).subscribeOn(Schedulers.boundedElastic()).subscribe();
//                }catch (Exception e){log.error("{}", e);}
//            }
//        }


        requestData = this.requestDataRepository.save(requestData);
        // nối với các phiếu liên quan
        if(requestDataDTO.getRequestDataList() != null)     // nếu có phiếu liên quan -> nối
            saveReqDataConcerned(requestData, requestDataDTO);

        RequestDataDTO result = requestDataMapper.toDto(requestData);
        result.setAttachmentFileList(attachmentFileDTOList);

        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(ele -> this.convertToDTOMegaIgnoreField(ele)).collect(Collectors.toList());
        result.setRequestDataList(requestDataDTOList);

        try {
            requestDataSearchRepository.save(requestData);
        }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
            log.error("{}", e.getMessage());
        }catch (Exception e){
            log.error("{}", e);
        }

        // khi tạo thành công -> update numberRequestData của request
        Long numberRequestData = request.getNumberRequestData() == null ? 0L : request.getNumberRequestData();
        request.setNumberRequestData(numberRequestData + 1);
        request = this.requestRepository.save(request);

        return result;
    }

    @Transactional
    public RequestDataDTO customSave_v2(RequestDataDTO requestDataDTO) throws Exception {
        log.debug("RequestCustomService customSave: {}", requestDataDTO);
        List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
        // generate code
        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);
        Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();

        // save success -> clone templateForm (AttachmentFile);
        // create new folder with code of RequestDataDto
        CreateFolder365Option createFolder365Option = new CreateFolder365Option();
        createFolder365Option.setCreatedId( requestData.getCreated() != null ? requestData.getCreated().getId() : null);
        createFolder365Option.setFolderName( requestData.getRequestDataCode() );
        createFolder365Option.setParentItemId( Strings.isNullOrEmpty(request.getIdDirectoryPath()) ?  this.uploadFile365CustomService.ROOT_FOLDER_ITEM_ID : request.getIdDirectoryPath());
        //createFolder365Option.setParentItemId(request.getIdDirectoryPath());
        AttachmentFileDTO folder = this.folder365CustomService.createFolder(createFolder365Option);
        AttachmentFile folderCreated = this.attachmentFileMapper.toEntity(folder);
//            requestData.setDescription(folderCreated.getIdInFileService());
        requestData.setIdDirectoryPath(folderCreated.getItemId365());
        folderCreated.setRequestData(requestData);
        if(!Strings.isNullOrEmpty(request.getIdDirectoryPath())){            // cập nhật lại folder cha cho folder vừa tạo (folder của phiếu yêu cầu)
            try {
                AttachmentFile folderParent = this.attachmentFileRepository.findAllByItemId365(request.getIdDirectoryPath()).get(0);
                folderCreated.setParentId(folderParent.getId());
            }catch (Exception e){
                log.error("{}", e);
                folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
            }
        }else{
            folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
        }
        this.attachmentFileRepository.save(folderCreated);

        int stt = 1;
        List<Edit365Option> edit365OptionList = new ArrayList<>();
        for(AttachmentFileDTO attachmentFileDTO : requestDataDTO.getAttachmentFileList()){
            AttachmentFile attachmentFile = this.attachmentFileMapper.toEntity(attachmentFileDTO);

            Edit365Option edit365Option = new Edit365Option();
            edit365Option.setUserId(requestData.getUserInfos() != null ? requestData.getCreated().getId() : null);

            // sửa quy tắc đặt tên file thành : Số PYC_Tiêu đề (Tiếng việt không đâu)_STT.extension \\
//            edit365Option.setFileName(attachmentFile.getFileName());
            String fileName = requestData.getRequestDataCode() + "_" + vnCharacterUtils.removeAccent(requestData.getTitle() == null ? "" : requestData.getTitle()).replaceAll("\\s", "") + "_" + stt + "." + attachmentFile.getFileExtension();
            if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestData.getId());
                fileName = generateCodeUtils.v2_generateFileName(stt, attachmentFile.getFileExtension(), requestData.getRuleGenerateAttachName(), request, requestData, fieldDataList);
            }
            edit365Option.setFileName(fileName);
            edit365Option.setSourceId(attachmentFile.getId());
            edit365Option.setFolderTargetId(folderCreated.getId());
            edit365OptionList.add(edit365Option);
            stt++;
        }

        List<AttachmentFile> attachmentFileList = this.uploadFile365CustomService.cloneListFile(edit365OptionList, requestData);
        if(attachmentFileList != null && !attachmentFileList.isEmpty()){
            attachmentFileList = attachmentFileList.stream().map(ele -> {
                ele.setParentId(folderCreated.getId());
                return ele;
            }).collect(Collectors.toList());

            attachmentFileDTOList = this.attachmentFileRepository.saveAll(attachmentFileList).stream().map(ele -> this.attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
        }

        // clear memory
        try {
            attachmentFileList = null;
            edit365OptionList = null;
        }catch (Exception e){log.error("{}", e);}

        requestData = this.requestDataRepository.save(requestData);


        RequestData finalRequestData = requestData;
        CompletableFuture.runAsync(() -> {
            try {
                requestDataSearchRepository.save(finalRequestData);
            }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
                log.error("{}", e.getMessage());
            }catch (Exception e){
                log.error("{}", e);
            }
        });


        // nối với các phiếu liên quan
        if(requestDataDTO.getRequestDataList() != null)     // nếu có phiếu liên quan -> nối
            saveReqDataConcerned(requestData, requestDataDTO);

        RequestDataDTO result = requestDataMapper.toDto(requestData);
        result.setAttachmentFileList(attachmentFileDTOList);

        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(ele -> this.convertToDTOMegaIgnoreField(ele)).collect(Collectors.toList());
        result.setRequestDataList(requestDataDTOList);



        // khi tạo thành công -> update numberRequestData của request
        Long numberRequestData = request.getNumberRequestData() == null ? 0L : request.getNumberRequestData();
        request.setNumberRequestData(numberRequestData + 1);
        request = this.requestRepository.save(request);

        return result;
    }

    //    @Transactional
    public RequestDataDTO customSave_v3(RequestDataDTO requestDataDTO) throws Exception {
        log.debug("RequestCustomService customSave: {}", requestDataDTO);
        List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
        // generate code
        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);
        Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();

        // save success -> clone templateForm (AttachmentFile);
        // create new folder with code of RequestDataDto
        CreateFolder365Option createFolder365Option = new CreateFolder365Option();
        createFolder365Option.setCreatedId( requestData.getCreated() != null ? requestData.getCreated().getId() : null);
        createFolder365Option.setFolderName( requestData.getRequestDataCode() );
        createFolder365Option.setParentItemId( Strings.isNullOrEmpty(request.getIdDirectoryPath()) ?  this.uploadFile365CustomService.ROOT_FOLDER_ITEM_ID : request.getIdDirectoryPath());
        //createFolder365Option.setParentItemId(request.getIdDirectoryPath());
        AttachmentFileDTO folder = this.folder365CustomService.createFolder(createFolder365Option);
        AttachmentFile folderCreated = this.attachmentFileMapper.toEntity(folder);
//            requestData.setDescription(folderCreated.getIdInFileService());
        requestData.setIdDirectoryPath(folderCreated.getItemId365());
        folderCreated.setRequestData(requestData);
        if(!Strings.isNullOrEmpty(request.getIdDirectoryPath())){            // cập nhật lại folder cha cho folder vừa tạo (folder của phiếu yêu cầu)
            try {
                AttachmentFile folderParent = this.attachmentFileRepository.findAllByItemId365(request.getIdDirectoryPath()).get(0);
                folderCreated.setParentId(folderParent.getId());
            }catch (Exception e){
                log.error("{}", e);
                folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
            }
        }else{
            folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
        }
        this.attachmentFileRepository.save(folderCreated);

        int stt = 1;
        List<Edit365Option> edit365OptionList = new ArrayList<>();
        for(AttachmentFileDTO attachmentFileDTO : requestDataDTO.getAttachmentFileList()){
            AttachmentFile attachmentFile = this.attachmentFileMapper.toEntity(attachmentFileDTO);

            Edit365Option edit365Option = new Edit365Option();
            edit365Option.setUserId(requestData.getCreated() != null ? requestData.getCreated().getId() : null);

            // sửa quy tắc đặt tên file thành : Số PYC_Tiêu đề (Tiếng việt không đâu)_STT.extension \\
//            edit365Option.setFileName(attachmentFile.getFileName());
            String fileName = requestData.getRequestDataCode() + "_" + vnCharacterUtils.removeAccent(requestData.getTitle() == null ? "" : requestData.getTitle()).replaceAll("\\s", "") + "_" + stt + "." + attachmentFile.getFileExtension();
            if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestData.getId());
                fileName = generateCodeUtils.v2_generateFileName(stt, attachmentFile.getFileExtension(), requestData.getRuleGenerateAttachName(), request, requestData, fieldDataList);
            }
            edit365Option.setFileName(fileName);
            edit365Option.setSourceId(attachmentFile.getId());
            edit365Option.setFolderTargetId(folderCreated.getId());
            edit365OptionList.add(edit365Option);
            stt++;
        }

        this.uploadFile365CustomService.cloneListFile_v2(edit365OptionList, requestData);

        // clear memory
        try {
            edit365OptionList = null;
            folderCreated = null;
        }catch (Exception e){log.error("{}", e);}

//        if(attachmentFileList != null && !attachmentFileList.isEmpty()){
//            attachmentFileList = attachmentFileList.stream().map(ele -> {
//                ele.setParentId(folderCreated.getId());
//                return ele;
//            }).collect(Collectors.toList());
//
//            attachmentFileDTOList = this.attachmentFileRepository.saveAll(attachmentFileList).stream().map(ele -> this.attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
//        }

        requestData = this.requestDataRepository.save(requestData);


        RequestData finalRequestData = requestData;
        CompletableFuture.runAsync(() -> {
            try {
                requestDataSearchRepository.save(finalRequestData);
            }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
                log.error("{}", e.getMessage());
            }catch (Exception e){
                log.error("{}", e);
            }
        });


        // nối với các phiếu liên quan
        if(requestDataDTO.getRequestDataList() != null)     // nếu có phiếu liên quan -> nối
            saveReqDataConcerned(requestData, requestDataDTO);

        RequestDataDTO result = requestDataMapper.toDto(requestData);
        result.setAttachmentFileList(attachmentFileDTOList);

        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(ele -> this.convertToDTOMegaIgnoreField(ele)).collect(Collectors.toList());
        result.setRequestDataList(requestDataDTOList);


        // khi tạo thành công -> update numberRequestData của request
        Long numberRequestData = request.getNumberRequestData() == null ? 0L : request.getNumberRequestData();
        request.setNumberRequestData(numberRequestData + 1);
        request = this.requestRepository.save(request);

        return result;
    }

//    @Transactional
    public Map<Long, Boolean> copyRequestDatas(List<Long> requestDataIds, Long userId) throws Exception {

        Map<Long, Boolean> result = new HashMap<>();

        UserInfo creater = this.userInfoRepository.findById(userId).get();
        String createrOrgName = creater.getOrganizations() != null ? creater.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", ")) : "";
        String createrRankName = creater.getRanks() != null ? creater.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", ")) : "";

        Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();

        Long requestDataIdDelete = null;
        if(requestDataIds != null && !requestDataIds.isEmpty()){
            for(Long requestDataId : requestDataIds){
                try {
                    RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
                    RequestData requestDataCopy = new RequestData();
                    BeanUtils.copyProperties(requestData, requestDataCopy);
                    // bỏ các thông tin ko cần copy trong requestData
                    requestDataCopy.setId(null);
                    requestDataCopy.setCreated(creater);
                    requestDataCopy.setCreatedName(creater.getFullName());
                    requestDataCopy.setCreatedDate(Instant.now());
                    requestDataCopy.setCreatedOrgName(createrOrgName);
                    requestDataCopy.setCreatedRankName(createrRankName);
                    requestDataCopy.setModified(creater);
                    requestDataCopy.setModifiedName(creater.getFullName());
                    requestDataCopy.setModifiedDate(Instant.now());
                    requestDataCopy.setResultSyncContract(null);
                    requestDataCopy.setNumberAttach(null);
                    requestDataCopy.setPlotOfLandNumber(null);
                    requestDataCopy.contractExpireTime(null);
                    requestDataCopy.setContractNumber(null);
                    requestDataCopy.setTimeDone(null);
                    requestDataCopy.setIsDone(false);
                    requestDataCopy.subStatus(null);
                    requestDataCopy.setRevoker(null);
                    requestDataCopy.setRevokerName("");
                    requestDataCopy.setRevokerOrgName("");
                    requestDataCopy.setRevokerRankName("");
                    requestDataCopy.setIsRevoked(false);
                    requestDataCopy.setApprover(null);
                    requestDataCopy.setApproverName("");
                    requestDataCopy.setApproverOrgName("");
                    requestDataCopy.setApproverRankName("");
                    requestDataCopy.setIsApprove(false);
                    requestDataCopy.setStatus(status_DANGSOAN);
                    requestDataCopy.setStatusName(status_DANGSOAN.getStatusName());
                    requestDataCopy.setIsActive(true);
                    requestDataCopy.setIsDelete(false);
                    requestDataCopy.setExpiredTime(null);
                    requestDataCopy.setOldStatus(null);
                    requestDataCopy.setCurrentRound(1L);
                    requestDataCopy.setUserInfos(new HashSet<>());
                    requestDataCopy.setReqdataChangeHis(new HashSet<>());
                    requestDataCopy.setRequestRecalls(new HashSet<>());
                    requestDataCopy.setReqdataProcessHis(new HashSet<>());
                    requestDataCopy.setFieldData(new HashSet<>());
                    requestDataCopy.setFormData(new HashSet<>());
                    requestDataCopy.setAttachmentFiles(new HashSet<>());
                    requestDataCopy.setProcessData(new HashSet<>());
                    requestDataCopy.setStepData(new HashSet<>());
                    requestDataCopy.setInformationInExchanges(new HashSet<>());
                    requestDataCopy.setManageStampInfos(new HashSet<>());
                    requestDataCopy.setSignData(new HashSet<>());
                    requestDataCopy.setOTPS(new HashSet<>());
                    requestDataCopy.setTagInExchanges(new HashSet<>());
                    requestDataCopy.setTennantCode(requestDataId.toString());           // dùng trường tennantCode để đánh dấu phiếu này coppy từ phiếu nào
                    requestDataCopy = requestDataRepository.save(requestDataCopy);

                    requestDataIdDelete = requestDataCopy.getId();

                    // generate code \\
                    Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();
                    RequestDTO requestDTO = new RequestDTO();
                    requestDTO.setId(request.getId());
                    requestDTO.setRuleGenerateCode(request.getRuleGenerateCode());
                    RequestDataDTO requestDataDTO = new RequestDataDTO();
                    requestDataDTO.setRequest(requestDTO);
                    requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
                    requestDataCopy.setRequestDataCode(requestDataDTO.getRequestDataCode());


                    // save success -> clone templateForm (AttachmentFile);
                    // create new folder with code of RequestDataDto
                    CreateFolder365Option createFolder365Option = new CreateFolder365Option();
                    createFolder365Option.setCreatedId( requestDataCopy.getCreated() != null ? requestDataCopy.getCreated().getId() : null);
                    createFolder365Option.setFolderName( requestDataCopy.getRequestDataCode() );
                    createFolder365Option.setParentItemId( Strings.isNullOrEmpty(request.getIdDirectoryPath()) ?  this.uploadFile365CustomService.ROOT_FOLDER_ITEM_ID : request.getIdDirectoryPath());
                    //createFolder365Option.setParentItemId(request.getIdDirectoryPath());
                    AttachmentFileDTO folder = this.folder365CustomService.createFolder(createFolder365Option);
                    AttachmentFile folderCreated = this.attachmentFileMapper.toEntity(folder);
//            requestData.setDescription(folderCreated.getIdInFileService());
                    requestDataCopy.setIdDirectoryPath(folderCreated.getItemId365());
                    folderCreated.setRequestData(requestDataCopy);
                    if(!Strings.isNullOrEmpty(request.getIdDirectoryPath())){            // cập nhật lại folder cha cho folder vừa tạo (folder của phiếu yêu cầu)
                        try {
                            AttachmentFile folderParent = this.attachmentFileRepository.findAllByItemId365(request.getIdDirectoryPath()).get(0);
                            folderCreated.setParentId(folderParent.getId());
                        }catch (Exception e){
                            log.error("{}", e);
                            folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
                        }
                    }else{
                        folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
                    }
                    this.attachmentFileRepository.save(folderCreated);

                    int stt = 1;
                    List<Edit365Option> edit365OptionList = new ArrayList<>();
                    List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.getAllAttachmentFileTemplate(request.getTemplateForms().stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        Edit365Option edit365Option = new Edit365Option();
                        edit365Option.setUserId(requestDataCopy.getCreated() != null ? requestDataCopy.getCreated().getId() : null);

                        // sửa quy tắc đặt tên file thành : Số PYC_Tiêu đề (Tiếng việt không đâu)_STT.extension \\
//            edit365Option.setFileName(attachmentFile.getFileName());
                        String fileName = requestDataCopy.getRequestDataCode() + "_" + vnCharacterUtils.removeAccent(requestDataCopy.getTitle() == null ? "" : requestDataCopy.getTitle()).replaceAll("\\s", "") + "_" + stt + "." + attachmentFile.getFileExtension();
                        if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                            List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestData.getId());
                            fileName = generateCodeUtils.v2_generateFileName(stt, attachmentFile.getFileExtension(), requestDataCopy.getRuleGenerateAttachName(), request, requestDataCopy, fieldDataList);
                        }
                        edit365Option.setFileName(fileName);
                        edit365Option.setSourceId(attachmentFile.getId());
                        edit365Option.setFolderTargetId(folderCreated.getId());
                        edit365OptionList.add(edit365Option);
                        stt++;
                    }
                    this.uploadFile365CustomService.cloneListFile_v2(edit365OptionList, requestDataCopy);


                    requestDataCopy = this.requestDataRepository.save(requestDataCopy);
                    RequestData finalRequestDataCopy = requestDataCopy;
                    CompletableFuture.runAsync(() -> {
                        try {
                            requestDataSearchRepository.save(finalRequestDataCopy);
                        }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
                            log.error("{}", e.getMessage());
                        }catch (Exception e){
                            log.error("{}", e);
                        }
                    });

                    // tạo formData \\
                    List<FormData> formDataList = this.formDataRepository.findAllByRequestDataId(requestDataId);
                    formDataList = formDataList.stream().map(ele -> {
                        ele.setId(null);
                        ele.setRequestData(finalRequestDataCopy);
                        ele.setCreated(creater);
                        ele.setModifiedName(creater.getFullName());
                        ele.setCreatedRankName(createrRankName);
                        ele.setCreatedOrgName(createrOrgName);
                        ele.setCreatedDate(Instant.now());
                        ele.setModified(creater);
                        ele.setModifiedName(creater.getFullName());
                        ele.setModifiedDate(Instant.now());
                        return ele;
                    }).collect(Collectors.toList());
                    formDataList = this.formDataRepository.saveAll(formDataList);
                    // fieldData \\
                    List<FieldData> fieldDataList = this.fieldDataRepository.findAllByRequestDataId(requestDataId);
                    fieldDataList = fieldDataList.stream().map(ele -> {
                        ele.setId(null);
                        ele.setRequestData(finalRequestDataCopy);
                        ele.setCreated(creater);
                        ele.setModifiedName(creater.getFullName());
                        ele.setCreatedRankName(createrRankName);
                        ele.setCreatedOrgName(createrOrgName);
                        ele.setCreatedDate(Instant.now());
                        ele.setModified(creater);
                        ele.setModifiedName(creater.getFullName());
                        ele.setModifiedDate(Instant.now());
                        return ele;
                    }).collect(Collectors.toList());
                    fieldDataList = this.fieldDataRepository.saveAll(fieldDataList);

                    // processData \\
                    ProcessInfo processInfo = request.getProcessInfos().stream().filter(ele -> {
                        return this.processInfoRepository.getAllProcessInfoUserPermission(userId).stream().anyMatch(ele1 -> ele.getId().equals(ele1.getId()));
                    }).findFirst().orElseThrow(() -> new Exception("Not permission."));
                    ProcessData processData = this.copyProcessInfoToProcessData(processInfo, creater, requestDataCopy, createrOrgName, createrRankName);
                    processData = this.processDataRepository.save(processData);

                    // stepData \\
                    Set<StepInProcess> stepInProcessList = processInfo.getStepInProcesses();
                    List<StepData> stepDataList = this.copyStepInProcessToStepData(stepInProcessList, creater, requestDataCopy, processData, createrOrgName, createrRankName);
                    stepDataList = this.stepDataRepository.saveAll(stepDataList);

                    // sign data - manage stamp info (nếu ko phải nhóm yêu cầu Công Văn Đến và Tờ Trình -> thì tạo)\\
                    if(!requestDataCopy.getRequestGroupName().contains(AppConstant.RequestGroupConstant.CONG_VAN_DEN)
                        && !requestDataCopy.getRequestGroupName().contains(AppConstant.RequestGroupConstant.TO_TRINH)){
                        createSignDataAndManageStampInfo(requestDataCopy, creater, createrOrgName, createrRankName);
                    }

                    // history \\
                    ReqdataProcessHis reqdataProcessHis = new ReqdataProcessHis();
                    reqdataProcessHis.setRequestData(requestDataCopy);
                    reqdataProcessHis.setCreateDate(Instant.now());
                    reqdataProcessHis.setOrganizationName(createrOrgName);
                    reqdataProcessHis.setRankName(createrRankName);
                    reqdataProcessHis.setProcesser(creater);
                    reqdataProcessHis.setProcesserName(creater.getFullName());
                    reqdataProcessHis.setProcessDate(Instant.now());
                    reqdataProcessHis.setStatus(status_DANGSOAN.getStatusName());
                    reqdataProcessHis.setIsChild(false);
                    reqdataProcessHis.setIsShowCustomer(false);
                    reqdataProcessHis.setDescription("Soạn thảo");  // text cho giống với frontend
                    this.reqdataProcessHisRepository.save(reqdataProcessHis);

                    // khi tạo thành công -> update numberRequestData của request
                    Long numberRequestData = request.getNumberRequestData() == null ? 0L : request.getNumberRequestData();
                    request.setNumberRequestData(numberRequestData + 1);
                    request = this.requestRepository.save(request);

                    requestDataIdDelete = null;

                    result.put(requestDataId, true);
                }catch (Exception e){
                    log.error("{}", e);

                    try {
                        if(requestDataIdDelete != null){
                            this.requestDataRepository.deleteById(requestDataIdDelete);
                        }
                    }catch (Exception ex){};

                    result.put(requestDataId, false);
                }

            }
        }
        return result;
    }

    public List<AttachmentFileDTO> addAttachmentPrimaryToRequestData(MultipartFile file, Long requestDataId, Long templateFormId, Long userId) throws IOException {
        List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        AttachmentFile folderRequestData = this.attachmentFileRepository.findAllByItemId365(requestData.getIdDirectoryPath()).get(0);

        if(templateFormId != null && templateFormId > 0){                 // nếu là chọn biểu mẫu
//            TemplateForm templateForm = this.templateFormRepository.findById(templateFormId).get();
//            if(templateForm.getAttachmentFiles() != null && !templateForm.getAttachmentFiles().isEmpty()){
            List<AttachmentFile> attachmentFileOfTemplate = this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(templateFormId, null);
            if(attachmentFileOfTemplate != null && !attachmentFileOfTemplate.isEmpty()){
                Long counter = requestData.getNumberAttach() != null ? requestData.getNumberAttach() : 1L;
                for (AttachmentFile attachmentFile : attachmentFileOfTemplate){
                    if(attachmentFile.getRequestData() == null){    // nếu không có requestData -> là file cần clone (file biểu mẫu)
                        Edit365Option edit365Option = new Edit365Option();
                        //edit365Option.setUserId(requestData.getCreated() != null ? requestData.getCreated().getId() : null);
                        edit365Option.setUserId(userId != null ? userId : requestData.getCreated().getId());
                        if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                            List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestDataId);
                            String rules = requestData.getRuleGenerateAttachName();
                            String fileName = String.format("%s_%s.%s", rules, counter, attachmentFile.getFileExtension());
                            try{
                                fileName = generateCodeUtils.v2_generateFileName(Math.toIntExact(counter), attachmentFile.getFileExtension(), rules, requestData.getRequest(), requestData, fieldDataList);
                            }catch (Exception e){
                                log.error("addAttachmentPrimaryToRequestData {}", e);
                            }
                            edit365Option.setFileName(fileName);
                        }else{
                            edit365Option.setFileName(attachmentFile.getFileName());
                        }
                        edit365Option.setSourceId(attachmentFile.getId());
                        edit365Option.setFolderTargetId(folderRequestData.getId());
                        AttachmentFile attachmentFileResult = this.uploadFile365CustomService.cloneFile(edit365Option, requestData);
                        attachmentFileDTOList.add(this.attachmentFileMapper.toDto(attachmentFileResult));
                        counter++;
                    }
                }
            }
            return attachmentFileDTOList;
        }else{                                  // nếu tự upload file lên
            String fileName = file.getOriginalFilename();
            LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFile(fileName, requestData.getIdDirectoryPath(), file.getBytes());

            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setItemId365(uploadResult.responseBody.id);
            attachmentFile.setFileName(fileName);
            attachmentFile.setParentId(folderRequestData.getParentId());
            attachmentFile.setFileExtension(FilenameUtils.getExtension((Strings.isNullOrEmpty(fileName) ? file.getOriginalFilename() : fileName)));
            attachmentFile.setOfice365Path(uploadResult.responseBody.webUrl);
            attachmentFile.setFileSize(uploadResult.responseBody.size);
            attachmentFile.setContentType(uploadResult.responseBody.oDataType);
            attachmentFile.setIsActive(true);
            attachmentFile.setIsFolder(false);
            attachmentFile.setIsDelete(false);
            //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
            if(userId != null){
                UserInfo created = this.userInfoRepository.findById(userId).orElse(null);
                addUserInFo(attachmentFile, created);
            }
            attachmentFile.setCreatedDate(Instant.now());
            attachmentFile.setModifiedDate(Instant.now());

            if(templateFormCustomService.getTEMPLATE_FORM_CUSTOMER() == null) templateFormCustomService.TEMPLATE_FORM_CUSTOMER();
            attachmentFile.setTemplateForm(templateFormCustomService.getTEMPLATE_FORM_CUSTOMER());

            attachmentFile.setRequestData(this.requestDataRepository.findById(requestDataId).orElse(null));

            attachmentFile = this.attachmentFileRepository.save(attachmentFile);
            return Arrays.asList(this.attachmentFileMapper.toDto(attachmentFile));
        }
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

    @Value("${system.file.customer-file-code:CUSTOMER}")
    public String CUSTOMER_FILE_CODE;
    public List<AttachmentFileDTO> viewFileCustomer(Long requestDataId) throws Exception {
        List<AttachmentFileDTO> result = new ArrayList<>();
        Map<String, String> mapFileNameCustomer = new HashMap<>();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        if(requestData.getAttachmentFiles() != null && !requestData.getAttachmentFiles().isEmpty()){
            List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().filter(ele -> ele.getTemplateForm() != null).sorted((att1, att2) -> (int)(att1.getId() - att2.getId())).collect(Collectors.toList());
            attachmentFileList.forEach(ele ->{
                if(CUSTOMER_FILE_CODE.equals(ele.getTennantCode()))
                    mapFileNameCustomer.put(this.getFileNameWithoutExtension(ele.getFileName()), ele.getFileExtension());
            });
            for(AttachmentFile attachmentFile : attachmentFileList){
                if(!CUSTOMER_FILE_CODE.equals(attachmentFile.getTennantCode())){            // nếu không phải là file clone cho kahcs hàng
                    String fileName = this.getFileNameWithoutExtension(attachmentFile.getFileName());
                    if(!mapFileNameCustomer.containsKey(fileName)){   // kiểm tra xem có tên trong map (chứa tên của các file khách hàng) -> TH không có -> tao file mới

                        InputStream inputStream = this.graphService.getFile(attachmentFile.getItemId365(), attachmentFile.getFileExtension().equals(PDF_FORMAT) ? "" : PDF_FORMAT);
                        Download365Option download365Option = new Download365Option();
                        download365Option.setAddQrCode(true);
                        download365Option.setAddWatermark(true);
                        inputStream = this.uploadFile365CustomService.getFileContentWithOption(inputStream, download365Option, attachmentFile);
                        String pdfFileName = fileName + GUI_KHACH_HANG +"." + PDF_FORMAT;
                        LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFile(pdfFileName, requestData.getIdDirectoryPath(), IOUtils.toByteArray(inputStream));

                        AttachmentFile attachmentFileNew = new AttachmentFile();
                        BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
                        attachmentFileNew.setId(null);
                        attachmentFileNew.setTennantCode(CUSTOMER_FILE_CODE);

                        attachmentFileNew.setItemId365(uploadResult.responseBody.id);
                        attachmentFileNew.setFileName(pdfFileName);
                        attachmentFileNew.setParentId(attachmentFile.getParentId());
                        attachmentFileNew.setFileExtension(PDF_FORMAT);
                        attachmentFileNew.setOfice365Path(uploadResult.responseBody.webUrl);
                        attachmentFileNew.setFileSize(uploadResult.responseBody.size);
                        attachmentFileNew.setContentType(uploadResult.responseBody.oDataType);
                        attachmentFileNew.setIsActive(true);
                        attachmentFileNew.setIsFolder(false);
                        attachmentFileNew.setIsDelete(false);
                        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
                        attachmentFileNew.setCreatedDate(Instant.now());
                        attachmentFileNew.setModifiedDate(Instant.now());

                        // 2023/01/09: sửa thêm cho AttachmentPermistion và ChangeFileHistory
                        attachmentFileNew.setChangeFileHistories(new HashSet<>());
                        attachmentFileNew.setAttachmentPermisitions(new HashSet<>());

                        attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);

                        AttachmentFileDTO attachmentFileDTO = new AttachmentFileDTO();
                        BeanUtils.copyProperties(attachmentFileNew, attachmentFileDTO);
                        result.add(attachmentFileDTO);

                        mapFileNameCustomer.put(this.getFileNameWithoutExtension(attachmentFile.getFileName()), attachmentFile.getFileExtension());
                    }
                }else{
                    AttachmentFileDTO attachmentFileDTO = new AttachmentFileDTO();
                    BeanUtils.copyProperties(attachmentFile, attachmentFileDTO);
                    result.add(attachmentFileDTO);
                }
            }
        }
        return result;
    }

    /**
     * Hàm thực hiện lấy ra tên file không bao gồm đuôi file
     * @param fileName
     * @return
     */
    private final String GUI_KHACH_HANG = "_Gửi Khách Hàng";
    private String getFileNameWithoutExtension(String fileName){
        if(Strings.isNullOrEmpty(fileName)) return "";
        fileName = fileName.replace(GUI_KHACH_HANG, "");
        String names[] = fileName.split("\\.");
        String result = String.join("", Arrays.copyOfRange(names, 0, names.length-1));
        return result;
    }


    public RequestDataDTO customUpdate(RequestDataDTO requestDataDTO) {
        log.debug("Request to save RequestData : {}", requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);


        RequestData finalRequestData = requestData;
        CompletableFuture.runAsync(() -> {
            try{
                requestDataSearchRepository.save(finalRequestData);
            }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

            }catch (Exception e){
                log.error("customUpdate: {}", e);
            }
        });


        // nối với các phiếu liên quan
        if(requestDataDTO.getRequestDataList() != null)     // nếu có phiếu liên quan -> nối
            saveReqDataConcerned(requestData, requestDataDTO);

//        RequestDataDTO result = requestDataMapper.toDto(requestData);
        // DuowngTora: 20230209: chỉnh sửa để giảm bớt dữ liệu trả ra
        RequestDataDTO result = this.convertToDTOFindOne(requestData);

        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(ele -> this.convertToDTOMegaIgnoreField(ele)).collect(Collectors.toList());
        result.setRequestDataList(requestDataDTOList);


        return result;
    }

    public Boolean saveReqDataConcerned(RequestData requestData, RequestDataDTO requestDataDTO){
        // nối với các phiếu liên quan
        List<RequestData> requestDataList = new ArrayList<>();
        RequestData finalRequestData = requestData;
        requestDataDTO.getRequestDataList().forEach(ele -> {
            RequestData requestDataTemp = this.requestDataRepository.findById(ele.getId()).get();
            requestDataTemp.setReqDataConcerned(finalRequestData);
            requestDataList.add(requestDataTemp);
        });
        this.requestDataRepository.saveAll(requestDataList);
        return true;
    }

    public List<RequestDataDTO> saveAll(List<RequestDataDTO> requestDTOList) {
        List<RequestDataDTO> result = requestDataRepository.saveAll(requestDTOList.stream().map(requestDataMapper::toEntity).collect(Collectors.toList())).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        log.debug("RequestCustomService saveAll({}): {}", requestDTOList, result);
        return result;
    }

    @Transactional
    public Boolean deleteAll(List<RequestDataDTO> requestDataDTOList){
        log.debug("RequestCustomService deleteAll({})", requestDataDTOList);
        requestDataRepository.deleteAllById(requestDataDTOList.stream().map(ele -> ele.getId()).collect(Collectors.toList()));
        requestDataDTOList.stream().forEach(ele -> {
            try {
                //folderCustomService.deleteFolder(ele.getDescription());
            } catch (Exception e) {e.printStackTrace();}
        });
        return null;
    }

    @Transactional
    public RequestDataDTO customSave_mobile(RequestDataDTO requestDataDTO) throws Exception {
        log.debug("RequestCustomService customSave: {}", requestDataDTO);
        List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
        // generate code
        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);
        Request request = this.requestRepository.findById(requestData.getRequest().getId()).get();

        // save success -> clone templateForm (AttachmentFile);
        // create new folder with code of RequestDataDto
        CreateFolder365Option createFolder365Option = new CreateFolder365Option();
        createFolder365Option.setCreatedId( requestData.getCreated() != null ? requestData.getCreated().getId() : null);
        createFolder365Option.setFolderName( requestData.getRequestDataCode() );
        createFolder365Option.setParentItemId( Strings.isNullOrEmpty(request.getIdDirectoryPath()) ?  this.uploadFile365CustomService.ROOT_FOLDER_ITEM_ID : request.getIdDirectoryPath());
        //createFolder365Option.setParentItemId(request.getIdDirectoryPath());
        AttachmentFileDTO folder = this.folder365CustomService.createFolder(createFolder365Option);
        AttachmentFile folderCreated = this.attachmentFileMapper.toEntity(folder);
//            requestData.setDescription(folderCreated.getIdInFileService());
        requestData.setIdDirectoryPath(folderCreated.getItemId365());
        folderCreated.setRequestData(requestData);
        if(!Strings.isNullOrEmpty(request.getIdDirectoryPath())){            // cập nhật lại folder cha cho folder vừa tạo (folder của phiếu yêu cầu)
            try {
                AttachmentFile folderParent = this.attachmentFileRepository.findAllByItemId365(request.getIdDirectoryPath()).get(0);
                folderCreated.setParentId(folderParent.getId());
            }catch (Exception e){
                log.error("{}", e);
                folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
            }
        }else{
            folderCreated.setParentId(this.uploadFile365CustomService.getROOT_FOLDER() == null ? null : this.uploadFile365CustomService.getROOT_FOLDER().getId());
        }
        this.attachmentFileRepository.save(folderCreated);

        int stt = 1;
        for(AttachmentFileDTO attachmentFileDTO : requestDataDTO.getAttachmentFileList()){
            AttachmentFile attachmentFile = this.attachmentFileMapper.toEntity(attachmentFileDTO);

            Edit365Option edit365Option = new Edit365Option();
            edit365Option.setUserId(requestData.getUserInfos() != null ? requestData.getCreated().getId() : null);

            // sửa quy tắc đặt tên file thành : Số PYC_Tiêu đề (Tiếng việt không đâu)_STT.extension \\
//            edit365Option.setFileName(attachmentFile.getFileName());
            String fileName = requestData.getRequestDataCode() + "_" + vnCharacterUtils.removeAccent(requestData.getTitle() == null ? "" : requestData.getTitle()).replaceAll("\\s", "") + "_" + stt + "." + attachmentFile.getFileExtension();
            if(Constants.TRUE.equals(FEATURE_GENERATE_FILE_NAME)){
                List<FieldData> fieldDataList = fieldDataRepository.findAllByRequestDataId(requestData.getId());
                fileName = generateCodeUtils.v2_generateFileName(stt, attachmentFile.getFileExtension(), requestData.getRuleGenerateAttachName(), request, requestData, fieldDataList);
            }
            edit365Option.setFileName(fileName);

            edit365Option.setSourceId(attachmentFile.getId());
            edit365Option.setFolderTargetId(folderCreated.getId());

            AttachmentFile attachmentFileResult = this.uploadFile365CustomService.cloneFile(edit365Option, requestData);
            attachmentFileResult.parentId(folderCreated.getId());
            this.attachmentFileRepository.save(attachmentFileResult);
            attachmentFileDTOList.add(this.attachmentFileMapper.toDto(attachmentFileResult));
            stt++;
        }


        requestData = this.requestDataRepository.save(requestData);
        // nối với các phiếu liên quan
        if(requestDataDTO.getRequestDataList() != null)     // nếu có phiếu liên quan -> nối
            saveReqDataConcerned(requestData, requestDataDTO);

        RequestDataDTO result = requestDataMapper.toDto(requestData);
        result.setAttachmentFileList(attachmentFileDTOList);

        List<RequestDataDTO> requestDataDTOList = this.requestDataRepository.findAllByReqDataConcernedId(result.getId()).stream().map(ele -> this.convertToDTOMegaIgnoreField(ele)).collect(Collectors.toList());
        result.setRequestDataList(requestDataDTOList);

        try {
            requestDataSearchRepository.save(requestData);
        }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
            log.error("{}", e.getMessage());
        }catch (Exception e){
            log.error("{}", e);
        }

        // tạo quy trình + bước xử lý \\
        ProcessInfo processInfo = this.processInfoRepository.findById(requestDataDTO.getProcessInfoDTO().getId()).get();

        ProcessData processData = new ProcessData();
        processData.setProcessDataName(processInfo.getProcessName());
        processData.setProcessDataCode(processInfo.getProcessCode());
        processData.setIsActive(true);
        processData.setIsDelete(false);
        processData.setRoundNumber(1L);
        processData.setRequestData(requestData);
        processData.setCreated(requestData.getCreated());
        processData.setCreatedDate(requestData.getCreatedDate());
        processData.setCreatedName(requestData.getCreatedName());
        processData.setCreatedOrgName(requestData.getCreatedOrgName());
        processData.setCreatedRankName(requestData.getCreatedRankName());
        processData.setModified(requestData.getModified());
        processData.setModifiedName(requestData.getModifiedName());
        processData.setDescription("");
        processData = this.processDataRepository.save(processData);

        ProcessData finalProcessData = processData;
        RequestData finalRequestData = requestData;
        processInfo.getStepInProcesses().forEach(ele -> {
            StepData stepData = new StepData();
            BeanUtils.copyProperties(ele, stepData);
            stepData.setStepDataName(ele.getStepInProcessName());
            stepData.setStepDataCode(ele.getStepInProcessCode());
            stepData.setProcessData(finalProcessData);
            stepData.setRequestData(finalRequestData);
            stepData.setStepInProcess(ele);
            stepData.setCreatedDate(finalRequestData.getCreatedDate());
            stepData.setCreatedName(finalRequestData.getCreatedName());
            stepData.setCreated(finalRequestData.getCreated());
            stepData.setCreatedOrgName(finalRequestData.getCreatedOrgName());
            stepData.setCreatedRankName(finalRequestData.getCreatedRankName());
            stepData.setMailTemplate(ele.getMailTemplate());
            stepData.setMailTemplateCustomer(ele.getMailTemplateCustomer());
            stepData.setStepOrder(ele.getStepOrder());
            stepData = this.stepDataRepository.save(stepData);
        });

        // tạo form và field \\
        Form form = request.getForm();
        FormData formData = new FormData();
        formData.setRequestData(requestData);
        formData.setForm(form);
        formData.setFormDataCode(form.getFormCode());
        formData.setFormDateName(form.getFormName());
        //formData.setObjectModel(Strings.isNullOrEmpty(form.getObjectModel()) ? "{}" : form.getObjectModel());
        //formData.setObjectSchema(Strings.isNullOrEmpty(form.getObjectSchema()) ? "{}" : form.getObjectSchema());
        formData.setMappingInfo(Strings.isNullOrEmpty(form.getMappingInfo()) ? "[]" : form.getMappingInfo());
        formData.setOption(form.getOption());
        formData.setDescription(form.getDescription());
        formData.setIsDelete(false);
        formData.setIsActive(true);
        formData.setTennantCode(form.getTennantCode());
        formData.setTennantName(form.getTennantName());
        formData.setCreated(requestData.getCreated());
        formData.setCreatedDate(requestData.getCreatedDate());
        formData.setCreatedOrgName(requestData.getCreatedOrgName());
        formData.setCreatedRankName(requestData.getCreatedRankName());
        formData.setModified(requestData.getModified());
        formData.setModifiedName(requestData.getModifiedName());
        formData.setModifiedDate(requestData.getModifiedDate());
        formData = this.formDataRepository.save(formData);

        FormData finalFormData = formData;
        form.getFieldInForms().forEach(ele -> {
            FieldData fieldData = new FieldData();
            BeanUtils.copyProperties(ele, fieldData);
            fieldData.setRequestData(finalRequestData);
            fieldData.setFormData(finalFormData);
            fieldData.setField(ele.getField());
            fieldData.setFieldDataName(ele.getNameOfField());
            try {
                fieldData.setFieldDataCode(Arrays.stream(VNCharacterUtils.removeAccent(ele.getNameOfField()).toLowerCase().split("[^a-zA-Z0-9]+")).collect(Collectors.joining("_")));
            }catch (Exception e){log.error("{}", e);}
            fieldData.setNameOfField(ele.getNameOfField());
            fieldData.setCreated(finalRequestData.getCreated());
            fieldData.setCreatedName(finalRequestData.getCreatedName());
            fieldData.setCreatedOrgName(finalRequestData.getCreatedOrgName());
            fieldData.setCreatedRankName(finalRequestData.getCreatedRankName());
            fieldData.setCreatedDate(finalRequestData.getCreatedDate());
            fieldData.setModified(finalRequestData.getModified());
            fieldData.setModifiedName(finalRequestData.getModifiedName());
            fieldData.setModifiedDate(finalRequestData.getModifiedDate());
            fieldData.setObjectSchema(Strings.isNullOrEmpty(ele.getObjectSchema()) ? "{}" : ele.getObjectSchema());
            fieldData.setOption(ele.getOption());
            fieldData.setRow(ele.getRow());
            fieldData.setCol(ele.getCol());
            fieldData.setOrder(ele.getOrder());
            fieldData.setDescription(ele.getDescription());
            JSONObject jsonObjectTitle = new JSONObject(ele.getObjectSchema()).getJSONObject("title");
            if(jsonObjectTitle.has("Headers")){
                fieldData.setTennantCode("truong_bang");
                fieldData.setTennantName("truong_bang");
            }else if(jsonObjectTitle.has("upload_ext")){
                fieldData.setTennantCode("truong_file");
                fieldData.setTennantName("truong_file");
            }else if(jsonObjectTitle.has("type") && "number".equalsIgnoreCase(jsonObjectTitle.getString("type")) && !jsonObjectTitle.has("double")){
                fieldData.setTennantCode("number");
                fieldData.setTennantName("number");
            }else if(jsonObjectTitle.has("type") && "number".equalsIgnoreCase(jsonObjectTitle.getString("type")) && jsonObjectTitle.has("double")){
                fieldData.setTennantCode("double");
                fieldData.setTennantName("double");
            }else{
                fieldData.setTennantCode("");
                fieldData.setTennantName("");
            }
            fieldData = this.fieldDataRepository.save(fieldData);
        });

        // thông tin ký số & quản lý đóng dấu \\
        // tạm dùng trường is_create_outgoing_doc   -> true thì sẽ là công văn (sẽ không cần tạo thông tin ký số và quản lý đóng dấu)
        if(requestDataDTO.getIsCreateOutgoingDoc() == null || !requestDataDTO.getIsCreateOutgoingDoc()){
            ManageStampInfo manageStampInfo = new ManageStampInfo();
            manageStampInfo.setRequestData(requestData);
            manageStampInfo.setAddress("");
            manageStampInfo.setContent("");
            manageStampInfo.setCopiesNumber(0L);
            manageStampInfo.setCreater(requestData.getCreated());
            manageStampInfo.setCreatedOrgName(requestData.getCreatedOrgName());
            manageStampInfo.setCreatedRankName(requestData.getCreatedRankName());
            manageStampInfo.setCreatedName(requestData.getCreatedName());
            manageStampInfo.setCreatedDate(requestData.getCreatedDate());
            manageStampInfo.setModifier(requestData.getModified());
            manageStampInfo.setModifiedName(requestData.getModifiedName());
            manageStampInfo.setModifiedDate(requestData.getModifiedDate());
            manageStampInfo.setEmail("");
            manageStampInfo.setIsActive(true);
            manageStampInfo.setIsDelete(false);
            manageStampInfo.setName("");
            manageStampInfo.setOrgReturnName("");
            manageStampInfo.setOrgStorages(new HashSet<>());
            manageStampInfo.setPhoneNumber("");
            manageStampInfo.setStampCode("");
            manageStampInfo.setStampName("");
            manageStampInfo.setStamperName("");
            manageStampInfo.setStatus("");
            manageStampInfo.setStorageCode("");
            manageStampInfo.setStorageLocation("");
            manageStampInfo.setStoragePosition("");
            manageStampInfo = this.manageStampInfoRepository.save(manageStampInfo);

            SignData signData = new SignData();
            signData.setRequestData(requestData);
            signData.setAddress("");
            signData.setCreatedOrgName(requestData.getCreatedOrgName());
            signData.setCreatedRankName(requestData.getCreatedRankName());
            signData.setCreatedName(requestData.getCreatedName());
            signData.setCreatedDate(requestData.getCreatedDate());
            signData.setModifiedName(requestData.getModifiedName());
            signData.setModifiedate(requestData.getModifiedDate());
            signData.setEmail("");
            signData.setIsActive(true);
            signData.setIsDelete(false);
            signData.setNumberSign(0L);
            signData.setNumberDownload(0L);
            signData.setNumberPrint(0L);
            signData.setNumberView(0L);
            signData.setOrgName("");
            signData.setPhoneNumber("");
            signData.setRankName("");
            signData.setSignDate(requestData.getCreatedDate());
            signData.setSignName("");
            signData.setSignTypeCode("");
            signData.setSignTypeName("");
            signData.setSignature("");
            signData = this.signDataRepository.save(signData);
        }


        // khi tạo thành công -> update numberRequestData của request
        Long numberRequestData = request.getNumberRequestData() == null ? 0L : request.getNumberRequestData();
        request.setNumberRequestData(numberRequestData + 1);
        request = this.requestRepository.save(request);

        return result;
    }

    public RequestDataDTO getStatusOfRequestData(Long requestDataId){
        RequestDataDTO result = convertToDTOMegaIgnoreField(this.requestDataRepository.findById(requestDataId).get());
        log.debug("RequestCustomService getStatusOfRequestData({}), {}", requestDataId, result);
        return result;
    }

    public List<RequestDataDTO> getAllByUserId(Long userId){
        Set<RequestData> result = this.requestDataRepository.findAllByCreatedId(userId).stream().collect(Collectors.toSet());
        log.debug("RequestCustomService findAllByUserId({})", userId);
        Set<UserInStep> userInStepSet = this.userInStepRepository.findAllByUserInfoId(userId).stream().collect(Collectors.toSet());
        userInStepSet.forEach(ele -> {
            Set<StepInProcess> stepInProcessList = this.stepInProcessRepository.findAllByUserInSteps(ele).stream().collect(Collectors.toSet());
            stepInProcessList.forEach(ele1 -> {
                Set<StepData> stepDataList = this.stepDataRepository.findAllByStepInProcess(ele1).stream().collect(Collectors.toSet());
                stepDataList.forEach(ele2 -> {
                    Set<RequestData> requestDataList = this.requestDataRepository.findAllByStepData(ele2).stream().collect(Collectors.toSet());
                    result.addAll(requestDataList);
                });
            });
        });
        return result.stream().map(requestDataMapper::toDto).collect(Collectors.toList());
    }

    public List<RequestDataDTO> getAllByUserId_v1(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllByUserId(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<RequestDataDTO> getAllByUserId_v2(Long userId){
        List<RequestDataDTO> result = new ArrayList<>();
        List<RequestData> requestDataList = this.requestDataRepository.getAllByUserId(userId);
        requestDataList.stream().forEach(ele -> {
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            BeanUtils.copyProperties(ele, requestDataDTO);
            result.add(requestDataDTO);
        });
        return result;
    }

    public Long countAllByUserId_v1(Long userId){
        Long result = this.requestDataRepository.countAllByUserId(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataNeedHandle(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataNeedHandle(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public Long countAllRequestDataNeedHandle(Long userId){
        Long result = this.requestDataRepository.countAllRequestDataNeedHandle(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataOverdue(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataOverDue(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public Long countAllRequestDataOverdue(Long userId){
        Long result = this.requestDataRepository.countAllRequestDataOverDue(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataAboutToExpire(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataAboutToExpire(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public Long countAllRequestDataAboutToExpire(Long userId){
        Long result = this.requestDataRepository.countAllRequestDataAboutToExpire(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataSharedToUser(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataSharedToUser(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public Long countAllRequestDataSharedToUser(Long userId){
        Long result = this.requestDataRepository.countAllRequestDataSharedToUser(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataInProcessingTime(Long userId){
        List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataInProcessingTime(userId).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public Long countAllRequestDataInProcessingTime(Long userId){
        Long result = this.requestDataRepository.countAllRequestDataInProcessingTime(userId);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataDrafting(Long userId){
        //List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataDrafting(userId).parallelStream().map(requestDataMapper::toDto).collect(Collectors.toList());
        //return result;
        List<RequestDataDTO> result = new ArrayList<>();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataDrafting(userId);
        requestDataList.stream().forEach(ele -> {
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            BeanUtils.copyProperties(ele, requestDataDTO);
            result.add(requestDataDTO);
        });
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataDraftingOfMe(Long userId){
        List<RequestDataDTO> result = new ArrayList<>();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataDrafting(userId);
        requestDataList.stream().forEach(ele -> {
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            BeanUtils.copyProperties(ele, requestDataDTO);
            result.add(requestDataDTO);
        });
        return result;
    }

    public Long countAllRequestDataDrafting(Long userid){
        Long result = this.requestDataRepository.countAllRequestDataDrafting(userid);
        return result;
    }

    public List<RequestDataDTO> getAllRequestDataFollowing(Long userId){
        //List<RequestDataDTO> result = this.requestDataRepository.getAllRequestDataFollowing(userId).parallelStream().map(requestDataMapper::toDto).collect(Collectors.toList());
        //return result;
        List<RequestDataDTO> result = new ArrayList<>();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowing(userId);
        requestDataList.stream().forEach(ele -> {
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            BeanUtils.copyProperties(ele, requestDataDTO);
            result.add(requestDataDTO);
        });
        return result;
    }

    public Long countAllRequestDataFollowing(Long userid){
        Long result = this.requestDataRepository.countAllRequestDataFollowing(userid);
        return result;
    }

    public List<RequestDataDTO> getRequestDataWithOption(List<ConditionQuery> conditionQueryList){
        Specification<RequestData> specification = RequestDataSpecification.buildQuery(conditionQueryList, RequestData.class);
        return this.requestDataRepository.findAll(specification).stream().map(requestDataMapper::toDto).collect(Collectors.toList());
    }

    @Autowired
    public EntityManager entityManager;
    public List<Object[]> testGroupBy(List<String> groupFieldName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<RequestData> root = criteriaQuery.from(RequestData.class);
        List<Expression<?>> listGroupBy = new ArrayList<>();
        List<Selection<?>> listSelect = new ArrayList<>();
        for(String fieldName : groupFieldName){
            listSelect.add(root.get(fieldName));
            listGroupBy.add(root.get(fieldName));
        }
        listSelect.add(criteriaBuilder.count(root));
        criteriaQuery.multiselect(listSelect).groupBy(listGroupBy);
        List<Object[]> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result;
    }

    public List<Object> statisticWithOption(List<String> groupFieldName) throws JsonProcessingException {
        return AbstractSpecification.groupQuery(groupFieldName, RequestData.class, entityManager);
    }

    public List<RequestData> getAllByStatusId(Long statusId, Long userId){
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusId(statusId);
        return requestDataList.stream().filter(ele -> {
            return (ele.getCreated().getId() == userId || ele.getStepData().stream().anyMatch(ele1 -> ele.getUserInfos().stream().anyMatch(ele2 -> ele2.getId() == userId)));
        }).collect(Collectors.toList());
    }

    public RequestDataDTO updateContractNumberRequestData(Long requestDataId, RequestDataDTO requestDataDTO){
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        requestData.setContractNumber(requestDataDTO.getContractNumber());
        requestData = this.requestDataRepository.save(requestData);
        try {
            requestDataSearchRepository.save(requestData);
        }catch (UncategorizedElasticsearchException  | GenericJDBCException | StackOverflowError e){
            log.debug(e.getMessage());
        }catch (Exception e){
            log.error("updateContractNumberRequestData: {}", e);
        }
        return this.requestDataMapper.toDto(requestData);
    }

    @Autowired private RequestRepository requestRepository;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    public String generateCode(RequestDataDTO requestDataDTO){
        StringBuilder result = new StringBuilder();
        Request request = this.requestRepository.getById(requestDataDTO.getRequest().getId());
        request.getRequestType().getRequestTypeCode();
        result.append(request.getRequestType().getRequestTypeCode()).append("/").append(this.simpleDateFormat.format(new Date())).append(request.getId());
        return result.toString();
    }

    // utils \\
    private AttachmentFile convertAttachDocumentToFoder(AttachDocument attachDocument, RequestData requestData) throws JsonProcessingException {
        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setIsFolder(true);
        attachmentFile.setIdInFileService(attachDocument.getId());
        attachmentFile.setFileName(requestData.getRequestDataCode());
        attachmentFile.setPath(attachDocument.getFilePath());
        attachmentFile.setOfice365Path("");
        attachmentFile.setDescription(new ObjectMapper().writeValueAsString(attachDocument));
        attachmentFile.setCreatedDate(Instant.now());
        attachmentFile.setCreated(requestData.getCreated());
        attachmentFile.setCreatedName(requestData.getCreated().getFullName());
        attachmentFile.setModified(requestData.getModified());
        attachmentFile.setModifiedName(requestData.getModified().getFullName());
        attachmentFile.setModifiedDate(Instant.now());
        attachmentFile.setIsActive(true);

        attachmentFile.setRequestData(requestData);
        return attachmentFile;
    }




    // binding data form to file \\

    private void saveDataFormToFile(RequestData requestData) throws Exception {
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(requestData.getMappingInfo());
        }catch (Exception e){
            jsonObject = new JSONObject(requestData.getRequest().getMappingInfo());
        }
        List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(requestData.getId()).stream().filter(ele -> ele.getTemplateForm() != null).collect(Collectors.toList());
        List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestData.getId());
        List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestData.getId());
        if(attachmentFileList != null && !attachmentFileList.isEmpty()){
            for (AttachmentFile attachmentFile : attachmentFileList) {
                JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
                FormData formData = requestData.getFormData().stream().collect(Collectors.toList()).get(0);
                writeDataToFile(attachmentFile, formData, jsonArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);

                //clear memory
                try {
                    jsonArray = null;
                    formData = null;
                }catch (Exception e){log.error("{}", e);}

            }
        }

    }

    @Autowired
    private GraphService gapGraphService;
    @Autowired
    private WordUtils wordUtils;
    @Autowired
    private ExcelUtils excelUtils;

    @Autowired
    private WordXmlUtils wordXmlUtils;

    @Autowired
    private WordXmlUtilsV2 wordXmlUtilsV2;

    @Autowired
    private FileUtils fileUtils;

    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    public String FOLDER_TEMP;
    public String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    public void writeDataToFile(AttachmentFile attachmentFile, FormData formData ,JSONArray jsonArray, String itemId365Update, RequestData requestData, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList) throws Exception {

        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if (!folderTemp.exists()) folderTemp.mkdirs();
        File file = null;
        FileInputStream fileInputStream = null;
        try {
            file = cloneFile365ToLocal(attachmentFile, folderTemp);
            Map<String, Object> props = getDataFromForm(formData, attachmentFile.getTemplateForm(), jsonArray, requestData, signDataList, manageStampInfoList);
            fileInputStream = new FileInputStream(file);
            OutputStream outputStream = null;
            switch (attachmentFile.getFileExtension()) {
                case "docx":
                    //outputStream = this.wordUtils.writeTextToFile_bak(props, fileInputStream);
                    outputStream = this.wordXmlUtils.writeTextToFile(props, fileInputStream);
                    //outputStream = this.wordXmlUtilsV2.writeTextToFile(props, fileInputStream);
                    break;
                case "xlsx":
                    outputStream = this.excelUtils.writeTextToFile(props, fileInputStream);
                    break;
                default:
                    log.info("no support type: {}", attachmentFile.getFileExtension());
                    return;
            }
            if (outputStream != null) {
                byte[] fileContent = ((ByteArrayOutputStream) outputStream).toByteArray();
                if (fileContent == null || fileContent.length <= 0) return;
                DriveItem driveItem = this.gapGraphService.updateFile(itemId365Update, fileContent);
                attachmentFile.setFileSize(driveItem.size);
                this.attachmentFileRepository.save(attachmentFile);
                // clear memory
                try {outputStream.close();}catch (Exception ex){log.error("{}", ex);}
            }
        } catch (FileNotFoundException e) {
            log.error("{}", e);
        } finally {
            fileInputStream.close();
            if (file != null)
                file.delete();
            folderTemp.delete();
        }


    }

    public File cloneFile365ToLocal(AttachmentFile attachmentFile, File folderInLocal) throws Exception {
        InputStream inputStream = this.gapGraphService.getFile(attachmentFile.getItemId365(), null);
        File result = new File(folderInLocal.getAbsolutePath() + this.PATH_SEPARATOR +  fileUtils.checkFileName(attachmentFile.getFileName()));
        if(!result.exists()) result.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(result);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();

        // clear memory
        try {
            bytes = null;
        }catch (Exception e){log.error("{}", e);}

        return result;
    }



    @Autowired
    private Environment environment;
    private Map<String, Object> getDataFromForm(FormData formData, TemplateForm templateForm, JSONArray jsonArray, RequestData requestData, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList){
        String REQ_DATA_CODE = environment.getProperty("system.fill-in.MA_HOP_DONG", "${{req_data_code}}");
        REQ_DATA_CODE = REQ_DATA_CODE.replace("\\","");
        Map<String, Object> result = new HashMap<>();
        result.put(REQ_DATA_CODE, requestData != null ? requestData.getRequestDataCode() : "");
        String BRAND_LIST_CODE = environment.getProperty("system.fill-in.KY_HIEU_CHUOI", "${{brand_list}}");
        String BRAND_LIST_DELIMITER = environment.getProperty("system.fill-in.NGAN_CACH_CHUOI");
        BRAND_LIST_CODE = BRAND_LIST_CODE.replace("\\", "");
        result.put(BRAND_LIST_CODE, "");
        if(signDataList != null && signDataList.size() > 0){
            try{
                String brandList = signDataList.stream().map(e->e.getSignName()).collect(Collectors.joining(System.lineSeparator()));
                brandList += System.lineSeparator() + manageStampInfoList.stream().map(e->e.getName()).collect(Collectors.joining(System.lineSeparator()));
                result.put(BRAND_LIST_CODE, brandList);
                log.info("FEATURE_FILL_SIGN_DATA: requestDataId={}, signDataList={}, brandList={}", requestData.getId(), signDataList, brandList);
            }catch (Exception e){
                log.error("brandList: {}", e);
            }
        }
        JSONArray templateMapping = new JSONArray(templateForm.getMappingInfo());
        Map<String, String> templateMappingData = new HashMap<>();
        for(int i=0; i<templateMapping.length(); i++){
            try {
                JSONObject ele = templateMapping.getJSONObject(i);
                templateMappingData.put(ele.getString("name"), ele.getString("position"));
            }catch (Exception e){
                log.error("{}", e);
            }
        }

        JSONObject jsonObject = new JSONObject(formData.getObjectModel());

        // maping lại theo mã \\
        JSONObject jsonSchema = new JSONObject(formData.getObjectSchema());
        JSONObject properties =  jsonSchema.getJSONObject("properties");
        Iterator<String> keys = jsonObject.keys();
        JSONObject jsonObjectNew = new JSONObject();
        while(keys.hasNext()){
            try {
                String key = keys.next();
                JSONObject temp = properties.getJSONObject(key);
                if(temp.has("Headers")){
                    TableFieldDuowngTora tableFieldDuowngTora = new TableFieldDuowngTora();
                    JSONArray headers = new JSONArray(temp.getString("Headers"));
                    for(int i=0; i<headers.length(); i++){
                        tableFieldDuowngTora.getHeaders().add(headers.getJSONObject(i).toString());
                    }
                    JSONArray body = jsonObject.getJSONArray(key);
                    for(int i=0; i<body.length(); i++){
                        tableFieldDuowngTora.getBody().add(body.getJSONObject(i).toString());
                    }
                    jsonObjectNew.put(temp.getString("title"), tableFieldDuowngTora);
                    result.put(temp.getString("title"), tableFieldDuowngTora);              // nếu là trường bảng -> thêm luôn vào mapping không cần mapping với cấu hình
                }else if(temp.has("contentMediaType") && jsonObject.getJSONObject(key).has("data")){
                    String base64Data = jsonObject.getJSONObject(key).getString("data");
                    byte[] base64Content = Base64.getDecoder().decode(base64Data.getBytes(StandardCharsets.UTF_8));
                    //result.put(temp.getString("title"), base64Content);
                    jsonObjectNew.put(temp.getString("title"), base64Content);
                }else{
                    jsonObjectNew.put(temp.getString("title"), jsonObject.get(key));
                }
            }catch (Exception e){
                log.debug("{}", e);
            }
        }

        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject jsonObjectEle = jsonArray.getJSONObject(i);
                String key = templateMappingData.get(jsonObjectEle.getString("position"));
                Object value = jsonObjectNew.get(jsonObjectEle.getString("name"));
                if(key != null && value != null){
                    result.put(key, value);
                }
            }catch (Exception e){
                log.error("{}", e);
            }
        }

        // clear memory
        try {
            templateMapping = null;
            jsonObjectNew = null;
            templateMappingData = null;
        }catch (Exception e){
            log.error("{}", e);
        }

        return result;
    }



    // ultils \\
    private final List<String> ignoreFieldField = Arrays.asList("modified", "status", "subStatus", "reqDataConcerned", "approver", "revoker", "userInfos", "manageStampInfo", "formData", "attachmentFiles", "reqdataProcessHis", "reqdataChangeHis", "processData", "stepData", "fieldData", "informationInExchanges", "tagInExchanges", "requestRecalls", "oTPS", "signData");
    private RequestDataDTO convertToDTO(RequestData requestData){
        if(requestData == null) return null;
        RequestDataDTO result = new RequestDataDTO();
        BeanUtils.copyProperties(requestData, result, ignoreFieldField.stream().toArray(String[]::new));
        return result;
    }

    /**
     * hàm dùng để convert entity sang dto -> chỉ quan tâm đến trường trạng thái (status)
     * @param requestData
     * @return
     */
    private RequestDataDTO convertToDTOMegaIgnoreField(RequestData requestData){
        if(requestData == null) return null;
        RequestDataDTO result = new RequestDataDTO();
        BeanUtils.copyProperties(requestData, result);
        if(result.getStatus() == null && requestData.getStatus() != null){
            StatusDTO statusDTO = new StatusDTO();
            BeanUtils.copyProperties(requestData.getStatus(), statusDTO);
            result.setStatus(statusDTO);
        }
        return result;
    }

    private ProcessData copyProcessInfoToProcessData(ProcessInfo processInfo, UserInfo userInfo, RequestData requestData, String orgNameString, String rankNameString){
        if(processInfo == null) return null;
        ProcessData processData = new ProcessData();
        BeanUtils.copyProperties(processInfo, processData);
        processData.setId(null);
        processData.setIsActive(true);
        processData.setIsDelete(false);
        processData.setRoundNumber(1L);
        processData.setRequestData(requestData);
        processData.setCreated(userInfo);
        processData.setCreatedDate(Instant.now());
        processData.setCreatedName(userInfo.getFullName());
        processData.setCreatedRankName(rankNameString);
        processData.setCreatedOrgName(orgNameString);
        processData.setModified(userInfo);
        processData.setModifiedDate(Instant.now());
        processData.setModifiedName(userInfo.getFullName());
        processData.setProcessDataCode(processInfo.getProcessCode());
        processData.setProcessDataName(processInfo.getProcessName());

        return processData;
    }

    private List<StepData> copyStepInProcessToStepData(Set<StepInProcess> stepInProcessSet, UserInfo userInfo, RequestData requestData, ProcessData processData,String orgNameString, String rankNameString){
        if(stepInProcessSet == null) return null;
        List<StepData> stepDataSet = new ArrayList<>();
        stepInProcessSet.forEach(ele -> {
            StepData stepData = new StepData();
            BeanUtils.copyProperties(ele, stepData);
            stepData.setId(null);
            stepData.setStepDataName(ele.getStepInProcessName());
            stepData.setStepDataCode(ele.getStepInProcessCode());
            stepData.setStepInProcess(ele);
            stepData.setProcessData(processData);
            stepData.setRequestData(requestData);
            stepData.setIsActive(false);
            stepData.setTimeActive(null);
            stepData.setProcessingTermTime(null);
            stepData.setCreated(userInfo);
            stepData.setCreatedDate(Instant.now());
            stepData.setCreatedName(userInfo.getFullName());
            stepData.setCreatedRankName(rankNameString);
            stepData.setCreatedOrgName(orgNameString);
            stepData.setModified(userInfo);
            stepData.setModifiedDate(Instant.now());
            stepData.setModifiedName(userInfo.getFullName());
            stepData.setUserInfos(ele.getUserInSteps().stream().map(ele1 -> ele1.getUserInfo()).collect(Collectors.toSet()));
            stepData.setRoundNumber(1L);
            stepDataSet.add(stepData);
        });
        if(requestData.getRequestGroupName().contains(AppConstant.RequestGroupConstant.CONG_VAN_DEN)){          // nếu là công văn đến active bước 1 và thêm người tạo vào trong người xử lý
            int indexStepDataFirst = IntStream.range(0, stepDataSet.size()).filter(index -> 1L == stepDataSet.get(index).getStepOrder()).findFirst().getAsInt();
            stepDataSet.get(indexStepDataFirst).setIsActive(true);
            stepDataSet.get(indexStepDataFirst).setTimeActive(Instant.now());
            if(stepDataSet.get(indexStepDataFirst).getProcessingTerm() != null && stepDataSet.get(indexStepDataFirst).getProcessingTerm() > 0){
                Double secondPlus = (stepDataSet.get(indexStepDataFirst).getProcessingTerm() * 3600);           // do chuyển từ Hours -> second => cần nhân thêm 60*60
                stepDataSet.get(indexStepDataFirst).setProcessingTermTime(Instant.now().plus(secondPlus.longValue(), ChronoUnit.SECONDS));
                    // thêm người tạo vào quy trình
                if(stepDataSet.get(indexStepDataFirst).getUserInfos() != null)
                    stepDataSet.get(indexStepDataFirst).getUserInfos().add(userInfo);
                else {
                    Set<UserInfo> userInfoSet = new HashSet<>();
                    userInfoSet.add(userInfo);
                    stepDataSet.get(indexStepDataFirst).setUserInfos(userInfoSet);
                }
            }else{
                stepDataSet.get(indexStepDataFirst).setProcessingTermTime(null);
            }
        }
        return stepDataSet;
    }

    private void createSignDataAndManageStampInfo(RequestData requestData, UserInfo userInfo, String orgNameString, String rankNameString){
        ManageStampInfo manageStampInfo = new ManageStampInfo();
        manageStampInfo.setRequestData(requestData);
        manageStampInfo.setCreater(userInfo);
        manageStampInfo.setCreatedName(userInfo.getFullName());
        manageStampInfo.setCreatedDate(Instant.now());
        manageStampInfo.setCreatedRankName(rankNameString);
        manageStampInfo.setCreatedOrgName(orgNameString);
        manageStampInfo.setModifier(userInfo);
        manageStampInfo.setModifiedName(userInfo.getFullName());
        manageStampInfo.setModifiedDate(Instant.now());
        manageStampInfo.setCopiesNumber(0L);
        manageStampInfo.setContent("");
        manageStampInfo.setStampName("");
        manageStampInfo.setStampCode("");
        manageStampInfo.setEmail("");
        manageStampInfo.setName("");
        manageStampInfo.setPhoneNumber("");
        manageStampInfo.setAddress("");
        manageStampInfo.setStamperName("");
        manageStampInfo.setIsActive(true);
        manageStampInfo.setIsDelete(false);
        this.manageStampInfoRepository.save(manageStampInfo);
        SignData signData = new SignData();
        signData.setRequestData(requestData);
        signData.setCreatedName(userInfo.getFullName());
        signData.setCreatedDate(Instant.now());
        signData.setCreatedRankName(rankNameString);
        signData.setCreatedOrgName(orgNameString);
        signData.setModifiedName(userInfo.getFullName());
        signData.setModifiedate(Instant.now());
        signData.setSignName("");
        signData.setEmail("");
        signData.setPhoneNumber("");
        signData.setAddress("");
        signData.setNumberSign(0L);
        signData.setNumberPrint(0L);
        signData.setNumberDownload(0L);
        signData.setNumberView(0L);
        signData.setIsActive(true);
        signData.setIsDelete(false);
        this.signDataRepository.save(signData);
    }

    public static class TableFieldDuowngTora {
        private List<String> headers = new ArrayList<>();
        private List<String> body = new ArrayList<>();

        public TableFieldDuowngTora() {
        }

        public TableFieldDuowngTora(List<String> headers, List<String> body) {
            this.headers = headers;
            this.body = body;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public List<String> getBody() {
            return body;
        }

        public void setBody(List<String> body) {
            this.body = body;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println("start DuowngTora: " + finalI);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end DuowngTora: " + finalI);
                return "done DuowngTora: " + finalI;
            }, executor);
        }
        System.out.println("ENDDDDDD");
        executor.shutdown();
    }

}
