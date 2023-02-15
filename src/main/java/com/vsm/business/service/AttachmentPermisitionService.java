package com.vsm.business.service;

import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.AttachmentPermisition}.
 */
public interface AttachmentPermisitionService {
    /**
     * Save a attachmentPermisition.
     *
     * @param attachmentPermisitionDTO the entity to save.
     * @return the persisted entity.
     */
    AttachmentPermisitionDTO save(AttachmentPermisitionDTO attachmentPermisitionDTO);

    /**
     * Partially updates a attachmentPermisition.
     *
     * @param attachmentPermisitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachmentPermisitionDTO> partialUpdate(AttachmentPermisitionDTO attachmentPermisitionDTO);

    /**
     * Get all the attachmentPermisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentPermisitionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachmentPermisition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachmentPermisitionDTO> findOne(Long id);

    /**
     * Delete the "id" attachmentPermisition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the attachmentPermisition corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentPermisitionDTO> search(String query, Pageable pageable);
}
