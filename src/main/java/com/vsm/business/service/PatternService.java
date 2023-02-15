package com.vsm.business.service;

import com.vsm.business.service.dto.PatternDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Pattern}.
 */
public interface PatternService {
    /**
     * Save a pattern.
     *
     * @param patternDTO the entity to save.
     * @return the persisted entity.
     */
    PatternDTO save(PatternDTO patternDTO);

    /**
     * Partially updates a pattern.
     *
     * @param patternDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatternDTO> partialUpdate(PatternDTO patternDTO);

    /**
     * Get all the patterns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatternDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pattern.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatternDTO> findOne(Long id);

    /**
     * Delete the "id" pattern.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pattern corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatternDTO> search(String query, Pageable pageable);
}
