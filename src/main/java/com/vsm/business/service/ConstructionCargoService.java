package com.vsm.business.service;

import com.vsm.business.service.dto.ConstructionCargoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ConstructionCargo}.
 */
public interface ConstructionCargoService {
    /**
     * Save a constructionCargo.
     *
     * @param constructionCargoDTO the entity to save.
     * @return the persisted entity.
     */
    ConstructionCargoDTO save(ConstructionCargoDTO constructionCargoDTO);

    /**
     * Partially updates a constructionCargo.
     *
     * @param constructionCargoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConstructionCargoDTO> partialUpdate(ConstructionCargoDTO constructionCargoDTO);

    /**
     * Get all the constructionCargos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConstructionCargoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" constructionCargo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConstructionCargoDTO> findOne(Long id);

    /**
     * Delete the "id" constructionCargo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the constructionCargo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConstructionCargoDTO> search(String query, Pageable pageable);
}
