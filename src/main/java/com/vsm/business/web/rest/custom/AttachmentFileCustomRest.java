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
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.AttachmentFileService;
import com.vsm.business.service.custom.AttachmentFileCustomService;
import com.vsm.business.service.custom.search.service.AttachmentFileSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.AttachmentFileDTO;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.PermissionFileUtils;
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
 * REST controller for managing {@link com.vsm.business.domain.AttachmentFile}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentFileCustomRest {

    private final Logger log = LoggerFactory.getLogger(AttachmentFileCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceAttachmentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${role-request.check-role:TRUE}")
    public String CHECK_ROLE;

    private final AttachmentFileService attachmentFileService;

    private final AttachmentFileRepository attachmentFileRepository;

    private final AttachmentFileCustomService attachmentFileCustomService;

    private final AttachmentFileSearchService attachmentFileSearchService;

    private final AuthenticateUtils authenticateUtils;

    private final PermissionFileUtils permissionFileUtils;

    public AttachmentFileCustomRest(AttachmentFileService attachmentFileService, AttachmentFileRepository attachmentFileRepository, AttachmentFileCustomService attachmentFileCustomService, AttachmentFileSearchService attachmentFileSearchService, AuthenticateUtils authenticateUtils, PermissionFileUtils permissionFileUtils) {
        this.attachmentFileService = attachmentFileService;
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileCustomService = attachmentFileCustomService;
        this.attachmentFileSearchService = attachmentFileSearchService;
        this.authenticateUtils = authenticateUtils;
        this.permissionFileUtils = permissionFileUtils;
    }

    /**
     * {@code POST  /attachment-files} : Create a new attachmentFile.
     *
     * @param attachmentFileDTO the attachmentFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentFileDTO, or with status {@code 400 (Bad Request)} if the attachmentFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachment-files")
    public ResponseEntity<IResponseMessage> createAttachmentFile(@Valid @RequestBody AttachmentFileDTO attachmentFileDTO)
        throws URISyntaxException {
//        log.debug("REST request to save AttachmentFile : {}", attachmentFileDTO);
//        if (attachmentFileDTO.getId() != null) {
//            throw new BadRequestAlertException("A new attachmentFile cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        AttachmentFileDTO result = attachmentFileService.save(attachmentFileDTO);
//        return ResponseEntity
//            .created(new URI("/api/attachment-files/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//
        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
            // 07/11/2022: thêm phần kiểm tra user có quyền tạo phiếu vào folder cha ko
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null)
            && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && attachmentFileDTO.getRequestData() != null && !this.permissionFileUtils.checkPermisstionToFile(this.attachmentFileRepository.findByRequestDataIdAndIsFolder(attachmentFileDTO.getRequestData().getId(), true).get().getId(), PermissionFileUtils.EDIT)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        if (attachmentFileDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(attachmentFileDTO));
        }
        AttachmentFileDTO result = attachmentFileService.save(attachmentFileDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(attachmentFileDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /attachment-files/:id} : Updates an existing attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to save.
     * @param attachmentFileDTO the attachmentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentFileDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachment-files/{id}")
    public ResponseEntity<IResponseMessage> updateAttachmentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttachmentFileDTO attachmentFileDTO
    ) throws URISyntaxException {

        // nếu không phải dữ liệu của phiếu -> phải là ADMIN mới được thay đổi
//        if(attachmentFileDTO.getRequestData() == null){
//            if(!this.authenticateUtils.checkPermisionADMIN())
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
//        }
        // 07/11/2022: thêm phần kiểm tra user có quyền tạo phiếu vào folder cha ko
        if(attachmentFileDTO.getRequestData() == null){
            if(!this.authenticateUtils.checkPermisionADMIN() && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.EDIT)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }
        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        // 07/11/2022: thêm phần kiểm tra user có quyền tạo phiếu vào folder cha ko
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null)
            && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.EDIT)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update AttachmentFile : {}, {}", id, attachmentFileDTO);
        if (attachmentFileDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentFileDTO));
        }
        if (!Objects.equals(id, attachmentFileDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentFileDTO));
        }

        if (!attachmentFileRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentFileDTO));
        }

        AttachmentFileDTO result = attachmentFileService.save(attachmentFileDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentFileDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /attachment-files/:id} : Partial updates given fields of an existing attachmentFile, field will ignore if it is null
     *
     * @param id the id of the attachmentFileDTO to save.
     * @param attachmentFileDTO the attachmentFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentFileDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachment-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateAttachmentFile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttachmentFileDTO attachmentFileDTO
    ) throws URISyntaxException {

        // nếu không phải dữ liệu của phiếu -> phải là ADMIN mới được thay đổi
//        if(attachmentFileDTO.getRequestData() == null){
//            if(!this.authenticateUtils.checkPermisionADMIN())
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
//        }
        // 07/11/2022: thêm phần kiểm tra user có quyền tạo phiếu vào folder cha ko
        if(attachmentFileDTO.getRequestData() == null){
            if(!this.authenticateUtils.checkPermisionADMIN() && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.EDIT)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDTO.getRequestData() != null ? attachmentFileDTO.getRequestData().getId() : null)
            && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.EDIT)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update AttachmentFile partially : {}, {}", id, attachmentFileDTO);
        if (attachmentFileDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(attachmentFileDTO));
        }
        if (!Objects.equals(id, attachmentFileDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(attachmentFileDTO));
        }

        if (!attachmentFileRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(attachmentFileDTO));
        }

        Optional<AttachmentFileDTO> result = attachmentFileService.partialUpdate(attachmentFileDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(attachmentFileDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /attachment-files} : get all the attachmentFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachmentFiles in body.
     */
    @GetMapping("/attachment-files")
    public ResponseEntity<List<AttachmentFileDTO>> getAllAttachmentFiles(Pageable pageable) {
        log.debug("REST request to get a page of AttachmentFiles");
        Page<AttachmentFileDTO> page = attachmentFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachment-files/:id} : get the "id" attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachment-files/{id}")
    public ResponseEntity<IResponseMessage> getAttachmentFile(@PathVariable Long id) {
        log.debug("REST request to get AttachmentFile : {}", id);

        // nếu không phải dữ liệu của phiếu -> phải là ADMIN mới được xem
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
//        if(attachmentFile.getRequestData() == null){
//            if(!this.authenticateUtils.checkPermisionADMIN())
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
//        }
        // 07/11/2022: kết hợp kiểm tra thêm người dùng có được cấu hình quyền với file không
        if(attachmentFile.getRequestData() == null){
            if(!this.authenticateUtils.checkPermisionADMIN() && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.VIEW)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getId() : null)
            && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.VIEW)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);


        Optional<AttachmentFileDTO> attachmentFileDTO = attachmentFileService.findOne(id);
        if (!attachmentFileDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(attachmentFileDTO.get()));
    }

    /**
     * {@code DELETE  /attachment-files/:id} : delete the "id" attachmentFile.
     *
     * @param id the id of the attachmentFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachment-files/{id}")
    public ResponseEntity<IResponseMessage> deleteAttachmentFile(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        AttachmentFile attachmentFileDelete = this.attachmentFileRepository.findById(id).get();
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDelete.getRequestData() != null ? attachmentFileDelete.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        // 11/07/2022: kết hợp kiểm tra thêm người dùng có được cấu hình quyền với file không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFileDelete.getRequestData() != null ? attachmentFileDelete.getRequestData().getId() : null)
            && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.DELETE)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete AttachmentFile : {}", id);
        //attachmentFileService.delete(id);
        this.attachmentFileCustomService.customDelete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));

    }

    /**
     * {@code SEARCH  /_search/attachment-files?query=:query} : search for the attachmentFile corresponding
     * to the query.
     *
     * @param query the query of the attachmentFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/attachment-files")
    public ResponseEntity<List<AttachmentFileDTO>> searchAttachmentFiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AttachmentFiles for query {}", query);
        Page<AttachmentFileDTO> page = attachmentFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

//    @GetMapping("/_all/attachment-files")
//    public ResponseEntity<IResponseMessage> getAll() {
//        List<StepInProcessDTO> result = this.stepInProcessCustomService.getAll();
//        log.debug("StepInProcessCustomRest: getAll() {}", result);;
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }
//
//    @PostMapping("/_delete/attachment-files")
//    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<StepInProcessDTO> stepInProcessDTOS) {
//        List<StepInProcessDTO> result = this.stepInProcessCustomService.deleteAll(stepInProcessDTOS);
//        log.debug("StepInProcessCustomRest: deleteAll({}) {}", stepInProcessDTOS, result);
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }

    @GetMapping("/template-form/{templateFormId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> findAllByTemplateFormId(@PathVariable("templateFormId") Long templateFormId) {
        List<AttachmentFileDTO> result = attachmentFileCustomService.findAllByTemplateForm(templateFormId);
        log.debug("REST request to findAllByStepInProcessId({}): {}", templateFormId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> findAllByRequestDataId(@PathVariable("requestDataId") Long requestDataId){

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestDataId))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        // 21/11/2022: sửa để cho phép người được phân quyền xem thống kê cũng có thể xem dữ liệu
        if(!this.authenticateUtils.checkPermisionForDataOfUser(requestDataId) && !this.authenticateUtils.checkPermisionForDataStatisticToUser(requestDataId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        List<AttachmentFileDTO> result = attachmentFileCustomService.findAllByRequestData(requestDataId);
        log.debug("REST request to findAllByRequestDataId({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @DeleteMapping("/template-form/{templateFormId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> deleteAllByTemplateFormId(@PathVariable("templateFormId") Long templateFormId) throws Exception {
        this.attachmentFileCustomService.deleteAllByTemplateFormId(templateFormId);
        return ResponseEntity.ok().body(new DeletedMessage(templateFormId));
    }

    @GetMapping("/manage-stamp-info/{manageStampInfoId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> getAllByManageStampInfoId(@PathVariable("manageStampInfoId") Long manageStampInfoId){
        List<AttachmentFileDTO> result = attachmentFileCustomService.getAllByManageStampInfo(manageStampInfoId);
        log.debug("REST request to getAllByManageStampInfoId({}): {}", manageStampInfoId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/mail-template/{mailTemplateId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> getAllByMailTemplateId(@PathVariable("mailTemplateId") Long mailTemplateId){
        List<AttachmentFileDTO> result = attachmentFileCustomService.getAllByMailTemplate(mailTemplateId);
        log.debug("REST request to getAllByMailTemplateId({}): {}", mailTemplateId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/template-forms/{templateFormId}/no-request-data/_all/attachment-files")
    public ResponseEntity<IResponseMessage> findAllByTemplateFormIdNoRequestData(@PathVariable("templateFormId") Long templateFormId){
        List<AttachmentFileDTO> result = attachmentFileCustomService.getAllByTemplateFormIdAndRequestData(templateFormId, null);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/attachment-files")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody AttachmentFileDTO attachmentFileDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentFileSearchService.simpleQuerySearch(attachmentFileDTO, pageable);
        log.debug("AttachmentFileCustomRest: customSearch(IBaseSearchDTO: {}): {}", attachmentFileDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/attachment-files")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody AttachmentFileDTO attachmentFileDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.attachmentFileSearchService.simpleQuerySearchWithParam(attachmentFileDTO, paramMaps, pageable);
        log.debug("AttachmentFileCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", attachmentFileDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
