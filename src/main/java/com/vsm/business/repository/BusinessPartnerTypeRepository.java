package com.vsm.business.repository;

import com.vsm.business.domain.BusinessPartnerType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BusinessPartnerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessPartnerTypeRepository extends JpaRepository<BusinessPartnerType, Long>, JpaSpecificationExecutor<BusinessPartnerType> {}
