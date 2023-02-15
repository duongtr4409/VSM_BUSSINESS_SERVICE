package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.service.RequestService;
import com.vsm.business.service.custom.RequestCustomService;
import com.vsm.business.service.custom.search.service.RequestSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.GenerateAttachNameUtils;
import com.vsm.business.utils.GenerateCodeUtils;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.vsm.business.domain.Request}.
 */
@RestController
@RequestMapping("/api")
public class RequestCustomRest {

    private final Logger log = LoggerFactory.getLogger(RequestCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;

    private final RequestRepository requestRepository;

    private final RequestCustomService requestCustomService;

    private final RequestSearchService requestSearchService;

    private final UserUtils userUtils;

    private final AuthenticateUtils authenticateUtils;

    private Map<String, RequestDTO> requestDTOMap = new HashMap<>();

    public RequestCustomRest(RequestService requestService, RequestRepository requestRepository, RequestCustomService requestCustomService, RequestSearchService requestSearchService, UserUtils userUtils, AuthenticateUtils authenticateUtils) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.requestCustomService = requestCustomService;
        this.requestSearchService = requestSearchService;
        this.userUtils = userUtils;
        this.authenticateUtils = authenticateUtils;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadRequestMap(){
        List<RequestDTO> requestDTOList = this.requestCustomService.findAllIgnoreField().stream().map(ele -> {
            ele.setRequestType(null);
            ele.setRequestGroup(null);
            ele.setTemplateForms(new HashSet<>());
            ele.setProcessInfos(new HashSet<>());
            ele.setModified(null);
            ele.setCreated(null);
            return ele;
        }).collect(Collectors.toList());
        this.requestDTOMap = new HashMap<>();
        requestDTOList.stream().forEach(ele ->{
            this.requestDTOMap.put(ele.getRequestCode(), ele);
        });
    }

    @Autowired
    private GenerateAttachNameUtils generateAttachNameUtils;
    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(RequestDTO requestDTO){
        if(this.requestDTOMap == null || this.requestDTOMap.size() == 0) this.loadRequestMap();
        try {
            String code = this.generateCodeUtils.generateCode(requestDTO.getRequestName(), this.requestDTOMap, RequestDTO.class, "getRequestCode");
            requestDTO.setRequestCode(code);
            this.requestDTOMap.put(code, requestDTO);

            this.requestDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("{}", e);

            this.requestDTOMap = null;

            return null;
        }

    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param requestDTO the requestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDTO, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requests")
    public ResponseEntity<IResponseMessage> createRequest(@Valid @RequestBody RequestDTO requestDTO) throws URISyntaxException, IllegalAccessException {
        log.debug("REST request to save Request : {}", requestDTO);
        if (requestDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(requestDTO));
        }
        //RequestDTO result = requestService.save(requestDTO);
        RequestDTO result = this.requestCustomService.customSave(requestDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(requestDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /requests/:id} : Updates an existing request.
     *
     * @param id         the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requests/{id}")
    public ResponseEntity<IResponseMessage> updateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestDTO));
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestDTO));
        }

        if (!requestRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestDTO));
        }

        RequestDTO result = requestService.save(requestDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /requests/:id} : Partial updates given fields of an existing request, field will ignore if it is null
     *
     * @param id         the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requests/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Request partially : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestDTO));
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestDTO));
        }

        if (!requestRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestDTO));
        }

        Optional<RequestDTO> result = requestService.partialUpdate(requestDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("/requests")
    public ResponseEntity<IResponseMessage> getAllRequests(
        Pageable pageable
    ) {
        log.debug("REST request to get a page of Requests");
        Page<RequestDTO> page;

        page = requestService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the requestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<IResponseMessage> getRequest(@PathVariable Long id, @RequestParam(value = "ignoreField", required = false, defaultValue = "false") boolean ignoreField) {
        log.debug("REST request to get Request : {}", id);
        //Optional<RequestDTO> requestDTO = requestService.findOne(id);
        // sửa để giảm bớt dữ liệu theo param truyền lên
        Optional<RequestDTO> requestDTO = this.requestCustomService.customFindOne(id, ignoreField);

        if (!requestDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestDTO.get()));
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the requestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<IResponseMessage> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/requests?query=:query} : search for the request corresponding
     * to the query.
     *
     * @param query    the query of the request search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/requests")
    public ResponseEntity<List<RequestDTO>> searchRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Requests for query {}", query);
        Page<RequestDTO> page = requestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/_save/requests")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RequestDTO> requestDTOList) {
        log.debug("Request to RequestCustomRest saveAll({})", requestDTOList);
        return ResponseEntity.ok().body(new UpdatedMessage(requestCustomService.saveAll(requestDTOList)));
    }

    @GetMapping("/_all/requests")
    public ResponseEntity<IResponseMessage> findAll(@RequestParam(value = "ignoreField", defaultValue = "true") Boolean ignoreField) {
        log.debug("Request to RequestCustomRest findAll()");
        if(ignoreField == null || !ignoreField) ignoreField = true;
        return ResponseEntity.ok().body(new LoadedMessage(requestCustomService.findAll(ignoreField)));
    }

    @GetMapping("request-types/{requestTypeId}/requests")
    public ResponseEntity<IResponseMessage> getAllByRequestTypeId(@PathVariable("requestTypeId") Long requestTypeId){
        List<RequestDTO> result = requestCustomService.findAllByRequestTypeId(requestTypeId);
        log.debug("Request to RequestCustomRest getAllByRequestTypeId({}): {}", requestTypeId, result);
        return ResponseEntity.ok().body(new LoadedMessage(requestCustomService.findAllByRequestTypeId(requestTypeId)));
    }

    @PostMapping("/check_code/requests")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody RequestDTO requestDTO){
        if(this.requestDTOMap.get(this.generateCodeUtils.getCode(requestDTO.getRequestName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(requestDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestDTO));
    }


    @GetMapping("/_all/generate-code-option")
    public ResponseEntity<IResponseMessage> getAllGenerateCodeOption(){
        return ResponseEntity.ok().body(new LoadedMessage(this.generateCodeUtils.getAllGenerateCodeOption()));
    }

    @GetMapping("/_all/generate-attach-name-option")
    public ResponseEntity<IResponseMessage> getAllGenerateAttachNameOption(){
        return ResponseEntity.ok().body(new LoadedMessage(this.generateAttachNameUtils.getAllGenerateAttachNameOption()));
    }


    @GetMapping("/_all_ignorefile/requests")
    public ResponseEntity<IResponseMessage> getAllIgnoreField(){
        log.debug("Request to RequestCustomRest getAllIgnoreField()");
        List<RequestDTO> result = this.requestCustomService.findAllIgnoreField();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all_ignorefile_form/requests")
    public ResponseEntity<IResponseMessage> getAllWithForm(){
        List<RequestDTO> result = this.requestCustomService.findAllIgnoreFieldWithForm();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/request_group/{requestGroupId}/requests")
    public ResponseEntity<IResponseMessage> getAllByRequestGroupIgnoreField(@PathVariable("requestGroupId") Long requestGroupId, @RequestParam(value = "ignoreField", defaultValue = "false", required = false) Boolean ignoreField){

        List<RequestDTO> result = this.requestCustomService.findAllByRequestGroupIgnoreField(requestGroupId, ignoreField);
        log.debug("Request to RequestCustomRest getAllByRequestGroupIgnoreField(requestGroupId: {}): {}" , requestGroupId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/request_group/{requestGroupId}/user/requests")
    public ResponseEntity<IResponseMessage> getAllByRequestGroupByUserIgnoreField(@PathVariable("requestGroupId") Long requestGroupId, @RequestParam(value = "ignoreField", defaultValue = "false", required = false) Boolean ignoreField){

        if(this.userUtils.getCurrentUser() == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        List<RequestDTO> result = this.requestCustomService.findAllByRequestGroupIgnoreFieldByUser(requestGroupId, ignoreField, this.userUtils.getCurrentUser().getId());
        log.debug("Request to RequestCustomRest getAllByRequestGroupIgnoreField(requestGroupId: {}): {}" , requestGroupId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/request_group/with-role/{requestGroupId}/requests")
    public ResponseEntity<IResponseMessage> getAllByRequestGroupIgnoreFieldWithRole(@PathVariable("requestGroupId") Long requestGroupId, @RequestParam(value = "ignoreField", defaultValue = "false", required = false) Boolean ignoreField){

        List<RequestDTO> result = this.requestCustomService.findAllByRequestGroupIgnoreFieldWithRole(requestGroupId, ignoreField);
        log.debug("Request to RequestCustomRest getAllByRequestGroupIgnoreField(requestGroupId: {}): {}" , requestGroupId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/requests/{requestId}/_all/generate-code-option")
    public ResponseEntity<IResponseMessage> v2_getAllGenerateCodeOption(@PathVariable("requestId") Long requestId){
        return ResponseEntity.ok().body(new LoadedMessage(this.generateCodeUtils.v2_getAllGenerateCodeOption(requestId)));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/requests")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RequestDTO requestDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestSearchService.simpleQuerySearch(requestDTO, pageable);
        log.debug("RequestCustomRest: customSearch(IBaseSearchDTO: {}): {}", requestDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/requests")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RequestDTO requestDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestSearchService.simpleQuerySearchWithParam(requestDTO, paramMaps, pageable);
        log.debug("RequestRest: customSearchWithParam(IBaseSearchDTO: {}): {}", requestDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
