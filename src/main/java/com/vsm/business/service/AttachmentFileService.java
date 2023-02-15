package com.vsm.business.service;

import com.vsm.business.service.dto.AttachmentFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.AttachmentFile}.
 */
public interface AttachmentFileService {
    /**
     * Save a attachmentFile.
     *
     * @param attachmentFileDTO the entity to save.
     * @return the persisted entity.
     */
    AttachmentFileDTO save(AttachmentFileDTO attachmentFileDTO);

    /**
     * Partially updates a attachmentFile.
     *
     * @param attachmentFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachmentFileDTO> partialUpdate(AttachmentFileDTO attachmentFileDTO);

    /**
     * Get all the attachmentFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachmentFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachmentFileDTO> findOne(Long id);

    /**
     * Delete the "id" attachmentFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attachmentFile corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentFileDTO> search(String query, Pageable pageable);
}
