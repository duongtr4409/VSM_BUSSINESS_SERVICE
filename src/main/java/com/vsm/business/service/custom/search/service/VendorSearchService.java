package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Vendor;
import com.vsm.business.repository.VendorRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.VendorDTO;
import com.vsm.business.service.mapper.VendorMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VendorSearchService implements IBaseSearchService<VendorDTO, Vendor> {

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    public VendorSearchService(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(VendorDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = vendorRepository.findAll(pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(pageable).get().map(ele -> vendorMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Vendor> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Vendor.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = vendorRepository.findAll(specification, pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(specification, pageable).get().map(ele -> vendorMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(VendorDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = vendorRepository.findAll(pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(pageable).get().map(ele -> vendorMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Vendor> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Vendor.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = vendorRepository.findAll(specification, pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(specification, pageable).get().map(ele -> vendorMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(VendorDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = vendorRepository.findAll(pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(pageable).get().map(ele -> this.convertToDTO(VendorDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Vendor> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Vendor.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = vendorRepository.findAll(specification, pageable).getTotalElements();
            List<VendorDTO> listResult = vendorRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(VendorDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
