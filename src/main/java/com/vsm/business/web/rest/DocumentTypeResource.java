package com.vsm.business.web.rest;

import com.vsm.business.repository.DocumentTypeRepository;
import com.vsm.business.service.DocumentTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.DocumentTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.DocumentType}.
 */
//@RestController
@RequestMapping("/api")
public class DocumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDocumentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentTypeService documentTypeService;

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeResource(DocumentTypeService documentTypeService, DocumentTypeRepository documentTypeRepository) {
        this.documentTypeService = documentTypeService;
        this.documentTypeRepository = documentTypeRepository;
    }

    /**
     * {@code POST  /document-types} : Create a new documentType.
     *
     * @param documentTypeDTO the documentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentTypeDTO, or with status {@code 400 (Bad Request)} if the documentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-types")
    public ResponseEntity<DocumentTypeDTO> createDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", documentTypeDTO);
        if (documentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        return ResponseEntity
            .created(new URI("/api/document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-types/:id} : Updates an existing documentType.
     *
     * @param id the id of the documentTypeDTO to save.
     * @param documentTypeDTO the documentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-types/{id}")
    public ResponseEntity<DocumentTypeDTO> updateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentType : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-types/:id} : Partial updates given fields of an existing documentType, field will ignore if it is null
     *
     * @param id the id of the documentTypeDTO to save.
     * @param documentTypeDTO the documentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentTypeDTO> partialUpdateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentType partially : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentTypeDTO> result = documentTypeService.partialUpdate(documentTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /document-types} : get all the documentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentTypes in body.
     */
    @GetMapping("/document-types")
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTypes");
        Page<DocumentTypeDTO> page = documentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-types/:id} : get the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-types/{id}")
    public ResponseEntity<DocumentTypeDTO> getDocumentType(@PathVariable Long id) {
        log.debug("REST request to get DocumentType : {}", id);
        Optional<DocumentTypeDTO> documentTypeDTO = documentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTypeDTO);
    }

    /**
     * {@code DELETE  /document-types/:id} : delete the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-types/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete DocumentType : {}", id);
        documentTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/document-types?query=:query} : search for the documentType corresponding
     * to the query.
     *
     * @param query the query of the documentType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/document-types")
    public ResponseEntity<List<DocumentTypeDTO>> searchDocumentTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentTypes for query {}", query);
        Page<DocumentTypeDTO> page = documentTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
