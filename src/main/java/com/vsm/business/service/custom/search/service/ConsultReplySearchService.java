package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.ConsultReply;
import com.vsm.business.repository.ConsultReplyRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConsultReplyDTO;
import com.vsm.business.service.mapper.ConsultReplyMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsultReplySearchService implements IBaseSearchService<ConsultReplyDTO, ConsultReply> {

    private final ConsultReplyRepository consultReplyRepository;

    private final ConsultReplyMapper consultReplyMapper;

    public ConsultReplySearchService(ConsultReplyRepository consultReplyRepository, ConsultReplyMapper consultReplyMapper) {
        this.consultReplyRepository = consultReplyRepository;
        this.consultReplyMapper = consultReplyMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ConsultReplyDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultReplyRepository.findAll(pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(pageable).get().map(ele -> consultReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConsultReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConsultReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(specification, pageable).get().map(ele -> consultReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ConsultReplyDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultReplyRepository.findAll(pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(pageable).get().map(ele -> consultReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConsultReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConsultReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(specification, pageable).get().map(ele -> consultReplyMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ConsultReplyDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = consultReplyRepository.findAll(pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ConsultReplyDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ConsultReply> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ConsultReply.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = consultReplyRepository.findAll(specification, pageable).getTotalElements();
            List<ConsultReplyDTO> listResult = consultReplyRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ConsultReplyDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
