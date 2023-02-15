package com.vsm.business.service.custom.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.custom.file.bo.AttachDocument;
import com.vsm.business.service.custom.file.bo.CreateFolderOption;
import com.vsm.business.service.custom.file.bo.response.BaseClientRp;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.CallAPIUtils;
import com.vsm.business.utils.ConditionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FolderCustomService {

    @Value("${fileServer.url.baseUrl}")
    private String urlFileSercice;

    @Value("${fileServer.url.folder.create:/folder}")
    private String urlCreateFolder;

    @Value("${fileServer.url.folder.show:/folder/}")
    private String urlShowFolderContent;

    @Value("${fileServer.url.folder.delete:/folder/}")
    private String urlDeleteFolder;

    @Value("${url.folder.restore:/folder/undo-delete-folder/}")
    private String urlRestoreFolder;

    @Value("${url.folder.copy:/folder/copy/}")
    private String urlCopyFolder;

    @Value("${url.folder.rename:/folder/}")
    private String urlRenameFolder;

    @Autowired
    private CallAPIUtils callAPIUtils;

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private AttachmentFileMapper attachmentFileMapper;

    @Autowired
    private ConditionUtils conditionUtils;

    @Autowired
    private UserInfoRepository userInfoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BaseClientRp createFolder(CreateFolderOption createFolderOption) throws Exception {
        String url = this.urlFileSercice + this.urlCreateFolder;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, createFolderOption, HttpMethod.POST, null, BaseClientRp.class);
        return result;
    }

    public BaseClientRp createFolder_v1(CreateFolderOption createFolderOption) throws Exception {
        String url = this.urlFileSercice + this.urlCreateFolder;


        UserInfo created = null;
        if(createFolderOption.getCreatedId() != null)
            created = this.userInfoRepository.findById(createFolderOption.getCreatedId()).orElse(null);

        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, createFolderOption, HttpMethod.POST, null, BaseClientRp.class);
        if (result.getState()){
            AttachDocument attachDocument = this.objectMapper.convertValue(result.getData(), AttachDocument.class);
            AttachmentFile attachmentFile = this.attachDocumentToAttachmentFile(attachDocument, createFolderOption.getFileName(), createFolderOption.getParentId());
            attachmentFile.setCreated(created);
            attachmentFile.setCreatedName(created != null ? created.getFullName() : null);
            attachmentFile.setModified(created);
            attachmentFile.setModifiedName(created != null ? created.getFullName() : null);
            this.attachmentFileRepository.save(attachmentFile);
        }
        return result;
    }

    public BaseClientRp showFolderContent(String id) throws Exception {
        String url = this.urlFileSercice + this.urlShowFolderContent + id;
        List<AttachmentFile> resultAttachmentFile = new ArrayList<>();
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.GET, null, BaseClientRp.class);
        return result;
    }

    public List<AttachmentFileDTO> showFolderContent_v1(String id) throws Exception {
//        String url = this.urlFileSercice + this.urlShowFolderContent + id;
//        List<AttachmentFile> resultAttachmentFile = new ArrayList<>();
//        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.GET, null, BaseClientRp.class);
//        if(result.getState()){
//            List<AttachDocument> attachDocumentList = this.objectMapper.convertValue(result.getData(), new TypeReference<List<AttachDocument>>(){});
//            if(attachDocumentList != null){
//                attachDocumentList.forEach(ele -> {
//                    List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(ele.getId());
//                    if(attachmentFileList != null && attachmentFileList.size() > 0){
//                        resultAttachmentFile.add(attachmentFileList.get(0));
//                    }
//                });
//            }
//        }
//        return resultAttachmentFile.stream().map(ele -> attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
        List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(id);
        if(attachmentFileList == null || attachmentFileList.size() < 1) throw  new Exception("Folder not found");
        List<AttachmentFileDTO> result = this.attachmentFileRepository.findAllByParentId(attachmentFileList.get(0).getId()).stream().filter(ele -> this.conditionUtils.checkTrueFalse(ele.getIsActive())).map(this.attachmentFileMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public BaseClientRp deleteFolder(String id) throws Exception {
        String url = this.urlFileSercice + this.urlDeleteFolder + id;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.DELETE, null, BaseClientRp.class);
        if(result.getState()){
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(id);
            if (attachmentFileList != null && attachmentFileList.size() > 0) {
                AttachmentFile attachmentFile = attachmentFileList.get(0);
                this.attachmentFileRepository.deleteFolder(attachmentFile.getId());
            }
        }
        return result;
    }

    public BaseClientRp restoreFolder(String id) throws Exception {
        String url = this.urlFileSercice + this.urlRestoreFolder + id;
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.GET, null, BaseClientRp.class);
        if(result.getState()){
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(id);
            if (attachmentFileList != null && attachmentFileList.size() > 0) {
                AttachmentFile attachmentFile = attachmentFileList.get(0);
                this.attachmentFileRepository.restoreFolder(attachmentFile.getId());
            }
        }
        return result;
    }

    public BaseClientRp copyFolder(String id, String targetId) throws Exception {
        String url = this.urlFileSercice + this.urlCopyFolder + id;
        Map<String, Object> params = new HashMap<>();
        params.put("targetId", targetId);
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.GET, params, BaseClientRp.class);
        return  result;
    }

//    public List<AttachmentFileDTO> copyFolder_v1(String id, String targetId) throws Exception {
//        String url = this.urlFileSercice + this.urlCopyFolder + id;
//        List<AttachmentFile> result = new ArrayList<>();
//        Map<String, Object> params = new HashMap<>();
//        params.put("targetId", targetId);
//        BaseClientRp fileServieResult = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.GET, params, BaseClientRp.class);
//        if(fileServieResult.getState()){
//            List<AttachDocument> attachDocumentList = this.objectMapper.convertValue(fileServieResult.getData(), new TypeReference<List<AttachDocument>>() {});
//            if(attachDocumentList != null){
//                attachDocumentList.forEach(ele -> {
//                    List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(ele.getId());
//                    AttachmentFile attachmentFile = (attachmentFileList != null && attachmentFileList.size() > 0)? attachmentFileList.get(0) : null;
//                    AttachmentFile attachmentFileCopy = new AttachmentFile();
//                    BeanUtils.copyProperties(attachmentFile, attachmentFileCopy);
//                    attachmentFileCopy.setParentId(this.attachmentFileRepository.findAllByIdInFileService(attachmentFile.get));
//                });
//            }
//        }
//        return  result;
//    }

    public BaseClientRp renameFolder(String id, String folderName) throws Exception {
        String url = this.urlFileSercice + this.urlRenameFolder + id;
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", folderName);
        BaseClientRp result = this.callAPIUtils.createRestTemplateFolder(url, null, HttpMethod.PUT, params, BaseClientRp.class);
        if(result.getState()){
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(id);
            if (attachmentFileList != null && attachmentFileList.size() > 0) {
                AttachmentFile attachmentFile = attachmentFileList.get(0);
                attachmentFile.setFileName(folderName); ;
                this.attachmentFileRepository.save(attachmentFile);
            }
        }
        return result;
    }

    private AttachmentFile attachDocumentToAttachmentFile(AttachDocument attachDocument, String fileName, String parentId) throws JsonProcessingException {
        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setIsFolder(true);
        attachmentFile.setIdInFileService(attachDocument.getId());
        attachmentFile.setPath(attachDocument.getFilePath());
        attachmentFile.setDescription(this.objectMapper.writeValueAsString(attachDocument));
        attachmentFile.setFileName(fileName);
        if(parentId != null && !parentId.trim().isEmpty()) {
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByIdInFileService(parentId);
            if (attachmentFileList != null && attachmentFileList.size() > 0) {
                AttachmentFile attachmentFileParent = attachmentFileList.get(0);
                attachmentFile.setParentId(attachmentFileParent.getId());
            }
        }
        attachmentFile.setIsActive(true);
        return attachmentFile;
    }
}
