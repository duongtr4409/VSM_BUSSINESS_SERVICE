package com.vsm.business.web.rest;

import com.vsm.business.repository.CurrencyUnitRepository;
import com.vsm.business.service.CurrencyUnitService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.CurrencyUnitDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.CurrencyUnit}.
 */
//@RestController
@RequestMapping("/api")
public class CurrencyUnitResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyUnitResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCurrencyUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyUnitService currencyUnitService;

    private final CurrencyUnitRepository currencyUnitRepository;

    public CurrencyUnitResource(CurrencyUnitService currencyUnitService, CurrencyUnitRepository currencyUnitRepository) {
        this.currencyUnitService = currencyUnitService;
        this.currencyUnitRepository = currencyUnitRepository;
    }

    /**
     * {@code POST  /currency-units} : Create a new currencyUnit.
     *
     * @param currencyUnitDTO the currencyUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyUnitDTO, or with status {@code 400 (Bad Request)} if the currencyUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currency-units")
    public ResponseEntity<CurrencyUnitDTO> createCurrencyUnit(@RequestBody CurrencyUnitDTO currencyUnitDTO) throws URISyntaxException {
        log.debug("REST request to save CurrencyUnit : {}", currencyUnitDTO);
        if (currencyUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new currencyUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyUnitDTO result = currencyUnitService.save(currencyUnitDTO);
        return ResponseEntity
            .created(new URI("/api/currency-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currency-units/:id} : Updates an existing currencyUnit.
     *
     * @param id the id of the currencyUnitDTO to save.
     * @param currencyUnitDTO the currencyUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyUnitDTO,
     * or with status {@code 400 (Bad Request)} if the currencyUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currency-units/{id}")
    public ResponseEntity<CurrencyUnitDTO> updateCurrencyUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CurrencyUnitDTO currencyUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CurrencyUnit : {}, {}", id, currencyUnitDTO);
        if (currencyUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencyUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencyUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CurrencyUnitDTO result = currencyUnitService.save(currencyUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, currencyUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /currency-units/:id} : Partial updates given fields of an existing currencyUnit, field will ignore if it is null
     *
     * @param id the id of the currencyUnitDTO to save.
     * @param currencyUnitDTO the currencyUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyUnitDTO,
     * or with status {@code 400 (Bad Request)} if the currencyUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currencyUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currencyUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/currency-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CurrencyUnitDTO> partialUpdateCurrencyUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CurrencyUnitDTO currencyUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CurrencyUnit partially : {}, {}", id, currencyUnitDTO);
        if (currencyUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencyUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencyUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrencyUnitDTO> result = currencyUnitService.partialUpdate(currencyUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, currencyUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /currency-units} : get all the currencyUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencyUnits in body.
     */
    @GetMapping("/currency-units")
    public ResponseEntity<List<CurrencyUnitDTO>> getAllCurrencyUnits(Pageable pageable) {
        log.debug("REST request to get a page of CurrencyUnits");
        Page<CurrencyUnitDTO> page = currencyUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /currency-units/:id} : get the "id" currencyUnit.
     *
     * @param id the id of the currencyUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currency-units/{id}")
    public ResponseEntity<CurrencyUnitDTO> getCurrencyUnit(@PathVariable Long id) {
        log.debug("REST request to get CurrencyUnit : {}", id);
        Optional<CurrencyUnitDTO> currencyUnitDTO = currencyUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyUnitDTO);
    }

    /**
     * {@code DELETE  /currency-units/:id} : delete the "id" currencyUnit.
     *
     * @param id the id of the currencyUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currency-units/{id}")
    public ResponseEntity<Void> deleteCurrencyUnit(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyUnit : {}", id);
        currencyUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/currency-units?query=:query} : search for the currencyUnit corresponding
     * to the query.
     *
     * @param query the query of the currencyUnit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/currency-units")
    public ResponseEntity<List<CurrencyUnitDTO>> searchCurrencyUnits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CurrencyUnits for query {}", query);
        Page<CurrencyUnitDTO> page = currencyUnitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
