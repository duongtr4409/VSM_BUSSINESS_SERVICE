package com.vsm.business.repository;

import com.vsm.business.domain.StepInProcess;
import com.vsm.business.domain.UserInStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the StepInProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepInProcessRepository extends JpaRepository<StepInProcess, Long>, JpaSpecificationExecutor<StepInProcess> {
    List<StepInProcess> findAllByProcessInfoId(Long processInfoId);
    List<StepInProcess> findAllByUserInSteps(UserInStep userInStep);
}
