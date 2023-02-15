package com.vsm.business.repository;

import com.vsm.business.domain.RankInOrg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the RankInOrg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RankInOrgRepository extends JpaRepository<RankInOrg, Long>, JpaSpecificationExecutor<RankInOrg> {
    List<RankInOrg> findAllByOrganizationId(Long organizationId);
    List<RankInOrg> findAllByRankId(Long rankId);
}
