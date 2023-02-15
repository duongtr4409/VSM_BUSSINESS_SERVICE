package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OutOrganization;
import com.vsm.business.repository.OutOrganizationRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OutOrganizationDTO;
import com.vsm.business.service.mapper.OutOrganizationMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutOrganizationSearchService implements IBaseSearchService<OutOrganizationDTO, OutOrganization> {

    private final OutOrganizationRepository outOrganizationRepository;

    private final OutOrganizationMapper outOrganizationMapper;

    public OutOrganizationSearchService(OutOrganizationRepository outOrganizationRepository, OutOrganizationMapper outOrganizationMapper) {
        this.outOrganizationRepository = outOrganizationRepository;
        this.outOrganizationMapper = outOrganizationMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OutOrganizationDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = outOrganizationRepository.findAll(pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(pageable).get().map(ele -> outOrganizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OutOrganization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OutOrganization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = outOrganizationRepository.findAll(specification, pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(specification, pageable).get().map(ele -> outOrganizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OutOrganizationDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = outOrganizationRepository.findAll(pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(pageable).get().map(ele -> outOrganizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OutOrganization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OutOrganization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = outOrganizationRepository.findAll(specification, pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(specification, pageable).get().map(ele -> outOrganizationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OutOrganizationDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = outOrganizationRepository.findAll(pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OutOrganizationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OutOrganization> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OutOrganization.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = outOrganizationRepository.findAll(specification, pageable).getTotalElements();
            List<OutOrganizationDTO> listResult = outOrganizationRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OutOrganizationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
