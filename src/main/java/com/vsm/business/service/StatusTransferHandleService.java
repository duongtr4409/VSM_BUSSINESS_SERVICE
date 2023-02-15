package com.vsm.business.service;

import com.vsm.business.service.dto.StatusTransferHandleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.StatusTransferHandle}.
 */
public interface StatusTransferHandleService {
    /**
     * Save a statusTransferHandle.
     *
     * @param statusTransferHandleDTO the entity to save.
     * @return the persisted entity.
     */
    StatusTransferHandleDTO save(StatusTransferHandleDTO statusTransferHandleDTO);

    /**
     * Partially updates a statusTransferHandle.
     *
     * @param statusTransferHandleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatusTransferHandleDTO> partialUpdate(StatusTransferHandleDTO statusTransferHandleDTO);

    /**
     * Get all the statusTransferHandles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusTransferHandleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" statusTransferHandle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatusTransferHandleDTO> findOne(Long id);

    /**
     * Delete the "id" statusTransferHandle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the statusTransferHandle corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusTransferHandleDTO> search(String query, Pageable pageable);
}
