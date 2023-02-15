package com.vsm.business.service;

import com.vsm.business.service.dto.InformationInExchangeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.InformationInExchange}.
 */
public interface InformationInExchangeService {
    /**
     * Save a informationInExchange.
     *
     * @param informationInExchangeDTO the entity to save.
     * @return the persisted entity.
     */
    InformationInExchangeDTO save(InformationInExchangeDTO informationInExchangeDTO);

    /**
     * Partially updates a informationInExchange.
     *
     * @param informationInExchangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InformationInExchangeDTO> partialUpdate(InformationInExchangeDTO informationInExchangeDTO);

    /**
     * Get all the informationInExchanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformationInExchangeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" informationInExchange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InformationInExchangeDTO> findOne(Long id);

    /**
     * Delete the "id" informationInExchange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the informationInExchange corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformationInExchangeDTO> search(String query, Pageable pageable);
}
