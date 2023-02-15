package com.vsm.business.web.rest;

import com.vsm.business.repository.OutOrganizationRepository;
import com.vsm.business.service.OutOrganizationService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OutOrganizationDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.OutOrganization}.
 */
//@RestController
@RequestMapping("/api")
public class OutOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OutOrganizationResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOutOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutOrganizationService outOrganizationService;

    private final OutOrganizationRepository outOrganizationRepository;

    public OutOrganizationResource(OutOrganizationService outOrganizationService, OutOrganizationRepository outOrganizationRepository) {
        this.outOrganizationService = outOrganizationService;
        this.outOrganizationRepository = outOrganizationRepository;
    }

    /**
     * {@code POST  /out-organizations} : Create a new outOrganization.
     *
     * @param outOrganizationDTO the outOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outOrganizationDTO, or with status {@code 400 (Bad Request)} if the outOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/out-organizations")
    public ResponseEntity<OutOrganizationDTO> createOutOrganization(@RequestBody OutOrganizationDTO outOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save OutOrganization : {}", outOrganizationDTO);
        if (outOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new outOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OutOrganizationDTO result = outOrganizationService.save(outOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/out-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /out-organizations/:id} : Updates an existing outOrganization.
     *
     * @param id the id of the outOrganizationDTO to save.
     * @param outOrganizationDTO the outOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the outOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/out-organizations/{id}")
    public ResponseEntity<OutOrganizationDTO> updateOutOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OutOrganizationDTO outOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OutOrganization : {}, {}", id, outOrganizationDTO);
        if (outOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, outOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!outOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OutOrganizationDTO result = outOrganizationService.save(outOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, outOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /out-organizations/:id} : Partial updates given fields of an existing outOrganization, field will ignore if it is null
     *
     * @param id the id of the outOrganizationDTO to save.
     * @param outOrganizationDTO the outOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the outOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the outOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the outOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/out-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OutOrganizationDTO> partialUpdateOutOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OutOrganizationDTO outOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OutOrganization partially : {}, {}", id, outOrganizationDTO);
        if (outOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, outOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!outOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OutOrganizationDTO> result = outOrganizationService.partialUpdate(outOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, outOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /out-organizations} : get all the outOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outOrganizations in body.
     */
    @GetMapping("/out-organizations")
    public ResponseEntity<List<OutOrganizationDTO>> getAllOutOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of OutOrganizations");
        Page<OutOrganizationDTO> page = outOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /out-organizations/:id} : get the "id" outOrganization.
     *
     * @param id the id of the outOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/out-organizations/{id}")
    public ResponseEntity<OutOrganizationDTO> getOutOrganization(@PathVariable Long id) {
        log.debug("REST request to get OutOrganization : {}", id);
        Optional<OutOrganizationDTO> outOrganizationDTO = outOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outOrganizationDTO);
    }

    /**
     * {@code DELETE  /out-organizations/:id} : delete the "id" outOrganization.
     *
     * @param id the id of the outOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/out-organizations/{id}")
    public ResponseEntity<Void> deleteOutOrganization(@PathVariable Long id) {
        log.debug("REST request to delete OutOrganization : {}", id);
        outOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/out-organizations?query=:query} : search for the outOrganization corresponding
     * to the query.
     *
     * @param query the query of the outOrganization search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/out-organizations")
    public ResponseEntity<List<OutOrganizationDTO>> searchOutOrganizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OutOrganizations for query {}", query);
        Page<OutOrganizationDTO> page = outOrganizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
