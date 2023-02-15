package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.MallAndStall;
import com.vsm.business.repository.MallAndStallRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.MallAndStallDTO;
import com.vsm.business.service.mapper.MallAndStallMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MallAndStallSearchService implements IBaseSearchService<MallAndStallDTO, MallAndStall> {

    private final MallAndStallRepository mallAndStallRepository;

    private final MallAndStallMapper mallAndStallMapper;

    public MallAndStallSearchService(MallAndStallRepository mallAndStallRepository, MallAndStallMapper mallAndStallMapper) {
        this.mallAndStallRepository = mallAndStallRepository;
        this.mallAndStallMapper = mallAndStallMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(MallAndStallDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mallAndStallRepository.findAll(pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(pageable).get().map(ele -> mallAndStallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MallAndStall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MallAndStall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mallAndStallRepository.findAll(specification, pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(specification, pageable).get().map(ele -> mallAndStallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(MallAndStallDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mallAndStallRepository.findAll(pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(pageable).get().map(ele -> mallAndStallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MallAndStall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MallAndStall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mallAndStallRepository.findAll(specification, pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(specification, pageable).get().map(ele -> mallAndStallMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(MallAndStallDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mallAndStallRepository.findAll(pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(pageable).get().map(ele -> this.convertToDTO(MallAndStallDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MallAndStall> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MallAndStall.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mallAndStallRepository.findAll(specification, pageable).getTotalElements();
            List<MallAndStallDTO> listResult = mallAndStallRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(MallAndStallDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
