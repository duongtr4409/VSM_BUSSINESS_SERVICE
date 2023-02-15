package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.OfficialDispatchHis;
import com.vsm.business.repository.OfficialDispatchHisRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;
import com.vsm.business.service.mapper.OfficialDispatchHisMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchHisSearchService implements IBaseSearchService<OfficialDispatchHisDTO, OfficialDispatchHis> {

    private final OfficialDispatchHisRepository officialDispatchHisRepository;

    private final OfficialDispatchHisMapper officialDispatchHisMapper;

    public OfficialDispatchHisSearchService(OfficialDispatchHisRepository officialDispatchHisRepository, OfficialDispatchHisMapper officialDispatchHisMapper) {
        this.officialDispatchHisRepository = officialDispatchHisRepository;
        this.officialDispatchHisMapper = officialDispatchHisMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(OfficialDispatchHisDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchHisRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(pageable).get().map(ele -> officialDispatchHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchHisRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(specification, pageable).get().map(ele -> officialDispatchHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(OfficialDispatchHisDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchHisRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(pageable).get().map(ele -> officialDispatchHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchHisRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(specification, pageable).get().map(ele -> officialDispatchHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(OfficialDispatchHisDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = officialDispatchHisRepository.findAll(pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(pageable).get().map(ele -> this.convertToDTO(OfficialDispatchHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<OfficialDispatchHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, OfficialDispatchHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = officialDispatchHisRepository.findAll(specification, pageable).getTotalElements();
            List<OfficialDispatchHisDTO> listResult = officialDispatchHisRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(OfficialDispatchHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
