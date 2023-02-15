package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.SecurityLevel;
import com.vsm.business.repository.SecurityLevelRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.SecurityLevelDTO;
import com.vsm.business.service.mapper.SecurityLevelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SecurityLevelSearchService implements IBaseSearchService<SecurityLevelDTO, SecurityLevel> {

    private final SecurityLevelRepository securityLevelRepository;

    private final SecurityLevelMapper securityLevelMapper;

    public SecurityLevelSearchService(SecurityLevelRepository securityLevelRepository, SecurityLevelMapper securityLevelMapper) {
        this.securityLevelRepository = securityLevelRepository;
        this.securityLevelMapper = securityLevelMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(SecurityLevelDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = securityLevelRepository.findAll(pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(pageable).get().map(ele -> securityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SecurityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SecurityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = securityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(specification, pageable).get().map(ele -> securityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(SecurityLevelDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = securityLevelRepository.findAll(pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(pageable).get().map(ele -> securityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SecurityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SecurityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = securityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(specification, pageable).get().map(ele -> securityLevelMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(SecurityLevelDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = securityLevelRepository.findAll(pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(pageable).get().map(ele -> this.convertToDTO(SecurityLevelDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SecurityLevel> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SecurityLevel.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = securityLevelRepository.findAll(specification, pageable).getTotalElements();
            List<SecurityLevelDTO> listResult = securityLevelRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(SecurityLevelDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
