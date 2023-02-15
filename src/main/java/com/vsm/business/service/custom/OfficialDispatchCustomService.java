package com.vsm.business.service.custom;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.OfficialDispatchSearchRepository;
import com.vsm.business.repository.search.UserInfoSearchRepository;
import com.vsm.business.service.dto.OfficialDispatchDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.OfficialDispatchMapper;
import com.vsm.business.service.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private OfficialDispatchRepository officialDispatchRepository;
    private OfficialDispatchSearchRepository officialDispatchSearchRepository;
    protected OfficialDispatchMapper officialDispatchMapper;

    public OfficialDispatchCustomService(OfficialDispatchRepository officialDispatchRepository, OfficialDispatchSearchRepository officialDispatchSearchRepository, OfficialDispatchMapper officialDispatchMapper) {
        this.officialDispatchRepository = officialDispatchRepository;
        this.officialDispatchSearchRepository = officialDispatchSearchRepository;
        this.officialDispatchMapper = officialDispatchMapper;
    }

    public List<OfficialDispatchDTO> getAll() {
        List<OfficialDispatchDTO> result = this.officialDispatchRepository.findAll().stream().map(officialDispatchMapper::toDto).collect(Collectors.toList());
        log.debug("OfficialDispatchCustomService: getAll() {}", result);
        return result;
    }

    public List<OfficialDispatchDTO> deleteAll(List<OfficialDispatchDTO> userInfoDTOS) {
        log.debug("OfficialDispatchCustomService: deleteAll({})", userInfoDTOS);
        List<Long> ids = userInfoDTOS.stream().map(OfficialDispatchDTO::getId).collect(Collectors.toList());
        this.officialDispatchRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            officialDispatchRepository.deleteById(id);
            officialDispatchSearchRepository.deleteById(id);
            log.debug("OfficialDispatchCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("OfficialDispatchCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<OfficialDispatchDTO> saveAll(List<OfficialDispatchDTO> userInfoDTOList){
        List<OfficialDispatchDTO> result = officialDispatchRepository.saveAll(userInfoDTOList.stream().map(officialDispatchMapper::toEntity).collect(Collectors.toList())).stream().map(officialDispatchMapper::toDto).collect(Collectors.toList());
        log.debug("OfficialDispatchCustomService: saveAll({}) {}", userInfoDTOList, result);
        return result;
    }

}
