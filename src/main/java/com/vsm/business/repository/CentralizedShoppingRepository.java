package com.vsm.business.repository;

import com.vsm.business.domain.CentralizedShopping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CentralizedShopping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentralizedShoppingRepository extends JpaRepository<CentralizedShopping, Long>, JpaSpecificationExecutor<CentralizedShopping> {
    List<CentralizedShopping> findAllByMaChungPnL(String maChungPnL);
}
