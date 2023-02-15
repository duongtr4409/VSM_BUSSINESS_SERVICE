package com.vsm.business.service.custom;

import com.vsm.business.domain.AttachmentInStepType;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.AttachmentInStepTypeRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.AttachmentInStepTypeSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.AttachmentInStepTypeMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentInStepTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(AttachmentInStepTypeCustomService.class);

    private AttachmentInStepTypeRepository attachmentInStepTypeRepository;

    private AttachmentInStepTypeSearchRepository attachmentInStepTypeSearchRepository;

    private AttachmentInStepTypeMapper attachmentInStepTypeMapper;

    public AttachmentInStepTypeCustomService(AttachmentInStepTypeRepository attachmentInStepTypeRepository, AttachmentInStepTypeSearchRepository attachmentInStepTypeSearchRepository, AttachmentInStepTypeMapper attachmentInStepTypeMapper) {
        this.attachmentInStepTypeRepository = attachmentInStepTypeRepository;
        this.attachmentInStepTypeSearchRepository = attachmentInStepTypeSearchRepository;
        this.attachmentInStepTypeMapper = attachmentInStepTypeMapper;
    }

    public List<AttachmentInStepTypeDTO> getAll() {
        log.debug("AttachmentInStepTypeCustomService: getAll()");
        List<AttachmentInStepTypeDTO> attachmentInStepTypeDTOS = new ArrayList<>();
        try {
            List<AttachmentInStepType> attachmentInStepTypes = this.attachmentInStepTypeRepository.findAll();
            for (AttachmentInStepType attachmentInStepType :
                attachmentInStepTypes) {
                AttachmentInStepTypeDTO attachmentInStepTypeDTO = attachmentInStepTypeMapper.toDto(attachmentInStepType);
                attachmentInStepTypeDTOS.add(attachmentInStepTypeDTO);
            }
        }catch (Exception e){
            log.error("AttachmentInStepTypeCustomService: getAll() {}", e);
        }
        log.debug("AttachmentInStepTypeCustomService: getAll() {}", attachmentInStepTypeDTOS);
        return attachmentInStepTypeDTOS;
    }

    public List<AttachmentInStepTypeDTO> deleteAll(List<AttachmentInStepTypeDTO> attachmentInStepTypeDTOS) {
        log.debug("AttachmentInStepTypeCustomService: deleteAll({})", attachmentInStepTypeDTOS);
        List<Long> ids = attachmentInStepTypeDTOS.stream().map(AttachmentInStepTypeDTO::getId).collect(Collectors.toList());
        this.attachmentInStepTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            attachmentInStepTypeRepository.deleteById(id);
            attachmentInStepTypeSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("AttachmentInStepTypeCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
