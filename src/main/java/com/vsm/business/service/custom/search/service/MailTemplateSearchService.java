package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.MailTemplate;
import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.MailTemplateDTO;
import com.vsm.business.service.mapper.MailTemplateMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MailTemplateSearchService implements IBaseSearchService<MailTemplateDTO, MailTemplate> {

    private final MailTemplateRepository mailTemplateRepository;

    private final MailTemplateMapper mailTemplateMapper;

    public MailTemplateSearchService(MailTemplateRepository mailTemplateRepository, MailTemplateMapper mailTemplateMapper) {
        this.mailTemplateRepository = mailTemplateRepository;
        this.mailTemplateMapper = mailTemplateMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(MailTemplateDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailTemplateRepository.findAll(pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(pageable).get().map(ele -> mailTemplateMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailTemplate> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailTemplate.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailTemplateRepository.findAll(specification, pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(specification, pageable).get().map(ele -> mailTemplateMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(MailTemplateDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailTemplateRepository.findAll(pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(pageable).get().map(ele -> mailTemplateMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailTemplate> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailTemplate.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailTemplateRepository.findAll(specification, pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(specification, pageable).get().map(ele -> mailTemplateMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(MailTemplateDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = mailTemplateRepository.findAll(pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(pageable).get().map(ele -> this.convertToDTO(MailTemplateDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<MailTemplate> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, MailTemplate.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = mailTemplateRepository.findAll(specification, pageable).getTotalElements();
            List<MailTemplateDTO> listResult = mailTemplateRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(MailTemplateDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
