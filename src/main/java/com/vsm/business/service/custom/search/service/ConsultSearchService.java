package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Consult;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConsultDTO;
import com.vsm.business.service.mapper.ConsultMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsultSearchService implements IBaseSearchService<ConsultDTO, Consult> {

    private final ConsultRepository consultRepository;

    private final ConsultMapper consultMapper;

    public ConsultSearchService(ConsultRepository consultRepository, ConsultMapper consultMapper) {
        this.consultRepository = consultRepository;
        this.consultMapper = consultMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ConsultDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultRepository.findAll(pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(pageable).get().map(ele -> consultMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Consult> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Consult.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(specification, pageable).get().map(ele -> consultMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ConsultDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultRepository.findAll(pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(pageable).get().map(ele -> consultMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Consult> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Consult.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(specification, pageable).get().map(ele -> consultMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ConsultDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultRepository.findAll(pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ConsultDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<Consult> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, Consult.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultDTO> listResult = consultRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ConsultDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
