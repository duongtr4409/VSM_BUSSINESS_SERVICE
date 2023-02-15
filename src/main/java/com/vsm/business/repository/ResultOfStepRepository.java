package com.vsm.business.repository;

import com.vsm.business.domain.ResultOfStep;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ResultOfStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultOfStepRepository extends JpaRepository<ResultOfStep, Long>, JpaSpecificationExecutor<ResultOfStep>{
    List<ResultOfStep> findAllByStepDataId(Long stepDataId);
}
