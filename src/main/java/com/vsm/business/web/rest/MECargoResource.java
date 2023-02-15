package com.vsm.business.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.repository.MECargoRepository;
import com.vsm.business.service.MECargoService;
import com.vsm.business.service.dto.MECargoDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.MECargo}.
 */
//@RestController
@RequestMapping("/api")
public class MECargoResource {

    private final Logger log = LoggerFactory.getLogger(MECargoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMeCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MECargoService mECargoService;

    private final MECargoRepository mECargoRepository;

    public MECargoResource(MECargoService mECargoService, MECargoRepository mECargoRepository) {
        this.mECargoService = mECargoService;
        this.mECargoRepository = mECargoRepository;
    }

    /**
     * {@code POST  /me-cargos} : Create a new mECargo.
     *
     * @param mECargoDTO the mECargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mECargoDTO, or with status {@code 400 (Bad Request)} if the mECargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/me-cargos")
    public ResponseEntity<MECargoDTO> createMECargo(@RequestBody MECargoDTO mECargoDTO) throws URISyntaxException {
        log.debug("REST request to save MECargo : {}", mECargoDTO);
        if (mECargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new mECargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MECargoDTO result = mECargoService.save(mECargoDTO);
        return ResponseEntity
            .created(new URI("/api/me-cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /me-cargos/:id} : Updates an existing mECargo.
     *
     * @param id the id of the mECargoDTO to save.
     * @param mECargoDTO the mECargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mECargoDTO,
     * or with status {@code 400 (Bad Request)} if the mECargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mECargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/me-cargos/{id}")
    public ResponseEntity<MECargoDTO> updateMECargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MECargoDTO mECargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MECargo : {}, {}", id, mECargoDTO);
        if (mECargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mECargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mECargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MECargoDTO result = mECargoService.save(mECargoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mECargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /me-cargos/:id} : Partial updates given fields of an existing mECargo, field will ignore if it is null
     *
     * @param id the id of the mECargoDTO to save.
     * @param mECargoDTO the mECargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mECargoDTO,
     * or with status {@code 400 (Bad Request)} if the mECargoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mECargoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mECargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/me-cargos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MECargoDTO> partialUpdateMECargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MECargoDTO mECargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MECargo partially : {}, {}", id, mECargoDTO);
        if (mECargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mECargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mECargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MECargoDTO> result = mECargoService.partialUpdate(mECargoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mECargoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /me-cargos} : get all the mECargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mECargos in body.
     */
    @GetMapping("/me-cargos")
    public ResponseEntity<List<MECargoDTO>> getAllMECargos(Pageable pageable) {
        log.debug("REST request to get a page of MECargos");
        Page<MECargoDTO> page = mECargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /me-cargos/:id} : get the "id" mECargo.
     *
     * @param id the id of the mECargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mECargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/me-cargos/{id}")
    public ResponseEntity<MECargoDTO> getMECargo(@PathVariable Long id) {
        log.debug("REST request to get MECargo : {}", id);
        Optional<MECargoDTO> mECargoDTO = mECargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mECargoDTO);
    }

    /**
     * {@code DELETE  /me-cargos/:id} : delete the "id" mECargo.
     *
     * @param id the id of the mECargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/me-cargos/{id}")
    public ResponseEntity<Void> deleteMECargo(@PathVariable Long id) {
        log.debug("REST request to delete MECargo : {}", id);
        mECargoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/me-cargos?query=:query} : search for the mECargo corresponding
     * to the query.
     *
     * @param query the query of the mECargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/me-cargos")
    public ResponseEntity<List<MECargoDTO>> searchMECargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MECargos for query {}", query);
        Page<MECargoDTO> page = mECargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
