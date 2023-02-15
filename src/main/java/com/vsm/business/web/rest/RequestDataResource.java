package com.vsm.business.web.rest;

import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.RequestDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.RequestDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.RequestData}.
 */
//@RestController
@RequestMapping("/api")
public class RequestDataResource {

    private final Logger log = LoggerFactory.getLogger(RequestDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestDataService requestDataService;

    private final RequestDataRepository requestDataRepository;

    public RequestDataResource(RequestDataService requestDataService, RequestDataRepository requestDataRepository) {
        this.requestDataService = requestDataService;
        this.requestDataRepository = requestDataRepository;
    }

    /**
     * {@code POST  /request-data} : Create a new requestData.
     *
     * @param requestDataDTO the requestDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDataDTO, or with status {@code 400 (Bad Request)} if the requestData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-data")
    public ResponseEntity<RequestDataDTO> createRequestData(@RequestBody RequestDataDTO requestDataDTO) throws URISyntaxException {
        log.debug("REST request to save RequestData : {}", requestDataDTO);
        if (requestDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestDataDTO result = requestDataService.save(requestDataDTO);
        return ResponseEntity
            .created(new URI("/api/request-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-data/:id} : Updates an existing requestData.
     *
     * @param id the id of the requestDataDTO to save.
     * @param requestDataDTO the requestDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDataDTO,
     * or with status {@code 400 (Bad Request)} if the requestDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-data/{id}")
    public ResponseEntity<RequestDataDTO> updateRequestData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestDataDTO requestDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestData : {}, {}", id, requestDataDTO);
        if (requestDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestDataDTO result = requestDataService.save(requestDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-data/:id} : Partial updates given fields of an existing requestData, field will ignore if it is null
     *
     * @param id the id of the requestDataDTO to save.
     * @param requestDataDTO the requestDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDataDTO,
     * or with status {@code 400 (Bad Request)} if the requestDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestDataDTO> partialUpdateRequestData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestDataDTO requestDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestData partially : {}, {}", id, requestDataDTO);
        if (requestDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestDataDTO> result = requestDataService.partialUpdate(requestDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-data} : get all the requestData.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestData in body.
     */
    @GetMapping("/request-data")
    public ResponseEntity<List<RequestDataDTO>> getAllRequestData(
        Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("managestampinfo-is-null".equals(filter)) {
            log.debug("REST request to get all RequestDatas where manageStampInfo is null");
            //return new ResponseEntity<>(requestDataService.findAllWhereManageStampInfoIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of RequestData");
        Page<RequestDataDTO> page;
        if (eagerload) {
            page = requestDataService.findAllWithEagerRelationships(pageable);
        } else {
            page = requestDataService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-data/:id} : get the "id" requestData.
     *
     * @param id the id of the requestDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-data/{id}")
    public ResponseEntity<RequestDataDTO> getRequestData(@PathVariable Long id) {
        log.debug("REST request to get RequestData : {}", id);
        Optional<RequestDataDTO> requestDataDTO = requestDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDataDTO);
    }

    /**
     * {@code DELETE  /request-data/:id} : delete the "id" requestData.
     *
     * @param id the id of the requestDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-data/{id}")
    public ResponseEntity<Void> deleteRequestData(@PathVariable Long id) {
        log.debug("REST request to delete RequestData : {}", id);
        requestDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/request-data?query=:query} : search for the requestData corresponding
     * to the query.
     *
     * @param query the query of the requestData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-data")
    public ResponseEntity<List<RequestDataDTO>> searchRequestData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestData for query {}", query);
        Page<RequestDataDTO> page = requestDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
