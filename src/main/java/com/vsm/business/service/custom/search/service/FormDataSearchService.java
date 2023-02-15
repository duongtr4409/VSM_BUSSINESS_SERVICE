package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.FormData;
import com.vsm.business.repository.FormDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FormDataDTO;
import com.vsm.business.service.mapper.FormDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormDataSearchService implements IBaseSearchService<FormDataDTO, FormData> {

    private final FormDataRepository formDataRepository;

    private final FormDataMapper formDataMapper;

    public FormDataSearchService(FormDataRepository formDataRepository, FormDataMapper formDataMapper) {
        this.formDataRepository = formDataRepository;
        this.formDataMapper = formDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FormDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formDataRepository.findAll(pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(pageable).get().map(ele -> formDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FormData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FormData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formDataRepository.findAll(specification, pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(specification, pageable).get().map(ele -> formDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FormDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formDataRepository.findAll(pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(pageable).get().map(ele -> formDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FormData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FormData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formDataRepository.findAll(specification, pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(specification, pageable).get().map(ele -> formDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FormDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = formDataRepository.findAll(pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FormDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FormData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FormData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = formDataRepository.findAll(specification, pageable).getTotalElements();
            List<FormDataDTO> listResult = formDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FormDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
