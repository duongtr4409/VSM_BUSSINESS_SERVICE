package com.vsm.business.service;

import com.vsm.business.service.dto.FieldInFormDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.FieldInForm}.
 */
public interface FieldInFormService {
    /**
     * Save a fieldInForm.
     *
     * @param fieldInFormDTO the entity to save.
     * @return the persisted entity.
     */
    FieldInFormDTO save(FieldInFormDTO fieldInFormDTO);

    /**
     * Partially updates a fieldInForm.
     *
     * @param fieldInFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldInFormDTO> partialUpdate(FieldInFormDTO fieldInFormDTO);

    /**
     * Get all the fieldInForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldInFormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fieldInForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldInFormDTO> findOne(Long id);

    /**
     * Delete the "id" fieldInForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fieldInForm corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldInFormDTO> search(String query, Pageable pageable);
}
