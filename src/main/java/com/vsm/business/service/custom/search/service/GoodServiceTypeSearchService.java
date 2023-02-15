package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.GoodServiceType;
import com.vsm.business.repository.GoodServiceTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.GoodServiceTypeDTO;
import com.vsm.business.service.mapper.GoodServiceTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodServiceTypeSearchService implements IBaseSearchService<GoodServiceTypeDTO, GoodServiceType> {

    private final GoodServiceTypeRepository goodServiceTypeRepository;

    private final GoodServiceTypeMapper goodServiceTypeMapper;

    public GoodServiceTypeSearchService(GoodServiceTypeRepository goodServiceTypeRepository, GoodServiceTypeMapper goodServiceTypeMapper) {
        this.goodServiceTypeRepository = goodServiceTypeRepository;
        this.goodServiceTypeMapper = goodServiceTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(GoodServiceTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceTypeRepository.findAll(pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(pageable).get().map(ele -> goodServiceTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodServiceType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodServiceType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceTypeRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(specification, pageable).get().map(ele -> goodServiceTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(GoodServiceTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceTypeRepository.findAll(pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(pageable).get().map(ele -> goodServiceTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodServiceType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodServiceType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceTypeRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(specification, pageable).get().map(ele -> goodServiceTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(GoodServiceTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = goodServiceTypeRepository.findAll(pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(GoodServiceTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<GoodServiceType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, GoodServiceType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = goodServiceTypeRepository.findAll(specification, pageable).getTotalElements();
            List<GoodServiceTypeDTO> listResult = goodServiceTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(GoodServiceTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
