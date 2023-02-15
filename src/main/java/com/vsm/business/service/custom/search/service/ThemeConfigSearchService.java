package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ThemeConfig;
import com.vsm.business.repository.ThemeConfigRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ThemeConfigDTO;
import com.vsm.business.service.mapper.ThemeConfigMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThemeConfigSearchService implements IBaseSearchService<ThemeConfigDTO, ThemeConfig> {

    private final ThemeConfigRepository themeConfigRepository;

    private final ThemeConfigMapper themeConfigMapper;

    public ThemeConfigSearchService(ThemeConfigRepository themeConfigRepository, ThemeConfigMapper themeConfigMapper) {
        this.themeConfigRepository = themeConfigRepository;
        this.themeConfigMapper = themeConfigMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ThemeConfigDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = themeConfigRepository.findAll(pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(pageable).get().map(ele -> themeConfigMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ThemeConfig> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ThemeConfig.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = themeConfigRepository.findAll(specification, pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(specification, pageable).get().map(ele -> themeConfigMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ThemeConfigDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = themeConfigRepository.findAll(pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(pageable).get().map(ele -> themeConfigMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ThemeConfig> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ThemeConfig.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = themeConfigRepository.findAll(specification, pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(specification, pageable).get().map(ele -> themeConfigMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ThemeConfigDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = themeConfigRepository.findAll(pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ThemeConfigDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ThemeConfig> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ThemeConfig.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = themeConfigRepository.findAll(specification, pageable).getTotalElements();
            List<ThemeConfigDTO> listResult = themeConfigRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ThemeConfigDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
