package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.SignatureInfomation;
import com.vsm.business.repository.SignatureInfomationRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.SignatureInfomationDTO;
import com.vsm.business.service.mapper.SignatureInfomationMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SignatureInfomationSearchService implements IBaseSearchService<SignatureInfomationDTO, SignatureInfomation> {

    private final SignatureInfomationRepository signatureInfomationRepository;

    private final SignatureInfomationMapper signatureInfomationMapper;

    public SignatureInfomationSearchService(SignatureInfomationRepository signatureInfomationRepository, SignatureInfomationMapper signatureInfomationMapper) {
        this.signatureInfomationRepository = signatureInfomationRepository;
        this.signatureInfomationMapper = signatureInfomationMapper;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(SignatureInfomationDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signatureInfomationRepository.findAll(pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(pageable).get().map(ele -> signatureInfomationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignatureInfomation> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignatureInfomation.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signatureInfomationRepository.findAll(specification, pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(specification, pageable).get().map(ele -> signatureInfomationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(SignatureInfomationDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signatureInfomationRepository.findAll(pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(pageable).get().map(ele -> signatureInfomationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignatureInfomation> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignatureInfomation.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signatureInfomationRepository.findAll(specification, pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(specification, pageable).get().map(ele -> signatureInfomationMapper.toDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(SignatureInfomationDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = signatureInfomationRepository.findAll(pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(pageable).get().map(ele -> this.convertToDTO(SignatureInfomationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<SignatureInfomation> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, SignatureInfomation.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = signatureInfomationRepository.findAll(specification, pageable).getTotalElements();
            List<SignatureInfomationDTO> listResult = signatureInfomationRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(SignatureInfomationDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

}
