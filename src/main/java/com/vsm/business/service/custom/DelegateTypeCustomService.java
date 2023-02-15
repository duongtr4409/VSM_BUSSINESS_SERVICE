package com.vsm.business.service.custom;

import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.DelegateTypeRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.repository.search.DelegateTypeSearchRepository;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.dto.DelegateTypeDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import com.vsm.business.service.mapper.DelegateTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DelegateTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(DelegateTypeCustomService.class);

    private DelegateTypeRepository delegateTypeRepository;
    private DelegateTypeSearchRepository delegateTypeSearchRepository;
    private DelegateTypeMapper delegateTypeMapper;

    public DelegateTypeCustomService(DelegateTypeRepository delegateTypeRepository, DelegateTypeSearchRepository delegateTypeSearchRepository, DelegateTypeMapper delegateTypeMapper) {
        this.delegateTypeRepository = delegateTypeRepository;
        this.delegateTypeSearchRepository = delegateTypeSearchRepository;
        this.delegateTypeMapper = delegateTypeMapper;
    }

    public List<DelegateTypeDTO> getAll() {
        log.debug("DelegateTypeCustomService: getAll()");
        List<DelegateTypeDTO> result = this.delegateTypeRepository.findAll().stream().map(delegateTypeMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<DelegateTypeDTO> deleteAll(List<DelegateTypeDTO> delegateTypeDTOS){
        log.debug("DelegateTypeCustomService: deleteAll({})", delegateTypeDTOS);
        List<Long> ids = delegateTypeDTOS.stream().map(DelegateTypeDTO::getId).collect(Collectors.toList());
        this.delegateTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

}
