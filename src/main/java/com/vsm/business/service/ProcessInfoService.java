package com.vsm.business.service;

import com.vsm.business.service.dto.ProcessInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ProcessInfo}.
 */
public interface ProcessInfoService {
    /**
     * Save a processInfo.
     *
     * @param processInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessInfoDTO save(ProcessInfoDTO processInfoDTO);

    /**
     * Partially updates a processInfo.
     *
     * @param processInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessInfoDTO> partialUpdate(ProcessInfoDTO processInfoDTO);

    /**
     * Get all the processInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the processInfos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessInfoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" processInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessInfoDTO> findOne(Long id);

    /**
     * Delete the "id" processInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the processInfo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessInfoDTO> search(String query, Pageable pageable);
}
