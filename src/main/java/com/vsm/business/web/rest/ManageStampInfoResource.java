package com.vsm.business.web.rest;

import com.vsm.business.repository.ManageStampInfoRepository;
import com.vsm.business.service.ManageStampInfoService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ManageStampInfoDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ManageStampInfo}.
 */
//@RestController
@RequestMapping("/api")
public class ManageStampInfoResource {

    private final Logger log = LoggerFactory.getLogger(ManageStampInfoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceManageStampInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManageStampInfoService manageStampInfoService;

    private final ManageStampInfoRepository manageStampInfoRepository;

    public ManageStampInfoResource(ManageStampInfoService manageStampInfoService, ManageStampInfoRepository manageStampInfoRepository) {
        this.manageStampInfoService = manageStampInfoService;
        this.manageStampInfoRepository = manageStampInfoRepository;
    }

    /**
     * {@code POST  /manage-stamp-infos} : Create a new manageStampInfo.
     *
     * @param manageStampInfoDTO the manageStampInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manageStampInfoDTO, or with status {@code 400 (Bad Request)} if the manageStampInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/manage-stamp-infos")
    public ResponseEntity<ManageStampInfoDTO> createManageStampInfo(@RequestBody ManageStampInfoDTO manageStampInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ManageStampInfo : {}", manageStampInfoDTO);
        if (manageStampInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new manageStampInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManageStampInfoDTO result = manageStampInfoService.save(manageStampInfoDTO);
        return ResponseEntity
            .created(new URI("/api/manage-stamp-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /manage-stamp-infos/:id} : Updates an existing manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to save.
     * @param manageStampInfoDTO the manageStampInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manageStampInfoDTO,
     * or with status {@code 400 (Bad Request)} if the manageStampInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manageStampInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<ManageStampInfoDTO> updateManageStampInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManageStampInfoDTO manageStampInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ManageStampInfo : {}, {}", id, manageStampInfoDTO);
        if (manageStampInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manageStampInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manageStampInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManageStampInfoDTO result = manageStampInfoService.save(manageStampInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, manageStampInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /manage-stamp-infos/:id} : Partial updates given fields of an existing manageStampInfo, field will ignore if it is null
     *
     * @param id the id of the manageStampInfoDTO to save.
     * @param manageStampInfoDTO the manageStampInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manageStampInfoDTO,
     * or with status {@code 400 (Bad Request)} if the manageStampInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the manageStampInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the manageStampInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/manage-stamp-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManageStampInfoDTO> partialUpdateManageStampInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManageStampInfoDTO manageStampInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManageStampInfo partially : {}, {}", id, manageStampInfoDTO);
        if (manageStampInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manageStampInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manageStampInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManageStampInfoDTO> result = manageStampInfoService.partialUpdate(manageStampInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, manageStampInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /manage-stamp-infos} : get all the manageStampInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manageStampInfos in body.
     */
    @GetMapping("/manage-stamp-infos")
    public ResponseEntity<List<ManageStampInfoDTO>> getAllManageStampInfos(Pageable pageable) {
        log.debug("REST request to get a page of ManageStampInfos");
        Page<ManageStampInfoDTO> page = manageStampInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /manage-stamp-infos/:id} : get the "id" manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manageStampInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<ManageStampInfoDTO> getManageStampInfo(@PathVariable Long id) {
        log.debug("REST request to get ManageStampInfo : {}", id);
        Optional<ManageStampInfoDTO> manageStampInfoDTO = manageStampInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manageStampInfoDTO);
    }

    /**
     * {@code DELETE  /manage-stamp-infos/:id} : delete the "id" manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<Void> deleteManageStampInfo(@PathVariable Long id) {
        log.debug("REST request to delete ManageStampInfo : {}", id);
        manageStampInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/manage-stamp-infos?query=:query} : search for the manageStampInfo corresponding
     * to the query.
     *
     * @param query the query of the manageStampInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/manage-stamp-infos")
    public ResponseEntity<List<ManageStampInfoDTO>> searchManageStampInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ManageStampInfos for query {}", query);
        Page<ManageStampInfoDTO> page = manageStampInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
