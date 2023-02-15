package com.vsm.business.repository;

import com.vsm.business.domain.Consult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Spring Data SQL repository for the Consult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long>, JpaSpecificationExecutor<Consult> {
    List<Consult> findAllByReceiverIdAndStepDataId(Long receiverId, Long stepDataId);
}
