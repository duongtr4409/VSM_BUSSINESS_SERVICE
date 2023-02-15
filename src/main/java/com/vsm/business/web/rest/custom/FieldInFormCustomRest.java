package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.FieldInFormRepository;
import com.vsm.business.service.FieldInFormService;
import com.vsm.business.service.custom.FieldInFormCustomService;
import com.vsm.business.service.custom.search.service.FieldInFormSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldInFormDTO;
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

import javax.validation.Valid;
import java.util.List;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FieldInFormCustomRest {
    private final Logger log = LoggerFactory.getLogger(FieldInFormCustomRest.class);
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldInFormService fieldInFormService;
    private final FieldInFormRepository fieldInFormRepository;
    private final FieldInFormCustomService fieldInFormCustomService;

    private final FieldInFormSearchService fieldInFormSearchService;

    public FieldInFormCustomRest(FieldInFormService fieldInFormService, FieldInFormRepository fieldInFormRepository, FieldInFormCustomService fieldInFormCustomService, FieldInFormSearchService fieldInFormSearchService) {
        this.fieldInFormService = fieldInFormService;
        this.fieldInFormRepository = fieldInFormRepository;
        this.fieldInFormCustomService = fieldInFormCustomService;
        this.fieldInFormSearchService = fieldInFormSearchService;
    }

    /**
     * {@code POST  /field-in-forms} : Create a new fieldInEForm.
     *
     * @param fieldInFormDTO the fieldInEFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldInEFormDTO, or with status {@code 400 (Bad Request)} if the fieldInEForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-in-forms")
    public ResponseEntity<IResponseMessage> createFieldInEForm(@Valid @RequestBody FieldInFormDTO fieldInFormDTO)
        throws URISyntaxException {
        log.debug("REST request to save FieldInEForm : {}", fieldInFormDTO);
        if (fieldInFormDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(fieldInFormDTO));
        }
        FieldInFormDTO result = fieldInFormService.save(fieldInFormDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(fieldInFormDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /field-in-forms/:id} : Updates an existing fieldInEForm.
     *
     * @param id              the id of the fieldInEFormDTO to save.
     * @param fieldInFormDTO the fieldInEFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldInEFormDTO,
     * or with status {@code 400 (Bad Request)} if the fieldInEFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldInEFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-in-forms/{id}")
    public ResponseEntity<IResponseMessage> updateFieldInEForm(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldInFormDTO fieldInFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FieldInEForm : {}, {}", id, fieldInFormDTO);
        if (fieldInFormDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldInFormDTO));
        }
        if (!Objects.equals(id, fieldInFormDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldInFormDTO));
        }

        if (!fieldInFormRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldInFormDTO));
        }

        FieldInFormDTO result = fieldInFormService.save(fieldInFormDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fieldInFormDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /field-in-forms/:id} : Partial updates given fields of an existing fieldInEForm, field will ignore if it is null
     *
     * @param id              the id of the fieldInEFormDTO to save.
     * @param fieldInFormDTO the fieldInEFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldInEFormDTO,
     * or with status {@code 400 (Bad Request)} if the fieldInEFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldInEFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldInEFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-in-forms/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateFieldInEForm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldInFormDTO fieldInFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldInEForm partially : {}, {}", id, fieldInFormDTO);
        if (fieldInFormDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldInFormDTO));
        }
        if (!Objects.equals(id, fieldInFormDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldInFormDTO));
        }

        if (!fieldInFormRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldInFormDTO));
        }

        Optional<FieldInFormDTO> result = fieldInFormService.partialUpdate(fieldInFormDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fieldInFormDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /field-in-forms} : get all the fieldInEForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldInEForms in body.
     */
    @GetMapping("/field-in-forms")
    public ResponseEntity<IResponseMessage> getAllFieldInEForms(Pageable pageable) {
        log.debug("REST request to get a page of FieldInEForms");
        Page<FieldInFormDTO> page = fieldInFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /field-in-forms/:id} : get the "id" fieldInEForm.
     *
     * @param id the id of the fieldInEFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldInEFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-in-forms/{id}")
    public ResponseEntity<IResponseMessage> getFieldInEForm(@PathVariable Long id) {
        log.debug("REST request to get FieldInEForm : {}", id);
        Optional<FieldInFormDTO> fieldInFormDTO = fieldInFormService.findOne(id);
        if (!fieldInFormDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(fieldInFormDTO.get()));
    }

    /**
     * {@code DELETE  /field-in-forms/:id} : delete the "id" fieldInEForm.
     *
     * @param id the id of the fieldInEFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-in-forms/{id}")
    public ResponseEntity<IResponseMessage> deleteFieldInEForm(@PathVariable Long id) {
        log.debug("REST request to delete FieldInEForm : {}", id);
        fieldInFormService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/field-in-forms?query=:query} : search for the fieldInEForm corresponding
     * to the query.
     *
     * @param query    the query of the fieldInEForm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/field-in-forms")
    public ResponseEntity<IResponseMessage> searchFieldInEForms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FieldInEForms for query {}", query);
        Page<FieldInFormDTO> page = fieldInFormService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/forms/{formId}/_all/field-in-form")
    public ResponseEntity<IResponseMessage> findAllByFormId(@PathVariable("formId") Long formId, @RequestParam(value = "ignoreField", defaultValue = "false", required = false) Boolean ignoreField) {
        List<FieldInFormDTO> result = fieldInFormCustomService.findAllByEFormId(formId, ignoreField);
        log.debug("REST request to findAllByFormId({}): {}", formId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/field-in-form")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FieldInFormDTO fieldInFormDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldInFormSearchService.simpleQuerySearch(fieldInFormDTO, pageable);
        log.debug("FieldInFormCustomRest: customSearch(IBaseSearchDTO: {}): {}", fieldInFormDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/field-in-form")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FieldInFormDTO filFieldInFormDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldInFormSearchService.simpleQuerySearchWithParam(filFieldInFormDTO, paramMaps, pageable);
        log.debug("FieldInFormCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", filFieldInFormDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
