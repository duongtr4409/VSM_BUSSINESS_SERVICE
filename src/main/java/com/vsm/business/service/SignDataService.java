package com.vsm.business.service;

import com.vsm.business.service.dto.SignDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.SignData}.
 */
public interface SignDataService {
    /**
     * Save a signData.
     *
     * @param signDataDTO the entity to save.
     * @return the persisted entity.
     */
    SignDataDTO save(SignDataDTO signDataDTO);

    /**
     * Partially updates a signData.
     *
     * @param signDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SignDataDTO> partialUpdate(SignDataDTO signDataDTO);

    /**
     * Get all the signData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" signData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SignDataDTO> findOne(Long id);

    /**
     * Delete the "id" signData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the signData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignDataDTO> search(String query, Pageable pageable);
}
