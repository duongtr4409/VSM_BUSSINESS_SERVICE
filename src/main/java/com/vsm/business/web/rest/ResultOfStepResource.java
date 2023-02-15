package com.vsm.business.web.rest;

import com.vsm.business.repository.ResultOfStepRepository;
import com.vsm.business.service.ResultOfStepService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ResultOfStepDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ResultOfStep}.
 */
//@RestController
@RequestMapping("/api")
public class ResultOfStepResource {

    private final Logger log = LoggerFactory.getLogger(ResultOfStepResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceResultOfStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultOfStepService resultOfStepService;

    private final ResultOfStepRepository resultOfStepRepository;

    public ResultOfStepResource(ResultOfStepService resultOfStepService, ResultOfStepRepository resultOfStepRepository) {
        this.resultOfStepService = resultOfStepService;
        this.resultOfStepRepository = resultOfStepRepository;
    }

    /**
     * {@code POST  /result-of-steps} : Create a new resultOfStep.
     *
     * @param resultOfStepDTO the resultOfStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultOfStepDTO, or with status {@code 400 (Bad Request)} if the resultOfStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/result-of-steps")
    public ResponseEntity<ResultOfStepDTO> createResultOfStep(@RequestBody ResultOfStepDTO resultOfStepDTO) throws URISyntaxException {
        log.debug("REST request to save ResultOfStep : {}", resultOfStepDTO);
        if (resultOfStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultOfStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultOfStepDTO result = resultOfStepService.save(resultOfStepDTO);
        return ResponseEntity
            .created(new URI("/api/result-of-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /result-of-steps/:id} : Updates an existing resultOfStep.
     *
     * @param id the id of the resultOfStepDTO to save.
     * @param resultOfStepDTO the resultOfStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultOfStepDTO,
     * or with status {@code 400 (Bad Request)} if the resultOfStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultOfStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/result-of-steps/{id}")
    public ResponseEntity<ResultOfStepDTO> updateResultOfStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultOfStepDTO resultOfStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResultOfStep : {}, {}", id, resultOfStepDTO);
        if (resultOfStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultOfStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultOfStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResultOfStepDTO result = resultOfStepService.save(resultOfStepDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultOfStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /result-of-steps/:id} : Partial updates given fields of an existing resultOfStep, field will ignore if it is null
     *
     * @param id the id of the resultOfStepDTO to save.
     * @param resultOfStepDTO the resultOfStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultOfStepDTO,
     * or with status {@code 400 (Bad Request)} if the resultOfStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resultOfStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultOfStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/result-of-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResultOfStepDTO> partialUpdateResultOfStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultOfStepDTO resultOfStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResultOfStep partially : {}, {}", id, resultOfStepDTO);
        if (resultOfStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultOfStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultOfStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultOfStepDTO> result = resultOfStepService.partialUpdate(resultOfStepDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultOfStepDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /result-of-steps} : get all the resultOfSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultOfSteps in body.
     */
    @GetMapping("/result-of-steps")
    public ResponseEntity<List<ResultOfStepDTO>> getAllResultOfSteps(Pageable pageable) {
        log.debug("REST request to get a page of ResultOfSteps");
        Page<ResultOfStepDTO> page = resultOfStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /result-of-steps/:id} : get the "id" resultOfStep.
     *
     * @param id the id of the resultOfStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultOfStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/result-of-steps/{id}")
    public ResponseEntity<ResultOfStepDTO> getResultOfStep(@PathVariable Long id) {
        log.debug("REST request to get ResultOfStep : {}", id);
        Optional<ResultOfStepDTO> resultOfStepDTO = resultOfStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultOfStepDTO);
    }

    /**
     * {@code DELETE  /result-of-steps/:id} : delete the "id" resultOfStep.
     *
     * @param id the id of the resultOfStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/result-of-steps/{id}")
    public ResponseEntity<Void> deleteResultOfStep(@PathVariable Long id) {
        log.debug("REST request to delete ResultOfStep : {}", id);
        resultOfStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/result-of-steps?query=:query} : search for the resultOfStep corresponding
     * to the query.
     *
     * @param query the query of the resultOfStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/result-of-steps")
    public ResponseEntity<List<ResultOfStepDTO>> searchResultOfSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ResultOfSteps for query {}", query);
        Page<ResultOfStepDTO> page = resultOfStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
