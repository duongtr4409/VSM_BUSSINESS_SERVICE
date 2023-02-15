package com.vsm.business.web.rest;

import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.service.MailLogService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.MailLogDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.MailLog}.
 */
//@RestController
@RequestMapping("/api")
public class MailLogResource {

    private final Logger log = LoggerFactory.getLogger(MailLogResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMailLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MailLogService mailLogService;

    private final MailLogRepository mailLogRepository;

    public MailLogResource(MailLogService mailLogService, MailLogRepository mailLogRepository) {
        this.mailLogService = mailLogService;
        this.mailLogRepository = mailLogRepository;
    }

    /**
     * {@code POST  /mail-logs} : Create a new mailLog.
     *
     * @param mailLogDTO the mailLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mailLogDTO, or with status {@code 400 (Bad Request)} if the mailLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mail-logs")
    public ResponseEntity<MailLogDTO> createMailLog(@RequestBody MailLogDTO mailLogDTO) throws URISyntaxException {
        log.debug("REST request to save MailLog : {}", mailLogDTO);
        if (mailLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new mailLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MailLogDTO result = mailLogService.save(mailLogDTO);
        return ResponseEntity
            .created(new URI("/api/mail-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mail-logs/:id} : Updates an existing mailLog.
     *
     * @param id the id of the mailLogDTO to save.
     * @param mailLogDTO the mailLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailLogDTO,
     * or with status {@code 400 (Bad Request)} if the mailLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mailLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mail-logs/{id}")
    public ResponseEntity<MailLogDTO> updateMailLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MailLogDTO mailLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MailLog : {}, {}", id, mailLogDTO);
        if (mailLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MailLogDTO result = mailLogService.save(mailLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mail-logs/:id} : Partial updates given fields of an existing mailLog, field will ignore if it is null
     *
     * @param id the id of the mailLogDTO to save.
     * @param mailLogDTO the mailLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailLogDTO,
     * or with status {@code 400 (Bad Request)} if the mailLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mailLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mailLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mail-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MailLogDTO> partialUpdateMailLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MailLogDTO mailLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MailLog partially : {}, {}", id, mailLogDTO);
        if (mailLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MailLogDTO> result = mailLogService.partialUpdate(mailLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mail-logs} : get all the mailLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mailLogs in body.
     */
    @GetMapping("/mail-logs")
    public ResponseEntity<List<MailLogDTO>> getAllMailLogs(Pageable pageable) {
        log.debug("REST request to get a page of MailLogs");
        Page<MailLogDTO> page = mailLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mail-logs/:id} : get the "id" mailLog.
     *
     * @param id the id of the mailLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mailLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mail-logs/{id}")
    public ResponseEntity<MailLogDTO> getMailLog(@PathVariable Long id) {
        log.debug("REST request to get MailLog : {}", id);
        Optional<MailLogDTO> mailLogDTO = mailLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mailLogDTO);
    }

    /**
     * {@code DELETE  /mail-logs/:id} : delete the "id" mailLog.
     *
     * @param id the id of the mailLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mail-logs/{id}")
    public ResponseEntity<Void> deleteMailLog(@PathVariable Long id) {
        log.debug("REST request to delete MailLog : {}", id);
        mailLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/mail-logs?query=:query} : search for the mailLog corresponding
     * to the query.
     *
     * @param query the query of the mailLog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mail-logs")
    public ResponseEntity<List<MailLogDTO>> searchMailLogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MailLogs for query {}", query);
        Page<MailLogDTO> page = mailLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
