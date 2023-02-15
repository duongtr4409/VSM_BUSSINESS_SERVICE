package com.vsm.business.repository;

import com.vsm.business.domain.Rank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RankRepository extends JpaRepository<Rank, Long>, JpaSpecificationExecutor<Rank> {}
