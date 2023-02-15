package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.RequestDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestDataSearchService implements IBaseSearchService<RequestDataDTO, RequestData> {

    private final RequestDataRepository requestDataRepository;

    private final RequestDataMapper requestDataMapper;

    public RequestDataSearchService(RequestDataRepository requestDataRepository, RequestDataMapper requestDataMapper) {
        this.requestDataRepository = requestDataRepository;
        this.requestDataMapper = requestDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RequestDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestDataRepository.findAll(pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(pageable).get().map(ele -> requestDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestDataRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(specification, pageable).get().map(ele -> requestDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RequestDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestDataRepository.findAll(pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(pageable).get().map(ele -> requestDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestDataRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(specification, pageable).get().map(ele -> requestDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RequestDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestDataRepository.findAll(pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RequestDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestDataRepository.findAll(specification, pageable).getTotalElements();
            List<RequestDataDTO> listResult = requestDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RequestDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
