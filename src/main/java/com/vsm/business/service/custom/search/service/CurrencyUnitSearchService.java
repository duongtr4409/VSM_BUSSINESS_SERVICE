package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.CurrencyUnit;
import com.vsm.business.repository.CurrencyUnitRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CurrencyUnitDTO;
import com.vsm.business.service.mapper.CurrencyUnitMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyUnitSearchService implements IBaseSearchService<CurrencyUnitDTO, CurrencyUnit> {

    private final CurrencyUnitRepository currencyUnitRepository;

    private final CurrencyUnitMapper currencyUnitMapper;

    public CurrencyUnitSearchService(CurrencyUnitRepository currencyUnitRepository, CurrencyUnitMapper currencyUnitMapper) {
        this.currencyUnitRepository = currencyUnitRepository;
        this.currencyUnitMapper = currencyUnitMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(CurrencyUnitDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = currencyUnitRepository.findAll(pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(pageable).get().map(ele -> currencyUnitMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CurrencyUnit> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CurrencyUnit.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = currencyUnitRepository.findAll(specification, pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(specification, pageable).get().map(ele -> currencyUnitMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(CurrencyUnitDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = currencyUnitRepository.findAll(pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(pageable).get().map(ele -> currencyUnitMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CurrencyUnit> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CurrencyUnit.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = currencyUnitRepository.findAll(specification, pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(specification, pageable).get().map(ele -> currencyUnitMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(CurrencyUnitDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = currencyUnitRepository.findAll(pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(pageable).get().map(ele -> this.convertToDTO(CurrencyUnitDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CurrencyUnit> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CurrencyUnit.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = currencyUnitRepository.findAll(specification, pageable).getTotalElements();
            List<CurrencyUnitDTO> listResult = currencyUnitRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(CurrencyUnitDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
