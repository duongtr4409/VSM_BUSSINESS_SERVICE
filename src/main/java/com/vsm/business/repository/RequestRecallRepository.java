package com.vsm.business.repository;

import com.vsm.business.domain.RequestRecall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the RequestRecall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRecallRepository extends JpaRepository<RequestRecall, Long>, JpaSpecificationExecutor<RequestRecall> {
    List<RequestRecall> findAllByRequestDataId(Long requestDataId);
}
