package com.vsm.business.service;

import com.vsm.business.service.dto.DispatchBookDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.DispatchBook}.
 */
public interface DispatchBookService {
    /**
     * Save a dispatchBook.
     *
     * @param dispatchBookDTO the entity to save.
     * @return the persisted entity.
     */
    DispatchBookDTO save(DispatchBookDTO dispatchBookDTO);

    /**
     * Partially updates a dispatchBook.
     *
     * @param dispatchBookDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispatchBookDTO> partialUpdate(DispatchBookDTO dispatchBookDTO);

    /**
     * Get all the dispatchBooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispatchBookDTO> findAll(Pageable pageable);

    /**
     * Get all the dispatchBooks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispatchBookDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dispatchBook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispatchBookDTO> findOne(Long id);

    /**
     * Delete the "id" dispatchBook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dispatchBook corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispatchBookDTO> search(String query, Pageable pageable);
}
