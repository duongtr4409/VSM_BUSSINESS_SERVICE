package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ProcessDataDTO;
import com.vsm.business.service.mapper.ProcessDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessDataSearchService implements IBaseSearchService<ProcessDataDTO, ProcessData> {

    private final ProcessDataRepository processDataRepository;

    private final ProcessDataMapper processDataMapper;

    public ProcessDataSearchService(ProcessDataRepository processDataRepository, ProcessDataMapper processDataMapper) {
        this.processDataRepository = processDataRepository;
        this.processDataMapper = processDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ProcessDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processDataRepository.findAll(pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(pageable).get().map(ele -> processDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processDataRepository.findAll(specification, pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(specification, pageable).get().map(ele -> processDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ProcessDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processDataRepository.findAll(pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(pageable).get().map(ele -> processDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processDataRepository.findAll(specification, pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(specification, pageable).get().map(ele -> processDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ProcessDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processDataRepository.findAll(pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ProcessDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processDataRepository.findAll(specification, pageable).getTotalElements();
            List<ProcessDataDTO> listResult = processDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ProcessDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
