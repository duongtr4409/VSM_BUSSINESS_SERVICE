package com.vsm.business.repository;

import com.vsm.business.domain.ExamineReply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExamineReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamineReplyRepository extends JpaRepository<ExamineReply, Long>, JpaSpecificationExecutor<ExamineReply> {}
