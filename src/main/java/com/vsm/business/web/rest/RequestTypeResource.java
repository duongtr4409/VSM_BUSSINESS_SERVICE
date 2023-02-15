package com.vsm.business.web.rest;

import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.service.RequestTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.RequestTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.RequestType}.
 */
//@RestController
@RequestMapping("/api")
public class RequestTypeResource {

    private final Logger log = LoggerFactory.getLogger(RequestTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestTypeService requestTypeService;

    private final RequestTypeRepository requestTypeRepository;

    public RequestTypeResource(RequestTypeService requestTypeService, RequestTypeRepository requestTypeRepository) {
        this.requestTypeService = requestTypeService;
        this.requestTypeRepository = requestTypeRepository;
    }

    /**
     * {@code POST  /request-types} : Create a new requestType.
     *
     * @param requestTypeDTO the requestTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestTypeDTO, or with status {@code 400 (Bad Request)} if the requestType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-types")
    public ResponseEntity<RequestTypeDTO> createRequestType(@RequestBody RequestTypeDTO requestTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RequestType : {}", requestTypeDTO);
        if (requestTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestTypeDTO result = requestTypeService.save(requestTypeDTO);
        return ResponseEntity
            .created(new URI("/api/request-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-types/:id} : Updates an existing requestType.
     *
     * @param id the id of the requestTypeDTO to save.
     * @param requestTypeDTO the requestTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-types/{id}")
    public ResponseEntity<RequestTypeDTO> updateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestTypeDTO requestTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestType : {}, {}", id, requestTypeDTO);
        if (requestTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestTypeDTO result = requestTypeService.save(requestTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-types/:id} : Partial updates given fields of an existing requestType, field will ignore if it is null
     *
     * @param id the id of the requestTypeDTO to save.
     * @param requestTypeDTO the requestTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestTypeDTO> partialUpdateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestTypeDTO requestTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestType partially : {}, {}", id, requestTypeDTO);
        if (requestTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestTypeDTO> result = requestTypeService.partialUpdate(requestTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-types} : get all the requestTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestTypes in body.
     */
    @GetMapping("/request-types")
    public ResponseEntity<List<RequestTypeDTO>> getAllRequestTypes(Pageable pageable) {
        log.debug("REST request to get a page of RequestTypes");
        Page<RequestTypeDTO> page = requestTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-types/:id} : get the "id" requestType.
     *
     * @param id the id of the requestTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-types/{id}")
    public ResponseEntity<RequestTypeDTO> getRequestType(@PathVariable Long id) {
        log.debug("REST request to get RequestType : {}", id);
        Optional<RequestTypeDTO> requestTypeDTO = requestTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestTypeDTO);
    }

    /**
     * {@code DELETE  /request-types/:id} : delete the "id" requestType.
     *
     * @param id the id of the requestTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-types/{id}")
    public ResponseEntity<Void> deleteRequestType(@PathVariable Long id) {
        log.debug("REST request to delete RequestType : {}", id);
        requestTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/request-types?query=:query} : search for the requestType corresponding
     * to the query.
     *
     * @param query the query of the requestType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-types")
    public ResponseEntity<List<RequestTypeDTO>> searchRequestTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestTypes for query {}", query);
        Page<RequestTypeDTO> page = requestTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
