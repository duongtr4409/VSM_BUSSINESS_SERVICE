package com.vsm.business.repository;

import com.vsm.business.domain.DocumentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>, JpaSpecificationExecutor<DocumentType> {}
