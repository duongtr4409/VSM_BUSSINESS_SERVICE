package com.vsm.business.web.rest;

import com.vsm.business.repository.FormDataRepository;
import com.vsm.business.service.FormDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.FormDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.FormData}.
 */
//@RestController
@RequestMapping("/api")
public class FormDataResource {

    private final Logger log = LoggerFactory.getLogger(FormDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFormData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormDataService formDataService;

    private final FormDataRepository formDataRepository;

    public FormDataResource(FormDataService formDataService, FormDataRepository formDataRepository) {
        this.formDataService = formDataService;
        this.formDataRepository = formDataRepository;
    }

    /**
     * {@code POST  /form-data} : Create a new formData.
     *
     * @param formDataDTO the formDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formDataDTO, or with status {@code 400 (Bad Request)} if the formData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-data")
    public ResponseEntity<FormDataDTO> createFormData(@RequestBody FormDataDTO formDataDTO) throws URISyntaxException {
        log.debug("REST request to save FormData : {}", formDataDTO);
        if (formDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new formData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormDataDTO result = formDataService.save(formDataDTO);
        return ResponseEntity
            .created(new URI("/api/form-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-data/:id} : Updates an existing formData.
     *
     * @param id the id of the formDataDTO to save.
     * @param formDataDTO the formDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDataDTO,
     * or with status {@code 400 (Bad Request)} if the formDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-data/{id}")
    public ResponseEntity<FormDataDTO> updateFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormDataDTO formDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FormData : {}, {}", id, formDataDTO);
        if (formDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormDataDTO result = formDataService.save(formDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /form-data/:id} : Partial updates given fields of an existing formData, field will ignore if it is null
     *
     * @param id the id of the formDataDTO to save.
     * @param formDataDTO the formDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDataDTO,
     * or with status {@code 400 (Bad Request)} if the formDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormDataDTO> partialUpdateFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormDataDTO formDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormData partially : {}, {}", id, formDataDTO);
        if (formDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormDataDTO> result = formDataService.partialUpdate(formDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /form-data} : get all the formData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formData in body.
     */
    @GetMapping("/form-data")
    public ResponseEntity<List<FormDataDTO>> getAllFormData(Pageable pageable) {
        log.debug("REST request to get a page of FormData");
        Page<FormDataDTO> page = formDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /form-data/:id} : get the "id" formData.
     *
     * @param id the id of the formDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-data/{id}")
    public ResponseEntity<FormDataDTO> getFormData(@PathVariable Long id) {
        log.debug("REST request to get FormData : {}", id);
        Optional<FormDataDTO> formDataDTO = formDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formDataDTO);
    }

    /**
     * {@code DELETE  /form-data/:id} : delete the "id" formData.
     *
     * @param id the id of the formDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-data/{id}")
    public ResponseEntity<Void> deleteFormData(@PathVariable Long id) {
        log.debug("REST request to delete FormData : {}", id);
        formDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/form-data?query=:query} : search for the formData corresponding
     * to the query.
     *
     * @param query the query of the formData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/form-data")
    public ResponseEntity<List<FormDataDTO>> searchFormData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FormData for query {}", query);
        Page<FormDataDTO> page = formDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
