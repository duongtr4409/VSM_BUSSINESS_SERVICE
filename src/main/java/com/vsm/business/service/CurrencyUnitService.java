package com.vsm.business.service;

import com.vsm.business.service.dto.CurrencyUnitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.CurrencyUnit}.
 */
public interface CurrencyUnitService {
    /**
     * Save a currencyUnit.
     *
     * @param currencyUnitDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyUnitDTO save(CurrencyUnitDTO currencyUnitDTO);

    /**
     * Partially updates a currencyUnit.
     *
     * @param currencyUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CurrencyUnitDTO> partialUpdate(CurrencyUnitDTO currencyUnitDTO);

    /**
     * Get all the currencyUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyUnitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" currencyUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyUnitDTO> findOne(Long id);

    /**
     * Delete the "id" currencyUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the currencyUnit corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyUnitDTO> search(String query, Pageable pageable);
}
