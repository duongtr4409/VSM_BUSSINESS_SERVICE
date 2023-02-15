package com.vsm.business.service;

import com.vsm.business.service.dto.RoleRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RoleRequest}.
 */
public interface RoleRequestService {
    /**
     * Save a roleRequest.
     *
     * @param roleRequestDTO the entity to save.
     * @return the persisted entity.
     */
    RoleRequestDTO save(RoleRequestDTO roleRequestDTO);

    /**
     * Partially updates a roleRequest.
     *
     * @param roleRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleRequestDTO> partialUpdate(RoleRequestDTO roleRequestDTO);

    /**
     * Get all the roleRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" roleRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleRequestDTO> findOne(Long id);

    /**
     * Delete the "id" roleRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the roleRequest corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleRequestDTO> search(String query, Pageable pageable);
}
