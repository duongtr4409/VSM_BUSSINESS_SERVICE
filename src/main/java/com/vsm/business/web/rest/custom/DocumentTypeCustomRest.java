package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.DocumentTypeRepository;
import com.vsm.business.service.DocumentTypeService;
import com.vsm.business.service.custom.search.service.DocumentTypeSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DocumentTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.DocumentType}.
 */
@RestController
@RequestMapping("/api")
public class DocumentTypeCustomRest {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDocumentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentTypeService documentTypeService;

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeSearchService documentTypeSearchService;

    public DocumentTypeCustomRest(DocumentTypeService documentTypeService, DocumentTypeRepository documentTypeRepository, DocumentTypeSearchService documentTypeSearchService) {
        this.documentTypeService = documentTypeService;
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeSearchService = documentTypeSearchService;
    }

    /**
     * {@code POST  /document-types} : Create a new documentType.
     *
     * @param documentTypeDTO the documentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentTypeDTO, or with status {@code 400 (Bad Request)} if the documentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-types")
    public ResponseEntity<IResponseMessage> createDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", documentTypeDTO);
        if (documentTypeDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(documentTypeDTO));
        }
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(documentTypeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
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
    public ResponseEntity<IResponseMessage> updateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentType : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(documentTypeDTO));
        }
        if (!Objects.equals(id, documentTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(documentTypeDTO));
        }

        if (!documentTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(documentTypeDTO));
        }

        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(documentTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
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
    public ResponseEntity<IResponseMessage> partialUpdateDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeDTO documentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentType partially : {}, {}", id, documentTypeDTO);
        if (documentTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(documentTypeDTO));
        }
        if (!Objects.equals(id, documentTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(documentTypeDTO));
        }

        if (!documentTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(documentTypeDTO));
        }

        Optional<DocumentTypeDTO> result = documentTypeService.partialUpdate(documentTypeDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(documentTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /document-types} : get all the documentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentTypes in body.
     */
    @GetMapping("/document-types")
    public ResponseEntity<IResponseMessage> getAllDocumentTypes(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTypes");
        Page<DocumentTypeDTO> page = documentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /document-types/:id} : get the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-types/{id}")
    public ResponseEntity<IResponseMessage> getDocumentType(@PathVariable Long id) {
        log.debug("REST request to get DocumentType : {}", id);
        Optional<DocumentTypeDTO> documentTypeDTO = documentTypeService.findOne(id);

        if (!documentTypeDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(documentTypeDTO.get()));
    }

    /**
     * {@code DELETE  /document-types/:id} : delete the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-types/{id}")
    public ResponseEntity<IResponseMessage> deleteDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete DocumentType : {}", id);
        documentTypeService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
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


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/document-types")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody DocumentTypeDTO documentTypeDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.documentTypeSearchService.simpleQuerySearch(documentTypeDTO, pageable);
        log.debug("DocumentTypeCustomRest: customSearch(IBaseSearchDTO: {}): {}", documentTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/document-types")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody DocumentTypeDTO documentTypeDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.documentTypeSearchService.simpleQuerySearchWithParam(documentTypeDTO, paramMaps, pageable);
        log.debug("DocumentTypeCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", documentTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

}
