package com.vsm.business.web.rest;

import com.vsm.business.repository.TransferHandleRepository;
import com.vsm.business.service.TransferHandleService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.TransferHandleDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.TransferHandle}.
 */
//@RestController
@RequestMapping("/api")
public class TransferHandleResource {

    private final Logger log = LoggerFactory.getLogger(TransferHandleResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTransferHandle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferHandleService transferHandleService;

    private final TransferHandleRepository transferHandleRepository;

    public TransferHandleResource(TransferHandleService transferHandleService, TransferHandleRepository transferHandleRepository) {
        this.transferHandleService = transferHandleService;
        this.transferHandleRepository = transferHandleRepository;
    }

    /**
     * {@code POST  /transfer-handles} : Create a new transferHandle.
     *
     * @param transferHandleDTO the transferHandleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferHandleDTO, or with status {@code 400 (Bad Request)} if the transferHandle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-handles")
    public ResponseEntity<TransferHandleDTO> createTransferHandle(@RequestBody TransferHandleDTO transferHandleDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferHandle : {}", transferHandleDTO);
        if (transferHandleDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferHandle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferHandleDTO result = transferHandleService.save(transferHandleDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-handles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-handles/:id} : Updates an existing transferHandle.
     *
     * @param id the id of the transferHandleDTO to save.
     * @param transferHandleDTO the transferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the transferHandleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-handles/{id}")
    public ResponseEntity<TransferHandleDTO> updateTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferHandleDTO transferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferHandle : {}, {}", id, transferHandleDTO);
        if (transferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferHandleDTO result = transferHandleService.save(transferHandleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transferHandleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-handles/:id} : Partial updates given fields of an existing transferHandle, field will ignore if it is null
     *
     * @param id the id of the transferHandleDTO to save.
     * @param transferHandleDTO the transferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the transferHandleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferHandleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-handles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferHandleDTO> partialUpdateTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferHandleDTO transferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferHandle partially : {}, {}", id, transferHandleDTO);
        if (transferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferHandleDTO> result = transferHandleService.partialUpdate(transferHandleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transferHandleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-handles} : get all the transferHandles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferHandles in body.
     */
    @GetMapping("/transfer-handles")
    public ResponseEntity<List<TransferHandleDTO>> getAllTransferHandles(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TransferHandles");
        Page<TransferHandleDTO> page;
        if (eagerload) {
            page = transferHandleService.findAllWithEagerRelationships(pageable);
        } else {
            page = transferHandleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-handles/:id} : get the "id" transferHandle.
     *
     * @param id the id of the transferHandleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferHandleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-handles/{id}")
    public ResponseEntity<TransferHandleDTO> getTransferHandle(@PathVariable Long id) {
        log.debug("REST request to get TransferHandle : {}", id);
        Optional<TransferHandleDTO> transferHandleDTO = transferHandleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferHandleDTO);
    }

    /**
     * {@code DELETE  /transfer-handles/:id} : delete the "id" transferHandle.
     *
     * @param id the id of the transferHandleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-handles/{id}")
    public ResponseEntity<Void> deleteTransferHandle(@PathVariable Long id) {
        log.debug("REST request to delete TransferHandle : {}", id);
        transferHandleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/transfer-handles?query=:query} : search for the transferHandle corresponding
     * to the query.
     *
     * @param query the query of the transferHandle search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transfer-handles")
    public ResponseEntity<List<TransferHandleDTO>> searchTransferHandles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TransferHandles for query {}", query);
        Page<TransferHandleDTO> page = transferHandleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
