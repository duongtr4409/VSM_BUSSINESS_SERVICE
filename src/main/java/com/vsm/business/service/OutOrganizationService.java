package com.vsm.business.service;

import com.vsm.business.service.dto.OutOrganizationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.OutOrganization}.
 */
public interface OutOrganizationService {
    /**
     * Save a outOrganization.
     *
     * @param outOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    OutOrganizationDTO save(OutOrganizationDTO outOrganizationDTO);

    /**
     * Partially updates a outOrganization.
     *
     * @param outOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OutOrganizationDTO> partialUpdate(OutOrganizationDTO outOrganizationDTO);

    /**
     * Get all the outOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OutOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" outOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OutOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" outOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the outOrganization corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OutOrganizationDTO> search(String query, Pageable pageable);
}
