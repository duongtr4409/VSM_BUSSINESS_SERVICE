package com.vsm.business.service.custom;

import com.vsm.business.domain.Step;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.StepMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private StepRepository stepRepository;

    private StepSearchRepository stepSearchRepository;

    private StepMapper stepMapper;

    private ObjectUtils objectUtils;

    public StepCustomService(StepRepository stepRepository, StepSearchRepository stepSearchRepository, StepMapper stepMapper, ObjectUtils objectUtils) {
        this.stepRepository = stepRepository;
        this.stepSearchRepository = stepSearchRepository;
        this.stepMapper = stepMapper;
        this.objectUtils = objectUtils;
    }

    public List<StepDTO> getAll() {
        log.debug("StepCustomService: getAll()");
        List<StepDTO> result = new ArrayList<>();
        try {
            List<Step> steps = this.stepRepository.findAll();
            for (Step step :
                steps) {
                StepDTO stepDTO = stepMapper.toDto(step);
                result.add(stepDTO);
            }
        }catch (Exception e){
            log.error("StepCustomService: getAll() {}", e);
        }
        log.debug("StepCustomService: getAll() {}", result);
        return result;
    }

    public List<StepDTO> deleteAll(List<StepDTO> stepDTOS) {
        log.debug("StepCustomService: deleteAll({})", stepDTOS);
        List<Long> ids = stepDTOS.stream().map(StepDTO::getId).collect(Collectors.toList());
        this.stepRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            stepRepository.deleteById(id);
            stepSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("StepCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<StepDTO> saveAll(List<StepDTO> stepDTOList){
        List<StepDTO> result = stepRepository.saveAll(stepDTOList.stream().map(stepMapper::toEntity).collect(Collectors.toList())).stream().map(stepMapper::toDto).collect(Collectors.toList());
        log.info("StepCustomService: saveAll({}) {}");
        return result;
    }

    public List<StepDTO> getAllIgnoreField() {
        log.debug("StepCustomService: getAllIgnoreField()");
        List<StepDTO> result = new ArrayList<>();
        try {
            List<Step> steps = this.stepRepository.findAll();
            for (Step step :
                steps) {
                if(step != null){
                    StepDTO stepDTO = new StepDTO();
                    try {stepDTO = this.objectUtils.coppySimpleType(step, stepDTO, StepDTO.class);}catch (IllegalAccessException e){log.error("{}", e);}
                    result.add(stepDTO);
                }
            }
        }catch (Exception e){
            log.error("StepCustomService: getAll() {}", e);
        }
        log.debug("StepCustomService: getAll() {}", result);
        return result;
    }
}
