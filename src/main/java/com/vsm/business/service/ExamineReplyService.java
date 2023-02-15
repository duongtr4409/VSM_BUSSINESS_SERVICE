package com.vsm.business.service;

import com.vsm.business.service.dto.ExamineReplyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ExamineReply}.
 */
public interface ExamineReplyService {
    /**
     * Save a examineReply.
     *
     * @param examineReplyDTO the entity to save.
     * @return the persisted entity.
     */
    ExamineReplyDTO save(ExamineReplyDTO examineReplyDTO);

    /**
     * Partially updates a examineReply.
     *
     * @param examineReplyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamineReplyDTO> partialUpdate(ExamineReplyDTO examineReplyDTO);

    /**
     * Get all the examineReplies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamineReplyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examineReply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamineReplyDTO> findOne(Long id);

    /**
     * Delete the "id" examineReply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the examineReply corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamineReplyDTO> search(String query, Pageable pageable);
}
