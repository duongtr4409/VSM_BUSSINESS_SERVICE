package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.AttachmentInStepRepository;
import com.vsm.business.service.AttachmentInStepService;
import com.vsm.business.service.custom.search.service.AttachmentInStepSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentInStepDTO;
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

@RestController
@RequestMapping("/api")
public class AttachmentInStepCustomRest {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentInStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentInStepService attachmentInStepService;

    private final AttachmentInStepRepository attachmentInStepRepository;

    private final AttachmentInStepSearchService attachmentInStepSearchService;

    public AttachmentInStepCustomRest(
        AttachmentInStepService attachmentInStepService,
        AttachmentInStepRepository attachmentInStepRepository,
        AttachmentInStepSearchService attachmentInStepSearchService
    ) {
        this.attachmentInStepService = attachmentInStepService;
        this.attachmentInStepRepository = attachmentInStepRepository;
        this.attachmentInStepSearchService = attachmentInStepSearchService;
    }

    /**
     * {@code POST  /attachment-in-steps} : Create a new attachmentInStep.
     *
     * @param attachmentInStepDTO the attachmentInStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentInStepDTO, or with status {@code 400 (Bad Request)} if the attachmentInStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-in-steps")
    public ResponseEntity<IResponseMessage> createAttachmentInStep(@RequestBody AttachmentInStepDTO attachmentInStepDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachmentInStep : {}", attachmentInStepDTO);
        if (attachmentInStepDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(attachmentInStepDTO));
        }
        AttachmentInStepDTO result = attachmentInStepService.save(attachmentInStepDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(attachmentInStepDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /attachment-in-steps/:id} : Updates an existing attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to save.
     * @param attachmentInStepDTO the attachmentInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-in-steps/{id}")
    public ResponseEntity<IResponseMessage> updateAttachmentInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepDTO attachmentInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachmentInStep : {}, {}", id, attachmentInStepDTO);
        if (attachmentInStepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentInStepDTO));
        }
        if (!Objects.equals(id, attachmentInStepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentInStepDTO));
        }

        if (!attachmentInStepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentInStepDTO));
        }

        AttachmentInStepDTO result = attachmentInStepService.save(attachmentInStepDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentInStepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /attachment-in-steps/:id} : Partial updates given fields of an existing attachmentInStep, field will ignore if it is null
     *
     * @param id the id of the attachmentInStepDTO to save.
     * @param attachmentInStepDTO the attachmentInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentInStepDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentInStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentInStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-in-steps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateAttachmentInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttachmentInStepDTO attachmentInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachmentInStep partially : {}, {}", id, attachmentInStepDTO);
        if (attachmentInStepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentInStepDTO));
        }
        if (!Objects.equals(id, attachmentInStepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentInStepDTO));
        }

        if (!attachmentInStepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentInStepDTO));
        }

        Optional<AttachmentInStepDTO> result = attachmentInStepService.partialUpdate(attachmentInStepDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentInStepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /attachment-in-steps} : get all the attachmentInSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentInSteps in body.
     */
    @GetMapping("/attachment-in-steps")
    public ResponseEntity<IResponseMessage> getAllAttachmentInSteps(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentInSteps");
        Page<AttachmentInStepDTO> page = attachmentInStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /attachment-in-steps/:id} : get the "id" attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentInStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-in-steps/{id}")
    public ResponseEntity<IResponseMessage> getAttachmentInStep(@PathVariable Long id) {
        log.debug("REST request to get AttachmentInStep : {}", id);
        Optional<AttachmentInStepDTO> attachmentInStepDTO = attachmentInStepService.findOne(id);

        if (!attachmentInStepDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(attachmentInStepDTO.get()));
    }

    /**
     * {@code DELETE  /attachment-in-steps/:id} : delete the "id" attachmentInStep.
     *
     * @param id the id of the attachmentInStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-in-steps/{id}")
    public ResponseEntity<IResponseMessage> deleteAttachmentInStep(@PathVariable Long id) {
        log.debug("REST request to delete AttachmentInStep : {}", id);
        attachmentInStepService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/attachment-in-steps?query=:query} : search for the attachmentInStep corresponding
     * to the query.
     *
     * @param query the query of the attachmentInStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-in-steps")
    public ResponseEntity<List<AttachmentInStepDTO>> searchAttachmentInSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentInSteps for query {}", query);
        Page<AttachmentInStepDTO> page = attachmentInStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/attachment-in-steps")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody AttachmentInStepDTO attachmentInStepDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentInStepSearchService.simpleQuerySearch(attachmentInStepDTO, pageable);
        log.debug("AttachmentInStepCustomRest: customSearch(IBaseSearchDTO: {}): {}", attachmentInStepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/attachment-in-steps")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody AttachmentInStepDTO attachmentInStepDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentInStepSearchService.simpleQuerySearchWithParam(attachmentInStepDTO, paramMaps, pageable);
        log.debug("AttachmentInStepCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", attachmentInStepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
