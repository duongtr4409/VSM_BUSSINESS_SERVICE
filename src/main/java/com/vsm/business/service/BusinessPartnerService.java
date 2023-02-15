package com.vsm.business.service;

import com.vsm.business.service.dto.BusinessPartnerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.BusinessPartner}.
 */
public interface BusinessPartnerService {
    /**
     * Save a businessPartner.
     *
     * @param businessPartnerDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessPartnerDTO save(BusinessPartnerDTO businessPartnerDTO);

    /**
     * Partially updates a businessPartner.
     *
     * @param businessPartnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessPartnerDTO> partialUpdate(BusinessPartnerDTO businessPartnerDTO);

    /**
     * Get all the businessPartners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessPartnerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" businessPartner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessPartnerDTO> findOne(Long id);

    /**
     * Delete the "id" businessPartner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the businessPartner corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessPartnerDTO> search(String query, Pageable pageable);
}
