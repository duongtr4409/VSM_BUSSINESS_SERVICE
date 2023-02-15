package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Field;
import com.vsm.business.repository.FieldRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldDTO;
import com.vsm.business.service.mapper.FieldMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FieldSearchService implements IBaseSearchService<FieldDTO, Field> {

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    public FieldSearchService(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FieldDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldRepository.findAll(pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(pageable).get().map(ele -> fieldMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Field> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Field.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(specification, pageable).get().map(ele -> fieldMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FieldDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldRepository.findAll(pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(pageable).get().map(ele -> fieldMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Field> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Field.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(specification, pageable).get().map(ele -> fieldMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FieldDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldRepository.findAll(pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FieldDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Field> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Field.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldRepository.findAll(specification, pageable).getTotalElements();
            List<FieldDTO> listResult = fieldRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FieldDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
