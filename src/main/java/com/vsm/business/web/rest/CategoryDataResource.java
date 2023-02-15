package com.vsm.business.web.rest;

import com.vsm.business.repository.CategoryDataRepository;
import com.vsm.business.service.CategoryDataService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.CategoryDataDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.CategoryData}.
 */
//@RestController
@RequestMapping("/api")
public class CategoryDataResource {

    private final Logger log = LoggerFactory.getLogger(CategoryDataResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCategoryData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryDataService categoryDataService;

    private final CategoryDataRepository categoryDataRepository;

    public CategoryDataResource(CategoryDataService categoryDataService, CategoryDataRepository categoryDataRepository) {
        this.categoryDataService = categoryDataService;
        this.categoryDataRepository = categoryDataRepository;
    }

    /**
     * {@code POST  /category-data} : Create a new categoryData.
     *
     * @param categoryDataDTO the categoryDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDataDTO, or with status {@code 400 (Bad Request)} if the categoryData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-data")
    public ResponseEntity<CategoryDataDTO> createCategoryData(@RequestBody CategoryDataDTO categoryDataDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryData : {}", categoryDataDTO);
        if (categoryDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryDataDTO result = categoryDataService.save(categoryDataDTO);
        return ResponseEntity
            .created(new URI("/api/category-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-data/:id} : Updates an existing categoryData.
     *
     * @param id the id of the categoryDataDTO to save.
     * @param categoryDataDTO the categoryDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDataDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-data/{id}")
    public ResponseEntity<CategoryDataDTO> updateCategoryData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryDataDTO categoryDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoryData : {}, {}", id, categoryDataDTO);
        if (categoryDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryDataDTO result = categoryDataService.save(categoryDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /category-data/:id} : Partial updates given fields of an existing categoryData, field will ignore if it is null
     *
     * @param id the id of the categoryDataDTO to save.
     * @param categoryDataDTO the categoryDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDataDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoryDataDTO> partialUpdateCategoryData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryDataDTO categoryDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoryData partially : {}, {}", id, categoryDataDTO);
        if (categoryDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryDataDTO> result = categoryDataService.partialUpdate(categoryDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /category-data} : get all the categoryData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryData in body.
     */
    @GetMapping("/category-data")
    public ResponseEntity<List<CategoryDataDTO>> getAllCategoryData(Pageable pageable) {
        log.debug("REST request to get a page of CategoryData");
        Page<CategoryDataDTO> page = categoryDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-data/:id} : get the "id" categoryData.
     *
     * @param id the id of the categoryDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-data/{id}")
    public ResponseEntity<CategoryDataDTO> getCategoryData(@PathVariable Long id) {
        log.debug("REST request to get CategoryData : {}", id);
        Optional<CategoryDataDTO> categoryDataDTO = categoryDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDataDTO);
    }

    /**
     * {@code DELETE  /category-data/:id} : delete the "id" categoryData.
     *
     * @param id the id of the categoryDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-data/{id}")
    public ResponseEntity<Void> deleteCategoryData(@PathVariable Long id) {
        log.debug("REST request to delete CategoryData : {}", id);
        categoryDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/category-data?query=:query} : search for the categoryData corresponding
     * to the query.
     *
     * @param query the query of the categoryData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/category-data")
    public ResponseEntity<List<CategoryDataDTO>> searchCategoryData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoryData for query {}", query);
        Page<CategoryDataDTO> page = categoryDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
