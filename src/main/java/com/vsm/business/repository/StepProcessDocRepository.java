package com.vsm.business.repository;

import com.vsm.business.domain.StepProcessDoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StepProcessDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepProcessDocRepository extends JpaRepository<StepProcessDoc, Long>, JpaSpecificationExecutor<StepProcessDoc> {}
