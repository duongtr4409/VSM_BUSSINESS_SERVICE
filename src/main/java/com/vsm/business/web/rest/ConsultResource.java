package com.vsm.business.web.rest;

import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.service.ConsultService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ConsultDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Consult}.
 */
//@RestController
@RequestMapping("/api")
public class ConsultResource {

    private final Logger log = LoggerFactory.getLogger(ConsultResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConsult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultService consultService;

    private final ConsultRepository consultRepository;

    public ConsultResource(ConsultService consultService, ConsultRepository consultRepository) {
        this.consultService = consultService;
        this.consultRepository = consultRepository;
    }

    /**
     * {@code POST  /consults} : Create a new consult.
     *
     * @param consultDTO the consultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultDTO, or with status {@code 400 (Bad Request)} if the consult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consults")
    public ResponseEntity<ConsultDTO> createConsult(@RequestBody ConsultDTO consultDTO) throws URISyntaxException {
        log.debug("REST request to save Consult : {}", consultDTO);
        if (consultDTO.getId() != null) {
            throw new BadRequestAlertException("A new consult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultDTO result = consultService.save(consultDTO);
        return ResponseEntity
            .created(new URI("/api/consults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consults/:id} : Updates an existing consult.
     *
     * @param id the id of the consultDTO to save.
     * @param consultDTO the consultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultDTO,
     * or with status {@code 400 (Bad Request)} if the consultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consults/{id}")
    public ResponseEntity<ConsultDTO> updateConsult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultDTO consultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Consult : {}, {}", id, consultDTO);
        if (consultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultDTO result = consultService.save(consultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consults/:id} : Partial updates given fields of an existing consult, field will ignore if it is null
     *
     * @param id the id of the consultDTO to save.
     * @param consultDTO the consultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultDTO,
     * or with status {@code 400 (Bad Request)} if the consultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consults/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsultDTO> partialUpdateConsult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultDTO consultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Consult partially : {}, {}", id, consultDTO);
        if (consultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultDTO> result = consultService.partialUpdate(consultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /consults} : get all the consults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consults in body.
     */
    @GetMapping("/consults")
    public ResponseEntity<List<ConsultDTO>> getAllConsults(Pageable pageable) {
        log.debug("REST request to get a page of Consults");
        Page<ConsultDTO> page = consultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consults/:id} : get the "id" consult.
     *
     * @param id the id of the consultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consults/{id}")
    public ResponseEntity<ConsultDTO> getConsult(@PathVariable Long id) {
        log.debug("REST request to get Consult : {}", id);
        Optional<ConsultDTO> consultDTO = consultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultDTO);
    }

    /**
     * {@code DELETE  /consults/:id} : delete the "id" consult.
     *
     * @param id the id of the consultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consults/{id}")
    public ResponseEntity<Void> deleteConsult(@PathVariable Long id) {
        log.debug("REST request to delete Consult : {}", id);
        consultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/consults?query=:query} : search for the consult corresponding
     * to the query.
     *
     * @param query the query of the consult search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consults")
    public ResponseEntity<List<ConsultDTO>> searchConsults(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Consults for query {}", query);
        Page<ConsultDTO> page = consultService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
