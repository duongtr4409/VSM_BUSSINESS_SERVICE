package com.vsm.business.repository;

import com.vsm.business.domain.OfficialDispatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OfficialDispatch entity.
 */
@Repository
public interface OfficialDispatchRepository extends JpaRepository<OfficialDispatch, Long>, JpaSpecificationExecutor<OfficialDispatch> {
    @Query(
        value = "select distinct officialDispatch from OfficialDispatch officialDispatch left join fetch officialDispatch.offDispatchUserReads",
        countQuery = "select count(distinct officialDispatch) from OfficialDispatch officialDispatch"
    )
    Page<OfficialDispatch> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct officialDispatch from OfficialDispatch officialDispatch left join fetch officialDispatch.offDispatchUserReads")
    List<OfficialDispatch> findAllWithEagerRelationships();

    @Query(
        "select officialDispatch from OfficialDispatch officialDispatch left join fetch officialDispatch.offDispatchUserReads where officialDispatch.id =:id"
    )
    Optional<OfficialDispatch> findOneWithEagerRelationships(@Param("id") Long id);
}
