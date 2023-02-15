package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.UserGroup;
import com.vsm.business.repository.UserGroupRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserGroupDTO;
import com.vsm.business.service.mapper.UserGroupMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserGroupSearchService implements IBaseSearchService<UserGroupDTO, UserGroup> {

    private final UserGroupRepository userGroupRepository;

    private final UserGroupMapper userGroupMapper;

    public UserGroupSearchService(UserGroupRepository userGroupRepository, UserGroupMapper userGroupMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupMapper = userGroupMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(UserGroupDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userGroupRepository.findAll(pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(pageable).get().map(ele -> userGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userGroupRepository.findAll(specification, pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(specification, pageable).get().map(ele -> userGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(UserGroupDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userGroupRepository.findAll(pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(pageable).get().map(ele -> userGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userGroupRepository.findAll(specification, pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(specification, pageable).get().map(ele -> userGroupMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(UserGroupDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userGroupRepository.findAll(pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(pageable).get().map(ele -> this.convertToDTO(UserGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserGroup> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserGroup.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userGroupRepository.findAll(specification, pageable).getTotalElements();
            List<UserGroupDTO> listResult = userGroupRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(UserGroupDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
