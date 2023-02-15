package com.vsm.business.web.rest;

import com.vsm.business.repository.GoodServiceRepository;
import com.vsm.business.service.GoodServiceService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.GoodServiceDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.GoodService}.
 */
//@RestController
@RequestMapping("/api")
public class GoodServiceResource {

    private final Logger log = LoggerFactory.getLogger(GoodServiceResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceGoodService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodServiceService goodServiceService;

    private final GoodServiceRepository goodServiceRepository;

    public GoodServiceResource(GoodServiceService goodServiceService, GoodServiceRepository goodServiceRepository) {
        this.goodServiceService = goodServiceService;
        this.goodServiceRepository = goodServiceRepository;
    }

    /**
     * {@code POST  /good-services} : Create a new goodService.
     *
     * @param goodServiceDTO the goodServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodServiceDTO, or with status {@code 400 (Bad Request)} if the goodService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/good-services")
    public ResponseEntity<GoodServiceDTO> createGoodService(@RequestBody GoodServiceDTO goodServiceDTO) throws URISyntaxException {
        log.debug("REST request to save GoodService : {}", goodServiceDTO);
        if (goodServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodServiceDTO result = goodServiceService.save(goodServiceDTO);
        return ResponseEntity
            .created(new URI("/api/good-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /good-services/:id} : Updates an existing goodService.
     *
     * @param id the id of the goodServiceDTO to save.
     * @param goodServiceDTO the goodServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodServiceDTO,
     * or with status {@code 400 (Bad Request)} if the goodServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/good-services/{id}")
    public ResponseEntity<GoodServiceDTO> updateGoodService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodServiceDTO goodServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoodService : {}, {}", id, goodServiceDTO);
        if (goodServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoodServiceDTO result = goodServiceService.save(goodServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goodServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /good-services/:id} : Partial updates given fields of an existing goodService, field will ignore if it is null
     *
     * @param id the id of the goodServiceDTO to save.
     * @param goodServiceDTO the goodServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodServiceDTO,
     * or with status {@code 400 (Bad Request)} if the goodServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goodServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goodServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/good-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GoodServiceDTO> partialUpdateGoodService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodServiceDTO goodServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoodService partially : {}, {}", id, goodServiceDTO);
        if (goodServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoodServiceDTO> result = goodServiceService.partialUpdate(goodServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goodServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /good-services} : get all the goodServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodServices in body.
     */
    @GetMapping("/good-services")
    public ResponseEntity<List<GoodServiceDTO>> getAllGoodServices(Pageable pageable) {
        log.debug("REST request to get a page of GoodServices");
        Page<GoodServiceDTO> page = goodServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /good-services/:id} : get the "id" goodService.
     *
     * @param id the id of the goodServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/good-services/{id}")
    public ResponseEntity<GoodServiceDTO> getGoodService(@PathVariable Long id) {
        log.debug("REST request to get GoodService : {}", id);
        Optional<GoodServiceDTO> goodServiceDTO = goodServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodServiceDTO);
    }

    /**
     * {@code DELETE  /good-services/:id} : delete the "id" goodService.
     *
     * @param id the id of the goodServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/good-services/{id}")
    public ResponseEntity<Void> deleteGoodService(@PathVariable Long id) {
        log.debug("REST request to delete GoodService : {}", id);
        goodServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/good-services?query=:query} : search for the goodService corresponding
     * to the query.
     *
     * @param query the query of the goodService search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/good-services")
    public ResponseEntity<List<GoodServiceDTO>> searchGoodServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GoodServices for query {}", query);
        Page<GoodServiceDTO> page = goodServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
