package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.RequestDataService;
import com.vsm.business.service.custom.RequestDataCustomServiceV2;
import com.vsm.business.service.custom.statistic.bo.StatisticOption;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.utils.GenerateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing {@link RequestData}.
 */
@RestController
@RequestMapping("/api")
public class RequestDataCustomRestV2 {

    private final Logger log = LoggerFactory.getLogger(RequestDataCustomRestV2.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestDataService requestDataService;

    private final RequestDataRepository requestDataRepository;

    private final RequestDataCustomServiceV2 requestDataCustomServiceV2;

    private final GenerateCodeUtils generateCodeUtils;

    public RequestDataCustomRestV2(RequestDataService requestDataService, RequestDataRepository requestDataRepository, RequestDataCustomServiceV2 requestDataCustomServiceV2, GenerateCodeUtils generateCodeUtils) {
        this.requestDataService = requestDataService;
        this.requestDataRepository = requestDataRepository;
        this.requestDataCustomServiceV2 = requestDataCustomServiceV2;
        this.generateCodeUtils = generateCodeUtils;
    }

    @GetMapping("/v2/user/{userId}/_all_drafting/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataDrafting(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataDrafting(userId);
        log.debug("REST request to getRequestDataWithOption({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_drafting/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataDraftingPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataDrafting(userId, pageable);
        log.debug("REST request to getRequestDataWithOption({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_drafting/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataDrafting(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataDrafting(userId);
        log.debug("REST request to getRequestDataWithOption({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_need_handle/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataNeedToHandle(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataNeedToHandle(userId);
        log.debug("REST request to getRequestDataNeedToHandle({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_need_handle/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataNeedToHandlePaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataNeedToHandle(userId, pageable);
        log.debug("REST request to getRequestDataNeedToHandle({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_need_handle/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataNeedToHandle(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataNeedToHandle(userId);
        log.debug("REST request to getRequestDataNeedToHandle({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_give_back/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataGiveBack(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataGiveBack(userId);
        log.debug("REST request to getRequestDataGiveBack({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_give_back/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataGiveBackPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataGiveBack(userId, pageable);
        log.debug("REST request to getRequestDataGiveBack({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_give_back/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataGiveBack(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataGiveBack(userId);
        log.debug("REST request to getRequestDataGiveBack({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_wait_approval/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataWaitApproval(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataWaitApproval(userId);
        log.debug("REST request to getRequestDataWaitApproval({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_wait_approval/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataWaitApprovalPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataWaitApproval(userId, pageable);
        log.debug("REST request to getRequestDataWaitApproval({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_wait_approval/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataWaitApproval(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequsetDataWaitApproval(userId);
        log.debug("REST request to getRequestDataWaitApproval({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_approved/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataApproved(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataApproved(userId);
        log.debug("REST request to getRequestDataApproved({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_approved/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataApprovedPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataApproved(userId, pageable);
        log.debug("REST request to getRequestDataApproved({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_approved/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataApproved(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataApproved(userId);
        log.debug("REST request to getRequestDataApproved({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_share_to_user/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataShareToUser(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataShareToUser(userId);
        log.debug("REST request to getRequestDataShareToUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_share_to_user/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataShareToUserPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataShareToUser(userId, pageable);
        log.debug("REST request to getRequestDataShareToUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_share_to_user/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataShareToUser(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataShareToUser(userId);
        log.debug("REST request to getRequestDataShareToUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_of_user/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataOfUser(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataOfUser(userId);
        log.debug("REST request to getRequestDataOfUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_of_user/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataOfUserPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataOfUser(userId, pageable);
        log.debug("REST request to getRequestDataOfUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_of_user/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataOfUser(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataOfUser(userId);
        log.debug("REST request to getRequestDataOfUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @GetMapping("/v2/user/{userId}/_all_following/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataFollow(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataFollow(userId);
        log.debug("REST request to getRequestDataFollow({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_following/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataFollowPaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataFollow(userId, pageable);
        log.debug("REST request to getRequestDataFollow({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_following/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataFollow(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataFollow(userId);
        log.debug("REST request to getRequestDataFollow({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_with_role/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataWithRole(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataWithRole(userId);
        log.debug("REST request to getRequestDataWithRole({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all_with_role/paging/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataWithRolePaging(@PathVariable("userId") Long userId, Pageable pageable){
        List<RequestDataDTO> result = this.requestDataCustomServiceV2.findAllRequestDataWithRole(userId, pageable);
        log.debug("REST request to getRequestDataWithRolePaging({}, {}): {}", userId, pageable, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count_all_with_role/request-data")
    public ResponseEntity<IResponseMessage> countRequestDataWithRole(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomServiceV2.countAllRequestDataWithRole(userId);
        log.debug("REST request to countRequestDataWithRole({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

        // api phục vụ thống kê \\
    public ResponseEntity<IResponseMessage> getRequestDataWithStatistic(StatisticOption statisticOption){
        List<RequestDataDTO> result = new ArrayList<>();
        log.debug("REST request to REST request to getRequestWithStatistic({}): {}", statisticOption, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

}
