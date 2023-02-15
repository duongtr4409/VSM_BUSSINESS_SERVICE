package com.vsm.business.service.custom;

import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.DelegateDocumentRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.repository.search.DelegateDocumentSearchRepository;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.dto.DelegateDocumentDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import com.vsm.business.service.mapper.DelegateDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DelegateDocumentCustomService {
    private final Logger log = LoggerFactory.getLogger(DelegateDocumentCustomService.class);

    private DelegateDocumentRepository delegateDocumentRepository;
    private DelegateDocumentSearchRepository delegateDocumentSearchRepository;
    private DelegateDocumentMapper delegateDocumentMapper;

    public DelegateDocumentCustomService(DelegateDocumentRepository delegateDocumentRepository, DelegateDocumentSearchRepository delegateDocumentSearchRepository, DelegateDocumentMapper delegateDocumentMapper) {
        this.delegateDocumentRepository = delegateDocumentRepository;
        this.delegateDocumentSearchRepository = delegateDocumentSearchRepository;
        this.delegateDocumentMapper = delegateDocumentMapper;
    }

    public List<DelegateDocumentDTO> getAll() {
        log.debug("DelegateDocumentCustomService: getAll()");
        List<DelegateDocumentDTO> result = this.delegateDocumentRepository.findAll().stream().map(delegateDocumentMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<DelegateDocumentDTO> deleteAll(List<DelegateDocumentDTO> delegateDocumentDTOS){
        log.debug("DelegateDocumentCustomService: deleteAll({})", delegateDocumentDTOS);
        List<Long> ids = delegateDocumentDTOS.stream().map(DelegateDocumentDTO::getId).collect(Collectors.toList());
        this.delegateDocumentRepository.deleteAllById(ids);
        return this.getAll();
    }

}
