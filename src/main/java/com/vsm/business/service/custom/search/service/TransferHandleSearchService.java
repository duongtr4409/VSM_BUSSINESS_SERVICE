package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.TransferHandle;
import com.vsm.business.repository.TransferHandleRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TransferHandleDTO;
import com.vsm.business.service.mapper.TransferHandleMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransferHandleSearchService implements IBaseSearchService<TransferHandleDTO, TransferHandle> {

    private final TransferHandleRepository transferHandleRepository;

    private final TransferHandleMapper transferHandleMapper;

    public TransferHandleSearchService(TransferHandleRepository transferHandleRepository, TransferHandleMapper transferHandleMapper) {
        this.transferHandleRepository = transferHandleRepository;
        this.transferHandleMapper = transferHandleMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(TransferHandleDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = transferHandleRepository.findAll(pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(pageable).get().map(ele -> transferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = transferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(specification, pageable).get().map(ele -> transferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(TransferHandleDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = transferHandleRepository.findAll(pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(pageable).get().map(ele -> transferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = transferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(specification, pageable).get().map(ele -> transferHandleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(TransferHandleDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = transferHandleRepository.findAll(pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(pageable).get().map(ele -> this.convertToDTO(TransferHandleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TransferHandle> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TransferHandle.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = transferHandleRepository.findAll(specification, pageable).getTotalElements();
            List<TransferHandleDTO> listResult = transferHandleRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(TransferHandleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
