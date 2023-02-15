package com.vsm.business.web.rest;

import com.vsm.business.repository.ReqdataProcessHisRepository;
import com.vsm.business.service.ReqdataProcessHisService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ReqdataProcessHis}.
 */
//@RestController
@RequestMapping("/api")
public class ReqdataProcessHisResource {

    private final Logger log = LoggerFactory.getLogger(ReqdataProcessHisResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceReqdataProcessHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqdataProcessHisService reqdataProcessHisService;

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;

    public ReqdataProcessHisResource(
        ReqdataProcessHisService reqdataProcessHisService,
        ReqdataProcessHisRepository reqdataProcessHisRepository
    ) {
        this.reqdataProcessHisService = reqdataProcessHisService;
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
    }

    /**
     * {@code POST  /reqdata-process-his} : Create a new reqdataProcessHis.
     *
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqdataProcessHisDTO, or with status {@code 400 (Bad Request)} if the reqdataProcessHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reqdata-process-his")
    public ResponseEntity<ReqdataProcessHisDTO> createReqdataProcessHis(@RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReqdataProcessHis : {}", reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() != null) {
            throw new BadRequestAlertException("A new reqdataProcessHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReqdataProcessHisDTO result = reqdataProcessHisService.save(reqdataProcessHisDTO);
        return ResponseEntity
            .created(new URI("/api/reqdata-process-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reqdata-process-his/:id} : Updates an existing reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to save.
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataProcessHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataProcessHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqdataProcessHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reqdata-process-his/{id}")
    public ResponseEntity<ReqdataProcessHisDTO> updateReqdataProcessHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReqdataProcessHis : {}, {}", id, reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reqdataProcessHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reqdataProcessHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReqdataProcessHisDTO result = reqdataProcessHisService.save(reqdataProcessHisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reqdataProcessHisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reqdata-process-his/:id} : Partial updates given fields of an existing reqdataProcessHis, field will ignore if it is null
     *
     * @param id the id of the reqdataProcessHisDTO to save.
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataProcessHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataProcessHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reqdataProcessHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reqdataProcessHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reqdata-process-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReqdataProcessHisDTO> partialUpdateReqdataProcessHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReqdataProcessHis partially : {}, {}", id, reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reqdataProcessHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reqdataProcessHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReqdataProcessHisDTO> result = reqdataProcessHisService.partialUpdate(reqdataProcessHisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reqdataProcessHisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reqdata-process-his} : get all the reqdataProcessHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqdataProcessHis in body.
     */
    @GetMapping("/reqdata-process-his")
    public ResponseEntity<List<ReqdataProcessHisDTO>> getAllReqdataProcessHis(Pageable pageable) {
        log.debug("REST request to get a page of ReqdataProcessHis");
        Page<ReqdataProcessHisDTO> page = reqdataProcessHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reqdata-process-his/:id} : get the "id" reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqdataProcessHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reqdata-process-his/{id}")
    public ResponseEntity<ReqdataProcessHisDTO> getReqdataProcessHis(@PathVariable Long id) {
        log.debug("REST request to get ReqdataProcessHis : {}", id);
        Optional<ReqdataProcessHisDTO> reqdataProcessHisDTO = reqdataProcessHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reqdataProcessHisDTO);
    }

    /**
     * {@code DELETE  /reqdata-process-his/:id} : delete the "id" reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reqdata-process-his/{id}")
    public ResponseEntity<Void> deleteReqdataProcessHis(@PathVariable Long id) {
        log.debug("REST request to delete ReqdataProcessHis : {}", id);
        reqdataProcessHisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/reqdata-process-his?query=:query} : search for the reqdataProcessHis corresponding
     * to the query.
     *
     * @param query the query of the reqdataProcessHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reqdata-process-his")
    public ResponseEntity<List<ReqdataProcessHisDTO>> searchReqdataProcessHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReqdataProcessHis for query {}", query);
        Page<ReqdataProcessHisDTO> page = reqdataProcessHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
