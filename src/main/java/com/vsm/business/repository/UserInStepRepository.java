package com.vsm.business.repository;

import com.vsm.business.domain.UserInStep;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserInStep entity.
 */
@Repository
public interface UserInStepRepository extends JpaRepository<UserInStep, Long>, JpaSpecificationExecutor<UserInStep> {
    @Query(
        value = "select distinct userInStep from UserInStep userInStep left join fetch userInStep.userInfo",
        countQuery = "select count(distinct userInStep) from UserInStep userInStep"
    )
    Page<UserInStep> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userInStep from UserInStep userInStep left join fetch userInStep.userInfo")
    List<UserInStep> findAllWithEagerRelationships();

    @Query("select userInStep from UserInStep userInStep left join fetch userInStep.userInfo where userInStep.id =:id")
    Optional<UserInStep> findOneWithEagerRelationships(@Param("id") Long id);

    List<UserInStep> findAllByStepInProcessId(Long stepInProcessId);

    List<UserInStep> findAllByUserInfoId(Long userInfoId);
}
