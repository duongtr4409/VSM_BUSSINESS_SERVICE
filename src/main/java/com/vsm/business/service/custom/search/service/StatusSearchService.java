package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Status;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StatusDTO;
import com.vsm.business.service.mapper.StatusMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatusSearchService implements IBaseSearchService<StatusDTO, Status> {

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusSearchService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StatusDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusRepository.findAll(pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(pageable).get().map(ele -> statusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Status> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Status.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusRepository.findAll(specification, pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(specification, pageable).get().map(ele -> statusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StatusDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusRepository.findAll(pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(pageable).get().map(ele -> statusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Status> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Status.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusRepository.findAll(specification, pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(specification, pageable).get().map(ele -> statusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StatusDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusRepository.findAll(pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StatusDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Status> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Status.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusRepository.findAll(specification, pageable).getTotalElements();
            List<StatusDTO> listResult = statusRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StatusDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
