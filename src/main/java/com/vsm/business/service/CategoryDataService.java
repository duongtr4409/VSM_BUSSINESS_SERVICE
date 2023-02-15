package com.vsm.business.service;

import com.vsm.business.service.dto.CategoryDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.CategoryData}.
 */
public interface CategoryDataService {
    /**
     * Save a categoryData.
     *
     * @param categoryDataDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryDataDTO save(CategoryDataDTO categoryDataDTO);

    /**
     * Partially updates a categoryData.
     *
     * @param categoryDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryDataDTO> partialUpdate(CategoryDataDTO categoryDataDTO);

    /**
     * Get all the categoryData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoryData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryDataDTO> findOne(Long id);

    /**
     * Delete the "id" categoryData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the categoryData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryDataDTO> search(String query, Pageable pageable);
}
