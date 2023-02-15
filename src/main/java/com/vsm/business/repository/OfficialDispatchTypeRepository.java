package com.vsm.business.repository;

import com.vsm.business.domain.OfficialDispatchType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OfficialDispatchType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficialDispatchTypeRepository extends JpaRepository<OfficialDispatchType, Long>, JpaSpecificationExecutor<OfficialDispatchType> {}
