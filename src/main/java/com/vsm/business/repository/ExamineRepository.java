package com.vsm.business.repository;

import com.vsm.business.domain.Examine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Examine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamineRepository extends JpaRepository<Examine, Long>, JpaSpecificationExecutor<Examine> {
    List<Examine> findAllByReceiverIdAndStepDataId(Long receiverId, Long stepDataId);
}
