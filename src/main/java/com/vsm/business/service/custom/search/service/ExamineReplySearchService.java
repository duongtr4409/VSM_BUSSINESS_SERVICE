package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ExamineReply;
import com.vsm.business.repository.ExamineReplyRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ExamineReplyDTO;
import com.vsm.business.service.mapper.ExamineReplyMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamineReplySearchService implements IBaseSearchService<ExamineReplyDTO, ExamineReply> {

    private final ExamineReplyRepository examineReplyRepository;

    private final ExamineReplyMapper examineReplyMapper;

    public ExamineReplySearchService(ExamineReplyRepository examineReplyRepository, ExamineReplyMapper examineReplyMapper) {
        this.examineReplyRepository = examineReplyRepository;
        this.examineReplyMapper = examineReplyMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ExamineReplyDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineReplyRepository.findAll(pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(pageable).get().map(ele -> examineReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ExamineReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ExamineReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(specification, pageable).get().map(ele -> examineReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ExamineReplyDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineReplyRepository.findAll(pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(pageable).get().map(ele -> examineReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ExamineReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ExamineReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(specification, pageable).get().map(ele -> examineReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ExamineReplyDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = examineReplyRepository.findAll(pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ExamineReplyDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ExamineReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ExamineReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = examineReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ExamineReplyDTO> listResult = examineReplyRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ExamineReplyDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
