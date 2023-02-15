package com.vsm.business.service;

import com.vsm.business.service.dto.ProcessDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ProcessData}.
 */
public interface ProcessDataService {
    /**
     * Save a processData.
     *
     * @param processDataDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessDataDTO save(ProcessDataDTO processDataDTO);

    /**
     * Partially updates a processData.
     *
     * @param processDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessDataDTO> partialUpdate(ProcessDataDTO processDataDTO);

    /**
     * Get all the processData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" processData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessDataDTO> findOne(Long id);

    /**
     * Delete the "id" processData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the processData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessDataDTO> search(String query, Pageable pageable);
}
