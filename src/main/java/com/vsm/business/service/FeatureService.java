package com.vsm.business.service;

import com.vsm.business.service.dto.FeatureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Feature}.
 */
public interface FeatureService {
    /**
     * Save a feature.
     *
     * @param featureDTO the entity to save.
     * @return the persisted entity.
     */
    FeatureDTO save(FeatureDTO featureDTO);

    /**
     * Partially updates a feature.
     *
     * @param featureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeatureDTO> partialUpdate(FeatureDTO featureDTO);

    /**
     * Get all the features.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeatureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" feature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeatureDTO> findOne(Long id);

    /**
     * Delete the "id" feature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the feature corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeatureDTO> search(String query, Pageable pageable);
}
