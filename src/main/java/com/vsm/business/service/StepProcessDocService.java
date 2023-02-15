package com.vsm.business.service;

import com.vsm.business.service.dto.StepProcessDocDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.StepProcessDoc}.
 */
public interface StepProcessDocService {
    /**
     * Save a stepProcessDoc.
     *
     * @param stepProcessDocDTO the entity to save.
     * @return the persisted entity.
     */
    StepProcessDocDTO save(StepProcessDocDTO stepProcessDocDTO);

    /**
     * Partially updates a stepProcessDoc.
     *
     * @param stepProcessDocDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StepProcessDocDTO> partialUpdate(StepProcessDocDTO stepProcessDocDTO);

    /**
     * Get all the stepProcessDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepProcessDocDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stepProcessDoc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StepProcessDocDTO> findOne(Long id);

    /**
     * Delete the "id" stepProcessDoc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stepProcessDoc corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepProcessDocDTO> search(String query, Pageable pageable);
}
