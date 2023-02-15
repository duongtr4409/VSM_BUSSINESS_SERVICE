package com.vsm.business.service.custom;

import com.vsm.business.domain.Step;
import com.vsm.business.domain.StepData;
import com.vsm.business.domain.TransferHandle;
import com.vsm.business.domain.UserInStep;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.TransferHandleRepository;
import com.vsm.business.repository.search.TransferHandleSearchRepository;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.dto.StepDataDTO;
import com.vsm.business.service.dto.TransferHandleDTO;
import com.vsm.business.service.dto.UserInStepDTO;
import com.vsm.business.service.mapper.TransferHandleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransferHandleCustomService {
    private final Logger log = LoggerFactory.getLogger(TransferHandleCustomService.class);

    private final TransferHandleRepository transferHandleRepository;

    private final TransferHandleMapper transferHandleMapper;

    private final TransferHandleSearchRepository transferHandleSearchRepository;

    private final RequestDataRepository requestDataRepository;

    private final StepDataRepository stepDataRepository;


    public TransferHandleCustomService(TransferHandleRepository transferHandleRepository, TransferHandleMapper transferHandleMapper, TransferHandleSearchRepository transferHandleSearchRepository, RequestDataRepository requestDataRepository, StepDataRepository stepDataRepository) {
        this.transferHandleRepository = transferHandleRepository;
        this.transferHandleMapper = transferHandleMapper;
        this.transferHandleSearchRepository = transferHandleSearchRepository;
        this.requestDataRepository = requestDataRepository;
        this.stepDataRepository = stepDataRepository;
    }

    public List<TransferHandleDTO> getAll() {
        log.debug("TransferHandleCustomService: getAll()");
        List<TransferHandleDTO> result = new ArrayList<>();
        try {
            List<TransferHandle> handleRepositoryAll = this.transferHandleRepository.findAll();
            for (TransferHandle step :
                handleRepositoryAll) {
                TransferHandleDTO transferHandleDTO = transferHandleMapper.toDto(step);
                result.add(transferHandleDTO);
            }
        }catch (Exception e){
            log.error("TransferHandleCustomService: getAll() {}", e);
        }
        log.debug("TransferHandleCustomService: getAll() {}", result);
        return result;
    }

    public List<TransferHandleDTO> deleteAll(List<TransferHandleDTO> transferHandleDTOS) {
        log.debug("TransferHandleCustomService: deleteAll({})", transferHandleDTOS);
        List<Long> ids = transferHandleDTOS.stream().map(TransferHandleDTO::getId).collect(Collectors.toList());
        this.transferHandleRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            transferHandleRepository.deleteById(id);
            //transferHandleSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("TransferHandleCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<TransferHandleDTO> saveAll(List<TransferHandleDTO> stepDTOList){
        List<TransferHandleDTO> result = transferHandleRepository.saveAll(stepDTOList.stream().map(transferHandleMapper::toEntity).collect(Collectors.toList())).stream().map(transferHandleMapper::toDto).collect(Collectors.toList());
        log.info("StepCustomService: saveAll({}) {}");
        return result;
    }

    public List<TransferHandleDTO> getAllByRequestData(Long requestDataId){
        List<TransferHandleDTO> result = new ArrayList<>();
        List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
        stepDataList.forEach(ele -> {
            if(ele != null && ele.getId() != null){
                List<TransferHandleDTO> transferHandleDTOList = this.transferHandleRepository.findAllByStepDataId(ele.getId()).stream().map(ele1 -> {
                    TransferHandleDTO transferHandleDTO = new TransferHandleDTO();
                    if(ele1 != null) BeanUtils.copyProperties(ele1, transferHandleDTO);
                    if(ele1.getStepData() != null){
                        StepDataDTO stepDataDTO = new StepDataDTO();
                        BeanUtils.copyProperties(ele1.getStepData(), stepDataDTO, "nextStep", "processData", "requestData", "tennant", "created", "modified", "stepInProcess", "rank", "mailTemplate", "mailTemplateCustomer", "userInfos");
                        transferHandleDTO.setStepData(stepDataDTO);
                    }
                    return transferHandleDTO;
                }).collect(Collectors.toList());
                result.addAll(transferHandleDTOList);
            }
        });
        return result;
    }
}
