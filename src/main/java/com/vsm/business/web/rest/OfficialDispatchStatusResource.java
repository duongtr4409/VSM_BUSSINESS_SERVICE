package com.vsm.business.web.rest;

import com.vsm.business.repository.OfficialDispatchStatusRepository;
import com.vsm.business.service.OfficialDispatchStatusService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OfficialDispatchStatusDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatchStatus}.
 */
//@RestController
@RequestMapping("/api")
public class OfficialDispatchStatusResource {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchStatusResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatchStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchStatusService officialDispatchStatusService;

    private final OfficialDispatchStatusRepository officialDispatchStatusRepository;

    public OfficialDispatchStatusResource(
        OfficialDispatchStatusService officialDispatchStatusService,
        OfficialDispatchStatusRepository officialDispatchStatusRepository
    ) {
        this.officialDispatchStatusService = officialDispatchStatusService;
        this.officialDispatchStatusRepository = officialDispatchStatusRepository;
    }

    /**
     * {@code POST  /official-dispatch-statuses} : Create a new officialDispatchStatus.
     *
     * @param officialDispatchStatusDTO the officialDispatchStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchStatusDTO, or with status {@code 400 (Bad Request)} if the officialDispatchStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatch-statuses")
    public ResponseEntity<OfficialDispatchStatusDTO> createOfficialDispatchStatus(
        @RequestBody OfficialDispatchStatusDTO officialDispatchStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OfficialDispatchStatus : {}", officialDispatchStatusDTO);
        if (officialDispatchStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new officialDispatchStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficialDispatchStatusDTO result = officialDispatchStatusService.save(officialDispatchStatusDTO);
        return ResponseEntity
            .created(new URI("/api/official-dispatch-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /official-dispatch-statuses/:id} : Updates an existing officialDispatchStatus.
     *
     * @param id the id of the officialDispatchStatusDTO to save.
     * @param officialDispatchStatusDTO the officialDispatchStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchStatusDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatch-statuses/{id}")
    public ResponseEntity<OfficialDispatchStatusDTO> updateOfficialDispatchStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchStatusDTO officialDispatchStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatchStatus : {}, {}", id, officialDispatchStatusDTO);
        if (officialDispatchStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfficialDispatchStatusDTO result = officialDispatchStatusService.save(officialDispatchStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /official-dispatch-statuses/:id} : Partial updates given fields of an existing officialDispatchStatus, field will ignore if it is null
     *
     * @param id the id of the officialDispatchStatusDTO to save.
     * @param officialDispatchStatusDTO the officialDispatchStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchStatusDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatch-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfficialDispatchStatusDTO> partialUpdateOfficialDispatchStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchStatusDTO officialDispatchStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatchStatus partially : {}, {}", id, officialDispatchStatusDTO);
        if (officialDispatchStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfficialDispatchStatusDTO> result = officialDispatchStatusService.partialUpdate(officialDispatchStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /official-dispatch-statuses} : get all the officialDispatchStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatchStatuses in body.
     */
    @GetMapping("/official-dispatch-statuses")
    public ResponseEntity<List<OfficialDispatchStatusDTO>> getAllOfficialDispatchStatuses(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatchStatuses");
        Page<OfficialDispatchStatusDTO> page = officialDispatchStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /official-dispatch-statuses/:id} : get the "id" officialDispatchStatus.
     *
     * @param id the id of the officialDispatchStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatch-statuses/{id}")
    public ResponseEntity<OfficialDispatchStatusDTO> getOfficialDispatchStatus(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatchStatus : {}", id);
        Optional<OfficialDispatchStatusDTO> officialDispatchStatusDTO = officialDispatchStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officialDispatchStatusDTO);
    }

    /**
     * {@code DELETE  /official-dispatch-statuses/:id} : delete the "id" officialDispatchStatus.
     *
     * @param id the id of the officialDispatchStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatch-statuses/{id}")
    public ResponseEntity<Void> deleteOfficialDispatchStatus(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatchStatus : {}", id);
        officialDispatchStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/official-dispatch-statuses?query=:query} : search for the officialDispatchStatus corresponding
     * to the query.
     *
     * @param query the query of the officialDispatchStatus search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatch-statuses")
    public ResponseEntity<List<OfficialDispatchStatusDTO>> searchOfficialDispatchStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatchStatuses for query {}", query);
        Page<OfficialDispatchStatusDTO> page = officialDispatchStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
