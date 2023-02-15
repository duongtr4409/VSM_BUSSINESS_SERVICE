package com.vsm.business.repository;

import com.vsm.business.domain.CategoryData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CategoryData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryDataRepository extends JpaRepository<CategoryData, Long>, JpaSpecificationExecutor<CategoryData> {
    public List<CategoryData> findAllByCategoryGroupId(Long categoryGroupId);
}
