package com.vsm.business.web.rest;

import com.vsm.business.repository.GoodServiceTypeRepository;
import com.vsm.business.service.GoodServiceTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.GoodServiceTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.GoodServiceType}.
 */
//@RestController
@RequestMapping("/api")
public class GoodServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(GoodServiceTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceGoodServiceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodServiceTypeService goodServiceTypeService;

    private final GoodServiceTypeRepository goodServiceTypeRepository;

    public GoodServiceTypeResource(GoodServiceTypeService goodServiceTypeService, GoodServiceTypeRepository goodServiceTypeRepository) {
        this.goodServiceTypeService = goodServiceTypeService;
        this.goodServiceTypeRepository = goodServiceTypeRepository;
    }

    /**
     * {@code POST  /good-service-types} : Create a new goodServiceType.
     *
     * @param goodServiceTypeDTO the goodServiceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodServiceTypeDTO, or with status {@code 400 (Bad Request)} if the goodServiceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/good-service-types")
    public ResponseEntity<GoodServiceTypeDTO> createGoodServiceType(@RequestBody GoodServiceTypeDTO goodServiceTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save GoodServiceType : {}", goodServiceTypeDTO);
        if (goodServiceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodServiceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodServiceTypeDTO result = goodServiceTypeService.save(goodServiceTypeDTO);
        return ResponseEntity
            .created(new URI("/api/good-service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /good-service-types/:id} : Updates an existing goodServiceType.
     *
     * @param id the id of the goodServiceTypeDTO to save.
     * @param goodServiceTypeDTO the goodServiceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodServiceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the goodServiceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodServiceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/good-service-types/{id}")
    public ResponseEntity<GoodServiceTypeDTO> updateGoodServiceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodServiceTypeDTO goodServiceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoodServiceType : {}, {}", id, goodServiceTypeDTO);
        if (goodServiceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodServiceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodServiceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoodServiceTypeDTO result = goodServiceTypeService.save(goodServiceTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goodServiceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /good-service-types/:id} : Partial updates given fields of an existing goodServiceType, field will ignore if it is null
     *
     * @param id the id of the goodServiceTypeDTO to save.
     * @param goodServiceTypeDTO the goodServiceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodServiceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the goodServiceTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goodServiceTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goodServiceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/good-service-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GoodServiceTypeDTO> partialUpdateGoodServiceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodServiceTypeDTO goodServiceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoodServiceType partially : {}, {}", id, goodServiceTypeDTO);
        if (goodServiceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodServiceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodServiceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoodServiceTypeDTO> result = goodServiceTypeService.partialUpdate(goodServiceTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goodServiceTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /good-service-types} : get all the goodServiceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodServiceTypes in body.
     */
    @GetMapping("/good-service-types")
    public ResponseEntity<List<GoodServiceTypeDTO>> getAllGoodServiceTypes(Pageable pageable) {
        log.debug("REST request to get a page of GoodServiceTypes");
        Page<GoodServiceTypeDTO> page = goodServiceTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /good-service-types/:id} : get the "id" goodServiceType.
     *
     * @param id the id of the goodServiceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodServiceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/good-service-types/{id}")
    public ResponseEntity<GoodServiceTypeDTO> getGoodServiceType(@PathVariable Long id) {
        log.debug("REST request to get GoodServiceType : {}", id);
        Optional<GoodServiceTypeDTO> goodServiceTypeDTO = goodServiceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodServiceTypeDTO);
    }

    /**
     * {@code DELETE  /good-service-types/:id} : delete the "id" goodServiceType.
     *
     * @param id the id of the goodServiceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/good-service-types/{id}")
    public ResponseEntity<Void> deleteGoodServiceType(@PathVariable Long id) {
        log.debug("REST request to delete GoodServiceType : {}", id);
        goodServiceTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/good-service-types?query=:query} : search for the goodServiceType corresponding
     * to the query.
     *
     * @param query the query of the goodServiceType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/good-service-types")
    public ResponseEntity<List<GoodServiceTypeDTO>> searchGoodServiceTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GoodServiceTypes for query {}", query);
        Page<GoodServiceTypeDTO> page = goodServiceTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
