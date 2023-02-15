package com.vsm.business.service.custom;

import com.vsm.business.repository.DelegateInfoRepository;
import com.vsm.business.repository.search.DelegateInfoSearchRepository;
import com.vsm.business.service.custom.StepCustomService;
import com.vsm.business.service.dto.DelegateInfoDTO;
import com.vsm.business.service.mapper.DelegateInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DelegateInfoCustomService {
    private final Logger log = LoggerFactory.getLogger(DelegateInfoCustomService.class);

    private DelegateInfoRepository delegateInfoRepository;
    private DelegateInfoSearchRepository delegateInfoSearchRepository;
    private DelegateInfoMapper delegateInfoMapper;

    public DelegateInfoCustomService(DelegateInfoRepository delegateInfoRepository, DelegateInfoSearchRepository delegateInfoSearchRepository, DelegateInfoMapper delegateInfoMapper) {
        this.delegateInfoRepository = delegateInfoRepository;
        this.delegateInfoSearchRepository = delegateInfoSearchRepository;
        this.delegateInfoMapper = delegateInfoMapper;
    }

    public List<DelegateInfoDTO> getAll() {
        log.debug("DelegateInfoCustomService: getAll()");
        List<DelegateInfoDTO> result = this.delegateInfoRepository.findAll().stream().map(delegateInfoMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<DelegateInfoDTO> deleteAll(List<DelegateInfoDTO> delegateInfoDTOS){
        log.debug("DelegateInfoCustomService: deleteAll({})", delegateInfoDTOS);
        List<Long> ids = delegateInfoDTOS.stream().map(DelegateInfoDTO::getId).collect(Collectors.toList());
        this.delegateInfoRepository.deleteAllById(ids);
        return this.getAll();
    }

}
