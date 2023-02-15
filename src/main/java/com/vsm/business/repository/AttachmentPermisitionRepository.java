package com.vsm.business.repository;

import com.vsm.business.domain.AttachmentPermisition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the AttachmentPermisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentPermisitionRepository extends JpaRepository<AttachmentPermisition, Long>, JpaSpecificationExecutor<AttachmentPermisition> {

    List<AttachmentPermisition> findAllByAttachmentFileId(Long attachmentFileId);

    List<AttachmentPermisition> findAllByUserInfoId(Long userInfoId);
}
