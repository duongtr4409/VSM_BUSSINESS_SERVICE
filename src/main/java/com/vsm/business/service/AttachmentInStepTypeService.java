package com.vsm.business.service;

import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.AttachmentInStepType}.
 */
public interface AttachmentInStepTypeService {
    /**
     * Save a attachmentInStepType.
     *
     * @param attachmentInStepTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AttachmentInStepTypeDTO save(AttachmentInStepTypeDTO attachmentInStepTypeDTO);

    /**
     * Partially updates a attachmentInStepType.
     *
     * @param attachmentInStepTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachmentInStepTypeDTO> partialUpdate(AttachmentInStepTypeDTO attachmentInStepTypeDTO);

    /**
     * Get all the attachmentInStepTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentInStepTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachmentInStepType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachmentInStepTypeDTO> findOne(Long id);

    /**
     * Delete the "id" attachmentInStepType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attachmentInStepType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentInStepTypeDTO> search(String query, Pageable pageable);
}
