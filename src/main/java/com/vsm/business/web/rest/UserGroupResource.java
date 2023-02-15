package com.vsm.business.web.rest;

import com.vsm.business.repository.UserGroupRepository;
import com.vsm.business.service.UserGroupService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.UserGroupDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.UserGroup}.
 */
//@RestController
@RequestMapping("/api")
public class UserGroupResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupService userGroupService;

    private final UserGroupRepository userGroupRepository;

    public UserGroupResource(UserGroupService userGroupService, UserGroupRepository userGroupRepository) {
        this.userGroupService = userGroupService;
        this.userGroupRepository = userGroupRepository;
    }

    /**
     * {@code POST  /user-groups} : Create a new userGroup.
     *
     * @param userGroupDTO the userGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupDTO, or with status {@code 400 (Bad Request)} if the userGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-groups")
    public ResponseEntity<UserGroupDTO> createUserGroup(@RequestBody UserGroupDTO userGroupDTO) throws URISyntaxException {
        log.debug("REST request to save UserGroup : {}", userGroupDTO);
        if (userGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupDTO result = userGroupService.save(userGroupDTO);
        return ResponseEntity
            .created(new URI("/api/user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-groups/:id} : Updates an existing userGroup.
     *
     * @param id the id of the userGroupDTO to save.
     * @param userGroupDTO the userGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-groups/{id}")
    public ResponseEntity<UserGroupDTO> updateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGroupDTO userGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserGroup : {}, {}", id, userGroupDTO);
        if (userGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserGroupDTO result = userGroupService.save(userGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-groups/:id} : Partial updates given fields of an existing userGroup, field will ignore if it is null
     *
     * @param id the id of the userGroupDTO to save.
     * @param userGroupDTO the userGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserGroupDTO> partialUpdateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGroupDTO userGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserGroup partially : {}, {}", id, userGroupDTO);
        if (userGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGroupDTO> result = userGroupService.partialUpdate(userGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-groups} : get all the userGroups.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroups in body.
     */
    @GetMapping("/user-groups")
    public ResponseEntity<List<UserGroupDTO>> getAllUserGroups(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserGroups");
        Page<UserGroupDTO> page;
        if (eagerload) {
            page = userGroupService.findAllWithEagerRelationships(pageable);
        } else {
            page = userGroupService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-groups/:id} : get the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-groups/{id}")
    public ResponseEntity<UserGroupDTO> getUserGroup(@PathVariable Long id) {
        log.debug("REST request to get UserGroup : {}", id);
        Optional<UserGroupDTO> userGroupDTO = userGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupDTO);
    }

    /**
     * {@code DELETE  /user-groups/:id} : delete the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-groups/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete UserGroup : {}", id);
        userGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-groups?query=:query} : search for the userGroup corresponding
     * to the query.
     *
     * @param query the query of the userGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-groups")
    public ResponseEntity<List<UserGroupDTO>> searchUserGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserGroups for query {}", query);
        Page<UserGroupDTO> page = userGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
