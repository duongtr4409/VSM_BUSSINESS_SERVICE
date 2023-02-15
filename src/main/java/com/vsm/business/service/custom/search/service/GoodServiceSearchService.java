package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.GoodService;
import com.vsm.business.repository.GoodServiceRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.GoodServiceDTO;
import com.vsm.business.service.mapper.GoodServiceMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodServiceSearchService implements IBaseSearchService<GoodServiceDTO, GoodService> {

    private final GoodServiceRepository goodServiceRepository;

    private final GoodServiceMapper goodServiceMapper;

    public GoodServiceSearchService(GoodServiceRepository goodServiceRepository, GoodServiceMapper goodServiceMapper) {
        this.goodServiceRepository = goodServiceRepository;
        this.goodServiceMapper = goodServiceMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(GoodServiceDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceRepository.findAll(pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(pageable).get().map(ele -> goodServiceMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodService> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodService.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(specification, pageable).get().map(ele -> goodServiceMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(GoodServiceDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceRepository.findAll(pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(pageable).get().map(ele -> goodServiceMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodService> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodService.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(specification, pageable).get().map(ele -> goodServiceMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(GoodServiceDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceRepository.findAll(pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(pageable).get().map(ele -> this.convertToDTO(GoodServiceDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodService> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodService.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceDTO> listResult = goodServiceRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(GoodServiceDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
