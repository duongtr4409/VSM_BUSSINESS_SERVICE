package com.vsm.business.web.rest;

import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.service.MailTemplateService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.MailTemplateDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.MailTemplate}.
 */
//@RestController
@RequestMapping("/api")
public class MailTemplateResource {

    private final Logger log = LoggerFactory.getLogger(MailTemplateResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMailTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MailTemplateService mailTemplateService;

    private final MailTemplateRepository mailTemplateRepository;

    public MailTemplateResource(MailTemplateService mailTemplateService, MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateService = mailTemplateService;
        this.mailTemplateRepository = mailTemplateRepository;
    }

    /**
     * {@code POST  /mail-templates} : Create a new mailTemplate.
     *
     * @param mailTemplateDTO the mailTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mailTemplateDTO, or with status {@code 400 (Bad Request)} if the mailTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mail-templates")
    public ResponseEntity<MailTemplateDTO> createMailTemplate(@RequestBody MailTemplateDTO mailTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save MailTemplate : {}", mailTemplateDTO);
        if (mailTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new mailTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MailTemplateDTO result = mailTemplateService.save(mailTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/mail-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mail-templates/:id} : Updates an existing mailTemplate.
     *
     * @param id the id of the mailTemplateDTO to save.
     * @param mailTemplateDTO the mailTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the mailTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mailTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mail-templates/{id}")
    public ResponseEntity<MailTemplateDTO> updateMailTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MailTemplateDTO mailTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MailTemplate : {}, {}", id, mailTemplateDTO);
        if (mailTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MailTemplateDTO result = mailTemplateService.save(mailTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mail-templates/:id} : Partial updates given fields of an existing mailTemplate, field will ignore if it is null
     *
     * @param id the id of the mailTemplateDTO to save.
     * @param mailTemplateDTO the mailTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the mailTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mailTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mailTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mail-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MailTemplateDTO> partialUpdateMailTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MailTemplateDTO mailTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MailTemplate partially : {}, {}", id, mailTemplateDTO);
        if (mailTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MailTemplateDTO> result = mailTemplateService.partialUpdate(mailTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mail-templates} : get all the mailTemplates.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mailTemplates in body.
     */
    @GetMapping("/mail-templates")
    public ResponseEntity<List<MailTemplateDTO>> getAllMailTemplates(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of MailTemplates");
        Page<MailTemplateDTO> page;
        if (eagerload) {
            page = mailTemplateService.findAllWithEagerRelationships(pageable);
        } else {
            page = mailTemplateService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mail-templates/:id} : get the "id" mailTemplate.
     *
     * @param id the id of the mailTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mailTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mail-templates/{id}")
    public ResponseEntity<MailTemplateDTO> getMailTemplate(@PathVariable Long id) {
        log.debug("REST request to get MailTemplate : {}", id);
        Optional<MailTemplateDTO> mailTemplateDTO = mailTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mailTemplateDTO);
    }

    /**
     * {@code DELETE  /mail-templates/:id} : delete the "id" mailTemplate.
     *
     * @param id the id of the mailTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mail-templates/{id}")
    public ResponseEntity<Void> deleteMailTemplate(@PathVariable Long id) {
        log.debug("REST request to delete MailTemplate : {}", id);
        mailTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/mail-templates?query=:query} : search for the mailTemplate corresponding
     * to the query.
     *
     * @param query the query of the mailTemplate search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mail-templates")
    public ResponseEntity<List<MailTemplateDTO>> searchMailTemplates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MailTemplates for query {}", query);
        Page<MailTemplateDTO> page = mailTemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
