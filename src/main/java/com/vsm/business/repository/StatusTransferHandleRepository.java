package com.vsm.business.repository;

import com.vsm.business.domain.StatusTransferHandle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusTransferHandle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusTransferHandleRepository extends JpaRepository<StatusTransferHandle, Long>, JpaSpecificationExecutor<StatusTransferHandle> {}
