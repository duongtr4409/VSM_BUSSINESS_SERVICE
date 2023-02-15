package com.vsm.business.repository;

import com.vsm.business.domain.InformationInExchange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the InformationInExchange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformationInExchangeRepository extends JpaRepository<InformationInExchange, Long>, JpaSpecificationExecutor<InformationInExchange> {
    List<InformationInExchange> findAllByRequestDataId(Long requestDataId);
}
