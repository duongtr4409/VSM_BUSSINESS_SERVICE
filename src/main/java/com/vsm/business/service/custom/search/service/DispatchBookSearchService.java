package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DispatchBook;
import com.vsm.business.repository.DispatchBookRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DispatchBookDTO;
import com.vsm.business.service.mapper.DispatchBookMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DispatchBookSearchService implements IBaseSearchService<DispatchBookDTO, DispatchBook> {

    private final DispatchBookRepository dispatchBookRepository;

    private final DispatchBookMapper dispatchBookMapper;

    public DispatchBookSearchService(DispatchBookRepository dispatchBookRepository, DispatchBookMapper dispatchBookMapper) {
        this.dispatchBookRepository = dispatchBookRepository;
        this.dispatchBookMapper = dispatchBookMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DispatchBookDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookRepository.findAll(pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(pageable).get().map(ele -> dispatchBookMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBook> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBook.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(specification, pageable).get().map(ele -> dispatchBookMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DispatchBookDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookRepository.findAll(pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(pageable).get().map(ele -> dispatchBookMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBook> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBook.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(specification, pageable).get().map(ele -> dispatchBookMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DispatchBookDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = dispatchBookRepository.findAll(pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DispatchBookDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DispatchBook> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DispatchBook.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = dispatchBookRepository.findAll(specification, pageable).getTotalElements();
            List<DispatchBookDTO> listResult = dispatchBookRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DispatchBookDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
