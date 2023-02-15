package com.vsm.business.service.custom;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.search.StepDataSearchRepository;
import com.vsm.business.service.dto.*;
import com.vsm.business.service.mapper.StepDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepDataCustomService {
    private final Logger log = LoggerFactory.getLogger(StepDataCustomService.class);

    private StepDataRepository stepDataRepository;

    private StepDataSearchRepository stepDataSearchRepository;

    private StepDataMapper stepDataMapper;

    private ProcessDataRepository processDataRepository;

    private RequestDataRepository requestDataRepository;

    public StepDataCustomService(StepDataRepository stepDataRepository, StepDataSearchRepository stepDataSearchRepository, StepDataMapper stepDataMapper, ProcessDataRepository processDataRepository, RequestDataRepository requestDataRepository) {
        this.stepDataRepository = stepDataRepository;
        this.stepDataSearchRepository = stepDataSearchRepository;
        this.stepDataMapper = stepDataMapper;
        this.processDataRepository = processDataRepository;
        this.requestDataRepository = requestDataRepository;
    }

    public List<StepDataDTO> getAll() {
        log.debug("StepDataCustomService: getAll()");
        List<StepDataDTO> result = new ArrayList<>();
        try {
            List<StepData> stepDatas = this.stepDataRepository.findAll();
            for (StepData stepData :
                stepDatas) {
                StepDataDTO stepDataDTO = stepDataMapper.toDto(stepData);
                result.add(stepDataDTO);
            }
        }catch (Exception e){
            log.error("StepDataCustomService: getAll() {}", e);
        }
        log.debug("StepDataCustomService: getAll() {}", result);
        return result;
    }

    public List<StepDataDTO> deleteAll(List<StepDataDTO> stepDataDTOS) {
        log.debug("StepDataCustomService: deleteAll({})", stepDataDTOS);
        List<Long> ids = stepDataDTOS.stream().map(StepDataDTO::getId).collect(Collectors.toList());
        this.stepDataRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            stepDataRepository.deleteById(id);
            stepDataSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("StepDataCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<StepDataDTO> getAllByReqDataId(Long requestDataId){
        return stepDataRepository.findAllByRequestDataId(requestDataId).stream().map(stepDataMapper::toDto).collect(Collectors.toList());
    }

    public List<StepDataDTO> getAllByProcessDataId(Long processDataId){
        //return stepDataRepository.findAllByProcessDataId(processDataId).stream().map(stepDataMapper::toDto).collect(Collectors.toList());
        return stepDataRepository.findAllByProcessDataId(processDataId).stream().sorted((ele1, ele2) -> (int)(ele1.getStepOrder() - ele2.getStepOrder())).map(ele -> {
            return this.convertToDTO(ele);
        }).collect(Collectors.toList());
    }

    public List<StepDataDTO> getCurrentStepData(Long requestDataId, Boolean ignoreField){
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        List<ProcessData> processDataList = this.processDataRepository.findAllByRequestDataId(requestDataId);
        ProcessData currentProcessData = processDataList.stream().filter(ele -> requestData.getCurrentRound().equals(ele.getRoundNumber())).findFirst().get();
        if(ignoreField){
            return stepDataRepository.findAllByProcessDataId(currentProcessData.getId()).stream().sorted((ele1, ele2) -> (int)(ele1.getStepOrder() - ele2.getStepOrder())).map(ele -> {
                return this.convertToDTO(ele);
            }).collect(Collectors.toList());
        }else{
            return stepDataRepository.findAllByProcessDataId(currentProcessData.getId()).stream().sorted((ele1, ele2) -> (int)(ele1.getStepOrder() - ele2.getStepOrder())).map(stepDataMapper::toDto).collect(Collectors.toList());
        }
    }


    private StepDataDTO convertToDTO(StepData stepData){
        if(stepData == null) return null;
        StepDataDTO result = new StepDataDTO();
        BeanUtils.copyProperties(stepData, result, "requestData");
        RequestDataDTO requestDataDTO = new RequestDataDTO();
        requestDataDTO.setId(stepData.getRequestData().getId());
        result.setUserInfos(
            stepData.getUserInfos().stream().map(ele -> {
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                if(ele != null){
                    BeanUtils.copyProperties(ele, userInfoDTO);
                }
                return userInfoDTO;
            }).collect(Collectors.toSet())
        );
        result.setRequestData(requestDataDTO);
        if(stepData.getProcessData() != null){
            ProcessDataDTO processDataDTO = new ProcessDataDTO();
            processDataDTO.setId(stepData.getProcessData().getId());
            result.setProcessData(processDataDTO);
        }
        if(result.getMailTemplate() == null && stepData.getMailTemplate() != null){
            MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
            mailTemplateDTO.setId(stepData.getMailTemplate().getId());
            result.setMailTemplate(mailTemplateDTO);
        }
        if(result.getMailTemplateCustomer() == null && stepData.getMailTemplateCustomer() != null){
            MailTemplateDTO mailTemplateCustomerDTO = new MailTemplateDTO();
            mailTemplateCustomerDTO.setId(stepData.getMailTemplateCustomer().getId());
            result.setMailTemplateCustomer(mailTemplateCustomerDTO);
        }
        return result;
    }
}
