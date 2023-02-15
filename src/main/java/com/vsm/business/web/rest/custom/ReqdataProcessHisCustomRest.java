package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.ReqdataProcessHis;
import com.vsm.business.repository.ReqdataProcessHisRepository;
import com.vsm.business.service.ReqdataProcessHisService;
import com.vsm.business.service.custom.ReqdataProcessHisCustomService;
import com.vsm.business.service.custom.search.service.ReqdataProcessHisSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * REST controller for managing {@link com.vsm.business.domain.ReqdataProcessHis}.
 */
@RestController
@RequestMapping("/api")
public class ReqdataProcessHisCustomRest {

    private final Logger log = LoggerFactory.getLogger(ReqdataProcessHisCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceReqdataProcessHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqdataProcessHisService reqdataProcessHisService;

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;

    private final ReqdataProcessHisCustomService reqdataProcessHisCustomService;

    private final ReqdataProcessHisSearchService reqdataProcessHisSearchService;

    private final AuthenticateUtils authenticateUtils;

    public ReqdataProcessHisCustomRest(
        ReqdataProcessHisService reqdataProcessHisService,
        ReqdataProcessHisRepository reqdataProcessHisRepository,
        ReqdataProcessHisCustomService reqdataProcessHisCustomService,
        ReqdataProcessHisSearchService reqdataProcessHisSearchService,
        AuthenticateUtils authenticateUtils
    ) {
        this.reqdataProcessHisService = reqdataProcessHisService;
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
        this.reqdataProcessHisCustomService = reqdataProcessHisCustomService;
        this.reqdataProcessHisSearchService = reqdataProcessHisSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /reqdata-process-his} : Create a new reqdataProcessHis.
     *
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqdataProcessHisDTO, or with status {@code 400 (Bad Request)} if the reqdataProcessHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reqdata-process-his")
    public ResponseEntity<IResponseMessage> createReqdataProcessHis(@Valid @RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO)
        throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(reqdataProcessHisDTO.getRequestData() != null ? reqdataProcessHisDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save ReqdataProcessHis : {}", reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(reqdataProcessHisDTO));
        }
        ReqdataProcessHisDTO result = reqdataProcessHisService.save(reqdataProcessHisDTO);

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(reqdataProcessHisDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /reqdata-process-his/:id} : Updates an existing reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to save.
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataProcessHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataProcessHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqdataProcessHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reqdata-process-his/{id}")
    public ResponseEntity<IResponseMessage> updateReqdataProcessHis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(reqdataProcessHisDTO.getRequestData() != null ? reqdataProcessHisDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update ReqdataProcessHis : {}, {}", id, reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(reqdataProcessHisDTO));
        }
        if (!Objects.equals(id, reqdataProcessHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(reqdataProcessHisDTO));
        }

        if (!reqdataProcessHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(reqdataProcessHisDTO));
        }

        ReqdataProcessHisDTO result = reqdataProcessHisService.save(reqdataProcessHisDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(reqdataProcessHisDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /reqdata-process-his/:id} : Partial updates given fields of an existing reqdataProcessHis, field will ignore if it is null
     *
     * @param id the id of the reqdataProcessHisDTO to save.
     * @param reqdataProcessHisDTO the reqdataProcessHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataProcessHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataProcessHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reqdataProcessHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reqdataProcessHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reqdata-process-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateReqdataProcessHis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(reqdataProcessHisDTO.getRequestData() != null ? reqdataProcessHisDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update ReqdataProcessHis partially : {}, {}", id, reqdataProcessHisDTO);
        if (reqdataProcessHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(reqdataProcessHisDTO));
        }
        if (!Objects.equals(id, reqdataProcessHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(reqdataProcessHisDTO));
        }

        if (!reqdataProcessHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(reqdataProcessHisDTO));
        }

        Optional<ReqdataProcessHisDTO> result = reqdataProcessHisService.partialUpdate(reqdataProcessHisDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(reqdataProcessHisDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /reqdata-process-his} : get all the reqdataProcessHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqdataProcessHis in body.
     */
    @GetMapping("/reqdata-process-his")
    public ResponseEntity<IResponseMessage> getAllReqdataProcessHis(Pageable pageable) {
        log.debug("REST request to get a page of ReqdataProcessHis");
        Page<ReqdataProcessHisDTO> page = reqdataProcessHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /reqdata-process-his/:id} : get the "id" reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqdataProcessHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reqdata-process-his/{id}")
    public ResponseEntity<IResponseMessage> getReqdataProcessHis(@PathVariable Long id) {
        log.debug("REST request to get ReqdataProcessHis : {}", id);
        Optional<ReqdataProcessHisDTO> reqdataProcessHisDTO = reqdataProcessHisService.findOne(id);

        if (!reqdataProcessHisDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(reqdataProcessHisDTO.get()));
    }

    /**
     * {@code DELETE  /reqdata-process-his/:id} : delete the "id" reqdataProcessHis.
     *
     * @param id the id of the reqdataProcessHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reqdata-process-his/{id}")
    public ResponseEntity<IResponseMessage> deleteReqdataProcessHis(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        ReqdataProcessHis reqdataProcessHis = this.reqdataProcessHisRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(reqdataProcessHis.getRequestData() != null ? reqdataProcessHis.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete ReqdataProcessHis : {}", id);
        reqdataProcessHisService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/reqdata-process-his?query=:query} : search for the reqdataProcessHis corresponding
     * to the query.
     *
     * @param query the query of the reqdataProcessHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reqdata-process-his")
    public ResponseEntity<List<ReqdataProcessHisDTO>> searchReqdataProcessHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReqdataProcessHis for query {}", query);
        Page<ReqdataProcessHisDTO> page = reqdataProcessHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/request-data/{requestDataId}/_all/reqdata-process-his")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        log.debug("Rest ReqdataProcessHisCustomRest({}): ", requestDataId);
        List<ReqdataProcessHisDTO> result = this.reqdataProcessHisCustomService.getAllByRequestData(requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/reqdata-process-his")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.reqdataProcessHisSearchService.simpleQuerySearch(reqdataProcessHisDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", reqdataProcessHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/reqdata-process-his")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ReqdataProcessHisDTO reqdataProcessHisDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.reqdataProcessHisSearchService.simpleQuerySearchWithParam(reqdataProcessHisDTO, paramMaps, pageable);
        log.debug("ReqdataProcessHisCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", reqdataProcessHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
