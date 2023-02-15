package com.vsm.business.repository;

import com.vsm.business.domain.MailLog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the MailLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MailLogRepository extends JpaRepository<MailLog, Long>, JpaSpecificationExecutor<MailLog> {
    List<MailLog> findAllByIsSucessNotAndSendCountLessThan(boolean isSuccess, Long sendCount);
}
