package com.vsm.business.web.rest;

import com.vsm.business.repository.OfficialDispatchTypeRepository;
import com.vsm.business.service.OfficialDispatchTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OfficialDispatchTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatchType}.
 */
//@RestController
@RequestMapping("/api")
public class OfficialDispatchTypeResource {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatchType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchTypeService officialDispatchTypeService;

    private final OfficialDispatchTypeRepository officialDispatchTypeRepository;

    public OfficialDispatchTypeResource(
        OfficialDispatchTypeService officialDispatchTypeService,
        OfficialDispatchTypeRepository officialDispatchTypeRepository
    ) {
        this.officialDispatchTypeService = officialDispatchTypeService;
        this.officialDispatchTypeRepository = officialDispatchTypeRepository;
    }

    /**
     * {@code POST  /official-dispatch-types} : Create a new officialDispatchType.
     *
     * @param officialDispatchTypeDTO the officialDispatchTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchTypeDTO, or with status {@code 400 (Bad Request)} if the officialDispatchType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatch-types")
    public ResponseEntity<OfficialDispatchTypeDTO> createOfficialDispatchType(@RequestBody OfficialDispatchTypeDTO officialDispatchTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save OfficialDispatchType : {}", officialDispatchTypeDTO);
        if (officialDispatchTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new officialDispatchType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficialDispatchTypeDTO result = officialDispatchTypeService.save(officialDispatchTypeDTO);
        return ResponseEntity
            .created(new URI("/api/official-dispatch-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /official-dispatch-types/:id} : Updates an existing officialDispatchType.
     *
     * @param id the id of the officialDispatchTypeDTO to save.
     * @param officialDispatchTypeDTO the officialDispatchTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchTypeDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatch-types/{id}")
    public ResponseEntity<OfficialDispatchTypeDTO> updateOfficialDispatchType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchTypeDTO officialDispatchTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatchType : {}, {}", id, officialDispatchTypeDTO);
        if (officialDispatchTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfficialDispatchTypeDTO result = officialDispatchTypeService.save(officialDispatchTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /official-dispatch-types/:id} : Partial updates given fields of an existing officialDispatchType, field will ignore if it is null
     *
     * @param id the id of the officialDispatchTypeDTO to save.
     * @param officialDispatchTypeDTO the officialDispatchTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchTypeDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatch-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfficialDispatchTypeDTO> partialUpdateOfficialDispatchType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchTypeDTO officialDispatchTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatchType partially : {}, {}", id, officialDispatchTypeDTO);
        if (officialDispatchTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officialDispatchTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officialDispatchTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfficialDispatchTypeDTO> result = officialDispatchTypeService.partialUpdate(officialDispatchTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officialDispatchTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /official-dispatch-types} : get all the officialDispatchTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatchTypes in body.
     */
    @GetMapping("/official-dispatch-types")
    public ResponseEntity<List<OfficialDispatchTypeDTO>> getAllOfficialDispatchTypes(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatchTypes");
        Page<OfficialDispatchTypeDTO> page = officialDispatchTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /official-dispatch-types/:id} : get the "id" officialDispatchType.
     *
     * @param id the id of the officialDispatchTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatch-types/{id}")
    public ResponseEntity<OfficialDispatchTypeDTO> getOfficialDispatchType(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatchType : {}", id);
        Optional<OfficialDispatchTypeDTO> officialDispatchTypeDTO = officialDispatchTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officialDispatchTypeDTO);
    }

    /**
     * {@code DELETE  /official-dispatch-types/:id} : delete the "id" officialDispatchType.
     *
     * @param id the id of the officialDispatchTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatch-types/{id}")
    public ResponseEntity<Void> deleteOfficialDispatchType(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatchType : {}", id);
        officialDispatchTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/official-dispatch-types?query=:query} : search for the officialDispatchType corresponding
     * to the query.
     *
     * @param query the query of the officialDispatchType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatch-types")
    public ResponseEntity<List<OfficialDispatchTypeDTO>> searchOfficialDispatchTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatchTypes for query {}", query);
        Page<OfficialDispatchTypeDTO> page = officialDispatchTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
