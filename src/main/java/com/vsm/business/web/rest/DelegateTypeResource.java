package com.vsm.business.web.rest;

import com.vsm.business.repository.DelegateTypeRepository;
import com.vsm.business.service.DelegateTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DelegateTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DelegateType}.
 */
//@RestController
@RequestMapping("/api")
public class DelegateTypeResource {

    private final Logger log = LoggerFactory.getLogger(DelegateTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDelegateType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegateTypeService delegateTypeService;

    private final DelegateTypeRepository delegateTypeRepository;

    public DelegateTypeResource(DelegateTypeService delegateTypeService, DelegateTypeRepository delegateTypeRepository) {
        this.delegateTypeService = delegateTypeService;
        this.delegateTypeRepository = delegateTypeRepository;
    }

    /**
     * {@code POST  /delegate-types} : Create a new delegateType.
     *
     * @param delegateTypeDTO the delegateTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegateTypeDTO, or with status {@code 400 (Bad Request)} if the delegateType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegate-types")
    public ResponseEntity<DelegateTypeDTO> createDelegateType(@RequestBody DelegateTypeDTO delegateTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DelegateType : {}", delegateTypeDTO);
        if (delegateTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new delegateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DelegateTypeDTO result = delegateTypeService.save(delegateTypeDTO);
        return ResponseEntity
            .created(new URI("/api/delegate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegate-types/:id} : Updates an existing delegateType.
     *
     * @param id the id of the delegateTypeDTO to save.
     * @param delegateTypeDTO the delegateTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateTypeDTO,
     * or with status {@code 400 (Bad Request)} if the delegateTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegateTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegate-types/{id}")
    public ResponseEntity<DelegateTypeDTO> updateDelegateType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateTypeDTO delegateTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DelegateType : {}, {}", id, delegateTypeDTO);
        if (delegateTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DelegateTypeDTO result = delegateTypeService.save(delegateTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delegate-types/:id} : Partial updates given fields of an existing delegateType, field will ignore if it is null
     *
     * @param id the id of the delegateTypeDTO to save.
     * @param delegateTypeDTO the delegateTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateTypeDTO,
     * or with status {@code 400 (Bad Request)} if the delegateTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the delegateTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the delegateTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delegate-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DelegateTypeDTO> partialUpdateDelegateType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateTypeDTO delegateTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DelegateType partially : {}, {}", id, delegateTypeDTO);
        if (delegateTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DelegateTypeDTO> result = delegateTypeService.partialUpdate(delegateTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delegate-types} : get all the delegateTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegateTypes in body.
     */
    @GetMapping("/delegate-types")
    public ResponseEntity<List<DelegateTypeDTO>> getAllDelegateTypes(Pageable pageable) {
        log.debug("REST request to get a page of DelegateTypes");
        Page<DelegateTypeDTO> page = delegateTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delegate-types/:id} : get the "id" delegateType.
     *
     * @param id the id of the delegateTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegateTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegate-types/{id}")
    public ResponseEntity<DelegateTypeDTO> getDelegateType(@PathVariable Long id) {
        log.debug("REST request to get DelegateType : {}", id);
        Optional<DelegateTypeDTO> delegateTypeDTO = delegateTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegateTypeDTO);
    }

    /**
     * {@code DELETE  /delegate-types/:id} : delete the "id" delegateType.
     *
     * @param id the id of the delegateTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegate-types/{id}")
    public ResponseEntity<Void> deleteDelegateType(@PathVariable Long id) {
        log.debug("REST request to delete DelegateType : {}", id);
        delegateTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/delegate-types?query=:query} : search for the delegateType corresponding
     * to the query.
     *
     * @param query the query of the delegateType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/delegate-types")
    public ResponseEntity<List<DelegateTypeDTO>> searchDelegateTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DelegateTypes for query {}", query);
        Page<DelegateTypeDTO> page = delegateTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
