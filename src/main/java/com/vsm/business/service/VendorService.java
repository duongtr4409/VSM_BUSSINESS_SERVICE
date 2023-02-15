package com.vsm.business.service;

import com.vsm.business.service.dto.VendorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.Vendor}.
 */
public interface VendorService {
    /**
     * Save a vendor.
     *
     * @param vendorDTO the entity to save.
     * @return the persisted entity.
     */
    VendorDTO save(VendorDTO vendorDTO);

    /**
     * Partially updates a vendor.
     *
     * @param vendorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VendorDTO> partialUpdate(VendorDTO vendorDTO);

    /**
     * Get all the vendors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" vendor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendorDTO> findOne(Long id);

    /**
     * Delete the "id" vendor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the vendor corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendorDTO> search(String query, Pageable pageable);
}
