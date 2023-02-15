package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.PriceInfo;
import com.vsm.business.repository.PriceInfoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.PriceInfoDTO;
import com.vsm.business.service.mapper.PriceInfoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceInfoSearchService implements IBaseSearchService<PriceInfoDTO, PriceInfo> {

    private final PriceInfoRepository priceInfoRepository;

    private final PriceInfoMapper priceInfoMapper;

    public PriceInfoSearchService(PriceInfoRepository priceInfoRepository, PriceInfoMapper priceInfoMapper) {
        this.priceInfoRepository = priceInfoRepository;
        this.priceInfoMapper = priceInfoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(PriceInfoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priceInfoRepository.findAll(pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(pageable).get().map(ele -> priceInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriceInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriceInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priceInfoRepository.findAll(specification, pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(specification, pageable).get().map(ele -> priceInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(PriceInfoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priceInfoRepository.findAll(pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(pageable).get().map(ele -> priceInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriceInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriceInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priceInfoRepository.findAll(specification, pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(specification, pageable).get().map(ele -> priceInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(PriceInfoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = priceInfoRepository.findAll(pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(PriceInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<PriceInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, PriceInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = priceInfoRepository.findAll(specification, pageable).getTotalElements();
            List<PriceInfoDTO> listResult = priceInfoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(PriceInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
