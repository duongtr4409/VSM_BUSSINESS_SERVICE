package com.vsm.business.web.rest;

import com.vsm.business.repository.FieldInFormRepository;
import com.vsm.business.service.FieldInFormService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.FieldInFormDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.FieldInForm}.
 */
//@RestController
@RequestMapping("/api")
public class FieldInFormResource {

    private final Logger log = LoggerFactory.getLogger(FieldInFormResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFieldInForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldInFormService fieldInFormService;

    private final FieldInFormRepository fieldInFormRepository;

    public FieldInFormResource(FieldInFormService fieldInFormService, FieldInFormRepository fieldInFormRepository) {
        this.fieldInFormService = fieldInFormService;
        this.fieldInFormRepository = fieldInFormRepository;
    }

    /**
     * {@code POST  /field-in-forms} : Create a new fieldInForm.
     *
     * @param fieldInFormDTO the fieldInFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldInFormDTO, or with status {@code 400 (Bad Request)} if the fieldInForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-in-forms")
    public ResponseEntity<FieldInFormDTO> createFieldInForm(@RequestBody FieldInFormDTO fieldInFormDTO) throws URISyntaxException {
        log.debug("REST request to save FieldInForm : {}", fieldInFormDTO);
        if (fieldInFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldInForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldInFormDTO result = fieldInFormService.save(fieldInFormDTO);
        return ResponseEntity
            .created(new URI("/api/field-in-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-in-forms/:id} : Updates an existing fieldInForm.
     *
     * @param id the id of the fieldInFormDTO to save.
     * @param fieldInFormDTO the fieldInFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldInFormDTO,
     * or with status {@code 400 (Bad Request)} if the fieldInFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldInFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-in-forms/{id}")
    public ResponseEntity<FieldInFormDTO> updateFieldInForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldInFormDTO fieldInFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FieldInForm : {}, {}", id, fieldInFormDTO);
        if (fieldInFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldInFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldInFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldInFormDTO result = fieldInFormService.save(fieldInFormDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldInFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-in-forms/:id} : Partial updates given fields of an existing fieldInForm, field will ignore if it is null
     *
     * @param id the id of the fieldInFormDTO to save.
     * @param fieldInFormDTO the fieldInFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldInFormDTO,
     * or with status {@code 400 (Bad Request)} if the fieldInFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldInFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldInFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-in-forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldInFormDTO> partialUpdateFieldInForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldInFormDTO fieldInFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldInForm partially : {}, {}", id, fieldInFormDTO);
        if (fieldInFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldInFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldInFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldInFormDTO> result = fieldInFormService.partialUpdate(fieldInFormDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldInFormDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-in-forms} : get all the fieldInForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldInForms in body.
     */
    @GetMapping("/field-in-forms")
    public ResponseEntity<List<FieldInFormDTO>> getAllFieldInForms(Pageable pageable) {
        log.debug("REST request to get a page of FieldInForms");
        Page<FieldInFormDTO> page = fieldInFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-in-forms/:id} : get the "id" fieldInForm.
     *
     * @param id the id of the fieldInFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldInFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-in-forms/{id}")
    public ResponseEntity<FieldInFormDTO> getFieldInForm(@PathVariable Long id) {
        log.debug("REST request to get FieldInForm : {}", id);
        Optional<FieldInFormDTO> fieldInFormDTO = fieldInFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldInFormDTO);
    }

    /**
     * {@code DELETE  /field-in-forms/:id} : delete the "id" fieldInForm.
     *
     * @param id the id of the fieldInFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-in-forms/{id}")
    public ResponseEntity<Void> deleteFieldInForm(@PathVariable Long id) {
        log.debug("REST request to delete FieldInForm : {}", id);
        fieldInFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/field-in-forms?query=:query} : search for the fieldInForm corresponding
     * to the query.
     *
     * @param query the query of the fieldInForm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/field-in-forms")
    public ResponseEntity<List<FieldInFormDTO>> searchFieldInForms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FieldInForms for query {}", query);
        Page<FieldInFormDTO> page = fieldInFormService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
