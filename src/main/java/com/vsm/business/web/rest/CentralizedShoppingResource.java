package com.vsm.business.web.rest;

import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.service.CentralizedShoppingService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.CentralizedShoppingDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.CentralizedShopping}.
 */
//@RestController
@RequestMapping("/api")
public class CentralizedShoppingResource {

    private final Logger log = LoggerFactory.getLogger(CentralizedShoppingResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCentralizedShopping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentralizedShoppingService centralizedShoppingService;

    private final CentralizedShoppingRepository centralizedShoppingRepository;

    public CentralizedShoppingResource(
        CentralizedShoppingService centralizedShoppingService,
        CentralizedShoppingRepository centralizedShoppingRepository
    ) {
        this.centralizedShoppingService = centralizedShoppingService;
        this.centralizedShoppingRepository = centralizedShoppingRepository;
    }

    /**
     * {@code POST  /centralized-shoppings} : Create a new centralizedShopping.
     *
     * @param centralizedShoppingDTO the centralizedShoppingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centralizedShoppingDTO, or with status {@code 400 (Bad Request)} if the centralizedShopping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centralized-shoppings")
    public ResponseEntity<CentralizedShoppingDTO> createCentralizedShopping(@RequestBody CentralizedShoppingDTO centralizedShoppingDTO)
        throws URISyntaxException {
        log.debug("REST request to save CentralizedShopping : {}", centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() != null) {
            throw new BadRequestAlertException("A new centralizedShopping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentralizedShoppingDTO result = centralizedShoppingService.save(centralizedShoppingDTO);
        return ResponseEntity
            .created(new URI("/api/centralized-shoppings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centralized-shoppings/:id} : Updates an existing centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to save.
     * @param centralizedShoppingDTO the centralizedShoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralizedShoppingDTO,
     * or with status {@code 400 (Bad Request)} if the centralizedShoppingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centralizedShoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centralized-shoppings/{id}")
    public ResponseEntity<CentralizedShoppingDTO> updateCentralizedShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentralizedShoppingDTO centralizedShoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CentralizedShopping : {}, {}", id, centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralizedShoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralizedShoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CentralizedShoppingDTO result = centralizedShoppingService.save(centralizedShoppingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centralizedShoppingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /centralized-shoppings/:id} : Partial updates given fields of an existing centralizedShopping, field will ignore if it is null
     *
     * @param id the id of the centralizedShoppingDTO to save.
     * @param centralizedShoppingDTO the centralizedShoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralizedShoppingDTO,
     * or with status {@code 400 (Bad Request)} if the centralizedShoppingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centralizedShoppingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centralizedShoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/centralized-shoppings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentralizedShoppingDTO> partialUpdateCentralizedShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentralizedShoppingDTO centralizedShoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentralizedShopping partially : {}, {}", id, centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralizedShoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralizedShoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentralizedShoppingDTO> result = centralizedShoppingService.partialUpdate(centralizedShoppingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centralizedShoppingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /centralized-shoppings} : get all the centralizedShoppings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centralizedShoppings in body.
     */
    @GetMapping("/centralized-shoppings")
    public ResponseEntity<List<CentralizedShoppingDTO>> getAllCentralizedShoppings(Pageable pageable) {
        log.debug("REST request to get a page of CentralizedShoppings");
        Page<CentralizedShoppingDTO> page = centralizedShoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centralized-shoppings/:id} : get the "id" centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centralizedShoppingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centralized-shoppings/{id}")
    public ResponseEntity<CentralizedShoppingDTO> getCentralizedShopping(@PathVariable Long id) {
        log.debug("REST request to get CentralizedShopping : {}", id);
        Optional<CentralizedShoppingDTO> centralizedShoppingDTO = centralizedShoppingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centralizedShoppingDTO);
    }

    /**
     * {@code DELETE  /centralized-shoppings/:id} : delete the "id" centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centralized-shoppings/{id}")
    public ResponseEntity<Void> deleteCentralizedShopping(@PathVariable Long id) {
        log.debug("REST request to delete CentralizedShopping : {}", id);
        centralizedShoppingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/centralized-shoppings?query=:query} : search for the centralizedShopping corresponding
     * to the query.
     *
     * @param query the query of the centralizedShopping search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/centralized-shoppings")
    public ResponseEntity<List<CentralizedShoppingDTO>> searchCentralizedShoppings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CentralizedShoppings for query {}", query);
        Page<CentralizedShoppingDTO> page = centralizedShoppingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
