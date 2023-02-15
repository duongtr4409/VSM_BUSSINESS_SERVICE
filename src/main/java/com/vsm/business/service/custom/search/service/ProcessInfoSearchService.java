package com.vsm.business.service.custom.search.service;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.ProcessInfo;
import com.vsm.business.domain.Request;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.specification.AbstractSpecification;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.IBaseSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.ProcessInfoMapper;
import com.vsm.business.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessInfoSearchService implements IBaseSearchService<ProcessInfoDTO, ProcessInfo> {

    private final ProcessInfoRepository processInfoRepository;

    private final ProcessInfoMapper processInfoMapper;

    private final OrganizationRepository organizationRepository;

    private final RequestRepository requestRepository;

    private final ObjectUtils objectUtils;

    public ProcessInfoSearchService(ProcessInfoRepository processInfoRepository, ProcessInfoMapper processInfoMapper, ObjectUtils objectUtils, OrganizationRepository organizationRepository, RequestRepository requestRepository) {
        this.processInfoRepository = processInfoRepository;
        this.processInfoMapper = processInfoMapper;
        this.objectUtils = objectUtils;
        this.organizationRepository = organizationRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public ISearchResponseDTO simpleQuerySearch(ProcessInfoDTO t, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildCondition(t);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processInfoRepository.findAll(pageable).getTotalElements();
            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(pageable).get().map(ele -> this.customConvertToDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processInfoRepository.findAll(specification, pageable).getTotalElements();
            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(specification, pageable).get().map(ele -> this.customConvertToDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchWithParam(ProcessInfoDTO t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processInfoRepository.findAll(pageable).getTotalElements();
            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(pageable).get().map(ele -> this.customConvertToDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processInfoRepository.findAll(specification, pageable).getTotalElements();

            List<ProcessInfo> temp = processInfoRepository.findAll(specification, pageable).get().collect(Collectors.toList());

            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(specification, pageable).get().map(ele -> this.customConvertToDto(ele)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }


    @Override
    public ISearchResponseDTO simpleQuerySearchIgnoreField(ProcessInfoDTO t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException {
        ISearchResponseDTO result = new ISearchResponseDTO();
        List<ConditionSimpleQuery> conditionSimpleQueryList = this.buildConditionWithParam(t, paramsMap);
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()){
            Long countAll = processInfoRepository.findAll(pageable).getTotalElements();
            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(pageable).get().map(ele -> this.convertToDTO(ProcessInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }else{
            Specification<ProcessInfo> specification = AbstractSpecification.buildQuerySimpleQuery(conditionSimpleQueryList, ProcessInfo.class);
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            Long countAll = processInfoRepository.findAll(specification, pageable).getTotalElements();
            List<ProcessInfoDTO> listResult = processInfoRepository.findAll(specification, pageable).get().map(ele -> this.convertToDTO(ProcessInfoDTO.class, ele, ignoreField)).collect(Collectors.toList());
            result.setTotal(countAll);
            result.setListData(listResult);
        }
        return result;
    }

    private List<ProcessInfoDTO> customConvertToDto(List<ProcessInfo> processInfoList){
        if(processInfoList == null) return null;
        return processInfoList.stream().map(ele -> customConvertToDto(ele)).collect(Collectors.toList());
    }

    private ProcessInfoDTO customConvertToDto(ProcessInfo processInfo){
        if(processInfo == null) return null;
//        ProcessInfoDTO processInfoDTO = processInfoMapper.toDto(processInfo);
        ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
        BeanUtils.copyProperties(processInfo, processInfoDTO, "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates");

            // người sửa \\
        if(processInfo.getModified() != null){
            UserInfoDTO modified = new UserInfoDTO();
            modified.setId(processInfo.getModified().getId());
            modified.setFullName(processInfo.getModified().getFullName());
            try {
                this.objectUtils.coppySimpleType(processInfo.getModified(), modified, UserInfoDTO.class);
            }catch (Exception e){log.error("{}", e);}
            processInfoDTO.setModified(modified);
        }

        processInfoDTO.setOrganizations(this.organizationRepository.getAllOrganizationByProcessInfo(processInfo.getId()).stream().map(ele -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(ele.getId());
            organizationDTO.setOrganizationName(ele.getOrganizationName());
            organizationDTO.setOrganizationCode(ele.getOrganizationCode());
            return organizationDTO;
        }).collect(Collectors.toSet()));

        processInfoDTO.setRequestDTOS(this.requestRepository.getAllRequestByProcessInfo(processInfo.getId()).stream().map(ele -> {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setId(ele.getId());
            requestDTO.setRequestCode(ele.getRequestCode());
            requestDTO.setRequestName(ele.getRequestName());
            return requestDTO;
        }).collect(Collectors.toList()));

            // đơn vị áp dụng \\
//        if(processInfo.getOrganizations() != null){
//            Set<OrganizationDTO> organizationDTOList = new HashSet<>();
//            for(Organization ele : processInfo.getOrganizations()){
//                OrganizationDTO organizationDTO = new OrganizationDTO();
//                organizationDTO.setId(ele.getId());
//                organizationDTO.setOrganizationName(ele.getOrganizationName());
//                organizationDTO.setOrganizationCode(ele.getOrganizationCode());
//                organizationDTOList.add(organizationDTO);
//                if(organizationDTOList.size() > 8){
//                    OrganizationDTO organizationDTOEnd = new OrganizationDTO();
//                    organizationDTO.setId(0L);
//                    organizationDTOEnd.setOrganizationName("...");
//                    organizationDTOEnd.setOrganizationCode("...");
//                    organizationDTOList.add(organizationDTOEnd);
//                    break;
//                }
//            }
//            processInfoDTO.setOrganizations(organizationDTOList);
//        }
//
//            // loại yêu cầu \\
//        if(processInfo.getRequests() != null){
//            List<RequestDTO> requestDTOList = new ArrayList<>();
//            for(Request ele : processInfo.getRequests()){
//                RequestDTO requestDTO = new RequestDTO();
//                requestDTO.setId(ele.getId());
//                requestDTO.setRequestName(ele.getRequestName());
//                requestDTO.setRequestCode(ele.getRequestCode());
//                requestDTOList.add(requestDTO);
//                if(requestDTOList.size() > 8){
//                    RequestDTO requestDTOEnd = new RequestDTO();
//                    requestDTOEnd.setId(0L);
//                    requestDTOEnd.setRequestName("...");
//                    requestDTOEnd.setRequestCode("...");
//                    break;
//                }
//            }
//            processInfoDTO.setRequestDTOS(requestDTOList);
//        }



        return processInfoDTO;
    }
}
