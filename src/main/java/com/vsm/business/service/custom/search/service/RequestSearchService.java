package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Request;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.mapper.RequestMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestSearchService implements IBaseSearchService<RequestDTO, Request> {

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    public RequestSearchService(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RequestDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRepository.findAll(pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(pageable).get().map(ele -> requestMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Request> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Request.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(specification, pageable).get().map(ele -> requestMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RequestDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRepository.findAll(pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(pageable).get().map(ele -> requestMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Request> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Request.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(specification, pageable).get().map(ele -> requestMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RequestDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRepository.findAll(pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RequestDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Request> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Request.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDTO> listResult = requestRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RequestDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
