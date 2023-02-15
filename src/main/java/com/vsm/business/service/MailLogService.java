package com.vsm.business.service;

import com.vsm.business.service.dto.MailLogDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.MailLog}.
 */
public interface MailLogService {
    /**
     * Save a mailLog.
     *
     * @param mailLogDTO the entity to save.
     * @return the persisted entity.
     */
    MailLogDTO save(MailLogDTO mailLogDTO);

    /**
     * Partially updates a mailLog.
     *
     * @param mailLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MailLogDTO> partialUpdate(MailLogDTO mailLogDTO);

    /**
     * Get all the mailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MailLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mailLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MailLogDTO> findOne(Long id);

    /**
     * Delete the "id" mailLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mailLog corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MailLogDTO> search(String query, Pageable pageable);
}
