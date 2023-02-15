package com.vsm.business.repository;

import com.vsm.business.domain.MECargo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the MECargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MECargoRepository extends JpaRepository<MECargo, Long>, JpaSpecificationExecutor<MECargo> {
    List<MECargo> findAllByCargoCode(String cargoCode);

    @Query(value = "select ma_hieu from me_cargo", nativeQuery = true)
    List<String> getAllMaHieu();
}
