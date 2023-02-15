package com.vsm.business.web.rest;

import com.vsm.business.repository.StatusTransferHandleRepository;
import com.vsm.business.service.StatusTransferHandleService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.StatusTransferHandleDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.StatusTransferHandle}.
 */
//@RestController
@RequestMapping("/api")
public class StatusTransferHandleResource {

    private final Logger log = LoggerFactory.getLogger(StatusTransferHandleResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStatusTransferHandle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusTransferHandleService statusTransferHandleService;

    private final StatusTransferHandleRepository statusTransferHandleRepository;

    public StatusTransferHandleResource(
        StatusTransferHandleService statusTransferHandleService,
        StatusTransferHandleRepository statusTransferHandleRepository
    ) {
        this.statusTransferHandleService = statusTransferHandleService;
        this.statusTransferHandleRepository = statusTransferHandleRepository;
    }

    /**
     * {@code POST  /status-transfer-handles} : Create a new statusTransferHandle.
     *
     * @param statusTransferHandleDTO the statusTransferHandleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusTransferHandleDTO, or with status {@code 400 (Bad Request)} if the statusTransferHandle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-transfer-handles")
    public ResponseEntity<StatusTransferHandleDTO> createStatusTransferHandle(@RequestBody StatusTransferHandleDTO statusTransferHandleDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatusTransferHandle : {}", statusTransferHandleDTO);
        if (statusTransferHandleDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusTransferHandle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusTransferHandleDTO result = statusTransferHandleService.save(statusTransferHandleDTO);
        return ResponseEntity
            .created(new URI("/api/status-transfer-handles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-transfer-handles/:id} : Updates an existing statusTransferHandle.
     *
     * @param id the id of the statusTransferHandleDTO to save.
     * @param statusTransferHandleDTO the statusTransferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusTransferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the statusTransferHandleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusTransferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-transfer-handles/{id}")
    public ResponseEntity<StatusTransferHandleDTO> updateStatusTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusTransferHandleDTO statusTransferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatusTransferHandle : {}, {}", id, statusTransferHandleDTO);
        if (statusTransferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusTransferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusTransferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusTransferHandleDTO result = statusTransferHandleService.save(statusTransferHandleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statusTransferHandleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /status-transfer-handles/:id} : Partial updates given fields of an existing statusTransferHandle, field will ignore if it is null
     *
     * @param id the id of the statusTransferHandleDTO to save.
     * @param statusTransferHandleDTO the statusTransferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusTransferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the statusTransferHandleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statusTransferHandleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusTransferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status-transfer-handles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatusTransferHandleDTO> partialUpdateStatusTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusTransferHandleDTO statusTransferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusTransferHandle partially : {}, {}", id, statusTransferHandleDTO);
        if (statusTransferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusTransferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusTransferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusTransferHandleDTO> result = statusTransferHandleService.partialUpdate(statusTransferHandleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statusTransferHandleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /status-transfer-handles} : get all the statusTransferHandles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusTransferHandles in body.
     */
    @GetMapping("/status-transfer-handles")
    public ResponseEntity<List<StatusTransferHandleDTO>> getAllStatusTransferHandles(Pageable pageable) {
        log.debug("REST request to get a page of StatusTransferHandles");
        Page<StatusTransferHandleDTO> page = statusTransferHandleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-transfer-handles/:id} : get the "id" statusTransferHandle.
     *
     * @param id the id of the statusTransferHandleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusTransferHandleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-transfer-handles/{id}")
    public ResponseEntity<StatusTransferHandleDTO> getStatusTransferHandle(@PathVariable Long id) {
        log.debug("REST request to get StatusTransferHandle : {}", id);
        Optional<StatusTransferHandleDTO> statusTransferHandleDTO = statusTransferHandleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusTransferHandleDTO);
    }

    /**
     * {@code DELETE  /status-transfer-handles/:id} : delete the "id" statusTransferHandle.
     *
     * @param id the id of the statusTransferHandleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-transfer-handles/{id}")
    public ResponseEntity<Void> deleteStatusTransferHandle(@PathVariable Long id) {
        log.debug("REST request to delete StatusTransferHandle : {}", id);
        statusTransferHandleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/status-transfer-handles?query=:query} : search for the statusTransferHandle corresponding
     * to the query.
     *
     * @param query the query of the statusTransferHandle search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/status-transfer-handles")
    public ResponseEntity<List<StatusTransferHandleDTO>> searchStatusTransferHandles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StatusTransferHandles for query {}", query);
        Page<StatusTransferHandleDTO> page = statusTransferHandleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
