package com.vsm.business.service;

import com.vsm.business.service.dto.PriorityLevelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.PriorityLevel}.
 */
public interface PriorityLevelService {
    /**
     * Save a priorityLevel.
     *
     * @param priorityLevelDTO the entity to save.
     * @return the persisted entity.
     */
    PriorityLevelDTO save(PriorityLevelDTO priorityLevelDTO);

    /**
     * Partially updates a priorityLevel.
     *
     * @param priorityLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PriorityLevelDTO> partialUpdate(PriorityLevelDTO priorityLevelDTO);

    /**
     * Get all the priorityLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriorityLevelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" priorityLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriorityLevelDTO> findOne(Long id);

    /**
     * Delete the "id" priorityLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the priorityLevel corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriorityLevelDTO> search(String query, Pageable pageable);
}
