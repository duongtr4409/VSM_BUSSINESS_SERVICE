package com.vsm.business.repository;

import com.vsm.business.domain.FormData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the FormData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long>, JpaSpecificationExecutor<FormData> {
    List<FormData> findAllByRequestDataId(Long requestDataId);
}
