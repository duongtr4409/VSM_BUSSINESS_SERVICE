package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.RequestType;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestTypeDTO;
import com.vsm.business.service.mapper.RequestTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestTypeSearchService implements IBaseSearchService<RequestTypeDTO, RequestType> {

    private final RequestTypeRepository requestTypeRepository;

    private final RequestTypeMapper requestTypeMapper;

    public RequestTypeSearchService(RequestTypeRepository requestTypeRepository, RequestTypeMapper requestTypeMapper) {
        this.requestTypeRepository = requestTypeRepository;
        this.requestTypeMapper = requestTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RequestTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestTypeRepository.findAll(pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(pageable).get().map(ele -> requestTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestTypeRepository.findAll(specification, pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(specification, pageable).get().map(ele -> requestTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RequestTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestTypeRepository.findAll(pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(pageable).get().map(ele -> requestTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestTypeRepository.findAll(specification, pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(specification, pageable).get().map(ele -> requestTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RequestTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestTypeRepository.findAll(pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RequestTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestTypeRepository.findAll(specification, pageable).getTotalElements();
            List<RequestTypeDTO> listResult = requestTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RequestTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
