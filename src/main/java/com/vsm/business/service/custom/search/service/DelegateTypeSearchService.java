package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DelegateType;
import com.vsm.business.repository.DelegateTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DelegateTypeDTO;
import com.vsm.business.service.mapper.DelegateTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DelegateTypeSearchService implements IBaseSearchService<DelegateTypeDTO, DelegateType> {

    private final DelegateTypeRepository delegateTypeRepository;

    private final DelegateTypeMapper delegateTypeMapper;

    public DelegateTypeSearchService(DelegateTypeRepository delegateTypeRepository, DelegateTypeMapper delegateTypeMapper) {
        this.delegateTypeRepository = delegateTypeRepository;
        this.delegateTypeMapper = delegateTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DelegateTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateTypeRepository.findAll(pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(pageable).get().map(ele -> delegateTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(specification, pageable).get().map(ele -> delegateTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DelegateTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateTypeRepository.findAll(pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(pageable).get().map(ele -> delegateTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(specification, pageable).get().map(ele -> delegateTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DelegateTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateTypeRepository.findAll(pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DelegateTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateTypeDTO> listResult = delegateTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DelegateTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
