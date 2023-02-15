package com.vsm.business.web.rest;

import com.vsm.business.repository.ThemeConfigRepository;
import com.vsm.business.service.ThemeConfigService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.ThemeConfigDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ThemeConfig}.
 */
//@RestController
@RequestMapping("/api")
public class ThemeConfigResource {

    private final Logger log = LoggerFactory.getLogger(ThemeConfigResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceThemeConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeConfigService themeConfigService;

    private final ThemeConfigRepository themeConfigRepository;

    public ThemeConfigResource(ThemeConfigService themeConfigService, ThemeConfigRepository themeConfigRepository) {
        this.themeConfigService = themeConfigService;
        this.themeConfigRepository = themeConfigRepository;
    }

    /**
     * {@code POST  /theme-configs} : Create a new themeConfig.
     *
     * @param themeConfigDTO the themeConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeConfigDTO, or with status {@code 400 (Bad Request)} if the themeConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-configs")
    public ResponseEntity<ThemeConfigDTO> createThemeConfig(@RequestBody ThemeConfigDTO themeConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeConfig : {}", themeConfigDTO);
        if (themeConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeConfigDTO result = themeConfigService.save(themeConfigDTO);
        return ResponseEntity
            .created(new URI("/api/theme-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-configs/:id} : Updates an existing themeConfig.
     *
     * @param id the id of the themeConfigDTO to save.
     * @param themeConfigDTO the themeConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeConfigDTO,
     * or with status {@code 400 (Bad Request)} if the themeConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-configs/{id}")
    public ResponseEntity<ThemeConfigDTO> updateThemeConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThemeConfigDTO themeConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThemeConfig : {}, {}", id, themeConfigDTO);
        if (themeConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, themeConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!themeConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThemeConfigDTO result = themeConfigService.save(themeConfigDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /theme-configs/:id} : Partial updates given fields of an existing themeConfig, field will ignore if it is null
     *
     * @param id the id of the themeConfigDTO to save.
     * @param themeConfigDTO the themeConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeConfigDTO,
     * or with status {@code 400 (Bad Request)} if the themeConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the themeConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the themeConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/theme-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThemeConfigDTO> partialUpdateThemeConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThemeConfigDTO themeConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThemeConfig partially : {}, {}", id, themeConfigDTO);
        if (themeConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, themeConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!themeConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThemeConfigDTO> result = themeConfigService.partialUpdate(themeConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /theme-configs} : get all the themeConfigs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeConfigs in body.
     */
    @GetMapping("/theme-configs")
    public ResponseEntity<List<ThemeConfigDTO>> getAllThemeConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ThemeConfigs");
        Page<ThemeConfigDTO> page = themeConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-configs/:id} : get the "id" themeConfig.
     *
     * @param id the id of the themeConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-configs/{id}")
    public ResponseEntity<ThemeConfigDTO> getThemeConfig(@PathVariable Long id) {
        log.debug("REST request to get ThemeConfig : {}", id);
        Optional<ThemeConfigDTO> themeConfigDTO = themeConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeConfigDTO);
    }

    /**
     * {@code DELETE  /theme-configs/:id} : delete the "id" themeConfig.
     *
     * @param id the id of the themeConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-configs/{id}")
    public ResponseEntity<Void> deleteThemeConfig(@PathVariable Long id) {
        log.debug("REST request to delete ThemeConfig : {}", id);
        themeConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/theme-configs?query=:query} : search for the themeConfig corresponding
     * to the query.
     *
     * @param query the query of the themeConfig search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/theme-configs")
    public ResponseEntity<List<ThemeConfigDTO>> searchThemeConfigs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ThemeConfigs for query {}", query);
        Page<ThemeConfigDTO> page = themeConfigService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
