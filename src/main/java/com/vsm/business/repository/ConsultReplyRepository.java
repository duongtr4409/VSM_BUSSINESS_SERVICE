package com.vsm.business.repository;

import com.vsm.business.domain.ConsultReply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConsultReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultReplyRepository extends JpaRepository<ConsultReply, Long>, JpaSpecificationExecutor<ConsultReply> {}
