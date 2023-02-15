package com.vsm.business.web.rest;

import com.vsm.business.repository.DispatchBookRepository;
import com.vsm.business.service.DispatchBookService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DispatchBookDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DispatchBook}.
 */
//@RestController
@RequestMapping("/api")
public class DispatchBookResource {

    private final Logger log = LoggerFactory.getLogger(DispatchBookResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDispatchBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispatchBookService dispatchBookService;

    private final DispatchBookRepository dispatchBookRepository;

    public DispatchBookResource(DispatchBookService dispatchBookService, DispatchBookRepository dispatchBookRepository) {
        this.dispatchBookService = dispatchBookService;
        this.dispatchBookRepository = dispatchBookRepository;
    }

    /**
     * {@code POST  /dispatch-books} : Create a new dispatchBook.
     *
     * @param dispatchBookDTO the dispatchBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispatchBookDTO, or with status {@code 400 (Bad Request)} if the dispatchBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispatch-books")
    public ResponseEntity<DispatchBookDTO> createDispatchBook(@RequestBody DispatchBookDTO dispatchBookDTO) throws URISyntaxException {
        log.debug("REST request to save DispatchBook : {}", dispatchBookDTO);
        if (dispatchBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispatchBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispatchBookDTO result = dispatchBookService.save(dispatchBookDTO);
        return ResponseEntity
            .created(new URI("/api/dispatch-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispatch-books/:id} : Updates an existing dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to save.
     * @param dispatchBookDTO the dispatchBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispatch-books/{id}")
    public ResponseEntity<DispatchBookDTO> updateDispatchBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookDTO dispatchBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DispatchBook : {}, {}", id, dispatchBookDTO);
        if (dispatchBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DispatchBookDTO result = dispatchBookService.save(dispatchBookDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispatchBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dispatch-books/:id} : Partial updates given fields of an existing dispatchBook, field will ignore if it is null
     *
     * @param id the id of the dispatchBookDTO to save.
     * @param dispatchBookDTO the dispatchBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispatchBookDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dispatch-books/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispatchBookDTO> partialUpdateDispatchBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookDTO dispatchBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DispatchBook partially : {}, {}", id, dispatchBookDTO);
        if (dispatchBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispatchBookDTO> result = dispatchBookService.partialUpdate(dispatchBookDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispatchBookDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dispatch-books} : get all the dispatchBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispatchBooks in body.
     */
    @GetMapping("/dispatch-books")
    public ResponseEntity<List<DispatchBookDTO>> getAllDispatchBooks(Pageable pageable) {
        log.debug("REST request to get a page of DispatchBooks");
        Page<DispatchBookDTO> page = dispatchBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispatch-books/:id} : get the "id" dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispatchBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispatch-books/{id}")
    public ResponseEntity<DispatchBookDTO> getDispatchBook(@PathVariable Long id) {
        log.debug("REST request to get DispatchBook : {}", id);
        Optional<DispatchBookDTO> dispatchBookDTO = dispatchBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispatchBookDTO);
    }

    /**
     * {@code DELETE  /dispatch-books/:id} : delete the "id" dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispatch-books/{id}")
    public ResponseEntity<Void> deleteDispatchBook(@PathVariable Long id) {
        log.debug("REST request to delete DispatchBook : {}", id);
        dispatchBookService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/dispatch-books?query=:query} : search for the dispatchBook corresponding
     * to the query.
     *
     * @param query the query of the dispatchBook search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dispatch-books")
    public ResponseEntity<List<DispatchBookDTO>> searchDispatchBooks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DispatchBooks for query {}", query);
        Page<DispatchBookDTO> page = dispatchBookService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
