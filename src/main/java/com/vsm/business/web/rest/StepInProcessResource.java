package com.vsm.business.web.rest;

import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.service.StepInProcessService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.StepInProcessDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.StepInProcess}.
 */
//@RestController
@RequestMapping("/api")
public class StepInProcessResource {

    private final Logger log = LoggerFactory.getLogger(StepInProcessResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStepInProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepInProcessService stepInProcessService;

    private final StepInProcessRepository stepInProcessRepository;

    public StepInProcessResource(StepInProcessService stepInProcessService, StepInProcessRepository stepInProcessRepository) {
        this.stepInProcessService = stepInProcessService;
        this.stepInProcessRepository = stepInProcessRepository;
    }

    /**
     * {@code POST  /step-in-processes} : Create a new stepInProcess.
     *
     * @param stepInProcessDTO the stepInProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepInProcessDTO, or with status {@code 400 (Bad Request)} if the stepInProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-in-processes")
    public ResponseEntity<StepInProcessDTO> createStepInProcess(@RequestBody StepInProcessDTO stepInProcessDTO) throws URISyntaxException {
        log.debug("REST request to save StepInProcess : {}", stepInProcessDTO);
        if (stepInProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new stepInProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepInProcessDTO result = stepInProcessService.save(stepInProcessDTO);
        return ResponseEntity
            .created(new URI("/api/step-in-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-in-processes/:id} : Updates an existing stepInProcess.
     *
     * @param id the id of the stepInProcessDTO to save.
     * @param stepInProcessDTO the stepInProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepInProcessDTO,
     * or with status {@code 400 (Bad Request)} if the stepInProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepInProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-in-processes/{id}")
    public ResponseEntity<StepInProcessDTO> updateStepInProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepInProcessDTO stepInProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StepInProcess : {}, {}", id, stepInProcessDTO);
        if (stepInProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepInProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepInProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepInProcessDTO result = stepInProcessService.save(stepInProcessDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepInProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-in-processes/:id} : Partial updates given fields of an existing stepInProcess, field will ignore if it is null
     *
     * @param id the id of the stepInProcessDTO to save.
     * @param stepInProcessDTO the stepInProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepInProcessDTO,
     * or with status {@code 400 (Bad Request)} if the stepInProcessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepInProcessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepInProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-in-processes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepInProcessDTO> partialUpdateStepInProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepInProcessDTO stepInProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepInProcess partially : {}, {}", id, stepInProcessDTO);
        if (stepInProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepInProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepInProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepInProcessDTO> result = stepInProcessService.partialUpdate(stepInProcessDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepInProcessDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /step-in-processes} : get all the stepInProcesses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepInProcesses in body.
     */
    @GetMapping("/step-in-processes")
    public ResponseEntity<List<StepInProcessDTO>> getAllStepInProcesses(Pageable pageable) {
        log.debug("REST request to get a page of StepInProcesses");
        Page<StepInProcessDTO> page = stepInProcessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-in-processes/:id} : get the "id" stepInProcess.
     *
     * @param id the id of the stepInProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepInProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-in-processes/{id}")
    public ResponseEntity<StepInProcessDTO> getStepInProcess(@PathVariable Long id) {
        log.debug("REST request to get StepInProcess : {}", id);
        Optional<StepInProcessDTO> stepInProcessDTO = stepInProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepInProcessDTO);
    }

    /**
     * {@code DELETE  /step-in-processes/:id} : delete the "id" stepInProcess.
     *
     * @param id the id of the stepInProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-in-processes/{id}")
    public ResponseEntity<Void> deleteStepInProcess(@PathVariable Long id) {
        log.debug("REST request to delete StepInProcess : {}", id);
        stepInProcessService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/step-in-processes?query=:query} : search for the stepInProcess corresponding
     * to the query.
     *
     * @param query the query of the stepInProcess search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/step-in-processes")
    public ResponseEntity<List<StepInProcessDTO>> searchStepInProcesses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StepInProcesses for query {}", query);
        Page<StepInProcessDTO> page = stepInProcessService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
