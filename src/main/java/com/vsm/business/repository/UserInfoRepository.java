package com.vsm.business.repository;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.domain.UserInfo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserInfo entity.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    @Query(
        value = "select distinct userInfo from UserInfo userInfo left join fetch userInfo.roles left join fetch userInfo.ranks left join fetch userInfo.organizations",
        countQuery = "select count(distinct userInfo) from UserInfo userInfo"
    )
    Page<UserInfo> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct userInfo from UserInfo userInfo left join fetch userInfo.roles left join fetch userInfo.ranks left join fetch userInfo.organizations"
    )
    List<UserInfo> findAllWithEagerRelationships();

    @Query(
        "select userInfo from UserInfo userInfo left join fetch userInfo.roles left join fetch userInfo.ranks left join fetch userInfo.organizations where userInfo.id =:id"
    )
    Optional<UserInfo> findOneWithEagerRelationships(@Param("id") Long id);

    List<UserInfo> findAllByOrganizationsAndRanks(Organization organization, Rank rank);

    List<UserInfo> findAllByOrganizations(Organization organization);

    List<UserInfo> findAllByRanks(Rank rank);

    List<UserInfo> findAllByIdInMicrosoft(String idInMicrosoft);

    List<UserInfo> findAllByEmail(String email);

    List<UserInfo> findAllByUserName(String userName);

    Optional<UserInfo> findByuserNameOrEmail(String username, String email);

    Optional<UserInfo> findByuserNameOrEmailAndPassword(String username, String email, String password);

    List<UserInfo> findByuserNameOrEmailAndIsDeleteNot(String username, String email, Boolean isDelete);

    List<UserInfo> findByUserNameAndIsDeleteNotAndIsActiveOrEmailAndIsDeleteNotAndIsActive(String username, Boolean isDelete1, Boolean isActive1, String email, Boolean isDelete2, Boolean isActive2);

    List<UserInfo> findAllByNumberOfLoginFailedGreaterThan(Long numberLogginFaild);

    Optional<UserInfo> findByIdAndIsDeleteNot(Long id, boolean isDelete);

    List<UserInfo> findAllByUserNameAndIsDeleteNot(String username, Boolean isDelete);

    List<UserInfo> findAllByUserNameAndIsDeleteNotAndIsActive(String username, Boolean isDelete, Boolean isActive);
}
