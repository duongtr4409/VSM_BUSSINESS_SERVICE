package com.vsm.business.service.custom;

import com.vsm.business.domain.Status;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.search.StatusSearchRepository;
import com.vsm.business.repository.search.StepDataSearchRepository;
import com.vsm.business.service.dto.StatusDTO;
import com.vsm.business.service.dto.StepDataDTO;
import com.vsm.business.service.mapper.StatusMapper;
import com.vsm.business.service.mapper.StepDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusCustomService {
    private final Logger log = LoggerFactory.getLogger(StatusCustomService.class);

    private StatusRepository statusRepository;

    private StatusSearchRepository statusSearchRepository;

    private StatusMapper statusMapper;

    public StatusCustomService(StatusRepository statusRepository, StatusSearchRepository statusSearchRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusSearchRepository = statusSearchRepository;
        this.statusMapper = statusMapper;
    }

    public List<StatusDTO> getAll() {
        log.debug("StatusCustomService: getAll()");
        List<StatusDTO> result = new ArrayList<>();
        try {
            List<Status> statuses = this.statusRepository.findAll();
            for (Status status :
                statuses) {
                StatusDTO statusDTO = statusMapper.toDto(status);
                result.add(statusDTO);
            }
        }catch (Exception e){
            log.error("StatusCustomService: getAll() {}", e);
        }
        log.debug("StatusCustomService: getAll() {}", result);
        return result;
    }

    public List<StatusDTO> deleteAll(List<StatusDTO> statusDTO) {
        log.debug("StatusCustomService: deleteAll({})", statusDTO);
        List<Long> ids = statusDTO.stream().map(StatusDTO::getId).collect(Collectors.toList());
        this.statusRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            statusRepository.deleteById(id);
            statusSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("StatusCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

}
