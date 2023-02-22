package com.vsm.business.repository;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestData;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.cert.ocsp.Req;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.vsm.business.domain.StepData;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

/**
 * Spring Data SQL repository for the RequestData entity.
 */
@Repository
public interface RequestDataRepository extends JpaRepository<RequestData, Long>, JpaSpecificationExecutor<RequestData>{
    @Query(
        value = "select distinct requestData from RequestData requestData left join fetch requestData.userInfos",
        countQuery = "select count(distinct requestData) from RequestData requestData"
    )
    Page<RequestData> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct requestData from RequestData requestData left join fetch requestData.userInfos")
    List<RequestData> findAllWithEagerRelationships();

    @Query("select requestData from RequestData requestData left join fetch requestData.userInfos where requestData.id =:id")
    Optional<RequestData> findOneWithEagerRelationships(@Param("id") Long id);
    List<RequestData> findAllByCreatedId(Long createdId);
    List<RequestData> findAllByCreatedId(Long createdId, Pageable pageable);
    List<RequestData> findAllByStepData(StepData stepData);
    List<RequestData> findAllByStatusIdAndCreatedId(Long statusId, Long createdId);
    List<RequestData> findAllByStatusIdAndCreatedId(Long statusId, Long createdId, Pageable pageable);
    List<RequestData> findAllByStatusIdInAndCreatedId(List<Long> statusIds, Long createdId);
    List<RequestData> findAllByStatusIdInAndCreatedId(List<Long> statusIds, Long createdId, Pageable pageable);
    List<RequestData> findAllByStatusId(Long statusId);
    List<RequestData> findAllByStatusId(Long statusId, Pageable pageable);

    List<RequestData> findAllByStatusIdIn(List<Long> statusIds);
    List<RequestData> findAllByStatusIdIn(List<Long> statusIds, Pageable pageable);
    List<RequestData> findAllByReqDataConcernedId(Long reqDataConcernedId);

    //  \\

    // lấy tất cả \\
    // danh sách phiếu mà người dùng có trong quy trình
    @Query(value = "select * from request_data reda " +
        "where reda.id in (select DISTINCT request_data_id from rel_request_data__user_info where user_info_id = :userId) " +
        "UNION " +
        "select reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllByUserId(@Param("userId") Long userId);

    @Query(value = "select count(temp.id) from(" +
        "select reda.id from request_data reda  " +
        "where reda.id in (select DISTINCT request_data_id from rel_request_data__user_info where user_info_id = :userId) " +
        "UNION " +
        "select reda.id From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and (:userId = - 1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId))) as temp ", nativeQuery = true)
    Long countAllByUserId(@Param("userId") Long userId);

    // phiếu của người dùng
    @Query(value = "select distinct * from request_data where :userId = -1 or created_id = :userId", nativeQuery = true)
    List<RequestData> getAllMe(@Param("userId") Long userId);

    @Query(value = "select distinct count(1) from request_data where :userId = -1 or created_id = :userId", nativeQuery = true)
    List<RequestData> countAllMe(@Param("userId") Long userId);
    // \\

    // lấy danh sách phiếu đang cần xử lý \\
    // danh sách phiếu mà người dùng có trong quy trình
    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and (:userId = -1 or :userId not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id)) ", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandle(@Param("userId") Long userId);

    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and (:userId = -1 or :userId not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id)) " +
        "order by modified_date desc limit :limit offset :offset", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandle(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit);

    @Query(value = "select count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and (:userId = -1 or :userId not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id)) " , nativeQuery = true)
    Long countAllRequestDataNeedHandle(@Param("userId") Long userId);
    // \\

    // lấy danh sách phiếu quá hạn xử lý \\
    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and stda.processing_term_time < timezone('utc', now()) ", nativeQuery = true)
    List<RequestData> getAllRequestDataOverDue(@Param("userId") Long userId);

    @Query(value = "select count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId))" +
        "and stda.processing_term_time < timezone('utc', now()) ", nativeQuery = true)
    Long countAllRequestDataOverDue(@Param("userId") Long userId);

    // \\


    // lấy danh sách phiếu sắp hết hạn \\
//    @Query(value = "select DISTINCT reda.* From request_data reda " +
//        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
//        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
//        "where stda.processing_term_time >= timezone('utc', now()) and stda.processing_term_time <= timezone('utc', now()) + 0.8*stda.processing_term*interval'1 HOUR' ", nativeQuery = true)
        // thay đổi do phiếu sắp hết hạn sẽ là 1 ngày trước khi hết hạn \\
    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "where stda.processing_term_time >= timezone('utc', now()) and stda.processing_term_time <= timezone('utc', now()) + interval'24 HOUR' ", nativeQuery = true)
    List<RequestData> getAllRequestDataAboutToExpire(@Param("userId") Long userId);

//    @Query(value = "select DISTINCT count(1) From request_data reda " +
//        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
//        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
//        "where stda.processing_term_time >= timezone('utc', now()) and stda.processing_term_time <= timezone('utc', now()) + 0.8*stda.processing_term*interval'1 HOUR' ", nativeQuery = true)
        // thay đổi do phiếu sắp hết hạn sẽ là 1 ngày trước khi hết hạn \\
    @Query(value = "select  count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "where stda.processing_term_time >= timezone('utc', now()) and stda.processing_term_time <= timezone('utc', now()) + interval'24 HOUR' ", nativeQuery = true)

    Long countAllRequestDataAboutToExpire(@Param("userId") Long userId);
    // \\


    // lấy danh sách phiếu được chia sẻ \\
    @Query(value = "select * From request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where :userId = -1 or user_info_id = :userId) " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUser(@Param("userId") Long userId);

    @Query(value = "select * From request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where :userId = -1 or user_info_id = :userId) " +
        "order by created_date desc limit :limit offset :offset", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUser(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit);

    @Query(value = "select count(1) From request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where :userId = -1 or user_info_id = :userId ) ", nativeQuery = true)
    Long countAllRequestDataSharedToUser(@Param("userId") Long userId);
    // \\


    // lấy danh sách đang trong thời hạn xử lý \\
    @Query(value = "select DISTINCT reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and ( :userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "where stda.processing_term_time >= timezone('utc', now()) ", nativeQuery = true)
    List<RequestData> getAllRequestDataInProcessingTime(@Param("userId") Long userId);

    @Query(value = "select count(DISTINCT reda.id) from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and ( :userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "where stda.processing_term_time >= timezone('utc', now()) ", nativeQuery = true)
    Long countAllRequestDataInProcessingTime(@Param("userId") Long userId);
    // \\


    // lấy danh sách phiếu đang soạn \\
    // phiếu của người dùng
    @Query(value = "select distinct reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id  " +
        "and (stda.is_active = false or stda.is_active is null) " +
        "where :userId = - 1 or reda.created_id = :userId order by reda.created_date desc", nativeQuery = true)
    List<RequestData> getAllRequestDataDrafting(@Param("userId") Long userId);

    @Query(value = "select count(distinct reda.id) from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id  " +
        "and (stda.is_active = false or stda.is_active is null) " +
        "where :userId = -1 or reda.created_id = :userId", nativeQuery = true)
    Long countAllRequestDataDrafting(@Param("userId") Long userId);
    // \\

    // lấy danh sách phiếu đang theo dõi \\
    @Query(value = "select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (:userId = -1 or stda.id in (select step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "union " +
        "select * From request_data where :userId = -1 or created_id = :userId " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowing(@Param("userId") Long userId);

    @Query(value = "select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (:userId = -1 or stda.id in (select step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "union " +
        "select * From request_data where :userId = -1 or created_id = :userId " +
        "order by created_date desc limit :limit offset :offset ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowing(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit);

    @Query(value = "select count(temp.id) from ( " +
        "select reda.id from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (:userId = -1 or stda.id in (select step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "union " +
        "select id From request_data where :userId = -1 or created_id = :userId) as temp", nativeQuery = true)
    Long countAllRequestDataFollowing(@Param("userId") Long userId);
    // \\


    @Query(value = "select max(temp.stt) from( " +
        "select " +
        "Cast(nullif(regexp_replace(Cast(REGEXP_MATCHES(re.request_data_code, '\\d{10}', 'g') as VARCHAR), '\\D', '', 'g') " +
        ", '') as numeric) as stt from request_data re where re.request_id = :requestId) as temp ", nativeQuery = true)
    Long getMaxSTTByRequetId(@Param("requestId") Long requestId);



                            // thống kê DashBoard \\
    List<RequestData> findAllByRequestGroupIdAndCreatedDateLessThanEqualAndCreatedGreaterThanEqual(Long requetGroupId, Instant startDate, Instant endDate);
    Long countByRequestGroupIdAndCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(Long requestGroupId, Instant endDate, Instant startDate);
    Long countByCreatedDateLessThanEqualAndCreatedDateGreaterThanEqual(Instant endDate, Instant startDate);

    List<RequestData> findAllByExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(Instant endDate, Instant startDate);
    Long countAllByExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(Instant endDate, Instant startDate);

    List<RequestData> findAllByRequestGroupIdAndExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(Long requestGroupId, Instant endDate, Instant startDate);
    Long countAllByRequestGroupIdAndExpiredTimeLessThanEqualAndExpiredTimeGreaterThanEqual(Long requestGroupId, Instant endDate, Instant startDate);



                            // có phân quyền \\
            // đang soạn \\ + // bị trả lại \\
    List<RequestData> findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(Long statusId, Long createdId, List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(Long statusId, List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByStatusIdAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(Long statusId, Long createdId, List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);
    List<RequestData> findAllByStatusIdAndRequestIdInAndCreatedOrganizationsIn(Long statusId, List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);

            // cần xử lý \\
    // lấy danh sách phiếu đang cần xử lý \\
    // danh sách phiếu mà người dùng có trong quy trình
    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and (:userId = -1 or :userId not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id)) where reda.request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in :organizationIds))", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandleWithRole(@Param("userId") Long userId, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    @Query(value = "select DISTINCT reda.* From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true and (:userId = -1 or stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "and (:userId = -1 or :userId not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id)) where reda.request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in :organizationIds)) " +
        "order by modified_date desc limit :limit offset :offset", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandleWithRole(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu đang cần xử lý của người thuộc những phòng ban và loại yêu cầu người dùng có quyền xem
    @Query(value = "select DISTINCT reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true  " +
        "and ( stda.id in " +
        "    (select step_data_id from rel_step_data__user_info where " +
        "        user_info_id in " +
        "            (select user_info_id from rel_user_info__organization where organization_id in :organizationIds) " +
        "            and user_info_id not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id) " +
        "    ) " +
        ") and reda.request_id in :requestIds order by modified_date desc " +
        "", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandleWithRole(@Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu đang cần xử lý của người thuộc những phòng ban và loại yêu cầu người dùng có quyền xem
    @Query(value = "select DISTINCT reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true  " +
        "and ( stda.id in " +
        "    (select step_data_id from rel_step_data__user_info where " +
        "        user_info_id in " +
        "            (select user_info_id from rel_user_info__organization where organization_id in :organizationIds) " +
        "            and user_info_id not in (select excutor_id from result_of_step reost where reost.step_data_id = stda.id) " +
        "    ) " +
        ") and reda.request_id in :requestIds order by modified_date desc limit :limit offset :offset " +
        "", nativeQuery = true)
    List<RequestData> getAllRequestDataNeedHandleWithRole(@Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

            // đang theo dõi \\
    // lấy danh sách phiếu đang theo dõi \\
    @Query(value = "select result.* from (select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (:userId = -1 or stda.id in (select step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "union " +
        "select * From request_data where :userId = -1 or created_id = :userId) as result " +
        "where request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = result.created_id and organization_id in :organizationIds)) " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowingWithRole(@Param("userId") Long userId, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    @Query(value = "select result.* from (select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (:userId = -1 or stda.id in (select step_data_id from rel_step_data__user_info where user_info_id = :userId)) " +
        "union " +
        "select * From request_data where :userId = -1 or created_id = :userId) as result " +
        "where request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = result.created_id and organization_id in :organizationIds)) " +
        "order by created_date desc limit :limit offset :offset ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowingWithRole(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu đang theo dõi của người thuộc những phòng ban và loại yêu cầu người dùng có quyền xem\\
    @Query(value = "select distinct result.* from ( " +
        "select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (stda.id in (select step_data_id from rel_step_data__user_info where user_info_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds))) " +
        "union " +
        "select * from request_data where created_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds) " +
        ") as result where request_id in :requestIds " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowingWithRole(@Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu đang theo dõi của người thuộc những phòng ban và loại yêu cầu người dùng có quyền xem\\
    @Query(value = "select distinct result.* from ( " +
        "select reda.* from request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id " +
        "and (stda.id in (select step_data_id from rel_step_data__user_info where user_info_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds))) " +
        "union " +
        "select * from request_data where created_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds) " +
        ") as result where request_id in :requestIds " +
        "order by created_date desc limit :limit ofset :offset ", nativeQuery = true)
    List<RequestData> getAllRequestDataFollowingWithRole(@Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);


    // chờ phê duyệt \\
    List<RequestData> findAllByStatusIdInAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(List<Long> statusIds, Long createdId, List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByStatusIdInAndRequestIdInAndCreatedOrganizationsIn(List<Long> statusIds, List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByStatusIdInAndCreatedIdAndRequestIdInAndCreatedOrganizationsIn(List<Long> statusIds, Long createdId, List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);
    List<RequestData> findAllByStatusIdInAndRequestIdInAndCreatedOrganizationsIn(List<Long> statusIds, List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);

            // được chia sẻ \\
    // lấy danh sách phiếu được chia sẻ \\
    @Query(value = "select * From request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where :userId = -1 or user_info_id = :userId) " +
        "and request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = result.created_id and organization_id in :organizationIds)) " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUserWithRole(@Param("userId") Long userId, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);
    @Query(value = "select * From request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where :userId = -1 or user_info_id = :userId) " +
        "and request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = result.created_id and organization_id in :organizationIds)) " +
        "order by created_date desc limit :limit offset :offset", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUserWithRole(@Param("userId") Long userId, @Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu được chia sẻ cho người thuộc phòng ban và loại yêu cầu thuộc quyền xem của người dùng \\
    @Query(value = "select * from request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where user_info_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds)) " +
        "and request_id in :requestIds " +
        "order by created_date desc ", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUserWithRole(@Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    // lấy danh sách phiếu yêu cầu được chia sẻ cho người thuộc phòng ban và loại yêu cầu thuộc quyền xem của người dùng \\
    @Query(value = "select * from request_data " +
        "where id in (select DISTINCT request_data_id from rel_request_data__user_info where user_info_id in (select user_info_id from rel_user_info__organization where organization_id in :organizationIds)) " +
        "and request_id in :requestIds " +
        "order by created_date desc limit :limit offset :offset ", nativeQuery = true)
    List<RequestData> getAllRequestDataSharedToUserWithRole(@Param("offset") Long offset, @Param("limit") Long limit, @Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);



    // phiếu của người dùng \\
    List<RequestData> findAllByCreatedIdAndRequestIdInAndCreatedOrganizationsIn(Long createdId, List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByRequestIdInAndCreatedOrganizationsIn(List<Long> requestSet, List<Organization> organizationSet);
    List<RequestData> findAllByCreatedIdAndRequestIdInAndCreatedOrganizationsIn(Long createdId, List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);
    List<RequestData> findAllByRequestIdInAndCreatedOrganizationsIn(List<Long> requestSet, List<Organization> organizationSet, Pageable pageable);


            // danh sách phiếu theo quyền \\
    @Query(value = "select DISTINCT reda.* from request_data reda " +
        "where request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in :organizationIds)) " +
        "order by modified_date desc ",
        nativeQuery = true)
    List<RequestData> getAllOfRole(@Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds);

    @Query(value = "select DISTINCT reda.* from request_data reda " +
        "where request_id in :requestIds " +
        "and (select EXISTS(select 1 from rel_user_info__organization where user_info_id = reda.created_id and organization_id in :organizationIds)) " +
        "order by modified_date desc limt :limit offset :offset",
        nativeQuery = true)
    List<RequestData> getAllOfRole(@Param("requestIds") Set<Long> requestIds, @Param("organizationIds") Set<Long> organizationIds, @Param("offset") Long offset, @Param("limit") Long limit);

    // thống kê \\
    @Query(value = ":query"
        , nativeQuery = true)
    List<RequestData> executeQuery(@Param("query") String query, Pageable pageable);

    @Query(value = ":query"
        , nativeQuery = true)
    List<RequestData> executeQuery(@Param("query") String query);

    @Query(value = ":query"
        , nativeQuery = true)
    Long executeCountQuery(@Param("query") String query);

    // cảnh báo \\

    /**
     * Hàm thực hiện lấy danh sách phiếu yêu cầu quá thời gian hết hạn xử lý 2 ngày(dateWaring ngày)
     * @param dateWaring
     * @return
     */
    @Query(value = "select * from request_data where date_trunc('day', expired_time) + interval'2' day <= date_trunc('day', timezone('utc', now()))", nativeQuery = true)
    List<RequestData> getAllRequestDataWarningOutOfDate(@Param("dateWaring") Long dateWaring);

    // đồng bộ SAP \\

    /**
     * hàm thực hiện lấy danh sách các phiếu yêu cầu đồng bộ sang SAP bị Failed
     * @return: danh sách id của các phiếu yêu cầu đồng bộ sang SAP bị Failed
     */
    @Query(value = "select id from request_data where contract_number is not null and (result_sync_contract is null or result_sync_contract = false)", nativeQuery = true)
    List<Long> getAllRequestDataSyncSAPFailed();


    // dashboard theo đơn vị \\
    List<RequestData> findAllByStatusIdAndCreatedOrganizationsIn(Long statusId, List<Organization> organizationSet);
    List<RequestData> findAllByStatusIdInAndCreatedOrganizationsIn(List<Long> statusIds, List<Organization> organizationSet);

    @Query(value = "select  count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id " +
        "in (select user_info_id from rel_user_info__organization where organization_id in ( :organizations ))))" +
        "where stda.processing_term_time >= timezone('utc', now()) and stda.processing_term_time <= timezone('utc', now()) + interval'24 HOUR' ", nativeQuery = true)
    Long countAllRequestDataAboutToExpireOfOrganizations(@Param("organizations") List<Long> organizations);

    @Query(value = "select count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id in " +
        "(select user_info_id from rel_user_info__organization where organization_id in ( :organizations )))) " +
        "and stda.processing_term_time < timezone('utc', now()) ", nativeQuery = true)
    Long countAllRequestDataOverDueOfOrganizations(@Param("organizations") List<Long> organizations);

    @Query(value = "select count(DISTINCT reda.id) From request_data reda " +
        "inner join step_data stda on reda.id = stda.request_data_id and stda.is_active = true " +
        "and (stda.id in (select DISTINCT step_data_id from rel_step_data__user_info where user_info_id in " +
        "(select user_info_id from rel_user_info__organization where organization_id in ( :organizations )))) " +
        "and (select NOT EXISTS(select excutor_id from result_of_step reost where reost.step_data_id = stda.id " +
        "and excutor_id in (select user_info_id from rel_user_info__organization where organization_id in ( :organizations )))) " , nativeQuery = true)
    Long countAllRequestDataNeedHandleOfOrganizations(@Param("organizations") List<Long> organizations);
}
