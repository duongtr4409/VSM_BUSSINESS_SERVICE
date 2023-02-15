package com.vsm.business.web.rest;

import com.vsm.business.repository.RoleOrganizationRepository;
import com.vsm.business.service.RoleOrganizationService;
import com.vsm.business.service.dto.RoleOrganizationDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.RoleOrganization}.
 */
//@RestController
@RequestMapping("/api")
public class RoleOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(RoleOrganizationResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRoleOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleOrganizationService roleOrganizationService;

    private final RoleOrganizationRepository roleOrganizationRepository;

    public RoleOrganizationResource(
        RoleOrganizationService roleOrganizationService,
        RoleOrganizationRepository roleOrganizationRepository
    ) {
        this.roleOrganizationService = roleOrganizationService;
        this.roleOrganizationRepository = roleOrganizationRepository;
    }

    /**
     * {@code POST  /role-organizations} : Create a new roleOrganization.
     *
     * @param roleOrganizationDTO the roleOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleOrganizationDTO, or with status {@code 400 (Bad Request)} if the roleOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-organizations")
    public ResponseEntity<RoleOrganizationDTO> createRoleOrganization(@RequestBody RoleOrganizationDTO roleOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save RoleOrganization : {}", roleOrganizationDTO);
        if (roleOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleOrganizationDTO result = roleOrganizationService.save(roleOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/role-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-organizations/:id} : Updates an existing roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to save.
     * @param roleOrganizationDTO the roleOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the roleOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-organizations/{id}")
    public ResponseEntity<RoleOrganizationDTO> updateRoleOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleOrganizationDTO roleOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleOrganization : {}, {}", id, roleOrganizationDTO);
        if (roleOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleOrganizationDTO result = roleOrganizationService.save(roleOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-organizations/:id} : Partial updates given fields of an existing roleOrganization, field will ignore if it is null
     *
     * @param id the id of the roleOrganizationDTO to save.
     * @param roleOrganizationDTO the roleOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the roleOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleOrganizationDTO> partialUpdateRoleOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleOrganizationDTO roleOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleOrganization partially : {}, {}", id, roleOrganizationDTO);
        if (roleOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleOrganizationDTO> result = roleOrganizationService.partialUpdate(roleOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-organizations} : get all the roleOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleOrganizations in body.
     */
    @GetMapping("/role-organizations")
    public ResponseEntity<List<RoleOrganizationDTO>> getAllRoleOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of RoleOrganizations");
        Page<RoleOrganizationDTO> page = roleOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-organizations/:id} : get the "id" roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-organizations/{id}")
    public ResponseEntity<RoleOrganizationDTO> getRoleOrganization(@PathVariable Long id) {
        log.debug("REST request to get RoleOrganization : {}", id);
        Optional<RoleOrganizationDTO> roleOrganizationDTO = roleOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleOrganizationDTO);
    }

    /**
     * {@code DELETE  /role-organizations/:id} : delete the "id" roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-organizations/{id}")
    public ResponseEntity<Void> deleteRoleOrganization(@PathVariable Long id) {
        log.debug("REST request to delete RoleOrganization : {}", id);
        roleOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/role-organizations?query=:query} : search for the roleOrganization corresponding
     * to the query.
     *
     * @param query the query of the roleOrganization search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/role-organizations")
    public ResponseEntity<List<RoleOrganizationDTO>> searchRoleOrganizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RoleOrganizations for query {}", query);
        Page<RoleOrganizationDTO> page = roleOrganizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
