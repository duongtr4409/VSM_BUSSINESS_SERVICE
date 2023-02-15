package com.vsm.business.service;

import com.vsm.business.service.dto.AttachmentInStepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.AttachmentInStep}.
 */
public interface AttachmentInStepService {
    /**
     * Save a attachmentInStep.
     *
     * @param attachmentInStepDTO the entity to save.
     * @return the persisted entity.
     */
    AttachmentInStepDTO save(AttachmentInStepDTO attachmentInStepDTO);

    /**
     * Partially updates a attachmentInStep.
     *
     * @param attachmentInStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachmentInStepDTO> partialUpdate(AttachmentInStepDTO attachmentInStepDTO);

    /**
     * Get all the attachmentInSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentInStepDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachmentInStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachmentInStepDTO> findOne(Long id);

    /**
     * Delete the "id" attachmentInStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attachmentInStep corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentInStepDTO> search(String query, Pageable pageable);
}
