package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.UserInStep;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserInStepDTO;
import com.vsm.business.service.mapper.UserInStepMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserInStepSearchService implements IBaseSearchService<UserInStepDTO, UserInStep> {

    private final UserInStepRepository userInStepRepository;

    private final UserInStepMapper userInStepMapper;

    public UserInStepSearchService(UserInStepRepository userInStepRepository, UserInStepMapper userInStepMapper) {
        this.userInStepRepository = userInStepRepository;
        this.userInStepMapper = userInStepMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(UserInStepDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInStepRepository.findAll(pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(pageable).get().map(ele -> userInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInStepRepository.findAll(specification, pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(specification, pageable).get().map(ele -> userInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(UserInStepDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInStepRepository.findAll(pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(pageable).get().map(ele -> userInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInStepRepository.findAll(specification, pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(specification, pageable).get().map(ele -> userInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(UserInStepDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = userInStepRepository.findAll(pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(pageable).get().map(ele -> this.convertToDTO(UserInStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<UserInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, UserInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = userInStepRepository.findAll(specification, pageable).getTotalElements();
            List<UserInStepDTO> listResult = userInStepRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(UserInStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
