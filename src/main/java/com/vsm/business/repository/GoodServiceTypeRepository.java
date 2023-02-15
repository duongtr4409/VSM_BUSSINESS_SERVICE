package com.vsm.business.repository;

import com.vsm.business.domain.GoodServiceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoodServiceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodServiceTypeRepository extends JpaRepository<GoodServiceType, Long>, JpaSpecificationExecutor<GoodServiceType> {}
