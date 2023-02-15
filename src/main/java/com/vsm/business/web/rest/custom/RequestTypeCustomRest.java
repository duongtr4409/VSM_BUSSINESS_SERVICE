package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.*;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.service.RequestTypeService;
import com.vsm.business.service.custom.RequestTypeCustomService;
import com.vsm.business.service.custom.search.service.RequestTypeSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestTypeDTO;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.vsm.business.utils.GenerateCodeUtils;
import joptsimple.internal.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.RequestType}.
 */
@RestController
@RequestMapping("/api")
public class RequestTypeCustomRest {

    private final Logger log = LoggerFactory.getLogger(RequestTypeCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestTypeService requestTypeService;

    private final RequestTypeRepository requestTypeRepository;

    private RequestTypeCustomService requestTypeCustomService;

    private RequestTypeSearchService requestTypeSearchService;

    private Map<String, RequestTypeDTO> requestTypeDTOMap = new HashMap<>();

    private final String ERROR_EXIST_REQUEST = "Không thể xóa loại yêu cầu đang hoạt động";

    public RequestTypeCustomRest(RequestTypeService requestTypeService, RequestTypeRepository requestTypeRepository, RequestTypeCustomService requestTypeCustomService, RequestTypeSearchService requestTypeSearchService) {
        this.requestTypeService = requestTypeService;
        this.requestTypeRepository = requestTypeRepository;
        this.requestTypeCustomService = requestTypeCustomService;
        this.requestTypeSearchService = requestTypeSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadRequestGroupMap(){
        List<RequestTypeDTO> requestGroupDTOList = this.requestTypeCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setCreated(null);
            ele.setModified(null);
            ele.setRequestGroup(null);
            return ele;
        }).collect(Collectors.toList());
        this.requestTypeDTOMap = new HashMap<>();
        requestGroupDTOList.stream().forEach( ele -> {
                this.requestTypeDTOMap.put(ele.getRequestTypeCode(), ele);
            }
        );
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(RequestTypeDTO requestTypeDTO){
        if(this.requestTypeDTOMap == null || this.requestTypeDTOMap.size() == 0) this.loadRequestGroupMap();
        try {
            String code = generateCodeUtils.generateCode(requestTypeDTO.getRequestTypeName(), this.requestTypeDTOMap, RequestTypeDTO.class, "getRequestTypeCode");
            requestTypeDTO.setRequestTypeCode(code);
            this.requestTypeDTOMap.put(code, requestTypeDTO);

            this.requestTypeDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.requestTypeDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /request-types} : Create a new requestType.
     *
     * @param requestTypeDTO the requestTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestTypeDTO, or with status {@code 400 (Bad Request)} if the requestType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-types")
    public ResponseEntity<IResponseMessage> createRequestType(@Valid @RequestBody RequestTypeDTO requestTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RequestType : {}", requestTypeDTO);
        if (requestTypeDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(requestTypeDTO));
        }

        // generate Code (ngày 14/12/2022: thêm cho phép frontend tạo mã (nếu frontend ko tạo mới tự sinh mã code))
        if(Strings.isNullOrEmpty(requestTypeDTO.getRequestTypeCode())){
            requestTypeDTO.setRequestTypeCode(this.generateCode(requestTypeDTO));
        }

        RequestTypeDTO result = requestTypeService.save(requestTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(requestTypeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /request-types/:id} : Updates an existing requestType.
     *
     * @param id             the id of the requestTypeDTO to save.
     * @param requestTypeDTO the requestTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-types/{id}")
    public ResponseEntity<IResponseMessage> updateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestTypeDTO requestTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestType : {}, {}", id, requestTypeDTO);
        if (requestTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestTypeDTO));
        }
        if (!Objects.equals(id, requestTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestTypeDTO));
        }

        if (!requestTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestTypeDTO));
        }

        //RequestTypeDTO result = requestTypeService.save(requestTypeDTO);
        RequestTypeDTO result;
        try {
            result = requestTypeCustomService.customSave(requestTypeDTO);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, requestTypeDTO));
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /request-types/:id} : Partial updates given fields of an existing requestType, field will ignore if it is null
     *
     * @param id             the id of the requestTypeDTO to save.
     * @param requestTypeDTO the requestTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypeDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-types/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateRequestType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestTypeDTO requestTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestType partially : {}, {}", id, requestTypeDTO);
        if (requestTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestTypeDTO));
        }
        if (!Objects.equals(id, requestTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestTypeDTO));

        }

        if (!requestTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestTypeDTO));
        }

        Optional<RequestTypeDTO> result = requestTypeService.partialUpdate(requestTypeDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /request-types} : get all the requestTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestTypes in body.
     */
    @GetMapping("/request-types")
    public ResponseEntity<IResponseMessage> getAllRequestTypes(Pageable pageable) {
        log.debug("REST request to get a page of RequestTypes");
        Page<RequestTypeDTO> page = requestTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /request-types/:id} : get the "id" requestType.
     *
     * @param id the id of the requestTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-types/{id}")
    public ResponseEntity<IResponseMessage> getRequestType(@PathVariable Long id) {
        log.debug("REST request to get RequestType : {}", id);
        Optional<RequestTypeDTO> requestTypeDTO = requestTypeService.findOne(id);
        if (!requestTypeDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestTypeDTO.get()));
    }

    /**
     * {@code DELETE  /request-types/:id} : delete the "id" requestType.
     *
     * @param id the id of the requestTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-types/{id}")
    public ResponseEntity<IResponseMessage> deleteRequestType(@PathVariable Long id) {
        log.debug("REST request to delete RequestType : {}", id);
        if (!requestTypeCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/request-types?query=:query} : search for the requestType corresponding
     * to the query.
     *
     * @param query    the query of the requestType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-types")
    public ResponseEntity<List<RequestTypeDTO>> searchRequestTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestTypes for query {}", query);
        Page<RequestTypeDTO> page = requestTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/request-types")
    public ResponseEntity<IResponseMessage> getAll() {
        List<RequestTypeDTO> result = this.requestTypeCustomService.getAll();
        log.debug("StepCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/request-types")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<RequestTypeDTO> requestTypeDTOS) {
        List<RequestTypeDTO> result = this.requestTypeCustomService.deleteAll(requestTypeDTOS);
        log.debug("StepCustomRest: deleteAll({}) {}", requestTypeDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

	@PostMapping("/_save/request-types")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RequestTypeDTO> requestTypeDTOList){
        log.error("StepCustomRest: saveAll({})", requestTypeDTOList);
        try {
            List<RequestTypeDTO> result = requestTypeCustomService.customSaveAll(requestTypeDTOList);
            return ResponseEntity.ok().body(new LoadedMessage(result));
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, requestTypeDTOList));
        }
    }

    @PostMapping("/check_code/request-types")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody RequestTypeDTO requestTypeDTO){
        if(this.requestTypeDTOMap.get(this.generateCodeUtils.getCode(requestTypeDTO.getRequestTypeName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(requestTypeDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestTypeDTO));
    }

    @GetMapping("/request_group/{requestGroupId}/_all/request-types")
    public ResponseEntity<IResponseMessage> getAllByRequestGroupId(@PathVariable("requestGroupId") Long requestGroupId){
        List<RequestTypeDTO> result = this.requestTypeCustomService.getAllByRequestGroupId(requestGroupId);
        log.info("RequestTypeCustomRest: getAllByRequestGroupId({}): {}", requestGroupId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/check_exist_request/{requestTypeId}/request-types")
    public ResponseEntity<IResponseMessage> checkHasRequest(@PathVariable("requestTypeId") Long requestTypeId){
        RequestDTO result = this.requestTypeCustomService.checkExistRequest(requestTypeId);
        log.info("RequestTypeCustomRest: getAllByRequestGroupId({})", requestTypeId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/request-types")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RequestTypeDTO requestTypeDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestTypeSearchService.simpleQuerySearch(requestTypeDTO, pageable);
        log.debug("RequestTypeCustomRest: customSearch(IBaseSearchDTO: {}): {}", requestTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/request-types")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RequestTypeDTO requestTypeDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestTypeSearchService.simpleQuerySearchWithParam(requestTypeDTO, paramMaps, pageable);
        log.debug("RequestTypeCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", requestTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
