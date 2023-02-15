package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ManageStampInfo;
import com.vsm.business.repository.ManageStampInfoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ManageStampInfoDTO;
import com.vsm.business.service.mapper.ManageStampInfoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ManageStampInfoSearchService implements IBaseSearchService<ManageStampInfoDTO, ManageStampInfo> {

    private final ManageStampInfoRepository manageStampInfoRepository;

    private final ManageStampInfoMapper manageStampInfoMapper;

    public ManageStampInfoSearchService(ManageStampInfoRepository manageStampInfoRepository, ManageStampInfoMapper manageStampInfoMapper) {
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.manageStampInfoMapper = manageStampInfoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ManageStampInfoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = manageStampInfoRepository.findAll(pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(pageable).get().map(ele -> manageStampInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ManageStampInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ManageStampInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = manageStampInfoRepository.findAll(specification, pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(specification, pageable).get().map(ele -> manageStampInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ManageStampInfoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = manageStampInfoRepository.findAll(pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(pageable).get().map(ele -> manageStampInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ManageStampInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ManageStampInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = manageStampInfoRepository.findAll(specification, pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(specification, pageable).get().map(ele -> manageStampInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ManageStampInfoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = manageStampInfoRepository.findAll(pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ManageStampInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ManageStampInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ManageStampInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = manageStampInfoRepository.findAll(specification, pageable).getTotalElements();
            List<ManageStampInfoDTO> listResult = manageStampInfoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ManageStampInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
