package com.vsm.business.service.custom;

import com.vsm.business.domain.ReqdataProcessHis;
import com.vsm.business.repository.ReqdataProcessHisRepository;
import com.vsm.business.repository.search.ReqdataProcessHisSearchRepository;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;
import com.vsm.business.service.mapper.ReqdataProcessHisMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReqdataProcessHisCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private ReqdataProcessHisRepository reqdataProcessHisRepository;
    private ReqdataProcessHisSearchRepository reqdataProcessHisSearchRepository;
    protected ReqdataProcessHisMapper reqdataProcessHisMapper;

    public ReqdataProcessHisCustomService(ReqdataProcessHisRepository reqdataProcessHisRepository, ReqdataProcessHisSearchRepository reqdataProcessHisSearchRepository, ReqdataProcessHisMapper reqdataProcessHisMapper) {
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
        this.reqdataProcessHisSearchRepository = reqdataProcessHisSearchRepository;
        this.reqdataProcessHisMapper = reqdataProcessHisMapper;
    }

    public List<ReqdataProcessHisDTO> getAll() {
        List<ReqdataProcessHisDTO> result = this.reqdataProcessHisRepository.findAll().stream().map(reqdataProcessHisMapper::toDto).collect(Collectors.toList());
        log.debug("ReqdataProcessHisCustomService: getAll() {}", result);
        return result;
    }

    public List<ReqdataProcessHisDTO> deleteAll(List<ReqdataProcessHisDTO> reqdataProcessHisDTOS) {
        log.debug("ReqdataProcessHisCustomService: deleteAll({})", reqdataProcessHisDTOS);
        List<Long> ids = reqdataProcessHisDTOS.stream().map(ReqdataProcessHisDTO::getId).collect(Collectors.toList());
        this.reqdataProcessHisRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            reqdataProcessHisRepository.deleteById(id);
            reqdataProcessHisSearchRepository.deleteById(id);
            log.debug("ReqdataProcessHisCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("ReqdataProcessHisCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ReqdataProcessHisDTO> saveAll(List<ReqdataProcessHisDTO> reqdataProcessHisDTOS){
        List<ReqdataProcessHisDTO> result = reqdataProcessHisRepository.saveAll(reqdataProcessHisDTOS.stream().map(reqdataProcessHisMapper::toEntity).collect(Collectors.toList())).stream().map(reqdataProcessHisMapper::toDto).collect(Collectors.toList());
        log.debug("ReqdataProcessHisCustomService: saveAll({}) {}", reqdataProcessHisDTOS, result);
        return result;
    }

    public List<ReqdataProcessHisDTO> getAllByRequestData(Long requestDataId){
//        List<ReqdataProcessHisDTO> result = this.reqdataProcessHisRepository.findAllByRequestDataId(requestDataId).stream().map(this.reqdataProcessHisMapper::toDto).collect(Collectors.toList());
        List<ReqdataProcessHisDTO> result = this.convertToDTO(this.reqdataProcessHisRepository.findAllByRequestDataId(requestDataId));
        log.debug("ReqdataProcessHisCustomService: getAllByUserGroup({}) {}", requestDataId, result);
        return result;
    }

    @Autowired
    public ObjectUtils objectUtils;
    private ReqdataProcessHisDTO convertToDTO(ReqdataProcessHis reqdataProcessHis) throws IllegalAccessException {
        ReqdataProcessHisDTO result = new ReqdataProcessHisDTO();
        result = this.objectUtils.copyproperties(reqdataProcessHis, result, ReqdataProcessHisDTO.class);
        return result;
    }

    private List<ReqdataProcessHisDTO> convertToDTO(List<ReqdataProcessHis> reqdataProcessHisList){
        List<ReqdataProcessHisDTO> result = new ArrayList<>();
        if(reqdataProcessHisList == null) return null;
        reqdataProcessHisList.forEach(ele -> {
            ReqdataProcessHisDTO reqdataProcessHisDTO = new ReqdataProcessHisDTO();
            try {
                result.add(this.objectUtils.copyproperties(ele, reqdataProcessHisDTO, ReqdataProcessHisDTO.class));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return result;
    };
}
