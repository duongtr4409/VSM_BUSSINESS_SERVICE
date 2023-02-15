package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ConstructionCargo;
import com.vsm.business.domain.MECargo;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.repository.MECargoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.dto.MECargoDTO;
import com.vsm.business.service.mapper.ConstructionCargoMapper;
import com.vsm.business.service.mapper.MECargoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MECargoSearchService implements IBaseSearchService<MECargoDTO, MECargo> {

    private final MECargoRepository meCargoRepository;

    private final MECargoMapper meCargoMapper;

    public MECargoSearchService(MECargoRepository meCargoRepository, MECargoMapper meCargoMapper) {
        this.meCargoRepository = meCargoRepository;
        this.meCargoMapper = meCargoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(MECargoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = meCargoRepository.findAll(pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(pageable).get().map(ele -> meCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MECargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MECargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = meCargoRepository.findAll(specification, pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(specification, pageable).get().map(ele -> meCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(MECargoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = meCargoRepository.findAll(pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(pageable).get().map(ele -> meCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MECargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MECargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = meCargoRepository.findAll(specification, pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(specification, pageable).get().map(ele -> meCargoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(MECargoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = meCargoRepository.findAll(pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(MECargoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MECargo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MECargo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = meCargoRepository.findAll(specification, pageable).getTotalElements();
            List<MECargoDTO> listResult = meCargoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(MECargoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
