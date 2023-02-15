package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.FieldData;
import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldDataDTO;
import com.vsm.business.service.mapper.FieldDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FieldDataSearchService implements IBaseSearchService<FieldDataDTO, FieldData> {

    private final FieldDataRepository fieldDataRepository;

    private final FieldDataMapper fieldDataMapper;

    public FieldDataSearchService(FieldDataRepository fieldDataRepository, FieldDataMapper fieldDataMapper) {
        this.fieldDataRepository = fieldDataRepository;
        this.fieldDataMapper = fieldDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FieldDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldDataRepository.findAll(pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(pageable).get().map(ele -> fieldDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldDataRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(specification, pageable).get().map(ele -> fieldDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FieldDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldDataRepository.findAll(pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(pageable).get().map(ele -> fieldDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldDataRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(specification, pageable).get().map(ele -> fieldDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FieldDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldDataRepository.findAll(pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FieldDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldDataRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDataDTO> listResult = fieldDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FieldDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
