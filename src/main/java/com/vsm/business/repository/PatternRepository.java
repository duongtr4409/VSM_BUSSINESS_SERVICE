package com.vsm.business.repository;

import com.vsm.business.domain.Pattern;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long>, JpaSpecificationExecutor<Pattern> {}
