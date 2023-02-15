package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.AttachmentInStep;
import com.vsm.business.repository.AttachmentInStepRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentInStepDTO;
import com.vsm.business.service.mapper.AttachmentInStepMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachmentInStepSearchService implements IBaseSearchService<AttachmentInStepDTO, AttachmentInStep> {

    private final AttachmentInStepRepository attachmentInStepRepository;

    private final AttachmentInStepMapper attachmentInStepMapper;

    public AttachmentInStepSearchService(AttachmentInStepRepository attachmentInStepRepository, AttachmentInStepMapper attachmentInStepMapper) {
        this.attachmentInStepRepository = attachmentInStepRepository;
        this.attachmentInStepMapper = attachmentInStepMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(AttachmentInStepDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(pageable).get().map(ele -> attachmentInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(specification, pageable).get().map(ele -> attachmentInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(AttachmentInStepDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(pageable).get().map(ele -> attachmentInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(specification, pageable).get().map(ele -> attachmentInStepMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(AttachmentInStepDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentInStepRepository.findAll(pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(pageable).get().map(ele -> this.convertToDTO(AttachmentInStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentInStep> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentInStep.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentInStepRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentInStepDTO> listResult = attachmentInStepRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(AttachmentInStepDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
