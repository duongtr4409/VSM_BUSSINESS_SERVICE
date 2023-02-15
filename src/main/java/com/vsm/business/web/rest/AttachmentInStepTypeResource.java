package com.vsm.business.web.rest;

import com.vsm.business.repository.AttachmentInStepTypeRepository;
import com.vsm.business.service.AttachmentInStepTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.AttachmentInStepType}.
 */
//@RestController
@RequestMapping("/api")
public class AttachmentInStepTypeResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentInStepType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentInStepTypeService attachmentInStepTypeService;

    private final AttachmentInStepTypeRepository attachmentInStepTypeRepository;

    public AttachmentInStepTypeResource(
        AttachmentInStepTypeService attachmentInStepTypeService,
        AttachmentInStepTypeRepository attachmentInStepTypeRepository
    ) {
        this.attachmentInStepTypeService = attachmentInStepTypeService;
        this.attachmentInStepTypeRepository = attachmentInStepTypeRepository;
    }

    /**
     * {@code POST  /attachment-in-step-types} : Create a new attachmentInStepType.
     *
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentInStepTypeDTO, or with status {@code 400 (Bad Request)} if the attachmentInStepType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-in-step-types")
    public ResponseEntity<AttachmentInStepTypeDTO> createAttachmentInStepType(@RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachmentInStepType : {}", attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentInStepType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentInStepTypeDTO result = attachmentInStepTypeService.save(attachmentInStepTypeDTO);
        return ResponseEntity
            .created(new URI("/api/attachment-in-step-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-in-step-types/:id} : Updates an existing attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to save.
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<AttachmentInStepTypeDTO> updateAttachmentInStepType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentInStepType : {}, {}", id, attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentInStepTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentInStepTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachmentInStepTypeDTO result = attachmentInStepTypeService.save(attachmentInStepTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentInStepTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachment-in-step-types/:id} : Partial updates given fields of an existing attachmentInStepType, field will ignore if it is null
     *
     * @param id the id of the attachmentInStepTypeDTO to save.
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentInStepTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-in-step-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachmentInStepTypeDTO> partialUpdateAttachmentInStepType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentInStepType partially : {}, {}", id, attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentInStepTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentInStepTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachmentInStepTypeDTO> result = attachmentInStepTypeService.partialUpdate(attachmentInStepTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentInStepTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attachment-in-step-types} : get all the attachmentInStepTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentInStepTypes in body.
     */
    @GetMapping("/attachment-in-step-types")
    public ResponseEntity<List<AttachmentInStepTypeDTO>> getAllAttachmentInStepTypes(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentInStepTypes");
        Page<AttachmentInStepTypeDTO> page = attachmentInStepTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-in-step-types/:id} : get the "id" attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentInStepTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<AttachmentInStepTypeDTO> getAttachmentInStepType(@PathVariable Long id) {
        log.debug("REST request to get AttachmentInStepType : {}", id);
        Optional<AttachmentInStepTypeDTO> attachmentInStepTypeDTO = attachmentInStepTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentInStepTypeDTO);
    }

    /**
     * {@code DELETE  /attachment-in-step-types/:id} : delete the "id" attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<Void> deleteAttachmentInStepType(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentInStepType : {}", id);
        attachmentInStepTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/attachment-in-step-types?query=:query} : search for the attachmentInStepType corresponding
     * to the query.
     *
     * @param query the query of the attachmentInStepType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-in-step-types")
    public ResponseEntity<List<AttachmentInStepTypeDTO>> searchAttachmentInStepTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentInStepTypes for query {}", query);
        Page<AttachmentInStepTypeDTO> page = attachmentInStepTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
