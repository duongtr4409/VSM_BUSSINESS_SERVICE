package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.Examine;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.service.ExamineService;
import com.vsm.business.service.custom.ExamineCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.ExamineSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ExamineDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.Examine}.
 */
@RestController
@RequestMapping("/api")
public class ExamineCustomRest {

    private final Logger log = LoggerFactory.getLogger(ExamineCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceExamine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamineService examineService;

    private final ExamineRepository examineRepository;

    private final ExamineCustomService examineCustomService;

    private final ExamineSearchService examineSearchService;

    private final AuthenticateUtils authenticateUtils;

    private final StepDataRepository stepDataRepository;

    public ExamineCustomRest(ExamineService examineService, ExamineRepository examineRepository, ExamineCustomService examineCustomService, ExamineSearchService examineSearchService, AuthenticateUtils authenticateUtils, StepDataRepository stepDataRepository) {
        this.examineService = examineService;
        this.examineRepository = examineRepository;
        this.examineCustomService = examineCustomService;
        this.examineSearchService = examineSearchService;
        this.authenticateUtils = authenticateUtils;
        this.stepDataRepository = stepDataRepository;
    }

    /**
     * {@code POST  /examines} : Create a new examine.
     *
     * @param examineDTO the examineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examineDTO, or with status {@code 400 (Bad Request)} if the examine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examines")
    public ResponseEntity<IResponseMessage> createExamine(@RequestBody ExamineDTO examineDTO) throws URISyntaxException {

        // Kiểm tra user có quyền thay đổi dữ liệu liên quan đén phiếu yêu cầu hay không
        if(examineDTO.getStepData() != null){
            StepData stepData = this.stepDataRepository.findById(examineDTO.getStepData().getId()).get();
            if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to save Examine : {}", examineDTO);
        if (examineDTO.getId() != null) {
            throw new BadRequestAlertException("A new examine cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // thêm gửi mail khi yêu cầu xoát xét
//        ExamineDTO result = examineService.save(examineDTO);
        ExamineDTO result = examineCustomService.customSave(examineDTO);

        if(result == null){
            return ResponseEntity.ok(new FailCreateMessage(examineDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /examines/:id} : Updates an existing examine.
     *
     * @param id the id of the examineDTO to save.
     * @param examineDTO the examineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineDTO,
     * or with status {@code 400 (Bad Request)} if the examineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examines/{id}")
    public ResponseEntity<IResponseMessage> updateExamine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineDTO examineDTO
    ) throws URISyntaxException {

        // Kiểm tra user có quyền thay đổi dữ liệu liên quan đén phiếu yêu cầu hay không
        if(examineDTO.getStepData() != null){
            StepData stepData = this.stepDataRepository.findById(examineDTO.getStepData().getId()).get();
            if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to update Examine : {}, {}", id, examineDTO);
        if (examineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamineDTO result = examineService.save(examineDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(examineDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /examines/:id} : Partial updates given fields of an existing examine, field will ignore if it is null
     *
     * @param id the id of the examineDTO to save.
     * @param examineDTO the examineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineDTO,
     * or with status {@code 400 (Bad Request)} if the examineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/examines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateExamine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineDTO examineDTO
    ) throws URISyntaxException {

        // Kiểm tra user có quyền thay đổi dữ liệu liên quan đén phiếu yêu cầu hay không
        if(examineDTO.getStepData() != null){
            StepData stepData = this.stepDataRepository.findById(examineDTO.getStepData().getId()).get();
            if(this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to partial update Examine partially : {}, {}", id, examineDTO);
        if (examineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamineDTO> result = examineService.partialUpdate(examineDTO);

        if(!result.isPresent()){
            ResponseEntity.ok().body(new FailUpdateMessage(examineDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /examines} : get all the examines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examines in body.
     */
    @GetMapping("/examines")
    public ResponseEntity<IResponseMessage> getAllExamines(Pageable pageable) {
        log.debug("REST request to get a page of Examines");
        Page<ExamineDTO> page = examineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /examines/:id} : get the "id" examine.
     *
     * @param id the id of the examineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examines/{id}")
    public ResponseEntity<IResponseMessage> getExamine(@PathVariable Long id) {
        log.debug("REST request to get Examine : {}", id);
        Optional<ExamineDTO> examineDTO = examineService.findOne(id);
        if(!examineDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(examineDTO.get()));
    }

    /**
     * {@code DELETE  /examines/:id} : delete the "id" examine.
     *
     * @param id the id of the examineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examines/{id}")
    public ResponseEntity<IResponseMessage> deleteExamine(@PathVariable Long id) {

        // Kiểm tra user có quyền thay đổi dữ liệu liên quan đén phiếu yêu cầu hay không
        Examine examine = this.examineRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(examine.getStepData().getRequestData() != null ? examine.getStepData().getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete Examine : {}", id);
        examineService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/examines?query=:query} : search for the examine corresponding
     * to the query.
     *
     * @param query the query of the examine search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/examines")
    public ResponseEntity<List<ExamineDTO>> searchExamines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Examines for query {}", query);
        Page<ExamineDTO> page = examineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/user/{userId}/request-data/{requestDataId}/_all/examines")
    public ResponseEntity<IResponseMessage> getAllByUserAndRequestData(@PathVariable("userId") Long userId, @PathVariable("requestDataId") Long requestDataId){
        log.info("ExamineCustomRest: getAllByUserAndRequestData(userId: {}, requestDataId: {})", userId, requestDataId);
        List<ExamineDTO> result = this.examineCustomService.getAllByUserAndRequestData(userId, requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/examines")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ExamineDTO examineDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.examineSearchService.simpleQuerySearch(examineDTO, pageable);
        log.debug("ExamineCustomRest: customSearch(IBaseSearchDTO: {}): {}", examineDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/examines")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ExamineDTO examineDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.examineSearchService.simpleQuerySearchWithParam(examineDTO, paramMaps, pageable);
        log.debug("ExamineCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", examineDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
