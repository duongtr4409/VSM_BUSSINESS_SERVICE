package com.vsm.business.repository;

import com.vsm.business.domain.Tennant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tennant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TennantRepository extends JpaRepository<Tennant, Long>, JpaSpecificationExecutor<Tennant> {}
