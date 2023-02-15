package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.UserInfoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserInfoSearchService implements IBaseSearchService<UserInfoDTO, UserInfo> {

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoSearchService(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(UserInfoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInfoRepository.findAll(pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(pageable).get().map(ele -> userInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInfoRepository.findAll(specification, pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(specification, pageable).get().map(ele -> userInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(UserInfoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInfoRepository.findAll(pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(pageable).get().map(ele -> userInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInfoRepository.findAll(specification, pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(specification, pageable).get().map(ele -> userInfoMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(UserInfoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInfoRepository.findAll(pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(UserInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInfoRepository.findAll(specification, pageable).getTotalElements();
            List<UserInfoDTO> listResult = userInfoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(UserInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
