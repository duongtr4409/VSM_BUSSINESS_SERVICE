package com.vsm.business.web.rest;

import com.vsm.business.repository.TagInExchangeRepository;
import com.vsm.business.service.TagInExchangeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.TagInExchangeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.TagInExchange}.
 */
//@RestController
@RequestMapping("/api")
public class TagInExchangeResource {

    private final Logger log = LoggerFactory.getLogger(TagInExchangeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTagInExchange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagInExchangeService tagInExchangeService;

    private final TagInExchangeRepository tagInExchangeRepository;

    public TagInExchangeResource(TagInExchangeService tagInExchangeService, TagInExchangeRepository tagInExchangeRepository) {
        this.tagInExchangeService = tagInExchangeService;
        this.tagInExchangeRepository = tagInExchangeRepository;
    }

    /**
     * {@code POST  /tag-in-exchanges} : Create a new tagInExchange.
     *
     * @param tagInExchangeDTO the tagInExchangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagInExchangeDTO, or with status {@code 400 (Bad Request)} if the tagInExchange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-in-exchanges")
    public ResponseEntity<TagInExchangeDTO> createTagInExchange(@RequestBody TagInExchangeDTO tagInExchangeDTO) throws URISyntaxException {
        log.debug("REST request to save TagInExchange : {}", tagInExchangeDTO);
        if (tagInExchangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagInExchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagInExchangeDTO result = tagInExchangeService.save(tagInExchangeDTO);
        return ResponseEntity
            .created(new URI("/api/tag-in-exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-in-exchanges/:id} : Updates an existing tagInExchange.
     *
     * @param id the id of the tagInExchangeDTO to save.
     * @param tagInExchangeDTO the tagInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the tagInExchangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-in-exchanges/{id}")
    public ResponseEntity<TagInExchangeDTO> updateTagInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagInExchangeDTO tagInExchangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TagInExchange : {}, {}", id, tagInExchangeDTO);
        if (tagInExchangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagInExchangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagInExchangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TagInExchangeDTO result = tagInExchangeService.save(tagInExchangeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagInExchangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tag-in-exchanges/:id} : Partial updates given fields of an existing tagInExchange, field will ignore if it is null
     *
     * @param id the id of the tagInExchangeDTO to save.
     * @param tagInExchangeDTO the tagInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the tagInExchangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tagInExchangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tagInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tag-in-exchanges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TagInExchangeDTO> partialUpdateTagInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagInExchangeDTO tagInExchangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TagInExchange partially : {}, {}", id, tagInExchangeDTO);
        if (tagInExchangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagInExchangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagInExchangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TagInExchangeDTO> result = tagInExchangeService.partialUpdate(tagInExchangeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagInExchangeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tag-in-exchanges} : get all the tagInExchanges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagInExchanges in body.
     */
    @GetMapping("/tag-in-exchanges")
    public ResponseEntity<List<TagInExchangeDTO>> getAllTagInExchanges(Pageable pageable) {
        log.debug("REST request to get a page of TagInExchanges");
        Page<TagInExchangeDTO> page = tagInExchangeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-in-exchanges/:id} : get the "id" tagInExchange.
     *
     * @param id the id of the tagInExchangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagInExchangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-in-exchanges/{id}")
    public ResponseEntity<TagInExchangeDTO> getTagInExchange(@PathVariable Long id) {
        log.debug("REST request to get TagInExchange : {}", id);
        Optional<TagInExchangeDTO> tagInExchangeDTO = tagInExchangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagInExchangeDTO);
    }

    /**
     * {@code DELETE  /tag-in-exchanges/:id} : delete the "id" tagInExchange.
     *
     * @param id the id of the tagInExchangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-in-exchanges/{id}")
    public ResponseEntity<Void> deleteTagInExchange(@PathVariable Long id) {
        log.debug("REST request to delete TagInExchange : {}", id);
        tagInExchangeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/tag-in-exchanges?query=:query} : search for the tagInExchange corresponding
     * to the query.
     *
     * @param query the query of the tagInExchange search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tag-in-exchanges")
    public ResponseEntity<List<TagInExchangeDTO>> searchTagInExchanges(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TagInExchanges for query {}", query);
        Page<TagInExchangeDTO> page = tagInExchangeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
