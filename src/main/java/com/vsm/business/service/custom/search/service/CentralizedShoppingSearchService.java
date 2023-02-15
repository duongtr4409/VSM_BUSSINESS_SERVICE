package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.CentralizedShopping;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import com.vsm.business.service.mapper.CentralizedShoppingMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CentralizedShoppingSearchService implements IBaseSearchService<CentralizedShoppingDTO, CentralizedShopping> {

    private final CentralizedShoppingRepository centralizedShoppingRepository;

    private final CentralizedShoppingMapper centralizedShoppingMapper;

    public CentralizedShoppingSearchService(CentralizedShoppingRepository centralizedShoppingRepository, CentralizedShoppingMapper centralizedShoppingMapper) {
        this.centralizedShoppingRepository = centralizedShoppingRepository;
        this.centralizedShoppingMapper = centralizedShoppingMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(CentralizedShoppingDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = centralizedShoppingRepository.findAll(pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(pageable).get().map(ele -> centralizedShoppingMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CentralizedShopping> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CentralizedShopping.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = centralizedShoppingRepository.findAll(specification, pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(specification, pageable).get().map(ele -> centralizedShoppingMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(CentralizedShoppingDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = centralizedShoppingRepository.findAll(pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(pageable).get().map(ele -> centralizedShoppingMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CentralizedShopping> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CentralizedShopping.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = centralizedShoppingRepository.findAll(specification, pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(specification, pageable).get().map(ele -> centralizedShoppingMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(CentralizedShoppingDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = centralizedShoppingRepository.findAll(pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(pageable).get().map(ele -> this.convertToDTO(CentralizedShoppingDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CentralizedShopping> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CentralizedShopping.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = centralizedShoppingRepository.findAll(specification, pageable).getTotalElements();
            List<CentralizedShoppingDTO> listResult = centralizedShoppingRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(CentralizedShoppingDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
