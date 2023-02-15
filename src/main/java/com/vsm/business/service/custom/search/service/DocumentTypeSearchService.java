package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.DocumentType;
import com.vsm.business.repository.DocumentTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DocumentTypeDTO;
import com.vsm.business.service.mapper.DocumentTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentTypeSearchService implements IBaseSearchService<DocumentTypeDTO, DocumentType> {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeSearchService(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(DocumentTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = documentTypeRepository.findAll(pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(pageable).get().map(ele -> documentTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DocumentType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DocumentType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = documentTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(specification, pageable).get().map(ele -> documentTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(DocumentTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = documentTypeRepository.findAll(pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(pageable).get().map(ele -> documentTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DocumentType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DocumentType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = documentTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(specification, pageable).get().map(ele -> documentTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(DocumentTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = documentTypeRepository.findAll(pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(DocumentTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<DocumentType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, DocumentType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = documentTypeRepository.findAll(specification, pageable).getTotalElements();
            List<DocumentTypeDTO> listResult = documentTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(DocumentTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
