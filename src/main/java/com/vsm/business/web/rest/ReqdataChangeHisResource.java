package com.vsm.business.web.rest;

import com.vsm.business.repository.ReqdataChangeHisRepository;
import com.vsm.business.service.ReqdataChangeHisService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ReqdataChangeHis}.
 */
//@RestController
@RequestMapping("/api")
public class ReqdataChangeHisResource {

    private final Logger log = LoggerFactory.getLogger(ReqdataChangeHisResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceReqdataChangeHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqdataChangeHisService reqdataChangeHisService;

    private final ReqdataChangeHisRepository reqdataChangeHisRepository;

    public ReqdataChangeHisResource(
        ReqdataChangeHisService reqdataChangeHisService,
        ReqdataChangeHisRepository reqdataChangeHisRepository
    ) {
        this.reqdataChangeHisService = reqdataChangeHisService;
        this.reqdataChangeHisRepository = reqdataChangeHisRepository;
    }

    /**
     * {@code POST  /reqdata-change-his} : Create a new reqdataChangeHis.
     *
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqdataChangeHisDTO, or with status {@code 400 (Bad Request)} if the reqdataChangeHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reqdata-change-his")
    public ResponseEntity<ReqdataChangeHisDTO> createReqdataChangeHis(@RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReqdataChangeHis : {}", reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() != null) {
            throw new BadRequestAlertException("A new reqdataChangeHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReqdataChangeHisDTO result = reqdataChangeHisService.save(reqdataChangeHisDTO);
        return ResponseEntity
            .created(new URI("/api/reqdata-change-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reqdata-change-his/:id} : Updates an existing reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to save.
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataChangeHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataChangeHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqdataChangeHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reqdata-change-his/{id}")
    public ResponseEntity<ReqdataChangeHisDTO> updateReqdataChangeHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReqdataChangeHis : {}, {}", id, reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reqdataChangeHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reqdataChangeHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReqdataChangeHisDTO result = reqdataChangeHisService.save(reqdataChangeHisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reqdataChangeHisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reqdata-change-his/:id} : Partial updates given fields of an existing reqdataChangeHis, field will ignore if it is null
     *
     * @param id the id of the reqdataChangeHisDTO to save.
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataChangeHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataChangeHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reqdataChangeHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reqdataChangeHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reqdata-change-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReqdataChangeHisDTO> partialUpdateReqdataChangeHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReqdataChangeHis partially : {}, {}", id, reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reqdataChangeHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reqdataChangeHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReqdataChangeHisDTO> result = reqdataChangeHisService.partialUpdate(reqdataChangeHisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reqdataChangeHisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reqdata-change-his} : get all the reqdataChangeHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqdataChangeHis in body.
     */
    @GetMapping("/reqdata-change-his")
    public ResponseEntity<List<ReqdataChangeHisDTO>> getAllReqdataChangeHis(Pageable pageable) {
        log.debug("REST request to get a page of ReqdataChangeHis");
        Page<ReqdataChangeHisDTO> page = reqdataChangeHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reqdata-change-his/:id} : get the "id" reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqdataChangeHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reqdata-change-his/{id}")
    public ResponseEntity<ReqdataChangeHisDTO> getReqdataChangeHis(@PathVariable Long id) {
        log.debug("REST request to get ReqdataChangeHis : {}", id);
        Optional<ReqdataChangeHisDTO> reqdataChangeHisDTO = reqdataChangeHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reqdataChangeHisDTO);
    }

    /**
     * {@code DELETE  /reqdata-change-his/:id} : delete the "id" reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reqdata-change-his/{id}")
    public ResponseEntity<Void> deleteReqdataChangeHis(@PathVariable Long id) {
        log.debug("REST request to delete ReqdataChangeHis : {}", id);
        reqdataChangeHisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/reqdata-change-his?query=:query} : search for the reqdataChangeHis corresponding
     * to the query.
     *
     * @param query the query of the reqdataChangeHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reqdata-change-his")
    public ResponseEntity<List<ReqdataChangeHisDTO>> searchReqdataChangeHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReqdataChangeHis for query {}", query);
        Page<ReqdataChangeHisDTO> page = reqdataChangeHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
