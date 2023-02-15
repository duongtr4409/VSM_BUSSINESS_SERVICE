package com.vsm.business.service.custom;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.ProcessDataSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.ProcessDataDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.ProcessDataMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessDataCustomService {
    private final Logger log = LoggerFactory.getLogger(ProcessDataCustomService.class);

    private ProcessDataRepository processDataRepository;

    private ProcessDataSearchRepository processDataSearchRepository;

    private ProcessDataMapper processDataMapper;

    public ProcessDataCustomService(ProcessDataRepository processDataRepository, ProcessDataSearchRepository processDataSearchRepository, ProcessDataMapper processDataMapper) {
        this.processDataRepository = processDataRepository;
        this.processDataSearchRepository = processDataSearchRepository;
        this.processDataMapper = processDataMapper;
    }

    public List<ProcessDataDTO> getAll() {
        log.debug("ProcessDataCustomService: getAll()");
        List<ProcessDataDTO> result = new ArrayList<>();
        try {
            List<ProcessData> processDatas = this.processDataRepository.findAll();
            for (ProcessData processData :
                processDatas) {
                ProcessDataDTO processDataDTO = processDataMapper.toDto(processData);
                result.add(processDataDTO);
            }
        }catch (Exception e){
            log.error("ProcessDataCustomService: getAll() {}", e);
        }
        log.debug("ProcessDataCustomService: getAll() {}", result);
        return result;
    }

    public List<ProcessDataDTO> deleteAll(List<ProcessDataDTO> processDataDTOS) {
        log.debug("ProcessDataCustomService: deleteAll({})", processDataDTOS);
        List<Long> ids = processDataDTOS.stream().map(ProcessDataDTO::getId).collect(Collectors.toList());
        this.processDataRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            processDataRepository.deleteById(id);
            processDataSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ProcessDataCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ProcessDataDTO> getAllByReqDataId(Long requestDataId){
        return processDataRepository.findAllByRequestDataId(requestDataId).stream().map(processDataMapper::toDto).collect(Collectors.toList());
    }
}
