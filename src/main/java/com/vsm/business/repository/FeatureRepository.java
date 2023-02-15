package com.vsm.business.repository;

import com.vsm.business.domain.Feature;
import com.vsm.business.domain.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Feature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
