package com.vsm.business.service;

import com.vsm.business.service.dto.RequestDataDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.RequestData}.
 */
public interface RequestDataService {
    /**
     * Save a requestData.
     *
     * @param requestDataDTO the entity to save.
     * @return the persisted entity.
     */
    RequestDataDTO save(RequestDataDTO requestDataDTO);

    /**
     * Partially updates a requestData.
     *
     * @param requestDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestDataDTO> partialUpdate(RequestDataDTO requestDataDTO);

    /**
     * Get all the requestData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestDataDTO> findAll(Pageable pageable);
//    /**
//     * Get all the RequestDataDTO where ManageStampInfo is {@code null}.
//     *
//     * @return the {@link List} of entities.
//     */
//    List<RequestDataDTO> findAllWhereManageStampInfoIsNull();

    /**
     * Get all the requestData with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestDataDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" requestData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestDataDTO> findOne(Long id);

    /**
     * Delete the "id" requestData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the requestData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestDataDTO> search(String query, Pageable pageable);
}
