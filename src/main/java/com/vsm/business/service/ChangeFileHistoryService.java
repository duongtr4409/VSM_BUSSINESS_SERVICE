package com.vsm.business.service;

import com.vsm.business.service.dto.ChangeFileHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ChangeFileHistory}.
 */
public interface ChangeFileHistoryService {
    /**
     * Save a changeFileHistory.
     *
     * @param changeFileHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ChangeFileHistoryDTO save(ChangeFileHistoryDTO changeFileHistoryDTO);

    /**
     * Partially updates a changeFileHistory.
     *
     * @param changeFileHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChangeFileHistoryDTO> partialUpdate(ChangeFileHistoryDTO changeFileHistoryDTO);

    /**
     * Get all the changeFileHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChangeFileHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" changeFileHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChangeFileHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" changeFileHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the changeFileHistory corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChangeFileHistoryDTO> search(String query, Pageable pageable);
}
