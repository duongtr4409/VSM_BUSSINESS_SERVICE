package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Stamp;
import com.vsm.business.repository.StampRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StampDTO;
import com.vsm.business.service.mapper.StampMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StampSearchService implements IBaseSearchService<StampDTO, Stamp> {

    private final StampRepository stampRepository;

    private final StampMapper stampMapper;

    public StampSearchService(StampRepository stampRepository, StampMapper stampMapper) {
        this.stampRepository = stampRepository;
        this.stampMapper = stampMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StampDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stampRepository.findAll(pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(pageable).get().map(ele -> stampMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Stamp> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Stamp.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stampRepository.findAll(specification, pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(specification, pageable).get().map(ele -> stampMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StampDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stampRepository.findAll(pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(pageable).get().map(ele -> stampMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Stamp> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Stamp.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stampRepository.findAll(specification, pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(specification, pageable).get().map(ele -> stampMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StampDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = stampRepository.findAll(pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StampDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Stamp> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Stamp.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = stampRepository.findAll(specification, pageable).getTotalElements();
            List<StampDTO> listResult = stampRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StampDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
