package com.vsm.business.web.rest;

import com.vsm.business.repository.RoleRequestRepository;
import com.vsm.business.service.RoleRequestService;
import com.vsm.business.service.dto.RoleRequestDTO;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.vsm.business.domain.RoleRequest}.
 */
//@RestController
@RequestMapping("/api")
public class RoleRequestResource {

    private final Logger log = LoggerFactory.getLogger(RoleRequestResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRoleRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleRequestService roleRequestService;

    private final RoleRequestRepository roleRequestRepository;

    public RoleRequestResource(RoleRequestService roleRequestService, RoleRequestRepository roleRequestRepository) {
        this.roleRequestService = roleRequestService;
        this.roleRequestRepository = roleRequestRepository;
    }

    /**
     * {@code POST  /role-requests} : Create a new roleRequest.
     *
     * @param roleRequestDTO the roleRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleRequestDTO, or with status {@code 400 (Bad Request)} if the roleRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-requests")
    public ResponseEntity<RoleRequestDTO> createRoleRequest(@RequestBody RoleRequestDTO roleRequestDTO) throws URISyntaxException {
        log.debug("REST request to save RoleRequest : {}", roleRequestDTO);
        if (roleRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleRequestDTO result = roleRequestService.save(roleRequestDTO);
        return ResponseEntity
            .created(new URI("/api/role-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-requests/:id} : Updates an existing roleRequest.
     *
     * @param id the id of the roleRequestDTO to save.
     * @param roleRequestDTO the roleRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleRequestDTO,
     * or with status {@code 400 (Bad Request)} if the roleRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-requests/{id}")
    public ResponseEntity<RoleRequestDTO> updateRoleRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleRequestDTO roleRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleRequest : {}, {}", id, roleRequestDTO);
        if (roleRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleRequestDTO result = roleRequestService.save(roleRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-requests/:id} : Partial updates given fields of an existing roleRequest, field will ignore if it is null
     *
     * @param id the id of the roleRequestDTO to save.
     * @param roleRequestDTO the roleRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleRequestDTO,
     * or with status {@code 400 (Bad Request)} if the roleRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleRequestDTO> partialUpdateRoleRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleRequestDTO roleRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleRequest partially : {}, {}", id, roleRequestDTO);
        if (roleRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleRequestDTO> result = roleRequestService.partialUpdate(roleRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-requests} : get all the roleRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleRequests in body.
     */
    @GetMapping("/role-requests")
    public ResponseEntity<List<RoleRequestDTO>> getAllRoleRequests(Pageable pageable) {
        log.debug("REST request to get a page of RoleRequests");
        Page<RoleRequestDTO> page = roleRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-requests/:id} : get the "id" roleRequest.
     *
     * @param id the id of the roleRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-requests/{id}")
    public ResponseEntity<RoleRequestDTO> getRoleRequest(@PathVariable Long id) {
        log.debug("REST request to get RoleRequest : {}", id);
        Optional<RoleRequestDTO> roleRequestDTO = roleRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleRequestDTO);
    }

    /**
     * {@code DELETE  /role-requests/:id} : delete the "id" roleRequest.
     *
     * @param id the id of the roleRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-requests/{id}")
    public ResponseEntity<Void> deleteRoleRequest(@PathVariable Long id) {
        log.debug("REST request to delete RoleRequest : {}", id);
        roleRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/role-requests?query=:query} : search for the roleRequest corresponding
     * to the query.
     *
     * @param query the query of the roleRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/role-requests")
    public ResponseEntity<List<RoleRequestDTO>> searchRoleRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RoleRequests for query {}", query);
        Page<RoleRequestDTO> page = roleRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
