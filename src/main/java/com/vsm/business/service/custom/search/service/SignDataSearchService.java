package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.SignData;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.SignDataDTO;
import com.vsm.business.service.mapper.SignDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SignDataSearchService implements IBaseSearchService<SignDataDTO, SignData> {

    private final SignDataRepository signDataRepository;

    private final SignDataMapper signDataMapper;

    public SignDataSearchService(SignDataRepository signDataRepository, SignDataMapper signDataMapper) {
        this.signDataRepository = signDataRepository;
        this.signDataMapper = signDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(SignDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signDataRepository.findAll(pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(pageable).get().map(ele -> signDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signDataRepository.findAll(specification, pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(specification, pageable).get().map(ele -> signDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(SignDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signDataRepository.findAll(pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(pageable).get().map(ele -> signDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signDataRepository.findAll(specification, pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(specification, pageable).get().map(ele -> signDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(SignDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signDataRepository.findAll(pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(SignDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signDataRepository.findAll(specification, pageable).getTotalElements();
            List<SignDataDTO> listResult = signDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(SignDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
