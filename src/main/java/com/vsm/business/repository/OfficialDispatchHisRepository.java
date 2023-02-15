package com.vsm.business.repository;

import com.vsm.business.domain.OfficialDispatchHis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the OfficialDispatchHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficialDispatchHisRepository extends JpaRepository<OfficialDispatchHis, Long>, JpaSpecificationExecutor<OfficialDispatchHis> {
    List<OfficialDispatchHis> findAllByOfficialDispatchId(Long officalDispatchID);
}
