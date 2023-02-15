package com.vsm.business.web.rest;

import com.vsm.business.repository.ConsultReplyRepository;
import com.vsm.business.service.ConsultReplyService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ConsultReplyDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ConsultReply}.
 */
//@RestController
@RequestMapping("/api")
public class ConsultReplyResource {

    private final Logger log = LoggerFactory.getLogger(ConsultReplyResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConsultReply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultReplyService consultReplyService;

    private final ConsultReplyRepository consultReplyRepository;

    public ConsultReplyResource(ConsultReplyService consultReplyService, ConsultReplyRepository consultReplyRepository) {
        this.consultReplyService = consultReplyService;
        this.consultReplyRepository = consultReplyRepository;
    }

    /**
     * {@code POST  /consult-replies} : Create a new consultReply.
     *
     * @param consultReplyDTO the consultReplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultReplyDTO, or with status {@code 400 (Bad Request)} if the consultReply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consult-replies")
    public ResponseEntity<ConsultReplyDTO> createConsultReply(@RequestBody ConsultReplyDTO consultReplyDTO) throws URISyntaxException {
        log.debug("REST request to save ConsultReply : {}", consultReplyDTO);
        if (consultReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultReplyDTO result = consultReplyService.save(consultReplyDTO);
        return ResponseEntity
            .created(new URI("/api/consult-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consult-replies/:id} : Updates an existing consultReply.
     *
     * @param id the id of the consultReplyDTO to save.
     * @param consultReplyDTO the consultReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultReplyDTO,
     * or with status {@code 400 (Bad Request)} if the consultReplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consult-replies/{id}")
    public ResponseEntity<ConsultReplyDTO> updateConsultReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultReplyDTO consultReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConsultReply : {}, {}", id, consultReplyDTO);
        if (consultReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultReplyDTO result = consultReplyService.save(consultReplyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consult-replies/:id} : Partial updates given fields of an existing consultReply, field will ignore if it is null
     *
     * @param id the id of the consultReplyDTO to save.
     * @param consultReplyDTO the consultReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultReplyDTO,
     * or with status {@code 400 (Bad Request)} if the consultReplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consultReplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consult-replies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsultReplyDTO> partialUpdateConsultReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultReplyDTO consultReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConsultReply partially : {}, {}", id, consultReplyDTO);
        if (consultReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultReplyDTO> result = consultReplyService.partialUpdate(consultReplyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultReplyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /consult-replies} : get all the consultReplies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultReplies in body.
     */
    @GetMapping("/consult-replies")
    public ResponseEntity<List<ConsultReplyDTO>> getAllConsultReplies(Pageable pageable) {
        log.debug("REST request to get a page of ConsultReplies");
        Page<ConsultReplyDTO> page = consultReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consult-replies/:id} : get the "id" consultReply.
     *
     * @param id the id of the consultReplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultReplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consult-replies/{id}")
    public ResponseEntity<ConsultReplyDTO> getConsultReply(@PathVariable Long id) {
        log.debug("REST request to get ConsultReply : {}", id);
        Optional<ConsultReplyDTO> consultReplyDTO = consultReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultReplyDTO);
    }

    /**
     * {@code DELETE  /consult-replies/:id} : delete the "id" consultReply.
     *
     * @param id the id of the consultReplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consult-replies/{id}")
    public ResponseEntity<Void> deleteConsultReply(@PathVariable Long id) {
        log.debug("REST request to delete ConsultReply : {}", id);
        consultReplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/consult-replies?query=:query} : search for the consultReply corresponding
     * to the query.
     *
     * @param query the query of the consultReply search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consult-replies")
    public ResponseEntity<List<ConsultReplyDTO>> searchConsultReplies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConsultReplies for query {}", query);
        Page<ConsultReplyDTO> page = consultReplyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
