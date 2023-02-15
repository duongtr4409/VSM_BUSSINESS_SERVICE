package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Rank;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.mapper.RankMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankSearchService implements IBaseSearchService<RankDTO, Rank> {

    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    public RankSearchService(RankRepository rankRepository, RankMapper rankMapper) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RankDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankRepository.findAll(pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(pageable).get().map(ele -> rankMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Rank> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Rank.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankRepository.findAll(specification, pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(specification, pageable).get().map(ele -> rankMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RankDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankRepository.findAll(pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(pageable).get().map(ele -> rankMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Rank> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Rank.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankRepository.findAll(specification, pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(specification, pageable).get().map(ele -> rankMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RankDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankRepository.findAll(pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RankDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Rank> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Rank.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankRepository.findAll(specification, pageable).getTotalElements();
            List<RankDTO> listResult = rankRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RankDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
