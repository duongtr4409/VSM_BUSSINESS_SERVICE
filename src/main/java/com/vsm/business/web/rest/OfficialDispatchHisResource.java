package com.vsm.business.web.rest;

import com.vsm.business.repository.OfficialDispatchHisRepository;
import com.vsm.business.service.OfficialDispatchHisService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatchHis}.
 */
//@RestController
@RequestMapping("/api")
public class OfficialDispatchHisResource {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchHisResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatchHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchHisService officialDispatchHisService;

    private final OfficialDispatchHisRepository officialDispatchHisRepository;

    public OfficialDispatchHisResource(
        OfficialDispatchHisService officialDispatchHisService,
        OfficialDispatchHisRepository officialDispatchHisRepository
    ) {
        this.officialDispatchHisService = officialDispatchHisService;
        this.officialDispatchHisRepository = officialDispatchHisRepository;
    }

    /**
     * {@code POST  /official-dispatch-his} : Create a new officialDispatchHis.
     *
     * @param officialDispatchHisDTO the officialDispatchHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchHisDTO, or with status {@code 400 (Bad Request)} if the officialDispatchHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatch-his")
    public ResponseEntity<OfficialDispatchHisDTO> createOfficialDispatchHis(@RequestBody OfficialDispatchHisDTO officialDispatchHisDTO)
        throws URISyntaxException {
        log.debug("REST request to save OfficialDispatchHis : {}", officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() != null) {
            throw new BadRequestAlertException("A new officialDispatchHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficialDispatchHisDTO result = officialDispatchHisService.save(officialDispatchHisDTO);
        return ResponseEntity
            .created(new URI("/api/official-dispatch-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /official-dispatch-his/:id} : Updates an existing officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to save.
     * @param officialDispatchHisDTO the officialDispatchHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchHisDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatch-his/{id}")
    public ResponseEntity<OfficialDispatchHisDTO> updateOfficialDispatchHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchHisDTO officialDispatchHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatchHis : {}, {}", id, officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfficialDispatchHisDTO result = officialDispatchHisService.save(officialDispatchHisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchHisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /official-dispatch-his/:id} : Partial updates given fields of an existing officialDispatchHis, field will ignore if it is null
     *
     * @param id the id of the officialDispatchHisDTO to save.
     * @param officialDispatchHisDTO the officialDispatchHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchHisDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatch-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfficialDispatchHisDTO> partialUpdateOfficialDispatchHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchHisDTO officialDispatchHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatchHis partially : {}, {}", id, officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchHisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchHisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfficialDispatchHisDTO> result = officialDispatchHisService.partialUpdate(officialDispatchHisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchHisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /official-dispatch-his} : get all the officialDispatchHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatchHis in body.
     */
    @GetMapping("/official-dispatch-his")
    public ResponseEntity<List<OfficialDispatchHisDTO>> getAllOfficialDispatchHis(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatchHis");
        Page<OfficialDispatchHisDTO> page = officialDispatchHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /official-dispatch-his/:id} : get the "id" officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatch-his/{id}")
    public ResponseEntity<OfficialDispatchHisDTO> getOfficialDispatchHis(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatchHis : {}", id);
        Optional<OfficialDispatchHisDTO> officialDispatchHisDTO = officialDispatchHisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officialDispatchHisDTO);
    }

    /**
     * {@code DELETE  /official-dispatch-his/:id} : delete the "id" officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatch-his/{id}")
    public ResponseEntity<Void> deleteOfficialDispatchHis(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatchHis : {}", id);
        officialDispatchHisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/official-dispatch-his?query=:query} : search for the officialDispatchHis corresponding
     * to the query.
     *
     * @param query the query of the officialDispatchHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatch-his")
    public ResponseEntity<List<OfficialDispatchHisDTO>> searchOfficialDispatchHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatchHis for query {}", query);
        Page<OfficialDispatchHisDTO> page = officialDispatchHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
