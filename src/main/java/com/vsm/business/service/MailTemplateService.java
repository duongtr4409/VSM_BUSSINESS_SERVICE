package com.vsm.business.service;

import com.vsm.business.service.dto.MailTemplateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.MailTemplate}.
 */
public interface MailTemplateService {
    /**
     * Save a mailTemplate.
     *
     * @param mailTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    MailTemplateDTO save(MailTemplateDTO mailTemplateDTO);

    /**
     * Partially updates a mailTemplate.
     *
     * @param mailTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MailTemplateDTO> partialUpdate(MailTemplateDTO mailTemplateDTO);

    /**
     * Get all the mailTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MailTemplateDTO> findAll(Pageable pageable);

    /**
     * Get all the mailTemplates with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MailTemplateDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mailTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MailTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" mailTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mailTemplate corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MailTemplateDTO> search(String query, Pageable pageable);
}
