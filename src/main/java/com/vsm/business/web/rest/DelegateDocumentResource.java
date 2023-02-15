package com.vsm.business.web.rest;

import com.vsm.business.repository.DelegateDocumentRepository;
import com.vsm.business.service.DelegateDocumentService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DelegateDocumentDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DelegateDocument}.
 */
//@RestController
@RequestMapping("/api")
public class DelegateDocumentResource {

    private final Logger log = LoggerFactory.getLogger(DelegateDocumentResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDelegateDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegateDocumentService delegateDocumentService;

    private final DelegateDocumentRepository delegateDocumentRepository;

    public DelegateDocumentResource(
        DelegateDocumentService delegateDocumentService,
        DelegateDocumentRepository delegateDocumentRepository
    ) {
        this.delegateDocumentService = delegateDocumentService;
        this.delegateDocumentRepository = delegateDocumentRepository;
    }

    /**
     * {@code POST  /delegate-documents} : Create a new delegateDocument.
     *
     * @param delegateDocumentDTO the delegateDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegateDocumentDTO, or with status {@code 400 (Bad Request)} if the delegateDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegate-documents")
    public ResponseEntity<DelegateDocumentDTO> createDelegateDocument(@RequestBody DelegateDocumentDTO delegateDocumentDTO)
        throws URISyntaxException {
        log.debug("REST request to save DelegateDocument : {}", delegateDocumentDTO);
        if (delegateDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new delegateDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DelegateDocumentDTO result = delegateDocumentService.save(delegateDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/delegate-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegate-documents/:id} : Updates an existing delegateDocument.
     *
     * @param id the id of the delegateDocumentDTO to save.
     * @param delegateDocumentDTO the delegateDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the delegateDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegateDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegate-documents/{id}")
    public ResponseEntity<DelegateDocumentDTO> updateDelegateDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateDocumentDTO delegateDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DelegateDocument : {}, {}", id, delegateDocumentDTO);
        if (delegateDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DelegateDocumentDTO result = delegateDocumentService.save(delegateDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delegate-documents/:id} : Partial updates given fields of an existing delegateDocument, field will ignore if it is null
     *
     * @param id the id of the delegateDocumentDTO to save.
     * @param delegateDocumentDTO the delegateDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the delegateDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the delegateDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the delegateDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delegate-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DelegateDocumentDTO> partialUpdateDelegateDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateDocumentDTO delegateDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DelegateDocument partially : {}, {}", id, delegateDocumentDTO);
        if (delegateDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DelegateDocumentDTO> result = delegateDocumentService.partialUpdate(delegateDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delegate-documents} : get all the delegateDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegateDocuments in body.
     */
    @GetMapping("/delegate-documents")
    public ResponseEntity<List<DelegateDocumentDTO>> getAllDelegateDocuments(Pageable pageable) {
        log.debug("REST request to get a page of DelegateDocuments");
        Page<DelegateDocumentDTO> page = delegateDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delegate-documents/:id} : get the "id" delegateDocument.
     *
     * @param id the id of the delegateDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegateDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegate-documents/{id}")
    public ResponseEntity<DelegateDocumentDTO> getDelegateDocument(@PathVariable Long id) {
        log.debug("REST request to get DelegateDocument : {}", id);
        Optional<DelegateDocumentDTO> delegateDocumentDTO = delegateDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegateDocumentDTO);
    }

    /**
     * {@code DELETE  /delegate-documents/:id} : delete the "id" delegateDocument.
     *
     * @param id the id of the delegateDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegate-documents/{id}")
    public ResponseEntity<Void> deleteDelegateDocument(@PathVariable Long id) {
        log.debug("REST request to delete DelegateDocument : {}", id);
        delegateDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/delegate-documents?query=:query} : search for the delegateDocument corresponding
     * to the query.
     *
     * @param query the query of the delegateDocument search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/delegate-documents")
    public ResponseEntity<List<DelegateDocumentDTO>> searchDelegateDocuments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DelegateDocuments for query {}", query);
        Page<DelegateDocumentDTO> page = delegateDocumentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
