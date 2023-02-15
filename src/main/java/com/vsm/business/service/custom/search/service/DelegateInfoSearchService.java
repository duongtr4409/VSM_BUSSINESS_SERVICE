package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DelegateInfo;
import com.vsm.business.repository.DelegateInfoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DelegateInfoDTO;
import com.vsm.business.service.mapper.DelegateInfoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DelegateInfoSearchService implements IBaseSearchService<DelegateInfoDTO, DelegateInfo> {

    private final DelegateInfoRepository delegateInfoRepository;

    private final DelegateInfoMapper delegateInfoMapper;

    public DelegateInfoSearchService(DelegateInfoRepository delegateInfoRepository, DelegateInfoMapper delegateInfoMapper) {
        this.delegateInfoRepository = delegateInfoRepository;
        this.delegateInfoMapper = delegateInfoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DelegateInfoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateInfoRepository.findAll(pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(pageable).get().map(ele -> delegateInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateInfoRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(specification, pageable).get().map(ele -> delegateInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DelegateInfoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateInfoRepository.findAll(pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(pageable).get().map(ele -> delegateInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateInfoRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(specification, pageable).get().map(ele -> delegateInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DelegateInfoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateInfoRepository.findAll(pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DelegateInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateInfoRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateInfoDTO> listResult = delegateInfoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DelegateInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
