package com.vsm.business.service;

import com.vsm.business.service.dto.StampDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Stamp}.
 */
public interface StampService {
    /**
     * Save a stamp.
     *
     * @param stampDTO the entity to save.
     * @return the persisted entity.
     */
    StampDTO save(StampDTO stampDTO);

    /**
     * Partially updates a stamp.
     *
     * @param stampDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StampDTO> partialUpdate(StampDTO stampDTO);

    /**
     * Get all the stamps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StampDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stamp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StampDTO> findOne(Long id);

    /**
     * Delete the "id" stamp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stamp corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StampDTO> search(String query, Pageable pageable);
}
