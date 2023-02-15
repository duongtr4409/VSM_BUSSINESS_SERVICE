package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Organization;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.mapper.OrganizationMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrganizationSearchService implements IBaseSearchService<OrganizationDTO, Organization> {

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationSearchService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OrganizationDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = organizationRepository.findAll(pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(pageable).get().map(ele -> organizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Organization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Organization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = organizationRepository.findAll(specification, pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(specification, pageable).get().map(ele -> organizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OrganizationDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = organizationRepository.findAll(pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(pageable).get().map(ele -> organizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Organization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Organization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = organizationRepository.findAll(specification, pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(specification, pageable).get().map(ele -> organizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OrganizationDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = organizationRepository.findAll(pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OrganizationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Organization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Organization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = organizationRepository.findAll(specification, pageable).getTotalElements();
            List<OrganizationDTO> listResult = organizationRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OrganizationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
