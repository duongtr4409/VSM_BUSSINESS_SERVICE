package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DispatchBookType;
import com.vsm.business.repository.DispatchBookTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DispatchBookTypeDTO;
import com.vsm.business.service.mapper.DispatchBookTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DispatchBookTypeSearchService implements IBaseSearchService<DispatchBookTypeDTO, DispatchBookType> {

    private final DispatchBookTypeRepository dispatchBookTypeRepository;

    private final DispatchBookTypeMapper dispatchBookTypeMapper;

    public DispatchBookTypeSearchService(DispatchBookTypeRepository dispatchBookTypeRepository, DispatchBookTypeMapper dispatchBookTypeMapper) {
        this.dispatchBookTypeRepository = dispatchBookTypeRepository;
        this.dispatchBookTypeMapper = dispatchBookTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DispatchBookTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookTypeRepository.findAll(pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(pageable).get().map(ele -> dispatchBookTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBookType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBookType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(specification, pageable).get().map(ele -> dispatchBookTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DispatchBookTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookTypeRepository.findAll(pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(pageable).get().map(ele -> dispatchBookTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBookType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBookType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(specification, pageable).get().map(ele -> dispatchBookTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DispatchBookTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookTypeRepository.findAll(pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DispatchBookTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBookType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBookType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookTypeDTO> listResult = dispatchBookTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DispatchBookTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
