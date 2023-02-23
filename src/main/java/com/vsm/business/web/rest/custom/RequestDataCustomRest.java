package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.specification.bo.ConditionQuery;
import com.vsm.business.service.RequestDataService;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.RequestDataCustomService;
import com.vsm.business.service.custom.search.service.RequestDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.dto.RequestDataDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.GenerateCodeUtils;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.RequestData}.
 */
@RestController
@RequestMapping("/api")
public class RequestDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(RequestDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${role-request.check-role:TRUE}")
    public String CHECK_ROLE;

    private final RequestDataService requestDataService;

    private final RequestDataRepository requestDataRepository;

    private final RequestDataCustomService requestDataCustomService;

    private final GenerateCodeUtils generateCodeUtils;

    private final RequestDataSearchService requestDataSearchService;

    private final AuthenticateUtils authenticateUtils;

    private final UserUtils userUtils;

    public RequestDataCustomRest(RequestDataService requestDataService, RequestDataRepository requestDataRepository, RequestDataCustomService requestDataCustomService, GenerateCodeUtils generateCodeUtils, RequestDataSearchService requestDataSearchService, AuthenticateUtils authenticateUtils, UserUtils userUtils) {
        this.requestDataService = requestDataService;
        this.requestDataRepository = requestDataRepository;
        this.requestDataCustomService = requestDataCustomService;
        this.generateCodeUtils = generateCodeUtils;
        this.requestDataSearchService = requestDataSearchService;
        this.authenticateUtils = authenticateUtils;
        this.userUtils = userUtils;
    }

    /**
     * {@code POST  /request-data} : Create a new requestData.
     *
     * @param requestDataDTO the requestDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDataDTO, or with status {@code 400 (Bad Request)} if the requestData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-data")
    public ResponseEntity<IResponseMessage> createRequestData(@Valid @RequestBody RequestDataDTO requestDataDTO) throws Exception {

        // kiểm tra user tạo phiếu có đúng không
        if(!this.checkPermisionChangeRequestData(requestDataDTO.getCreated().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }
        // kiểm tra user tạo phiếu có đúng không
        if(requestDataDTO.getModified() != null && requestDataDTO.getModified().getId() != null){
            if(!this.checkPermisionChangeRequestData(requestDataDTO.getModified().getId())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
            }
        }

        log.debug("REST request to save RequestData : {}", requestDataDTO);
        if (requestDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(requestDataDTO));
        }
//            // generate code
//        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
//        RequestDataDTO result = requestDataService.save(requestDataDTO);
        RequestDataDTO result = null;
        if(requestDataDTO.getAttachmentFileList() != null && requestDataDTO.getAttachmentFileList().size() > 1){        // nếu có nhiều hơn 1 file -> chạy đa luồng
            result = requestDataCustomService.customSave_v3(requestDataDTO);
        }else{
            result = requestDataCustomService.customSave_v2(requestDataDTO);
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(requestDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /request-data/:id} : Updates an existing requestData.
     *
     * @param id the id of the requestDataDTO to save.
     * @param requestDataDTO the requestDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDataDTO,
     * or with status {@code 400 (Bad Request)} if the requestDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-data/{requestDataId}")
    public ResponseEntity<IResponseMessage> updateRequestData(
        @PathVariable(value = "requestDataId", required = false) final Long id,
        @Valid @RequestBody RequestDataDTO requestDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update RequestData : {}, {}", id, requestDataDTO);
        if (requestDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestDataDTO));
        }
        if (!Objects.equals(id, requestDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestDataDTO));
        }

        if (!requestDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestDataDTO));
        }

        //RequestDataDTO result = requestDataService.save(requestDataDTO);
        RequestDataDTO result = requestDataCustomService.customUpdate(requestDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /request-data/:id} : Partial updates given fields of an existing requestData, field will ignore if it is null
     *
     * @param id the id of the requestDataDTO to save.
     * @param requestDataDTO the requestDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDataDTO,
     * or with status {@code 400 (Bad Request)} if the requestDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-data/{requestDataId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateRequestData(
        @PathVariable(value = "requestDataId", required = false) final Long id,
        @NotNull @RequestBody RequestDataDTO requestDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu phiếu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update RequestData partially : {}, {}", id, requestDataDTO);
        if (requestDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestDataDTO));
        }
        if (!Objects.equals(id, requestDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestDataDTO));
        }

        if (!requestDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestDataDTO));
        }

        Optional<RequestDataDTO> result = requestDataService.partialUpdate(requestDataDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /request-data} : get all the requestData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestData in body.
     */
    @GetMapping("/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestData(Pageable pageable) {
        log.debug("REST request to get a page of RequestData");
        Page<RequestDataDTO> page = requestDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /request-data/:id} : get the "id" requestData.
     *
     * @param id the id of the requestDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-data/{requestDataId}")
    public ResponseEntity<IResponseMessage> getRequestData(@PathVariable("requestDataId") Long id) {
        log.debug("REST request to get RequestData : {}", id);
//        Optional<RequestDataDTO> requestDataDTO = requestDataService.findOne(id);
        Optional<RequestDataDTO> requestDataDTO = requestDataCustomService.customFindOne(id);
        if (!requestDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestDataDTO.get()));
    }

    /**
     * {@code DELETE  /request-data/:id} : delete the "id" requestData.
     *
     * @param id the id of the requestDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-data/{requestDataId}")
    public ResponseEntity<IResponseMessage> deleteRequestData(@PathVariable("requestDataId") Long id) {
        log.debug("REST request to delete RequestData : {}", id);
//        requestDataService.delete(id);
        requestDataCustomService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/request-data?query=:query} : search for the requestData corresponding
     * to the query.
     *
     * @param query the query of the requestData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/request-data")
    public ResponseEntity<List<RequestDataDTO>> searchRequestData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequestData for query {}", query);
        Page<RequestDataDTO> page = requestDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/request-data")
    public ResponseEntity<IResponseMessage> findAll(){
        log.debug("REST requestDataCustomRest to findAll()");
        List<RequestDataDTO> result = requestDataCustomService.findAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/request-data")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<RequestDataDTO> requestDataDTOList){
        requestDataCustomService.deleteAll(requestDataDTOList);
        return ResponseEntity.ok().body(new DeletedMessage(requestDataDTOList.stream().map(ele -> ele.getId()).collect(Collectors.toList())));
    }

    @GetMapping("/user/{userId}/_all/request-data")
    public ResponseEntity<IResponseMessage> getAllByUserId(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllByUserId(userId);
        log.debug("REST request to getAllByUserId({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v1/user/{userId}/_all/request-data")
    public ResponseEntity<IResponseMessage> getAllByUserId_v1(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllByUserId_v1(userId);
        log.debug("REST request to getAllByUserId_v1({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v1/user/{userId}/_count/request-data")
    public ResponseEntity<IResponseMessage> countAllByUserId_v1(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomService.countAllByUserId_v1(userId);
        log.debug("REST request to countAllByUserId_v1({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_all/request-data")
    public ResponseEntity<IResponseMessage> getAllByUserId_v2(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllByUserId_v2(userId);
        log.debug("REST request to getAllByUserId_v1({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/v2/user/{userId}/_count/request-data")
    public ResponseEntity<IResponseMessage> countAllByUserId_v2(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomService.countAllByUserId_v1(userId);
        log.debug("REST request to countAllByUserId_v1({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_need_handle/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataNeedHandle(@PathVariable("userId") Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataNeedHandle(userId);
        log.debug("REST request to getAllRequestDataNeedHandle({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_need_handle/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataNeedHandle(@PathVariable("userId") Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataNeedHandle(userId);
        log.debug("REST request to countAllRequestDataNeedHandle({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_overdue/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataOverdue(@PathVariable(value = "userId", required = false) Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataOverdue(userId);
        log.debug("REST request to getAllRequestDataOverdue({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_overdue/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataOverdue(@PathVariable(value = "userId", required = false) Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataOverdue(userId);
        log.debug("REST request to getAllRequestDataOverdue({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_about_to_expire/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataAboutToExpire(@PathVariable(value = "userId", required = false) Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataAboutToExpire(userId);
        log.debug("REST request to getAllRequestDataAboutToExpire({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_about_to_expire/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataAboutToExpire(@PathVariable(value = "userId", required = false) Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataAboutToExpire(userId);
        log.debug("REST request to getAllRequestDataAboutToExpire({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_shared_to_user/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataSharedToUser(@PathVariable(value = "userId", required = false) Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataSharedToUser(userId);
        log.debug("REST request to getAllRequestDataSharedToUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_shared_to_user/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataSharedToUser(@PathVariable(value = "userId", required = false) Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataSharedToUser(userId);
        log.debug("REST request to countAllRequestDataSharedToUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_in_processing_time/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataInProcessingTime(@PathVariable(value = "userId", required = true) Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataInProcessingTime(userId);
        log.debug("REST request to getAllRequestDataInProcessingTime({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_in_processing_time/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataInProcessingTime(@PathVariable(value = "userId", required = true) Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataInProcessingTime(userId);
        log.debug("REST request to getAllRequestDataInProcessingTime({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all_drafting/request-data")
    public ResponseEntity<IResponseMessage> getAllRequestDataDrafting(@PathVariable(value = "userId", required = true) Long userId){
        List<RequestDataDTO> result = this.requestDataCustomService.getAllRequestDataDrafting(userId);
        log.debug("REST request to getAllRequestDataDrafting({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_count_drafting/request-data")
    public ResponseEntity<IResponseMessage> countAllRequestDataDrafting(@PathVariable(value = "userId", required = true) Long userId){
        Long result = this.requestDataCustomService.countAllRequestDataDrafting(userId);
        log.debug("REST request to getAllRequestDataDrafting({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

//    @PostMapping("/approve/request-data")
//    public ResponseEntity<IResponseMessage> approve(@RequestBody ApproveOption approveOption){
////        Boolean result = this.requestDataCustomService.actionRequestData(approveOption);
////        if(!result) return ResponseEntity.ok().body(new FailLoadMessage(approveOption.getErrorMessageReturn()));
//        return ResponseEntity.ok().body(new LoadedMessage(approveOption.getSuccessMessageReturn()));
//    }

    @PostMapping("/_search_with_option/request-data")
    public ResponseEntity<IResponseMessage> getRequestDataWithOption(@RequestBody List<ConditionQuery> conditionQueryList){
        List<RequestDataDTO> result = this.requestDataCustomService.getRequestDataWithOption(conditionQueryList);
        log.debug("REST request to getRequestDataWithOption({}): {}", conditionQueryList, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_add_primary_file/{userId}/request-data")
    public ResponseEntity<IResponseMessage> addPrimaryFile(@PathVariable("userId") Long userId, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("requestDataId") Long requestDataId, @RequestParam(value = "templateFormId", required = false, defaultValue = "-1") Long templateFormId) throws IOException {
        List<AttachmentFileDTO> attachmentFileDTOList = this.requestDataCustomService.addAttachmentPrimaryToRequestData(file, requestDataId, templateFormId, userId);
        log.debug("REST request to addPrimaryFile(requestDataId: {}, templateFormId:{})", requestDataId, templateFormId);
        return ResponseEntity.ok().body(new LoadedMessage(attachmentFileDTOList));
    }

    @GetMapping("/_view_file_customer/{requestDataId}/request-data")
    public ResponseEntity<IResponseMessage> getFileCustomer(@PathVariable("requestDataId") Long requestDataId) throws Exception {
        List<AttachmentFileDTO> result = this.requestDataCustomService.viewFileCustomer(requestDataId);
        log.debug("REST request to getFileCustomer({})", requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @DeleteMapping("/delete_all/request-data")
    public ResponseEntity<IResponseMessage> deleteAll_v1(@RequestBody List<RequestDataDTO> requestDataDTOList){
        this.requestDataCustomService.deleteAll_v1(requestDataDTOList);
        log.debug("RequestDataCustomRest: deleteAll({}): {}");
        return ResponseEntity.ok().body(new DeletedMessage(requestDataDTOList));
    }

    @GetMapping("/_status/{requestDataId}/request-data")
    public ResponseEntity<IResponseMessage> getStatusOfRequestData(@PathVariable("requestDataId") Long requestDataId){
        RequestDataDTO result = this.requestDataCustomService.getStatusOfRequestData(requestDataId);
        log.debug("RequestDataCustomRest: deleteAll({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @PutMapping("/_update_contract/{requestDataId}/request-data")
    public ResponseEntity<IResponseMessage> updateContractNumberRequestData(@PathVariable(value = "requestDataId", required = false) final Long id,
                                                                            @Valid @RequestBody RequestDataDTO requestDataDTO){
        log.debug("REST request to update RequestData : {}, {}", id, requestDataDTO);
        if (requestDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(requestDataDTO));
        }
        if (!Objects.equals(id, requestDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(requestDataDTO));
        }

        if (!requestDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(requestDataDTO));
        }

        //RequestDataDTO result = requestDataService.save(requestDataDTO);
        RequestDataDTO result = requestDataCustomService.updateContractNumberRequestData(id, requestDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(requestDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }



    /**
     * Hàm thực hiện kiểm tra user đang đăng nhập có phải user truyền vào khi tạo phiếu không
     * @param userId    : userId trong body
     * @return : true: nếu đúng | false: nếu không đúng
     */
    private boolean checkPermisionChangeRequestData(Long userId){
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(currentUser == null) return false;
        return currentUser.getId().equals(userId);
    }


    @PostMapping("/_custom_save/request-data")
    public ResponseEntity<IResponseMessage> createRequestDataMobile(@Valid @RequestBody RequestDataDTO requestDataDTO) throws Exception {

        // kiểm tra user tạo phiếu có đúng không
        if(!this.checkPermisionChangeRequestData(requestDataDTO.getCreated().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }
        // kiểm tra user tạo phiếu có đúng không
        if(requestDataDTO.getModified() != null && requestDataDTO.getModified().getId() != null){
            if(!this.checkPermisionChangeRequestData(requestDataDTO.getModified().getId())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
            }
        }

        log.debug("REST request to save RequestData : {}", requestDataDTO);
        if (requestDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(requestDataDTO));
        }
//            // generate code
//        requestDataDTO = this.generateCodeUtils.generateRequestDataCode(requestDataDTO);
//        RequestDataDTO result = requestDataService.save(requestDataDTO);
        RequestDataDTO result = this.requestDataCustomService.customSave_mobile(requestDataDTO);

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(requestDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    @PostMapping("/copy/request-data")
    public ResponseEntity<IResponseMessage> copyRequestData(@RequestBody List<Long> requestDataIds) throws Exception {

        // kiểm tra quyền copy\\
        List<Long> requestIdsNotPerMission = this.requestDataRepository.checkPermissionCopyRequestData(requestDataIds, this.userUtils.getCurrentUser().getId());
        if(requestIdsNotPerMission != null && !requestIdsNotPerMission.isEmpty())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("RequestDataCustomRest: copyRequestData({}): {}", requestDataIds);
        Map<Long, Boolean> result = this.requestDataCustomService.copyRequestDatas(requestDataIds, this.userUtils.getCurrentUser().getId());
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/request-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RequestDataDTO requestDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.requestDataSearchService.simpleQuerySearch(requestDataDTO, pageable);
        log.debug("RequestDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", requestDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

//    @PostMapping("/v2/search_custom/request-data")
//    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RequestDataDTO requestDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
//        ISearchResponseDTO result = this.requestDataSearchService.simpleQuerySearchWithParam(requestDataDTO, paramMaps, pageable);
//        log.debug("RequestDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", requestDataDTO, result);
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }
}
