package com.vsm.business.service;

import com.vsm.business.service.dto.MallAndStallDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.MallAndStall}.
 */
public interface MallAndStallService {
    /**
     * Save a mallAndStall.
     *
     * @param mallAndStallDTO the entity to save.
     * @return the persisted entity.
     */
    MallAndStallDTO save(MallAndStallDTO mallAndStallDTO);

    /**
     * Partially updates a mallAndStall.
     *
     * @param mallAndStallDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MallAndStallDTO> partialUpdate(MallAndStallDTO mallAndStallDTO);

    /**
     * Get all the mallAndStalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MallAndStallDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mallAndStall.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MallAndStallDTO> findOne(Long id);

    /**
     * Delete the "id" mallAndStall.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mallAndStall corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MallAndStallDTO> search(String query, Pageable pageable);
}
