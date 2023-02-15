package com.vsm.business.repository;

import com.vsm.business.domain.Organization;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    @Query(nativeQuery = true,
    value = "WITH RECURSIVE organ AS(" +
        " SELECT * From organization where id = :id" +
        " UNION ALL" +
        " SELECT org.* From organization org " +
        " INNER JOIN organ on org.org_parent_id = organ.id" +
        " )SELECT * FROM organ")
    List<Organization> getALlChildOrganization(@Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true,
    value = "with RECURSIVE cte as ( " +
        " select * from organization where id = :id " +
        " union all " +
        " select org.* from organization org inner join cte on org.org_parent_id = cte.id " +
        ")update organization set is_delete = true where id in (select id from cte)")
    void deleteAllChildOrganization(@Param("id") Long id);

    List<Organization> findAllByOrganizationNameAndOrgParentId(String organizationName, Long orgParentId);

    List<Organization> findALlByOrganizationName(String organizationName);


    @Query(value = "select org.id , org.organization_code , org.organization_name" +
        ", null as \"org_parent_code\", null as \"org_parent_name\", null as \"is_sync_ad\", null as \"description\", null as \"created_name\", null as \"created_org_name\", " +
        "null as \"created_rank_name\", null as \"created_date\", null as \"modified_name\", null as \"modified_date\", null as \"is_active\", " +
        "null as \"is_delete\", null as \"tennant_code\", null as \"tennant_name\", null as \"org_parent_id\" from organization org where id in " +
        "(select organization_id From rel_process_info__organization where process_info_id = :processInfo)",nativeQuery = true)
    List<Organization> getAllOrganizationByProcessInfo(@Param("processInfo") Long processInfo);
}
