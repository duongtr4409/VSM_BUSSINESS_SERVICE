package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.*;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RequestGroupRepository;
import com.vsm.business.service.RequestGroupService;
import com.vsm.business.service.custom.RequestGroupCustomService;
import com.vsm.business.service.custom.search.service.RequestGroupSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RequestGroupDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.GenerateCodeUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.vsm.business.utils.UserUtils;
import org.elasticsearch.common.Strings;
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

/**
 * REST controller for managing {@link com.vsm.business.domain.RequestGroup}.
 */
@RestController
@RequestMapping("/api")
public class RequestGroupCustomRest {

    private final Logger log = LoggerFactory.getLogger(RequestGroupCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestGroupService requestGroupService;

    private final RequestGroupRepository requestGroupRepository;

    private RequestGroupCustomService requestGroupCustomService;

    private final RequestGroupSearchService requestGroupSearchService;

    private final UserUtils userUtils;

    private final AuthenticateUtils authenticateUtils;

    private Map<String, RequestGroupDTO> requestGroupDTOMap = new HashMap<>();

    private final String ERROR_EXIST_REQUEST = "Không thể xóa nhóm yêu cầu đang hoạt động";

    public RequestGroupCustomRest(RequestGroupService requestGroupService, RequestGroupRepository requestGroupRepository, RequestGroupCustomService requestGroupCustomService, RequestGroupSearchService requestGroupSearchService, UserUtils userUtils, AuthenticateUtils authenticateUtils) {
        this.requestGroupService = requestGroupService;
        this.requestGroupRepository = requestGroupRepository;
        this.requestGroupCustomService = requestGroupCustomService;
        this.requestGroupSearchService = requestGroupSearchService;
        this.userUtils = userUtils;
        this.authenticateUtils = authenticateUtils;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadRequestGroupMap(){
        List<RequestGroupDTO> requestGroupDTOList = this.requestGroupCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setCreated(null);
            ele.setModified(null);
            return ele;
        }).collect(Collectors.toList());
        this.requestGroupDTOMap = new HashMap<>();
        requestGroupDTOList.stream().forEach( ele -> {
                this.requestGroupDTOMap.put(ele.getRequestGroupCode(), ele);
            }
        );
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(RequestGroupDTO requestGroupDTO){
        if(this.requestGroupDTOMap == null || this.requestGroupDTOMap.size() == 0) this.loadRequestGroupMap();
        try {
            String code = generateCodeUtils.generateCode(requestGroupDTO.getRequestGroupName(), this.requestGroupDTOMap, RequestGroupDTO.class, "getRequestGroupCode");
            requestGroupDTO.setRequestGroupCode(code);
            this.requestGroupDTOMap.put(code, requestGroupDTO);

            this.requestGroupDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            log.debug("{}", e.getStackTrace());

            this.requestGroupDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /request-groups} : Create a new requestGroup.
     *
     * @param requestGroupDTO the requestGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestGroupDTO, or with status {@code 400 (Bad Request)} if the requestGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-groups")
    public ResponseEntity<IResponseMessage> createRequestGroup(@Valid @RequestBody RequestGroupDTO requestGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestGroup : {}", requestGroupDTO);
        if (requestGroupDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(requestGroupDTO));
        }

        // generate Code (ngày 14/12/2022: thêm cho phép frontend tạo mã (nếu frontend ko tạo mới tự sinh mã code))
        if(Strings.isNullOrEmpty(requestGroupDTO.getRequestGroupCode())){
            requestGroupDTO.setRequestGroupCode(this.generateCode(requestGroupDTO));
        }

        RequestGroupDTO result = requestGroupService.save(requestGroupDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(requestGroupDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /request-groups/:id} : Updates an existing requestGroup.
     *
     * @param id the id of the requestGroupDTO to save.
     * @param requestGroupDTO the requestGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestGroupDTO,
     * or with status {@code 400 (Bad Request)} if the requestGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-groups/{id}")
    public ResponseEntity<IResponseMessage> updateRequestGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestGroupDTO requestGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestGroup : {}, {}", id, requestGroupDTO);
        if (requestGroupDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestGroupDTO));
        }
        if (!Objects.equals(id, requestGroupDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestGroupDTO));
        }

        if (!requestGroupRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestGroupDTO));
        }

//        RequestGroupDTO result = requestGroupService.save(requestGroupDTO);
        RequestGroupDTO result;
        try{
            result = requestGroupCustomService.customSave(requestGroupDTO);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, requestGroupDTO));
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /request-groups/:id} : Partial updates given fields of an existing requestGroup, field will ignore if it is null
     *
     * @param id the id of the requestGroupDTO to save.
     * @param requestGroupDTO the requestGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestGroupDTO,
     * or with status {@code 400 (Bad Request)} if the requestGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateRequestGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestGroupDTO requestGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestGroup partially : {}, {}", id, requestGroupDTO);
        if (requestGroupDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestGroupDTO));
        }
        if (!Objects.equals(id, requestGroupDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestGroupDTO));
        }

        if (!requestGroupRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestGroupDTO));
        }

        Optional<RequestGroupDTO> result = requestGroupService.partialUpdate(requestGroupDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /request-groups} : get all the requestGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestGroups in body.
     */
    @GetMapping("/request-groups")
    public ResponseEntity<IResponseMessage> getAllRequestGroups(Pageable pageable) {
        log.debug("REST request to get a page of RequestGroups");
        Page<RequestGroupDTO> page = requestGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /request-groups/:id} : get the "id" requestGroup.
     *
     * @param id the id of the requestGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-groups/{id}")
    public ResponseEntity<IResponseMessage> getRequestGroup(@PathVariable Long id) {
        log.debug("REST request to get RequestGroup : {}", id);
        Optional<RequestGroupDTO> requestGroupDTO = requestGroupService.findOne(id);

        if (!requestGroupDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestGroupDTO.get()));
    }

    /**
     * {@code DELETE  /request-groups/:id} : delete the "id" requestGroup.
     *
     * @param id the id of the requestGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-groups/{id}")
    public ResponseEntity<IResponseMessage> deleteRequestGroup(@PathVariable Long id) {
        log.debug("REST request to delete RequestGroup : {}", id);
        if (!requestGroupCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/request-groups?query=:query} : search for the requestGroup corresponding
     * to the query.
     *
     * @param query the query of the requestGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-groups")
    public ResponseEntity<List<RequestGroupDTO>> searchRequestGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestGroups for query {}", query);
        Page<RequestGroupDTO> page = requestGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/request-groups")
    public ResponseEntity<IResponseMessage> getAll() {
        List<RequestGroupDTO> result = this.requestGroupCustomService.getAll();
        log.debug("RequestGroupCustomService: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/user/request-groups")
    public ResponseEntity<IResponseMessage> getAllByRequestGroupByUserIgnoreField(@RequestParam(value = "ignoreField", defaultValue = "false", required = false) Boolean ignoreField){

        if(this.userUtils.getCurrentUser() == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        //List<RequestGroupDTO> result = this.requestGroupCustomService.getAllRequestGroupIgnoreFieldByUser(this.userUtils.getCurrentUser().getId(), ignoreField);
        List<RequestGroupDTO> result = this.requestGroupCustomService.getAllRequestGroupIgnoreFieldByUserV2(this.userUtils.getCurrentUser().getId(), ignoreField);
        log.debug("RequestGroupCustomRest: getAllByRequestGroupByUserIgnoreField(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/request-groups")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<RequestGroupDTO> requestGroupDTOS) {
        List<RequestGroupDTO> result = this.requestGroupCustomService.deleteAll(requestGroupDTOS);
        log.debug("RequestGroupCustomService: deleteAll({}) {}", requestGroupDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

	@PostMapping("/_save/request-groups")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RequestGroupDTO> requestGroupDTOList) {
        log.debug("RequestGroupCustomRest: saveAll({})", requestGroupDTOList);
//        return ResponseEntity.ok().body(new LoadedMessage(requestGroupCustomService.saveAll(requestGroupDTOList)));
        try{
            List<RequestGroupDTO> result = requestGroupCustomService.customSaveAll(requestGroupDTOList);
            return ResponseEntity.ok().body(new LoadedMessage(result));
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, requestGroupDTOList));
        }
    }

    @PostMapping("/check_code/request-groups")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody RequestGroupDTO requestGroupDTO){
        if(this.requestGroupDTOMap.get(this.generateCodeUtils.getCode(requestGroupDTO.getRequestGroupName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(requestGroupDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestGroupDTO));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/request-groups")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RequestGroupDTO requestGroupDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestGroupSearchService.simpleQuerySearch(requestGroupDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", requestGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/request-groups")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RequestGroupDTO requestGroupDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestGroupSearchService.simpleQuerySearchWithParam(requestGroupDTO, paramMaps, pageable);
        log.debug("RequestGroupCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", requestGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
