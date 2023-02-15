package com.vsm.business.repository;

import com.vsm.business.domain.Status;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {
    List<Status> findAllByStatusCode(String statusCode);
}
