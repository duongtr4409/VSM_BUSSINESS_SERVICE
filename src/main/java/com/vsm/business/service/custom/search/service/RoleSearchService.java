package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Role;
import com.vsm.business.repository.RoleRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RoleDTO;
import com.vsm.business.service.mapper.RoleMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleSearchService implements IBaseSearchService<RoleDTO, Role> {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleSearchService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(RoleDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = roleRepository.findAll(pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(pageable).get().map(ele -> roleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Role> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Role.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = roleRepository.findAll(specification, pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(specification, pageable).get().map(ele -> roleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(RoleDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = roleRepository.findAll(pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(pageable).get().map(ele -> roleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Role> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Role.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = roleRepository.findAll(specification, pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(specification, pageable).get().map(ele -> roleMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(RoleDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = roleRepository.findAll(pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(pageable).get().map(ele -> this.convertToDTO(RoleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Role> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Role.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = roleRepository.findAll(specification, pageable).getTotalElements();
            List<RoleDTO> listResult = roleRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(RoleDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
