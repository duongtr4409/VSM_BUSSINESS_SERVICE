package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ResultOfStep;
import com.vsm.business.repository.ResultOfStepRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ResultOfStepDTO;
import com.vsm.business.service.mapper.ResultOfStepMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultOfStepSearchService implements IBaseSearchService<ResultOfStepDTO, ResultOfStep> {

    private final ResultOfStepRepository resultOfStepRepository;

    private final ResultOfStepMapper resultOfStepMapper;

    public ResultOfStepSearchService(ResultOfStepRepository resultOfStepRepository, ResultOfStepMapper resultOfStepMapper) {
        this.resultOfStepRepository = resultOfStepRepository;
        this.resultOfStepMapper = resultOfStepMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ResultOfStepDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = resultOfStepRepository.findAll(pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(pageable).get().map(ele -> resultOfStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ResultOfStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ResultOfStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = resultOfStepRepository.findAll(specification, pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(specification, pageable).get().map(ele -> resultOfStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ResultOfStepDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = resultOfStepRepository.findAll(pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(pageable).get().map(ele -> resultOfStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ResultOfStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ResultOfStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = resultOfStepRepository.findAll(specification, pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(specification, pageable).get().map(ele -> resultOfStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ResultOfStepDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = resultOfStepRepository.findAll(pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ResultOfStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ResultOfStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ResultOfStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = resultOfStepRepository.findAll(specification, pageable).getTotalElements();
            List<ResultOfStepDTO> listResult = resultOfStepRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ResultOfStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
