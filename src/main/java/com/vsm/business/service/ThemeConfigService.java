package com.vsm.business.service;

import com.vsm.business.service.dto.ThemeConfigDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ThemeConfig}.
 */
public interface ThemeConfigService {
    /**
     * Save a themeConfig.
     *
     * @param themeConfigDTO the entity to save.
     * @return the persisted entity.
     */
    ThemeConfigDTO save(ThemeConfigDTO themeConfigDTO);

    /**
     * Partially updates a themeConfig.
     *
     * @param themeConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThemeConfigDTO> partialUpdate(ThemeConfigDTO themeConfigDTO);

    /**
     * Get all the themeConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeConfigDTO> findAll(Pageable pageable);

    /**
     * Get the "id" themeConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThemeConfigDTO> findOne(Long id);

    /**
     * Delete the "id" themeConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the themeConfig corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThemeConfigDTO> search(String query, Pageable pageable);
}
