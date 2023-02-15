package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Form;
import com.vsm.business.repository.FormRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FormDTO;
import com.vsm.business.service.mapper.FormMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormSearchService implements IBaseSearchService<FormDTO, Form> {

    private final FormRepository formRepository;

    private final FormMapper formMapper;

    public FormSearchService(FormRepository formRepository, FormMapper formMapper) {
        this.formRepository = formRepository;
        this.formMapper = formMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FormDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formRepository.findAll(pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(pageable).get().map(ele -> formMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Form> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Form.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formRepository.findAll(specification, pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(specification, pageable).get().map(ele -> formMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FormDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formRepository.findAll(pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(pageable).get().map(ele -> formMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Form> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Form.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formRepository.findAll(specification, pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(specification, pageable).get().map(ele -> formMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FormDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formRepository.findAll(pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Form> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Form.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formRepository.findAll(specification, pageable).getTotalElements();
            List<FormDTO> listResult = formRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
