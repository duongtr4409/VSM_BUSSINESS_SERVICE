package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.TagInExchange;
import com.vsm.business.repository.TagInExchangeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TagInExchangeDTO;
import com.vsm.business.service.mapper.TagInExchangeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagInExchangeSearchService implements IBaseSearchService<TagInExchangeDTO, TagInExchange> {

    private final TagInExchangeRepository tagInExchangeRepository;

    private final TagInExchangeMapper tagInExchangeMapper;

    public TagInExchangeSearchService(TagInExchangeRepository tagInExchangeRepository, TagInExchangeMapper tagInExchangeMapper) {
        this.tagInExchangeRepository = tagInExchangeRepository;
        this.tagInExchangeMapper = tagInExchangeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(TagInExchangeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tagInExchangeRepository.findAll(pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(pageable).get().map(ele -> tagInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TagInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TagInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tagInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(specification, pageable).get().map(ele -> tagInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(TagInExchangeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tagInExchangeRepository.findAll(pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(pageable).get().map(ele -> tagInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TagInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TagInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tagInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(specification, pageable).get().map(ele -> tagInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(TagInExchangeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = tagInExchangeRepository.findAll(pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(TagInExchangeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<TagInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, TagInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = tagInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<TagInExchangeDTO> listResult = tagInExchangeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(TagInExchangeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
