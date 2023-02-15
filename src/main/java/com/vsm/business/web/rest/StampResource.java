package com.vsm.business.web.rest;

import com.vsm.business.repository.StampRepository;
import com.vsm.business.service.StampService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.StampDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Stamp}.
 */
//@RestController
@RequestMapping("/api")
public class StampResource {

    private final Logger log = LoggerFactory.getLogger(StampResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStamp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StampService stampService;

    private final StampRepository stampRepository;

    public StampResource(StampService stampService, StampRepository stampRepository) {
        this.stampService = stampService;
        this.stampRepository = stampRepository;
    }

    /**
     * {@code POST  /stamps} : Create a new stamp.
     *
     * @param stampDTO the stampDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stampDTO, or with status {@code 400 (Bad Request)} if the stamp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stamps")
    public ResponseEntity<StampDTO> createStamp(@RequestBody StampDTO stampDTO) throws URISyntaxException {
        log.debug("REST request to save Stamp : {}", stampDTO);
        if (stampDTO.getId() != null) {
            throw new BadRequestAlertException("A new stamp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StampDTO result = stampService.save(stampDTO);
        return ResponseEntity
            .created(new URI("/api/stamps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stamps/:id} : Updates an existing stamp.
     *
     * @param id the id of the stampDTO to save.
     * @param stampDTO the stampDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stampDTO,
     * or with status {@code 400 (Bad Request)} if the stampDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stampDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stamps/{id}")
    public ResponseEntity<StampDTO> updateStamp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StampDTO stampDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stamp : {}, {}", id, stampDTO);
        if (stampDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stampDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stampRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StampDTO result = stampService.save(stampDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stampDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stamps/:id} : Partial updates given fields of an existing stamp, field will ignore if it is null
     *
     * @param id the id of the stampDTO to save.
     * @param stampDTO the stampDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stampDTO,
     * or with status {@code 400 (Bad Request)} if the stampDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stampDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stampDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stamps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StampDTO> partialUpdateStamp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StampDTO stampDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stamp partially : {}, {}", id, stampDTO);
        if (stampDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stampDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stampRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StampDTO> result = stampService.partialUpdate(stampDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stampDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stamps} : get all the stamps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stamps in body.
     */
    @GetMapping("/stamps")
    public ResponseEntity<List<StampDTO>> getAllStamps(Pageable pageable) {
        log.debug("REST request to get a page of Stamps");
        Page<StampDTO> page = stampService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stamps/:id} : get the "id" stamp.
     *
     * @param id the id of the stampDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stampDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stamps/{id}")
    public ResponseEntity<StampDTO> getStamp(@PathVariable Long id) {
        log.debug("REST request to get Stamp : {}", id);
        Optional<StampDTO> stampDTO = stampService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stampDTO);
    }

    /**
     * {@code DELETE  /stamps/:id} : delete the "id" stamp.
     *
     * @param id the id of the stampDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stamps/{id}")
    public ResponseEntity<Void> deleteStamp(@PathVariable Long id) {
        log.debug("REST request to delete Stamp : {}", id);
        stampService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/stamps?query=:query} : search for the stamp corresponding
     * to the query.
     *
     * @param query the query of the stamp search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stamps")
    public ResponseEntity<List<StampDTO>> searchStamps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Stamps for query {}", query);
        Page<StampDTO> page = stampService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
