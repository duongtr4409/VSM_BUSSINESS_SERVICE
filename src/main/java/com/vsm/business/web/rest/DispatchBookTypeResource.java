package com.vsm.business.web.rest;

import com.vsm.business.repository.DispatchBookTypeRepository;
import com.vsm.business.service.DispatchBookTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DispatchBookTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DispatchBookType}.
 */
//@RestController
@RequestMapping("/api")
public class DispatchBookTypeResource {

    private final Logger log = LoggerFactory.getLogger(DispatchBookTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDispatchBookType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispatchBookTypeService dispatchBookTypeService;

    private final DispatchBookTypeRepository dispatchBookTypeRepository;

    public DispatchBookTypeResource(
        DispatchBookTypeService dispatchBookTypeService,
        DispatchBookTypeRepository dispatchBookTypeRepository
    ) {
        this.dispatchBookTypeService = dispatchBookTypeService;
        this.dispatchBookTypeRepository = dispatchBookTypeRepository;
    }

    /**
     * {@code POST  /dispatch-book-types} : Create a new dispatchBookType.
     *
     * @param dispatchBookTypeDTO the dispatchBookTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispatchBookTypeDTO, or with status {@code 400 (Bad Request)} if the dispatchBookType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispatch-book-types")
    public ResponseEntity<DispatchBookTypeDTO> createDispatchBookType(@RequestBody DispatchBookTypeDTO dispatchBookTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save DispatchBookType : {}", dispatchBookTypeDTO);
        if (dispatchBookTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispatchBookType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispatchBookTypeDTO result = dispatchBookTypeService.save(dispatchBookTypeDTO);
        return ResponseEntity
            .created(new URI("/api/dispatch-book-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispatch-book-types/:id} : Updates an existing dispatchBookType.
     *
     * @param id the id of the dispatchBookTypeDTO to save.
     * @param dispatchBookTypeDTO the dispatchBookTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookTypeDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispatch-book-types/{id}")
    public ResponseEntity<DispatchBookTypeDTO> updateDispatchBookType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookTypeDTO dispatchBookTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DispatchBookType : {}, {}", id, dispatchBookTypeDTO);
        if (dispatchBookTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DispatchBookTypeDTO result = dispatchBookTypeService.save(dispatchBookTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispatchBookTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dispatch-book-types/:id} : Partial updates given fields of an existing dispatchBookType, field will ignore if it is null
     *
     * @param id the id of the dispatchBookTypeDTO to save.
     * @param dispatchBookTypeDTO the dispatchBookTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookTypeDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispatchBookTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dispatch-book-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispatchBookTypeDTO> partialUpdateDispatchBookType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookTypeDTO dispatchBookTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DispatchBookType partially : {}, {}", id, dispatchBookTypeDTO);
        if (dispatchBookTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispatchBookTypeDTO> result = dispatchBookTypeService.partialUpdate(dispatchBookTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispatchBookTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dispatch-book-types} : get all the dispatchBookTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispatchBookTypes in body.
     */
    @GetMapping("/dispatch-book-types")
    public ResponseEntity<List<DispatchBookTypeDTO>> getAllDispatchBookTypes(Pageable pageable) {
        log.debug("REST request to get a page of DispatchBookTypes");
        Page<DispatchBookTypeDTO> page = dispatchBookTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispatch-book-types/:id} : get the "id" dispatchBookType.
     *
     * @param id the id of the dispatchBookTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispatchBookTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispatch-book-types/{id}")
    public ResponseEntity<DispatchBookTypeDTO> getDispatchBookType(@PathVariable Long id) {
        log.debug("REST request to get DispatchBookType : {}", id);
        Optional<DispatchBookTypeDTO> dispatchBookTypeDTO = dispatchBookTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispatchBookTypeDTO);
    }

    /**
     * {@code DELETE  /dispatch-book-types/:id} : delete the "id" dispatchBookType.
     *
     * @param id the id of the dispatchBookTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispatch-book-types/{id}")
    public ResponseEntity<Void> deleteDispatchBookType(@PathVariable Long id) {
        log.debug("REST request to delete DispatchBookType : {}", id);
        dispatchBookTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/dispatch-book-types?query=:query} : search for the dispatchBookType corresponding
     * to the query.
     *
     * @param query the query of the dispatchBookType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dispatch-book-types")
    public ResponseEntity<List<DispatchBookTypeDTO>> searchDispatchBookTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DispatchBookTypes for query {}", query);
        Page<DispatchBookTypeDTO> page = dispatchBookTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
