package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.RequestRecall;
import com.vsm.business.repository.RequestRecallRepository;
import com.vsm.business.service.RequestRecallService;
import com.vsm.business.service.custom.RequestRecallCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.RequestRecallSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestRecallDTO;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.RequestRecall}.
 */
@RestController
@RequestMapping("/api")
public class RequestRecallCustomRest {

    private final Logger log = LoggerFactory.getLogger(RequestRecallCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestRecall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestRecallService requestRecallService;

    private final RequestRecallRepository requestRecallRepository;

    private final RequestRecallCustomService requestRecallCustomService;

    private final RequestRecallSearchService requestRecallSearchService;

    private final AuthenticateUtils authenticateUtils;

    public RequestRecallCustomRest(RequestRecallService requestRecallService, RequestRecallRepository requestRecallRepository, RequestRecallCustomService requestRecallCustomService, RequestRecallSearchService requestRecallSearchService, AuthenticateUtils authenticateUtils) {
        this.requestRecallService = requestRecallService;
        this.requestRecallRepository = requestRecallRepository;
        this.requestRecallCustomService = requestRecallCustomService;
        this.requestRecallSearchService = requestRecallSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /request-recalls} : Create a new requestRecall.
     *
     * @param requestRecallDTO the requestRecallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestRecallDTO, or with status {@code 400 (Bad Request)} if the requestRecall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-recalls")
    public ResponseEntity<IResponseMessage> createRequestRecall(@RequestBody RequestRecallDTO requestRecallDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestRecallDTO.getRequestData() != null ? requestRecallDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save RequestRecall : {}", requestRecallDTO);
        if (requestRecallDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestRecall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestRecallDTO result = requestRecallService.save(requestRecallDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(requestRecallDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /request-recalls/:id} : Updates an existing requestRecall.
     *
     * @param id the id of the requestRecallDTO to save.
     * @param requestRecallDTO the requestRecallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestRecallDTO,
     * or with status {@code 400 (Bad Request)} if the requestRecallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestRecallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-recalls/{id}")
    public ResponseEntity<IResponseMessage> updateRequestRecall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestRecallDTO requestRecallDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestRecallDTO.getRequestData() != null ? requestRecallDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update RequestRecall : {}, {}", id, requestRecallDTO);
        if (requestRecallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestRecallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRecallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestRecallDTO result = requestRecallService.save(requestRecallDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(requestRecallDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /request-recalls/:id} : Partial updates given fields of an existing requestRecall, field will ignore if it is null
     *
     * @param id the id of the requestRecallDTO to save.
     * @param requestRecallDTO the requestRecallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestRecallDTO,
     * or with status {@code 400 (Bad Request)} if the requestRecallDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestRecallDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestRecallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-recalls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateRequestRecall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestRecallDTO requestRecallDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestRecallDTO.getRequestData() != null ? requestRecallDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update RequestRecall partially : {}, {}", id, requestRecallDTO);
        if (requestRecallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestRecallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRecallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestRecallDTO> result = requestRecallService.partialUpdate(requestRecallDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(requestRecallDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /request-recalls} : get all the requestRecalls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestRecalls in body.
     */
    @GetMapping("/request-recalls")
    public ResponseEntity<IResponseMessage> getAllRequestRecalls(Pageable pageable) {
        log.debug("REST request to get a page of RequestRecalls");
        Page<RequestRecallDTO> page = requestRecallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new FailLoadMessage(page.getContent()));
    }

    /**
     * {@code GET  /request-recalls/:id} : get the "id" requestRecall.
     *
     * @param id the id of the requestRecallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestRecallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-recalls/{id}")
    public ResponseEntity<IResponseMessage> getRequestRecall(@PathVariable Long id) {
        log.debug("REST request to get RequestRecall : {}", id);
        Optional<RequestRecallDTO> requestRecallDTO = requestRecallService.findOne(id);
        if(!requestRecallDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestRecallDTO.get()));
    }

    /**
     * {@code DELETE  /request-recalls/:id} : delete the "id" requestRecall.
     *
     * @param id the id of the requestRecallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-recalls/{id}")
    public ResponseEntity<IResponseMessage> deleteRequestRecall(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        RequestRecall requestRecall = this.requestRecallRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestRecall.getRequestData() != null ? requestRecall.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete RequestRecall : {}", id);
        requestRecallService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));    }

    /**
     * {@code SEARCH  /_search/request-recalls?query=:query} : search for the requestRecall corresponding
     * to the query.
     *
     * @param query the query of the requestRecall search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-recalls")
    public ResponseEntity<List<RequestRecallDTO>> searchRequestRecalls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestRecalls for query {}", query);
        Page<RequestRecallDTO> page = requestRecallService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/request-data/{requestDataId}/_all/request-recalls")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        List<RequestRecallDTO> result = this.requestRecallCustomService.getByRequestData(requestDataId);
        log.info("RequestRecallCustomRest: getAllByRequestData({})", requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/request-recalls")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RequestRecallDTO requestRecallDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestRecallSearchService.simpleQuerySearch(requestRecallDTO, pageable);
        log.debug("RequestRecallCustomRest: customSearch(IBaseSearchDTO: {}): {}", requestRecallDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/request-recalls")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RequestRecallDTO requestRecallDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestRecallSearchService.simpleQuerySearchWithParam(requestRecallDTO, paramMaps, pageable);
        log.debug("RequestRecallCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", requestRecallDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
