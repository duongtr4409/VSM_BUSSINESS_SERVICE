package com.vsm.business.service.custom;

import com.vsm.business.domain.PriorityLevel;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.PriorityLevelRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.PriorityLevelSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.PriorityLevelDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.PriorityLevelMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriorityLevelCustomService {
    private final Logger log = LoggerFactory.getLogger(PriorityLevelCustomService.class);

    private PriorityLevelRepository priorityLevelRepository;

    private PriorityLevelSearchRepository priorityLevelSearchRepository;

    private PriorityLevelMapper priorityLevelMapper;

    public PriorityLevelCustomService(PriorityLevelRepository priorityLevelRepository, PriorityLevelSearchRepository priorityLevelSearchRepository, PriorityLevelMapper priorityLevelMapper) {
        this.priorityLevelRepository = priorityLevelRepository;
        this.priorityLevelSearchRepository = priorityLevelSearchRepository;
        this.priorityLevelMapper = priorityLevelMapper;
    }

    public List<PriorityLevelDTO> getAll() {
        log.debug("PriorityLevelCustomService: getAll()");
        List<PriorityLevelDTO> result = new ArrayList<>();
        try {
            List<PriorityLevel> priorityLevels = this.priorityLevelRepository.findAll();
            for (PriorityLevel priorityLevel :
                priorityLevels) {
                PriorityLevelDTO priorityLevelDTO = priorityLevelMapper.toDto(priorityLevel);
                result.add(priorityLevelDTO);
            }
        }catch (Exception e){
            log.error("PriorityLevelCustomService: getAll() {}", e);
        }
        log.debug("PriorityLevelCustomService: getAll() {}", result);
        return result;
    }

    public List<PriorityLevelDTO> deleteAll(List<PriorityLevelDTO> stepDTOS) {
        log.debug("PriorityLevelCustomService: deleteAll({})", stepDTOS);
        List<Long> ids = stepDTOS.stream().map(PriorityLevelDTO::getId).collect(Collectors.toList());
        this.priorityLevelRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            priorityLevelRepository.deleteById(id);
            priorityLevelSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("PriorityLevelCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
