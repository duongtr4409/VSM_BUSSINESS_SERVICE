package com.vsm.business.service;

import com.vsm.business.service.dto.TennantDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Tennant}.
 */
public interface TennantService {
    /**
     * Save a tennant.
     *
     * @param tennantDTO the entity to save.
     * @return the persisted entity.
     */
    TennantDTO save(TennantDTO tennantDTO);

    /**
     * Partially updates a tennant.
     *
     * @param tennantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TennantDTO> partialUpdate(TennantDTO tennantDTO);

    /**
     * Get all the tennants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TennantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tennant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TennantDTO> findOne(Long id);

    /**
     * Delete the "id" tennant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the tennant corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TennantDTO> search(String query, Pageable pageable);
}
