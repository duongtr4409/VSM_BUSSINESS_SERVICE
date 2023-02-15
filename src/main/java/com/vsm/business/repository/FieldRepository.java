package com.vsm.business.repository;

import com.vsm.business.domain.Field;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Field entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldRepository extends JpaRepository<Field, Long>, JpaSpecificationExecutor<Field> {}
