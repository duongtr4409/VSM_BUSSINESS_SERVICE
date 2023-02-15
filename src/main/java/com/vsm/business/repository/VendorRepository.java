package com.vsm.business.repository;

import com.vsm.business.domain.Vendor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {}
