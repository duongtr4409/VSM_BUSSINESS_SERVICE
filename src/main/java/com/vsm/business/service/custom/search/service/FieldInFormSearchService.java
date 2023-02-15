package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.FieldInForm;
import com.vsm.business.repository.FieldInFormRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldInFormDTO;
import com.vsm.business.service.mapper.FieldInFormMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FieldInFormSearchService implements IBaseSearchService<FieldInFormDTO, FieldInForm> {

    private final FieldInFormRepository fieldInFormRepository;

    private final FieldInFormMapper fieldInFormMapper;

    public FieldInFormSearchService(FieldInFormRepository fieldInFormRepository, FieldInFormMapper fieldInFormMapper) {
        this.fieldInFormRepository = fieldInFormRepository;
        this.fieldInFormMapper = fieldInFormMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FieldInFormDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldInFormRepository.findAll(pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(pageable).get().map(ele -> fieldInFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldInForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldInForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldInFormRepository.findAll(specification, pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(specification, pageable).get().map(ele -> fieldInFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FieldInFormDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldInFormRepository.findAll(pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(pageable).get().map(ele -> fieldInFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldInForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldInForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldInFormRepository.findAll(specification, pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(specification, pageable).get().map(ele -> fieldInFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FieldInFormDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fieldInFormRepository.findAll(pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FieldInFormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FieldInForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FieldInForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fieldInFormRepository.findAll(specification, pageable).getTotalElements();
            List<FieldInFormDTO> listResult = fieldInFormRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FieldInFormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
