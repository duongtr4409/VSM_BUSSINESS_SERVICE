package com.vsm.business.repository;

import com.vsm.business.domain.Stamp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Stamp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StampRepository extends JpaRepository<Stamp, Long>, JpaSpecificationExecutor<Stamp> {}
