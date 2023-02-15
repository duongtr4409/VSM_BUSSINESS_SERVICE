package com.vsm.business.repository;

import com.vsm.business.domain.SignData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the SignData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignDataRepository extends JpaRepository<SignData, Long>, JpaSpecificationExecutor<SignData> {
    List<SignData> findAllByRequestDataId(Long requetDataId);
}
