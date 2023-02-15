package com.vsm.business.repository;

import com.vsm.business.domain.DelegateDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DelegateDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelegateDocumentRepository extends JpaRepository<DelegateDocument, Long>, JpaSpecificationExecutor<DelegateDocument> {}
