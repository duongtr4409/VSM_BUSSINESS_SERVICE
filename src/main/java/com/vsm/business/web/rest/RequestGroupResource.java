package com.vsm.business.web.rest;

import com.vsm.business.repository.RequestGroupRepository;
import com.vsm.business.service.RequestGroupService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.RequestGroupDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.RequestGroup}.
 */
//@RestController
@RequestMapping("/api")
public class RequestGroupResource {

    private final Logger log = LoggerFactory.getLogger(RequestGroupResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestGroupService requestGroupService;

    private final RequestGroupRepository requestGroupRepository;

    public RequestGroupResource(RequestGroupService requestGroupService, RequestGroupRepository requestGroupRepository) {
        this.requestGroupService = requestGroupService;
        this.requestGroupRepository = requestGroupRepository;
    }

    /**
     * {@code POST  /request-groups} : Create a new requestGroup.
     *
     * @param requestGroupDTO the requestGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestGroupDTO, or with status {@code 400 (Bad Request)} if the requestGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-groups")
    public ResponseEntity<RequestGroupDTO> createRequestGroup(@RequestBody RequestGroupDTO requestGroupDTO) throws URISyntaxException {
        log.debug("REST request to save RequestGroup : {}", requestGroupDTO);
        if (requestGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestGroupDTO result = requestGroupService.save(requestGroupDTO);
        return ResponseEntity
            .created(new URI("/api/request-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-groups/:id} : Updates an existing requestGroup.
     *
     * @param id the id of the requestGroupDTO to save.
     * @param requestGroupDTO the requestGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestGroupDTO,
     * or with status {@code 400 (Bad Request)} if the requestGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-groups/{id}")
    public ResponseEntity<RequestGroupDTO> updateRequestGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestGroupDTO requestGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestGroup : {}, {}", id, requestGroupDTO);
        if (requestGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestGroupDTO result = requestGroupService.save(requestGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-groups/:id} : Partial updates given fields of an existing requestGroup, field will ignore if it is null
     *
     * @param id the id of the requestGroupDTO to save.
     * @param requestGroupDTO the requestGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestGroupDTO,
     * or with status {@code 400 (Bad Request)} if the requestGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestGroupDTO> partialUpdateRequestGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestGroupDTO requestGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestGroup partially : {}, {}", id, requestGroupDTO);
        if (requestGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestGroupDTO> result = requestGroupService.partialUpdate(requestGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-groups} : get all the requestGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestGroups in body.
     */
    @GetMapping("/request-groups")
    public ResponseEntity<List<RequestGroupDTO>> getAllRequestGroups(Pageable pageable) {
        log.debug("REST request to get a page of RequestGroups");
        Page<RequestGroupDTO> page = requestGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-groups/:id} : get the "id" requestGroup.
     *
     * @param id the id of the requestGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-groups/{id}")
    public ResponseEntity<RequestGroupDTO> getRequestGroup(@PathVariable Long id) {
        log.debug("REST request to get RequestGroup : {}", id);
        Optional<RequestGroupDTO> requestGroupDTO = requestGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestGroupDTO);
    }

    /**
     * {@code DELETE  /request-groups/:id} : delete the "id" requestGroup.
     *
     * @param id the id of the requestGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-groups/{id}")
    public ResponseEntity<Void> deleteRequestGroup(@PathVariable Long id) {
        log.debug("REST request to delete RequestGroup : {}", id);
        requestGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/request-groups?query=:query} : search for the requestGroup corresponding
     * to the query.
     *
     * @param query the query of the requestGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-groups")
    public ResponseEntity<List<RequestGroupDTO>> searchRequestGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestGroups for query {}", query);
        Page<RequestGroupDTO> page = requestGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
