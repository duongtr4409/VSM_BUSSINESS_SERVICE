package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ReqdataChangeHis;
import com.vsm.business.repository.ReqdataChangeHisRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;
import com.vsm.business.service.mapper.ReqdataChangeHisMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReqdataChangeHisSearchService implements IBaseSearchService<ReqdataChangeHisDTO, ReqdataChangeHis> {

    private final ReqdataChangeHisRepository reqdataChangeHisRepository;

    private final ReqdataChangeHisMapper reqdataChangeHisMapper;

    public ReqdataChangeHisSearchService(ReqdataChangeHisRepository reqdataChangeHisRepository, ReqdataChangeHisMapper reqdataChangeHisMapper) {
        this.reqdataChangeHisRepository = reqdataChangeHisRepository;
        this.reqdataChangeHisMapper = reqdataChangeHisMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ReqdataChangeHisDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataChangeHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(pageable).get().map(ele -> reqdataChangeHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataChangeHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataChangeHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataChangeHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(specification, pageable).get().map(ele -> reqdataChangeHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ReqdataChangeHisDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataChangeHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(pageable).get().map(ele -> reqdataChangeHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataChangeHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataChangeHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataChangeHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(specification, pageable).get().map(ele -> reqdataChangeHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ReqdataChangeHisDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataChangeHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ReqdataChangeHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataChangeHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataChangeHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataChangeHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataChangeHisDTO> listResult = reqdataChangeHisRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ReqdataChangeHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
