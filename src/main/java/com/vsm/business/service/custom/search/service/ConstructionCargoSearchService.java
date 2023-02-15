package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.CentralizedShopping;
import com.vsm.business.domain.ConstructionCargo;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.mapper.CentralizedShoppingMapper;
import com.vsm.business.service.mapper.ConstructionCargoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConstructionCargoSearchService implements IBaseSearchService<ConstructionCargoDTO, ConstructionCargo> {

    private final ConstructionCargoRepository constructionCargoRepository;

    private final ConstructionCargoMapper constructionCargoMapper;

    public ConstructionCargoSearchService(ConstructionCargoRepository constructionCargoRepository, ConstructionCargoMapper constructionCargoMapper) {
        this.constructionCargoRepository = constructionCargoRepository;
        this.constructionCargoMapper = constructionCargoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ConstructionCargoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = constructionCargoRepository.findAll(pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(pageable).get().map(ele -> constructionCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConstructionCargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConstructionCargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = constructionCargoRepository.findAll(specification, pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(specification, pageable).get().map(ele -> constructionCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ConstructionCargoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = constructionCargoRepository.findAll(pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(pageable).get().map(ele -> constructionCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConstructionCargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConstructionCargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = constructionCargoRepository.findAll(specification, pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(specification, pageable).get().map(ele -> constructionCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ConstructionCargoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = constructionCargoRepository.findAll(pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ConstructionCargoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConstructionCargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConstructionCargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = constructionCargoRepository.findAll(specification, pageable).getTotalElements();
            List<ConstructionCargoDTO> listResult = constructionCargoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ConstructionCargoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
