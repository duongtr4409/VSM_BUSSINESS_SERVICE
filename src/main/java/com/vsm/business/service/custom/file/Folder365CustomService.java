package com.vsm.business.service.custom.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.DriveItem;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.custom.file.bo.Office365.CreateFolder365Option;
import com.vsm.business.service.custom.file.bo.Office365.Edit365Option;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.CallAPIUtils;
import com.vsm.business.utils.ConditionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Folder365CustomService {

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

    @Autowired
    private AttachmentPermisitionRepository attachmentPermisitionRepository;

    @Autowired
    private GraphService graphService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public AttachmentFileDTO createFolder(CreateFolder365Option createFolder365Option) throws JsonProcessingException {
        UserInfo created = null;
        if(createFolder365Option.getCreatedId() != null)
            created = this.userInfoRepository.findById(createFolder365Option.getCreatedId()).orElse(null);

        if(createFolder365Option.getParentItemId() == null || createFolder365Option.getParentItemId().trim().isEmpty()){
            if(createFolder365Option.getParentId() != null){
                AttachmentFile folderParent = this.attachmentFileRepository.findById(createFolder365Option.getParentId()).orElse(new AttachmentFile());
                createFolder365Option.setParentItemId(folderParent.getItemId365());
            }
        }

        DriveItem folderInfo = graphService.createFolder(createFolder365Option.getFolderName(), createFolder365Option.getParentItemId());
        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setIsFolder(true);
        attachmentFile.setItemId365(folderInfo.id);
        attachmentFile.setOfice365Path(folderInfo.webUrl);
        attachmentFile.setPathInHandBook(createFolder365Option.getPathInHandBook());
        attachmentFile.setDescription(createFolder365Option.getDescription());
        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(folderInfo));
        attachmentFile.setFileName(folderInfo.name);
        attachmentFile.setParentId(createFolder365Option.getParentId());
        attachmentFile.setCreated(created);
        attachmentFile.setCreatedName(created != null ? created.getFullName() : null);
        attachmentFile.setCreatedDate(Instant.now());
        attachmentFile.setModified(created);
        attachmentFile.setModifiedName(created != null ? created.getFullName() : null);
        attachmentFile.setModifiedDate(Instant.now());
        attachmentFile.setIsActive(true);
        attachmentFile.setIsDelete(false);
        this.attachmentFileRepository.save(attachmentFile);
        return this.attachmentFileMapper.toDto(attachmentFile);
    }

    @Transactional
    public Boolean deleteFolder(Long id){
        String itemId = this.attachmentFileRepository.findById(id).get().getItemId365();

        // sửa để xóa cả khóa ngoại \\
        //this.attachmentFileRepository.deleteFolder(id);
        List<AttachmentFile> attachmentFileListDelete = this.attachmentFileRepository.showFolderContent(id);
        this.attachmentFileRepository.deleteAll(attachmentFileListDelete);

        this.graphService.deleteItem(itemId);
        return true;
    }

    @Transactional
    public Boolean restoreFolder(Long id){
        this.attachmentFileRepository.restoreFolder(id);
        return true;
    }

    public List<AttachmentFileDTO> showFolderContent(Long id){
//        List<AttachmentFile> result = this.attachmentFileRepository.showFolderContent(id);
        List<AttachmentFile> result = this.attachmentFileRepository.showFolderContent(id).stream().filter(ele -> !id.equals(ele.getId())).collect(Collectors.toList());
        return result.stream().map(this.attachmentFileMapper::toDto).collect(Collectors.toList());
    }

    public List<AttachmentFileDTO> showChild(Long id){
//        List<AttachmentFile> result = this.attachmentFileRepository.showFolderContent(id);
        List<AttachmentFile> result = this.attachmentFileRepository.findAllByParentId(id);
        return result.stream().map(this.attachmentFileMapper::toDto).collect(Collectors.toList());
    }

    public Boolean moveFolder(Edit365Option edit365Option){
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

    @Transactional
    public Boolean copyFolder(Edit365Option edit365Option) throws IOException {
        UserInfo created = null;
        if(edit365Option.getUserId() != null)
            created = this.userInfoRepository.findById(edit365Option.getUserId()).orElse(null);
        AttachmentFile folderTarget = this.attachmentFileRepository.findById(edit365Option.getFolderTargetId()).get();
        List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.showFolderContent(edit365Option.getSourceId()).stream().filter(ele -> !conditionUtils.checkTrueFalse(ele.getIsDelete())).collect(Collectors.toList());
        // map mapAttachmentFileSource lưu thông tin dữ liệu nguồn -> nhằm kiểm tra xem attachmentFile nào là cha to nhất -> nếu là cha to nhất thì parrentID của attachmentFile clone từ nó sẽ là FolderTargetId truyền vào
        Map<Long, AttachmentFile> mapAttachmentFileSource = attachmentFileList.stream().collect(Collectors.toMap(ele -> ele.getId(), ele -> ele, (ele1, ele2) -> ele2 , HashMap::new));
        // map mapAttachmentFileNew lưu thông tin dữ liệu clone (key là itempId clone, value là AttachmentFile đã clone)
        Map<String, AttachmentFile> mapAttachmentFileNew = new HashMap<>();

        for(AttachmentFile attachmentFile : attachmentFileList){
            AttachmentFile attachmentFileNew = cloneAttachmentFile(attachmentFile);
            Long parrentId;
            String parentItemId;
            DriveItem driveItem;
            if(mapAttachmentFileSource.containsKey(attachmentFile.getParentId())){
                AttachmentFile attachmentFileCloneDone = mapAttachmentFileNew.get(mapAttachmentFileSource.get(attachmentFile.getParentId()).getItemId365());
                parentItemId = attachmentFileCloneDone.getItemId365();
                parrentId = attachmentFileCloneDone.getId();
            }else{
                parentItemId = folderTarget.getItemId365();
                parrentId = edit365Option.getFolderTargetId();
            }

            if(conditionUtils.checkTrueFalse(attachmentFile.getIsFolder())){
                driveItem = this.graphService.createFolder(attachmentFileNew.getFileName(), parentItemId);
            }else{

                driveItem = this.graphService.copyFile(attachmentFile.getItemId365(), parentItemId, attachmentFileNew.getFileName());
            }

            attachmentFileNew.setItemId365(driveItem.id);
            attachmentFileNew.setOfice365Path(driveItem.webUrl);
            attachmentFileNew.setParentId(parrentId);
            attachmentFileNew = addUserInFo(attachmentFileNew, created);
            attachmentFileNew = this.attachmentFileRepository.save(attachmentFileNew);
            if(conditionUtils.checkTrueFalse(attachmentFile.getIsFolder())) mapAttachmentFileNew.put(attachmentFile.getItemId365(), attachmentFileNew);
            for(AttachmentPermisition ele : attachmentFile.getAttachmentPermisitions()){
                AttachmentPermisition attachmentPermisition = new AttachmentPermisition();
                BeanUtils.copyProperties(ele, attachmentPermisition);
                attachmentPermisition.setId(null);
                attachmentPermisition.setAttachmentFile(attachmentFileNew);
                attachmentPermisition = this.attachmentPermisitionRepository.save(attachmentPermisition);
                attachmentFileNew.getAttachmentPermisitions().add(attachmentPermisition);
            }
        }
        return true;
    }

    private AttachmentFile cloneAttachmentFile(AttachmentFile source){
        AttachmentFile attachmentFile = new AttachmentFile();
        BeanUtils.copyProperties(source, attachmentFile);
        attachmentFile.setId(null);
        attachmentFile.setAttachmentPermisitions(null);
        attachmentFile.setChangeFileHistories(null);
        return attachmentFile;
    }

    @Transactional
    public Boolean reNameFolder(Long id, String folderName){
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
        attachmentFile.setFileName(folderName);
        this.graphService.reNameItem(attachmentFile.getItemId365(), folderName);
        this.attachmentFileRepository.save(attachmentFile);
        return true;
    }

    public AttachmentFileDTO getRootFolder(){
        List<AttachmentFileDTO> ROOT_FOLDER = this.attachmentFileRepository.findAllByItemId365(UploadFile365CustomService.ROOT_FOLDER_ITEM_ID).stream().map(this.attachmentFileMapper::toDto).collect(Collectors.toList());
        if(ROOT_FOLDER != null && !ROOT_FOLDER.isEmpty()){
            return ROOT_FOLDER.get(0);
        }else{
            return null;
        }
    }

//    private Boolean saveResultCopy(DriveItem driveItem, Long parentId, UserInfo created) {
//        DriveItemCollectionPage children = driveItem.children;
//        if (children == null || children.getCount() == 0) return true;
//        else {
//            Boolean result = true;
//            while (children.getCurrentPage() != null && !children.getCurrentPage().isEmpty()) {
//                children = children.getNextPage().buildRequest().get();
//                for (DriveItem ele : children.getCurrentPage()) {
//                    result = result && saveResultCopy(ele,  , created);
//                }
//            }
//            return result;
//        }
//    }
//
//    private AttachmentFile saveDriveItem(DriveItem driveItem, Long parentId, UserInfo created){
//        AttachmentFile attachmentFile = new AttachmentFile();
//        attachmentFile.setItemId365(driveItem.id);
//        attachmentFile.setFileName(driveItem.name);
//        attachmentFile.setParentId(parentId);
//        attachmentFile.setFileExtension(FilenameUtils.getExtension(driveItem.name));
//        attachmentFile.setOfice365Path(driveItem.webUrl);
//        attachmentFile.setFileSize(driveItem.size);
//        attachmentFile.setContentType(driveItem.oDataType);
//        attachmentFile.setIsActive(true);
//        attachmentFile.setIsFolder(false);
//        attachmentFile.setIsDelete(false);
//        //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));
//        addUserInFo(attachmentFile, created);
//        attachmentFile.setCreatedDate(Instant.now());
//        attachmentFile.setModifiedDate(Instant.now());
//        if(uploadOption.getTemplateFormId() != null)
//            attachmentFile.setTemplateForm(this.templateFormRepository.findById(uploadOption.getTemplateFormId()).orElse(null));
//        if(uploadOption.getRequestDataId() != null)
//            attachmentFile.setRequestData(this.requestDataRepository.findById(uploadOption.getRequestDataId()).orElse(null));
//        if(uploadOption.getFileTypeId() != null){
//            FileType fileType = this.fileTypeRepository.findById(uploadOption.getFileTypeId()).orElse(null);
//            attachmentFile.setFileType(fileType);
//            attachmentFile.setFileTypeName(fileType == null ? null : fileType.getFileTypeName());
//            attachmentFile.setFileTypeCode(fileType == null ? null : fileType.getFileTypeCode());
//        }
//        if(uploadOption.getStepProcessId() != null)
//            attachmentFile.setStepProcessDoc(this.stepProcessDocRepository.findById(uploadOption.getStepProcessId()).orElse(null));
//        if(uploadOption.getRequestProcessHisId() != null)
//            attachmentFile.setReqdataProcessHis(this.reqdataProcessHisRepository.findById(uploadOption.getRequestProcessHisId()).orElse(null));
//        if(uploadOption.getIncomingDocId() != null)
//            attachmentFile.setIncomingDoc(this.incomingDocRepository.findById(uploadOption.getIncomingDocId()).orElse(null));
//        if(uploadOption.getOutgoingDocId() != null)
//            attachmentFile.setOutgoingDoc(this.outgoingDocRepository.findById(uploadOption.getOutgoingDocId()).orElse(null));
//
//        attachmentFile = this.attachmentFileRepository.save(attachmentFile);
//
//        return attachmentFile;
//    }

    private AttachmentFile addUserInFo(AttachmentFile attachmentFile, UserInfo userInfo){
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

}
