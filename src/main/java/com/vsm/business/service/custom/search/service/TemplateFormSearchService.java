package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.TemplateForm;
import com.vsm.business.repository.TemplateFormRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TemplateFormDTO;
import com.vsm.business.service.mapper.TemplateFormMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TemplateFormSearchService implements IBaseSearchService<TemplateFormDTO, TemplateForm> {

    private final TemplateFormRepository templateFormRepository;

    private final TemplateFormMapper templateFormMapper;

    public TemplateFormSearchService(TemplateFormRepository templateFormRepository, TemplateFormMapper templateFormMapper) {
        this.templateFormRepository = templateFormRepository;
        this.templateFormMapper = templateFormMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(TemplateFormDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = templateFormRepository.findAll(pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(pageable).get().map(ele -> templateFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TemplateForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TemplateForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = templateFormRepository.findAll(specification, pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(specification, pageable).get().map(ele -> templateFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(TemplateFormDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = templateFormRepository.findAll(pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(pageable).get().map(ele -> templateFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TemplateForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TemplateForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = templateFormRepository.findAll(specification, pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(specification, pageable).get().map(ele -> templateFormMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(TemplateFormDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = templateFormRepository.findAll(pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(pageable).get().map(ele -> this.convertToDTO(TemplateFormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TemplateForm> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TemplateForm.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = templateFormRepository.findAll(specification, pageable).getTotalElements();
            List<TemplateFormDTO> listResult = templateFormRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(TemplateFormDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
