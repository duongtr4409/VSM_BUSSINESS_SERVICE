package com.vsm.business.service;

import com.vsm.business.service.dto.RequestRecallDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RequestRecall}.
 */
public interface RequestRecallService {
    /**
     * Save a requestRecall.
     *
     * @param requestRecallDTO the entity to save.
     * @return the persisted entity.
     */
    RequestRecallDTO save(RequestRecallDTO requestRecallDTO);

    /**
     * Partially updates a requestRecall.
     *
     * @param requestRecallDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestRecallDTO> partialUpdate(RequestRecallDTO requestRecallDTO);

    /**
     * Get all the requestRecalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestRecallDTO> findAll(Pageable pageable);

    /**
     * Get the "id" requestRecall.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestRecallDTO> findOne(Long id);

    /**
     * Delete the "id" requestRecall.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the requestRecall corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestRecallDTO> search(String query, Pageable pageable);
}
