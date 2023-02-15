package com.vsm.business.repository;

import com.vsm.business.domain.PriorityLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PriorityLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriorityLevelRepository extends JpaRepository<PriorityLevel, Long>, JpaSpecificationExecutor<PriorityLevel> {}
