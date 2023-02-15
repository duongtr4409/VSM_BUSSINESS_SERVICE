package com.vsm.business.web.rest;

import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.service.ProcessDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ProcessDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ProcessData}.
 */
//@RestController
@RequestMapping("/api")
public class ProcessDataResource {

    private final Logger log = LoggerFactory.getLogger(ProcessDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceProcessData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessDataService processDataService;

    private final ProcessDataRepository processDataRepository;

    public ProcessDataResource(ProcessDataService processDataService, ProcessDataRepository processDataRepository) {
        this.processDataService = processDataService;
        this.processDataRepository = processDataRepository;
    }

    /**
     * {@code POST  /process-data} : Create a new processData.
     *
     * @param processDataDTO the processDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processDataDTO, or with status {@code 400 (Bad Request)} if the processData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-data")
    public ResponseEntity<ProcessDataDTO> createProcessData(@RequestBody ProcessDataDTO processDataDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessData : {}", processDataDTO);
        if (processDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new processData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessDataDTO result = processDataService.save(processDataDTO);
        return ResponseEntity
            .created(new URI("/api/process-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-data/:id} : Updates an existing processData.
     *
     * @param id the id of the processDataDTO to save.
     * @param processDataDTO the processDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processDataDTO,
     * or with status {@code 400 (Bad Request)} if the processDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-data/{id}")
    public ResponseEntity<ProcessDataDTO> updateProcessData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessDataDTO processDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessData : {}, {}", id, processDataDTO);
        if (processDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessDataDTO result = processDataService.save(processDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /process-data/:id} : Partial updates given fields of an existing processData, field will ignore if it is null
     *
     * @param id the id of the processDataDTO to save.
     * @param processDataDTO the processDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processDataDTO,
     * or with status {@code 400 (Bad Request)} if the processDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessDataDTO> partialUpdateProcessData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessDataDTO processDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessData partially : {}, {}", id, processDataDTO);
        if (processDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessDataDTO> result = processDataService.partialUpdate(processDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /process-data} : get all the processData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processData in body.
     */
    @GetMapping("/process-data")
    public ResponseEntity<List<ProcessDataDTO>> getAllProcessData(Pageable pageable) {
        log.debug("REST request to get a page of ProcessData");
        Page<ProcessDataDTO> page = processDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-data/:id} : get the "id" processData.
     *
     * @param id the id of the processDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-data/{id}")
    public ResponseEntity<ProcessDataDTO> getProcessData(@PathVariable Long id) {
        log.debug("REST request to get ProcessData : {}", id);
        Optional<ProcessDataDTO> processDataDTO = processDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processDataDTO);
    }

    /**
     * {@code DELETE  /process-data/:id} : delete the "id" processData.
     *
     * @param id the id of the processDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-data/{id}")
    public ResponseEntity<Void> deleteProcessData(@PathVariable Long id) {
        log.debug("REST request to delete ProcessData : {}", id);
        processDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/process-data?query=:query} : search for the processData corresponding
     * to the query.
     *
     * @param query the query of the processData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/process-data")
    public ResponseEntity<List<ProcessDataDTO>> searchProcessData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProcessData for query {}", query);
        Page<ProcessDataDTO> page = processDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
