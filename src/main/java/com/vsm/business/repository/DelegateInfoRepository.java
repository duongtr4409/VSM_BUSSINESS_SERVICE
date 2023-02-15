package com.vsm.business.repository;

import com.vsm.business.domain.DelegateInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DelegateInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegateInfoRepository extends JpaRepository<DelegateInfo, Long>, JpaSpecificationExecutor<DelegateInfo> {}
