package com.vsm.business.service;

import com.vsm.business.service.dto.ReqdataChangeHisDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ReqdataChangeHis}.
 */
public interface ReqdataChangeHisService {
    /**
     * Save a reqdataChangeHis.
     *
     * @param reqdataChangeHisDTO the entity to save.
     * @return the persisted entity.
     */
    ReqdataChangeHisDTO save(ReqdataChangeHisDTO reqdataChangeHisDTO);

    /**
     * Partially updates a reqdataChangeHis.
     *
     * @param reqdataChangeHisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReqdataChangeHisDTO> partialUpdate(ReqdataChangeHisDTO reqdataChangeHisDTO);

    /**
     * Get all the reqdataChangeHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReqdataChangeHisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" reqdataChangeHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReqdataChangeHisDTO> findOne(Long id);

    /**
     * Delete the "id" reqdataChangeHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the reqdataChangeHis corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReqdataChangeHisDTO> search(String query, Pageable pageable);
}
