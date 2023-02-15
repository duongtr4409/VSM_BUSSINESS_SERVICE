package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ChangeFileHistory;
import com.vsm.business.repository.ChangeFileHistoryRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ChangeFileHistoryDTO;
import com.vsm.business.service.mapper.ChangeFileHistoryMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChangeFileHistorySearchService implements IBaseSearchService<ChangeFileHistoryDTO, ChangeFileHistory> {

    private final ChangeFileHistoryRepository changeFileHistoryRepository;

    private final ChangeFileHistoryMapper changeFileHistoryMapper;

    public ChangeFileHistorySearchService(ChangeFileHistoryRepository changeFileHistoryRepository, ChangeFileHistoryMapper changeFileHistoryMapper) {
        this.changeFileHistoryRepository = changeFileHistoryRepository;
        this.changeFileHistoryMapper = changeFileHistoryMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ChangeFileHistoryDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = changeFileHistoryRepository.findAll(pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(pageable).get().map(ele -> changeFileHistoryMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ChangeFileHistory> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ChangeFileHistory.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = changeFileHistoryRepository.findAll(specification, pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(specification, pageable).get().map(ele -> changeFileHistoryMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ChangeFileHistoryDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = changeFileHistoryRepository.findAll(pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(pageable).get().map(ele -> changeFileHistoryMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ChangeFileHistory> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ChangeFileHistory.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = changeFileHistoryRepository.findAll(specification, pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(specification, pageable).get().map(ele -> changeFileHistoryMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ChangeFileHistoryDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = changeFileHistoryRepository.findAll(pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ChangeFileHistoryDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ChangeFileHistory> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ChangeFileHistory.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = changeFileHistoryRepository.findAll(specification, pageable).getTotalElements();
            List<ChangeFileHistoryDTO> listResult = changeFileHistoryRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ChangeFileHistoryDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
