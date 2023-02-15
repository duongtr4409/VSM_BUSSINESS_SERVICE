package com.vsm.business.service;

import com.vsm.business.service.dto.RequestTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RequestType}.
 */
public interface RequestTypeService {
    /**
     * Save a requestType.
     *
     * @param requestTypeDTO the entity to save.
     * @return the persisted entity.
     */
    RequestTypeDTO save(RequestTypeDTO requestTypeDTO);

    /**
     * Partially updates a requestType.
     *
     * @param requestTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestTypeDTO> partialUpdate(RequestTypeDTO requestTypeDTO);

    /**
     * Get all the requestTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" requestType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestTypeDTO> findOne(Long id);

    /**
     * Delete the "id" requestType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the requestType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestTypeDTO> search(String query, Pageable pageable);
}
