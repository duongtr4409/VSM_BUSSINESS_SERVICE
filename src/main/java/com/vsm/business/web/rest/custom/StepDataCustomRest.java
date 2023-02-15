package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.service.StepDataService;
import com.vsm.business.service.custom.StepDataCustomService;
import com.vsm.business.service.custom.search.service.StepDataSearchService;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.StepData}.
 */
@RestController
@RequestMapping("/api")
public class StepDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(StepDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStepData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepDataService stepDataService;

    private final StepDataRepository stepDataRepository;

    private final StepDataCustomService stepDataCustomService;

    private final StepDataSearchService stepDataSearchService;

    private final AuthenticateUtils authenticateUtils;

    public StepDataCustomRest(StepDataService stepDataService, StepDataRepository stepDataRepository, StepDataCustomService stepDataCustomService, StepDataSearchService stepDataSearchService, AuthenticateUtils authenticateUtils) {
        this.stepDataService = stepDataService;
        this.stepDataRepository = stepDataRepository;
        this.stepDataCustomService = stepDataCustomService;
        this.stepDataSearchService = stepDataSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /step-data} : Create a new stepData.
     *
     * @param stepDataDTO the stepDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepDataDTO, or with status {@code 400 (Bad Request)} if the stepData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-data")
    public ResponseEntity<IResponseMessage> createStepData(@Valid @RequestBody StepDataDTO stepDataDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepDataDTO.getRequestData() != null ? stepDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save StepData : {}", stepDataDTO);
        if (stepDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(stepDataDTO));
        }
        StepDataDTO result = stepDataService.save(stepDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(stepDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(this.ignoreStepData(result)));
    }

    /**
     * {@code PUT  /step-data/:id} : Updates an existing stepData.
     *
     * @param id the id of the stepDataDTO to save.
     * @param stepDataDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-data/{id}")
    public ResponseEntity<IResponseMessage> updateStepData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StepDataDTO stepDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepDataDTO.getRequestData() != null ? stepDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update StepData : {}, {}", id, stepDataDTO);
        if (stepDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepDataDTO));
        }
        if (!Objects.equals(id, stepDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepDataDTO));
        }

        if (!stepDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepDataDTO));
        }

        StepDataDTO result = stepDataService.save(stepDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(this.ignoreStepData(result)));
    }

    /**
     * {@code PATCH  /step-data/:id} : Partial updates given fields of an existing stepData, field will ignore if it is null
     *
     * @param id the id of the stepDataDTO to save.
     * @param stepDataDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateStepData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StepDataDTO stepDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepDataDTO.getRequestData() != null ? stepDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update StepData partially : {}, {}", id, stepDataDTO);
        if (stepDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepDataDTO));
        }
        if (!Objects.equals(id, stepDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepDataDTO));
        }

        if (!stepDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepDataDTO));
        }

        Optional<StepDataDTO> result = stepDataService.partialUpdate(stepDataDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(this.ignoreStepData(result.get())));
    }

    /**
     * {@code GET  /step-data} : get all the stepData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepData in body.
     */
    @GetMapping("/step-data")
    public ResponseEntity<IResponseMessage> getAllStepData(Pageable pageable) {
        log.debug("REST request to get a page of StepData");
        Page<StepDataDTO> page = stepDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /step-data/:id} : get the "id" stepData.
     *
     * @param id the id of the stepDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-data/{id}")
    public ResponseEntity<IResponseMessage> getStepData(@PathVariable Long id) {
        log.debug("REST request to get StepData : {}", id);
        Optional<StepDataDTO> stepDataDTO = stepDataService.findOne(id);

        if (!stepDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        //return ResponseEntity.ok().body(new LoadedMessage(stepDataDTO.get()));

        return ResponseEntity.ok().body(new LoadedMessage(this.ignoreStepData(stepDataDTO.get())));
    }

    /**
     * {@code DELETE  /step-data/:id} : delete the "id" stepData.
     *
     * @param id the id of the stepDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-data/{id}")
    public ResponseEntity<IResponseMessage> deleteStepData(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu
        StepData stepData = this.stepDataRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete StepData : {}", id);
        stepDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/step-data?query=:query} : search for the stepData corresponding
     * to the query.
     *
     * @param query the query of the stepData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/step-data")
    public ResponseEntity<List<StepDataDTO>> searchStepData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StepData for query {}", query);
        Page<StepDataDTO> page = stepDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/request-data/{requestDataId}/_all/step-data")
    public ResponseEntity<IResponseMessage> getAllByRequestDataId(@PathVariable("requestDataId") Long requestDataId) {
        List<StepDataDTO> result = this.stepDataCustomService.getAllByReqDataId(requestDataId);
        log.debug("StepDataCustomRest: getAllByProcessInfoId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/process-data/{processDataId}/_all/step-data")
    public ResponseEntity<IResponseMessage> getAllByProcessDataId(@PathVariable("processDataId") Long processDataId) {
        List<StepDataDTO> result = this.stepDataCustomService.getAllByProcessDataId(processDataId);
        log.debug("StepDataCustomRest: getAllByProcessInfoId({}) {}", processDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/current-steps/{requestDataId}/_all/step-data")
    public ResponseEntity<IResponseMessage> getByCurrentSteps(@PathVariable("requestDataId") Long requestDataId, @RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField){
        List<StepDataDTO> result = this.stepDataCustomService.getCurrentStepData(requestDataId, ignoreField);
        log.debug("StepDataCustomRest: getCurrentStepData({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/step-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody StepDataDTO stepDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepDataSearchService.simpleQuerySearch(stepDataDTO, pageable);
        log.debug("StepDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", stepDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/step-data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody StepDataDTO stepDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepDataSearchService.simpleQuerySearchWithParam(stepDataDTO, paramMaps, pageable);
        log.debug("StepDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", stepDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    // utils \\
    public StepDataDTO ignoreStepData(StepDataDTO stepDataDTO){
        // cắt giảm dữ liệu trả về
        if(stepDataDTO.getProcessData() != null){
            ProcessDataDTO processDataDTO = new ProcessDataDTO();
            processDataDTO.setId(stepDataDTO.getProcessData().getId());
            stepDataDTO.setProcessData(processDataDTO);
        }
        if(stepDataDTO.getRequestData() != null){
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            requestDataDTO.setId(stepDataDTO.getRequestData().getId());
            stepDataDTO.setRequestData(requestDataDTO);
        }
        if(stepDataDTO.getStepInProcess() != null){
            StepInProcessDTO stepInProcessDTO = new StepInProcessDTO();
            stepInProcessDTO.setId(stepDataDTO.getStepInProcess().getId());
            stepDataDTO.setStepInProcess(stepInProcessDTO);
        }
        if(stepDataDTO.getCreated() != null){
            UserInfoDTO created = new UserInfoDTO();
            created.setId(stepDataDTO.getId());
            stepDataDTO.setCreated(created);
        }
        if(stepDataDTO.getModified() != null){
            UserInfoDTO modified = new UserInfoDTO();
            modified.setId(stepDataDTO.getModified().getId());
            stepDataDTO.setModified(modified);
        }
        return stepDataDTO;
    }
}
