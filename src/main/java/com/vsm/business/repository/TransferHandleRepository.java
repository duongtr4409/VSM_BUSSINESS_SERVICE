package com.vsm.business.repository;

import com.vsm.business.domain.TransferHandle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransferHandle entity.
 */
@Repository
public interface TransferHandleRepository extends JpaRepository<TransferHandle, Long>, JpaSpecificationExecutor<TransferHandle> {
    @Query(
        value = "select distinct transferHandle from TransferHandle transferHandle left join fetch transferHandle.receiversHandles",
        countQuery = "select count(distinct transferHandle) from TransferHandle transferHandle"
    )
    Page<TransferHandle> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct transferHandle from TransferHandle transferHandle left join fetch transferHandle.receiversHandles")
    List<TransferHandle> findAllWithEagerRelationships();

    @Query(
        "select transferHandle from TransferHandle transferHandle left join fetch transferHandle.receiversHandles where transferHandle.id =:id"
    )
    Optional<TransferHandle> findOneWithEagerRelationships(@Param("id") Long id);

    List<TransferHandle> findAllByStepDataId(Long stepDataId);
}
