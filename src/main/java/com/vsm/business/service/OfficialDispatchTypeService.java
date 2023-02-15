package com.vsm.business.service;

import com.vsm.business.service.dto.OfficialDispatchTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.OfficialDispatchType}.
 */
public interface OfficialDispatchTypeService {
    /**
     * Save a officialDispatchType.
     *
     * @param officialDispatchTypeDTO the entity to save.
     * @return the persisted entity.
     */
    OfficialDispatchTypeDTO save(OfficialDispatchTypeDTO officialDispatchTypeDTO);

    /**
     * Partially updates a officialDispatchType.
     *
     * @param officialDispatchTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfficialDispatchTypeDTO> partialUpdate(OfficialDispatchTypeDTO officialDispatchTypeDTO);

    /**
     * Get all the officialDispatchTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" officialDispatchType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficialDispatchTypeDTO> findOne(Long id);

    /**
     * Delete the "id" officialDispatchType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the officialDispatchType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchTypeDTO> search(String query, Pageable pageable);
}
