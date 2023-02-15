package com.vsm.business.web.rest;

import com.vsm.business.repository.SecurityLevelRepository;
import com.vsm.business.service.SecurityLevelService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.SecurityLevelDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.SecurityLevel}.
 */
//@RestController
@RequestMapping("/api")
public class SecurityLevelResource {

    private final Logger log = LoggerFactory.getLogger(SecurityLevelResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceSecurityLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityLevelService securityLevelService;

    private final SecurityLevelRepository securityLevelRepository;

    public SecurityLevelResource(SecurityLevelService securityLevelService, SecurityLevelRepository securityLevelRepository) {
        this.securityLevelService = securityLevelService;
        this.securityLevelRepository = securityLevelRepository;
    }

    /**
     * {@code POST  /security-levels} : Create a new securityLevel.
     *
     * @param securityLevelDTO the securityLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityLevelDTO, or with status {@code 400 (Bad Request)} if the securityLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-levels")
    public ResponseEntity<SecurityLevelDTO> createSecurityLevel(@RequestBody SecurityLevelDTO securityLevelDTO) throws URISyntaxException {
        log.debug("REST request to save SecurityLevel : {}", securityLevelDTO);
        if (securityLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new securityLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityLevelDTO result = securityLevelService.save(securityLevelDTO);
        return ResponseEntity
            .created(new URI("/api/security-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-levels/:id} : Updates an existing securityLevel.
     *
     * @param id the id of the securityLevelDTO to save.
     * @param securityLevelDTO the securityLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityLevelDTO,
     * or with status {@code 400 (Bad Request)} if the securityLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-levels/{id}")
    public ResponseEntity<SecurityLevelDTO> updateSecurityLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityLevelDTO securityLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityLevel : {}, {}", id, securityLevelDTO);
        if (securityLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityLevelDTO result = securityLevelService.save(securityLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, securityLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-levels/:id} : Partial updates given fields of an existing securityLevel, field will ignore if it is null
     *
     * @param id the id of the securityLevelDTO to save.
     * @param securityLevelDTO the securityLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityLevelDTO,
     * or with status {@code 400 (Bad Request)} if the securityLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the securityLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityLevelDTO> partialUpdateSecurityLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecurityLevelDTO securityLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityLevel partially : {}, {}", id, securityLevelDTO);
        if (securityLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityLevelDTO> result = securityLevelService.partialUpdate(securityLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, securityLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /security-levels} : get all the securityLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityLevels in body.
     */
    @GetMapping("/security-levels")
    public ResponseEntity<List<SecurityLevelDTO>> getAllSecurityLevels(Pageable pageable) {
        log.debug("REST request to get a page of SecurityLevels");
        Page<SecurityLevelDTO> page = securityLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /security-levels/:id} : get the "id" securityLevel.
     *
     * @param id the id of the securityLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-levels/{id}")
    public ResponseEntity<SecurityLevelDTO> getSecurityLevel(@PathVariable Long id) {
        log.debug("REST request to get SecurityLevel : {}", id);
        Optional<SecurityLevelDTO> securityLevelDTO = securityLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(securityLevelDTO);
    }

    /**
     * {@code DELETE  /security-levels/:id} : delete the "id" securityLevel.
     *
     * @param id the id of the securityLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-levels/{id}")
    public ResponseEntity<Void> deleteSecurityLevel(@PathVariable Long id) {
        log.debug("REST request to delete SecurityLevel : {}", id);
        securityLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/security-levels?query=:query} : search for the securityLevel corresponding
     * to the query.
     *
     * @param query the query of the securityLevel search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/security-levels")
    public ResponseEntity<List<SecurityLevelDTO>> searchSecurityLevels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SecurityLevels for query {}", query);
        Page<SecurityLevelDTO> page = securityLevelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
