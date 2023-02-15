package com.vsm.business.service.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsm.business.domain.*;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RequestDataSearchRepository;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.RequestDataMapper;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestDataCustomServiceV3 {
    private final Logger log = LoggerFactory.getLogger(RequestDataCustomServiceV3.class);

    private final RequestDataRepository requestDataRepository;

    private final RequestDataMapper requestDataMapper;

    private final RequestDataSearchRepository requestDataSearchRepository;

    private final StatusRepository statusRepository;

    private final UserInfoRepository userInfoRepository;

    private final AuthenticateUtils authenticateUtils;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${system.category.status.KHOITAO:KHOITAO}")
    public String KHOITAO;
    @Value("${system.category.status.DANGSOAN:DANGSOAN}")
    public String DANGSOAN;
    @Value("${system.category.status.CHOXULY:CHOXULY}")
    public String CHOXULY;
    @Value("${system.category.status.DAPHEDUYET:DAPHEDUYET}")
    public String DAPHEDUYET;
    @Value("${system.category.status.DAHOANTHANH:DAHOANTHANH}")
    public String DAHOANTHANH;
    @Value("${system.category.status.TUCHOI:TUCHOI}")
    public String TUCHOI;
    @Value("${system.category.status.TRALAI:TRALAI}")
    public String TRALAI;
    @Value("${system.category.status.HUYPHEDUYET:HUYPHEDUYET}")
    public String HUYPHEDUYET;

    @Value("${role-request.check-role:TRUE}")
    public String CHECK_ROLE;

    @Autowired
    private ConditionUtils conditionUtils;
    @Autowired
    private RequestDataCustomService requestDataCustomService;

    public RequestDataCustomServiceV3(RequestDataRepository requestDataRepository, RequestDataMapper requestDataMapper, RequestDataSearchRepository requestDataSearchRepository, StatusRepository statusRepository, UserInfoRepository userInfoRepository, AuthenticateUtils authenticateUtils) {
        this.requestDataRepository = requestDataRepository;
        this.requestDataMapper = requestDataMapper;
        this.requestDataSearchRepository = requestDataSearchRepository;
        this.statusRepository = statusRepository;
        this.userInfoRepository = userInfoRepository;
        this.authenticateUtils = authenticateUtils;
    }

            // lấy danh sách phiếu đang soạn (của người tạo phiếu)
    public List<RequestDataDTO> findAllRequestDataDrafting(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_DANGSOAN.getId());
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DANGSOAN.getId(), userId);
//            return convertToDTO(requestDataList);
////        return requestDataList.stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());
//        }else{
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
//            return convertToDTO(requestDataList);
//        }
        Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }

    // paging \\
    public List<RequestDataDTO> findAllRequestDataDrafting(Long userId, Pageable pageable){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            pageable = this.getDefaultSort(pageable);
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_DANGSOAN.getId(), pageable);
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DANGSOAN.getId(), userId, pageable);
//            return convertToDTO(requestDataList);
//        }else{
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            pageable = this.getDefaultSort(pageable);
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
//            return convertToDTO(requestDataList);
//        }
////        return requestDataList.stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());

        Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
        pageable = this.getDefaultSort(pageable);
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataDrafting(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            if(userId < 0)
//                return this.requestDataRepository.findAllByStatusId(status_DANGSOAN.getId()).stream().count();
//            else
//                return this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DANGSOAN.getId(), userId).stream().count();
//        }else{
//            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
//            List<RequestData> requestDataList = new ArrayList<>();
//            return this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
//        }

        Status  status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
        return this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DANGSOAN.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
    }


            // lấy danh sách phiếu cần xử lý (những người có trong bước đang xử lý)
    public List<RequestDataDTO> findAllRequestDataNeedToHandle(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandle(userId);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandleWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }

        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandleWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }
    // pagging
    public List<RequestDataDTO> findAllRequestDataNeedToHandle(Long userId, Pageable pageable){
//        long limit = pageable.getPageSize();
//        long offset = pageable.getPageNumber() * pageable.getPageSize();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandle(userId, offset, limit);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandleWithRole(userId, offset, limit, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }

        long limit = pageable.getPageSize();
        long offset = pageable.getPageNumber() * pageable.getPageSize();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataNeedHandleWithRole(offset, limit, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataNeedToHandle(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            return this.requestDataRepository.getAllRequestDataNeedHandle(userId).stream().count();
//        }else{
//            return this.requestDataRepository.getAllRequestDataNeedHandleWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
//        }

        return this.requestDataRepository.getAllRequestDataNeedHandleWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
    }

            // lấy danh sách phiếu bị trả lại (của người tạo phiếu)
    public List<RequestDataDTO> findAllRequestDataGiveBack(Long userId) {
//        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
//        List<RequestData> requestDataList = null;
//        if ("FALSE".equalsIgnoreCase(this.CHECK_ROLE)) {
//            requestDataList = new ArrayList<>();
//            if (userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_TRALAI.getId());
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_TRALAI.getId(), userId);
//        } else {
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
//        }
//        return convertToDTO(requestDataList);
////        return requestDataList.stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());

        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }

    // paging
    public List<RequestDataDTO> findAllRequestDataGiveBack(Long userId, Pageable pageable){
//        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
//        List<RequestData> requestDataList = new ArrayList<>();
//        pageable =  this.getDefaultSort(pageable);
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_TRALAI.getId(), pageable);
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_TRALAI.getId(), userId, pageable);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
//        }
//        return convertToDTO(requestDataList);
////        return requestDataList.stream().map(this.requestDataMapper::toDto).collect(Collectors.toList());

        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
        pageable =  this.getDefaultSort(pageable);
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataGiveBack(Long userId){
//        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                return this.requestDataRepository.findAllByStatusId(status_TRALAI.getId()).stream().count();
//            else
//                return this.requestDataRepository.findAllByStatusIdAndCreatedId(status_TRALAI.getId(), userId).stream().count();
//        }else{
//            return this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
//        }

        Status status_TRALAI = this.statusRepository.findAllByStatusCode(this.TRALAI).stream().findFirst().get();
        return this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_TRALAI.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
    }


            // lấy danh sách phiếu đang theo dõi (của người tạo và người có trong quy trình)
    public List<RequestDataDTO> findAllRequestDataFollow(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowing(userId);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowingWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }

        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowingWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }
    // paging
    public List<RequestDataDTO> findAllRequestDataFollow(Long userId, Pageable pageable){
//        long offset = pageable.getPageNumber() * pageable.getPageSize();
//        long limit = pageable.getPageSize();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowing(userId, offset, limit);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowingWithRole(userId, offset, limit, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }
        long offset = pageable.getPageNumber() * pageable.getPageSize();
        long limit = pageable.getPageSize();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataFollowingWithRole(limit, offset, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }
    public Long countAllRequestDataFollow(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            return this.requestDataRepository.getAllRequestDataFollowing(userId).stream().count();
//        }else{
//            return this.requestDataRepository.getAllRequestDataFollowingWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
//        }

        return this.requestDataRepository.getAllRequestDataFollowingWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
    }


            // lấy danh sách phiếu đang đang chờ phê duyệt (của người tạo)
    public List<RequestDataDTO> findAllRequestDataWaitApproval(Long userId){
//        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
//        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
//        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                //requestDataList = this.requestDataRepository.findAllByStatusId(status_CHOPHEDUYET.getId());
//                requestDataList = this.requestDataRepository.findAllByStatusIdIn(statusIds);
//            else
//                //requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_CHOPHEDUYET.getId(), userId);
//                requestDataList = this.requestDataRepository.findAllByStatusIdInAndCreatedId(statusIds, userId);
//            return convertToDTO(requestDataList);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByStatusIdInAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(statusIds, userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
//            return convertToDTO(requestDataList);
//        }

        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdInAndRequestIdInAndCreatedOrganizationsIn(statusIds, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }

    public List<RequestDataDTO> findAllRequestDataWaitApproval(Long userId, Pageable pageable){
//        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
//        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
//        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
//        List<RequestData> requestDataList = new ArrayList<>();
//        pageable = this.getDefaultSort(pageable);
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                //requestDataList = this.requestDataRepository.findAllByStatusId(status_CHOPHEDUYET.getId());
//                requestDataList = this.requestDataRepository.findAllByStatusIdIn(statusIds, pageable);
//            else
//                //requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_CHOPHEDUYET.getId(), userId);
//                requestDataList = this.requestDataRepository.findAllByStatusIdInAndCreatedId(statusIds, userId, pageable);
//            return convertToDTO(requestDataList);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByStatusIdInAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(statusIds, userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
//            return convertToDTO(requestDataList);
//        }

        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
        pageable = this.getDefaultSort(pageable);
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdInAndRequestIdInAndCreatedOrganizationsIn(statusIds, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }

    public Long countAllRequsetDataWaitApproval(Long userId){
//        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
//        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
//        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                //return this.requestDataRepository.findAllByStatusId(status_CHOPHEDUYET.getId()).stream().count();
//                return this.requestDataRepository.findAllByStatusIdIn(statusIds).stream().count();
//            else
//                //return this.requestDataRepository.findAllByStatusIdAndCreatedId(status_CHOPHEDUYET.getId(), userId).stream().count();
//                return this.requestDataRepository.findAllByStatusIdInAndCreatedId(statusIds, userId).stream().count();
//        }else{
//            return this.requestDataRepository.findAllByStatusIdInAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(statusIds, userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
//        }

        Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
        Status status_HUYPHEDUYET = this.statusRepository.findAllByStatusCode(this.HUYPHEDUYET).stream().findFirst().get();
        List<Long> statusIds = Arrays.asList(status_CHOPHEDUYET.getId(), status_HUYPHEDUYET.getId());
        return this.requestDataRepository.findAllByStatusIdInAndRequestIdInAndCreatedOrganizationsIn(statusIds, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
    }



            // lấy danh sách phiếu đã phê duyệt (của người tạo)
    public List<RequestDataDTO> findAllRequestDataApproved(Long userId){
//        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_DAPHEDUYET.getId());
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DAPHEDUYET.getId(), userId);
//            return convertToDTO(requestDataList);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
//            return convertToDTO(requestDataList);
//        }

        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }

    public List<RequestDataDTO> findAllRequestDataApproved(Long userId, Pageable pageable){
//        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
//        List<RequestData> requestDataList = new ArrayList<>();
//        pageable = this.getDefaultSort(pageable);
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAllByStatusId(status_DAPHEDUYET.getId(), pageable);
//            else
//                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DAPHEDUYET.getId(), userId, pageable);
//            return convertToDTO(requestDataList);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW) ,pageable);
//            return convertToDTO(requestDataList);
//        }

        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
        pageable = this.getDefaultSort(pageable);
        List<RequestData> requestDataList = this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataApproved(Long userId){
//        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                return this.requestDataRepository.findAllByStatusId(status_DAPHEDUYET.getId()).stream().count();
//            else
//                return this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DAPHEDUYET.getId(), userId).stream().count();
//        }else{
//            return this.requestDataRepository.findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
//        }

        Status status_DAPHEDUYET = this.statusRepository.findAllByStatusCode(this.DAPHEDUYET).stream().findFirst().get();
        return this.requestDataRepository.findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(status_DAPHEDUYET.getId(), this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
    }


            // lấy danh sách được chia sẻ (của người dùng)
    public List<RequestDataDTO> findAllRequestDataShareToUser(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUser(userId);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUserWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }

        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUserWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }
    // paging
    public List<RequestDataDTO> findAllRequestDataShareToUser(Long userId, Pageable pageable){
//        long offset = pageable.getPageNumber() * pageable.getPageNumber();
//        long limit = pageable.getPageNumber();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUser(userId, offset, limit);
//            return convertToDTO(requestDataList);
//        }else{
//            List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUserWithRole(userId, offset, limit, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
//            return convertToDTO(requestDataList);
//        }

        long offset = pageable.getPageNumber() * pageable.getPageSize();
        long limit = pageable.getPageSize();
        List<RequestData> requestDataList = this.requestDataRepository.getAllRequestDataSharedToUserWithRole(offset, limit, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet()));
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataShareToUser(Long userId){
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            return this.requestDataRepository.getAllRequestDataSharedToUser(userId).stream().count();
//        }else{
//            return this.requestDataRepository.getAllRequestDataSharedToUserWithRole(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
//        }

        return this.requestDataRepository.getAllRequestDataSharedToUserWithRole(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().collect(Collectors.toSet()), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toSet())).stream().count();
    }


            // lấy danh phiếu của người dùng (của người dùng) \\
    public List<RequestDataDTO> findAllRequestDataOfUser(Long userId){
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAll();
//            else
//                requestDataList = this.requestDataRepository.findAllByCreatedId(userId);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByCreatedIdAndRequestIdInAndCreatedOrganizationsIn(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
//        }
//        return convertToDTO(requestDataList);

        List<RequestData> requestDataList = this.requestDataRepository.findAllByRequestIdInAndCreatedOrganizationsIn(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }
    // paging
    public List<RequestDataDTO> findAllRequestDataOfUser(Long userId, Pageable pageable){
//        List<RequestData> requestDataList = new ArrayList<>();
//        pageable = this.getDefaultSort(pageable);
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                requestDataList = this.requestDataRepository.findAll(pageable).toList();
//            else
//                requestDataList = this.requestDataRepository.findAllByCreatedId(userId, pageable);
//        }else{
//            requestDataList = this.requestDataRepository.findAllByCreatedIdAndRequestIdInAndCreatedOrganizationsIn(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
//        }
//        return convertToDTO(requestDataList);

        List<RequestData> requestDataList = this.requestDataRepository.findAllByRequestIdInAndCreatedOrganizationsIn(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }

    public Long countAllRequestDataOfUser(Long userId){
//        List<RequestData> requestDataList = new ArrayList<>();
//        if("FALSE".equalsIgnoreCase(this.CHECK_ROLE)){
//            if(userId < 0)
//                return this.requestDataRepository.findAll().stream().count();
//            else
//                return this.requestDataRepository.findAllByCreatedId(userId).stream().count();
//        }else{
//            return this.requestDataRepository.findAllByCreatedIdAndRequestIdInAndCreatedOrganizationsIn(userId, this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
//        }

        return this.requestDataRepository.findAllByRequestIdInAndCreatedOrganizationsIn(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW)).stream().count();
    }

                // utils \\
    @Autowired
    public ObjectUtils objectUtils;
    private RequestDataDTO convertToDTO(RequestData requestData) throws IllegalAccessException {
        RequestDataDTO requestDataDTO = new RequestDataDTO();

//        requestData.getRequestType();
//        requestData.getRequestGroup();
//        requestData.getStatus();
//        requestData.getCreated();
//        requestData.getModified();
//        BeanUtils.copyProperties(requestData, requestDataDTO);
        this.objectUtils.copyproperties(requestData, requestDataDTO, RequestDataDTO.class);
        return requestDataDTO;
    }

    private List<RequestDataDTO> convertToDTO(List<RequestData> requestDataList){
        if(requestDataList == null) return null;
        List<RequestDataDTO> requestDataDTOList = new ArrayList<>();
        requestDataList.forEach(ele ->{
            try {
                requestDataDTOList.add(convertToDTO(ele));
            } catch (IllegalAccessException e) {log.error("{}", e);}
        });
        return requestDataDTOList;
    }

    private Pageable getDefaultSort(Pageable pageable){
        if(pageable.getSort() == null || pageable.getSort().get().count() != 0) return pageable;
        try {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("modifiedDate").descending());
        }catch (Exception e){log.error("{}", e);}
        return pageable;
    }

}
