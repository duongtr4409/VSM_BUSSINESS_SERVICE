package com.vsm.business.web.rest;

import com.vsm.business.repository.PriorityLevelRepository;
import com.vsm.business.service.PriorityLevelService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.PriorityLevelDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.PriorityLevel}.
 */
//@RestController
@RequestMapping("/api")
public class PriorityLevelResource {

    private final Logger log = LoggerFactory.getLogger(PriorityLevelResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServicePriorityLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriorityLevelService priorityLevelService;

    private final PriorityLevelRepository priorityLevelRepository;

    public PriorityLevelResource(PriorityLevelService priorityLevelService, PriorityLevelRepository priorityLevelRepository) {
        this.priorityLevelService = priorityLevelService;
        this.priorityLevelRepository = priorityLevelRepository;
    }

    /**
     * {@code POST  /priority-levels} : Create a new priorityLevel.
     *
     * @param priorityLevelDTO the priorityLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priorityLevelDTO, or with status {@code 400 (Bad Request)} if the priorityLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/priority-levels")
    public ResponseEntity<PriorityLevelDTO> createPriorityLevel(@RequestBody PriorityLevelDTO priorityLevelDTO) throws URISyntaxException {
        log.debug("REST request to save PriorityLevel : {}", priorityLevelDTO);
        if (priorityLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new priorityLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriorityLevelDTO result = priorityLevelService.save(priorityLevelDTO);
        return ResponseEntity
            .created(new URI("/api/priority-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /priority-levels/:id} : Updates an existing priorityLevel.
     *
     * @param id the id of the priorityLevelDTO to save.
     * @param priorityLevelDTO the priorityLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priorityLevelDTO,
     * or with status {@code 400 (Bad Request)} if the priorityLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priorityLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/priority-levels/{id}")
    public ResponseEntity<PriorityLevelDTO> updatePriorityLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriorityLevelDTO priorityLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PriorityLevel : {}, {}", id, priorityLevelDTO);
        if (priorityLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priorityLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priorityLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PriorityLevelDTO result = priorityLevelService.save(priorityLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priorityLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /priority-levels/:id} : Partial updates given fields of an existing priorityLevel, field will ignore if it is null
     *
     * @param id the id of the priorityLevelDTO to save.
     * @param priorityLevelDTO the priorityLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priorityLevelDTO,
     * or with status {@code 400 (Bad Request)} if the priorityLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the priorityLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the priorityLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/priority-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PriorityLevelDTO> partialUpdatePriorityLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriorityLevelDTO priorityLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PriorityLevel partially : {}, {}", id, priorityLevelDTO);
        if (priorityLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priorityLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priorityLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PriorityLevelDTO> result = priorityLevelService.partialUpdate(priorityLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priorityLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /priority-levels} : get all the priorityLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priorityLevels in body.
     */
    @GetMapping("/priority-levels")
    public ResponseEntity<List<PriorityLevelDTO>> getAllPriorityLevels(Pageable pageable) {
        log.debug("REST request to get a page of PriorityLevels");
        Page<PriorityLevelDTO> page = priorityLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /priority-levels/:id} : get the "id" priorityLevel.
     *
     * @param id the id of the priorityLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priorityLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/priority-levels/{id}")
    public ResponseEntity<PriorityLevelDTO> getPriorityLevel(@PathVariable Long id) {
        log.debug("REST request to get PriorityLevel : {}", id);
        Optional<PriorityLevelDTO> priorityLevelDTO = priorityLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priorityLevelDTO);
    }

    /**
     * {@code DELETE  /priority-levels/:id} : delete the "id" priorityLevel.
     *
     * @param id the id of the priorityLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/priority-levels/{id}")
    public ResponseEntity<Void> deletePriorityLevel(@PathVariable Long id) {
        log.debug("REST request to delete PriorityLevel : {}", id);
        priorityLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/priority-levels?query=:query} : search for the priorityLevel corresponding
     * to the query.
     *
     * @param query the query of the priorityLevel search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/priority-levels")
    public ResponseEntity<List<PriorityLevelDTO>> searchPriorityLevels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PriorityLevels for query {}", query);
        Page<PriorityLevelDTO> page = priorityLevelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
