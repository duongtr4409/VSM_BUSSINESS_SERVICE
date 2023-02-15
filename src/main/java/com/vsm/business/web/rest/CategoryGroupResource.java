package com.vsm.business.web.rest;

import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.service.CategoryGroupService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.CategoryGroupDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.CategoryGroup}.
 */
//@RestController
@RequestMapping("/api")
public class CategoryGroupResource {

    private final Logger log = LoggerFactory.getLogger(CategoryGroupResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCategoryGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryGroupService categoryGroupService;

    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroupResource(CategoryGroupService categoryGroupService, CategoryGroupRepository categoryGroupRepository) {
        this.categoryGroupService = categoryGroupService;
        this.categoryGroupRepository = categoryGroupRepository;
    }

    /**
     * {@code POST  /category-groups} : Create a new categoryGroup.
     *
     * @param categoryGroupDTO the categoryGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryGroupDTO, or with status {@code 400 (Bad Request)} if the categoryGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-groups")
    public ResponseEntity<CategoryGroupDTO> createCategoryGroup(@RequestBody CategoryGroupDTO categoryGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryGroup : {}", categoryGroupDTO);
        if (categoryGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryGroupDTO result = categoryGroupService.save(categoryGroupDTO);
        return ResponseEntity
            .created(new URI("/api/category-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-groups/:id} : Updates an existing categoryGroup.
     *
     * @param id the id of the categoryGroupDTO to save.
     * @param categoryGroupDTO the categoryGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryGroupDTO,
     * or with status {@code 400 (Bad Request)} if the categoryGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-groups/{id}")
    public ResponseEntity<CategoryGroupDTO> updateCategoryGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryGroupDTO categoryGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoryGroup : {}, {}", id, categoryGroupDTO);
        if (categoryGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryGroupDTO result = categoryGroupService.save(categoryGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /category-groups/:id} : Partial updates given fields of an existing categoryGroup, field will ignore if it is null
     *
     * @param id the id of the categoryGroupDTO to save.
     * @param categoryGroupDTO the categoryGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryGroupDTO,
     * or with status {@code 400 (Bad Request)} if the categoryGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoryGroupDTO> partialUpdateCategoryGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryGroupDTO categoryGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoryGroup partially : {}, {}", id, categoryGroupDTO);
        if (categoryGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryGroupDTO> result = categoryGroupService.partialUpdate(categoryGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /category-groups} : get all the categoryGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryGroups in body.
     */
    @GetMapping("/category-groups")
    public ResponseEntity<List<CategoryGroupDTO>> getAllCategoryGroups(Pageable pageable) {
        log.debug("REST request to get a page of CategoryGroups");
        Page<CategoryGroupDTO> page = categoryGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-groups/:id} : get the "id" categoryGroup.
     *
     * @param id the id of the categoryGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-groups/{id}")
    public ResponseEntity<CategoryGroupDTO> getCategoryGroup(@PathVariable Long id) {
        log.debug("REST request to get CategoryGroup : {}", id);
        Optional<CategoryGroupDTO> categoryGroupDTO = categoryGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryGroupDTO);
    }

    /**
     * {@code DELETE  /category-groups/:id} : delete the "id" categoryGroup.
     *
     * @param id the id of the categoryGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-groups/{id}")
    public ResponseEntity<Void> deleteCategoryGroup(@PathVariable Long id) {
        log.debug("REST request to delete CategoryGroup : {}", id);
        categoryGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/category-groups?query=:query} : search for the categoryGroup corresponding
     * to the query.
     *
     * @param query the query of the categoryGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/category-groups")
    public ResponseEntity<List<CategoryGroupDTO>> searchCategoryGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoryGroups for query {}", query);
        Page<CategoryGroupDTO> page = categoryGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
