package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OfficialDispatchType;
import com.vsm.business.repository.OfficialDispatchTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchTypeDTO;
import com.vsm.business.service.mapper.OfficialDispatchTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchTypeSearchService implements IBaseSearchService<OfficialDispatchTypeDTO, OfficialDispatchType> {

    private final OfficialDispatchTypeRepository officialDispatchTypeRepository;

    private final OfficialDispatchTypeMapper officialDispatchTypeMapper;

    public OfficialDispatchTypeSearchService(OfficialDispatchTypeRepository officialDispatchTypeRepository, OfficialDispatchTypeMapper officialDispatchTypeMapper) {
        this.officialDispatchTypeRepository = officialDispatchTypeRepository;
        this.officialDispatchTypeMapper = officialDispatchTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OfficialDispatchTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchTypeRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(pageable).get().map(ele -> officialDispatchTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchTypeRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(specification, pageable).get().map(ele -> officialDispatchTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OfficialDispatchTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchTypeRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(pageable).get().map(ele -> officialDispatchTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchTypeRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(specification, pageable).get().map(ele -> officialDispatchTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OfficialDispatchTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchTypeRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OfficialDispatchTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchTypeRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchTypeDTO> listResult = officialDispatchTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OfficialDispatchTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
