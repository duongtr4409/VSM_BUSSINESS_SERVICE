package com.vsm.business.service;

import com.vsm.business.service.dto.StepInProcessDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.StepInProcess}.
 */
public interface StepInProcessService {
    /**
     * Save a stepInProcess.
     *
     * @param stepInProcessDTO the entity to save.
     * @return the persisted entity.
     */
    StepInProcessDTO save(StepInProcessDTO stepInProcessDTO);

    /**
     * Partially updates a stepInProcess.
     *
     * @param stepInProcessDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StepInProcessDTO> partialUpdate(StepInProcessDTO stepInProcessDTO);

    /**
     * Get all the stepInProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepInProcessDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stepInProcess.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StepInProcessDTO> findOne(Long id);

    /**
     * Delete the "id" stepInProcess.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stepInProcess corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepInProcessDTO> search(String query, Pageable pageable);
}
