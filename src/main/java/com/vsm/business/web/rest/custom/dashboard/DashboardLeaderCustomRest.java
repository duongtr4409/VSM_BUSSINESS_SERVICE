package com.vsm.business.web.rest.custom.dashboard;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.dashboard.DashBoardLeaderCustomService;
import com.vsm.business.service.custom.dashboard.bo.DashboardDto;
import com.vsm.business.service.custom.dashboard.bo.DashboardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class DashboardLeaderCustomRest {

    private final Logger log = LoggerFactory.getLogger(DashboardLeaderCustomRest.class);

    private final DashBoardLeaderCustomService dashBoardLeaderCustomService;

    public DashboardLeaderCustomRest(DashBoardLeaderCustomService dashBoardLeaderCustomService) {
        this.dashBoardLeaderCustomService = dashBoardLeaderCustomService;
    }

    @PostMapping("/_leader_statistic/member/dashboard")
    public ResponseEntity<IResponseMessage> statisticMember(@RequestBody DashboardDto dashboardDto) throws Exception {
        List<DashboardResponse> result = this.dashBoardLeaderCustomService.statisticMember(dashboardDto);
        log.info("DashboardLeaderCustomRest: statisticMember({})", dashboardDto);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
