package com.vsm.business.web.rest;

import com.vsm.business.repository.AttachmentInStepRepository;
import com.vsm.business.service.AttachmentInStepService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.AttachmentInStepDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.AttachmentInStep}.
 */
//@RestController
@RequestMapping("/api")
public class AttachmentInStepResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentInStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentInStepService attachmentInStepService;

    private final AttachmentInStepRepository attachmentInStepRepository;

    public AttachmentInStepResource(
        AttachmentInStepService attachmentInStepService,
        AttachmentInStepRepository attachmentInStepRepository
    ) {
        this.attachmentInStepService = attachmentInStepService;
        this.attachmentInStepRepository = attachmentInStepRepository;
    }

    /**
     * {@code POST  /attachment-in-steps} : Create a new attachmentInStep.
     *
     * @param attachmentInStepDTO the attachmentInStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentInStepDTO, or with status {@code 400 (Bad Request)} if the attachmentInStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-in-steps")
    public ResponseEntity<AttachmentInStepDTO> createAttachmentInStep(@RequestBody AttachmentInStepDTO attachmentInStepDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachmentInStep : {}", attachmentInStepDTO);
        if (attachmentInStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentInStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentInStepDTO result = attachmentInStepService.save(attachmentInStepDTO);
        return ResponseEntity
            .created(new URI("/api/attachment-in-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-in-steps/:id} : Updates an existing attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to save.
     * @param attachmentInStepDTO the attachmentInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-in-steps/{id}")
    public ResponseEntity<AttachmentInStepDTO> updateAttachmentInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepDTO attachmentInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentInStep : {}, {}", id, attachmentInStepDTO);
        if (attachmentInStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentInStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentInStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachmentInStepDTO result = attachmentInStepService.save(attachmentInStepDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentInStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachment-in-steps/:id} : Partial updates given fields of an existing attachmentInStep, field will ignore if it is null
     *
     * @param id the id of the attachmentInStepDTO to save.
     * @param attachmentInStepDTO the attachmentInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentInStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-in-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachmentInStepDTO> partialUpdateAttachmentInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepDTO attachmentInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentInStep partially : {}, {}", id, attachmentInStepDTO);
        if (attachmentInStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentInStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentInStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachmentInStepDTO> result = attachmentInStepService.partialUpdate(attachmentInStepDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentInStepDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attachment-in-steps} : get all the attachmentInSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentInSteps in body.
     */
    @GetMapping("/attachment-in-steps")
    public ResponseEntity<List<AttachmentInStepDTO>> getAllAttachmentInSteps(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentInSteps");
        Page<AttachmentInStepDTO> page = attachmentInStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-in-steps/:id} : get the "id" attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentInStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-in-steps/{id}")
    public ResponseEntity<AttachmentInStepDTO> getAttachmentInStep(@PathVariable Long id) {
        log.debug("REST request to get AttachmentInStep : {}", id);
        Optional<AttachmentInStepDTO> attachmentInStepDTO = attachmentInStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentInStepDTO);
    }

    /**
     * {@code DELETE  /attachment-in-steps/:id} : delete the "id" attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-in-steps/{id}")
    public ResponseEntity<Void> deleteAttachmentInStep(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentInStep : {}", id);
        attachmentInStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/attachment-in-steps?query=:query} : search for the attachmentInStep corresponding
     * to the query.
     *
     * @param query the query of the attachmentInStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-in-steps")
    public ResponseEntity<List<AttachmentInStepDTO>> searchAttachmentInSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentInSteps for query {}", query);
        Page<AttachmentInStepDTO> page = attachmentInStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
