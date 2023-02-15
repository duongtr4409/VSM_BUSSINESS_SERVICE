package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OfficialDispatch;
import com.vsm.business.repository.OfficialDispatchRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchDTO;
import com.vsm.business.service.mapper.OfficialDispatchMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchSearchService implements IBaseSearchService<OfficialDispatchDTO, OfficialDispatch> {

    private final OfficialDispatchRepository officialDispatchRepository;

    private final OfficialDispatchMapper officialDispatchMapper;

    public OfficialDispatchSearchService(OfficialDispatchRepository officialDispatchRepository, OfficialDispatchMapper officialDispatchMapper) {
        this.officialDispatchRepository = officialDispatchRepository;
        this.officialDispatchMapper = officialDispatchMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OfficialDispatchDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(pageable).get().map(ele -> officialDispatchMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatch> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatch.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(specification, pageable).get().map(ele -> officialDispatchMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OfficialDispatchDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(pageable).get().map(ele -> officialDispatchMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatch> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatch.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(specification, pageable).get().map(ele -> officialDispatchMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OfficialDispatchDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OfficialDispatchDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatch> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatch.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchDTO> listResult = officialDispatchRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OfficialDispatchDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
