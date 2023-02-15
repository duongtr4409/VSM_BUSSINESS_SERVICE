package com.vsm.business.service.custom.statistic;

import com.vsm.business.domain.*;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.custom.statistic.bo.StatisticOption;
import com.vsm.business.service.dto.FieldInFormDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.ObjectUtils;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticCustomService {

    private final Logger log = LoggerFactory.getLogger(StatisticCustomService.class);

    private final UserInfoRepository userInfoRepository;

    private final RequestRepository requestRepository;

    private final RequestDataRepository requestDataRepository;

    private final AuthenticateUtils authenticateUtils;

    private final ObjectUtils objectUtils;

    private final UserUtils userUtils;

    @Autowired
    private EntityManager entityManager;

    public StatisticCustomService(UserInfoRepository userInfoRepository, RequestRepository requestRepository, RequestDataRepository requestDataRepository, AuthenticateUtils authenticateUtils, ObjectUtils objectUtils, UserUtils userUtils) {
        this.userInfoRepository = userInfoRepository;
        this.requestRepository = requestRepository;
        this.requestDataRepository = requestDataRepository;
        this.authenticateUtils = authenticateUtils;
        this.objectUtils = objectUtils;
        this.userUtils = userUtils;
    }

    /**
     * Hàm thực hiện lấy danh sách loại yêu cầu(request) ban được phân quyền của user
     * @param userId    : id của user
     * @return  : danh sách loại yêu cầu(request) mà user có quyền
     */
    public Set<RequestDTO> getAllRequestWithRole(Long userId){
        Set<RequestDTO> result = new HashSet<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getRoleRequests().stream().map(ele1 -> {
                return this.convertToDto(ele1.getRequest());
            }).collect(Collectors.toSet()));
        });
        log.debug("StatisticCustomService: getAllRequestWithRole({}): {}", userId, result);
        return result;
    }

    /**
     * Hàm thực hiện lấy danh sách phòng ban được phân quyền của user
     * @param userId    : id của user
     * @return  : danh sách phòng ban mà user có quyền
     */
    public Set<OrganizationDTO> getAllOrganizationWithRole(Long userId){
        Set<OrganizationDTO> result = new HashSet<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getRoleOrganizations().stream().map(ele1 -> {
                return this.convertToDto(ele1.getOrganization());
            }).collect(Collectors.toSet()));
        });
        log.debug("StatisticCustomService: getAllOrganizationWithRole({}): {}", userId, result);
        return result;
    }

    /**
     * Hàm thực hiện lấy danh sách phòng ban được phân quyền của user
     * @param userId    : id của user
     * @return  : danh sách phòng ban mà user có quyền
     */
    public Set<Organization> getAllOrganizationWithRoleEntity(Long userId){
        Set<Organization> result = new HashSet<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getRoleOrganizations().stream().map(ele1 -> {
                return ele1.getOrganization();
            }).collect(Collectors.toSet()));
        });
        log.debug("StatisticCustomService: getAllOrganizationWithRoleEntity({}): {}", userId, result);
        return result;
    }

    /**
     * Hàm thực hiện lấy danh sách fieldInForm của loại yêu cầu (Request)
     * @param requestId : id của Request
     * @return  : danh sách fieldInForm của loại yêu cầu (Request)
     */
    public List<FieldInFormDTO> getAllFieldInFormWithRequest(Long requestId){
        List<FieldInFormDTO> result = this.requestRepository.findById(requestId).get().getForm().getFieldInForms().stream().map(ele -> {
            return this.convertToDto(ele);
        }).collect(Collectors.toList());
        log.debug("StatisticCustomService: getAllFieldInFormWithRequest({}): {}", requestId, result);
        return result;
    }

    public List<RequestDataDTO> getRequestData(StatisticOption statisticOption, Pageable pageable){
        pageable = this.getDefaultSort(pageable);
        if((statisticOption.getRequestId() == null || statisticOption.getRequestId() <= 0)
            && (statisticOption.getOrganizationIds() == null || statisticOption.getOrganizationIds().isEmpty()) ){
            return this.findAllRequestDataOfUser(this.userUtils.getCurrentUser().getId(), pageable);
        }else{
            StringBuilder query = new StringBuilder("select DISTINCT(reda.*) From request_data reda where 1=1 ");
            if(statisticOption.getRequestId() != null && statisticOption.getRequestId() > 0){
                query.append(" and request_id = " + statisticOption.getRequestId());
            }
            if(statisticOption.getOrganizationIds() != null && !statisticOption.getOrganizationIds().isEmpty()){
                query.append(" and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in " +
                    statisticOption.getOrganizationIds().stream().map(ele -> ele.toString()).collect(Collectors.joining(", ", "(", ") ")));
                query.append(" ))");
            }
            if(statisticOption.getCompareDtos() != null && !statisticOption.getCompareDtos().isEmpty()){
                String subQuery = " and (select EXISTS( \n" +
                    "\t select 1 from field_data where request_data_id = reda.id {{SUB_QUERY_2}} )) ";

                StringBuilder subQuery2 = new StringBuilder("");
                int n = statisticOption.getCompareDtos().size();
                for(int i=0; i<n; i++){
                    subQuery2.append(" and ");
                    subQuery2.append(this.buildQuery(statisticOption.getCompareDtos().get(i)));
                }

                subQuery = subQuery.replace("{{SUB_QUERY_2}}", subQuery2.toString());
                query.append(subQuery);
//                query.append(" order by modified_date desc ");
//                log.info("DuowngTora statisticQuery: {}", query.toString());
            }
            query.append(" order by modified_date desc ");
            log.info("DuowngTora statisticQuery: {}", query.toString());

            Query nativeQuery = entityManager.createNativeQuery(query.toString(), RequestData.class).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
            return this.convertToDTO(nativeQuery.getResultList());
//            return this.convertToDTO(this.requestDataRepository.executeQuery(query.toString(), pageable));
        }
    }

    public List<RequestDataDTO> getRequestData(StatisticOption statisticOption){
        if((statisticOption.getRequestId() == null || statisticOption.getRequestId() <= 0)
            && (statisticOption.getOrganizationIds() == null || statisticOption.getOrganizationIds().isEmpty()) ){
            return this.findAllRequestDataOfUser(this.userUtils.getCurrentUser().getId());
        }else{
            StringBuilder query = new StringBuilder("select DISTINCT(reda.*) From request_data reda where 1=1 ");
            if(statisticOption.getRequestId() != null && statisticOption.getRequestId() > 0){
                query.append(" and request_id = " + statisticOption.getRequestId());
            }
            if(statisticOption.getOrganizationIds() != null && !statisticOption.getOrganizationIds().isEmpty()){
                query.append(" and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in " +
                    statisticOption.getOrganizationIds().stream().map(ele -> ele.toString()).collect(Collectors.joining(", ", "(", ") ")));
                query.append(" ))");
            }
            if(statisticOption.getCompareDtos() != null && !statisticOption.getCompareDtos().isEmpty()){
                String subQuery = " and (select EXISTS( \n" +
                    "\t select 1 from field_data where request_data_id = reda.id {{SUB_QUERY_2}} )) ";

                StringBuilder subQuery2 = new StringBuilder("");
                int n = statisticOption.getCompareDtos().size();
                for(int i=0; i<n; i++){
                    subQuery2.append(" and ");
                    subQuery2.append(this.buildQuery(statisticOption.getCompareDtos().get(i)));
                }

                subQuery = subQuery.replace("{{SUB_QUERY_2}}", subQuery2.toString());
                query.append(subQuery);
//                query.append(" order by modified_date desc ");
//                log.info("DuowngTora statisticQuery: {}", query.toString());
            }
            query.append(" order by modified_date desc ");
            log.info("DuowngTora statisticQuery: {}", query.toString());

            Query nativeQuery = entityManager.createNativeQuery(query.toString(), RequestData.class);
            return this.convertToDTO(nativeQuery.getResultList());
//            return this.convertToDTO(this.requestDataRepository.executeQuery(query.toString()));
        }
    }

    public Long countRequestData(StatisticOption statisticOption){
        if((statisticOption.getRequestId() == null || statisticOption.getRequestId() <= 0)
            && (statisticOption.getOrganizationIds() == null || statisticOption.getOrganizationIds().isEmpty()) ){
            return this.findAllRequestDataOfUser(this.userUtils.getCurrentUser().getId()).stream().count();
        }else{
            StringBuilder query = new StringBuilder("select count(DISTINCT(reda.*)) From request_data reda where 1=1 ");
            if(statisticOption.getRequestId() != null && statisticOption.getRequestId() > 0){
                query.append(" and request_id = " + statisticOption.getRequestId());
            }
            if(statisticOption.getOrganizationIds() != null && !statisticOption.getOrganizationIds().isEmpty()){
                query.append(" and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in " +
                    statisticOption.getOrganizationIds().stream().map(ele -> ele.toString()).collect(Collectors.joining(", ", "(", ") ")));
                query.append(" ))");
            }
            if(statisticOption.getCompareDtos() != null && !statisticOption.getCompareDtos().isEmpty()){
                String subQuery = " and (select EXISTS( \n" +
                    "\t select 1 from field_data where request_data_id = reda.id {{SUB_QUERY_2}} )) ";

                StringBuilder subQuery2 = new StringBuilder("");
                int n = statisticOption.getCompareDtos().size();
                for(int i=0; i<n; i++){
                    subQuery2.append(" and ");
                    subQuery2.append(this.buildQuery(statisticOption.getCompareDtos().get(i)));
                }

                subQuery = subQuery.replace("{{SUB_QUERY_2}}", subQuery2.toString());
                query.append(subQuery);
                log.info("DuowngTora statisticQuery: {}", query.toString());
            }
            Query nativeQuery = entityManager.createNativeQuery(query.toString());
            return ((Number)entityManager.createNativeQuery(query.toString()).getSingleResult()).longValue();
//            return this.requestDataRepository.executeCouuntQuery(query.toString());
        }
    }

    private String buildQuery(StatisticOption.CompareDto compareDto){
        String result = " field_data_code = '{{FIELD_DATA_CODE}}' and " +
            " ( (tennant_code in ('number', 'double') and \n" +
            "\t\t case \n" +
            "\t\t\t when object_model ~ '^[0-9\\.]+$' and tennant_code = 'number' then REPLACE(object_model, '.', '')\n" +
            "\t\t\t when object_model ~ '^[0-9\\.]+$' and tennant_code = 'double' then object_model\n" +
            "\t\t\t else null\n" +
            "\t\t end \\:\\:DECIMAL {{OPERATOR_1}} {{VALUE_1}}\n" +
            "\t ) or (\n" +
            "\t\t tennant_code not in ('number', 'double') and \n" +
            "\t\t upper(case\n" +
            "\t\t\t when object_model is not null then object_model\n" +
            "\t\t\t else '' \n" +
            "\t\t end) {{OPERATOR_2}} {{VALUE_2}} \n" +
            "\t )\n" +
            "\t ) ";
        if(compareDto.operator.equals(StatisticOption.Operator.CONTAIN)){                 // TH là like -> cần sửa lại query (do postgres ko hỗ trợ)
            result = result.replace("{{VALUE_2}}", "'%" + compareDto.value.toUpperCase() + "%'");
                        // fake query 1 -> để query 1 sai
            result = result.replace("{{OPERATOR_1}}", "=" );
            result = result.replace("{{VALUE_1}}", "1 and 1!=1 ");
                        // end fake query 1 -> để query 1 sai
        }else{
            result = result.replace("{{VALUE_1}}", "'" + compareDto.value + "'");
            result = result.replace("{{VALUE_2}}", "'" + compareDto.value + "'");
        }
        result = result.replace("{{FIELD_DATA_CODE}}", compareDto.fieldCode);
        result = result.replace("{{OPERATOR_1}}", compareDto.operator.getOperator());
        result = result.replace("{{OPERATOR_2}}", compareDto.operator.getOperator());

        return result;
    }

    public List<RequestDataDTO> findAllRequestDataOfUser(Long userId){
        List<RequestData> requestDataList = this.requestDataRepository.findAllByRequestIdInAndCreatedOrganizationsIn(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW));
        return convertToDTO(requestDataList);
    }

    public List<RequestDataDTO> findAllRequestDataOfUser(Long userId, Pageable pageable){
        List<RequestData> requestDataList = this.requestDataRepository.findAllByRequestIdInAndCreatedOrganizationsIn(this.authenticateUtils.getRequestForUserWithHasAction(userId, AuthenticateUtils.VIEW), this.authenticateUtils.getOrganizationForUserWithHasAction(userId, AuthenticateUtils.VIEW), pageable);
        return convertToDTO(requestDataList);
    }


    public Map<String, Boolean> getPermissionOfUserInRequestData(Long userId, Long requestDataId){
        Map<String, Boolean> result = new HashMap<>();
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        // kiểm tra quyền xem \\
        result.put(AuthenticateUtils.VIEW, userInfo.getRoles().stream().anyMatch(ele ->
            (ele.getRoleRequests().stream().anyMatch(ele1 -> ele1.getRequest().getId().equals(requestData.getRequest().getId()) && ele1.getIsView() != null && ele1.getIsView() == true))
                ||
                (ele.getRoleOrganizations().stream().anyMatch(ele1 -> requestData.getCreated().getOrganizations().stream().anyMatch(ele2 -> ele2.getId().equals(ele1.getOrganization().getId()) && ele1.getIsView() != null && ele1.getIsView() == true)))
        ));
        // kiểm tra quyền sửa \\
        result.put(AuthenticateUtils.EDIT, userInfo.getRoles().stream().anyMatch(ele ->
            (ele.getRoleRequests().stream().anyMatch(ele1 -> ele1.getRequest().getId().equals(requestData.getRequest().getId()) && ele1.getIsEdit() != null && ele1.getIsEdit() == true))
                ||
                (ele.getRoleOrganizations().stream().anyMatch(ele1 -> requestData.getCreated().getOrganizations().stream().anyMatch(ele2 -> ele2.getId().equals(ele1.getOrganization().getId()) && ele1.getIsEdit() != null && ele1.getIsEdit() == true)))
        ));
        // kiểm tra quyền xóa \\
        result.put(AuthenticateUtils.DELETE, userInfo.getRoles().stream().anyMatch(ele ->
            (ele.getRoleRequests().stream().anyMatch(ele1 -> ele1.getRequest().getId().equals(requestData.getRequest().getId()) && ele1.getIsDelete() != null && ele1.getIsDelete() == true))
                ||
                (ele.getRoleOrganizations().stream().anyMatch(ele1 -> requestData.getCreated().getOrganizations().stream().anyMatch(ele2 -> ele2.getId().equals(ele1.getOrganization().getId()) && ele1.getIsDelete() != null && ele1.getIsDelete() == true)))
        ));
        // kiểm tra quyền tải \\
        result.put(AuthenticateUtils.DOWNLOAD, userInfo.getRoles().stream().anyMatch(ele ->
            (ele.getRoleRequests().stream().anyMatch(ele1 -> ele1.getRequest().getId().equals(requestData.getRequest().getId()) && ele1.getIsDownload() != null && ele1.getIsDownload() == true))
                ||
                (ele.getRoleOrganizations().stream().anyMatch(ele1 -> requestData.getCreated().getOrganizations().stream().anyMatch(ele2 -> ele2.getId().equals(ele1.getOrganization().getId()) && ele1.getIsDownload() != null && ele1.getIsDownload() == true)))
        ));

        return result;
    }

        // utils \\
    private RequestDTO convertToDto(Request request){
        if(request == null) return null;
        RequestDTO requestDTO = new RequestDTO();
        try {
            return this.objectUtils.coppySimpleType(request, requestDTO, RequestDTO.class);
        }catch (Exception e){
            log.error("{}", e);
            return requestDTO;
        }
    }

    private OrganizationDTO convertToDto(Organization organization){
        if(organization == null) return null;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        try {
            return this.objectUtils.coppySimpleType(organization, organizationDTO, OrganizationDTO.class);
        }catch (Exception e){
            log.error("{}", e);
            return organizationDTO;
        }
    }

    private FieldInFormDTO convertToDto(FieldInForm fieldInForm){
        if(fieldInForm == null) return null;
        FieldInFormDTO fieldInFormDTO = new FieldInFormDTO();
        try {
            return this.objectUtils.coppySimpleType(fieldInForm, fieldInFormDTO, FieldInFormDTO.class);
        }catch (Exception e){
            log.error("{}", e);
            return fieldInFormDTO;
        }
    }

    private RequestDataDTO convertToDTO(RequestData requestData) throws IllegalAccessException {
        RequestDataDTO requestDataDTO = new RequestDataDTO();
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
