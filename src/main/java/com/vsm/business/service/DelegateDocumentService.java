package com.vsm.business.service;

import com.vsm.business.service.dto.DelegateDocumentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.DelegateDocument}.
 */
public interface DelegateDocumentService {
    /**
     * Save a delegateDocument.
     *
     * @param delegateDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    DelegateDocumentDTO save(DelegateDocumentDTO delegateDocumentDTO);

    /**
     * Partially updates a delegateDocument.
     *
     * @param delegateDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DelegateDocumentDTO> partialUpdate(DelegateDocumentDTO delegateDocumentDTO);

    /**
     * Get all the delegateDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateDocumentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" delegateDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DelegateDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" delegateDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the delegateDocument corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateDocumentDTO> search(String query, Pageable pageable);
}
