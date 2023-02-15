package com.vsm.business.service;

import com.vsm.business.service.dto.UserInStepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.UserInStep}.
 */
public interface UserInStepService {
    /**
     * Save a userInStep.
     *
     * @param userInStepDTO the entity to save.
     * @return the persisted entity.
     */
    UserInStepDTO save(UserInStepDTO userInStepDTO);

    /**
     * Partially updates a userInStep.
     *
     * @param userInStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserInStepDTO> partialUpdate(UserInStepDTO userInStepDTO);

    /**
     * Get all the userInSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserInStepDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userInStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserInStepDTO> findOne(Long id);

    /**
     * Delete the "id" userInStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the userInStep corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserInStepDTO> search(String query, Pageable pageable);
}
