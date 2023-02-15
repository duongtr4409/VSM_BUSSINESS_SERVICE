package com.vsm.business.repository;

import com.vsm.business.domain.RoleOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the RoleOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleOrganizationRepository extends JpaRepository<RoleOrganization, Long> {
    List<RoleOrganization> findAllByRoleId(Long roleId);
}
