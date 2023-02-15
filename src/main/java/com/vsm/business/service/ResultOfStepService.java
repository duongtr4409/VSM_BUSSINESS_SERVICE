package com.vsm.business.service;

import com.vsm.business.service.dto.ResultOfStepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ResultOfStep}.
 */
public interface ResultOfStepService {
    /**
     * Save a resultOfStep.
     *
     * @param resultOfStepDTO the entity to save.
     * @return the persisted entity.
     */
    ResultOfStepDTO save(ResultOfStepDTO resultOfStepDTO);

    /**
     * Partially updates a resultOfStep.
     *
     * @param resultOfStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResultOfStepDTO> partialUpdate(ResultOfStepDTO resultOfStepDTO);

    /**
     * Get all the resultOfSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultOfStepDTO> findAll(Pageable pageable);

    /**
     * Get the "id" resultOfStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultOfStepDTO> findOne(Long id);

    /**
     * Delete the "id" resultOfStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the resultOfStep corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultOfStepDTO> search(String query, Pageable pageable);
}
