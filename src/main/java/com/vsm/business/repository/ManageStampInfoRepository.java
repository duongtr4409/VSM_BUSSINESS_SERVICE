package com.vsm.business.repository;

import com.vsm.business.domain.ManageStampInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ManageStampInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManageStampInfoRepository extends JpaRepository<ManageStampInfo, Long>, JpaSpecificationExecutor<ManageStampInfo> {
    @Query(
        value = "select distinct manageStampInfo from ManageStampInfo manageStampInfo left join fetch manageStampInfo.orgStorages",
        countQuery = "select count(distinct manageStampInfo) from ManageStampInfo manageStampInfo"
    )
    Page<ManageStampInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct manageStampInfo from ManageStampInfo manageStampInfo left join fetch manageStampInfo.orgStorages")
    List<ManageStampInfo> findAllWithEagerRelationships();

    @Query(
        "select manageStampInfo from ManageStampInfo manageStampInfo left join fetch manageStampInfo.orgStorages where manageStampInfo.id =:id"
    )
    Optional<ManageStampInfo> findOneWithEagerRelationships(@Param("id") Long id);

    List<ManageStampInfo> findAllByRequestDataId(Long requestDataId);
}
