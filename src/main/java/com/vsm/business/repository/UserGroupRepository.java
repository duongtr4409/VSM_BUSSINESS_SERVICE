package com.vsm.business.repository;

import com.vsm.business.domain.UserGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserGroup entity.
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {
    @Query(
        value = "select distinct userGroup from UserGroup userGroup left join fetch userGroup.userInfos left join fetch userGroup.roles",
        countQuery = "select count(distinct userGroup) from UserGroup userGroup"
    )
    Page<UserGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userGroup from UserGroup userGroup left join fetch userGroup.userInfos left join fetch userGroup.roles")
    List<UserGroup> findAllWithEagerRelationships();

    @Query(
        "select userGroup from UserGroup userGroup left join fetch userGroup.userInfos left join fetch userGroup.roles where userGroup.id =:id"
    )
    Optional<UserGroup> findOneWithEagerRelationships(@Param("id") Long id);

    List<UserGroup> findAllByIsAutoAdd(Boolean isAutoAdd);
}
