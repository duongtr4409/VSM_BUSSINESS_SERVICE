package com.vsm.business.web.rest;

import com.vsm.business.repository.ChangeFileHistoryRepository;
import com.vsm.business.service.ChangeFileHistoryService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ChangeFileHistoryDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ChangeFileHistory}.
 */
//@RestController
@RequestMapping("/api")
public class ChangeFileHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ChangeFileHistoryResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceChangeFileHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChangeFileHistoryService changeFileHistoryService;

    private final ChangeFileHistoryRepository changeFileHistoryRepository;

    public ChangeFileHistoryResource(
        ChangeFileHistoryService changeFileHistoryService,
        ChangeFileHistoryRepository changeFileHistoryRepository
    ) {
        this.changeFileHistoryService = changeFileHistoryService;
        this.changeFileHistoryRepository = changeFileHistoryRepository;
    }

    /**
     * {@code POST  /change-file-histories} : Create a new changeFileHistory.
     *
     * @param changeFileHistoryDTO the changeFileHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new changeFileHistoryDTO, or with status {@code 400 (Bad Request)} if the changeFileHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/change-file-histories")
    public ResponseEntity<ChangeFileHistoryDTO> createChangeFileHistory(@RequestBody ChangeFileHistoryDTO changeFileHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChangeFileHistory : {}", changeFileHistoryDTO);
        if (changeFileHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new changeFileHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChangeFileHistoryDTO result = changeFileHistoryService.save(changeFileHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/change-file-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /change-file-histories/:id} : Updates an existing changeFileHistory.
     *
     * @param id the id of the changeFileHistoryDTO to save.
     * @param changeFileHistoryDTO the changeFileHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeFileHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the changeFileHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the changeFileHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/change-file-histories/{id}")
    public ResponseEntity<ChangeFileHistoryDTO> updateChangeFileHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChangeFileHistoryDTO changeFileHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChangeFileHistory : {}, {}", id, changeFileHistoryDTO);
        if (changeFileHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeFileHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeFileHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChangeFileHistoryDTO result = changeFileHistoryService.save(changeFileHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, changeFileHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /change-file-histories/:id} : Partial updates given fields of an existing changeFileHistory, field will ignore if it is null
     *
     * @param id the id of the changeFileHistoryDTO to save.
     * @param changeFileHistoryDTO the changeFileHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeFileHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the changeFileHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the changeFileHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the changeFileHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/change-file-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChangeFileHistoryDTO> partialUpdateChangeFileHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChangeFileHistoryDTO changeFileHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChangeFileHistory partially : {}, {}", id, changeFileHistoryDTO);
        if (changeFileHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeFileHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeFileHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChangeFileHistoryDTO> result = changeFileHistoryService.partialUpdate(changeFileHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, changeFileHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /change-file-histories} : get all the changeFileHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of changeFileHistories in body.
     */
    @GetMapping("/change-file-histories")
    public ResponseEntity<List<ChangeFileHistoryDTO>> getAllChangeFileHistories(Pageable pageable) {
        log.debug("REST request to get a page of ChangeFileHistories");
        Page<ChangeFileHistoryDTO> page = changeFileHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /change-file-histories/:id} : get the "id" changeFileHistory.
     *
     * @param id the id of the changeFileHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the changeFileHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/change-file-histories/{id}")
    public ResponseEntity<ChangeFileHistoryDTO> getChangeFileHistory(@PathVariable Long id) {
        log.debug("REST request to get ChangeFileHistory : {}", id);
        Optional<ChangeFileHistoryDTO> changeFileHistoryDTO = changeFileHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(changeFileHistoryDTO);
    }

    /**
     * {@code DELETE  /change-file-histories/:id} : delete the "id" changeFileHistory.
     *
     * @param id the id of the changeFileHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/change-file-histories/{id}")
    public ResponseEntity<Void> deleteChangeFileHistory(@PathVariable Long id) {
        log.debug("REST request to delete ChangeFileHistory : {}", id);
        changeFileHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/change-file-histories?query=:query} : search for the changeFileHistory corresponding
     * to the query.
     *
     * @param query the query of the changeFileHistory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/change-file-histories")
    public ResponseEntity<List<ChangeFileHistoryDTO>> searchChangeFileHistories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChangeFileHistories for query {}", query);
        Page<ChangeFileHistoryDTO> page = changeFileHistoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
