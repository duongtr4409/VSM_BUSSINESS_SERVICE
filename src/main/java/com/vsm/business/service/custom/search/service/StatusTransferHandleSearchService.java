package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.StatusTransferHandle;
import com.vsm.business.repository.StatusTransferHandleRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StatusTransferHandleDTO;
import com.vsm.business.service.mapper.StatusTransferHandleMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatusTransferHandleSearchService implements IBaseSearchService<StatusTransferHandleDTO, StatusTransferHandle> {

    private final StatusTransferHandleRepository statusTransferHandleRepository;

    private final StatusTransferHandleMapper statusTransferHandleMapper;

    public StatusTransferHandleSearchService(StatusTransferHandleRepository statusTransferHandleRepository, StatusTransferHandleMapper statusTransferHandleMapper) {
        this.statusTransferHandleRepository = statusTransferHandleRepository;
        this.statusTransferHandleMapper = statusTransferHandleMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(StatusTransferHandleDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusTransferHandleRepository.findAll(pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(pageable).get().map(ele -> statusTransferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StatusTransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StatusTransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusTransferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(specification, pageable).get().map(ele -> statusTransferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(StatusTransferHandleDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusTransferHandleRepository.findAll(pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(pageable).get().map(ele -> statusTransferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StatusTransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StatusTransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusTransferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(specification, pageable).get().map(ele -> statusTransferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(StatusTransferHandleDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = statusTransferHandleRepository.findAll(pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(pageable).get().map(ele -> this.convertToDTO(StatusTransferHandleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<StatusTransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, StatusTransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = statusTransferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<StatusTransferHandleDTO> listResult = statusTransferHandleRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(StatusTransferHandleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
