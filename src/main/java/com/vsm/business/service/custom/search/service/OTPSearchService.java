package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OTP;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OTPDTO;
import com.vsm.business.service.mapper.OTPMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OTPSearchService implements IBaseSearchService<OTPDTO, OTP> {

    private final OTPRepository oTPRepository;

    private final OTPMapper oTPMapper;

    public OTPSearchService(OTPRepository oTPRepository, OTPMapper oTPMapper) {
        this.oTPRepository = oTPRepository;
        this.oTPMapper = oTPMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OTPDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = oTPRepository.findAll(pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(pageable).get().map(ele -> oTPMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OTP> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OTP.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = oTPRepository.findAll(specification, pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(specification, pageable).get().map(ele -> oTPMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OTPDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = oTPRepository.findAll(pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(pageable).get().map(ele -> oTPMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OTP> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OTP.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = oTPRepository.findAll(specification, pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(specification, pageable).get().map(ele -> oTPMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OTPDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = oTPRepository.findAll(pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OTPDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OTP> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OTP.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = oTPRepository.findAll(specification, pageable).getTotalElements();
            List<OTPDTO> listResult = oTPRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OTPDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
