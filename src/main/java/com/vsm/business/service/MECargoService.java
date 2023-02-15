package com.vsm.business.service;

import com.vsm.business.service.dto.MECargoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.MECargo}.
 */
public interface MECargoService {
    /**
     * Save a mECargo.
     *
     * @param mECargoDTO the entity to save.
     * @return the persisted entity.
     */
    MECargoDTO save(MECargoDTO mECargoDTO);

    /**
     * Partially updates a mECargo.
     *
     * @param mECargoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MECargoDTO> partialUpdate(MECargoDTO mECargoDTO);

    /**
     * Get all the mECargos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MECargoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mECargo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MECargoDTO> findOne(Long id);

    /**
     * Delete the "id" mECargo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mECargo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MECargoDTO> search(String query, Pageable pageable);
}
