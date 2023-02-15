package com.vsm.business.service;

import com.vsm.business.service.dto.FormDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.FormData}.
 */
public interface FormDataService {
    /**
     * Save a formData.
     *
     * @param formDataDTO the entity to save.
     * @return the persisted entity.
     */
    FormDataDTO save(FormDataDTO formDataDTO);

    /**
     * Partially updates a formData.
     *
     * @param formDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormDataDTO> partialUpdate(FormDataDTO formDataDTO);

    /**
     * Get all the formData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" formData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormDataDTO> findOne(Long id);

    /**
     * Delete the "id" formData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the formData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormDataDTO> search(String query, Pageable pageable);
}
