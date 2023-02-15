package com.vsm.business.repository;

import com.vsm.business.domain.Step;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Step entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepRepository extends JpaRepository<Step, Long>, JpaSpecificationExecutor<Step> {}
