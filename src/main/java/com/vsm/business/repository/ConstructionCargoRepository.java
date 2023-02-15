package com.vsm.business.repository;

import com.vsm.business.domain.ConstructionCargo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ConstructionCargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConstructionCargoRepository extends JpaRepository<ConstructionCargo, Long>, JpaSpecificationExecutor<ConstructionCargo> {
    List<ConstructionCargo> findAllByCargoCode(String cargoCode);

    @Query(value = "select ma_hieu from construction_cargo", nativeQuery = true)
    List<String> getAllMaHieu();
}
