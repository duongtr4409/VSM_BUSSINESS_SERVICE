package com.vsm.business.service;

import com.vsm.business.service.dto.BusinessPartnerTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.BusinessPartnerType}.
 */
public interface BusinessPartnerTypeService {
    /**
     * Save a businessPartnerType.
     *
     * @param businessPartnerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessPartnerTypeDTO save(BusinessPartnerTypeDTO businessPartnerTypeDTO);

    /**
     * Partially updates a businessPartnerType.
     *
     * @param businessPartnerTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessPartnerTypeDTO> partialUpdate(BusinessPartnerTypeDTO businessPartnerTypeDTO);

    /**
     * Get all the businessPartnerTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessPartnerTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" businessPartnerType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessPartnerTypeDTO> findOne(Long id);

    /**
     * Delete the "id" businessPartnerType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the businessPartnerType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessPartnerTypeDTO> search(String query, Pageable pageable);
}
