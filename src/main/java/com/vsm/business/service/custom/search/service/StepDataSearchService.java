package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.StepData;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepDataDTO;
import com.vsm.business.service.mapper.StepDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepDataSearchService implements IBaseSearchService<StepDataDTO, StepData> {

    private final StepDataRepository stepDataRepository;

    private final StepDataMapper stepDataMapper;

    public StepDataSearchService(StepDataRepository stepDataRepository, StepDataMapper stepDataMapper) {
        this.stepDataRepository = stepDataRepository;
        this.stepDataMapper = stepDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StepDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepDataRepository.findAll(pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(pageable).get().map(ele -> stepDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepDataRepository.findAll(specification, pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(specification, pageable).get().map(ele -> stepDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StepDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepDataRepository.findAll(pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(pageable).get().map(ele -> stepDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepDataRepository.findAll(specification, pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(specification, pageable).get().map(ele -> stepDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StepDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepDataRepository.findAll(pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StepDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepDataRepository.findAll(specification, pageable).getTotalElements();
            List<StepDataDTO> listResult = stepDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StepDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
