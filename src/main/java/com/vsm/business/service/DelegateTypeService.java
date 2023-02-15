package com.vsm.business.service;

import com.vsm.business.service.dto.DelegateTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.DelegateType}.
 */
public interface DelegateTypeService {
    /**
     * Save a delegateType.
     *
     * @param delegateTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DelegateTypeDTO save(DelegateTypeDTO delegateTypeDTO);

    /**
     * Partially updates a delegateType.
     *
     * @param delegateTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DelegateTypeDTO> partialUpdate(DelegateTypeDTO delegateTypeDTO);

    /**
     * Get all the delegateTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" delegateType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DelegateTypeDTO> findOne(Long id);

    /**
     * Delete the "id" delegateType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the delegateType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegateTypeDTO> search(String query, Pageable pageable);
}
