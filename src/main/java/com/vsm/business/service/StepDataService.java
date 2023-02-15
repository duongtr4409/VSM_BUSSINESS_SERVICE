package com.vsm.business.service;

import com.vsm.business.service.dto.StepDataDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.StepData}.
 */
public interface StepDataService {
    /**
     * Save a stepData.
     *
     * @param stepDataDTO the entity to save.
     * @return the persisted entity.
     */
    StepDataDTO save(StepDataDTO stepDataDTO);

    /**
     * Partially updates a stepData.
     *
     * @param stepDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StepDataDTO> partialUpdate(StepDataDTO stepDataDTO);

    /**
     * Get all the stepData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepDataDTO> findAll(Pageable pageable);
    /**
     * Get all the StepDataDTO where PreviousStep is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<StepDataDTO> findAllWherePreviousStepIsNull();

    /**
     * Get all the stepData with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepDataDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" stepData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StepDataDTO> findOne(Long id);

    /**
     * Delete the "id" stepData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stepData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StepDataDTO> search(String query, Pageable pageable);
}
