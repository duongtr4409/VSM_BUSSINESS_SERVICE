package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.StepInProcess;
import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepInProcessDTO;
import com.vsm.business.service.mapper.StepInProcessMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepInProcessSearchService implements IBaseSearchService<StepInProcessDTO, StepInProcess> {

    private final StepInProcessRepository stepInProcessRepository;

    private final StepInProcessMapper stepInProcessMapper;

    public StepInProcessSearchService(StepInProcessRepository stepInProcessRepository, StepInProcessMapper stepInProcessMapper) {
        this.stepInProcessRepository = stepInProcessRepository;
        this.stepInProcessMapper = stepInProcessMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StepInProcessDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepInProcessRepository.findAll(pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(pageable).get().map(ele -> stepInProcessMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepInProcess> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepInProcess.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepInProcessRepository.findAll(specification, pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(specification, pageable).get().map(ele -> stepInProcessMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StepInProcessDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepInProcessRepository.findAll(pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(pageable).get().map(ele -> stepInProcessMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepInProcess> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepInProcess.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepInProcessRepository.findAll(specification, pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(specification, pageable).get().map(ele -> stepInProcessMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StepInProcessDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepInProcessRepository.findAll(pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StepInProcessDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepInProcess> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepInProcess.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepInProcessRepository.findAll(specification, pageable).getTotalElements();
            List<StepInProcessDTO> listResult = stepInProcessRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StepInProcessDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
