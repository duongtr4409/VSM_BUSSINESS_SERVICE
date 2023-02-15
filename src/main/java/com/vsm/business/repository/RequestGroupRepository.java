package com.vsm.business.repository;

import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data SQL repository for the RequestGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestGroupRepository extends JpaRepository<RequestGroup, Long>, JpaSpecificationExecutor<RequestGroup> {

    Set<RequestGroup> findAllByRequestsIn(Set<Request> requestSet);
}
