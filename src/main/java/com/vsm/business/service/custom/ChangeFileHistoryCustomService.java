package com.vsm.business.service.custom;

import com.vsm.business.repository.ChangeFileHistoryRepository;
import com.vsm.business.repository.search.ChangeFileHistorySearchRepository;
import com.vsm.business.service.dto.ChangeFileHistoryDTO;
import com.vsm.business.service.mapper.ChangeFileHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChangeFileHistoryCustomService {
    private final Logger log = LoggerFactory.getLogger(ChangeFileHistoryCustomService.class);

    private ChangeFileHistoryRepository changeFileHistoryRepository;
    private ChangeFileHistorySearchRepository changeFileHistorySearchRepository;
    private ChangeFileHistoryMapper changeFileHistoryMapper;

    public ChangeFileHistoryCustomService(ChangeFileHistoryRepository changeFileHistoryRepository, ChangeFileHistorySearchRepository changeFileHistorySearchRepository, ChangeFileHistoryMapper changeFileHistoryMapper) {
        this.changeFileHistoryRepository = changeFileHistoryRepository;
        this.changeFileHistorySearchRepository = changeFileHistorySearchRepository;
        this.changeFileHistoryMapper = changeFileHistoryMapper;
    }

    public List<ChangeFileHistoryDTO> getAll() {
        log.debug("ChangeFileHistoryCustomService: getAll()");
        List<ChangeFileHistoryDTO> result = this.changeFileHistoryRepository.findAll().stream().map(changeFileHistoryMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<ChangeFileHistoryDTO> deleteAll(List<ChangeFileHistoryDTO> changeFileHistoryDTOS){
        log.debug("ChangeFileHistoryCustomService: deleteAll({})", changeFileHistoryDTOS);
        List<Long> ids = changeFileHistoryDTOS.stream().map(ChangeFileHistoryDTO::getId).collect(Collectors.toList());
        this.changeFileHistoryRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<ChangeFileHistoryDTO> getAllByAttachmentFile(Long attachmentFileId){
        log.debug("ChangeFileHistoryCustomService: getAllByAttachmentFile({})");
        List<ChangeFileHistoryDTO> result = this.changeFileHistoryRepository.findAllByAttachmentFileId(attachmentFileId).stream().map(changeFileHistoryMapper::toDto).collect(Collectors.toList());
        return result;
    }

}
