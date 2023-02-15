package com.vsm.business.repository;

import com.vsm.business.domain.DelegateType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DelegateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegateTypeRepository extends JpaRepository<DelegateType, Long>, JpaSpecificationExecutor<DelegateType> {}
