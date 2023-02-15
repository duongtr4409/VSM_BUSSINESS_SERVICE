package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.vsm.business.domain.FormData;
import com.vsm.business.repository.FormDataRepository;
import com.vsm.business.service.FormDataService;
import com.vsm.business.service.custom.FormDataCustomService;
import com.vsm.business.service.custom.search.service.FormDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.*;
import com.vsm.business.utils.AuthenticateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.FormData}.
 */
@RestController
@RequestMapping("/api")
public class FormDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(FormDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceEFormData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormDataService formDataService;

    private final FormDataRepository formDataRepository;

    private final FormDataCustomService formDataCustomService;

    private final FormDataSearchService formDataSearchService;

    private final AuthenticateUtils authenticateUtils;

    public FormDataCustomRest(FormDataService formDataService, FormDataRepository formDataRepository, FormDataCustomService formDataCustomService, FormDataSearchService formDataSearchService, AuthenticateUtils authenticateUtils) {
        this.formDataService = formDataService;
        this.formDataRepository = formDataRepository;
        this.formDataCustomService = formDataCustomService;
        this.formDataSearchService = formDataSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /form-data} : Create a new eFormData.
     *
     * @param formDataDTO the formDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formDataDTO, or with status {@code 400 (Bad Request)} if the eFormData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-data")
    public ResponseEntity<IResponseMessage> createEFormData(@Valid @RequestBody FormDataDTO formDataDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(formDataDTO.getRequestData() != null ? formDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save EFormData : {}", formDataDTO);
        if (formDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(formDataDTO));
        }
        FormDataDTO result = formDataService.save(formDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(formDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /form-data/:id} : Updates an existing eFormData.
     *
     * @param id the id of the formDataDTO to save.
     * @param formDataDTO the formDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDataDTO,
     * or with status {@code 400 (Bad Request)} if the formDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-data/{id}")
    public ResponseEntity<IResponseMessage> updateEFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FormDataDTO formDataDTO,
        @RequestParam(value = "fillIn", required = false, defaultValue = "false") Boolean fillIn
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(formDataDTO.getRequestData() != null ? formDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update EFormData : {}, {}", id, formDataDTO);
        if (formDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(formDataDTO));
        }
        if (!Objects.equals(id, formDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(formDataDTO));
        }

        if (!formDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(formDataDTO));
        }

        //FormDataDTO result = formDataService.save(formDataDTO);
        FormDataDTO result = formDataCustomService.customSave(formDataDTO, fillIn);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(formDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /form-data/:id} : Partial updates given fields of an existing eFormData, field will ignore if it is null
     *
     * @param id the id of the eFormDataDTO to save.
     * @param eFormDataDTO the eFormDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eFormDataDTO,
     * or with status {@code 400 (Bad Request)} if the eFormDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eFormDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eFormDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateEFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FormDataDTO eFormDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(eFormDataDTO.getRequestData() != null ? eFormDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update EFormData partially : {}, {}", id, eFormDataDTO);
        if (eFormDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(eFormDataDTO));
        }
        if (!Objects.equals(id, eFormDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(eFormDataDTO));
        }

        if (!formDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(eFormDataDTO));
        }

        Optional<FormDataDTO> result = formDataService.partialUpdate(eFormDataDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(eFormDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /form-data} : get all the eFormData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eFormData in body.
     */
    @GetMapping("/form-data")
    public ResponseEntity<IResponseMessage> getAllEFormData(Pageable pageable) {
        log.debug("REST request to get a page of EFormData");
        Page<FormDataDTO> page = formDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /form-data/:id} : get the "id" eFormData.
     *
     * @param id the id of the eFormDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eFormDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-data/{id}")
    public ResponseEntity<IResponseMessage> getEFormData(@PathVariable Long id) {
        log.debug("REST request to get EFormData : {}", id);
        Optional<FormDataDTO> formDataDTO = formDataService.findOne(id);
        if (!formDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(formDataDTO.get()));
    }

    /**
     * {@code DELETE  /form-data/:id} : delete the "id" eFormData.
     *
     * @param id the id of the eFormDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-data/{id}")
    public ResponseEntity<IResponseMessage> deleteEFormData(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        FormData formData = this.formDataRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(formData.getRequestData() != null ? formData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete EFormData : {}", id);
        formDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/form-data?query=:query} : search for the eFormData corresponding
     * to the query.
     *
     * @param query the query of the eFormData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/form-data")
    public ResponseEntity<List<FormDataDTO>> searchEFormData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EFormData for query {}", query);
        Page<FormDataDTO> page = formDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/form-data")
    public ResponseEntity<IResponseMessage> getAll() {
        List<FormDataDTO> result = this.formDataCustomService.getAll();
        log.debug("FormDataCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/form-data")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<FormDataDTO> formDataDTOS) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
       if(formDataDTOS.stream().anyMatch(ele -> {
           return !this.authenticateUtils.checkPermisionForDataOfUser(ele.getRequestData() != null ? ele.getRequestData().getId() : null);
       }))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        List<FormDataDTO> result = this.formDataCustomService.deleteAll(formDataDTOS);
        log.debug("FormDataCustomRest: deleteAll({}) {}", formDataDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/form-data")
    public ResponseEntity<IResponseMessage> getAllByRequestDataId(@PathVariable("requestDataId") Long requestDataId, @RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreFiled) {
        List<FormDataDTO> result = this.formDataCustomService.getAllByReqDataId(requestDataId, ignoreFiled);
        log.debug("FormDataCustomRest: getAllByRequestDataId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PutMapping("/update_data/form_data/{id}")
    public ResponseEntity<IResponseMessage> updateData(@PathVariable(value = "id", required = false) final Long id,
                                                       @Valid @RequestBody FormDataDTO formDataDTO,
                                                       @RequestParam(value = "fillIn", required = false, defaultValue = "true") Boolean fillIn,
                                                       @RequestParam(value = "useTemplate", required = false, defaultValue = "true") Boolean useTemplate){

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(formDataDTO.getRequestData() != null ? formDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        if (formDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(formDataDTO));
        }
        if (!Objects.equals(id, formDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(formDataDTO));
        }

        if (!formDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(formDataDTO));
        }

        Boolean result = this.formDataCustomService.saveDataOfForm(formDataDTO, fillIn, useTemplate);
        if(result)
            return ResponseEntity.ok().body(new LoadedMessage(result));
        else
            return ResponseEntity.ok().body(new FailLoadMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/form_data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FormDataDTO formDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.formDataSearchService.simpleQuerySearch(formDataDTO, pageable);
        log.debug("FormDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", formDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/form_data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FormDataDTO formDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.formDataSearchService.simpleQuerySearchWithParam(formDataDTO, paramMaps, pageable);
        log.debug("FormDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", formDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
