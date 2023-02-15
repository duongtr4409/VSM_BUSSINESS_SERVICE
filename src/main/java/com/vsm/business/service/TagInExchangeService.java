package com.vsm.business.service;

import com.vsm.business.service.dto.TagInExchangeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.TagInExchange}.
 */
public interface TagInExchangeService {
    /**
     * Save a tagInExchange.
     *
     * @param tagInExchangeDTO the entity to save.
     * @return the persisted entity.
     */
    TagInExchangeDTO save(TagInExchangeDTO tagInExchangeDTO);

    /**
     * Partially updates a tagInExchange.
     *
     * @param tagInExchangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TagInExchangeDTO> partialUpdate(TagInExchangeDTO tagInExchangeDTO);

    /**
     * Get all the tagInExchanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagInExchangeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tagInExchange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagInExchangeDTO> findOne(Long id);

    /**
     * Delete the "id" tagInExchange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the tagInExchange corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagInExchangeDTO> search(String query, Pageable pageable);
}
