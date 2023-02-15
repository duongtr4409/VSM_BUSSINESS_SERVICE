package com.vsm.business.service;

import com.vsm.business.service.dto.SecurityLevelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.SecurityLevel}.
 */
public interface SecurityLevelService {
    /**
     * Save a securityLevel.
     *
     * @param securityLevelDTO the entity to save.
     * @return the persisted entity.
     */
    SecurityLevelDTO save(SecurityLevelDTO securityLevelDTO);

    /**
     * Partially updates a securityLevel.
     *
     * @param securityLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SecurityLevelDTO> partialUpdate(SecurityLevelDTO securityLevelDTO);

    /**
     * Get all the securityLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SecurityLevelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" securityLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SecurityLevelDTO> findOne(Long id);

    /**
     * Delete the "id" securityLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the securityLevel corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SecurityLevelDTO> search(String query, Pageable pageable);
}
