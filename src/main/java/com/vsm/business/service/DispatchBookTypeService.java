package com.vsm.business.service;

import com.vsm.business.service.dto.DispatchBookTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.DispatchBookType}.
 */
public interface DispatchBookTypeService {
    /**
     * Save a dispatchBookType.
     *
     * @param dispatchBookTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DispatchBookTypeDTO save(DispatchBookTypeDTO dispatchBookTypeDTO);

    /**
     * Partially updates a dispatchBookType.
     *
     * @param dispatchBookTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispatchBookTypeDTO> partialUpdate(DispatchBookTypeDTO dispatchBookTypeDTO);

    /**
     * Get all the dispatchBookTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispatchBookTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dispatchBookType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispatchBookTypeDTO> findOne(Long id);

    /**
     * Delete the "id" dispatchBookType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dispatchBookType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispatchBookTypeDTO> search(String query, Pageable pageable);
}
