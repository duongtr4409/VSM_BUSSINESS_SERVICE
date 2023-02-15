package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.FieldData;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.service.FieldDataService;
import com.vsm.business.service.custom.FieldDataCustomService;
import com.vsm.business.service.custom.search.service.FieldDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldDataDTO;
import com.vsm.business.utils.AuthenticateUtils;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.elasticsearch.common.Strings;
import org.json.JSONArray;
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
 * REST controller for managing {@link com.vsm.business.domain.FieldData}.
 */
@RestController
@RequestMapping("/api")
public class FieldDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(FieldDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFieldData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldDataService fieldDataService;

    private final FieldDataRepository fieldDataRepository;

    private final FieldDataCustomService fieldDataCustomService;

    private final FieldDataSearchService fieldDataSearchService;

    private final AttachmentFileRepository attachmentFileRepository;

    private final AuthenticateUtils authenticateUtils;

    public FieldDataCustomRest(FieldDataService fieldDataService, FieldDataRepository fieldDataRepository, FieldDataCustomService fieldDataCustomService, FieldDataSearchService fieldDataSearchService, AuthenticateUtils authenticateUtils, AttachmentFileRepository attachmentFileRepository) {
        this.fieldDataService = fieldDataService;
        this.fieldDataRepository = fieldDataRepository;
        this.fieldDataCustomService = fieldDataCustomService;
        this.fieldDataSearchService = fieldDataSearchService;
        this.authenticateUtils = authenticateUtils;
        this.attachmentFileRepository = attachmentFileRepository;
    }

    /**
     * {@code POST  /field-data} : Create a new fieldData.
     *
     * @param fieldDataDTO the fieldDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldDataDTO, or with status {@code 400 (Bad Request)} if the fieldData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-data")
    public ResponseEntity<IResponseMessage> createFieldData(@Valid @RequestBody FieldDataDTO fieldDataDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(fieldDataDTO.getRequestData() != null ? fieldDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save FieldData : {}", fieldDataDTO);
        if (fieldDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(fieldDataDTO));
        }
        FieldDataDTO result = fieldDataService.save(fieldDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(fieldDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /field-data/:id} : Updates an existing fieldData.
     *
     * @param id the id of the fieldDataDTO to save.
     * @param fieldDataDTO the fieldDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDataDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-data/{id}")
    public ResponseEntity<IResponseMessage> updateFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldDataDTO fieldDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(fieldDataDTO.getRequestData() != null ? fieldDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update FieldData : {}, {}", id, fieldDataDTO);
        if (fieldDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldDataDTO));
        }
        if (!Objects.equals(id, fieldDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldDataDTO));
        }

        if (!fieldDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldDataDTO));
        }

        FieldDataDTO result = fieldDataService.save(fieldDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fieldDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /field-data/:id} : Partial updates given fields of an existing fieldData, field will ignore if it is null
     *
     * @param id the id of the fieldDataDTO to save.
     * @param fieldDataDTO the fieldDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDataDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldDataDTO fieldDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu hay không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(fieldDataDTO.getRequestData() != null ? fieldDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update FieldData partially : {}, {}", id, fieldDataDTO);
        if (fieldDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldDataDTO));
        }
        if (!Objects.equals(id, fieldDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldDataDTO));
        }

        if (!fieldDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldDataDTO));
        }

        Optional<FieldDataDTO> result = fieldDataService.partialUpdate(fieldDataDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fieldDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /field-data} : get all the fieldData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldData in body.
     */
    @GetMapping("/field-data")
    public ResponseEntity<IResponseMessage> getAllFieldData(Pageable pageable) {
        log.debug("REST request to get a page of FieldData");
        Page<FieldDataDTO> page = fieldDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /field-data/:id} : get the "id" fieldData.
     *
     * @param id the id of the fieldDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-data/{id}")
    public ResponseEntity<IResponseMessage> getFieldData(@PathVariable Long id) {
        log.debug("REST request to get FieldData : {}", id);
        Optional<FieldDataDTO> fieldDataDTO = fieldDataService.findOne(id);
        if (!fieldDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(fieldDataDTO.get()));
    }

    /**
     * {@code DELETE  /field-data/:id} : delete the "id" fieldData.
     *
     * @param id the id of the fieldDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-data/{id}")
    public ResponseEntity<IResponseMessage> deleteFieldData(@PathVariable Long id) {

        // kiểm tra user hiện tại có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        FieldData fieldData = this.fieldDataRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(fieldData.getRequestData() != null ? fieldData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete FieldData : {}", id);
        fieldDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/field-data?query=:query} : search for the fieldData corresponding
     * to the query.
     *
     * @param query the query of the fieldData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/field-data")
    public ResponseEntity<List<FieldDataDTO>> searchFieldData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FieldData for query {}", query);
        Page<FieldDataDTO> page = fieldDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/field-data")
    public ResponseEntity<IResponseMessage> getAll() {
        List<FieldDataDTO> result = this.fieldDataCustomService.getAll();
        log.debug("FieldDataCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/field-data")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<FieldDataDTO> FieldDataDTOS) {

        // kiểm tra user hiện tại có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(FieldDataDTOS.stream().anyMatch(ele -> {
            return !this.authenticateUtils.checkPermisionForDataOfUser(ele.getRequestData() != null ? ele.getRequestData().getId() : null);
        })){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        List<FieldDataDTO> result = this.fieldDataCustomService.deleteAll(FieldDataDTOS);
        log.debug("FieldDataCustomRest: deleteAll({}) {}", FieldDataDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/form-data/{formDataId}/_all/field-data")
    public ResponseEntity<IResponseMessage> findAllByFormDataId(@PathVariable("formDataId") Long formDataId, Pageable pageable,
                                                                @RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField) {
        Map<String, Object> result = this.fieldDataCustomService.getAllByFormDataId(formDataId, pageable, ignoreField);
        log.debug("REST request to findAllByFormDataId({}): {}", formDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/field-data")
    public ResponseEntity<IResponseMessage> getAllByRequestDataId(@PathVariable("requestDataId") Long requestDataId, Pageable pageable,
                                                                  @RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField) {
        Map<String, Object> result = this.fieldDataCustomService.getAllByReqDataId(requestDataId, pageable, ignoreField);
        log.debug("FieldDataCustomRest: getAllByRequestDataId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_create_custom/field-data")
    public ResponseEntity<IResponseMessage> customCreate(@RequestBody List<FieldDataDTO> fieldDataDTOS){

        // kiểm tra user hiện tại có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        Long requestDataId = fieldDataDTOS.get(0).getRequestData().getId();
        if(fieldDataDTOS.stream().anyMatch(ele -> ele.getRequestData() != null && !requestDataId.equals(ele.getRequestData().getId()))
            && !this.authenticateUtils.checkPermisionForDataOfUser(requestDataId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        List<FieldDataDTO> result = this.fieldDataCustomService.customCreate(fieldDataDTOS);
        log.debug("FieldDataCustomRest: customCreate({}): {}", fieldDataDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save_custom/field-data/{requestDataId}")
    public ResponseEntity<IResponseMessage> customSaveAll(@RequestBody List<FieldDataDTO> fieldDataDTOS,
                                                          @PathVariable("requestDataId") Long requestDataId,
                                                          @RequestParam(value = "fillIn", required = false, defaultValue = "true") Boolean fillIn,
                                                          @RequestParam(value = "useTemplate", required = false, defaultValue = "true") Boolean useTemplate){
        // kiểm tra user hiện tại có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(fieldDataDTOS.stream().anyMatch(ele -> ele.getRequestData() != null && !requestDataId.equals(ele.getRequestData().getId()))
            && !this.authenticateUtils.checkPermisionForDataOfUser(requestDataId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        List<FieldDataDTO> result = this.fieldDataCustomService.customSave(fieldDataDTOS, fillIn, useTemplate, requestDataId);
        log.debug("FieldDataCustomRest: customSaveAll({}) {}", fieldDataDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_fill_in/{requestDataId}")
    public ResponseEntity<IResponseMessage> fillInDataToFile(
        @PathVariable("requestDataId") Long requestDataId,
        @RequestParam(value = "attachmentFileId") Long attachmentFileId,
        @RequestParam(value = "useTemplate", required = false, defaultValue = "true") Boolean useTemplate) {

        // kiểm tra xem attachmentFile có thuộc phiếu không
        AttachmentFile attachmentFile = null;
        try {
            attachmentFile = this.attachmentFileRepository.findById(attachmentFileId).get();
            if(!attachmentFile.getRequestData().getId().equals(requestDataId)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        // kiểm tra xem có thông tin mapping hay không \\
        if(attachmentFile != null && attachmentFile.getTemplateForm() != null && !Strings.isNullOrEmpty(attachmentFile.getTemplateForm().getMappingInfo())){
            try {
                JSONArray jsonArray = new JSONArray(attachmentFile.getTemplateForm().getMappingInfo());
            }catch (Exception e){
                log.error("{}", e); // TH không có mapping -> trả về luôn
                return ResponseEntity.ok().body(new LoadedMessage(true));
            }
        }
        this.fieldDataCustomService.fillIntoFile(attachmentFileId, useTemplate, requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/field-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FieldDataDTO fieldDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldDataSearchService.simpleQuerySearch(fieldDataDTO, pageable);
        log.debug("FieldDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", fieldDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/field-data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FieldDataDTO fieldDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldDataSearchService.simpleQuerySearchWithParam(fieldDataDTO, paramMaps, pageable);
        log.debug("FieldDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", fieldDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
