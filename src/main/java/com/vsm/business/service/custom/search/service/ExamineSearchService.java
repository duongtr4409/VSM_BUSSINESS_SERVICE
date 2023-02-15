package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Examine;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ExamineDTO;
import com.vsm.business.service.mapper.ExamineMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamineSearchService implements IBaseSearchService<ExamineDTO, Examine> {

    private final ExamineRepository examineRepository;

    private final ExamineMapper examineMapper;

    public ExamineSearchService(ExamineRepository examineRepository, ExamineMapper examineMapper) {
        this.examineRepository = examineRepository;
        this.examineMapper = examineMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ExamineDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineRepository.findAll(pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(pageable).get().map(ele -> examineMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Examine> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Examine.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(specification, pageable).get().map(ele -> examineMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ExamineDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineRepository.findAll(pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(pageable).get().map(ele -> examineMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Examine> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Examine.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(specification, pageable).get().map(ele -> examineMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ExamineDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineRepository.findAll(pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ExamineDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Examine> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Examine.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineDTO> listResult = examineRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ExamineDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
