package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.PriorityLevel;
import com.vsm.business.repository.PriorityLevelRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.PriorityLevelDTO;
import com.vsm.business.service.mapper.PriorityLevelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriorityLevelSearchService implements IBaseSearchService<PriorityLevelDTO, PriorityLevel> {

    private final PriorityLevelRepository priorityLevelRepository;

    private final PriorityLevelMapper priorityLevelMapper;

    public PriorityLevelSearchService(PriorityLevelRepository priorityLevelRepository, PriorityLevelMapper priorityLevelMapper) {
        this.priorityLevelRepository = priorityLevelRepository;
        this.priorityLevelMapper = priorityLevelMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(PriorityLevelDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priorityLevelRepository.findAll(pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(pageable).get().map(ele -> priorityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriorityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriorityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priorityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(specification, pageable).get().map(ele -> priorityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(PriorityLevelDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priorityLevelRepository.findAll(pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(pageable).get().map(ele -> priorityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriorityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriorityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priorityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(specification, pageable).get().map(ele -> priorityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(PriorityLevelDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priorityLevelRepository.findAll(pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(pageable).get().map(ele -> this.convertToDTO(PriorityLevelDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriorityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriorityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priorityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<PriorityLevelDTO> listResult = priorityLevelRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(PriorityLevelDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
