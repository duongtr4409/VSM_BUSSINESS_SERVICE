package com.vsm.business.service;

import com.vsm.business.service.dto.ConsultDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Consult}.
 */
public interface ConsultService {
    /**
     * Save a consult.
     *
     * @param consultDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultDTO save(ConsultDTO consultDTO);

    /**
     * Partially updates a consult.
     *
     * @param consultDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConsultDTO> partialUpdate(ConsultDTO consultDTO);

    /**
     * Get all the consults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultDTO> findAll(Pageable pageable);

    /**
     * Get the "id" consult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultDTO> findOne(Long id);

    /**
     * Delete the "id" consult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the consult corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultDTO> search(String query, Pageable pageable);
}
