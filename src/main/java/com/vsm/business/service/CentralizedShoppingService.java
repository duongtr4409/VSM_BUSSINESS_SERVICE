package com.vsm.business.service;

import com.vsm.business.service.dto.CentralizedShoppingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.CentralizedShopping}.
 */
public interface CentralizedShoppingService {
    /**
     * Save a centralizedShopping.
     *
     * @param centralizedShoppingDTO the entity to save.
     * @return the persisted entity.
     */
    CentralizedShoppingDTO save(CentralizedShoppingDTO centralizedShoppingDTO);

    /**
     * Partially updates a centralizedShopping.
     *
     * @param centralizedShoppingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CentralizedShoppingDTO> partialUpdate(CentralizedShoppingDTO centralizedShoppingDTO);

    /**
     * Get all the centralizedShoppings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CentralizedShoppingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" centralizedShopping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentralizedShoppingDTO> findOne(Long id);

    /**
     * Delete the "id" centralizedShopping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the centralizedShopping corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CentralizedShoppingDTO> search(String query, Pageable pageable);
}
