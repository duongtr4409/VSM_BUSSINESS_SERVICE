package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.repository.BusinessPartnerRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.BusinessPartnerDTO;
import com.vsm.business.service.mapper.BusinessPartnerMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusinessPartnerSearchService implements IBaseSearchService<BusinessPartnerDTO, BusinessPartner> {

    private final BusinessPartnerRepository businessPartnerRepository;

    private final BusinessPartnerMapper businessPartnerMapper;

    public BusinessPartnerSearchService(BusinessPartnerRepository businessPartnerRepository, BusinessPartnerMapper businessPartnerMapper) {
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnerMapper = businessPartnerMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(BusinessPartnerDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(pageable).get().map(ele -> businessPartnerMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartner> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartner.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(specification, pageable).get().map(ele -> businessPartnerMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(BusinessPartnerDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(pageable).get().map(ele -> businessPartnerMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartner> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartner.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(specification, pageable).get().map(ele -> businessPartnerMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(BusinessPartnerDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = businessPartnerRepository.findAll(pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(pageable).get().map(ele -> this.convertToDTO(BusinessPartnerDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<BusinessPartner> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, BusinessPartner.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = businessPartnerRepository.findAll(specification, pageable).getTotalElements();
            List<BusinessPartnerDTO> listResult = businessPartnerRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(BusinessPartnerDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
