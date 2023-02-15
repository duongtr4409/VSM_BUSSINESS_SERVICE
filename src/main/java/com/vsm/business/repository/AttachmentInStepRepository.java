package com.vsm.business.repository;

import com.vsm.business.domain.AttachmentInStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttachmentInStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentInStepRepository extends JpaRepository<AttachmentInStep, Long>, JpaSpecificationExecutor<AttachmentInStep> {}
