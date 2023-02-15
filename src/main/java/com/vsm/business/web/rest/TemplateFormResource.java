package com.vsm.business.web.rest;

import com.vsm.business.repository.TemplateFormRepository;
import com.vsm.business.service.TemplateFormService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.TemplateFormDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.TemplateForm}.
 */
//@RestController
@RequestMapping("/api")
public class TemplateFormResource {

    private final Logger log = LoggerFactory.getLogger(TemplateFormResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTemplateForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateFormService templateFormService;

    private final TemplateFormRepository templateFormRepository;

    public TemplateFormResource(TemplateFormService templateFormService, TemplateFormRepository templateFormRepository) {
        this.templateFormService = templateFormService;
        this.templateFormRepository = templateFormRepository;
    }

    /**
     * {@code POST  /template-forms} : Create a new templateForm.
     *
     * @param templateFormDTO the templateFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateFormDTO, or with status {@code 400 (Bad Request)} if the templateForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-forms")
    public ResponseEntity<TemplateFormDTO> createTemplateForm(@RequestBody TemplateFormDTO templateFormDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateForm : {}", templateFormDTO);
        if (templateFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateFormDTO result = templateFormService.save(templateFormDTO);
        return ResponseEntity
            .created(new URI("/api/template-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-forms/:id} : Updates an existing templateForm.
     *
     * @param id the id of the templateFormDTO to save.
     * @param templateFormDTO the templateFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-forms/{id}")
    public ResponseEntity<TemplateFormDTO> updateTemplateForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateFormDTO templateFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateForm : {}, {}", id, templateFormDTO);
        if (templateFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateFormDTO result = templateFormService.save(templateFormDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, templateFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-forms/:id} : Partial updates given fields of an existing templateForm, field will ignore if it is null
     *
     * @param id the id of the templateFormDTO to save.
     * @param templateFormDTO the templateFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateFormDTO> partialUpdateTemplateForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateFormDTO templateFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateForm partially : {}, {}", id, templateFormDTO);
        if (templateFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateFormDTO> result = templateFormService.partialUpdate(templateFormDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, templateFormDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-forms} : get all the templateForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateForms in body.
     */
    @GetMapping("/template-forms")
    public ResponseEntity<List<TemplateFormDTO>> getAllTemplateForms(Pageable pageable) {
        log.debug("REST request to get a page of TemplateForms");
        Page<TemplateFormDTO> page = templateFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-forms/:id} : get the "id" templateForm.
     *
     * @param id the id of the templateFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-forms/{id}")
    public ResponseEntity<TemplateFormDTO> getTemplateForm(@PathVariable Long id) {
        log.debug("REST request to get TemplateForm : {}", id);
        Optional<TemplateFormDTO> templateFormDTO = templateFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateFormDTO);
    }

    /**
     * {@code DELETE  /template-forms/:id} : delete the "id" templateForm.
     *
     * @param id the id of the templateFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-forms/{id}")
    public ResponseEntity<Void> deleteTemplateForm(@PathVariable Long id) {
        log.debug("REST request to delete TemplateForm : {}", id);
        templateFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/template-forms?query=:query} : search for the templateForm corresponding
     * to the query.
     *
     * @param query the query of the templateForm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/template-forms")
    public ResponseEntity<List<TemplateFormDTO>> searchTemplateForms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TemplateForms for query {}", query);
        Page<TemplateFormDTO> page = templateFormService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
