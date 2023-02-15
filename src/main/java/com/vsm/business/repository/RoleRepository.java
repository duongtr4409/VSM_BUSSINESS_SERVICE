package com.vsm.business.repository;

import com.vsm.business.domain.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    @Query(
        value = "select distinct role from Role role left join fetch role.features",
        countQuery = "select count(distinct role) from Role role"
    )
    Page<Role> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct role from Role role left join fetch role.features")
    List<Role> findAllWithEagerRelationships();

    @Query("select role from Role role left join fetch role.features where role.id =:id")
    Optional<Role> findOneWithEagerRelationships(@Param("id") Long id);

	List<Role> findAllByIsAutoAdd(Boolean isAutoAdd);

    List<Role> findALlByRoleCode(String roleCode);
}
