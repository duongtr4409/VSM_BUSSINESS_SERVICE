package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachmentFileSearchService implements IBaseSearchService<AttachmentFileDTO, AttachmentFile> {

    private final AttachmentFileRepository attachmentFileRepository;

    private final AttachmentFileMapper attachmentFileMapper;

    public AttachmentFileSearchService(AttachmentFileRepository attachmentFileRepository, AttachmentFileMapper attachmentFileMapper) {
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileMapper = attachmentFileMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(AttachmentFileDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentFileRepository.findAll(pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(pageable).get().map(ele -> attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentFile> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentFile.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentFileRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(specification, pageable).get().map(ele -> attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
//    @Cacheable("com.vsm.business.domain.AttachmentFile")
    public ISearchResponseDTO simpleQuerySearchWithParam(AttachmentFileDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentFileRepository.findAll(pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(pageable).get().map(ele -> attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentFile> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentFile.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentFileRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(specification, pageable).get().map(ele -> attachmentFileMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(AttachmentFileDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentFileRepository.findAll(pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(pageable).get().map(ele -> this.convertToDTO(AttachmentFileDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentFile> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentFile.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentFileRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentFileDTO> listResult = attachmentFileRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(AttachmentFileDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
