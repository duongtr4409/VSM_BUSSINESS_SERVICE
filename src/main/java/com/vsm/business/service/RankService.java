package com.vsm.business.service;

import com.vsm.business.service.dto.RankDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Rank}.
 */
public interface RankService {
    /**
     * Save a rank.
     *
     * @param rankDTO the entity to save.
     * @return the persisted entity.
     */
    RankDTO save(RankDTO rankDTO);

    /**
     * Partially updates a rank.
     *
     * @param rankDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RankDTO> partialUpdate(RankDTO rankDTO);

    /**
     * Get all the ranks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RankDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rank.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RankDTO> findOne(Long id);

    /**
     * Delete the "id" rank.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the rank corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RankDTO> search(String query, Pageable pageable);
}
