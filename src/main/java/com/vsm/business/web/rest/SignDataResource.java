package com.vsm.business.web.rest;

import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.service.SignDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.SignDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.SignData}.
 */
//@RestController
@RequestMapping("/api")
public class SignDataResource {

    private final Logger log = LoggerFactory.getLogger(SignDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceSignData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignDataService signDataService;

    private final SignDataRepository signDataRepository;

    public SignDataResource(SignDataService signDataService, SignDataRepository signDataRepository) {
        this.signDataService = signDataService;
        this.signDataRepository = signDataRepository;
    }

    /**
     * {@code POST  /sign-data} : Create a new signData.
     *
     * @param signDataDTO the signDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signDataDTO, or with status {@code 400 (Bad Request)} if the signData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sign-data")
    public ResponseEntity<SignDataDTO> createSignData(@RequestBody SignDataDTO signDataDTO) throws URISyntaxException {
        log.debug("REST request to save SignData : {}", signDataDTO);
        if (signDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new signData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignDataDTO result = signDataService.save(signDataDTO);
        return ResponseEntity
            .created(new URI("/api/sign-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sign-data/:id} : Updates an existing signData.
     *
     * @param id the id of the signDataDTO to save.
     * @param signDataDTO the signDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signDataDTO,
     * or with status {@code 400 (Bad Request)} if the signDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sign-data/{id}")
    public ResponseEntity<SignDataDTO> updateSignData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignDataDTO signDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SignData : {}, {}", id, signDataDTO);
        if (signDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SignDataDTO result = signDataService.save(signDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, signDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sign-data/:id} : Partial updates given fields of an existing signData, field will ignore if it is null
     *
     * @param id the id of the signDataDTO to save.
     * @param signDataDTO the signDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signDataDTO,
     * or with status {@code 400 (Bad Request)} if the signDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the signDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the signDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sign-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SignDataDTO> partialUpdateSignData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignDataDTO signDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SignData partially : {}, {}", id, signDataDTO);
        if (signDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SignDataDTO> result = signDataService.partialUpdate(signDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, signDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sign-data} : get all the signData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signData in body.
     */
    @GetMapping("/sign-data")
    public ResponseEntity<List<SignDataDTO>> getAllSignData(Pageable pageable) {
        log.debug("REST request to get a page of SignData");
        Page<SignDataDTO> page = signDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sign-data/:id} : get the "id" signData.
     *
     * @param id the id of the signDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sign-data/{id}")
    public ResponseEntity<SignDataDTO> getSignData(@PathVariable Long id) {
        log.debug("REST request to get SignData : {}", id);
        Optional<SignDataDTO> signDataDTO = signDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signDataDTO);
    }

    /**
     * {@code DELETE  /sign-data/:id} : delete the "id" signData.
     *
     * @param id the id of the signDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sign-data/{id}")
    public ResponseEntity<Void> deleteSignData(@PathVariable Long id) {
        log.debug("REST request to delete SignData : {}", id);
        signDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/sign-data?query=:query} : search for the signData corresponding
     * to the query.
     *
     * @param query the query of the signData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sign-data")
    public ResponseEntity<List<SignDataDTO>> searchSignData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SignData for query {}", query);
        Page<SignDataDTO> page = signDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
