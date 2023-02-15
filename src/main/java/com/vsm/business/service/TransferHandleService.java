package com.vsm.business.service;

import com.vsm.business.service.dto.TransferHandleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.TransferHandle}.
 */
public interface TransferHandleService {
    /**
     * Save a transferHandle.
     *
     * @param transferHandleDTO the entity to save.
     * @return the persisted entity.
     */
    TransferHandleDTO save(TransferHandleDTO transferHandleDTO);

    /**
     * Partially updates a transferHandle.
     *
     * @param transferHandleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransferHandleDTO> partialUpdate(TransferHandleDTO transferHandleDTO);

    /**
     * Get all the transferHandles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferHandleDTO> findAll(Pageable pageable);

    /**
     * Get all the transferHandles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferHandleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" transferHandle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransferHandleDTO> findOne(Long id);

    /**
     * Delete the "id" transferHandle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the transferHandle corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferHandleDTO> search(String query, Pageable pageable);
}
