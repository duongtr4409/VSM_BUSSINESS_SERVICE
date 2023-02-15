package com.vsm.business.web.rest;

import com.vsm.business.repository.PatternRepository;
import com.vsm.business.service.PatternService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.PatternDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Pattern}.
 */
//@RestController
@RequestMapping("/api")
public class PatternResource {

    private final Logger log = LoggerFactory.getLogger(PatternResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServicePattern";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatternService patternService;

    private final PatternRepository patternRepository;

    public PatternResource(PatternService patternService, PatternRepository patternRepository) {
        this.patternService = patternService;
        this.patternRepository = patternRepository;
    }

    /**
     * {@code POST  /patterns} : Create a new pattern.
     *
     * @param patternDTO the patternDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patternDTO, or with status {@code 400 (Bad Request)} if the pattern has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patterns")
    public ResponseEntity<PatternDTO> createPattern(@RequestBody PatternDTO patternDTO) throws URISyntaxException {
        log.debug("REST request to save Pattern : {}", patternDTO);
        if (patternDTO.getId() != null) {
            throw new BadRequestAlertException("A new pattern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatternDTO result = patternService.save(patternDTO);
        return ResponseEntity
            .created(new URI("/api/patterns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patterns/:id} : Updates an existing pattern.
     *
     * @param id the id of the patternDTO to save.
     * @param patternDTO the patternDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patternDTO,
     * or with status {@code 400 (Bad Request)} if the patternDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patternDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patterns/{id}")
    public ResponseEntity<PatternDTO> updatePattern(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatternDTO patternDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pattern : {}, {}", id, patternDTO);
        if (patternDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patternDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patternRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatternDTO result = patternService.save(patternDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patternDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patterns/:id} : Partial updates given fields of an existing pattern, field will ignore if it is null
     *
     * @param id the id of the patternDTO to save.
     * @param patternDTO the patternDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patternDTO,
     * or with status {@code 400 (Bad Request)} if the patternDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patternDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patternDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/patterns/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatternDTO> partialUpdatePattern(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatternDTO patternDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pattern partially : {}, {}", id, patternDTO);
        if (patternDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patternDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patternRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatternDTO> result = patternService.partialUpdate(patternDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patternDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patterns} : get all the patterns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patterns in body.
     */
    @GetMapping("/patterns")
    public ResponseEntity<List<PatternDTO>> getAllPatterns(Pageable pageable) {
        log.debug("REST request to get a page of Patterns");
        Page<PatternDTO> page = patternService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patterns/:id} : get the "id" pattern.
     *
     * @param id the id of the patternDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patternDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patterns/{id}")
    public ResponseEntity<PatternDTO> getPattern(@PathVariable Long id) {
        log.debug("REST request to get Pattern : {}", id);
        Optional<PatternDTO> patternDTO = patternService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patternDTO);
    }

    /**
     * {@code DELETE  /patterns/:id} : delete the "id" pattern.
     *
     * @param id the id of the patternDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patterns/{id}")
    public ResponseEntity<Void> deletePattern(@PathVariable Long id) {
        log.debug("REST request to delete Pattern : {}", id);
        patternService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/patterns?query=:query} : search for the pattern corresponding
     * to the query.
     *
     * @param query the query of the pattern search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/patterns")
    public ResponseEntity<List<PatternDTO>> searchPatterns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Patterns for query {}", query);
        Page<PatternDTO> page = patternService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
