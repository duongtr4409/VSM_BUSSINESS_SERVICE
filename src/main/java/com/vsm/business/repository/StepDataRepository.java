package com.vsm.business.repository;

import com.vsm.business.domain.StepData;
import java.util.List;
import java.util.Optional;

import com.vsm.business.domain.StepInProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StepData entity.
 */
@Repository
public interface StepDataRepository extends JpaRepository<StepData, Long>, JpaSpecificationExecutor<StepData> {
    @Query(
        value = "select distinct stepData from StepData stepData left join fetch stepData.userInfos",
        countQuery = "select count(distinct stepData) from StepData stepData"
    )
    Page<StepData> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct stepData from StepData stepData left join fetch stepData.userInfos")
    List<StepData> findAllWithEagerRelationships();

    @Query("select stepData from StepData stepData left join fetch stepData.userInfos where stepData.id =:id")
    Optional<StepData> findOneWithEagerRelationships(@Param("id") Long id);

	List<StepData> findAllByRequestDataId(Long requestDataId);
    List<StepData> findAllByProcessDataId(Long processDataId);
    List<StepData> findAllByStepInProcess(StepInProcess stepInProcess);

    List<StepData> findAllByStepInProcessId(Long StepInProcessId);
}
