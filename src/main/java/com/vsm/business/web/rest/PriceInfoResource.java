package com.vsm.business.web.rest;

import com.vsm.business.repository.PriceInfoRepository;
import com.vsm.business.service.PriceInfoService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.PriceInfoDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.PriceInfo}.
 */
//@RestController
@RequestMapping("/api")
public class PriceInfoResource {

    private final Logger log = LoggerFactory.getLogger(PriceInfoResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServicePriceInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceInfoService priceInfoService;

    private final PriceInfoRepository priceInfoRepository;

    public PriceInfoResource(PriceInfoService priceInfoService, PriceInfoRepository priceInfoRepository) {
        this.priceInfoService = priceInfoService;
        this.priceInfoRepository = priceInfoRepository;
    }

    /**
     * {@code POST  /price-infos} : Create a new priceInfo.
     *
     * @param priceInfoDTO the priceInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priceInfoDTO, or with status {@code 400 (Bad Request)} if the priceInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-infos")
    public ResponseEntity<PriceInfoDTO> createPriceInfo(@RequestBody PriceInfoDTO priceInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PriceInfo : {}", priceInfoDTO);
        if (priceInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceInfoDTO result = priceInfoService.save(priceInfoDTO);
        return ResponseEntity
            .created(new URI("/api/price-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-infos/:id} : Updates an existing priceInfo.
     *
     * @param id the id of the priceInfoDTO to save.
     * @param priceInfoDTO the priceInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceInfoDTO,
     * or with status {@code 400 (Bad Request)} if the priceInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priceInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-infos/{id}")
    public ResponseEntity<PriceInfoDTO> updatePriceInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriceInfoDTO priceInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PriceInfo : {}, {}", id, priceInfoDTO);
        if (priceInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priceInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priceInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PriceInfoDTO result = priceInfoService.save(priceInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priceInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /price-infos/:id} : Partial updates given fields of an existing priceInfo, field will ignore if it is null
     *
     * @param id the id of the priceInfoDTO to save.
     * @param priceInfoDTO the priceInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceInfoDTO,
     * or with status {@code 400 (Bad Request)} if the priceInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the priceInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the priceInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/price-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PriceInfoDTO> partialUpdatePriceInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriceInfoDTO priceInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PriceInfo partially : {}, {}", id, priceInfoDTO);
        if (priceInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priceInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priceInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PriceInfoDTO> result = priceInfoService.partialUpdate(priceInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priceInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /price-infos} : get all the priceInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceInfos in body.
     */
    @GetMapping("/price-infos")
    public ResponseEntity<List<PriceInfoDTO>> getAllPriceInfos(Pageable pageable) {
        log.debug("REST request to get a page of PriceInfos");
        Page<PriceInfoDTO> page = priceInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /price-infos/:id} : get the "id" priceInfo.
     *
     * @param id the id of the priceInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-infos/{id}")
    public ResponseEntity<PriceInfoDTO> getPriceInfo(@PathVariable Long id) {
        log.debug("REST request to get PriceInfo : {}", id);
        Optional<PriceInfoDTO> priceInfoDTO = priceInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceInfoDTO);
    }

    /**
     * {@code DELETE  /price-infos/:id} : delete the "id" priceInfo.
     *
     * @param id the id of the priceInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-infos/{id}")
    public ResponseEntity<Void> deletePriceInfo(@PathVariable Long id) {
        log.debug("REST request to delete PriceInfo : {}", id);
        priceInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/price-infos?query=:query} : search for the priceInfo corresponding
     * to the query.
     *
     * @param query the query of the priceInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/price-infos")
    public ResponseEntity<List<PriceInfoDTO>> searchPriceInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PriceInfos for query {}", query);
        Page<PriceInfoDTO> page = priceInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
