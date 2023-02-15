package com.vsm.business.service;

import com.vsm.business.service.dto.FormDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Form}.
 */
public interface FormService {
    /**
     * Save a form.
     *
     * @param formDTO the entity to save.
     * @return the persisted entity.
     */
    FormDTO save(FormDTO formDTO);

    /**
     * Partially updates a form.
     *
     * @param formDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormDTO> partialUpdate(FormDTO formDTO);

    /**
     * Get all the forms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormDTO> findAll(Pageable pageable);

    /**
     * Get all the forms with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" form.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormDTO> findOne(Long id);

    /**
     * Delete the "id" form.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the form corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormDTO> search(String query, Pageable pageable);
}
