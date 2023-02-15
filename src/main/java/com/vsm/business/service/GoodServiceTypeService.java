package com.vsm.business.service;

import com.vsm.business.service.dto.GoodServiceTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.GoodServiceType}.
 */
public interface GoodServiceTypeService {
    /**
     * Save a goodServiceType.
     *
     * @param goodServiceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    GoodServiceTypeDTO save(GoodServiceTypeDTO goodServiceTypeDTO);

    /**
     * Partially updates a goodServiceType.
     *
     * @param goodServiceTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GoodServiceTypeDTO> partialUpdate(GoodServiceTypeDTO goodServiceTypeDTO);

    /**
     * Get all the goodServiceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoodServiceTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" goodServiceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoodServiceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" goodServiceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the goodServiceType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoodServiceTypeDTO> search(String query, Pageable pageable);
}
