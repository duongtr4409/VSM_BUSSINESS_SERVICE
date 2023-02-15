package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.BusinessPartnerType;
import com.vsm.business.repository.BusinessPartnerTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.BusinessPartnerTypeDTO;
import com.vsm.business.service.mapper.BusinessPartnerTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusinessPartnerTypeSearchService implements IBaseSearchService<BusinessPartnerTypeDTO, BusinessPartnerType> {

    private final BusinessPartnerTypeRepository businessPartnerTypeRepository;

    private final BusinessPartnerTypeMapper businessPartnerTypeMapper;

    public BusinessPartnerTypeSearchService(BusinessPartnerTypeRepository businessPartnerTypeRepository, BusinessPartnerTypeMapper businessPartnerTypeMapper) {
        this.businessPartnerTypeRepository = businessPartnerTypeRepository;
        this.businessPartnerTypeMapper = businessPartnerTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(BusinessPartnerTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerTypeRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(pageable).get().map(ele -> businessPartnerTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartnerType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartnerType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerTypeRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(specification, pageable).get().map(ele -> businessPartnerTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(BusinessPartnerTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerTypeRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(pageable).get().map(ele -> businessPartnerTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartnerType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartnerType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerTypeRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(specification, pageable).get().map(ele -> businessPartnerTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(BusinessPartnerTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerTypeRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(BusinessPartnerTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartnerType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartnerType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerTypeRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerTypeDTO> listResult = businessPartnerTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(BusinessPartnerTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
