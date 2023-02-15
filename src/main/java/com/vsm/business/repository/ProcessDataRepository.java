package com.vsm.business.repository;

import com.vsm.business.domain.ProcessData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ProcessData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDataRepository extends JpaRepository<ProcessData, Long>, JpaSpecificationExecutor<ProcessData> {
    List<ProcessData> findAllByRequestDataId(Long requestDataId);
}
