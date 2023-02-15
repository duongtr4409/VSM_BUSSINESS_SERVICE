package com.vsm.business.web.rest.custom.dashboard;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.RequestDataService;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.RequestDataCustomServiceV2;
import com.vsm.business.service.custom.dashboard.DashBoardCustomService;
import com.vsm.business.service.custom.dashboard.bo.DashboardDto;
import com.vsm.business.service.custom.dashboard.bo.DashboardResponse;
import com.vsm.business.service.custom.statistic.StatisticCustomService;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link RequestData}.
 */
@RestController
@RequestMapping("/api")
public class DashboardCustomRest {

    private final Logger log = LoggerFactory.getLogger(DashboardCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestDataService requestDataService;

    private final RequestDataRepository requestDataRepository;

    private final RequestDataCustomServiceV2 requestDataCustomServiceV2;

    private final DashBoardCustomService dashBoardCustomService;

    private final UserUtils userUtils;

    private final AuthenticateUtils authenticateUtils;

    private final StatisticCustomService statisticCustomService;

    public DashboardCustomRest(RequestDataService requestDataService, RequestDataRepository requestDataRepository, RequestDataCustomServiceV2 requestDataCustomServiceV2, DashBoardCustomService dashBoardCustomService, UserUtils userUtils, AuthenticateUtils authenticateUtils, StatisticCustomService statisticCustomService) {
        this.requestDataService = requestDataService;
        this.requestDataRepository = requestDataRepository;
        this.requestDataCustomServiceV2 = requestDataCustomServiceV2;
        this.dashBoardCustomService = dashBoardCustomService;
        this.userUtils = userUtils;
        this.authenticateUtils = authenticateUtils;
        this.statisticCustomService = statisticCustomService;
    }

    @PostMapping("/statistic/dashboard")
    public ResponseEntity<IResponseMessage> statisticRequestData(@RequestBody DashboardDto dashboardDto){

        // check quyền xem thống kê
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw  new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        Long result = this.dashBoardCustomService.statisticRequestData(dashboardDto);
        log.debug("DashboardCustomRest: statisticRequestData({})", dashboardDto);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/statistic-day/dashboard")
    public ResponseEntity<IResponseMessage> statisticRequestDataByDay(@RequestBody DashboardDto dashboardDto){

        // check quyền xem thống kê
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        List<DashboardResponse> result = this.dashBoardCustomService.statisticRequestDataByDay(dashboardDto);
        log.debug("DashboardCustomRest: statisticRequestDataByDay({})", dashboardDto);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/statistic-status/dashboard")
    public ResponseEntity<IResponseMessage> statisticRequestDataStatus(@RequestBody DashboardDto dashboardDto){

        // check quyền xem thống kê
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        List<DashboardResponse> result = this.dashBoardCustomService.statisticRequestDataStatus(dashboardDto);
        log.debug("DashboardCustomRest: statisticRequestDataStatus({})", dashboardDto);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_count_about_to_expire/dashboard")
    public ResponseEntity<IResponseMessage> countAboutToExpire(@RequestBody DashboardDto dashboardDto){
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        Long result = this.dashBoardCustomService.countAboutToExpire(dashboardDto);
        log.debug("DashboardCustomRest: countAboutToExpire({}): {}", dashboardDto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_count_overdue/dashboard")
    public ResponseEntity<IResponseMessage> countOverdue(@RequestBody DashboardDto dashboardDto){
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        Long result = this.dashBoardCustomService.countOverdue(dashboardDto);
        log.debug("DashboardCustomRest: countOverdue({}): {}", dashboardDto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_count_all_need_handle/dashboard")
    public ResponseEntity<IResponseMessage> countAllNeedHandle(@RequestBody DashboardDto dashboardDto){
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        Long result = this.dashBoardCustomService.countAllNeedHandle(dashboardDto);
        log.debug("DashboardCustomRest: countAllNeedHandle({}): {}", dashboardDto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_count_all_give_back/dashboard")
    public ResponseEntity<IResponseMessage> countAllGiveBack(@RequestBody DashboardDto dashboardDto){
        if(!this.checkUser(dashboardDto) && !this.checkPermisitionViewOrganization(dashboardDto)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        Long result = this.dashBoardCustomService.countAllGiveBack(dashboardDto);
        log.debug("DashboardCustomRest: countAllGiveBack({}): {}", dashboardDto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    // hàm kiểm tra xem thông tin user truyền trong body có giống với user đang đăng nhập hay không
    private boolean checkUser(DashboardDto dashboardDto){
        Long userId = dashboardDto.getUserId();
        if(userId >= 0L) {
            MyUserDetail currentUser = this.userUtils.getCurrentUser();
            if (userId != null && currentUser != null) {
                return userId.equals(currentUser.getId());
            }
        }
        return false;
    }

    // hàm kiểm tra xem thông tin user hiện tại có quyền xem thông tin dashboard của các đơn vị hay không
    private boolean checkPermisitionViewOrganization(DashboardDto dashboardDto){
        if(dashboardDto.getOrganizationIds() == null) return true;  // nếu ko thống kê theo đơn vị -> cho qua luôn
        try {
            Long userId = dashboardDto.getUserId();
            if(userId == null) return false;
            List<Long> organizationIds = this.statisticCustomService.getAllOrganizationWithRole(userId).stream().map(ele -> ele.getId()).collect(Collectors.toList());
            if(dashboardDto.getOrganizationIds() != null && organizationIds != null){
                boolean isNotExist = dashboardDto.getOrganizationIds().stream().anyMatch(ele -> !organizationIds.stream().anyMatch(ele1 -> ele1.equals(ele)));
                if(isNotExist) return false;
            }else if(organizationIds == null) return false;
            return true;
        }catch (Exception e){
            log.error("{}", e);
        }
        return false;
    }
}
