package com.vsm.business.web.rest;

import com.vsm.business.repository.InformationInExchangeRepository;
import com.vsm.business.service.InformationInExchangeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.InformationInExchangeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.InformationInExchange}.
 */
//@RestController
@RequestMapping("/api")
public class InformationInExchangeResource {

    private final Logger log = LoggerFactory.getLogger(InformationInExchangeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceInformationInExchange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformationInExchangeService informationInExchangeService;

    private final InformationInExchangeRepository informationInExchangeRepository;

    public InformationInExchangeResource(
        InformationInExchangeService informationInExchangeService,
        InformationInExchangeRepository informationInExchangeRepository
    ) {
        this.informationInExchangeService = informationInExchangeService;
        this.informationInExchangeRepository = informationInExchangeRepository;
    }

    /**
     * {@code POST  /information-in-exchanges} : Create a new informationInExchange.
     *
     * @param informationInExchangeDTO the informationInExchangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informationInExchangeDTO, or with status {@code 400 (Bad Request)} if the informationInExchange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/information-in-exchanges")
    public ResponseEntity<InformationInExchangeDTO> createInformationInExchange(
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save InformationInExchange : {}", informationInExchangeDTO);
        if (informationInExchangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new informationInExchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformationInExchangeDTO result = informationInExchangeService.save(informationInExchangeDTO);
        return ResponseEntity
            .created(new URI("/api/information-in-exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /information-in-exchanges/:id} : Updates an existing informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to save.
     * @param informationInExchangeDTO the informationInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the informationInExchangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informationInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/information-in-exchanges/{id}")
    public ResponseEntity<InformationInExchangeDTO> updateInformationInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InformationInExchange : {}, {}", id, informationInExchangeDTO);
        if (informationInExchangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationInExchangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationInExchangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InformationInExchangeDTO result = informationInExchangeService.save(informationInExchangeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, informationInExchangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /information-in-exchanges/:id} : Partial updates given fields of an existing informationInExchange, field will ignore if it is null
     *
     * @param id the id of the informationInExchangeDTO to save.
     * @param informationInExchangeDTO the informationInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the informationInExchangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the informationInExchangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the informationInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/information-in-exchanges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InformationInExchangeDTO> partialUpdateInformationInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InformationInExchange partially : {}, {}", id, informationInExchangeDTO);
        if (informationInExchangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationInExchangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationInExchangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InformationInExchangeDTO> result = informationInExchangeService.partialUpdate(informationInExchangeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, informationInExchangeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /information-in-exchanges} : get all the informationInExchanges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informationInExchanges in body.
     */
    @GetMapping("/information-in-exchanges")
    public ResponseEntity<List<InformationInExchangeDTO>> getAllInformationInExchanges(Pageable pageable) {
        log.debug("REST request to get a page of InformationInExchanges");
        Page<InformationInExchangeDTO> page = informationInExchangeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /information-in-exchanges/:id} : get the "id" informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informationInExchangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/information-in-exchanges/{id}")
    public ResponseEntity<InformationInExchangeDTO> getInformationInExchange(@PathVariable Long id) {
        log.debug("REST request to get InformationInExchange : {}", id);
        Optional<InformationInExchangeDTO> informationInExchangeDTO = informationInExchangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informationInExchangeDTO);
    }

    /**
     * {@code DELETE  /information-in-exchanges/:id} : delete the "id" informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/information-in-exchanges/{id}")
    public ResponseEntity<Void> deleteInformationInExchange(@PathVariable Long id) {
        log.debug("REST request to delete InformationInExchange : {}", id);
        informationInExchangeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/information-in-exchanges?query=:query} : search for the informationInExchange corresponding
     * to the query.
     *
     * @param query the query of the informationInExchange search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/information-in-exchanges")
    public ResponseEntity<List<InformationInExchangeDTO>> searchInformationInExchanges(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InformationInExchanges for query {}", query);
        Page<InformationInExchangeDTO> page = informationInExchangeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
