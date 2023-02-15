package com.vsm.business.repository;

import com.vsm.business.domain.AttachmentInStepType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttachmentInStepType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentInStepTypeRepository extends JpaRepository<AttachmentInStepType, Long>, JpaSpecificationExecutor<AttachmentInStepType> {}
