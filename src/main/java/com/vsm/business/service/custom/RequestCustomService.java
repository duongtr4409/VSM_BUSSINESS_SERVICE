package com.vsm.business.service.custom;

import com.vsm.business.domain.ProcessInfo;
import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestType;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RequestSearchRepository;
import com.vsm.business.service.dto.*;
import com.vsm.business.service.mapper.RequestMapper;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.ObjectUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestCustomService {
    private final Logger log = LoggerFactory.getLogger(RequestCustomService.class);

    @Value("${role-request.check-role:TRUE}")
    public String CHECK_ROLE;

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final RequestSearchRepository requestSearchRepository;

    private final RequestTypeRepository requestTypeRepository;

    private final ProcessInfoRepository processInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final AuthenticateUtils authenticateUtils;

    public RequestCustomService(
        RequestRepository requestRepository,
        RequestMapper requestMapper,
        RequestSearchRepository requestSearchRepository,
        RequestTypeRepository requestTypeRepository,
        ProcessInfoRepository processInfoRepository,
        AuthenticateUtils authenticateUtils,
        UserInfoRepository userInfoRepository
    ) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.requestSearchRepository = requestSearchRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.processInfoRepository = processInfoRepository;
        this.authenticateUtils = authenticateUtils;
        this.userInfoRepository = userInfoRepository;
    }

    public RequestDTO customSave(RequestDTO requestDTO) throws IllegalAccessException {

        // generate code: hiện tại lấy luôn code của requestType tương ứng
        String code = requestDTO.getRequestType().getRequestTypeCode();
        if(Strings.isNullOrEmpty(code)){
            code = this.requestTypeRepository.findById(requestDTO.getRequestType().getId()).orElse(new RequestType()).getRequestTypeCode();
        }
        requestDTO.setRequestCode(code);

        log.debug("Request to customSave Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        //RequestDTO result = requestMapper.toDto(request);
        RequestDTO result = convertToDto(request);
        try {
            requestSearchRepository.save(request);
        }catch (StackOverflowError | UncategorizedElasticsearchException e){
            log.debug(e.getMessage());
        }

        cleanCache();

        return result;
    }

    public Optional<RequestDTO> customFindOne(Long id, boolean ignoreField) {
        log.debug("Request to get Request : {}", id);
        if(ignoreField){

            Request request = this.requestRepository.findById(id).get();
            RequestDTO result = new RequestDTO();
            BeanUtils.copyProperties(request, result);
            // thêm thông tin quy trình vào trong result
            Set<ProcessInfoDTO> processInfoDTOS = new HashSet<>();
            request.getProcessInfos().stream().forEach(ele -> {
                try {
                    ProcessInfoDTO processInfoTemp = new ProcessInfoDTO();
                    processInfoTemp = this.objectUtils.coppySimpleType(ele, processInfoTemp, ProcessInfoDTO.class);
                    processInfoDTOS.add(processInfoTemp);
                }catch (Exception e){
                    log.error("{}", e);
                }
            });
            result.setProcessInfos(processInfoDTOS);
            return Optional.of(result);
        }else{
            return requestRepository.findOneWithEagerRelationships(id).map(requestMapper::toDto);
        }
    }

    public List<RequestDTO> findAll(Boolean ignoreField) {
//        List<RequestDTO> result = requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
//        log.info("RequestCustomService findAll(): {}", result);
//        return result;
        List<RequestDTO> result = new ArrayList<>();

        if(ignoreField){
            result = this.requestRepository.getAll().stream().map(ele -> {
                RequestDTO requestDTO = new RequestDTO();
                if(ele != null){
                    try {
                        BeanUtils.copyProperties(ele, requestDTO, "requestType", "requestGroup", "form", "tennant", "created", "modified", "templateForms", "processInfos");
                    }catch (Exception e){
                        log.error("{}", e);
                        return requestDTO;
                    }
                }
                return requestDTO;
            }).collect(Collectors.toList());
        }else{
            result = requestRepository.findAll().stream().map(ele -> {
                try {
                    return convertToDto(ele);
                } catch (IllegalAccessException e) {throw new RuntimeException(e); }
            }).collect(Collectors.toList());
        }
//        log.info("RequestCustomService findAll(): {}", result);
        log.debug("RequestCustomService findAll(): {}", result);
        return result;
    }

    public List<RequestDTO> findAllIgnoreField(){
        List<RequestDTO> result = new ArrayList<>();
        result = this.requestRepository.findAll().stream().map(ele -> {
           return this.ConvertToDTOIgnoreField(ele);
        }).collect(Collectors.toList());
        log.debug("RequestCustomService findAll(): {}", result);
        return result;
    }

    public List<RequestDTO> findAllIgnoreFieldWithForm(){
        List<RequestDTO> result = new ArrayList<>();
        result = this.requestRepository.findAll().stream().map(ele -> {
           RequestDTO requestDTO = new RequestDTO();
           BeanUtils.copyProperties(ele, requestDTO);
           if(ele.getForm() != null){
               FormDTO formDTO = new FormDTO();
               BeanUtils.copyProperties(ele.getForm(), formDTO);
               requestDTO.setForm(formDTO);
           }
           return requestDTO;
        }).collect(Collectors.toList());
        return result;
    }

    private final String[] ignoreField = {"tennant", "created", "modified"};
//    @Cacheable(value = "/_all/request_group/{requestGroupId}/requests", key = "#requestGroupId + \"\" + #ignoreField + \"\"")
    public List<RequestDTO> findAllByRequestGroupIgnoreField(Long requestGroupId, Boolean ignoreField){
        List<RequestDTO> result = new ArrayList<>();
        if(ignoreField){
            result = this.requestRepository.getAllByRequestGroupId(requestGroupId).stream().map(ele -> {
                return this.ConvertToDTOIgnoreField(ele);
            }).collect(Collectors.toList());
        }else{
            result = this.requestRepository.findAllByRequestGroupId(requestGroupId).stream().map(ele -> this.requestMapper.toDto(ele)).collect(Collectors.toList());
        }
        log.debug("RequestCustomService findAllByRequestGroupIgnoreField(): {}", result);
        return result;
    }

    //    @Cacheable(value = "/_all/request_group/{requestGroupId}/requests", key = "#requestGroupId + \"\" + #ignoreField + \"\"")
    public List<RequestDTO> findAllByRequestGroupIgnoreFieldByUser(Long requestGroupId, Boolean ignoreField, Long userId){
        List<Long> organizationIds = new ArrayList<>();

        UserInfo userInfo = this.userInfoRepository.findById(userId).orElse(null);
        if(userInfo != null){
            organizationIds = userInfo.getOrganizations().stream().map(ele -> ele.getId()).collect(Collectors.toList());
        }

        List<Long> finalOrganizationIds = organizationIds;
        List<RequestDTO> result = new ArrayList<>();
        if(ignoreField){
            result = this.requestRepository.getAllByRequestGroupId(requestGroupId).stream().filter(ele -> {
                return ele.getProcessInfos().stream().anyMatch(ele1 -> {
                    return ele1.getOrganizations().stream().anyMatch(ele2 -> finalOrganizationIds.stream().anyMatch(ele3 -> ele3.equals(ele2.getId())));
                });
            }).map(ele -> {
                return this.ConvertToDTOIgnoreField(ele);
            }).collect(Collectors.toList());
        }else{
            result = this.requestRepository.findAllByRequestGroupId(requestGroupId).stream().filter(ele -> {
                return ele.getProcessInfos().stream().anyMatch(ele1 -> {
                    return ele1.getOrganizations().stream().anyMatch(ele2 -> finalOrganizationIds.stream().anyMatch(ele3 -> ele3.equals(ele2.getId())));
                });
            }).map(ele -> this.requestMapper.toDto(ele)).collect(Collectors.toList());
        }
        log.debug("RequestCustomService findAllByRequestGroupIgnoreField(): {}", result);
        return result;
    }

    public List<RequestDTO> findAllByRequestGroupIgnoreFieldWithRole(Long requestGroupId, Boolean ignoreField){
        List<RequestDTO> result = new ArrayList<>();
        if(ignoreField){
            result = this.requestRepository.getAllByRequestGroupId(requestGroupId).stream().filter(ele -> {             // thêm phần phân quyền chỉ được tạo những loại yêu cầu được phép
                return "FALSE".equalsIgnoreCase(this.CHECK_ROLE) || this.authenticateUtils.getRequestForUserWithHasAction(null, AuthenticateUtils.EDIT).contains(ele.getId());
            }).map(ele -> {
                return this.ConvertToDTOIgnoreField(ele);
            }).collect(Collectors.toList());
        }else{
            result = this.requestRepository.findAllByRequestGroupId(requestGroupId).stream().filter(ele -> {            // thêm phần phân quyền chỉ được tạo những loại yêu cầu được phép
                return "FALSE".equalsIgnoreCase(this.CHECK_ROLE) || (ele.getRequestType() != null && this.authenticateUtils.getRequestForUserWithHasAction(null, AuthenticateUtils.EDIT).contains(ele.getId()));
            }).map(ele -> this.requestMapper.toDto(ele)).collect(Collectors.toList());
        }
        log.debug("RequestCustomService findAllByRequestGroupIgnoreField(): {}", result);
        return result;
    }

    public List<RequestDTO> saveAll(List<RequestDTO> requestDTOList) {
        List<RequestDTO> result = requestRepository.saveAll(requestDTOList.stream().map(requestMapper::toEntity).collect(Collectors.toList())).stream().map(requestMapper::toDto).collect(Collectors.toList());
//        log.info("RequestCustomService saveAll({}): {}", requestDTOList, result);
        log.debug("RequestCustomService saveAll({}): {}", result);

        cleanCache();

        return result;
    }

    public List<RequestDTO> findAllByRequestTypeId(Long requestTypeId){
//        List<RequestDTO> result = requestRepository.findAllByRequestTypeId(requestTypeId).stream().map(requestMapper::toDto).collect(Collectors.toList());
//        log.info("RequestCustomService findAllByRequestTypeId({}): {}", requestTypeId, result);
//        return result;
        List<RequestDTO> result = requestRepository.findAllByRequestTypeId(requestTypeId).stream().map(ele -> {
            try {
                return convertToDto(ele);
            } catch (IllegalAccessException e) {throw new RuntimeException(e); }
        }).collect(Collectors.toList());
//        log.info("RequestCustomService findAllByRequestTypeId({}): {}", requestTypeId, result);
        log.debug("RequestCustomService findAllByRequestTypeId({}): {}", requestTypeId, result);
        return result;
    }

//    public List<RequestDTO> findAllByProcessInfoId(Long processInfoId) {
//        List<RequestDTO> result = this.processInfoRepository.findById(processInfoId).get().getRequests().stream().map(ele -> convertEntityToDto(ele)).collect(Collectors.toList());
//        return result;
//    }

    private RequestDTO convertEntityToDto(Request request){
        RequestDTO result = new RequestDTO();
        if(result == null) return null;
        request.getRequestGroup();
        request.getRequestType();
        BeanUtils.copyProperties(request, result);
        return result;
    }

    @Autowired
    private ObjectUtils objectUtils;
    private RequestDTO convertToDto(Request request) throws IllegalAccessException {
        if(request == null) return null;
        RequestDTO result = new RequestDTO();
        this.objectUtils.copyproperties_v2(request, result, RequestDTO.class);
//        result.setProcessInfos(
//            request.getProcessInfos().stream().map(ele -> {
//                ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
//                try {
//                    processInfoDTO = this.objectUtils.copyproperties_v2(ele, processInfoDTO, ProcessInfoDTO.class);
//                } catch (IllegalAccessException e){throw new RuntimeException(e);}
//                return processInfoDTO;
//            }).collect(Collectors.toSet()));
        return result;
    }

    private final List<String> listFieldIgnore =Arrays.asList("");
    private RequestDTO ConvertToDTOIgnoreField(Request request){
        if(request == null) return null;
        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        requestDTO.setProcessInfos(
            request.getProcessInfos().stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();

                BeanUtils.copyProperties(ele, processInfoDTO);

                processInfoDTO.setOrganizations(ele.getOrganizations().stream().map(ele1 -> {
                    OrganizationDTO organizationDTO = new OrganizationDTO();
                    BeanUtils.copyProperties(ele1, organizationDTO);
                    return organizationDTO;
                }).collect(Collectors.toSet()));

                return processInfoDTO;
            }).collect(Collectors.toSet())
        );

        if(request.getModified() != null){
            UserInfoDTO modified = new UserInfoDTO();
            BeanUtils.copyProperties(request.getModified(), modified);
            requestDTO.setModified(modified);
        }

        if(request.getRequestGroup() != null){
            RequestGroupDTO requestGroupDTO = new RequestGroupDTO();
            BeanUtils.copyProperties(request.getRequestGroup(), requestGroupDTO);
            requestDTO.setRequestGroup(requestGroupDTO);
        }

        if(request.getRequestType() != null){
            RequestTypeDTO requestTypeDTO = new RequestTypeDTO();
            BeanUtils.copyProperties(request.getRequestType(), requestTypeDTO);
            requestDTO.setRequestType(requestTypeDTO);
        }

        return requestDTO;
    }

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JCacheManagerCustomizer jCacheManagerCustomizer;

    public void cleanCache(){
        try {
            cacheManager.getCache("/_all/request_group/{requestGroupId}/requests").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
    }
}
