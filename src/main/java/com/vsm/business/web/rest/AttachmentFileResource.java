package com.vsm.business.web.rest;

import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.AttachmentFileService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.AttachmentFileDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.AttachmentFile}.
 */
//@RestController
@RequestMapping("/api")
public class AttachmentFileResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentFileResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentFileService attachmentFileService;

    private final AttachmentFileRepository attachmentFileRepository;

    public AttachmentFileResource(AttachmentFileService attachmentFileService, AttachmentFileRepository attachmentFileRepository) {
        this.attachmentFileService = attachmentFileService;
        this.attachmentFileRepository = attachmentFileRepository;
    }

    /**
     * {@code POST  /attachment-files} : Create a new attachmentFile.
     *
     * @param attachmentFileDTO the attachmentFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentFileDTO, or with status {@code 400 (Bad Request)} if the attachmentFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-files")
    public ResponseEntity<AttachmentFileDTO> createAttachmentFile(@RequestBody AttachmentFileDTO attachmentFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachmentFile : {}", attachmentFileDTO);
        if (attachmentFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentFileDTO result = attachmentFileService.save(attachmentFileDTO);
        return ResponseEntity
            .created(new URI("/api/attachment-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-files/:id} : Updates an existing attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to save.
     * @param attachmentFileDTO the attachmentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentFileDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-files/{id}")
    public ResponseEntity<AttachmentFileDTO> updateAttachmentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentFileDTO attachmentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentFile : {}, {}", id, attachmentFileDTO);
        if (attachmentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachmentFileDTO result = attachmentFileService.save(attachmentFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachment-files/:id} : Partial updates given fields of an existing attachmentFile, field will ignore if it is null
     *
     * @param id the id of the attachmentFileDTO to save.
     * @param attachmentFileDTO the attachmentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentFileDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachmentFileDTO> partialUpdateAttachmentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentFileDTO attachmentFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentFile partially : {}, {}", id, attachmentFileDTO);
        if (attachmentFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachmentFileDTO> result = attachmentFileService.partialUpdate(attachmentFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attachment-files} : get all the attachmentFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentFiles in body.
     */
    @GetMapping("/attachment-files")
    public ResponseEntity<List<AttachmentFileDTO>> getAllAttachmentFiles(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentFiles");
        Page<AttachmentFileDTO> page = attachmentFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-files/:id} : get the "id" attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-files/{id}")
    public ResponseEntity<AttachmentFileDTO> getAttachmentFile(@PathVariable Long id) {
        log.debug("REST request to get AttachmentFile : {}", id);
        Optional<AttachmentFileDTO> attachmentFileDTO = attachmentFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentFileDTO);
    }

    /**
     * {@code DELETE  /attachment-files/:id} : delete the "id" attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-files/{id}")
    public ResponseEntity<Void> deleteAttachmentFile(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentFile : {}", id);
        attachmentFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/attachment-files?query=:query} : search for the attachmentFile corresponding
     * to the query.
     *
     * @param query the query of the attachmentFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-files")
    public ResponseEntity<List<AttachmentFileDTO>> searchAttachmentFiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentFiles for query {}", query);
        Page<AttachmentFileDTO> page = attachmentFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
