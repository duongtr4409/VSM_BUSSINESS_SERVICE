package com.vsm.business.service.custom;

import com.vsm.business.domain.Request;
import com.vsm.business.domain.TemplateForm;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.TemplateFormRepository;
import com.vsm.business.repository.search.TemplateFormSearchRepository;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.TemplateFormDTO;
import com.vsm.business.service.mapper.TemplateFormMapper;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TemplateFormCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);
    private TemplateFormRepository templateFormRepository;
    private TemplateFormSearchRepository templateFormSearchRepository;
    private TemplateFormMapper templateFormMapper;
    private RequestRepository requestRepository;
    private AttachmentFileCustomService attachmentFileCustomService;

    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private ConditionUtils conditionUtils;

    public TemplateFormCustomService(TemplateFormRepository templateFormRepository, TemplateFormSearchRepository templateFormSearchRepository, TemplateFormMapper templateFormMapper, RequestRepository requestRepository, AttachmentFileCustomService attachmentFileCustomService, AttachmentFileRepository attachmentFileRepository) {
        this.templateFormRepository = templateFormRepository;
        this.templateFormSearchRepository = templateFormSearchRepository;
        this.templateFormMapper = templateFormMapper;
        this.requestRepository = requestRepository;
        this.attachmentFileCustomService = attachmentFileCustomService;
        this.attachmentFileRepository = attachmentFileRepository;
    }

    @Autowired
    public Environment environment;

    public TemplateForm TEMPLATE_FORM_CUSTOMER;

    public TemplateForm getTEMPLATE_FORM_CUSTOMER() {
        return TEMPLATE_FORM_CUSTOMER;
    }

    public void setTEMPLATE_FORM_CUSTOMER(TemplateForm TEMPLATE_FORM_CUSTOMER) {
        this.TEMPLATE_FORM_CUSTOMER = TEMPLATE_FORM_CUSTOMER;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void TEMPLATE_FORM_CUSTOMER(){
        Long ID_TEMPLATE_FORM_CUSTOMER = -1L;
        String NAME_TEMPLATE_FORM_CUSTOMER = "Tài liệu người dùng upload";
        String CODE_TEMPLATE_FORM_CUSTOMER = "TEMPLATE_FORM_CUSTOMER";
        try {
            ID_TEMPLATE_FORM_CUSTOMER = Long.valueOf(this.environment.getProperty("system.templateform.id-templateform-upload", "-1"));
            NAME_TEMPLATE_FORM_CUSTOMER = this.environment.getProperty("system.templateform.name-templateform-upload", "Tài liệu người dùng upload");
            CODE_TEMPLATE_FORM_CUSTOMER = this.environment.getProperty("system.templateform.code-templateform-upload", "TEMPLATE_FORM_CUSTOMER");
        }catch (Exception e){
            log.error("{}", e);
        }
        List<TemplateForm> templateFormList = this.templateFormRepository.findAllByTemplateFormCode(CODE_TEMPLATE_FORM_CUSTOMER);
        if(templateFormList != null && !templateFormList.isEmpty() && templateFormList.get(0) != null){
            setTEMPLATE_FORM_CUSTOMER(templateFormList.get(0));
        }else{
            TemplateForm templateForm = new TemplateForm();
            Instant now = Instant.now();
            templateForm.setTemplateFormName(NAME_TEMPLATE_FORM_CUSTOMER);
            templateForm.setTemplateFormCode(CODE_TEMPLATE_FORM_CUSTOMER);
            templateForm.setCreatedName("SYSTEM");
            templateForm.setModifiedName("SYSTEM");
            templateForm.setCreatedDate(now);
            templateForm.setModifiedDate(now);
            templateForm.setIsDelete(false);
            templateForm.setIsActive(false);
            templateForm.setMappingInfo("[]");
            templateForm.setDescription("Biểu mẫu đánh dấu file tài liệu chính do người dùng upload");
            setTEMPLATE_FORM_CUSTOMER(this.templateFormRepository.save(templateForm));
        }
    }

    public List<TemplateFormDTO> getAll(Boolean ignoreFile) throws IllegalAccessException {
        log.debug("TemplateFormCustomService: getAll()");
        List<TemplateForm> templateForms = this.templateFormRepository.findAll();
        List<TemplateFormDTO> result = new ArrayList<>();
        if(ignoreFile){
            result = templateForms.stream().map(ele -> {
                TemplateFormDTO templateFormDTO = new TemplateFormDTO();
                BeanUtils.copyProperties(ele, templateFormDTO);
                return templateFormDTO;
            }).collect(Collectors.toList());
        }else {
            for (TemplateForm templateForm :
                templateForms) {
//            TemplateFormDTO templateFormDTO = templateFormMapper.toDto(templateForm);
//            templateFormDTO.setAttachmentFileDTOS(this.attachmentFileCustomService.findAllByTemplateForm(templateFormDTO.getId()).stream().filter(ele -> ele.getRequestData() == null).collect(Collectors.toList()));
//            templateFormDTO.setRequestDTOS(templateForm.getRequests().stream().map(ele -> {
//                try {
//                    return objectUtils.copyproperties(ele, new RequestDTO(), RequestDTO.class);
//                } catch (IllegalAccessException e) {return new RequestDTO();}
//            }).collect(Collectors.toList()));
                TemplateFormDTO templateFormDTO = this.convertToDTO(templateForm);
                result.add(templateFormDTO);
            }
        }
        return result;
    }

    public List<TemplateFormDTO> getAllHasFile(Boolean ignoreFile) throws IllegalAccessException {
        log.debug("TemplateFormCustomService: getAllHasFile()");
        List<TemplateForm> templateForms = this.templateFormRepository.customFindAllHasFile();
        List<TemplateFormDTO> result = new ArrayList<>();
        if(ignoreFile){
            result = templateForms.stream().map(ele -> {
                TemplateFormDTO templateFormDTO = new TemplateFormDTO();
                BeanUtils.copyProperties(ele, templateFormDTO);
                return templateFormDTO;
            }).collect(Collectors.toList());
        }else {
            for (TemplateForm templateForm :
                templateForms) {
//            TemplateFormDTO templateFormDTO = templateFormMapper.toDto(templateForm);
//            templateFormDTO.setAttachmentFileDTOS(this.attachmentFileCustomService.findAllByTemplateForm(templateFormDTO.getId()).stream().filter(ele -> ele.getRequestData() == null).collect(Collectors.toList()));
//            templateFormDTO.setRequestDTOS(templateForm.getRequests().stream().map(ele -> {
//                try {
//                    return objectUtils.copyproperties(ele, new RequestDTO(), RequestDTO.class);
//                } catch (IllegalAccessException e) {return new RequestDTO();}
//            }).collect(Collectors.toList()));
                if(templateForm.getAttachmentFiles() != null && !templateForm.getAttachmentFiles().isEmpty()){
                    TemplateFormDTO templateFormDTO = this.convertToDTO(templateForm);
                    result.add(templateFormDTO);
                }
            }
        }
        return result;
    }

    public TemplateFormDTO getOne(Long id) throws IllegalAccessException {
        log.debug("TemplateFormCustomService: getOne({})", id);
        TemplateForm templateForm = this.templateFormRepository.findById(id).get();
//        TemplateFormDTO result = templateFormMapper.toDto(templateForm);
//        result.setAttachmentFileDTOS(this.attachmentFileCustomService.findAllByTemplateForm(id).stream().filter(ele -> ele.getRequestData() == null).collect(Collectors.toList()));
//        result.setRequestDTOS(templateForm.getRequests().stream().map(ele -> {
//            try {
//                return objectUtils.copyproperties(ele, new RequestDTO(), RequestDTO.class);
//            } catch (IllegalAccessException e) {return new RequestDTO();}
//        }).collect(Collectors.toList()));
        TemplateFormDTO result = this.convertToDTO(templateForm);
        return result;
    }

    public List<TemplateFormDTO> deleteAll(List<TemplateFormDTO> templateFormDTOS) throws IllegalAccessException {
        log.debug("TemplateFormCustomService: deleteAll({})", templateFormDTOS);
        List<Long> ids = templateFormDTOS.stream().map(TemplateFormDTO::getId).collect(Collectors.toList());
        this.templateFormRepository.deleteAllById(ids);
        return this.getAll(false);
    }

    public boolean delete(Long id) {
        try {
            templateFormRepository.deleteById(id);
            templateFormSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("TemplateFormCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<TemplateFormDTO> saveAll(List<TemplateFormDTO> templateFormDTOList){
        List<TemplateFormDTO> result = templateFormRepository.saveAll(templateFormDTOList.stream().map(templateFormMapper::toEntity).collect(Collectors.toList())).stream().map(templateFormMapper::toDto).collect(Collectors.toList());
        log.debug("TemplateFormCustomService: saveAll({}) {}", templateFormDTOList, result);
        return  result;
    }

    public List<TemplateFormDTO> customSaveAll(List<TemplateFormDTO> templateFormDTOList) throws Exception {
        if(checkExist(templateFormDTOList)){
            throw new Exception("Exist Request");
        }
        List<TemplateFormDTO> result = this.templateFormRepository.saveAll(templateFormDTOList.stream().map(templateFormMapper::toEntity).collect(Collectors.toList())).stream().map(templateFormMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<TemplateFormDTO> getAllByRequest(Long requestId){
        Request request = this.requestRepository.findById(requestId).get();
        List<TemplateFormDTO> result = this.templateFormRepository.findAllByRequestsIn(Arrays.asList(request).stream().collect(Collectors.toSet())).stream().map(ele -> {
            TemplateFormDTO templateFormDTO = new TemplateFormDTO();
            BeanUtils.copyProperties(ele, templateFormDTO);
            return templateFormDTO;
        }).collect(Collectors.toList());
        return result;
    }

    public List<TemplateFormDTO> getAllIgnoreField() {
        List<TemplateFormDTO> result = this.templateFormRepository.findAll().stream().map(ele -> {
            TemplateFormDTO templateFormDTO = new TemplateFormDTO();
            if(ele != null){
                try {templateFormDTO = this.objectUtils.coppySimpleType(ele, templateFormDTO, TemplateFormDTO.class);}catch (IllegalAccessException e){log.error("{}", e);}
            }
            return templateFormDTO;
        }).collect(Collectors.toList());
        return result;
    }


    private Boolean checkExist(List<TemplateFormDTO> templateFormDTOList){
        Set<Long> templateFormIds = templateFormDTOList.stream().map(ele -> ele.getId()).collect(Collectors.toSet());
        Boolean result = this.requestRepository.findAllByTemplateForms(templateFormIds).stream().anyMatch(ele -> ele.getIsDelete() != true);
        return result;
    }


        // utils \\
    private TemplateFormDTO convertToDTO(TemplateForm templateForm) throws IllegalAccessException {
        if(templateForm == null) return null;
        TemplateFormDTO templateFormDTO = new TemplateFormDTO();
//        this.objectUtils.copyproperties(templateForm, templateFormDTO, TemplateFormDTO.class);
        this.objectUtils.copyproperties_v2(templateForm, templateFormDTO, TemplateFormDTO.class);
        templateFormDTO.setAttachmentFileDTOS(this.attachmentFileCustomService.findAllByTemplateForm(templateForm.getId()).stream().filter(ele -> ele.getRequestData() == null).collect(Collectors.toList()));
        templateFormDTO.setRequestDTOS(templateForm.getRequests().stream().filter(ele -> !this.conditionUtils.checkDelete(ele.getIsDelete())).map(ele -> {
            try {
                return objectUtils.copyproperties(ele, new RequestDTO(), RequestDTO.class);
            } catch (IllegalAccessException e) {return new RequestDTO();}
        }).collect(Collectors.toList()));
        return templateFormDTO;
    }
}
