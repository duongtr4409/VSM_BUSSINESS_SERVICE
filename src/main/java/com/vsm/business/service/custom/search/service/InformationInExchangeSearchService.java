package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.InformationInExchange;
import com.vsm.business.repository.InformationInExchangeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.InformationInExchangeDTO;
import com.vsm.business.service.mapper.InformationInExchangeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InformationInExchangeSearchService implements IBaseSearchService<InformationInExchangeDTO, InformationInExchange> {

    private final InformationInExchangeRepository informationInExchangeRepository;

    private final InformationInExchangeMapper informationInExchangeMapper;

    public InformationInExchangeSearchService(InformationInExchangeRepository informationInExchangeRepository, InformationInExchangeMapper informationInExchangeMapper) {
        this.informationInExchangeRepository = informationInExchangeRepository;
        this.informationInExchangeMapper = informationInExchangeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(InformationInExchangeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = informationInExchangeRepository.findAll(pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(pageable).get().map(ele -> informationInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<InformationInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, InformationInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = informationInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(specification, pageable).get().map(ele -> informationInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(InformationInExchangeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = informationInExchangeRepository.findAll(pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(pageable).get().map(ele -> informationInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<InformationInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, InformationInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = informationInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(specification, pageable).get().map(ele -> informationInExchangeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(InformationInExchangeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = informationInExchangeRepository.findAll(pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(InformationInExchangeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<InformationInExchange> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, InformationInExchange.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = informationInExchangeRepository.findAll(specification, pageable).getTotalElements();
            List<InformationInExchangeDTO> listResult = informationInExchangeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(InformationInExchangeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
