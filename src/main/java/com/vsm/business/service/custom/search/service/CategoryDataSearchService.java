package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.CategoryData;
import com.vsm.business.repository.CategoryDataRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CategoryDataDTO;
import com.vsm.business.service.mapper.CategoryDataMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryDataSearchService implements IBaseSearchService<CategoryDataDTO, CategoryData> {

    private final CategoryDataRepository categoryDataRepository;

    private final CategoryDataMapper categoryDataMapper;

    public CategoryDataSearchService(CategoryDataRepository categoryDataRepository, CategoryDataMapper categoryDataMapper) {
        this.categoryDataRepository = categoryDataRepository;
        this.categoryDataMapper = categoryDataMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(CategoryDataDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryDataRepository.findAll(pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(pageable).get().map(ele -> categoryDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryDataRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(specification, pageable).get().map(ele -> categoryDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(CategoryDataDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryDataRepository.findAll(pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(pageable).get().map(ele -> categoryDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryDataRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(specification, pageable).get().map(ele -> categoryDataMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(CategoryDataDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryDataRepository.findAll(pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(pageable).get().map(ele -> this.convertToDTO(CategoryDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryData> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryData.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryDataRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryDataDTO> listResult = categoryDataRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(CategoryDataDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
