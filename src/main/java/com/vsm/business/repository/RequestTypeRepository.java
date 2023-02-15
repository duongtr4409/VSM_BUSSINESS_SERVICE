package com.vsm.business.repository;

import com.vsm.business.domain.RequestType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequestType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Long>, JpaSpecificationExecutor<RequestType> {
    List<RequestType> findAllByRequestGroupId(Long RequestGroupId);
}
