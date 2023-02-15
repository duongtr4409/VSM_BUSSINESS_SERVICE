package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OfficialDispatchStatus;
import com.vsm.business.repository.OfficialDispatchStatusRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchStatusDTO;
import com.vsm.business.service.mapper.OfficialDispatchStatusMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchStatusSearchService implements IBaseSearchService<OfficialDispatchStatusDTO, OfficialDispatchStatus> {

    private final OfficialDispatchStatusRepository officialDispatchStatusRepository;

    private final OfficialDispatchStatusMapper officialDispatchStatusMapper;

    public OfficialDispatchStatusSearchService(OfficialDispatchStatusRepository officialDispatchStatusRepository, OfficialDispatchStatusMapper officialDispatchStatusMapper) {
        this.officialDispatchStatusRepository = officialDispatchStatusRepository;
        this.officialDispatchStatusMapper = officialDispatchStatusMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OfficialDispatchStatusDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchStatusRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(pageable).get().map(ele -> officialDispatchStatusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchStatus> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchStatus.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchStatusRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(specification, pageable).get().map(ele -> officialDispatchStatusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OfficialDispatchStatusDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchStatusRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(pageable).get().map(ele -> officialDispatchStatusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchStatus> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchStatus.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchStatusRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(specification, pageable).get().map(ele -> officialDispatchStatusMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OfficialDispatchStatusDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchStatusRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OfficialDispatchStatusDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchStatus> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchStatus.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchStatusRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchStatusDTO> listResult = officialDispatchStatusRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OfficialDispatchStatusDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
