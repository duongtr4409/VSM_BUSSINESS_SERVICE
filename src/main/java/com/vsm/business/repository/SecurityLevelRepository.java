package com.vsm.business.repository;

import com.vsm.business.domain.SecurityLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityLevelRepository extends JpaRepository<SecurityLevel, Long>, JpaSpecificationExecutor<SecurityLevel> {}
