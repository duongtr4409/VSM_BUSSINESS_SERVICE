package com.vsm.business.repository;

import com.vsm.business.domain.ChangeFileHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ChangeFileHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChangeFileHistoryRepository extends JpaRepository<ChangeFileHistory, Long>, JpaSpecificationExecutor<ChangeFileHistory> {
    List<ChangeFileHistory> findAllByAttachmentFileId(Long attachmentFileId);
}
