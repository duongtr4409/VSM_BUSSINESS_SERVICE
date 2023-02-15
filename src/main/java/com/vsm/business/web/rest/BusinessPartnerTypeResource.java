package com.vsm.business.web.rest;

import com.vsm.business.repository.BusinessPartnerTypeRepository;
import com.vsm.business.service.BusinessPartnerTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.BusinessPartnerTypeDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.BusinessPartnerType}.
 */
//@RestController
@RequestMapping("/api")
public class BusinessPartnerTypeResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceBusinessPartnerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessPartnerTypeService businessPartnerTypeService;

    private final BusinessPartnerTypeRepository businessPartnerTypeRepository;

    public BusinessPartnerTypeResource(
        BusinessPartnerTypeService businessPartnerTypeService,
        BusinessPartnerTypeRepository businessPartnerTypeRepository
    ) {
        this.businessPartnerTypeService = businessPartnerTypeService;
        this.businessPartnerTypeRepository = businessPartnerTypeRepository;
    }

    /**
     * {@code POST  /business-partner-types} : Create a new businessPartnerType.
     *
     * @param businessPartnerTypeDTO the businessPartnerTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessPartnerTypeDTO, or with status {@code 400 (Bad Request)} if the businessPartnerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-partner-types")
    public ResponseEntity<BusinessPartnerTypeDTO> createBusinessPartnerType(@RequestBody BusinessPartnerTypeDTO businessPartnerTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessPartnerType : {}", businessPartnerTypeDTO);
        if (businessPartnerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessPartnerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessPartnerTypeDTO result = businessPartnerTypeService.save(businessPartnerTypeDTO);
        return ResponseEntity
            .created(new URI("/api/business-partner-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-partner-types/:id} : Updates an existing businessPartnerType.
     *
     * @param id the id of the businessPartnerTypeDTO to save.
     * @param businessPartnerTypeDTO the businessPartnerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-partner-types/{id}")
    public ResponseEntity<BusinessPartnerTypeDTO> updateBusinessPartnerType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessPartnerTypeDTO businessPartnerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessPartnerType : {}, {}", id, businessPartnerTypeDTO);
        if (businessPartnerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessPartnerTypeDTO result = businessPartnerTypeService.save(businessPartnerTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessPartnerTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-partner-types/:id} : Partial updates given fields of an existing businessPartnerType, field will ignore if it is null
     *
     * @param id the id of the businessPartnerTypeDTO to save.
     * @param businessPartnerTypeDTO the businessPartnerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessPartnerTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-partner-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessPartnerTypeDTO> partialUpdateBusinessPartnerType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessPartnerTypeDTO businessPartnerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessPartnerType partially : {}, {}", id, businessPartnerTypeDTO);
        if (businessPartnerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessPartnerTypeDTO> result = businessPartnerTypeService.partialUpdate(businessPartnerTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessPartnerTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-partner-types} : get all the businessPartnerTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessPartnerTypes in body.
     */
    @GetMapping("/business-partner-types")
    public ResponseEntity<List<BusinessPartnerTypeDTO>> getAllBusinessPartnerTypes(Pageable pageable) {
        log.debug("REST request to get a page of BusinessPartnerTypes");
        Page<BusinessPartnerTypeDTO> page = businessPartnerTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-partner-types/:id} : get the "id" businessPartnerType.
     *
     * @param id the id of the businessPartnerTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessPartnerTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-partner-types/{id}")
    public ResponseEntity<BusinessPartnerTypeDTO> getBusinessPartnerType(@PathVariable Long id) {
        log.debug("REST request to get BusinessPartnerType : {}", id);
        Optional<BusinessPartnerTypeDTO> businessPartnerTypeDTO = businessPartnerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessPartnerTypeDTO);
    }

    /**
     * {@code DELETE  /business-partner-types/:id} : delete the "id" businessPartnerType.
     *
     * @param id the id of the businessPartnerTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-partner-types/{id}")
    public ResponseEntity<Void> deleteBusinessPartnerType(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartnerType : {}", id);
        businessPartnerTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/business-partner-types?query=:query} : search for the businessPartnerType corresponding
     * to the query.
     *
     * @param query the query of the businessPartnerType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/business-partner-types")
    public ResponseEntity<List<BusinessPartnerTypeDTO>> searchBusinessPartnerTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BusinessPartnerTypes for query {}", query);
        Page<BusinessPartnerTypeDTO> page = businessPartnerTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
