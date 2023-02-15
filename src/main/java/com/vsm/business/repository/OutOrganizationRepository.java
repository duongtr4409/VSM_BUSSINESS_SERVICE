package com.vsm.business.repository;

import com.vsm.business.domain.OutOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OutOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutOrganizationRepository extends JpaRepository<OutOrganization, Long>, JpaSpecificationExecutor<OutOrganization> {}
