package com.vsm.business.web.rest;

import com.vsm.business.repository.RankInOrgRepository;
import com.vsm.business.service.RankInOrgService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.RankInOrgDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.RankInOrg}.
 */
//@RestController
@RequestMapping("/api")
public class RankInOrgResource {

    private final Logger log = LoggerFactory.getLogger(RankInOrgResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRankInOrg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RankInOrgService rankInOrgService;

    private final RankInOrgRepository rankInOrgRepository;

    public RankInOrgResource(RankInOrgService rankInOrgService, RankInOrgRepository rankInOrgRepository) {
        this.rankInOrgService = rankInOrgService;
        this.rankInOrgRepository = rankInOrgRepository;
    }

    /**
     * {@code POST  /rank-in-orgs} : Create a new rankInOrg.
     *
     * @param rankInOrgDTO the rankInOrgDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rankInOrgDTO, or with status {@code 400 (Bad Request)} if the rankInOrg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rank-in-orgs")
    public ResponseEntity<RankInOrgDTO> createRankInOrg(@RequestBody RankInOrgDTO rankInOrgDTO) throws URISyntaxException {
        log.debug("REST request to save RankInOrg : {}", rankInOrgDTO);
        if (rankInOrgDTO.getId() != null) {
            throw new BadRequestAlertException("A new rankInOrg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RankInOrgDTO result = rankInOrgService.save(rankInOrgDTO);
        return ResponseEntity
            .created(new URI("/api/rank-in-orgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rank-in-orgs/:id} : Updates an existing rankInOrg.
     *
     * @param id the id of the rankInOrgDTO to save.
     * @param rankInOrgDTO the rankInOrgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankInOrgDTO,
     * or with status {@code 400 (Bad Request)} if the rankInOrgDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rankInOrgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rank-in-orgs/{id}")
    public ResponseEntity<RankInOrgDTO> updateRankInOrg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RankInOrgDTO rankInOrgDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RankInOrg : {}, {}", id, rankInOrgDTO);
        if (rankInOrgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankInOrgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankInOrgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RankInOrgDTO result = rankInOrgService.save(rankInOrgDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rankInOrgDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rank-in-orgs/:id} : Partial updates given fields of an existing rankInOrg, field will ignore if it is null
     *
     * @param id the id of the rankInOrgDTO to save.
     * @param rankInOrgDTO the rankInOrgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankInOrgDTO,
     * or with status {@code 400 (Bad Request)} if the rankInOrgDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rankInOrgDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rankInOrgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rank-in-orgs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RankInOrgDTO> partialUpdateRankInOrg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RankInOrgDTO rankInOrgDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RankInOrg partially : {}, {}", id, rankInOrgDTO);
        if (rankInOrgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankInOrgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankInOrgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RankInOrgDTO> result = rankInOrgService.partialUpdate(rankInOrgDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rankInOrgDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rank-in-orgs} : get all the rankInOrgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rankInOrgs in body.
     */
    @GetMapping("/rank-in-orgs")
    public ResponseEntity<List<RankInOrgDTO>> getAllRankInOrgs(Pageable pageable) {
        log.debug("REST request to get a page of RankInOrgs");
        Page<RankInOrgDTO> page = rankInOrgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rank-in-orgs/:id} : get the "id" rankInOrg.
     *
     * @param id the id of the rankInOrgDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankInOrgDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rank-in-orgs/{id}")
    public ResponseEntity<RankInOrgDTO> getRankInOrg(@PathVariable Long id) {
        log.debug("REST request to get RankInOrg : {}", id);
        Optional<RankInOrgDTO> rankInOrgDTO = rankInOrgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rankInOrgDTO);
    }

    /**
     * {@code DELETE  /rank-in-orgs/:id} : delete the "id" rankInOrg.
     *
     * @param id the id of the rankInOrgDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rank-in-orgs/{id}")
    public ResponseEntity<Void> deleteRankInOrg(@PathVariable Long id) {
        log.debug("REST request to delete RankInOrg : {}", id);
        rankInOrgService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/rank-in-orgs?query=:query} : search for the rankInOrg corresponding
     * to the query.
     *
     * @param query the query of the rankInOrg search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/rank-in-orgs")
    public ResponseEntity<List<RankInOrgDTO>> searchRankInOrgs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RankInOrgs for query {}", query);
        Page<RankInOrgDTO> page = rankInOrgService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
