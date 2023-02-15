package com.vsm.business.web.rest;

import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.service.FileTypeService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.FileTypeDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.FileType}.
 */
//@RestController
@RequestMapping("/api")
public class FileTypeResource {

    private final Logger log = LoggerFactory.getLogger(FileTypeResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileTypeService fileTypeService;

    private final FileTypeRepository fileTypeRepository;

    public FileTypeResource(FileTypeService fileTypeService, FileTypeRepository fileTypeRepository) {
        this.fileTypeService = fileTypeService;
        this.fileTypeRepository = fileTypeRepository;
    }

    /**
     * {@code POST  /file-types} : Create a new fileType.
     *
     * @param fileTypeDTO the fileTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileTypeDTO, or with status {@code 400 (Bad Request)} if the fileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-types")
    public ResponseEntity<FileTypeDTO> createFileType(@RequestBody FileTypeDTO fileTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FileType : {}", fileTypeDTO);
        if (fileTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        return ResponseEntity
            .created(new URI("/api/file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-types/:id} : Updates an existing fileType.
     *
     * @param id the id of the fileTypeDTO to save.
     * @param fileTypeDTO the fileTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fileTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-types/{id}")
    public ResponseEntity<FileTypeDTO> updateFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileTypeDTO fileTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileType : {}, {}", id, fileTypeDTO);
        if (fileTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-types/:id} : Partial updates given fields of an existing fileType, field will ignore if it is null
     *
     * @param id the id of the fileTypeDTO to save.
     * @param fileTypeDTO the fileTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fileTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileTypeDTO> partialUpdateFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileTypeDTO fileTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileType partially : {}, {}", id, fileTypeDTO);
        if (fileTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileTypeDTO> result = fileTypeService.partialUpdate(fileTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /file-types} : get all the fileTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileTypes in body.
     */
    @GetMapping("/file-types")
    public ResponseEntity<List<FileTypeDTO>> getAllFileTypes(Pageable pageable) {
        log.debug("REST request to get a page of FileTypes");
        Page<FileTypeDTO> page = fileTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-types/:id} : get the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-types/{id}")
    public ResponseEntity<FileTypeDTO> getFileType(@PathVariable Long id) {
        log.debug("REST request to get FileType : {}", id);
        Optional<FileTypeDTO> fileTypeDTO = fileTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileTypeDTO);
    }

    /**
     * {@code DELETE  /file-types/:id} : delete the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-types/{id}")
    public ResponseEntity<Void> deleteFileType(@PathVariable Long id) {
        log.debug("REST request to delete FileType : {}", id);
        fileTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/file-types?query=:query} : search for the fileType corresponding
     * to the query.
     *
     * @param query the query of the fileType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/file-types")
    public ResponseEntity<List<FileTypeDTO>> searchFileTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FileTypes for query {}", query);
        Page<FileTypeDTO> page = fileTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
