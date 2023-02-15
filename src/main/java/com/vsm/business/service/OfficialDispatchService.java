package com.vsm.business.service;

import com.vsm.business.service.dto.OfficialDispatchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.OfficialDispatch}.
 */
public interface OfficialDispatchService {
    /**
     * Save a officialDispatch.
     *
     * @param officialDispatchDTO the entity to save.
     * @return the persisted entity.
     */
    OfficialDispatchDTO save(OfficialDispatchDTO officialDispatchDTO);

    /**
     * Partially updates a officialDispatch.
     *
     * @param officialDispatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfficialDispatchDTO> partialUpdate(OfficialDispatchDTO officialDispatchDTO);

    /**
     * Get all the officialDispatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchDTO> findAll(Pageable pageable);

    /**
     * Get all the officialDispatches with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" officialDispatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficialDispatchDTO> findOne(Long id);

    /**
     * Delete the "id" officialDispatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the officialDispatch corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchDTO> search(String query, Pageable pageable);
}
