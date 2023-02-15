package com.vsm.business.service.custom;

import com.vsm.business.domain.Step;
import com.vsm.business.domain.StepData;
import com.vsm.business.domain.StepInProcess;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.repository.search.StepInProcessSearchRepository;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.dto.StepInProcessDTO;
import com.vsm.business.service.mapper.StepInProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepInProcessCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private StepInProcessRepository stepInProcessRepository;
    private StepInProcessSearchRepository stepInProcessSearchRepository;
    private StepInProcessMapper stepInProcessMapper;
    private UserInStepCustomService userInStepCustomService;
    private StepDataRepository stepDataRepository;

    public StepInProcessCustomService(StepInProcessRepository stepInProcessRepository, StepInProcessSearchRepository stepInProcessSearchRepository, StepInProcessMapper stepInProcessMapper, UserInStepCustomService userInStepCustomService, StepDataRepository stepDataRepository) {
        this.stepInProcessRepository = stepInProcessRepository;
        this.stepInProcessSearchRepository = stepInProcessSearchRepository;
        this.stepInProcessMapper = stepInProcessMapper;
        this.userInStepCustomService = userInStepCustomService;
        this.stepDataRepository = stepDataRepository;
    }
    public List<StepInProcessDTO> getAll() {
        log.debug("StepCustomService: getAll()");
        List<StepInProcess> stepInProcesses = this.stepInProcessRepository.findAll();
        List<StepInProcessDTO> result = new ArrayList<>();
        for (StepInProcess stepInProcess :
            stepInProcesses) {
            StepInProcessDTO stepInProcessDTO = stepInProcessMapper.toDto(stepInProcess);
            result.add(stepInProcessDTO);
        }
        return result;
    }

    public List<StepInProcessDTO> deleteAll(List<StepInProcessDTO> stepInProcessDTOS) {
        log.debug("StepInProcessCustomService: deleteAll({})", stepInProcessDTOS);
        List<Long> ids = stepInProcessDTOS.stream().map(StepInProcessDTO::getId).collect(Collectors.toList());
        this.stepInProcessRepository.deleteAllById(ids);
        //try {
//            this.stepInProcessSearchRepository.deleteAllById(ids);
        //}catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        //}
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            // Delete UserInStep
            Boolean resultDeleteUserInStep = userInStepCustomService.deleteAllByStepInProcessId(id);
            if(!resultDeleteUserInStep){    // when delete UserInStep false -> return false;
                return false;
            }
            List<StepData> stepDataList = this.stepDataRepository.findAllByStepInProcessId(id);
            List<StepData> stepDataUpdateList = new ArrayList<>();
            stepDataList.forEach(ele -> {
                ele.setStepInProcess(null);
                stepDataUpdateList.add(ele);
            });
            this.stepDataRepository.saveAll(stepDataUpdateList);
            stepInProcessRepository.deleteById(id);
            //try{
                //stepInProcessSearchRepository.deleteById(id);
            //}catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

            //}
            return true;
        } catch (Exception e) {
            log.error("StepInProcessCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
    public List<StepInProcessDTO> getAllByProcessInfoId(Long processInfoId)
    {
        List<StepInProcess> stepInProcesses = this.stepInProcessRepository.findAllByProcessInfoId(processInfoId);
        List<StepInProcessDTO> result = new ArrayList<>();
        for (StepInProcess stepInProcess :
            stepInProcesses) {
            StepInProcessDTO stepInProcessDTO = stepInProcessMapper.toDto(stepInProcess);
            result.add(stepInProcessDTO);
        }
        log.debug("StepCustomService: getAll({}) {}", processInfoId, result);
        return result;
    }
}
