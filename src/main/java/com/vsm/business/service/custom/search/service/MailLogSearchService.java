package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.MailLog;
import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.MailLogDTO;
import com.vsm.business.service.mapper.MailLogMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MailLogSearchService implements IBaseSearchService<MailLogDTO, MailLog> {

    private final MailLogRepository mailLogRepository;

    private final MailLogMapper mailLogMapper;

    public MailLogSearchService(MailLogRepository mailLogRepository, MailLogMapper mailLogMapper) {
        this.mailLogRepository = mailLogRepository;
        this.mailLogMapper = mailLogMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(MailLogDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailLogRepository.findAll(pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(pageable).get().map(ele -> mailLogMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailLog> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailLog.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailLogRepository.findAll(specification, pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(specification, pageable).get().map(ele -> mailLogMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(MailLogDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailLogRepository.findAll(pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(pageable).get().map(ele -> mailLogMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailLog> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailLog.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailLogRepository.findAll(specification, pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(specification, pageable).get().map(ele -> mailLogMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(MailLogDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailLogRepository.findAll(pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(pageable).get().map(ele -> this.convertToDTO(MailLogDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailLog> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailLog.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailLogRepository.findAll(specification, pageable).getTotalElements();
            List<MailLogDTO> listResult = mailLogRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(MailLogDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
