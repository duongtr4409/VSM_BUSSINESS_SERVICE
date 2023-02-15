package com.vsm.business.service.custom;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.RequestRecall;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.RequestRecallRepository;
import com.vsm.business.repository.search.RequestRecallSearchRepository;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.dto.RequestRecallDTO;
import com.vsm.business.service.mapper.RequestRecallMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestRecallCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private RequestRecallRepository requestRecallRepository;
    private RequestRecallSearchRepository requestRecallSearchRepository;
    private RequestRecallMapper requestRecallMapper;

    private RequestDataRepository requestDataRepository;

    public RequestRecallCustomService(RequestRecallRepository requestRecallRepository, RequestRecallSearchRepository requestRecallSearchRepository, RequestRecallMapper requestRecallMapper, RequestDataRepository requestDataRepository) {
        this.requestRecallRepository = requestRecallRepository;
        this.requestRecallSearchRepository = requestRecallSearchRepository;
        this.requestRecallMapper = requestRecallMapper;
        this.requestDataRepository = requestDataRepository;
    }

    public List<RequestRecallDTO> getAll() {
        log.debug("RequestRecallCustomService: getAll()");
        List<RequestRecall> ranks = this.requestRecallRepository.findAll();
        List<RequestRecallDTO> result = new ArrayList<>();
        for (RequestRecall requestRecall :
            ranks) {
            RequestRecallDTO requestRecallDTO = requestRecallMapper.toDto(requestRecall);
            result.add(requestRecallDTO);
        }
        return result;
    }

    public List<RequestRecallDTO> deleteAll(List<RequestRecallDTO> requestRecallDTOS) {
        log.debug("RequestRecallCustomService: deleteAll({})", requestRecallDTOS);
        List<Long> ids = requestRecallDTOS.stream().map(RequestRecallDTO::getId).collect(Collectors.toList());
        this.requestRecallRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            requestRecallRepository.deleteById(id);
            requestRecallSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RequestRecallCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<RequestRecallDTO> saveAll(List<RequestRecallDTO> rankDTOList){
        List<RequestRecallDTO> result = requestRecallRepository.saveAll(rankDTOList.stream().map(requestRecallMapper::toEntity).collect(Collectors.toList())).stream().map(requestRecallMapper::toDto).collect(Collectors.toList());
        log.debug("RequestRecallCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

    public List<RequestRecallDTO> getByRequestData(Long requestDataId){
//        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
//        List<ProcessData> processDataList = requestData.getProcessData().stream().collect(Collectors.toList());
//        if(processDataList == null || processDataList.isEmpty()) throw new RuntimeException("Can not find ProcessData");
//        ProcessData processData = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().orElse(null);
//        if(processData == null) throw new RuntimeException("Can not find ProcessData");
//        List<StepData> stepDataList = processData.getStepData().stream().collect(Collectors.toList());
//        List<RequestRecall> requestRecallList = new ArrayList<>();

        List<RequestRecallDTO> requestRecallList = this.requestRecallRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> {
            return ele.getProcessTime() == null && ele.getProcesser() == null;
        }).map(ele -> {
            try {
                return this.convertToDTO(ele);
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        }).collect(Collectors.toList());
        return requestRecallList;
    }



        // utils \\
    @Autowired
    private ObjectUtils objectUtils;
    public RequestRecallDTO convertToDTO(RequestRecall requestRecall) throws IllegalAccessException {
        if(requestRecall == null) return null;
        RequestRecallDTO result = new RequestRecallDTO();
        result = this.objectUtils.copyproperties(requestRecall, result, RequestRecallDTO.class);
        return result;
    }

    public List<RequestRecallDTO> convertToDTO(List<RequestRecall> requestRecallList){
        if(requestRecallList == null) return null;
        List<RequestRecallDTO> result = new ArrayList<>();
        requestRecallList.forEach(ele -> {
            try {
                result.add(this.convertToDTO(ele));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
        return result;
    }

}
