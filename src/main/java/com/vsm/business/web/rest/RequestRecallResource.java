package com.vsm.business.web.rest;

import com.vsm.business.repository.RequestRecallRepository;
import com.vsm.business.service.RequestRecallService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.RequestRecallDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.RequestRecall}.
 */
//@RestController
@RequestMapping("/api")
public class RequestRecallResource {

    private final Logger log = LoggerFactory.getLogger(RequestRecallResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestRecall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestRecallService requestRecallService;

    private final RequestRecallRepository requestRecallRepository;

    public RequestRecallResource(RequestRecallService requestRecallService, RequestRecallRepository requestRecallRepository) {
        this.requestRecallService = requestRecallService;
        this.requestRecallRepository = requestRecallRepository;
    }

    /**
     * {@code POST  /request-recalls} : Create a new requestRecall.
     *
     * @param requestRecallDTO the requestRecallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestRecallDTO, or with status {@code 400 (Bad Request)} if the requestRecall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-recalls")
    public ResponseEntity<RequestRecallDTO> createRequestRecall(@RequestBody RequestRecallDTO requestRecallDTO) throws URISyntaxException {
        log.debug("REST request to save RequestRecall : {}", requestRecallDTO);
        if (requestRecallDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestRecall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestRecallDTO result = requestRecallService.save(requestRecallDTO);
        return ResponseEntity
            .created(new URI("/api/request-recalls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-recalls/:id} : Updates an existing requestRecall.
     *
     * @param id the id of the requestRecallDTO to save.
     * @param requestRecallDTO the requestRecallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestRecallDTO,
     * or with status {@code 400 (Bad Request)} if the requestRecallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestRecallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-recalls/{id}")
    public ResponseEntity<RequestRecallDTO> updateRequestRecall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestRecallDTO requestRecallDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestRecall : {}, {}", id, requestRecallDTO);
        if (requestRecallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestRecallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRecallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestRecallDTO result = requestRecallService.save(requestRecallDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestRecallDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-recalls/:id} : Partial updates given fields of an existing requestRecall, field will ignore if it is null
     *
     * @param id the id of the requestRecallDTO to save.
     * @param requestRecallDTO the requestRecallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestRecallDTO,
     * or with status {@code 400 (Bad Request)} if the requestRecallDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestRecallDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestRecallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-recalls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestRecallDTO> partialUpdateRequestRecall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestRecallDTO requestRecallDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestRecall partially : {}, {}", id, requestRecallDTO);
        if (requestRecallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestRecallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRecallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestRecallDTO> result = requestRecallService.partialUpdate(requestRecallDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestRecallDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-recalls} : get all the requestRecalls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestRecalls in body.
     */
    @GetMapping("/request-recalls")
    public ResponseEntity<List<RequestRecallDTO>> getAllRequestRecalls(Pageable pageable) {
        log.debug("REST request to get a page of RequestRecalls");
        Page<RequestRecallDTO> page = requestRecallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-recalls/:id} : get the "id" requestRecall.
     *
     * @param id the id of the requestRecallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestRecallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-recalls/{id}")
    public ResponseEntity<RequestRecallDTO> getRequestRecall(@PathVariable Long id) {
        log.debug("REST request to get RequestRecall : {}", id);
        Optional<RequestRecallDTO> requestRecallDTO = requestRecallService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestRecallDTO);
    }

    /**
     * {@code DELETE  /request-recalls/:id} : delete the "id" requestRecall.
     *
     * @param id the id of the requestRecallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-recalls/{id}")
    public ResponseEntity<Void> deleteRequestRecall(@PathVariable Long id) {
        log.debug("REST request to delete RequestRecall : {}", id);
        requestRecallService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/request-recalls?query=:query} : search for the requestRecall corresponding
     * to the query.
     *
     * @param query the query of the requestRecall search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-recalls")
    public ResponseEntity<List<RequestRecallDTO>> searchRequestRecalls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestRecalls for query {}", query);
        Page<RequestRecallDTO> page = requestRecallService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
