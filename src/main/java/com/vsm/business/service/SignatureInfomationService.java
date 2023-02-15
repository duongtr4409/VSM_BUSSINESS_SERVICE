package com.vsm.business.service;

import com.vsm.business.service.dto.SignatureInfomationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.SignatureInfomation}.
 */
public interface SignatureInfomationService {
    /**
     * Save a signatureInfomation.
     *
     * @param signatureInfomationDTO the entity to save.
     * @return the persisted entity.
     */
    SignatureInfomationDTO save(SignatureInfomationDTO signatureInfomationDTO);

    /**
     * Partially updates a signatureInfomation.
     *
     * @param signatureInfomationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SignatureInfomationDTO> partialUpdate(SignatureInfomationDTO signatureInfomationDTO);

    /**
     * Get all the signatureInfomations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignatureInfomationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" signatureInfomation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SignatureInfomationDTO> findOne(Long id);

    /**
     * Delete the "id" signatureInfomation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the signatureInfomation corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignatureInfomationDTO> search(String query, Pageable pageable);
}
