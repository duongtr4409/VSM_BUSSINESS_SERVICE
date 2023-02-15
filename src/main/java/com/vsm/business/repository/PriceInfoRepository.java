package com.vsm.business.repository;

import com.vsm.business.domain.PriceInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PriceInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long>, JpaSpecificationExecutor<PriceInfo> {}
