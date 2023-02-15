package com.vsm.business.repository;

import com.vsm.business.domain.OfficialDispatchStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OfficialDispatchStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficialDispatchStatusRepository extends JpaRepository<OfficialDispatchStatus, Long>, JpaSpecificationExecutor<OfficialDispatchStatus> {}
