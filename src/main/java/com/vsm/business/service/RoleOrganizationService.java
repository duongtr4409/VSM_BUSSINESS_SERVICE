package com.vsm.business.service;

import com.vsm.business.service.dto.RoleOrganizationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RoleOrganization}.
 */
public interface RoleOrganizationService {
    /**
     * Save a roleOrganization.
     *
     * @param roleOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    RoleOrganizationDTO save(RoleOrganizationDTO roleOrganizationDTO);

    /**
     * Partially updates a roleOrganization.
     *
     * @param roleOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleOrganizationDTO> partialUpdate(RoleOrganizationDTO roleOrganizationDTO);

    /**
     * Get all the roleOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" roleOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" roleOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the roleOrganization corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleOrganizationDTO> search(String query, Pageable pageable);
}
