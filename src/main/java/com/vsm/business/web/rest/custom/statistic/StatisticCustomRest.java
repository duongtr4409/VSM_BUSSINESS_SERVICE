package com.vsm.business.web.rest.custom.statistic;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.statistic.StatisticCustomService;
import com.vsm.business.service.custom.statistic.bo.StatisticOption;
import com.vsm.business.service.dto.FieldInFormDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * REST controller for managing {@link com.vsm.business.domain.RequestData}.
 */

@RestController
@RequestMapping("/api")
public class StatisticCustomRest {

    private final Logger log = LoggerFactory.getLogger(StatisticCustomRest.class);

    private final StatisticCustomService statisticCustomService;

    private final UserUtils userUtils;

    public StatisticCustomRest(StatisticCustomService statisticCustomService, UserUtils userUtils) {
        this.statisticCustomService = statisticCustomService;
        this.userUtils = userUtils;
    }

    @GetMapping("/user/{userId}/_all/request-with-role")
    public ResponseEntity<IResponseMessage> getAllRequestWithRole(@PathVariable("userId") Long userId){
        Set<RequestDTO> result = this.statisticCustomService.getAllRequestWithRole(userId);
        log.debug("StatisticCustomRest: getAllRequestWithRole({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all/organization-with-role")
    public ResponseEntity<IResponseMessage> getAllOrganizationWithRole(@PathVariable("userId") Long userId){
        Set<OrganizationDTO> result = this.statisticCustomService.getAllOrganizationWithRole(userId);
        log.debug("StatisticCustomRest: getAllOrganizationWithRole({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request/{requestId}/_all/field-in-form")
    public ResponseEntity<IResponseMessage> getAllFileInFormOfRequest(@PathVariable("requestId") Long requestId){
        List<FieldInFormDTO> result = this.statisticCustomService.getAllFieldInFormWithRequest(requestId);
        log.debug("StatisticCustomRest: getAllFileInFormOfRequest({}): {}", requestId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/statistic")
    public ResponseEntity<IResponseMessage> getRequestDataStatistic(@RequestBody StatisticOption statisticOption, Pageable pageable){
        List<RequestDataDTO> result = this.statisticCustomService.getRequestData(statisticOption, pageable);
        log.debug("StatisticCustomRest: getAllRequestDataStatistic({}, {}): {}", statisticOption, pageable, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_all/statistic")
    public ResponseEntity<IResponseMessage> getAllRequestDataStatistic(@RequestBody StatisticOption statisticOption){
        List<RequestDataDTO> result = this.statisticCustomService.getRequestData(statisticOption);
        log.debug("StatisticCustomRest: getAllRequestDataStatistic({}): {}", statisticOption, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_count/statistic")
    public ResponseEntity<IResponseMessage> countAllRequestDataStatistic(@RequestBody StatisticOption statisticOption){
        Long result = this.statisticCustomService.countRequestData(statisticOption);
        log.debug("StatisticCustomRest: getAllRequestDataStatistic({}): {}", statisticOption, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_check_permission/statistic")
    public ResponseEntity<IResponseMessage> getPermissionOfUserInRequestData(@RequestParam("requestDataId") Long requestDataId){
        Map<String, Boolean> result = this.statisticCustomService.getPermissionOfUserInRequestData(this.userUtils.getCurrentUser().getId(),requestDataId);
        log.debug("StatisticCustomRest: getPermissionOfUserInRequestData({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
