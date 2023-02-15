package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.RankInOrg;
import com.vsm.business.repository.RankInOrgRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RankInOrgDTO;
import com.vsm.business.service.mapper.RankInOrgMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankInOrgSearchService implements IBaseSearchService<RankInOrgDTO, RankInOrg> {

    private final RankInOrgRepository rankInOrgRepository;

    private final RankInOrgMapper rankInOrgMapper;

    public RankInOrgSearchService(RankInOrgRepository rankInOrgRepository, RankInOrgMapper rankInOrgMapper) {
        this.rankInOrgRepository = rankInOrgRepository;
        this.rankInOrgMapper = rankInOrgMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RankInOrgDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankInOrgRepository.findAll(pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(pageable).get().map(ele -> rankInOrgMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RankInOrg> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RankInOrg.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankInOrgRepository.findAll(specification, pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(specification, pageable).get().map(ele -> rankInOrgMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RankInOrgDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankInOrgRepository.findAll(pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(pageable).get().map(ele -> rankInOrgMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RankInOrg> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RankInOrg.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankInOrgRepository.findAll(specification, pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(specification, pageable).get().map(ele -> rankInOrgMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RankInOrgDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = rankInOrgRepository.findAll(pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RankInOrgDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<RankInOrg> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, RankInOrg.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = rankInOrgRepository.findAll(specification, pageable).getTotalElements();
            List<RankInOrgDTO> listResult = rankInOrgRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RankInOrgDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
