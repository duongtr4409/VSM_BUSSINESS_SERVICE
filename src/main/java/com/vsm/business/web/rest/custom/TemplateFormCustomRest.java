package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.*;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.TemplateFormRepository;
import com.vsm.business.service.TemplateFormService;
import com.vsm.business.service.custom.AttachmentFileCustomService;
import com.vsm.business.service.custom.TemplateFormCustomService;
import com.vsm.business.service.custom.search.service.TemplateFormSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TemplateFormDTO;
import com.vsm.business.utils.GenerateCodeUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * REST controller for managing {@link com.vsm.business.domain.TemplateForm}.
 */
@RestController
@RequestMapping("/api")
public class TemplateFormCustomRest {

    private final Logger log = LoggerFactory.getLogger(TemplateFormCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTemplateForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateFormService templateFormService;

    private final TemplateFormRepository templateFormRepository;

    private TemplateFormCustomService templateFormCustomService;

    private AttachmentFileCustomService attachmentFileCustomService;

    private TemplateFormSearchService templateFormSearchService;

    private Map<String, TemplateFormDTO> templateFormDTOMap = new HashMap<>();

    private final String ERROR_EXIST_REQUEST = "Không thể xóa biểu mẫu đang hoạt động";

    public TemplateFormCustomRest(TemplateFormService templateFormService, TemplateFormRepository templateFormRepository, TemplateFormCustomService templateFormCustomService, AttachmentFileCustomService attachmentFileCustomService, TemplateFormSearchService templateFormSearchService) {
        this.templateFormService = templateFormService;
        this.templateFormRepository = templateFormRepository;
        this.templateFormCustomService = templateFormCustomService;
        this.attachmentFileCustomService = attachmentFileCustomService;
        this.templateFormSearchService = templateFormSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadTemplateForm() throws IllegalAccessException {
        List<TemplateFormDTO> templateFormDTOList = this.templateFormCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setAttachmentFileDTOS(new ArrayList<>());
            ele.setCreated(null);
            ele.setModified(null);
            ele.setOrganizations(new HashSet<>());
            ele.setRequestDTOS(new ArrayList<>());
            return ele;
        }).collect(Collectors.toList());
        this.templateFormDTOMap = new HashMap<>();
        templateFormDTOList.stream().forEach(ele -> {
            this.templateFormDTOMap.put(ele.getTemplateFormCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(TemplateFormDTO templateFormDTO) throws IllegalAccessException {
        if(this.templateFormDTOMap == null || this.templateFormDTOMap.size() == 0) this.loadTemplateForm();
        try {
            String code = generateCodeUtils.generateCode(templateFormDTO.getTemplateFormName(), this.templateFormDTOMap, TemplateFormDTO.class, "getTemplateFormCode");
            templateFormDTO.setTemplateFormCode(code);
            this.templateFormDTOMap.put(code, templateFormDTO);

            this.templateFormDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.templateFormDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /template-forms} : Create a new templateForm.
     *
     * @param templateFormDTO the templateFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateFormDTO, or with status {@code 400 (Bad Request)} if the templateForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-forms")
    public ResponseEntity<IResponseMessage> createTemplateForm(@Valid @RequestBody TemplateFormDTO templateFormDTO)
        throws URISyntaxException, IllegalAccessException {
        log.debug("REST request to save TemplateForm : {}", templateFormDTO);
        if (templateFormDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(templateFormDTO));
        }

        // generate Code
        templateFormDTO.setTemplateFormCode(this.generateCode(templateFormDTO));

        TemplateFormDTO result = templateFormService.save(templateFormDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(templateFormDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /template-forms/:id} : Updates an existing templateForm.
     *
     * @param id              the id of the templateFormDTO to save.
     * @param templateFormDTO the templateFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-forms/{id}")
    public ResponseEntity<IResponseMessage> updateTemplateForm(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemplateFormDTO templateFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateForm : {}, {}", id, templateFormDTO);
        if (templateFormDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(templateFormDTO));
        }
        if (!Objects.equals(id, templateFormDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(templateFormDTO));
        }

        if (!templateFormRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(templateFormDTO));
        }

        TemplateFormDTO result = templateFormService.save(templateFormDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(templateFormDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /template-forms/:id} : Partial updates given fields of an existing templateForm, field will ignore if it is null
     *
     * @param id              the id of the templateFormDTO to save.
     * @param templateFormDTO the templateFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-forms/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateTemplateForm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemplateFormDTO templateFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateForm partially : {}, {}", id, templateFormDTO);
        if (templateFormDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(templateFormDTO));
        }
        if (!Objects.equals(id, templateFormDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(templateFormDTO));
        }

        if (!templateFormRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(templateFormDTO));
        }

        Optional<TemplateFormDTO> result = templateFormService.partialUpdate(templateFormDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(templateFormDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /template-forms} : get all the templateForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateForms in body.
     */
    @GetMapping("/template-forms")
    public ResponseEntity<IResponseMessage> getAllTemplateForms(Pageable pageable) {
        log.debug("REST request to get a page of TemplateForms");
        Page<TemplateFormDTO> page = templateFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /template-forms/:id} : get the "id" templateForm.
     *
     * @param id the id of the templateFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-forms/{id}")
    public ResponseEntity<IResponseMessage> getTemplateForm(@PathVariable Long id) throws IllegalAccessException {
//        log.debug("REST request to get TemplateForm : {}", id);
//        Optional<TemplateFormDTO> templateFormDTO = templateFormService.findOne(id);
//
//        if (!templateFormDTO.isPresent()) {
//            return ResponseEntity.ok().body(new FailLoadMessage(id));
//        }
////        return ResponseEntity.ok().body(new LoadedMessage(templateFormDTO.get()));
//
//        TemplateFormDTO result = templateFormDTO.get();
//        result.setAttachmentFileDTOS(this.attachmentFileCustomService.findAllByTemplateForm(result.getId()));
//        return ResponseEntity.ok().body(new LoadedMessage(result));
        log.debug("REST request to get TemplateForm : {}", id);
        TemplateFormDTO result = templateFormCustomService.getOne(id);
        if (result == null) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * {@code DELETE  /template-forms/:id} : delete the "id" templateForm.
     *
     * @param id the id of the templateFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-forms/{id}")
    public ResponseEntity<IResponseMessage> deleteTemplateForm(@PathVariable Long id) {
        log.debug("REST request to delete TemplateForm : {}", id);
        if (!templateFormCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/template-forms?query=:query} : search for the templateForm corresponding
     * to the query.
     *
     * @param query    the query of the templateForm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/template-forms")
    public ResponseEntity<List<TemplateFormDTO>> searchTemplateForms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TemplateForms for query {}", query);
        Page<TemplateFormDTO> page = templateFormService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/template-forms")
    public ResponseEntity<IResponseMessage> getAll(@RequestParam(required = false, defaultValue = "false", value = "ignoreFile") Boolean ignoreFile) throws IllegalAccessException {
        List<TemplateFormDTO> result = this.templateFormCustomService.getAll(ignoreFile);
        log.debug("TemplateFormCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/template-forms")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<TemplateFormDTO> templateFormDTOS) throws IllegalAccessException {
        List<TemplateFormDTO> result = this.templateFormCustomService.deleteAll(templateFormDTOS);
        log.debug("TemplateFormCustomRest: deleteAll({}) {}", templateFormDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/template-forms")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<TemplateFormDTO> templateFormDTOList){
        List<TemplateFormDTO> result;
        try {
             result = this.templateFormCustomService.customSaveAll(templateFormDTOList);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, templateFormDTOList));
        }
        log.debug("TemplateFormCustomRest: saveAll({}): {}", templateFormDTOList, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/template-forms")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody TemplateFormDTO templateFormDTO){
        if(this.templateFormDTOMap.get(this.generateCodeUtils.getCode(templateFormDTO.getTemplateFormCode())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(templateFormDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(templateFormDTO));
    }

    @GetMapping("/_all/request/{requestId}/template-forms")
    public ResponseEntity<IResponseMessage> getAllByRequest(@PathVariable("requestId") Long requestId){
        List<TemplateFormDTO> result = this.templateFormCustomService.getAllByRequest(requestId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/has_file/template-forms")
    public ResponseEntity<IResponseMessage> getAllTemplateFormHasFile(@RequestParam(required = false, defaultValue = "false", value = "ignoreFile") Boolean ignoreFile) throws IllegalAccessException {
        List<TemplateFormDTO> result = this.templateFormCustomService.getAllHasFile(ignoreFile);
        log.debug("TemplateFormCustomRest: getAllTemplateFormHasFile(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/template-forms")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody TemplateFormDTO templateFormDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.templateFormSearchService.simpleQuerySearch(templateFormDTO, pageable);
        log.debug("TemplateFormCustomRest: customSearch(IBaseSearchDTO: {}): {}", templateFormDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/template-forms")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody TemplateFormDTO templateFormDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.templateFormSearchService.simpleQuerySearchWithParam(templateFormDTO, paramMaps, pageable);
        log.debug("TemplateFormCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", templateFormDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
