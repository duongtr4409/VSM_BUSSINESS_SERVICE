package com.vsm.business.web.rest;

import com.vsm.business.repository.ExamineReplyRepository;
import com.vsm.business.service.ExamineReplyService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ExamineReplyDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ExamineReply}.
 */
//@RestController
@RequestMapping("/api")
public class ExamineReplyResource {

    private final Logger log = LoggerFactory.getLogger(ExamineReplyResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceExamineReply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamineReplyService examineReplyService;

    private final ExamineReplyRepository examineReplyRepository;

    public ExamineReplyResource(ExamineReplyService examineReplyService, ExamineReplyRepository examineReplyRepository) {
        this.examineReplyService = examineReplyService;
        this.examineReplyRepository = examineReplyRepository;
    }

    /**
     * {@code POST  /examine-replies} : Create a new examineReply.
     *
     * @param examineReplyDTO the examineReplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examineReplyDTO, or with status {@code 400 (Bad Request)} if the examineReply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examine-replies")
    public ResponseEntity<ExamineReplyDTO> createExamineReply(@RequestBody ExamineReplyDTO examineReplyDTO) throws URISyntaxException {
        log.debug("REST request to save ExamineReply : {}", examineReplyDTO);
        if (examineReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new examineReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamineReplyDTO result = examineReplyService.save(examineReplyDTO);
        return ResponseEntity
            .created(new URI("/api/examine-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examine-replies/:id} : Updates an existing examineReply.
     *
     * @param id the id of the examineReplyDTO to save.
     * @param examineReplyDTO the examineReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineReplyDTO,
     * or with status {@code 400 (Bad Request)} if the examineReplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examineReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examine-replies/{id}")
    public ResponseEntity<ExamineReplyDTO> updateExamineReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineReplyDTO examineReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExamineReply : {}, {}", id, examineReplyDTO);
        if (examineReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamineReplyDTO result = examineReplyService.save(examineReplyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examineReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /examine-replies/:id} : Partial updates given fields of an existing examineReply, field will ignore if it is null
     *
     * @param id the id of the examineReplyDTO to save.
     * @param examineReplyDTO the examineReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineReplyDTO,
     * or with status {@code 400 (Bad Request)} if the examineReplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examineReplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examineReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/examine-replies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExamineReplyDTO> partialUpdateExamineReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineReplyDTO examineReplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExamineReply partially : {}, {}", id, examineReplyDTO);
        if (examineReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamineReplyDTO> result = examineReplyService.partialUpdate(examineReplyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examineReplyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /examine-replies} : get all the examineReplies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examineReplies in body.
     */
    @GetMapping("/examine-replies")
    public ResponseEntity<List<ExamineReplyDTO>> getAllExamineReplies(Pageable pageable) {
        log.debug("REST request to get a page of ExamineReplies");
        Page<ExamineReplyDTO> page = examineReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /examine-replies/:id} : get the "id" examineReply.
     *
     * @param id the id of the examineReplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examineReplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examine-replies/{id}")
    public ResponseEntity<ExamineReplyDTO> getExamineReply(@PathVariable Long id) {
        log.debug("REST request to get ExamineReply : {}", id);
        Optional<ExamineReplyDTO> examineReplyDTO = examineReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examineReplyDTO);
    }

    /**
     * {@code DELETE  /examine-replies/:id} : delete the "id" examineReply.
     *
     * @param id the id of the examineReplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examine-replies/{id}")
    public ResponseEntity<Void> deleteExamineReply(@PathVariable Long id) {
        log.debug("REST request to delete ExamineReply : {}", id);
        examineReplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/examine-replies?query=:query} : search for the examineReply corresponding
     * to the query.
     *
     * @param query the query of the examineReply search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/examine-replies")
    public ResponseEntity<List<ExamineReplyDTO>> searchExamineReplies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ExamineReplies for query {}", query);
        Page<ExamineReplyDTO> page = examineReplyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
