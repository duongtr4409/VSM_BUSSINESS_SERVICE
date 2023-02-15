package com.vsm.business.service.custom;

import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestGroup;
import com.vsm.business.domain.Step;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.RequestGroupRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RequestGroupSearchRepository;
import com.vsm.business.service.dto.CategoryGroupDTO;
import com.vsm.business.service.dto.RequestGroupDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.RequestGroupMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestGroupCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);
    private RequestGroupRepository requestGroupRepository;
    private RequestGroupSearchRepository requestGroupSearchRepository;
    private RequestGroupMapper requestGroupMapper;
    private RequestRepository requestRepository;
    private RequestTypeRepository requestTypeRepository;

    private UserInfoRepository userInfoRepository;

    private ObjectUtils objectUtils;

    public RequestGroupCustomService(RequestGroupRepository requestGroupRepository, RequestGroupSearchRepository requestGroupSearchRepository, RequestGroupMapper requestGroupMapper, RequestRepository requestRepository, RequestTypeRepository requestTypeRepository, ObjectUtils objectUtils, UserInfoRepository userInfoRepository) {
        this.requestGroupRepository = requestGroupRepository;
        this.requestGroupSearchRepository = requestGroupSearchRepository;
        this.requestGroupMapper = requestGroupMapper;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.objectUtils = objectUtils;
        this.userInfoRepository = userInfoRepository;
    }

    public List<RequestGroupDTO> getAll() {
        log.debug("RequestGroupCustomService: getAll()");
        List<RequestGroup> requestGroups = this.requestGroupRepository.findAll();
        List<RequestGroupDTO> result = new ArrayList<>();
        for (RequestGroup requestGroup :
            requestGroups) {
            RequestGroupDTO requestGroupDTO = requestGroupMapper.toDto(requestGroup);
            result.add(requestGroupDTO);
        }
        return result;
    }

    public List<RequestGroupDTO> deleteAll(List<RequestGroupDTO> requestGroupDTOS) {
        log.debug("RequestGroupCustomService: deleteAll({})", requestGroupDTOS);
        List<Long> ids = requestGroupDTOS.stream().map(RequestGroupDTO::getId).collect(Collectors.toList());
        this.requestGroupRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<RequestGroupDTO> getAllRequestGroupIgnoreFieldByUser(Long userId, Boolean ignoreField){
        List<Long> organizationIds = new ArrayList<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).orElse(null);

        if(userInfo != null){
            organizationIds = userInfo.getOrganizations().stream().map(ele -> ele.getId()).collect(Collectors.toList());
        }

        List<RequestGroupDTO> result = new ArrayList<>();
        List<Long> finalOrganizationIds = organizationIds;
        if(ignoreField){
            result = this.requestGroupRepository.findAll().stream().filter(ele -> {
                return ele.getRequests().stream().anyMatch(ele1 -> ele1.getProcessInfos().stream().anyMatch(ele2 -> {
                    return ele2.getOrganizations().stream().anyMatch(ele3 -> {
                        return finalOrganizationIds.stream().anyMatch(ele4 -> ele4.equals(ele3.getId()));
                    });
                }));
            }).map(ele -> {
                RequestGroupDTO requestGroupDTO = new RequestGroupDTO();
                try {requestGroupDTO = this.objectUtils.coppySimpleType(ele, requestGroupDTO, RequestGroupDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                return requestGroupDTO;
            }).collect(Collectors.toList());
        }else{
            result = this.requestGroupRepository.findAll().stream().filter(ele -> {
                return ele.getRequests().stream().anyMatch(ele1 -> ele1.getProcessInfos().stream().anyMatch(ele2 -> {
                    return ele2.getOrganizations().stream().anyMatch(ele3 -> {
                        return finalOrganizationIds.stream().anyMatch(ele4 -> ele4.equals(ele3.getId()));
                    });
                }));
            }).map(ele -> {
                return this.requestGroupMapper.toDto(ele);
            }).collect(Collectors.toList());
        }
        log.debug("RequestGroupCustomService: getAllRequestGroupIgnoreFieldByUse({}, {}); {}", userId, ignoreField, result);
        return result;
    }

    public List<RequestGroupDTO> getAllRequestGroupIgnoreFieldByUserV2(Long userId, boolean ignoreField){
        Set<Request> requestHasRole = new HashSet<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).orElse(null);
        if(userInfo != null){
            requestHasRole = this.requestRepository.getAllRequestIdsByOrganization(userInfo.getOrganizations().stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().map(ele -> {
                Request request = new Request();
                request.setId(ele);
                return request;
            }).collect(Collectors.toSet());
        }
        List<RequestGroup> requestGroups = this.requestGroupRepository.findAllByRequestsIn(requestHasRole).stream().collect(Collectors.toList());
        if(ignoreField){
            return requestGroups.stream().map(ele -> {
                RequestGroupDTO requestGroupDTO = new RequestGroupDTO();
                try {requestGroupDTO = this.objectUtils.coppySimpleType(ele, requestGroupDTO, RequestGroupDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                return requestGroupDTO;
            }).collect(Collectors.toList());
        }else{
            return requestGroups.stream().map(ele -> {
                return this.requestGroupMapper.toDto(ele);
            }).collect(Collectors.toList());
        }
    }

    public boolean delete(Long id) {
        try {
            requestGroupRepository.deleteById(id);
            requestGroupSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RequestGroupCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<RequestGroupDTO> saveAll(List<RequestGroupDTO> requestGroupDTOList){
        List<RequestGroupDTO> result = requestGroupRepository.saveAll(requestGroupDTOList.stream().map(requestGroupMapper::toEntity).collect(Collectors.toList())).stream().map(requestGroupMapper::toDto).collect(Collectors.toList());
        log.error("RequestGroupCustomService: delete({}) {}", requestGroupDTOList, result);
        return result;
    }

    /**
     * hàm thực hiện update cờ isdelete của RequetGroup (tương đương xóa RequestGroup): kiểm tra xem có Request nào tương ứng ko rồi mới xóa
     * @param categoryGroupDTO
     * @return
     */
    public RequestGroupDTO customSave(RequestGroupDTO requestGroupDTO) throws Exception {
        log.debug("Request to save RequestGroup: {}", requestGroupDTO);

            // kiểm tra xem có Request nào ko (khi thực hiện xóa)
        if(requestGroupDTO.getIsDelete()){
            Boolean checkRequestExit = this.checkExit(requestGroupDTO);
            if(checkRequestExit) throw new Exception("Exist Request");
        }

        RequestGroup requestGroup = requestGroupMapper.toEntity(requestGroupDTO);
        requestGroup = requestGroupRepository.save(requestGroup);
        RequestGroupDTO result = requestGroupMapper.toDto(requestGroup);
        try{
            requestGroupSearchRepository.save(requestGroup);
        }catch (StackOverflowError e){
            log.debug(e.getMessage());
        }
        return result;
    }

    /**
     *  hàm thực hiện update cờ isdelete của RequetGroup (tương đương xóa RequestGroup): kiểm tra xem có Request nào tương ứng ko rồi mới xóa
     * @return
     */
    public List<RequestGroupDTO> customSaveAll(List<RequestGroupDTO> requestGroupDTOList) throws Exception {

        if(requestGroupDTOList.stream().anyMatch(ele -> ele.getIsDelete() == true)){
            Boolean checkRequestExit = false;
            for(RequestGroupDTO requestGroupDTO : requestGroupDTOList){
                checkRequestExit  = this.checkExit(requestGroupDTO);
                if(checkRequestExit) throw new Exception("Exist Request");
            }
        }

        List<RequestGroupDTO> result = requestGroupRepository.saveAll(requestGroupDTOList.stream().map(requestGroupMapper::toEntity).collect(Collectors.toList())).stream().map(requestGroupMapper::toDto).collect(Collectors.toList());
        log.debug("RequestGroupCustomService: delete({}) {}", requestGroupDTOList, result);
        return result;
    }

    private Boolean checkExit(RequestGroupDTO requestGroupDTO) {
        Boolean result =
            requestRepository.findAllByRequestGroup(requestGroupMapper.toEntity(requestGroupDTO)).stream().anyMatch(ele -> ele.getIsDelete() != true)
            || requestTypeRepository.findAllByRequestGroupId(requestGroupDTO.getId()).stream().anyMatch(ele -> ele.getIsDelete() != true);
        return result;
    }

    public List<RequestGroupDTO> getAllIgnoreField() {
        log.debug("RequestGroupCustomService: getAllIgnoreField()");
        List<RequestGroup> requestGroups = this.requestGroupRepository.findAll();
        List<RequestGroupDTO> result = new ArrayList<>();
        for (RequestGroup requestGroup :
            requestGroups) {
            if(requestGroup != null){
                RequestGroupDTO requestGroupDTO = new RequestGroupDTO();
                try {requestGroupDTO = this.objectUtils.coppySimpleType(requestGroup, requestGroupDTO, RequestGroupDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                result.add(requestGroupDTO);
            }
        }
        return result;
    }
}
