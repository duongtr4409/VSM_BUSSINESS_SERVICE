package com.vsm.business.repository;

import com.vsm.business.domain.ReqdataChangeHis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ReqdataChangeHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReqdataChangeHisRepository extends JpaRepository<ReqdataChangeHis, Long>, JpaSpecificationExecutor<ReqdataChangeHis> {
    List<ReqdataChangeHis> findAllByRequestDataId(long requestDataId);
}
