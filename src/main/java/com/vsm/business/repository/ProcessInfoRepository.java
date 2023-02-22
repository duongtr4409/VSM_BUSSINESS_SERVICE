package com.vsm.business.repository;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.ProcessInfo;
import java.util.List;
import java.util.Optional;

import com.vsm.business.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessInfo entity.
 */
@Repository
public interface ProcessInfoRepository extends JpaRepository<ProcessInfo, Long>, JpaSpecificationExecutor<ProcessInfo> {
    @Query(
        value = "select distinct processInfo from ProcessInfo processInfo left join fetch processInfo.organizations",
        countQuery = "select count(distinct processInfo) from ProcessInfo processInfo"
    )
    Page<ProcessInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct processInfo from ProcessInfo processInfo left join fetch processInfo.organizations")
    List<ProcessInfo> findAllWithEagerRelationships();

    @Query("select processInfo from ProcessInfo processInfo left join fetch processInfo.organizations where processInfo.id =:id")
    Optional<ProcessInfo> findOneWithEagerRelationships(@Param("id") Long id);

    Page<ProcessInfo> findAllByIsDeleteIsNot(Pageable pageable, boolean isDelete);

    @Query(value = "select * from process_info " +
        "where id in (select process_info_id from rel_process_info__organization " +
        "where organization_id in (select organization_id from rel_user_info__organization " +
        "where user_info_id = :userId))",
    nativeQuery = true)
    List<ProcessInfo> getAllProcessInfoUserPermission(@Param("userId") Long userId);

//    @Query(value = "select req from Request req where :processInfo MEMBER OF req.processInfos")
//    List<Request> getAllRequestByProcessInfo(@Param("processInfo") ProcessInfo processInfo);



//    @Query(value = "select org.id, org.organizationName, org.orgParentCode From Organization org where :processInfo MEMBER OF org.processInfos")
//    List<Organization> getAllOrganizationByProcessInfo(@Param("processInfo") ProcessInfo processInfo);
}
