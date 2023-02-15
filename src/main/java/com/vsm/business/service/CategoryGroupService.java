package com.vsm.business.service;

import com.vsm.business.service.dto.CategoryGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.CategoryGroup}.
 */
public interface CategoryGroupService {
    /**
     * Save a categoryGroup.
     *
     * @param categoryGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryGroupDTO save(CategoryGroupDTO categoryGroupDTO);

    /**
     * Partially updates a categoryGroup.
     *
     * @param categoryGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryGroupDTO> partialUpdate(CategoryGroupDTO categoryGroupDTO);

    /**
     * Get all the categoryGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoryGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryGroupDTO> findOne(Long id);

    /**
     * Delete the "id" categoryGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the categoryGroup corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryGroupDTO> search(String query, Pageable pageable);
}
