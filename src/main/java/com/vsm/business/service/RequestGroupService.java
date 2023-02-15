package com.vsm.business.service;

import com.vsm.business.service.dto.RequestGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RequestGroup}.
 */
public interface RequestGroupService {
    /**
     * Save a requestGroup.
     *
     * @param requestGroupDTO the entity to save.
     * @return the persisted entity.
     */
    RequestGroupDTO save(RequestGroupDTO requestGroupDTO);

    /**
     * Partially updates a requestGroup.
     *
     * @param requestGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestGroupDTO> partialUpdate(RequestGroupDTO requestGroupDTO);

    /**
     * Get all the requestGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" requestGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestGroupDTO> findOne(Long id);

    /**
     * Delete the "id" requestGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the requestGroup corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestGroupDTO> search(String query, Pageable pageable);
}
