package com.vsm.business.repository;

import com.vsm.business.domain.TagInExchange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TagInExchange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagInExchangeRepository extends JpaRepository<TagInExchange, Long>, JpaSpecificationExecutor<TagInExchange> {}
