package com.vsm.business.service.custom.dashboard;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.Status;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.custom.RequestDataCustomService;
import com.vsm.business.service.custom.dashboard.bo.DashboardDto;
import com.vsm.business.service.custom.dashboard.bo.DashboardResponse;
import com.vsm.business.utils.ConditionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashBoardLeaderCustomService {

    private final Logger log = LoggerFactory.getLogger(DashBoardLeaderCustomService.class);

    @Value("${system.statistic.time-expeire:24}")
    public String time_expeire;            // thời gian để xem xét phiếu là sắp hết hạn chưa (mặc định là 24. phiếu còn 1 ngày để xử lý thì sẽ coi là phiếu sắp hết hạn)

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

    @Value("${system.mailtemplate.reject-id:1}")
    private Long REJECT_MAILTEMPLATE_ID;

    private final RequestDataRepository requestDataRepository;

    private final RequestDataCustomService requestDataCustomService;

    private final StatusRepository statusRepository;

    private final ConditionUtils conditionUtils;

    private final UserInfoRepository userInfoRepository;

    private final OrganizationRepository organizationRepository;
    @Autowired
    private EntityManager entityManager;

    private Map<String, Function<DashboardDto, Long>> handlerStatistic = new HashMap<>();

    private final String CAN_XU_LY = "Cần xử lý";
    private final String SAP_HET_HAN = "Sắp hết hạn";
    private final String QUA_HAN = "Quá hạn";
    private final String DANG_SOAN = "Đang soạn";
    private final String CHO_PHE_DUYET = "Chờ phê duyệt";



    public DashBoardLeaderCustomService(RequestDataRepository requestDataRepository, RequestDataCustomService requestDataCustomService, StatusRepository statusRepository, ConditionUtils conditionUtils, UserInfoRepository userInfoRepository, OrganizationRepository organizationRepository) {
        this.requestDataRepository = requestDataRepository;
        this.requestDataCustomService = requestDataCustomService;
        this.statusRepository = statusRepository;
        this.conditionUtils = conditionUtils;
        this.userInfoRepository = userInfoRepository;
        this.organizationRepository = organizationRepository;
    }


    private void loadHandlerStatistic(){
        if(this.handlerStatistic == null || this.handlerStatistic.isEmpty()){
            this.handlerStatistic.put(this.CAN_XU_LY, functionNeedHandler());
            this.handlerStatistic.put(this.SAP_HET_HAN, functionAboutToExpeire());
            this.handlerStatistic.put(this.QUA_HAN, functionOutOfDate());
            this.handlerStatistic.put(this.DANG_SOAN, functionDrafting());
            this.handlerStatistic.put(this.CHO_PHE_DUYET, functionWaitingApproval());
        }

    }

    private List<Status> ALL_STATUS = new ArrayList<>();
    @Scheduled(cron = "${cron.tab:0 15 03 * * ?}")
    public void getAllStatus(){
        this.ALL_STATUS = this.statusRepository.findAll().stream().filter(ele -> {        // lấy tất cả trạng thái được sử dụng và không bị xóa
            return this.conditionUtils.checkTrueFalse(ele.getIsActive()) && !this.conditionUtils.checkTrueFalse(ele.getIsDelete());
        }).collect(Collectors.toList());
    }

    private Function<DashboardDto, Long> functionNeedHandler(){                             // thực hiện xử lý thống kê phiếu cần xử lý
        return dashboardDto -> {
            String query = "select count(distinct reda.id) from request_data reda " +
                "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and stda.id in (select stda.id from rel_step_data__user_info where " + dashboardDto.getUserId() +" = -1 or user_info_id = " + dashboardDto.getUserId() + ") where " +
                "DATE_TRUNC('day',timezone('utc', reda.created_date)) between " + "'" + dashboardDto.getStartDate().toString() + "'" + " and " + "'" + dashboardDto.getEndDate() + "'";
            if(dashboardDto.getRequestGroupId() != null && !dashboardDto.getRequestGroupId().equals(-1L)){
                query += " and reda.request_group_id = " + dashboardDto.getRequestGroupId();
            }
            System.out.println("DuowngTora Cần Xử Lý: " + query);
            long result =  ((Number)entityManager.createNativeQuery(query).getSingleResult()).longValue();
            return result;
        };
    }

    private Function<DashboardDto, Long> functionAboutToExpeire(){                          // thống kê phiếu sắp hết hạn
        return dashboardDto -> {
            String query = "select count(distinct reda.id) from request_data reda " +
                "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
                "and stda.processing_term_time is not null and timezone('utc', now()) BETWEEN stda.processing_term_time - interval'" + this.time_expeire + "' hour and stda.processing_term_time " +
                "and stda.id in (select stda.id from rel_step_data__user_info where " + dashboardDto.getUserId() + " = -1 or user_info_id = " + dashboardDto.getUserId() + ") where " +
                "DATE_TRUNC('day',timezone('utc', reda.created_date)) between " + "'" + dashboardDto.getStartDate().toString() + "'" + " and " + "'" + dashboardDto.getEndDate() + "'";
            if(dashboardDto.getRequestGroupId() != null && !dashboardDto.getRequestGroupId().equals(-1L)){
                query += " and reda.request_group_id = " + dashboardDto.getRequestGroupId();
            }
            System.out.println("DuowngTora Sắp Hết Hạn: " + query);
            long result =  ((Number)entityManager.createNativeQuery(query).getSingleResult()).longValue();
            return result;
        };
    }

    private Function<DashboardDto, Long> functionOutOfDate(){                               // thóng kê phiếu quá hạn
        return dashboardDto -> {
            String query = "select count(distinct reda.id) from request_data reda " +
                "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
                "and stda.processing_term_time is not null and timezone('utc', now()) > stda.processing_term_time " +
                "and stda.id in (select stda.id from rel_step_data__user_info where " + dashboardDto.getUserId() + " = -1 or user_info_id = " + dashboardDto.getUserId() + ") where " +
                "DATE_TRUNC('day',timezone('utc', reda.created_date)) between " + "'" + dashboardDto.getStartDate().toString() + "'" + " and " + "'" + dashboardDto.getEndDate() + "'";
            if(dashboardDto.getRequestGroupId() != null && !dashboardDto.getRequestGroupId().equals(-1L)){
                query += " and reda.request_group_id = " + dashboardDto.getRequestGroupId();
            }
            System.out.println("DuowngTora Quá Hạn: " + query);
            long result =  ((Number)entityManager.createNativeQuery(query).getSingleResult()).longValue();
            return result;
        };
    }

    private Function<DashboardDto, Long> functionDrafting(){                                // thống kê phiếu đang soạn
        return dashboardDto -> {
            Status status_DANGSOAN = this.statusRepository.findAllByStatusCode(this.DANGSOAN).stream().findFirst().get();
            List<RequestData> requestDataList = new ArrayList<>();
            if(dashboardDto.getUserId() == null || dashboardDto.getUserId().equals(-1L))
                requestDataList = this.requestDataRepository.findAllByStatusId(status_DANGSOAN.getId());
            else
                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_DANGSOAN.getId(), dashboardDto.getUserId());
            if(dashboardDto.getRequestGroupId() == null || dashboardDto.getRequestGroupId().equals(-1L))
                return (long)requestDataList.size();
            else
                return requestDataList.stream().filter(ele -> dashboardDto.getRequestGroupId().equals(ele.getRequestGroup().getId())).count();
        };
    }

    private Function<DashboardDto, Long> functionWaitingApproval(){                     // thống kê phiếu chờ phê duyệt
        return dashboardDto -> {
            Status status_CHOPHEDUYET = this.statusRepository.findAllByStatusCode(this.CHOXULY).stream().findFirst().get();
            List<RequestData> requestDataList = new ArrayList<>();
            if(dashboardDto.getUserId() == null || dashboardDto.getUserId().equals(-1L))
                requestDataList = this.requestDataRepository.findAllByStatusId(status_CHOPHEDUYET.getId());
            else
                requestDataList = this.requestDataRepository.findAllByStatusIdAndCreatedId(status_CHOPHEDUYET.getId(), dashboardDto.getUserId());
            if(dashboardDto.getRequestGroupId() == null || dashboardDto.getRequestGroupId().equals(-1L))
                return (long)requestDataList.size();
            else
                return requestDataList.stream().filter(ele -> dashboardDto.getRequestGroupId().equals(ele.getRequestGroup().getId())).count();
        };
    }


    public Long statisticRequestData(DashboardDto dashboardDto){
//        if(dashboardDto.getRequestGroupId() == null || dashboardDto.getRequestGroupId().equals(-1L)){
////            return this.requestDataRepository.countByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(dashboardDto.getEndDate(), dashboardDto.getStartDate());
//            return this.requestDataRepository.countAllByExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(dashboardDto.getEndDate(), dashboardDto.getStartDate());
//        }else{
////            return this.requestDataRepository.countByRequestGroupIdAndCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(dashboardDto.getRequestGroupId(), dashboardDto.getEndDate(), dashboardDto.getStartDate());
//            return this.requestDataRepository.countAllByRequestGroupIdAndExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(dashboardDto.getRequestGroupId(), dashboardDto.getEndDate(), dashboardDto.getStartDate());
//        }
        dashboardDto.setEndDate(dashboardDto.getEndDate().plus(1, ChronoUnit.DAYS));
        dashboardDto.setStartDate(dashboardDto.getStartDate().truncatedTo(ChronoUnit.DAYS));
        dashboardDto.setEndDate(dashboardDto.getEndDate().truncatedTo(ChronoUnit.DAYS));
        List<RequestData> result = new ArrayList<>();
        if(dashboardDto.getRequestGroupId() == null || dashboardDto.getRequestGroupId().equals(-1L)){
            result = this.requestDataRepository.findAllByExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(dashboardDto.getEndDate(), dashboardDto.getStartDate());
        }else{
            result = this.requestDataRepository.findAllByRequestGroupIdAndExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(dashboardDto.getRequestGroupId(), dashboardDto.getEndDate(), dashboardDto.getStartDate());
        }
        if(dashboardDto.getUserId() != null || !dashboardDto.getUserId().equals(-1L)){
            return result.stream().filter(ele -> {
                return ele.getStepData().stream().filter(ele1 -> {
                    return ele1.getIsActive() == true && (ele1.getRoundNumber().equals(ele.getCurrentRound()));
                }).filter(ele2 -> {
                    return ele2.getUserInfos().stream().filter(ele3 -> ele3.getId().equals(dashboardDto.getUserId())).count() > 0;
                }).count() > 0;
            }).count();
        }
        return (long)result.size();
    }
//
//    public List<Object> statisticRequestData_V1(DashboardDto dashboardDto) throws JsonProcessingException {
//        List<Object> result = this.requestDataCustomService.staticticWithOption(Arrays.asList("createdDate"));
//        return result;
//    }

    public List<DashboardResponse> statisticRequestDataByDay(DashboardDto dashboardDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
            .withZone(ZoneId.of(ZoneOffset.UTC.getId()));
        dashboardDto.setStartDate(dashboardDto.getStartDate().truncatedTo(ChronoUnit.DAYS));
        dashboardDto.setEndDate(dashboardDto.getEndDate().truncatedTo(ChronoUnit.DAYS));
        String query = "select DATE_TRUNC('day',timezone('utc', reda.expired_time)) as date, count(*) as total From request_data reda where " +
            "DATE_TRUNC('day',timezone('utc', reda.expired_time)) between " + "'" + formatter.format(dashboardDto.getStartDate()) + "'" + " and " + "'" + formatter.format(dashboardDto.getEndDate()) + "'";
        if(dashboardDto.getRequestGroupId() != null && !dashboardDto.getRequestGroupId().equals(-1L)){
            query += " and reda.request_group_id = " + dashboardDto.getRequestGroupId();
        }
        if(dashboardDto.getUserId() != null && !dashboardDto.getUserId().equals(-1L)){
            query += " and  ((select count(rel_st_us.user_info_id) from rel_step_data__user_info rel_st_us where user_info_id = " + dashboardDto.getUserId() + " and step_data_id in (select stda.id From step_data stda where request_data_id = reda.id " +
                " and stda.is_active = true and round_number = reda.current_round)) > 0 ) ";
        }
        query += " group by DATE_TRUNC('day',timezone('utc', reda.expired_time))";
        System.out.println("DuowngTora: " + query);
        List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();

        Map<String, DashboardResponse> dateMap = new HashMap<>();
        for(Instant temp = dashboardDto.getStartDate(); temp.isBefore(dashboardDto.getEndDate()) || temp.equals(dashboardDto.getEndDate()) ; temp = temp.plus(1, ChronoUnit.DAYS)){
            String keyTemp = formatter.format(temp);
            dateMap.put(formatter.format(temp), new DashboardResponse(formatter.format(temp), 0L));
        }

        List<DashboardResponse> result = new ArrayList<>();
        for (Object[] temp : resultList) {
            DashboardResponse dashboardResponse = new DashboardResponse();
            String name = temp[0].toString();
            long count = ((Number) temp[1]).intValue();
            dashboardResponse = dateMap.get(name);
            dashboardResponse.setCount(count);
            dateMap.put(name, dashboardResponse);
            result.add(dashboardResponse);
        }
        return dateMap.values().stream().collect(Collectors.toList());
    }

    public List<DashboardResponse> statisticRequestDataStatus(DashboardDto dashboardDto){
        List<DashboardResponse> result = new ArrayList<>();
        result.add(new DashboardResponse(this.CAN_XU_LY, this.handlerStatistic.get(this.CAN_XU_LY).apply(dashboardDto)));
        result.add(new DashboardResponse(this.SAP_HET_HAN, this.handlerStatistic.get(this.SAP_HET_HAN).apply(dashboardDto)));
        result.add(new DashboardResponse(this.QUA_HAN, this.handlerStatistic.get(this.QUA_HAN).apply(dashboardDto)));
        result.add(new DashboardResponse(this.DANG_SOAN, this.handlerStatistic.get(this.DANG_SOAN).apply(dashboardDto)));
        result.add(new DashboardResponse(this.CHO_PHE_DUYET, this.handlerStatistic.get(this.CHO_PHE_DUYET).apply(dashboardDto)));
        return result;
    }


    public List<DashboardResponse> statisticMember(DashboardDto dashboardDto) throws Exception {
        if(!check(dashboardDto.getUserId(), dashboardDto.getOrganizationId()))
            throw new Exception("NOT PERMISSIION.");
        loadHandlerStatistic();
        this.getAllStatus();
        List<DashboardResponse> result = new ArrayList<>();
        Organization organization = this.organizationRepository.findById(dashboardDto.getOrganizationId()).get();
        List<UserInfo> member = organization.getUserInfos().stream().collect(Collectors.toList());
        member.forEach(ele -> {
            try {
                DashboardResponse dashboardResponse = new DashboardResponse();
                dashboardResponse.setName(ele.getFullName());
                DashboardDto dashboardDtoMember = new DashboardDto();
                BeanUtils.copyProperties(dashboardDto, dashboardDtoMember);
                dashboardDtoMember.setUserId(ele.getId());
                List<DashboardResponse> resultOfMember = statisticRequestDataStatus(dashboardDtoMember);
                dashboardResponse.setListStatistic(resultOfMember);
                result.add(dashboardResponse);
            }catch (Exception e){
                log.debug("{}", e);
            }
        });
        return result;
    }


    /**
     * Hàm  thực hiện kiểm tra xem user có quyền xem thống kê của phòng hay không
     * @param userId            : id của User cần xem thống kê
     * @param organizationId    : id của Organization muốn xem thông kê
     * @return                  : true nếu đủ điều kiện xem thống kê, false nếu không đủ điều kiện xem thống kê
     */
    public boolean check(Long userId, Long organizationId){
        try {
            UserInfo userInfo = this.userInfoRepository.findById(userId).get();
            if(userInfo.getLeader() == null) return false;                  // TH User không phải là lãnh đạo của 1 phòng nào -> false
            List<Organization> organizationList = this.organizationRepository.getALlChildOrganization(userInfo.getLeader().getId());
            if(organizationList == null || organizationList.isEmpty()) return false;
            boolean result = organizationList.stream().anyMatch(ele -> organizationId.equals(ele.getId()));
            return result;
        }catch (Exception e){
            log.debug("{}", e);
            return false;
        }
    }

}
