package com.vsm.business.web.rest;

import com.vsm.business.repository.FeatureRepository;
import com.vsm.business.service.FeatureService;
import com.vsm.business.service.dto.FeatureDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.Feature}.
 */
//@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeatureService featureService;

    private final FeatureRepository featureRepository;

    public FeatureResource(FeatureService featureService, FeatureRepository featureRepository) {
        this.featureService = featureService;
        this.featureRepository = featureRepository;
    }

    /**
     * {@code POST  /features} : Create a new feature.
     *
     * @param featureDTO the featureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featureDTO, or with status {@code 400 (Bad Request)} if the feature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<FeatureDTO> createFeature(@RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", featureDTO);
        if (featureDTO.getId() != null) {
            throw new BadRequestAlertException("A new feature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity
            .created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features/:id} : Updates an existing feature.
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> updateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feature : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, featureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /features/:id} : Partial updates given fields of an existing feature, field will ignore if it is null
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the featureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/features/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeatureDTO> partialUpdateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feature partially : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!featureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeatureDTO> result = featureService.partialUpdate(featureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, featureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /features} : get all the features.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public ResponseEntity<List<FeatureDTO>> getAllFeatures(Pageable pageable) {
        log.debug("REST request to get a page of Features");
        Page<FeatureDTO> page = featureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /features/:id} : get the "id" feature.
     *
     * @param id the id of the featureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Optional<FeatureDTO> featureDTO = featureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featureDTO);
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" feature.
     *
     * @param id the id of the featureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/features?query=:query} : search for the feature corresponding
     * to the query.
     *
     * @param query the query of the feature search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/features")
    public ResponseEntity<List<FeatureDTO>> searchFeatures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Features for query {}", query);
        Page<FeatureDTO> page = featureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
