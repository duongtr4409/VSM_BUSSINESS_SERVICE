package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Tennant;
import com.vsm.business.repository.TennantRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TennantDTO;
import com.vsm.business.service.mapper.TennantMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TennantSearchService implements IBaseSearchService<TennantDTO, Tennant> {

    private final TennantRepository tennantRepository;

    private final TennantMapper tennantMapper;

    public TennantSearchService(TennantRepository tennantRepository, TennantMapper tennantMapper) {
        this.tennantRepository = tennantRepository;
        this.tennantMapper = tennantMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(TennantDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tennantRepository.findAll(pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(pageable).get().map(ele -> tennantMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Tennant> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Tennant.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tennantRepository.findAll(specification, pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(specification, pageable).get().map(ele -> tennantMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(TennantDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tennantRepository.findAll(pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(pageable).get().map(ele -> tennantMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Tennant> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Tennant.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tennantRepository.findAll(specification, pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(specification, pageable).get().map(ele -> tennantMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(TennantDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tennantRepository.findAll(pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(pageable).get().map(ele -> this.convertToDTO(TennantDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Tennant> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Tennant.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tennantRepository.findAll(specification, pageable).getTotalElements();
            List<TennantDTO> listResult = tennantRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(TennantDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
