package com.vsm.business.service;

import com.vsm.business.service.dto.DelegateInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.DelegateInfo}.
 */
public interface DelegateInfoService {
    /**
     * Save a delegateInfo.
     *
     * @param delegateInfoDTO the entity to save.
     * @return the persisted entity.
     */
    DelegateInfoDTO save(DelegateInfoDTO delegateInfoDTO);

    /**
     * Partially updates a delegateInfo.
     *
     * @param delegateInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DelegateInfoDTO> partialUpdate(DelegateInfoDTO delegateInfoDTO);

    /**
     * Get all the delegateInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" delegateInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DelegateInfoDTO> findOne(Long id);

    /**
     * Delete the "id" delegateInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the delegateInfo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateInfoDTO> search(String query, Pageable pageable);
}
