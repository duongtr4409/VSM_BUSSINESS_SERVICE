package com.vsm.business.service;

import com.vsm.business.service.dto.ConsultReplyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ConsultReply}.
 */
public interface ConsultReplyService {
    /**
     * Save a consultReply.
     *
     * @param consultReplyDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultReplyDTO save(ConsultReplyDTO consultReplyDTO);

    /**
     * Partially updates a consultReply.
     *
     * @param consultReplyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConsultReplyDTO> partialUpdate(ConsultReplyDTO consultReplyDTO);

    /**
     * Get all the consultReplies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultReplyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" consultReply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultReplyDTO> findOne(Long id);

    /**
     * Delete the "id" consultReply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the consultReply corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultReplyDTO> search(String query, Pageable pageable);
}
