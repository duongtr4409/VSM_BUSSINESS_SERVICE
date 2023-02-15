package com.vsm.business.web.rest;

import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.service.StepDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.StepDataDTO;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.StepData}.
 */
//@RestController
@RequestMapping("/api")
public class StepDataResource {

    private final Logger log = LoggerFactory.getLogger(StepDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStepData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepDataService stepDataService;

    private final StepDataRepository stepDataRepository;

    public StepDataResource(StepDataService stepDataService, StepDataRepository stepDataRepository) {
        this.stepDataService = stepDataService;
        this.stepDataRepository = stepDataRepository;
    }

    /**
     * {@code POST  /step-data} : Create a new stepData.
     *
     * @param stepDataDTO the stepDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepDataDTO, or with status {@code 400 (Bad Request)} if the stepData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-data")
    public ResponseEntity<StepDataDTO> createStepData(@RequestBody StepDataDTO stepDataDTO) throws URISyntaxException {
        log.debug("REST request to save StepData : {}", stepDataDTO);
        if (stepDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new stepData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepDataDTO result = stepDataService.save(stepDataDTO);
        return ResponseEntity
            .created(new URI("/api/step-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-data/:id} : Updates an existing stepData.
     *
     * @param id the id of the stepDataDTO to save.
     * @param stepDataDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-data/{id}")
    public ResponseEntity<StepDataDTO> updateStepData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepDataDTO stepDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StepData : {}, {}", id, stepDataDTO);
        if (stepDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepDataDTO result = stepDataService.save(stepDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-data/:id} : Partial updates given fields of an existing stepData, field will ignore if it is null
     *
     * @param id the id of the stepDataDTO to save.
     * @param stepDataDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepDataDTO> partialUpdateStepData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepDataDTO stepDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepData partially : {}, {}", id, stepDataDTO);
        if (stepDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepDataDTO> result = stepDataService.partialUpdate(stepDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /step-data} : get all the stepData.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepData in body.
     */
    @GetMapping("/step-data")
    public ResponseEntity<List<StepDataDTO>> getAllStepData(
        Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("previousstep-is-null".equals(filter)) {
            log.debug("REST request to get all StepDatas where previousStep is null");
            return new ResponseEntity<>(stepDataService.findAllWherePreviousStepIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of StepData");
        Page<StepDataDTO> page;
        if (eagerload) {
            page = stepDataService.findAllWithEagerRelationships(pageable);
        } else {
            page = stepDataService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-data/:id} : get the "id" stepData.
     *
     * @param id the id of the stepDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-data/{id}")
    public ResponseEntity<StepDataDTO> getStepData(@PathVariable Long id) {
        log.debug("REST request to get StepData : {}", id);
        Optional<StepDataDTO> stepDataDTO = stepDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepDataDTO);
    }

    /**
     * {@code DELETE  /step-data/:id} : delete the "id" stepData.
     *
     * @param id the id of the stepDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-data/{id}")
    public ResponseEntity<Void> deleteStepData(@PathVariable Long id) {
        log.debug("REST request to delete StepData : {}", id);
        stepDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/step-data?query=:query} : search for the stepData corresponding
     * to the query.
     *
     * @param query the query of the stepData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/step-data")
    public ResponseEntity<List<StepDataDTO>> searchStepData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StepData for query {}", query);
        Page<StepDataDTO> page = stepDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
