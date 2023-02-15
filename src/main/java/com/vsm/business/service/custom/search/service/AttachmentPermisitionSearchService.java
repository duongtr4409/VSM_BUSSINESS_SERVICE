package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.AttachmentPermisition;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttachmentPermisitionSearchService implements IBaseSearchService<AttachmentPermisitionDTO, AttachmentPermisition> {

    private final AttachmentPermisitionRepository attachmentPermisitionRepository;

    private final AttachmentPermisitionMapper attachmentPermisitionMapper;

    public AttachmentPermisitionSearchService(AttachmentPermisitionRepository attachmentPermisitionRepository, AttachmentPermisitionMapper attachmentPermisitionMapper) {
        this.attachmentPermisitionRepository = attachmentPermisitionRepository;
        this.attachmentPermisitionMapper = attachmentPermisitionMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(AttachmentPermisitionDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentPermisitionRepository.findAll(pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(pageable).get().map(ele -> attachmentPermisitionMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentPermisition> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentPermisition.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentPermisitionRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(specification, pageable).get().map(ele -> attachmentPermisitionMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(AttachmentPermisitionDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentPermisitionRepository.findAll(pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(pageable).get().map(ele -> attachmentPermisitionMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentPermisition> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentPermisition.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentPermisitionRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(specification, pageable).get().map(ele -> attachmentPermisitionMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(AttachmentPermisitionDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = attachmentPermisitionRepository.findAll(pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(pageable).get().map(ele -> this.convertToDTO(AttachmentPermisitionDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<AttachmentPermisition> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, AttachmentPermisition.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = attachmentPermisitionRepository.findAll(specification, pageable).getTotalElements();
            List<AttachmentPermisitionDTO> listResult = attachmentPermisitionRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(AttachmentPermisitionDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
