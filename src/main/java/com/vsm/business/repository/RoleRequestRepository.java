package com.vsm.business.repository;

import com.vsm.business.domain.RoleRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the RoleRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {
    List<RoleRequest> findAllByRoleId(Long roleId);
}
