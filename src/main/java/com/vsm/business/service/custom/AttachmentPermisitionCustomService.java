package com.vsm.business.service.custom;

import com.vsm.business.domain.StepInProcess;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.repository.search.StepInProcessSearchRepository;
import com.vsm.business.service.custom.StepCustomService;
import com.vsm.business.service.custom.UserInStepCustomService;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.dto.StepInProcessDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import com.vsm.business.service.mapper.StepInProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentPermisitionCustomService {
    private final Logger log = LoggerFactory.getLogger(AttachmentPermisitionCustomService.class);

    private AttachmentPermisitionRepository attachmentPermisitionRepository;
    private AttachmentPermisitionSearchRepository attachmentPermisitionSearchRepository;
    private AttachmentPermisitionMapper attachmentPermisitionMapper;

    public AttachmentPermisitionCustomService(AttachmentPermisitionRepository attachmentPermisitionRepository, AttachmentPermisitionSearchRepository attachmentPermisitionSearchRepository, AttachmentPermisitionMapper attachmentPermisitionMapper) {
        this.attachmentPermisitionRepository = attachmentPermisitionRepository;
        this.attachmentPermisitionSearchRepository = attachmentPermisitionSearchRepository;
        this.attachmentPermisitionMapper = attachmentPermisitionMapper;
    }

    public List<AttachmentPermisitionDTO> getAll() {
        log.debug("AttachmentPermisitionCustomService: getAll()");
        List<AttachmentPermisitionDTO> result = this.attachmentPermisitionRepository.findAll().stream().map(attachmentPermisitionMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<AttachmentPermisitionDTO> deleteAll(List<AttachmentPermisitionDTO> attachmentPermisitionDTOS){
        log.debug("AttachmentPermisitionCustomService: deleteAll({})", attachmentPermisitionDTOS);
        List<Long> ids = attachmentPermisitionDTOS.stream().map(AttachmentPermisitionDTO::getId).collect(Collectors.toList());
        this.attachmentPermisitionRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<AttachmentPermisitionDTO> saveAll(List<AttachmentPermisitionDTO> attachmentPermisitionDTOS){
        List<AttachmentPermisitionDTO> result = this.attachmentPermisitionRepository.saveAll(attachmentPermisitionDTOS.stream().map(this.attachmentPermisitionMapper::toEntity).collect(Collectors.toList())).stream().map(this.attachmentPermisitionMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentPermisitionCustomService: saveAll({}): {}", attachmentPermisitionDTOS, result);
        return result;
    }

    public List<AttachmentPermisitionDTO> getAllByUser(Long userId){
        List<AttachmentPermisitionDTO> result = this.attachmentPermisitionRepository.findAllByUserInfoId(userId).stream().map(attachmentPermisitionMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentPermisitionCustomService: getAllByUser({}): {}", userId, result);
        return result;
    }

    public List<AttachmentPermisitionDTO> getAllByAttachmentId(Long attachmentId){
        List<AttachmentPermisitionDTO> result = this.attachmentPermisitionRepository.findAllByAttachmentFileId(attachmentId).stream().map(this.attachmentPermisitionMapper::toDto).collect(Collectors.toList());
        log.debug("AttachmentPermisitionCustomService: getAllByAttachmentId({}): {}", attachmentId, result);
        return result;
    }
}
