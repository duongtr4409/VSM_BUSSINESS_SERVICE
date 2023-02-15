package com.vsm.business.repository;

import com.vsm.business.domain.SignatureInfomation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the SignatureInfomation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignatureInfomationRepository extends JpaRepository<SignatureInfomation, Long>, JpaSpecificationExecutor<SignatureInfomation> {
    List<SignatureInfomation> findAllByUserInfoId(Long userId);
}
