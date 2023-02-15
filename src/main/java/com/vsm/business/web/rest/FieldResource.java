package com.vsm.business.web.rest;

import com.vsm.business.repository.FieldRepository;
import com.vsm.business.service.FieldService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.FieldDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Field}.
 */
//@RestController
@RequestMapping("/api")
public class FieldResource {

    private final Logger log = LoggerFactory.getLogger(FieldResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldService fieldService;

    private final FieldRepository fieldRepository;

    public FieldResource(FieldService fieldService, FieldRepository fieldRepository) {
        this.fieldService = fieldService;
        this.fieldRepository = fieldRepository;
    }

    /**
     * {@code POST  /fields} : Create a new field.
     *
     * @param fieldDTO the fieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldDTO, or with status {@code 400 (Bad Request)} if the field has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fields")
    public ResponseEntity<FieldDTO> createField(@RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to save Field : {}", fieldDTO);
        if (fieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new field cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity
            .created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fields/:id} : Updates an existing field.
     *
     * @param id the id of the fieldDTO to save.
     * @param fieldDTO the fieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fields/{id}")
    public ResponseEntity<FieldDTO> updateField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldDTO fieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Field : {}, {}", id, fieldDTO);
        if (fieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldDTO result = fieldService.save(fieldDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fields/:id} : Partial updates given fields of an existing field, field will ignore if it is null
     *
     * @param id the id of the fieldDTO to save.
     * @param fieldDTO the fieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldDTO> partialUpdateField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldDTO fieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Field partially : {}, {}", id, fieldDTO);
        if (fieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldDTO> result = fieldService.partialUpdate(fieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fields} : get all the fields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fields in body.
     */
    @GetMapping("/fields")
    public ResponseEntity<List<FieldDTO>> getAllFields(Pageable pageable) {
        log.debug("REST request to get a page of Fields");
        Page<FieldDTO> page = fieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fields/:id} : get the "id" field.
     *
     * @param id the id of the fieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fields/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable Long id) {
        log.debug("REST request to get Field : {}", id);
        Optional<FieldDTO> fieldDTO = fieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldDTO);
    }

    /**
     * {@code DELETE  /fields/:id} : delete the "id" field.
     *
     * @param id the id of the fieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        log.debug("REST request to delete Field : {}", id);
        fieldService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/fields?query=:query} : search for the field corresponding
     * to the query.
     *
     * @param query the query of the field search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fields")
    public ResponseEntity<List<FieldDTO>> searchFields(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fields for query {}", query);
        Page<FieldDTO> page = fieldService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
