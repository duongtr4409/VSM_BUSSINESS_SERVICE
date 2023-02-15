package com.vsm.business.service;

import com.vsm.business.service.dto.ReqdataProcessHisDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.ReqdataProcessHis}.
 */
public interface ReqdataProcessHisService {
    /**
     * Save a reqdataProcessHis.
     *
     * @param reqdataProcessHisDTO the entity to save.
     * @return the persisted entity.
     */
    ReqdataProcessHisDTO save(ReqdataProcessHisDTO reqdataProcessHisDTO);

    /**
     * Partially updates a reqdataProcessHis.
     *
     * @param reqdataProcessHisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReqdataProcessHisDTO> partialUpdate(ReqdataProcessHisDTO reqdataProcessHisDTO);

    /**
     * Get all the reqdataProcessHis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReqdataProcessHisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" reqdataProcessHis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReqdataProcessHisDTO> findOne(Long id);

    /**
     * Delete the "id" reqdataProcessHis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the reqdataProcessHis corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReqdataProcessHisDTO> search(String query, Pageable pageable);
}
