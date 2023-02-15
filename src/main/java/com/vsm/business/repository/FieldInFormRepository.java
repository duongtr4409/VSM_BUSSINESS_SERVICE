package com.vsm.business.repository;

import com.vsm.business.domain.FieldInForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the FieldInForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldInFormRepository extends JpaRepository<FieldInForm, Long>, JpaSpecificationExecutor<FieldInForm> {
    List<FieldInForm> findAllByFormId(Long formId);
}
