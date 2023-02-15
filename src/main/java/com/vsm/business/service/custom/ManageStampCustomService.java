package com.vsm.business.service.custom;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.ManageStampInfoSearchRepository;
import com.vsm.business.service.dto.ManageStampInfoDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.ManageStampInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageStampCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private ManageStampInfoRepository manageStampInfoRepository;
    private ManageStampInfoSearchRepository manageStampInfoSearchRepository;
    protected ManageStampInfoMapper manageStampInfoMapper;

    public ManageStampCustomService(ManageStampInfoRepository manageStampInfoRepository, ManageStampInfoSearchRepository manageStampInfoSearchRepository, ManageStampInfoMapper manageStampInfoMapper) {
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.manageStampInfoSearchRepository = manageStampInfoSearchRepository;
        this.manageStampInfoMapper = manageStampInfoMapper;
    }

    public List<ManageStampInfoDTO> getAll() {
        List<ManageStampInfoDTO> result = this.manageStampInfoRepository.findAll().stream().map(manageStampInfoMapper::toDto).collect(Collectors.toList());
        log.debug("ManageStampCustomService: getAll() {}", result);
        return result;
    }

    public List<ManageStampInfoDTO> deleteAll(List<ManageStampInfoDTO> userInfoDTOS) {
        log.debug("ManageStampCustomService: deleteAll({})", userInfoDTOS);
        List<Long> ids = userInfoDTOS.stream().map(ManageStampInfoDTO::getId).collect(Collectors.toList());
        this.manageStampInfoRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            manageStampInfoRepository.deleteById(id);
            manageStampInfoSearchRepository.deleteById(id);
            log.debug("ManageStampCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("ManageStampCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ManageStampInfoDTO> saveAll(List<ManageStampInfoDTO> userInfoDTOList){
        List<ManageStampInfoDTO> result = manageStampInfoRepository.saveAll(userInfoDTOList.stream().map(manageStampInfoMapper::toEntity).collect(Collectors.toList())).stream().map(manageStampInfoMapper::toDto).collect(Collectors.toList());
        log.debug("ManageStampCustomService: saveAll({}) {}", userInfoDTOList, result);
        return result;
    }

    public List<ManageStampInfoDTO> getAllByRequestData(Long requestDataId){
        List<ManageStampInfoDTO> result = manageStampInfoRepository.findAllByRequestDataId(requestDataId).stream().map(manageStampInfoMapper::toDto).collect(Collectors.toList());
        log.debug("ManageStampCustomService: getAllByRequestData({}) {}", requestDataId, result);
        return result;
    }
}
