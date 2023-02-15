package com.vsm.business.web.rest;

import com.vsm.business.repository.DelegateInfoRepository;
import com.vsm.business.service.DelegateInfoService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DelegateInfoDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DelegateInfo}.
 */
//@RestController
@RequestMapping("/api")
public class DelegateInfoResource {

    private final Logger log = LoggerFactory.getLogger(DelegateInfoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDelegateInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegateInfoService delegateInfoService;

    private final DelegateInfoRepository delegateInfoRepository;

    public DelegateInfoResource(DelegateInfoService delegateInfoService, DelegateInfoRepository delegateInfoRepository) {
        this.delegateInfoService = delegateInfoService;
        this.delegateInfoRepository = delegateInfoRepository;
    }

    /**
     * {@code POST  /delegate-infos} : Create a new delegateInfo.
     *
     * @param delegateInfoDTO the delegateInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegateInfoDTO, or with status {@code 400 (Bad Request)} if the delegateInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegate-infos")
    public ResponseEntity<DelegateInfoDTO> createDelegateInfo(@RequestBody DelegateInfoDTO delegateInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DelegateInfo : {}", delegateInfoDTO);
        if (delegateInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new delegateInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DelegateInfoDTO result = delegateInfoService.save(delegateInfoDTO);
        return ResponseEntity
            .created(new URI("/api/delegate-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegate-infos/:id} : Updates an existing delegateInfo.
     *
     * @param id the id of the delegateInfoDTO to save.
     * @param delegateInfoDTO the delegateInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateInfoDTO,
     * or with status {@code 400 (Bad Request)} if the delegateInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegateInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegate-infos/{id}")
    public ResponseEntity<DelegateInfoDTO> updateDelegateInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateInfoDTO delegateInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DelegateInfo : {}, {}", id, delegateInfoDTO);
        if (delegateInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DelegateInfoDTO result = delegateInfoService.save(delegateInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delegate-infos/:id} : Partial updates given fields of an existing delegateInfo, field will ignore if it is null
     *
     * @param id the id of the delegateInfoDTO to save.
     * @param delegateInfoDTO the delegateInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegateInfoDTO,
     * or with status {@code 400 (Bad Request)} if the delegateInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the delegateInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the delegateInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delegate-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DelegateInfoDTO> partialUpdateDelegateInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DelegateInfoDTO delegateInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DelegateInfo partially : {}, {}", id, delegateInfoDTO);
        if (delegateInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delegateInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!delegateInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DelegateInfoDTO> result = delegateInfoService.partialUpdate(delegateInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, delegateInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delegate-infos} : get all the delegateInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegateInfos in body.
     */
    @GetMapping("/delegate-infos")
    public ResponseEntity<List<DelegateInfoDTO>> getAllDelegateInfos(Pageable pageable) {
        log.debug("REST request to get a page of DelegateInfos");
        Page<DelegateInfoDTO> page = delegateInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delegate-infos/:id} : get the "id" delegateInfo.
     *
     * @param id the id of the delegateInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegateInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegate-infos/{id}")
    public ResponseEntity<DelegateInfoDTO> getDelegateInfo(@PathVariable Long id) {
        log.debug("REST request to get DelegateInfo : {}", id);
        Optional<DelegateInfoDTO> delegateInfoDTO = delegateInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegateInfoDTO);
    }

    /**
     * {@code DELETE  /delegate-infos/:id} : delete the "id" delegateInfo.
     *
     * @param id the id of the delegateInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegate-infos/{id}")
    public ResponseEntity<Void> deleteDelegateInfo(@PathVariable Long id) {
        log.debug("REST request to delete DelegateInfo : {}", id);
        delegateInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/delegate-infos?query=:query} : search for the delegateInfo corresponding
     * to the query.
     *
     * @param query the query of the delegateInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/delegate-infos")
    public ResponseEntity<List<DelegateInfoDTO>> searchDelegateInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DelegateInfos for query {}", query);
        Page<DelegateInfoDTO> page = delegateInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
