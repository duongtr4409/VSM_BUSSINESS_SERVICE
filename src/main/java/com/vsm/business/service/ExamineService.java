package com.vsm.business.service;

import com.vsm.business.service.dto.ExamineDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Examine}.
 */
public interface ExamineService {
    /**
     * Save a examine.
     *
     * @param examineDTO the entity to save.
     * @return the persisted entity.
     */
    ExamineDTO save(ExamineDTO examineDTO);

    /**
     * Partially updates a examine.
     *
     * @param examineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamineDTO> partialUpdate(ExamineDTO examineDTO);

    /**
     * Get all the examines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamineDTO> findOne(Long id);

    /**
     * Delete the "id" examine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the examine corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamineDTO> search(String query, Pageable pageable);
}
