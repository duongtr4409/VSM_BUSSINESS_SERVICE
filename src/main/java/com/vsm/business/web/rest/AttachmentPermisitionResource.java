package com.vsm.business.web.rest;

import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.service.AttachmentPermisitionService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.AttachmentPermisition}.
 */
//@RestController
@RequestMapping("/api")
public class AttachmentPermisitionResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentPermisitionResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentPermisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentPermisitionService attachmentPermisitionService;

    private final AttachmentPermisitionRepository attachmentPermisitionRepository;

    public AttachmentPermisitionResource(
        AttachmentPermisitionService attachmentPermisitionService,
        AttachmentPermisitionRepository attachmentPermisitionRepository
    ) {
        this.attachmentPermisitionService = attachmentPermisitionService;
        this.attachmentPermisitionRepository = attachmentPermisitionRepository;
    }

    /**
     * {@code POST  /attachment-permisitions} : Create a new attachmentPermisition.
     *
     * @param attachmentPermisitionDTO the attachmentPermisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentPermisitionDTO, or with status {@code 400 (Bad Request)} if the attachmentPermisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-permisitions")
    public ResponseEntity<AttachmentPermisitionDTO> createAttachmentPermisition(
        @RequestBody AttachmentPermisitionDTO attachmentPermisitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AttachmentPermisition : {}", attachmentPermisitionDTO);
        if (attachmentPermisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachmentPermisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentPermisitionDTO result = attachmentPermisitionService.save(attachmentPermisitionDTO);
        return ResponseEntity
            .created(new URI("/api/attachment-permisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachment-permisitions/:id} : Updates an existing attachmentPermisition.
     *
     * @param id the id of the attachmentPermisitionDTO to save.
     * @param attachmentPermisitionDTO the attachmentPermisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentPermisitionDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentPermisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentPermisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-permisitions/{id}")
    public ResponseEntity<AttachmentPermisitionDTO> updateAttachmentPermisition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentPermisitionDTO attachmentPermisitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentPermisition : {}, {}", id, attachmentPermisitionDTO);
        if (attachmentPermisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentPermisitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentPermisitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachmentPermisitionDTO result = attachmentPermisitionService.save(attachmentPermisitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentPermisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachment-permisitions/:id} : Partial updates given fields of an existing attachmentPermisition, field will ignore if it is null
     *
     * @param id the id of the attachmentPermisitionDTO to save.
     * @param attachmentPermisitionDTO the attachmentPermisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentPermisitionDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentPermisitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentPermisitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentPermisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-permisitions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachmentPermisitionDTO> partialUpdateAttachmentPermisition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentPermisitionDTO attachmentPermisitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentPermisition partially : {}, {}", id, attachmentPermisitionDTO);
        if (attachmentPermisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentPermisitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentPermisitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachmentPermisitionDTO> result = attachmentPermisitionService.partialUpdate(attachmentPermisitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentPermisitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attachment-permisitions} : get all the attachmentPermisitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentPermisitions in body.
     */
    @GetMapping("/attachment-permisitions")
    public ResponseEntity<List<AttachmentPermisitionDTO>> getAllAttachmentPermisitions(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentPermisitions");
        Page<AttachmentPermisitionDTO> page = attachmentPermisitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-permisitions/:id} : get the "id" attachmentPermisition.
     *
     * @param id the id of the attachmentPermisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentPermisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-permisitions/{id}")
    public ResponseEntity<AttachmentPermisitionDTO> getAttachmentPermisition(@PathVariable Long id) {
        log.debug("REST request to get AttachmentPermisition : {}", id);
        Optional<AttachmentPermisitionDTO> attachmentPermisitionDTO = attachmentPermisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentPermisitionDTO);
    }

    /**
     * {@code DELETE  /attachment-permisitions/:id} : delete the "id" attachmentPermisition.
     *
     * @param id the id of the attachmentPermisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-permisitions/{id}")
    public ResponseEntity<Void> deleteAttachmentPermisition(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentPermisition : {}", id);
        attachmentPermisitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/attachment-permisitions?query=:query} : search for the attachmentPermisition corresponding
     * to the query.
     *
     * @param query the query of the attachmentPermisition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-permisitions")
    public ResponseEntity<List<AttachmentPermisitionDTO>> searchAttachmentPermisitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentPermisitions for query {}", query);
        Page<AttachmentPermisitionDTO> page = attachmentPermisitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
