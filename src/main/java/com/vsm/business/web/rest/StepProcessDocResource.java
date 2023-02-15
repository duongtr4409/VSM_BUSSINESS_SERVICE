package com.vsm.business.web.rest;

import com.vsm.business.repository.StepProcessDocRepository;
import com.vsm.business.service.StepProcessDocService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.StepProcessDocDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.StepProcessDoc}.
 */
//@RestController
@RequestMapping("/api")
public class StepProcessDocResource {

    private final Logger log = LoggerFactory.getLogger(StepProcessDocResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStepProcessDoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepProcessDocService stepProcessDocService;

    private final StepProcessDocRepository stepProcessDocRepository;

    public StepProcessDocResource(StepProcessDocService stepProcessDocService, StepProcessDocRepository stepProcessDocRepository) {
        this.stepProcessDocService = stepProcessDocService;
        this.stepProcessDocRepository = stepProcessDocRepository;
    }

    /**
     * {@code POST  /step-process-docs} : Create a new stepProcessDoc.
     *
     * @param stepProcessDocDTO the stepProcessDocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepProcessDocDTO, or with status {@code 400 (Bad Request)} if the stepProcessDoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-process-docs")
    public ResponseEntity<StepProcessDocDTO> createStepProcessDoc(@RequestBody StepProcessDocDTO stepProcessDocDTO)
        throws URISyntaxException {
        log.debug("REST request to save StepProcessDoc : {}", stepProcessDocDTO);
        if (stepProcessDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new stepProcessDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepProcessDocDTO result = stepProcessDocService.save(stepProcessDocDTO);
        return ResponseEntity
            .created(new URI("/api/step-process-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-process-docs/:id} : Updates an existing stepProcessDoc.
     *
     * @param id the id of the stepProcessDocDTO to save.
     * @param stepProcessDocDTO the stepProcessDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepProcessDocDTO,
     * or with status {@code 400 (Bad Request)} if the stepProcessDocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepProcessDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-process-docs/{id}")
    public ResponseEntity<StepProcessDocDTO> updateStepProcessDoc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepProcessDocDTO stepProcessDocDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StepProcessDoc : {}, {}", id, stepProcessDocDTO);
        if (stepProcessDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepProcessDocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepProcessDocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepProcessDocDTO result = stepProcessDocService.save(stepProcessDocDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepProcessDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-process-docs/:id} : Partial updates given fields of an existing stepProcessDoc, field will ignore if it is null
     *
     * @param id the id of the stepProcessDocDTO to save.
     * @param stepProcessDocDTO the stepProcessDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepProcessDocDTO,
     * or with status {@code 400 (Bad Request)} if the stepProcessDocDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepProcessDocDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepProcessDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-process-docs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepProcessDocDTO> partialUpdateStepProcessDoc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepProcessDocDTO stepProcessDocDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepProcessDoc partially : {}, {}", id, stepProcessDocDTO);
        if (stepProcessDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepProcessDocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepProcessDocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepProcessDocDTO> result = stepProcessDocService.partialUpdate(stepProcessDocDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepProcessDocDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /step-process-docs} : get all the stepProcessDocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepProcessDocs in body.
     */
    @GetMapping("/step-process-docs")
    public ResponseEntity<List<StepProcessDocDTO>> getAllStepProcessDocs(Pageable pageable) {
        log.debug("REST request to get a page of StepProcessDocs");
        Page<StepProcessDocDTO> page = stepProcessDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-process-docs/:id} : get the "id" stepProcessDoc.
     *
     * @param id the id of the stepProcessDocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepProcessDocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-process-docs/{id}")
    public ResponseEntity<StepProcessDocDTO> getStepProcessDoc(@PathVariable Long id) {
        log.debug("REST request to get StepProcessDoc : {}", id);
        Optional<StepProcessDocDTO> stepProcessDocDTO = stepProcessDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepProcessDocDTO);
    }

    /**
     * {@code DELETE  /step-process-docs/:id} : delete the "id" stepProcessDoc.
     *
     * @param id the id of the stepProcessDocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-process-docs/{id}")
    public ResponseEntity<Void> deleteStepProcessDoc(@PathVariable Long id) {
        log.debug("REST request to delete StepProcessDoc : {}", id);
        stepProcessDocService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/step-process-docs?query=:query} : search for the stepProcessDoc corresponding
     * to the query.
     *
     * @param query the query of the stepProcessDoc search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/step-process-docs")
    public ResponseEntity<List<StepProcessDocDTO>> searchStepProcessDocs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StepProcessDocs for query {}", query);
        Page<StepProcessDocDTO> page = stepProcessDocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
