package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Step;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.StepMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepSearchService implements IBaseSearchService<StepDTO, Step> {

    private final StepRepository stepRepository;

    private final StepMapper stepMapper;

    public StepSearchService(StepRepository stepRepository, StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StepDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepRepository.findAll(pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(pageable).get().map(ele -> stepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Step> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Step.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepRepository.findAll(specification, pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(specification, pageable).get().map(ele -> stepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StepDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepRepository.findAll(pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(pageable).get().map(ele -> stepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Step> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Step.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepRepository.findAll(specification, pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(specification, pageable).get().map(ele -> stepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StepDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepRepository.findAll(pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Step> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Step.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepRepository.findAll(specification, pageable).getTotalElements();
            List<StepDTO> listResult = stepRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
