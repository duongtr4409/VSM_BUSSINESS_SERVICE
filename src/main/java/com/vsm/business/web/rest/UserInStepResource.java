package com.vsm.business.web.rest;

import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.service.UserInStepService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.UserInStepDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.UserInStep}.
 */
//@RestController
@RequestMapping("/api")
public class UserInStepResource {

    private final Logger log = LoggerFactory.getLogger(UserInStepResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceUserInStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInStepService userInStepService;

    private final UserInStepRepository userInStepRepository;

    public UserInStepResource(UserInStepService userInStepService, UserInStepRepository userInStepRepository) {
        this.userInStepService = userInStepService;
        this.userInStepRepository = userInStepRepository;
    }

    /**
     * {@code POST  /user-in-steps} : Create a new userInStep.
     *
     * @param userInStepDTO the userInStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInStepDTO, or with status {@code 400 (Bad Request)} if the userInStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-in-steps")
    public ResponseEntity<UserInStepDTO> createUserInStep(@RequestBody UserInStepDTO userInStepDTO) throws URISyntaxException {
        log.debug("REST request to save UserInStep : {}", userInStepDTO);
        if (userInStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInStepDTO result = userInStepService.save(userInStepDTO);
        return ResponseEntity
            .created(new URI("/api/user-in-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-in-steps/:id} : Updates an existing userInStep.
     *
     * @param id the id of the userInStepDTO to save.
     * @param userInStepDTO the userInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInStepDTO,
     * or with status {@code 400 (Bad Request)} if the userInStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-in-steps/{id}")
    public ResponseEntity<UserInStepDTO> updateUserInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserInStepDTO userInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserInStep : {}, {}", id, userInStepDTO);
        if (userInStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userInStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userInStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserInStepDTO result = userInStepService.save(userInStepDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userInStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-in-steps/:id} : Partial updates given fields of an existing userInStep, field will ignore if it is null
     *
     * @param id the id of the userInStepDTO to save.
     * @param userInStepDTO the userInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInStepDTO,
     * or with status {@code 400 (Bad Request)} if the userInStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userInStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-in-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserInStepDTO> partialUpdateUserInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserInStepDTO userInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserInStep partially : {}, {}", id, userInStepDTO);
        if (userInStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userInStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userInStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserInStepDTO> result = userInStepService.partialUpdate(userInStepDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userInStepDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-in-steps} : get all the userInSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInSteps in body.
     */
    @GetMapping("/user-in-steps")
    public ResponseEntity<List<UserInStepDTO>> getAllUserInSteps(Pageable pageable) {
        log.debug("REST request to get a page of UserInSteps");
        Page<UserInStepDTO> page = userInStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-in-steps/:id} : get the "id" userInStep.
     *
     * @param id the id of the userInStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-in-steps/{id}")
    public ResponseEntity<UserInStepDTO> getUserInStep(@PathVariable Long id) {
        log.debug("REST request to get UserInStep : {}", id);
        Optional<UserInStepDTO> userInStepDTO = userInStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInStepDTO);
    }

    /**
     * {@code DELETE  /user-in-steps/:id} : delete the "id" userInStep.
     *
     * @param id the id of the userInStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-in-steps/{id}")
    public ResponseEntity<Void> deleteUserInStep(@PathVariable Long id) {
        log.debug("REST request to delete UserInStep : {}", id);
        userInStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-in-steps?query=:query} : search for the userInStep corresponding
     * to the query.
     *
     * @param query the query of the userInStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-in-steps")
    public ResponseEntity<List<UserInStepDTO>> searchUserInSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserInSteps for query {}", query);
        Page<UserInStepDTO> page = userInStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
