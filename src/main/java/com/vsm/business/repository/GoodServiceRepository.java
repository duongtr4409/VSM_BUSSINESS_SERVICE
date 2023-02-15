package com.vsm.business.repository;

import com.vsm.business.domain.GoodService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoodService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodServiceRepository extends JpaRepository<GoodService, Long>, JpaSpecificationExecutor<GoodService> {}
