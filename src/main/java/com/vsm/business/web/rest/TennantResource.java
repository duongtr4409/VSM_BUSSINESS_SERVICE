package com.vsm.business.web.rest;

import com.vsm.business.repository.TennantRepository;
import com.vsm.business.service.TennantService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.TennantDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.Tennant}.
 */
//@RestController
@RequestMapping("/api")
public class TennantResource {

    private final Logger log = LoggerFactory.getLogger(TennantResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTennant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TennantService tennantService;

    private final TennantRepository tennantRepository;

    public TennantResource(TennantService tennantService, TennantRepository tennantRepository) {
        this.tennantService = tennantService;
        this.tennantRepository = tennantRepository;
    }

    /**
     * {@code POST  /tennants} : Create a new tennant.
     *
     * @param tennantDTO the tennantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tennantDTO, or with status {@code 400 (Bad Request)} if the tennant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tennants")
    public ResponseEntity<TennantDTO> createTennant(@RequestBody TennantDTO tennantDTO) throws URISyntaxException {
        log.debug("REST request to save Tennant : {}", tennantDTO);
        if (tennantDTO.getId() != null) {
            throw new BadRequestAlertException("A new tennant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TennantDTO result = tennantService.save(tennantDTO);
        return ResponseEntity
            .created(new URI("/api/tennants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tennants/:id} : Updates an existing tennant.
     *
     * @param id the id of the tennantDTO to save.
     * @param tennantDTO the tennantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tennantDTO,
     * or with status {@code 400 (Bad Request)} if the tennantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tennantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tennants/{id}")
    public ResponseEntity<TennantDTO> updateTennant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TennantDTO tennantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tennant : {}, {}", id, tennantDTO);
        if (tennantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tennantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tennantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TennantDTO result = tennantService.save(tennantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tennantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tennants/:id} : Partial updates given fields of an existing tennant, field will ignore if it is null
     *
     * @param id the id of the tennantDTO to save.
     * @param tennantDTO the tennantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tennantDTO,
     * or with status {@code 400 (Bad Request)} if the tennantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tennantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tennantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tennants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TennantDTO> partialUpdateTennant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TennantDTO tennantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tennant partially : {}, {}", id, tennantDTO);
        if (tennantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tennantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tennantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TennantDTO> result = tennantService.partialUpdate(tennantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tennantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tennants} : get all the tennants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tennants in body.
     */
    @GetMapping("/tennants")
    public ResponseEntity<List<TennantDTO>> getAllTennants(Pageable pageable) {
        log.debug("REST request to get a page of Tennants");
        Page<TennantDTO> page = tennantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tennants/:id} : get the "id" tennant.
     *
     * @param id the id of the tennantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tennantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tennants/{id}")
    public ResponseEntity<TennantDTO> getTennant(@PathVariable Long id) {
        log.debug("REST request to get Tennant : {}", id);
        Optional<TennantDTO> tennantDTO = tennantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tennantDTO);
    }

    /**
     * {@code DELETE  /tennants/:id} : delete the "id" tennant.
     *
     * @param id the id of the tennantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tennants/{id}")
    public ResponseEntity<Void> deleteTennant(@PathVariable Long id) {
        log.debug("REST request to delete Tennant : {}", id);
        tennantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/tennants?query=:query} : search for the tennant corresponding
     * to the query.
     *
     * @param query the query of the tennant search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tennants")
    public ResponseEntity<List<TennantDTO>> searchTennants(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Tennants for query {}", query);
        Page<TennantDTO> page = tennantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
