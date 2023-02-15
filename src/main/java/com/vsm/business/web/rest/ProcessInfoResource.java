package com.vsm.business.web.rest;

import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.service.ProcessInfoService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ProcessInfoDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ProcessInfo}.
 */
//@RestController
@RequestMapping("/api")
public class ProcessInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInfoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceProcessInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInfoService processInfoService;

    private final ProcessInfoRepository processInfoRepository;

    public ProcessInfoResource(ProcessInfoService processInfoService, ProcessInfoRepository processInfoRepository) {
        this.processInfoService = processInfoService;
        this.processInfoRepository = processInfoRepository;
    }

    /**
     * {@code POST  /process-infos} : Create a new processInfo.
     *
     * @param processInfoDTO the processInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInfoDTO, or with status {@code 400 (Bad Request)} if the processInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-infos")
    public ResponseEntity<ProcessInfoDTO> createProcessInfo(@RequestBody ProcessInfoDTO processInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessInfo : {}", processInfoDTO);
        if (processInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new processInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessInfoDTO result = processInfoService.save(processInfoDTO);
        return ResponseEntity
            .created(new URI("/api/process-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-infos/:id} : Updates an existing processInfo.
     *
     * @param id the id of the processInfoDTO to save.
     * @param processInfoDTO the processInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInfoDTO,
     * or with status {@code 400 (Bad Request)} if the processInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-infos/{id}")
    public ResponseEntity<ProcessInfoDTO> updateProcessInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessInfoDTO processInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessInfo : {}, {}", id, processInfoDTO);
        if (processInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessInfoDTO result = processInfoService.save(processInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /process-infos/:id} : Partial updates given fields of an existing processInfo, field will ignore if it is null
     *
     * @param id the id of the processInfoDTO to save.
     * @param processInfoDTO the processInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInfoDTO,
     * or with status {@code 400 (Bad Request)} if the processInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessInfoDTO> partialUpdateProcessInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessInfoDTO processInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessInfo partially : {}, {}", id, processInfoDTO);
        if (processInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessInfoDTO> result = processInfoService.partialUpdate(processInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /process-infos} : get all the processInfos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processInfos in body.
     */
    @GetMapping("/process-infos")
    public ResponseEntity<List<ProcessInfoDTO>> getAllProcessInfos(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ProcessInfos");
        Page<ProcessInfoDTO> page;
        if (eagerload) {
            page = processInfoService.findAllWithEagerRelationships(pageable);
        } else {
            page = processInfoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-infos/:id} : get the "id" processInfo.
     *
     * @param id the id of the processInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-infos/{id}")
    public ResponseEntity<ProcessInfoDTO> getProcessInfo(@PathVariable Long id) {
        log.debug("REST request to get ProcessInfo : {}", id);
        Optional<ProcessInfoDTO> processInfoDTO = processInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processInfoDTO);
    }

    /**
     * {@code DELETE  /process-infos/:id} : delete the "id" processInfo.
     *
     * @param id the id of the processInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-infos/{id}")
    public ResponseEntity<Void> deleteProcessInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProcessInfo : {}", id);
        processInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/process-infos?query=:query} : search for the processInfo corresponding
     * to the query.
     *
     * @param query the query of the processInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/process-infos")
    public ResponseEntity<List<ProcessInfoDTO>> searchProcessInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProcessInfos for query {}", query);
        Page<ProcessInfoDTO> page = processInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
