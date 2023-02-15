package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.FileType;
import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FileTypeDTO;
import com.vsm.business.service.mapper.FileTypeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileTypeSearchService implements IBaseSearchService<FileTypeDTO, FileType> {

    private final FileTypeRepository fileTypeRepository;

    private final FileTypeMapper fileTypeMapper;

    public FileTypeSearchService(FileTypeRepository fileTypeRepository, FileTypeMapper fileTypeMapper) {
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeMapper = fileTypeMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(FileTypeDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fileTypeRepository.findAll(pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(pageable).get().map(ele -> fileTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FileType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FileType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fileTypeRepository.findAll(specification, pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(specification, pageable).get().map(ele -> fileTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(FileTypeDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fileTypeRepository.findAll(pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(pageable).get().map(ele -> fileTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FileType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FileType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fileTypeRepository.findAll(specification, pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(specification, pageable).get().map(ele -> fileTypeMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(FileTypeDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = fileTypeRepository.findAll(pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(pageable).get().map(ele -> this.convertToDTO(FileTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<FileType> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, FileType.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = fileTypeRepository.findAll(specification, pageable).getTotalElements();
            List<FileTypeDTO> listResult = fileTypeRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(FileTypeDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
