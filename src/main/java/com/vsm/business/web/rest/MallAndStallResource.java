package com.vsm.business.web.rest;

import com.vsm.business.repository.MallAndStallRepository;
import com.vsm.business.service.MallAndStallService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.MallAndStallDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.MallAndStall}.
 */
//@RestController
@RequestMapping("/api")
public class MallAndStallResource {

    private final Logger log = LoggerFactory.getLogger(MallAndStallResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMallAndStall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MallAndStallService mallAndStallService;

    private final MallAndStallRepository mallAndStallRepository;

    public MallAndStallResource(MallAndStallService mallAndStallService, MallAndStallRepository mallAndStallRepository) {
        this.mallAndStallService = mallAndStallService;
        this.mallAndStallRepository = mallAndStallRepository;
    }

    /**
     * {@code POST  /mall-and-stalls} : Create a new mallAndStall.
     *
     * @param mallAndStallDTO the mallAndStallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mallAndStallDTO, or with status {@code 400 (Bad Request)} if the mallAndStall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mall-and-stalls")
    public ResponseEntity<MallAndStallDTO> createMallAndStall(@RequestBody MallAndStallDTO mallAndStallDTO) throws URISyntaxException {
        log.debug("REST request to save MallAndStall : {}", mallAndStallDTO);
        if (mallAndStallDTO.getId() != null) {
            throw new BadRequestAlertException("A new mallAndStall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MallAndStallDTO result = mallAndStallService.save(mallAndStallDTO);
        return ResponseEntity
            .created(new URI("/api/mall-and-stalls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mall-and-stalls/:id} : Updates an existing mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to save.
     * @param mallAndStallDTO the mallAndStallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallAndStallDTO,
     * or with status {@code 400 (Bad Request)} if the mallAndStallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mallAndStallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mall-and-stalls/{id}")
    public ResponseEntity<MallAndStallDTO> updateMallAndStall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MallAndStallDTO mallAndStallDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MallAndStall : {}, {}", id, mallAndStallDTO);
        if (mallAndStallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallAndStallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallAndStallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MallAndStallDTO result = mallAndStallService.save(mallAndStallDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mallAndStallDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mall-and-stalls/:id} : Partial updates given fields of an existing mallAndStall, field will ignore if it is null
     *
     * @param id the id of the mallAndStallDTO to save.
     * @param mallAndStallDTO the mallAndStallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallAndStallDTO,
     * or with status {@code 400 (Bad Request)} if the mallAndStallDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mallAndStallDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mallAndStallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mall-and-stalls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MallAndStallDTO> partialUpdateMallAndStall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MallAndStallDTO mallAndStallDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MallAndStall partially : {}, {}", id, mallAndStallDTO);
        if (mallAndStallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallAndStallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallAndStallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MallAndStallDTO> result = mallAndStallService.partialUpdate(mallAndStallDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mallAndStallDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mall-and-stalls} : get all the mallAndStalls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mallAndStalls in body.
     */
    @GetMapping("/mall-and-stalls")
    public ResponseEntity<List<MallAndStallDTO>> getAllMallAndStalls(Pageable pageable) {
        log.debug("REST request to get a page of MallAndStalls");
        Page<MallAndStallDTO> page = mallAndStallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mall-and-stalls/:id} : get the "id" mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mallAndStallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mall-and-stalls/{id}")
    public ResponseEntity<MallAndStallDTO> getMallAndStall(@PathVariable Long id) {
        log.debug("REST request to get MallAndStall : {}", id);
        Optional<MallAndStallDTO> mallAndStallDTO = mallAndStallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mallAndStallDTO);
    }

    /**
     * {@code DELETE  /mall-and-stalls/:id} : delete the "id" mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mall-and-stalls/{id}")
    public ResponseEntity<Void> deleteMallAndStall(@PathVariable Long id) {
        log.debug("REST request to delete MallAndStall : {}", id);
        mallAndStallService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/mall-and-stalls?query=:query} : search for the mallAndStall corresponding
     * to the query.
     *
     * @param query the query of the mallAndStall search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mall-and-stalls")
    public ResponseEntity<List<MallAndStallDTO>> searchMallAndStalls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MallAndStalls for query {}", query);
        Page<MallAndStallDTO> page = mallAndStallService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
