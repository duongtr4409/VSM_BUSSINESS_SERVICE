package com.vsm.business.service;

import com.vsm.business.service.dto.ManageStampInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ManageStampInfo}.
 */
public interface ManageStampInfoService {
    /**
     * Save a manageStampInfo.
     *
     * @param manageStampInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ManageStampInfoDTO save(ManageStampInfoDTO manageStampInfoDTO);

    /**
     * Partially updates a manageStampInfo.
     *
     * @param manageStampInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManageStampInfoDTO> partialUpdate(ManageStampInfoDTO manageStampInfoDTO);

    /**
     * Get all the manageStampInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManageStampInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the manageStampInfos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManageStampInfoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" manageStampInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManageStampInfoDTO> findOne(Long id);

    /**
     * Delete the "id" manageStampInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the manageStampInfo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManageStampInfoDTO> search(String query, Pageable pageable);
}
