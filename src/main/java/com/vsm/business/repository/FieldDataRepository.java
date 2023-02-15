package com.vsm.business.repository;

import com.vsm.business.domain.FieldData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the FieldData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldDataRepository extends JpaRepository<FieldData, Long>, JpaSpecificationExecutor<FieldData> {
    List<FieldData> findAllByRequestDataId(Long requestDataId);
    List<FieldData> findAllByRequestDataId(Long requestDataId, Pageable pageable);
    List<FieldData> findAllByFormDataId(Long formDataId);
    List<FieldData> findAllByFormDataId(Long formDataId, Pageable pageable);
}
