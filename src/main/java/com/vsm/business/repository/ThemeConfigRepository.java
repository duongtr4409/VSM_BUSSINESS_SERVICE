package com.vsm.business.repository;

import com.vsm.business.domain.ThemeConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ThemeConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeConfigRepository extends JpaRepository<ThemeConfig, Long>, JpaSpecificationExecutor<ThemeConfig> {}
