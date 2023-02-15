package com.vsm.business.repository;

import com.vsm.business.domain.CurrencyUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CurrencyUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyUnitRepository extends JpaRepository<CurrencyUnit, Long>, JpaSpecificationExecutor<CurrencyUnit> {}
