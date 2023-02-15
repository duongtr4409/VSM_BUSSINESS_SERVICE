package com.vsm.business.web.rest;

import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.service.ExamineService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ExamineDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Examine}.
 */
//@RestController
@RequestMapping("/api")
public class ExamineResource {

    private final Logger log = LoggerFactory.getLogger(ExamineResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceExamine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamineService examineService;

    private final ExamineRepository examineRepository;

    public ExamineResource(ExamineService examineService, ExamineRepository examineRepository) {
        this.examineService = examineService;
        this.examineRepository = examineRepository;
    }

    /**
     * {@code POST  /examines} : Create a new examine.
     *
     * @param examineDTO the examineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examineDTO, or with status {@code 400 (Bad Request)} if the examine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examines")
    public ResponseEntity<ExamineDTO> createExamine(@RequestBody ExamineDTO examineDTO) throws URISyntaxException {
        log.debug("REST request to save Examine : {}", examineDTO);
        if (examineDTO.getId() != null) {
            throw new BadRequestAlertException("A new examine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamineDTO result = examineService.save(examineDTO);
        return ResponseEntity
            .created(new URI("/api/examines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examines/:id} : Updates an existing examine.
     *
     * @param id the id of the examineDTO to save.
     * @param examineDTO the examineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineDTO,
     * or with status {@code 400 (Bad Request)} if the examineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examines/{id}")
    public ResponseEntity<ExamineDTO> updateExamine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineDTO examineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Examine : {}, {}", id, examineDTO);
        if (examineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamineDTO result = examineService.save(examineDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /examines/:id} : Partial updates given fields of an existing examine, field will ignore if it is null
     *
     * @param id the id of the examineDTO to save.
     * @param examineDTO the examineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineDTO,
     * or with status {@code 400 (Bad Request)} if the examineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/examines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExamineDTO> partialUpdateExamine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineDTO examineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Examine partially : {}, {}", id, examineDTO);
        if (examineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamineDTO> result = examineService.partialUpdate(examineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /examines} : get all the examines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examines in body.
     */
    @GetMapping("/examines")
    public ResponseEntity<List<ExamineDTO>> getAllExamines(Pageable pageable) {
        log.debug("REST request to get a page of Examines");
        Page<ExamineDTO> page = examineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /examines/:id} : get the "id" examine.
     *
     * @param id the id of the examineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examines/{id}")
    public ResponseEntity<ExamineDTO> getExamine(@PathVariable Long id) {
        log.debug("REST request to get Examine : {}", id);
        Optional<ExamineDTO> examineDTO = examineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examineDTO);
    }

    /**
     * {@code DELETE  /examines/:id} : delete the "id" examine.
     *
     * @param id the id of the examineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examines/{id}")
    public ResponseEntity<Void> deleteExamine(@PathVariable Long id) {
        log.debug("REST request to delete Examine : {}", id);
        examineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/examines?query=:query} : search for the examine corresponding
     * to the query.
     *
     * @param query the query of the examine search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/examines")
    public ResponseEntity<List<ExamineDTO>> searchExamines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Examines for query {}", query);
        Page<ExamineDTO> page = examineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
