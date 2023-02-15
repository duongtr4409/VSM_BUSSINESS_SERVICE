package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ReqdataProcessHis;
import com.vsm.business.repository.ReqdataProcessHisRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;
import com.vsm.business.service.mapper.ReqdataProcessHisMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReqdataProcessHisSearchService implements IBaseSearchService<ReqdataProcessHisDTO, ReqdataProcessHis> {

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;

    private final ReqdataProcessHisMapper reqdataProcessHisMapper;

    public ReqdataProcessHisSearchService(ReqdataProcessHisRepository reqdataProcessHisRepository, ReqdataProcessHisMapper reqdataProcessHisMapper) {
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
        this.reqdataProcessHisMapper = reqdataProcessHisMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ReqdataProcessHisDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataProcessHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(pageable).get().map(ele -> reqdataProcessHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataProcessHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataProcessHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataProcessHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(specification, pageable).get().map(ele -> reqdataProcessHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ReqdataProcessHisDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataProcessHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(pageable).get().map(ele -> reqdataProcessHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataProcessHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataProcessHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataProcessHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(specification, pageable).get().map(ele -> reqdataProcessHisMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ReqdataProcessHisDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = reqdataProcessHisRepository.findAll(pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ReqdataProcessHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ReqdataProcessHis> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ReqdataProcessHis.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = reqdataProcessHisRepository.findAll(specification, pageable).getTotalElements();
            List<ReqdataProcessHisDTO> listResult = reqdataProcessHisRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ReqdataProcessHisDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
