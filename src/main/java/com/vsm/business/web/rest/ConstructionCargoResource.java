package com.vsm.business.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.service.ConstructionCargoService;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.ConstructionCargo}.
 */
//@RestController
@RequestMapping("/api")
public class ConstructionCargoResource {

    private final Logger log = LoggerFactory.getLogger(ConstructionCargoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConstructionCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConstructionCargoService constructionCargoService;

    private final ConstructionCargoRepository constructionCargoRepository;

    public ConstructionCargoResource(
        ConstructionCargoService constructionCargoService,
        ConstructionCargoRepository constructionCargoRepository
    ) {
        this.constructionCargoService = constructionCargoService;
        this.constructionCargoRepository = constructionCargoRepository;
    }

    /**
     * {@code POST  /construction-cargos} : Create a new constructionCargo.
     *
     * @param constructionCargoDTO the constructionCargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new constructionCargoDTO, or with status {@code 400 (Bad Request)} if the constructionCargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/construction-cargos")
    public ResponseEntity<ConstructionCargoDTO> createConstructionCargo(@RequestBody ConstructionCargoDTO constructionCargoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConstructionCargo : {}", constructionCargoDTO);
        if (constructionCargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new constructionCargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConstructionCargoDTO result = constructionCargoService.save(constructionCargoDTO);
        return ResponseEntity
            .created(new URI("/api/construction-cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /construction-cargos/:id} : Updates an existing constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to save.
     * @param constructionCargoDTO the constructionCargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constructionCargoDTO,
     * or with status {@code 400 (Bad Request)} if the constructionCargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the constructionCargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/construction-cargos/{id}")
    public ResponseEntity<ConstructionCargoDTO> updateConstructionCargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConstructionCargoDTO constructionCargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConstructionCargo : {}, {}", id, constructionCargoDTO);
        if (constructionCargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, constructionCargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!constructionCargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConstructionCargoDTO result = constructionCargoService.save(constructionCargoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, constructionCargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /construction-cargos/:id} : Partial updates given fields of an existing constructionCargo, field will ignore if it is null
     *
     * @param id the id of the constructionCargoDTO to save.
     * @param constructionCargoDTO the constructionCargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constructionCargoDTO,
     * or with status {@code 400 (Bad Request)} if the constructionCargoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the constructionCargoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the constructionCargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/construction-cargos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConstructionCargoDTO> partialUpdateConstructionCargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConstructionCargoDTO constructionCargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConstructionCargo partially : {}, {}", id, constructionCargoDTO);
        if (constructionCargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, constructionCargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!constructionCargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConstructionCargoDTO> result = constructionCargoService.partialUpdate(constructionCargoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, constructionCargoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /construction-cargos} : get all the constructionCargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of constructionCargos in body.
     */
    @GetMapping("/construction-cargos")
    public ResponseEntity<List<ConstructionCargoDTO>> getAllConstructionCargos(Pageable pageable) {
        log.debug("REST request to get a page of ConstructionCargos");
        Page<ConstructionCargoDTO> page = constructionCargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /construction-cargos/:id} : get the "id" constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the constructionCargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/construction-cargos/{id}")
    public ResponseEntity<ConstructionCargoDTO> getConstructionCargo(@PathVariable Long id) {
        log.debug("REST request to get ConstructionCargo : {}", id);
        Optional<ConstructionCargoDTO> constructionCargoDTO = constructionCargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(constructionCargoDTO);
    }

    /**
     * {@code DELETE  /construction-cargos/:id} : delete the "id" constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/construction-cargos/{id}")
    public ResponseEntity<Void> deleteConstructionCargo(@PathVariable Long id) {
        log.debug("REST request to delete ConstructionCargo : {}", id);
        constructionCargoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/construction-cargos?query=:query} : search for the constructionCargo corresponding
     * to the query.
     *
     * @param query the query of the constructionCargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/construction-cargos")
    public ResponseEntity<List<ConstructionCargoDTO>> searchConstructionCargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConstructionCargos for query {}", query);
        Page<ConstructionCargoDTO> page = constructionCargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
