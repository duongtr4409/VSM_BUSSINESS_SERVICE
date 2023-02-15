package com.vsm.business.service;

import com.vsm.business.service.dto.FieldDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.FieldData}.
 */
public interface FieldDataService {
    /**
     * Save a fieldData.
     *
     * @param fieldDataDTO the entity to save.
     * @return the persisted entity.
     */
    FieldDataDTO save(FieldDataDTO fieldDataDTO);

    /**
     * Partially updates a fieldData.
     *
     * @param fieldDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldDataDTO> partialUpdate(FieldDataDTO fieldDataDTO);

    /**
     * Get all the fieldData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fieldData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldDataDTO> findOne(Long id);

    /**
     * Delete the "id" fieldData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fieldData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldDataDTO> search(String query, Pageable pageable);
}
