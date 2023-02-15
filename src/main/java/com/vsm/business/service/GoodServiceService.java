package com.vsm.business.service;

import com.vsm.business.service.dto.GoodServiceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.GoodService}.
 */
public interface GoodServiceService {
    /**
     * Save a goodService.
     *
     * @param goodServiceDTO the entity to save.
     * @return the persisted entity.
     */
    GoodServiceDTO save(GoodServiceDTO goodServiceDTO);

    /**
     * Partially updates a goodService.
     *
     * @param goodServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoodServiceDTO> partialUpdate(GoodServiceDTO goodServiceDTO);

    /**
     * Get all the goodServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoodServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" goodService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoodServiceDTO> findOne(Long id);

    /**
     * Delete the "id" goodService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the goodService corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoodServiceDTO> search(String query, Pageable pageable);
}
