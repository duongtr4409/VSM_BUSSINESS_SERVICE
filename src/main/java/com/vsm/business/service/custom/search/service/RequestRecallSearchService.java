package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.RequestRecall;
import com.vsm.business.repository.RequestRecallRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestRecallDTO;
import com.vsm.business.service.mapper.RequestRecallMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestRecallSearchService implements IBaseSearchService<RequestRecallDTO, RequestRecall> {

    private final RequestRecallRepository requestRecallRepository;

    private final RequestRecallMapper requestRecallMapper;

    public RequestRecallSearchService(RequestRecallRepository requestRecallRepository, RequestRecallMapper requestRecallMapper) {
        this.requestRecallRepository = requestRecallRepository;
        this.requestRecallMapper = requestRecallMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RequestRecallDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRecallRepository.findAll(pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(pageable).get().map(ele -> requestRecallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestRecall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestRecall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRecallRepository.findAll(specification, pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(specification, pageable).get().map(ele -> requestRecallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RequestRecallDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRecallRepository.findAll(pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(pageable).get().map(ele -> requestRecallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestRecall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestRecall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRecallRepository.findAll(specification, pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(specification, pageable).get().map(ele -> requestRecallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RequestRecallDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestRecallRepository.findAll(pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RequestRecallDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestRecall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestRecall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestRecallRepository.findAll(specification, pageable).getTotalElements();
            List<RequestRecallDTO> listResult = requestRecallRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RequestRecallDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
