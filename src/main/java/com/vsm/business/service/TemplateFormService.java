package com.vsm.business.service;

import com.vsm.business.service.dto.TemplateFormDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.TemplateForm}.
 */
public interface TemplateFormService {
    /**
     * Save a templateForm.
     *
     * @param templateFormDTO the entity to save.
     * @return the persisted entity.
     */
    TemplateFormDTO save(TemplateFormDTO templateFormDTO);

    /**
     * Partially updates a templateForm.
     *
     * @param templateFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemplateFormDTO> partialUpdate(TemplateFormDTO templateFormDTO);

    /**
     * Get all the templateForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateFormDTO> findAll(Pageable pageable);

    /**
     * Get all the templateForms with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateFormDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" templateForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemplateFormDTO> findOne(Long id);

    /**
     * Delete the "id" templateForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the templateForm corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateFormDTO> search(String query, Pageable pageable);
}
