package com.vsm.business.web.rest;

import com.vsm.business.repository.OfficialDispatchRepository;
import com.vsm.business.service.OfficialDispatchService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OfficialDispatchDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatch}.
 */
//@RestController
@RequestMapping("/api")
public class OfficialDispatchResource {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchService officialDispatchService;

    private final OfficialDispatchRepository officialDispatchRepository;

    public OfficialDispatchResource(
        OfficialDispatchService officialDispatchService,
        OfficialDispatchRepository officialDispatchRepository
    ) {
        this.officialDispatchService = officialDispatchService;
        this.officialDispatchRepository = officialDispatchRepository;
    }

    /**
     * {@code POST  /official-dispatches} : Create a new officialDispatch.
     *
     * @param officialDispatchDTO the officialDispatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchDTO, or with status {@code 400 (Bad Request)} if the officialDispatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatches")
    public ResponseEntity<OfficialDispatchDTO> createOfficialDispatch(@RequestBody OfficialDispatchDTO officialDispatchDTO)
        throws URISyntaxException {
        log.debug("REST request to save OfficialDispatch : {}", officialDispatchDTO);
        if (officialDispatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new officialDispatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficialDispatchDTO result = officialDispatchService.save(officialDispatchDTO);
        return ResponseEntity
            .created(new URI("/api/official-dispatches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /official-dispatches/:id} : Updates an existing officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to save.
     * @param officialDispatchDTO the officialDispatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatches/{id}")
    public ResponseEntity<OfficialDispatchDTO> updateOfficialDispatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchDTO officialDispatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatch : {}, {}", id, officialDispatchDTO);
        if (officialDispatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfficialDispatchDTO result = officialDispatchService.save(officialDispatchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /official-dispatches/:id} : Partial updates given fields of an existing officialDispatch, field will ignore if it is null
     *
     * @param id the id of the officialDispatchDTO to save.
     * @param officialDispatchDTO the officialDispatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfficialDispatchDTO> partialUpdateOfficialDispatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchDTO officialDispatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatch partially : {}, {}", id, officialDispatchDTO);
        if (officialDispatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfficialDispatchDTO> result = officialDispatchService.partialUpdate(officialDispatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /official-dispatches} : get all the officialDispatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatches in body.
     */
    @GetMapping("/official-dispatches")
    public ResponseEntity<List<OfficialDispatchDTO>> getAllOfficialDispatches(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatches");
        Page<OfficialDispatchDTO> page = officialDispatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /official-dispatches/:id} : get the "id" officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatches/{id}")
    public ResponseEntity<OfficialDispatchDTO> getOfficialDispatch(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatch : {}", id);
        Optional<OfficialDispatchDTO> officialDispatchDTO = officialDispatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officialDispatchDTO);
    }

    /**
     * {@code DELETE  /official-dispatches/:id} : delete the "id" officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatches/{id}")
    public ResponseEntity<Void> deleteOfficialDispatch(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatch : {}", id);
        officialDispatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/official-dispatches?query=:query} : search for the officialDispatch corresponding
     * to the query.
     *
     * @param query the query of the officialDispatch search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatches")
    public ResponseEntity<List<OfficialDispatchDTO>> searchOfficialDispatches(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatches for query {}", query);
        Page<OfficialDispatchDTO> page = officialDispatchService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
