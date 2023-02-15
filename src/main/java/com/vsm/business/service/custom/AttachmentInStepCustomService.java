package com.vsm.business.service.custom;

import com.vsm.business.domain.AttachmentInStep;
import com.vsm.business.repository.AttachmentInStepRepository;
import com.vsm.business.repository.search.AttachmentInStepSearchRepository;
import com.vsm.business.service.dto.AttachmentInStepDTO;
import com.vsm.business.service.mapper.AttachmentInStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentInStepCustomService {
    private final Logger log = LoggerFactory.getLogger(AttachmentInStepCustomService.class);

    private AttachmentInStepRepository attachmentInStepRepository;

    private AttachmentInStepSearchRepository attachmentInStepSearchRepository;

    private AttachmentInStepMapper attachmentInStepMapper;

    public AttachmentInStepCustomService(AttachmentInStepRepository attachmentInStepRepository, AttachmentInStepSearchRepository attachmentInStepSearchRepository, AttachmentInStepMapper attachmentInStepMapper) {
        this.attachmentInStepRepository = attachmentInStepRepository;
        this.attachmentInStepSearchRepository = attachmentInStepSearchRepository;
        this.attachmentInStepMapper = attachmentInStepMapper;
    }

    public List<AttachmentInStepDTO> getAll() {
        log.debug("AttachmentInStepCustomService: getAll()");
        List<AttachmentInStepDTO> result = new ArrayList<>();
        try {
            List<AttachmentInStep> attachmentInSteps = this.attachmentInStepRepository.findAll();
            for (AttachmentInStep attachmentInStep :
                attachmentInSteps) {
                AttachmentInStepDTO attachmentInStepDTO = attachmentInStepMapper.toDto(attachmentInStep);
                result.add(attachmentInStepDTO);
            }
        }catch (Exception e){
            log.error("AttachmentInStepCustomService: getAll() {}", e);
        }
        log.debug("AttachmentInStepCustomService: getAll() {}", result);
        return result;
    }

    public List<AttachmentInStepDTO> deleteAll(List<AttachmentInStepDTO> attachmentInStepDTOS) {
        log.debug("AttachmentInStepCustomService: deleteAll({})", attachmentInStepDTOS);
        List<Long> ids = attachmentInStepDTOS.stream().map(AttachmentInStepDTO::getId).collect(Collectors.toList());
        this.attachmentInStepRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            attachmentInStepRepository.deleteById(id);
            attachmentInStepSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("AttachmentInStepCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
