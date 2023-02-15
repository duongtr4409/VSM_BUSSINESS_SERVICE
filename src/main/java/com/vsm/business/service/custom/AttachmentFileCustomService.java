package com.vsm.business.service.custom;

import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.search.AttachmentFileSearchRepository;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.file.UploadFileCustomService;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.dto.TemplateFormDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.service.mapper.RequestDataMapper;
import com.vsm.business.utils.ConditionUtils;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentFileCustomService {
    private final Logger log = LoggerFactory.getLogger(AttachmentFileCustomService.class);

    private AttachmentFileRepository attachmentFileRepository;

    private AttachmentFileSearchRepository attachmentFileSearchRepository;

    private AttachmentFileMapper attachmentFileMapper;

    private UploadFileCustomService uploadFileCustomService;

    private UploadFile365CustomService uploadFile365CustomService;

    private final RequestDataMapper requestDataMapper;

    @Autowired
    private ConditionUtils conditionUtils;


    public AttachmentFileCustomService(AttachmentFileRepository attachmentFileRepository, AttachmentFileSearchRepository attachmentFileSearchRepository, AttachmentFileMapper attachmentFileMapper, UploadFileCustomService uploadFileCustomService, UploadFile365CustomService uploadFile365CustomService, RequestDataMapper requestDataMapper) {
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileSearchRepository = attachmentFileSearchRepository;
        this.attachmentFileMapper = attachmentFileMapper;
        this.uploadFileCustomService = uploadFileCustomService;
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.requestDataMapper = requestDataMapper;
    }

    public List<AttachmentFileDTO> getAll() {
        log.debug("AttachmentFileCustomService: getAll()");
        List<AttachmentFileDTO> result = new ArrayList<>();
        try {
            List<AttachmentFile> attachmentFiles = this.attachmentFileRepository.findAll();
            for (AttachmentFile attachmentFile :
                attachmentFiles) {
                AttachmentFileDTO attachmentFileDTO = attachmentFileMapper.toDto(attachmentFile);
                result.add(attachmentFileDTO);
            }
        }catch (Exception e){
            log.error("AttachmentFileCustomService: getAll() {}", e);
        }
        log.debug("AttachmentFileCustomService: getAll() {}", result);
        return result;
    }

    public List<AttachmentFileDTO> deleteAll(List<AttachmentFileDTO> attachmentFileDTOS) {
        log.debug("AttachmentFileCustomService: deleteAll({})", attachmentFileDTOS);
        List<Long> ids = attachmentFileDTOS.stream().map(AttachmentFileDTO::getId).collect(Collectors.toList());
        this.attachmentFileRepository.deleteAllById(ids);
        return this.getAll();
    }

    @Transactional
    public boolean customDelete(Long id) {
        try {
//            attachmentFileRepository.deleteById(id);
//            attachmentFileSearchRepository.deleteById(id);
            this.uploadFile365CustomService.deleteItemFile(id);     // thực hiện xoá trên office 365
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.showFolderContent(id);          // lấy tất cả item con để xoá
            this.attachmentFileRepository.deleteAll(attachmentFileList);

            cleanCache();

            return true;
        } catch (Exception e) {
            log.error("AttachmentFileCustomService: delete({}) {}", id, e.getStackTrace());
            throw e;
        }
    }


    @Cacheable(value = "/template-form/{templateFormId}/_all/attachment-files", key = "#templateFormId + \"\"")
    public List<AttachmentFileDTO> findAllByTemplateForm(Long templateFormId){
        List<AttachmentFileDTO> result = attachmentFileRepository.findAllByTemplateFormId(templateFormId).stream().filter(ele -> !this.conditionUtils.checkTrueFalse(ele.getIsFolder())).map(attachmentFileMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentFileCustomService: findAllByTemplateForm({}) {}", templateFormId, result);
        return result;
    }


    @Value("${system.file.customer-file-code:CUSTOMER}")
    public String CUSTOMER_FILE_CODE;
    public List<AttachmentFileDTO> findAllByRequestData(Long requestDataId){
//        List<AttachmentFileDTO> result = attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> !this.conditionUtils.checkTrueFalse(ele.getIsFolder())).map(attachmentFileMapper::toDto).collect(Collectors.toList());
//        log.info("AttachmentFileCustomService: findAllByRequestData({}) {}", requestDataId, result);
        List<AttachmentFileDTO> result = attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele ->
                !this.conditionUtils.checkTrueFalse(ele.getIsFolder())
                && !this.conditionUtils.checkDelete(ele.getIsDelete())
                && !this.CUSTOMER_FILE_CODE.equals(ele.getTennantCode())
            ).map(ele -> {
            AttachmentFileDTO attachmentFileDTO = new AttachmentFileDTO();
            attachmentFileDTO = attachmentFileMapper.toDto(ele);
            if(ele != null){
                if(ele.getTemplateForm() != null){
                    TemplateFormDTO templateFormDTO = new TemplateFormDTO();
                    BeanUtils.copyProperties(ele.getTemplateForm(), templateFormDTO);
                    List<AttachmentFileDTO> attachmentFileDTOList = new ArrayList<>();
//                    for(AttachmentFile attachmentFile : ele.getTemplateForm().getAttachmentFiles()){
//                        AttachmentFileDTO attachmentFileDTOTemp = new AttachmentFileDTO();
//                        attachmentFileDTOTemp.setId(attachmentFile.getId());
//                        attachmentFileDTOTemp.setItemId365(attachmentFile.getItemId365());
//                        if(attachmentFile.getRequestData() == null)
//                            attachmentFileDTOList.add(attachmentFileDTOTemp);
//                    }
                    attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(ele.getTemplateForm().getId(), null).forEach(ele1 -> {
                        AttachmentFileDTO attachmentFileDTOTemp = new AttachmentFileDTO();
                        attachmentFileDTOTemp.setId(ele1.getId());
                        attachmentFileDTOTemp.setItemId365(ele1.getItemId365());
                        attachmentFileDTOList.add(attachmentFileDTOTemp);
                    });
                    templateFormDTO.setAttachmentFileDTOS(attachmentFileDTOList);
                    attachmentFileDTO.setTemplateForm(templateFormDTO);
                }
            }
            return attachmentFileDTO;
        }).collect(Collectors.toList());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAllByTemplateFormId(Long templateFormId) throws Exception {
        List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByTemplateFormId(templateFormId);
        if(attachmentFileList != null && !attachmentFileList.isEmpty()){
            this.attachmentFileRepository.deleteAllByTemplateFormId(templateFormId);
            for(AttachmentFile attachmentFile : attachmentFileList){
                //this.uploadFileCustomService.deleteFile(attachmentFile.getIdInFileService());
                this.uploadFile365CustomService.deleteFile(attachmentFile.getId());
            }
        }

        cleanCache();

       return true;
    }

    public List<AttachmentFileDTO> getAllByManageStampInfo(Long manageStampInfoId){
        List<AttachmentFileDTO> result = attachmentFileRepository.findAllByManageStampInfoId(manageStampInfoId).stream().filter(ele -> !this.conditionUtils.checkTrueFalse(ele.getIsFolder())).map(attachmentFileMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentFileCustomService: getAllByManageStampInfo({}) {}", manageStampInfoId, result);
        return result;
    }

    public List<AttachmentFileDTO> getAllByMailTemplate(Long mailTemplateId){
        List<AttachmentFileDTO> result = attachmentFileRepository.findAllByMailTemplateId(mailTemplateId).stream().filter(ele -> !this.conditionUtils.checkTrueFalse(ele.getIsFolder())).map(attachmentFileMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentFileCustomService: getAllByMailTemplate({}) {}", mailTemplateId, result);
        return result;
    }

    /**
     * hàm kiểm tra giá trị true false (null -> false)
     * @param isdelete
     * @return
     */
    private boolean checkIsDelete(Boolean isdelete){
        if(isdelete == null) return false;
        return isdelete;
    }

    public List<AttachmentFileDTO> getAllByTemplateFormIdAndRequestData(Long templateFormId, RequestDataDTO requestDataDTO){
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        List<AttachmentFile> attachmentFiles = attachmentFileRepository.findAttachmentFilesByTemplateFormIdAndRequestData(templateFormId, requestData);
        return attachmentFiles.stream().map(e-> attachmentFileMapper.toDto(e)).collect(Collectors.toList());
    }


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JCacheManagerCustomizer jCacheManagerCustomizer;

    public void cleanCache(){
        try {
            cacheManager.getCache("/template-form/{templateFormId}/_all/attachment-files").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
    }
}
