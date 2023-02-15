package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.AttachmentInStepTypeRepository;
import com.vsm.business.service.AttachmentInStepTypeService;
import com.vsm.business.service.custom.search.service.AttachmentInStepTypeSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.AttachmentInStepType}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentInStepTypeCustomRest {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepTypeCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentInStepType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentInStepTypeService attachmentInStepTypeService;

    private final AttachmentInStepTypeRepository attachmentInStepTypeRepository;

    private final AttachmentInStepTypeSearchService attachmentInStepTypeSearchService;

    public AttachmentInStepTypeCustomRest(
        AttachmentInStepTypeService attachmentInStepTypeService,
        AttachmentInStepTypeRepository attachmentInStepTypeRepository,
        AttachmentInStepTypeSearchService attachmentInStepTypeSearchService
    ) {
        this.attachmentInStepTypeService = attachmentInStepTypeService;
        this.attachmentInStepTypeRepository = attachmentInStepTypeRepository;
        this.attachmentInStepTypeSearchService = attachmentInStepTypeSearchService;
    }

    /**
     * {@code POST  /attachment-in-step-types} : Create a new attachmentInStepType.
     *
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentInStepTypeDTO, or with status {@code 400 (Bad Request)} if the attachmentInStepType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-in-step-types")
    public ResponseEntity<IResponseMessage> createAttachmentInStepType(@RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachmentInStepType : {}", attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(attachmentInStepTypeDTO));
        }
        AttachmentInStepTypeDTO result = attachmentInStepTypeService.save(attachmentInStepTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(attachmentInStepTypeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /attachment-in-step-types/:id} : Updates an existing attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to save.
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<IResponseMessage> updateAttachmentInStepType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentInStepType : {}, {}", id, attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentInStepTypeDTO));
        }
        if (!Objects.equals(id, attachmentInStepTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentInStepTypeDTO));
        }

        if (!attachmentInStepTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentInStepTypeDTO));
        }

        AttachmentInStepTypeDTO result = attachmentInStepTypeService.save(attachmentInStepTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentInStepTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /attachment-in-step-types/:id} : Partial updates given fields of an existing attachmentInStepType, field will ignore if it is null
     *
     * @param id the id of the attachmentInStepTypeDTO to save.
     * @param attachmentInStepTypeDTO the attachmentInStepTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentInStepTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-in-step-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateAttachmentInStepType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentInStepType partially : {}, {}", id, attachmentInStepTypeDTO);
        if (attachmentInStepTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentInStepTypeDTO));
        }
        if (!Objects.equals(id, attachmentInStepTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentInStepTypeDTO));
        }

        if (!attachmentInStepTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentInStepTypeDTO));
        }

        Optional<AttachmentInStepTypeDTO> result = attachmentInStepTypeService.partialUpdate(attachmentInStepTypeDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentInStepTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /attachment-in-step-types} : get all the attachmentInStepTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentInStepTypes in body.
     */
    @GetMapping("/attachment-in-step-types")
    public ResponseEntity<IResponseMessage> getAllAttachmentInStepTypes(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentInStepTypes");
        Page<AttachmentInStepTypeDTO> page = attachmentInStepTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /attachment-in-step-types/:id} : get the "id" attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentInStepTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<IResponseMessage> getAttachmentInStepType(@PathVariable Long id) {
        log.debug("REST request to get AttachmentInStepType : {}", id);
        Optional<AttachmentInStepTypeDTO> attachmentInStepTypeDTO = attachmentInStepTypeService.findOne(id);

        if (!attachmentInStepTypeDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(attachmentInStepTypeDTO.get()));
    }

    /**
     * {@code DELETE  /attachment-in-step-types/:id} : delete the "id" attachmentInStepType.
     *
     * @param id the id of the attachmentInStepTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-in-step-types/{id}")
    public ResponseEntity<Void> deleteAttachmentInStepType(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentInStepType : {}", id);
        attachmentInStepTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/attachment-in-step-types?query=:query} : search for the attachmentInStepType corresponding
     * to the query.
     *
     * @param query the query of the attachmentInStepType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-in-step-types")
    public ResponseEntity<List<AttachmentInStepTypeDTO>> searchAttachmentInStepTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentInStepTypes for query {}", query);
        Page<AttachmentInStepTypeDTO> page = attachmentInStepTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/attachment-in-step-types")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentInStepTypeSearchService.simpleQuerySearch(attachmentInStepTypeDTO, pageable);
        log.debug("AttachmentInStepTypeCustomRest: customSearch(IBaseSearchDTO: {}): {}", attachmentInStepTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/attachment-in-step-types")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody AttachmentInStepTypeDTO attachmentInStepTypeDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentInStepTypeSearchService.simpleQuerySearchWithParam(attachmentInStepTypeDTO, paramMaps, pageable);
        log.debug("AttachmentInStepTypeCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", attachmentInStepTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
