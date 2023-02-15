package com.vsm.business.service;

import com.vsm.business.service.dto.RankInOrgDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RankInOrg}.
 */
public interface RankInOrgService {
    /**
     * Save a rankInOrg.
     *
     * @param rankInOrgDTO the entity to save.
     * @return the persisted entity.
     */
    RankInOrgDTO save(RankInOrgDTO rankInOrgDTO);

    /**
     * Partially updates a rankInOrg.
     *
     * @param rankInOrgDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RankInOrgDTO> partialUpdate(RankInOrgDTO rankInOrgDTO);

    /**
     * Get all the rankInOrgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RankInOrgDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rankInOrg.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RankInOrgDTO> findOne(Long id);

    /**
     * Delete the "id" rankInOrg.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the rankInOrg corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RankInOrgDTO> search(String query, Pageable pageable);
}
