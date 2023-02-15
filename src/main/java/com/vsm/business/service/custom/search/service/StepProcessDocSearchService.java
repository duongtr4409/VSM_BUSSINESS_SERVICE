package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.StepProcessDoc;
import com.vsm.business.repository.StepProcessDocRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepProcessDocDTO;
import com.vsm.business.service.mapper.StepProcessDocMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepProcessDocSearchService implements IBaseSearchService<StepProcessDocDTO, StepProcessDoc> {

    private final StepProcessDocRepository stepProcessDocRepository;

    private final StepProcessDocMapper stepProcessDocMapper;

    public StepProcessDocSearchService(StepProcessDocRepository stepProcessDocRepository, StepProcessDocMapper stepProcessDocMapper) {
        this.stepProcessDocRepository = stepProcessDocRepository;
        this.stepProcessDocMapper = stepProcessDocMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StepProcessDocDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepProcessDocRepository.findAll(pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(pageable).get().map(ele -> stepProcessDocMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepProcessDoc> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepProcessDoc.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepProcessDocRepository.findAll(specification, pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(specification, pageable).get().map(ele -> stepProcessDocMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StepProcessDocDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepProcessDocRepository.findAll(pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(pageable).get().map(ele -> stepProcessDocMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepProcessDoc> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepProcessDoc.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepProcessDocRepository.findAll(specification, pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(specification, pageable).get().map(ele -> stepProcessDocMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StepProcessDocDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stepProcessDocRepository.findAll(pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StepProcessDocDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StepProcessDoc> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StepProcessDoc.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stepProcessDocRepository.findAll(specification, pageable).getTotalElements();
            List<StepProcessDocDTO> listResult = stepProcessDocRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StepProcessDocDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
