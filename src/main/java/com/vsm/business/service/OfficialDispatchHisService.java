package com.vsm.business.service;

import com.vsm.business.service.dto.OfficialDispatchHisDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.OfficialDispatchHis}.
 */
public interface OfficialDispatchHisService {
    /**
     * Save a officialDispatchHis.
     *
     * @param officialDispatchHisDTO the entity to save.
     * @return the persisted entity.
     */
    OfficialDispatchHisDTO save(OfficialDispatchHisDTO officialDispatchHisDTO);

    /**
     * Partially updates a officialDispatchHis.
     *
     * @param officialDispatchHisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfficialDispatchHisDTO> partialUpdate(OfficialDispatchHisDTO officialDispatchHisDTO);

    /**
     * Get all the officialDispatchHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchHisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" officialDispatchHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficialDispatchHisDTO> findOne(Long id);

    /**
     * Delete the "id" officialDispatchHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the officialDispatchHis corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficialDispatchHisDTO> search(String query, Pageable pageable);
}
