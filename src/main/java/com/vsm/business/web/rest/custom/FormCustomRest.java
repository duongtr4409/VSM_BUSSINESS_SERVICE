package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.FormRepository;
import com.vsm.business.service.FormService;
import com.vsm.business.service.custom.FieldInFormCustomService;
import com.vsm.business.service.custom.FormCustomService;
import com.vsm.business.service.custom.search.service.FormSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FormDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

/**
 * REST controller for managing {@link com.vsm.business.domain.Form}.
 */
@RestController
@RequestMapping("/api")
public class FormCustomRest {

    private final Logger log = LoggerFactory.getLogger(FormCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceEForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormService formService;

    private final FormRepository formRepository;

    private final FormCustomService formCustomService;

    private final FieldInFormCustomService fieldInFormCustomService;

    private final FormSearchService formSearchService;

    public FormCustomRest(FormService formService, FormRepository formRepository, FormCustomService formCustomService, FieldInFormCustomService fieldInFormCustomService, FormSearchService formSearchService) {
        this.formService = formService;
        this.formRepository = formRepository;
        this.formCustomService = formCustomService;
        this.fieldInFormCustomService = fieldInFormCustomService;
        this.formSearchService = formSearchService;
    }

    /**
     * {@code POST  /forms} : Create a new eForm.
     *
     * @param formDTO the formDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formDTO, or with status {@code 400 (Bad Request)} if the eForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forms")
    public ResponseEntity<IResponseMessage> createEForm(@Valid @RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to save EForm : {}", formDTO);
        if (formDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(formDTO));
        }
        FormDTO result = formService.save(formDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(formDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /forms/:id} : Updates an existing eForm.
     *
     * @param id the id of the formDTO to save.
     * @param formDTO the formDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDTO,
     * or with status {@code 400 (Bad Request)} if the formDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forms/{id}")
    public ResponseEntity<IResponseMessage> updateEForm(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FormDTO formDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EForm : {}, {}", id, formDTO);
        if (formDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(formDTO));
        }
        if (!Objects.equals(id, formDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(formDTO));
        }

        if (!formRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(formDTO));
        }

        FormDTO result = formService.save(formDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(formDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /forms/:id} : Partial updates given fields of an existing eForm, field will ignore if it is null
     *
     * @param id the id of the formDTO to save.
     * @param formDTO the formDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDTO,
     * or with status {@code 400 (Bad Request)} if the formDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateEForm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FormDTO formDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EForm partially : {}, {}", id, formDTO);
        if (formDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(formDTO));
        }
        if (!Objects.equals(id, formDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(formDTO));
        }

        if (!formRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(formDTO));
        }

        Optional<FormDTO> result = formService.partialUpdate(formDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(formDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /forms} : get all the eForms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eForms in body.
     */
    @GetMapping("/forms")
    public ResponseEntity<IResponseMessage> getAllEForms(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of EForms");
        Page<FormDTO> page;
        if (eagerload) {
            page = formService.findAllWithEagerRelationships(pageable);
        } else {
            page = formService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));


    }

    /**
     * {@code GET  /forms/:id} : get the "id" eForm.
     *
     * @param id the id of the eFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forms/{id}")
    public ResponseEntity<IResponseMessage> getEForm(@PathVariable Long id) {
        log.debug("REST request to get EForm : {}", id);
        Optional<FormDTO> formDTO = formService.findOne(id);
        if (!formDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(formDTO.get()));
    }

    /**
     * {@code DELETE  /forms/:id} : delete the "id" eForm.
     *
     * @param id the id of the eFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<IResponseMessage> deleteEForm(@PathVariable Long id) {
        log.debug("REST request to delete EForm : {}", id);
        formService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/forms?query=:query} : search for the eForm corresponding
     * to the query.
     *
     * @param query the query of the eForm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/forms")
    public ResponseEntity<List<FormDTO>> searchEForms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EForms for query {}", query);
        Page<FormDTO> page = formService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/forms/{eformId}/_all/field-in-eform")
    public ResponseEntity<IResponseMessage> findAllByStepInProcessId(@PathVariable("eformId") Long eformId) {
        List<FieldInFormDTO> result = fieldInFormCustomService.findAllByEFormId(eformId, false);
        log.debug("REST request to findAllByStepInProcessId({}): {}", eformId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/forms")
    public ResponseEntity<IResponseMessage> getAll() {
        List<FormDTO> result = formCustomService.getAll();
        log.debug("FormCustomService: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PutMapping("/_save/forms")
    public ResponseEntity<IResponseMessage>saveAll(List<FormDTO> formDTOList){
        List<FormDTO> result = formCustomService.saveAll(formDTOList);
        log.debug("REST request to saveAll({}): ", formDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all_by_request/{requestId}/forms")
    public ResponseEntity<IResponseMessage> getAllByRequest(@PathVariable("requestId") Long requestId){
        List<FormDTO> result = formCustomService.getAllByRequest(requestId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/forms")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FormDTO formDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.formSearchService.simpleQuerySearch(formDTO, pageable);
        log.debug("FormCustomRest: customSearch(IBaseSearchDTO: {}): {}", formDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/forms")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FormDTO formDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.formSearchService.simpleQuerySearchWithParam(formDTO, paramMaps, pageable);
        log.debug("FormCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", formDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
