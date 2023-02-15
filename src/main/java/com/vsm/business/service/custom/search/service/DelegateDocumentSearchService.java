package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DelegateDocument;
import com.vsm.business.repository.DelegateDocumentRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DelegateDocumentDTO;
import com.vsm.business.service.mapper.DelegateDocumentMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DelegateDocumentSearchService implements IBaseSearchService<DelegateDocumentDTO, DelegateDocument> {

    private final DelegateDocumentRepository delegateDocumentRepository;

    private final DelegateDocumentMapper delegateDocumentMapper;

    public DelegateDocumentSearchService(DelegateDocumentRepository delegateDocumentRepository, DelegateDocumentMapper delegateDocumentMapper) {
        this.delegateDocumentRepository = delegateDocumentRepository;
        this.delegateDocumentMapper = delegateDocumentMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DelegateDocumentDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateDocumentRepository.findAll(pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(pageable).get().map(ele -> delegateDocumentMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateDocument> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateDocument.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateDocumentRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(specification, pageable).get().map(ele -> delegateDocumentMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DelegateDocumentDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateDocumentRepository.findAll(pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(pageable).get().map(ele -> delegateDocumentMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateDocument> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateDocument.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateDocumentRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(specification, pageable).get().map(ele -> delegateDocumentMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DelegateDocumentDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = delegateDocumentRepository.findAll(pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DelegateDocumentDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DelegateDocument> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DelegateDocument.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = delegateDocumentRepository.findAll(specification, pageable).getTotalElements();
            List<DelegateDocumentDTO> listResult = delegateDocumentRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DelegateDocumentDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
