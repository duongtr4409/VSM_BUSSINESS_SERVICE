package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.RequestGroup;
import com.vsm.business.repository.RequestGroupRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestGroupDTO;
import com.vsm.business.service.mapper.RequestGroupMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestGroupSearchService implements IBaseSearchService<RequestGroupDTO, RequestGroup> {

    private final RequestGroupRepository requestGroupRepository;

    private final RequestGroupMapper requestGroupMapper;

    public RequestGroupSearchService(RequestGroupRepository requestGroupRepository, RequestGroupMapper requestGroupMapper) {
        this.requestGroupRepository = requestGroupRepository;
        this.requestGroupMapper = requestGroupMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RequestGroupDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestGroupRepository.findAll(pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(pageable).get().map(ele -> requestGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestGroupRepository.findAll(specification, pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(specification, pageable).get().map(ele -> requestGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RequestGroupDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestGroupRepository.findAll(pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(pageable).get().map(ele -> requestGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestGroupRepository.findAll(specification, pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(specification, pageable).get().map(ele -> requestGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RequestGroupDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = requestGroupRepository.findAll(pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RequestGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RequestGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RequestGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = requestGroupRepository.findAll(specification, pageable).getTotalElements();
            List<RequestGroupDTO> listResult = requestGroupRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RequestGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
