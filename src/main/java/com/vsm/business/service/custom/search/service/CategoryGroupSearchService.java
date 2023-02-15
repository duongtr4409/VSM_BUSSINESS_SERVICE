package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CategoryGroupDTO;
import com.vsm.business.service.mapper.CategoryGroupMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryGroupSearchService implements IBaseSearchService<CategoryGroupDTO, CategoryGroup> {

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupMapper categoryGroupMapper;

    public CategoryGroupSearchService(CategoryGroupRepository categoryGroupRepository, CategoryGroupMapper categoryGroupMapper) {
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryGroupMapper = categoryGroupMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(CategoryGroupDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryGroupRepository.findAll(pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(pageable).get().map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryGroupRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(specification, pageable).get().map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(CategoryGroupDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryGroupRepository.findAll(pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(pageable).get().map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryGroupRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(specification, pageable).get().map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(CategoryGroupDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = categoryGroupRepository.findAll(pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(pageable).get().map(ele -> this.convertToDTO(CategoryGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<CategoryGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, CategoryGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = categoryGroupRepository.findAll(specification, pageable).getTotalElements();
            List<CategoryGroupDTO> listResult = categoryGroupRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(CategoryGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
