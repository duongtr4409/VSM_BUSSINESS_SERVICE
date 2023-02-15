package com.vsm.business.web.rest;

import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.service.FieldDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.FieldDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.FieldData}.
 */
//@RestController
@RequestMapping("/api")
public class FieldDataResource {

    private final Logger log = LoggerFactory.getLogger(FieldDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFieldData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldDataService fieldDataService;

    private final FieldDataRepository fieldDataRepository;

    public FieldDataResource(FieldDataService fieldDataService, FieldDataRepository fieldDataRepository) {
        this.fieldDataService = fieldDataService;
        this.fieldDataRepository = fieldDataRepository;
    }

    /**
     * {@code POST  /field-data} : Create a new fieldData.
     *
     * @param fieldDataDTO the fieldDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldDataDTO, or with status {@code 400 (Bad Request)} if the fieldData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-data")
    public ResponseEntity<FieldDataDTO> createFieldData(@RequestBody FieldDataDTO fieldDataDTO) throws URISyntaxException {
        log.debug("REST request to save FieldData : {}", fieldDataDTO);
        if (fieldDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldDataDTO result = fieldDataService.save(fieldDataDTO);
        return ResponseEntity
            .created(new URI("/api/field-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-data/:id} : Updates an existing fieldData.
     *
     * @param id the id of the fieldDataDTO to save.
     * @param fieldDataDTO the fieldDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDataDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-data/{id}")
    public ResponseEntity<FieldDataDTO> updateFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldDataDTO fieldDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FieldData : {}, {}", id, fieldDataDTO);
        if (fieldDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldDataDTO result = fieldDataService.save(fieldDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-data/:id} : Partial updates given fields of an existing fieldData, field will ignore if it is null
     *
     * @param id the id of the fieldDataDTO to save.
     * @param fieldDataDTO the fieldDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDataDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldDataDTO> partialUpdateFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldDataDTO fieldDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldData partially : {}, {}", id, fieldDataDTO);
        if (fieldDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldDataDTO> result = fieldDataService.partialUpdate(fieldDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-data} : get all the fieldData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldData in body.
     */
    @GetMapping("/field-data")
    public ResponseEntity<List<FieldDataDTO>> getAllFieldData(Pageable pageable) {
        log.debug("REST request to get a page of FieldData");
        Page<FieldDataDTO> page = fieldDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-data/:id} : get the "id" fieldData.
     *
     * @param id the id of the fieldDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-data/{id}")
    public ResponseEntity<FieldDataDTO> getFieldData(@PathVariable Long id) {
        log.debug("REST request to get FieldData : {}", id);
        Optional<FieldDataDTO> fieldDataDTO = fieldDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldDataDTO);
    }

    /**
     * {@code DELETE  /field-data/:id} : delete the "id" fieldData.
     *
     * @param id the id of the fieldDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-data/{id}")
    public ResponseEntity<Void> deleteFieldData(@PathVariable Long id) {
        log.debug("REST request to delete FieldData : {}", id);
        fieldDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/field-data?query=:query} : search for the fieldData corresponding
     * to the query.
     *
     * @param query the query of the fieldData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/field-data")
    public ResponseEntity<List<FieldDataDTO>> searchFieldData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FieldData for query {}", query);
        Page<FieldDataDTO> page = fieldDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
