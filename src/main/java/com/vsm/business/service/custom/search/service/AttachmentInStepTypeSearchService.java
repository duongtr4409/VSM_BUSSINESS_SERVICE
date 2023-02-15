package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.AttachmentInStepType;
import com.vsm.business.repository.AttachmentInStepTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
import com.vsm.business.service.mapper.AttachmentInStepTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachmentInStepTypeSearchService implements IBaseSearchService<AttachmentInStepTypeDTO, AttachmentInStepType> {

    private final AttachmentInStepTypeRepository attachmentInStepTypeRepository;

    private final AttachmentInStepTypeMapper attachmentInStepTypeMapper;

    public AttachmentInStepTypeSearchService(AttachmentInStepTypeRepository attachmentInStepTypeRepository, AttachmentInStepTypeMapper attachmentInStepTypeMapper) {
        this.attachmentInStepTypeRepository = attachmentInStepTypeRepository;
        this.attachmentInStepTypeMapper = attachmentInStepTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(AttachmentInStepTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepTypeRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(pageable).get().map(ele -> attachmentInStepTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStepType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStepType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepTypeRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(specification, pageable).get().map(ele -> attachmentInStepTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(AttachmentInStepTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepTypeRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(pageable).get().map(ele -> attachmentInStepTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStepType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStepType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepTypeRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(specification, pageable).get().map(ele -> attachmentInStepTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(AttachmentInStepTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepTypeRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(AttachmentInStepTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStepType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStepType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepTypeRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepTypeDTO> listResult = attachmentInStepTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(AttachmentInStepTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
