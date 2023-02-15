package com.vsm.business.repository;

import com.vsm.business.domain.ReqdataProcessHis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ReqdataProcessHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReqdataProcessHisRepository extends JpaRepository<ReqdataProcessHis, Long>, JpaSpecificationExecutor<ReqdataProcessHis> {
    List<ReqdataProcessHis> findAllByRequestDataId(Long requestDataId);
}
