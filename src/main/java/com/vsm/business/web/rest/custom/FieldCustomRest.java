package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.FieldRepository;
import com.vsm.business.service.FieldService;
import com.vsm.business.service.custom.FieldCustomService;
import com.vsm.business.service.custom.search.service.FieldSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FieldDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.Field}.
 */
@RestController
@RequestMapping("/api")
public class FieldCustomRest {

    private final Logger log = LoggerFactory.getLogger(FieldCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldService fieldService;

    private final FieldRepository fieldRepository;

    private final FieldCustomService fieldCustomService;

    private final FieldSearchService fieldSearchService;

    private Map<String, FieldDTO> fieldDTOMap = new HashMap<>();



    public FieldCustomRest(FieldService fieldService, FieldRepository fieldRepository, FieldCustomService fieldCustomService, FieldSearchService fieldSearchService) {
        this.fieldService = fieldService;
        this.fieldRepository = fieldRepository;
        this.fieldCustomService = fieldCustomService;
        this.fieldSearchService = fieldSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadField(){
        List<FieldDTO> fieldDTOList = this.fieldCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setModified(null);
            ele.setCreated(null);
            return ele;
        }).collect(Collectors.toList());
        this.fieldDTOMap = new HashMap<>();
        fieldDTOList.stream().forEach(ele -> {
            this.fieldDTOMap.put(ele.getFieldCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(FieldDTO fieldDTO){
        if(this.fieldDTOMap == null || this.fieldDTOMap.size() == 0) this.loadField();
        try {
            String code = this.generateCodeUtils.generateCode(fieldDTO.getFieldName(), this.fieldDTOMap, FieldDTO.class, "getFieldCode");
            fieldDTO.setFieldCode(code);
            this.fieldDTOMap.put(code, fieldDTO);

            this.fieldDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.fieldDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /fields} : Create a new field.
     *
     * @param fieldDTO the fieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldDTO, or with status {@code 400 (Bad Request)} if the field has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fields")
    public ResponseEntity<IResponseMessage> createField(@Valid @RequestBody FieldDTO fieldDTO) throws URISyntaxException {
        log.debug("REST request to save Field : {}", fieldDTO);
        if (fieldDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(fieldDTO));
        }

        // generate code
        fieldDTO.setFieldCode(this.generateCode(fieldDTO));

        FieldDTO result = fieldService.save(fieldDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(fieldDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /fields/:id} : Updates an existing field.
     *
     * @param id the id of the fieldDTO to save.
     * @param fieldDTO the fieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fields/{id}")
    public ResponseEntity<IResponseMessage> updateField(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldDTO fieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Field : {}, {}", id, fieldDTO);
        if (fieldDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldDTO));
        }
        if (!Objects.equals(id, fieldDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldDTO));
        }

        if (!fieldRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldDTO));
        }

        FieldDTO result = fieldService.save(fieldDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fieldDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /fields/:id} : Partial updates given fields of an existing field, field will ignore if it is null
     *
     * @param id the id of the fieldDTO to save.
     * @param fieldDTO the fieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldDTO,
     * or with status {@code 400 (Bad Request)} if the fieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateField(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldDTO fieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Field partially : {}, {}", id, fieldDTO);
        if (fieldDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fieldDTO));

        }
        if (!Objects.equals(id, fieldDTO.getId())) {
         return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fieldDTO));
        }

        if (!fieldRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fieldDTO));
        }

        Optional<FieldDTO> result = fieldService.partialUpdate(fieldDTO);

        if (!result.isPresent()) {
        return ResponseEntity.ok().body(new FailUpdateMessage(fieldDTO));
    }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /fields} : get all the fields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fields in body.
     */
    @GetMapping("/fields")
    public ResponseEntity<IResponseMessage> getAllFields(Pageable pageable) {
        log.debug("REST request to get a page of Fields");
        Page<FieldDTO> page = fieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /fields/:id} : get the "id" field.
     *
     * @param id the id of the fieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fields/{id}")
    public ResponseEntity<IResponseMessage> getField(@PathVariable Long id) {
        log.debug("REST request to get Field : {}", id);
        Optional<FieldDTO> fieldDTO = fieldService.findOne(id);
        if (!fieldDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(fieldDTO.get()));
    }

    /**
     * {@code DELETE  /fields/:id} : delete the "id" field.
     *
     * @param id the id of the fieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<IResponseMessage> deleteField(@PathVariable Long id) {
        log.debug("REST request to delete Field : {}", id);
        fieldService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/fields?query=:query} : search for the field corresponding
     * to the query.
     *
     * @param query the query of the field search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fields")
    public ResponseEntity<List<FieldDTO>> searchFields(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fields for query {}", query);
        Page<FieldDTO> page = fieldService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/fields")
    public ResponseEntity<IResponseMessage> getAll(){
        List<FieldDTO> result = fieldCustomService.getAll();
        log.debug("FieldCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/fields")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<FieldDTO> fieldDTOS) {
        List<FieldDTO> result = this.fieldCustomService.deleteAll(fieldDTOS);
        log.debug("FieldCustomRest: deleteAll({}) {}", fieldDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/fields")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody FieldDTO fieldDTO){
        if(this.fieldDTOMap.get(this.generateCodeUtils.getCode(fieldDTO.getFieldName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(fieldDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(fieldDTO));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/fields")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FieldDTO fieldDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldSearchService.simpleQuerySearch(fieldDTO, pageable);
        log.debug("FieldCustomRest: customSearch(IBaseSearchDTO: {}): {}", fieldDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/fields")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FieldDTO fieldDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fieldSearchService.simpleQuerySearchWithParam(fieldDTO, paramMaps, pageable);
        log.debug("FieldCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", fieldDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
