package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.ProcessData;
import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.service.ProcessDataService;
import com.vsm.business.service.custom.ProcessDataCustomService;
import com.vsm.business.service.custom.search.service.ProcessDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ProcessDataDTO;
import com.vsm.business.utils.AuthenticateUtils;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.vsm.business.domain.ProcessData}.
 */
@RestController
@RequestMapping("/api")
public class ProcessDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(ProcessDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceProcessData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessDataService processDataService;

    private final ProcessDataRepository processDataRepository;

    private final ProcessDataCustomService processDataCustomService;

    private final ProcessDataSearchService processDataSearchService;

    private final AuthenticateUtils authenticateUtils;

    public ProcessDataCustomRest(ProcessDataService processDataService, ProcessDataRepository processDataRepository, ProcessDataCustomService processDataCustomService, ProcessDataSearchService processDataSearchService, AuthenticateUtils authenticateUtils) {
        this.processDataService = processDataService;
        this.processDataRepository = processDataRepository;
        this.processDataCustomService = processDataCustomService;
        this.processDataSearchService = processDataSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /process-data} : Create a new processData.
     *
     * @param processDataDTO the processDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processDataDTO, or with status {@code 400 (Bad Request)} if the processData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-data")
    public ResponseEntity<IResponseMessage> createProcessData(@Valid @RequestBody ProcessDataDTO processDataDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(processDataDTO.getRequestData() != null ? processDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save ProcessData : {}", processDataDTO);
        if (processDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(processDataDTO));
        }
        ProcessDataDTO result = processDataService.save(processDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(processDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /process-data/:id} : Updates an existing processData.
     *
     * @param id the id of the processDataDTO to save.
     * @param processDataDTO the processDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processDataDTO,
     * or with status {@code 400 (Bad Request)} if the processDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-data/{id}")
    public ResponseEntity<IResponseMessage> updateProcessData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcessDataDTO processDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(processDataDTO.getRequestData() != null ? processDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update ProcessData : {}, {}", id, processDataDTO);
        if (processDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(processDataDTO));
        }
        if (!Objects.equals(id, processDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(processDataDTO));
        }

        if (!processDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(processDataDTO));
        }

        ProcessDataDTO result = processDataService.save(processDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(processDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /process-data/:id} : Partial updates given fields of an existing processData, field will ignore if it is null
     *
     * @param id the id of the processDataDTO to save.
     * @param processDataDTO the processDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processDataDTO,
     * or with status {@code 400 (Bad Request)} if the processDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateProcessData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcessDataDTO processDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(processDataDTO.getRequestData() != null ? processDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update ProcessData partially : {}, {}", id, processDataDTO);
        if (processDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(processDataDTO));
        }
        if (!Objects.equals(id, processDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(processDataDTO));
        }

        if (!processDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(processDataDTO));
        }

        Optional<ProcessDataDTO> result = processDataService.partialUpdate(processDataDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(processDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /process-data} : get all the processData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processData in body.
     */
    @GetMapping("/process-data")
    public ResponseEntity<IResponseMessage> getAllProcessData(Pageable pageable) {
        log.debug("REST request to get a page of ProcessData");
        Page<ProcessDataDTO> page = processDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /process-data/:id} : get the "id" processData.
     *
     * @param id the id of the processDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-data/{id}")
    public ResponseEntity<IResponseMessage> getProcessData(@PathVariable Long id) {
        log.debug("REST request to get ProcessData : {}", id);
        Optional<ProcessDataDTO> processDataDTO = processDataService.findOne(id);

        if (!processDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(processDataDTO.get()));
    }

    /**
     * {@code DELETE  /process-data/:id} : delete the "id" processData.
     *
     * @param id the id of the processDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-data/{id}")
    public ResponseEntity<IResponseMessage> deleteProcessData(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        ProcessData processData = this.processDataRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(processData.getRequestData() != null ? processData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete ProcessData : {}", id);
        processDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/process-data?query=:query} : search for the processData corresponding
     * to the query.
     *
     * @param query the query of the processData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/process-data")
    public ResponseEntity<List<ProcessDataDTO>> searchProcessData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProcessData for query {}", query);
        Page<ProcessDataDTO> page = processDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/request-data/{requestDataId}/_all/process-data")
    public ResponseEntity<IResponseMessage> getAllByProcessInfoId(@PathVariable("requestDataId") Long requestDataId) {
        List<ProcessDataDTO> result = this.processDataCustomService.getAllByReqDataId(requestDataId);
        log.debug("ProcessDataCustomRest: getAllByProcessInfoId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/process-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ProcessDataDTO processDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.processDataSearchService.simpleQuerySearch(processDataDTO, pageable);
        log.debug("ProcessDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", processDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/process-data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ProcessDataDTO processDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.processDataSearchService.simpleQuerySearchWithParam(processDataDTO, paramMaps, pageable);
        log.debug("ProcessDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", processDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
