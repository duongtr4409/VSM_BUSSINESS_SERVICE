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
import com.vsm.business.domain.ExamineReply;
import com.vsm.business.repository.ExamineReplyRepository;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.service.ExamineReplyService;
import com.vsm.business.service.custom.ExamineReplyCustomService;
import com.vsm.business.service.custom.search.service.ExamineReplySearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ExamineReplyDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.ExamineReply}.
 */
@RestController
@RequestMapping("/api")
public class ExamineReplyCustomRest {

    private final Logger log = LoggerFactory.getLogger(ExamineReplyCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceExamineReply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamineReplyService examineReplyService;

    private final ExamineReplyCustomService examineReplyCustomService;

    private final ExamineReplyRepository examineReplyRepository;

    private final ExamineReplySearchService examineReplySearchService;

    private final ExamineRepository examineRepository;

    private final AuthenticateUtils authenticateUtils;

    public ExamineReplyCustomRest(ExamineReplyService examineReplyService, ExamineReplyRepository examineReplyRepository, ExamineReplySearchService examineReplySearchService, AuthenticateUtils authenticateUtils, ExamineRepository examineRepository, ExamineReplyCustomService examineReplyCustomService) {
        this.examineReplyService = examineReplyService;
        this.examineReplyRepository = examineReplyRepository;
        this.examineReplySearchService = examineReplySearchService;
        this.examineRepository = examineRepository;
        this.authenticateUtils = authenticateUtils;
        this.examineReplyCustomService = examineReplyCustomService;
    }

    /**
     * {@code POST  /examine-replies} : Create a new examineReply.
     *
     * @param examineReplyDTO the examineReplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examineReplyDTO, or with status {@code 400 (Bad Request)} if the examineReply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examine-replies")
    public ResponseEntity<IResponseMessage> createExamineReply(@RequestBody ExamineReplyDTO examineReplyDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đỏi dữ liệu liên quan đén phiếu yêu cầu không
        if(examineReplyDTO.getExamine() != null){
            Examine examine = this.examineRepository.findById(examineReplyDTO.getExamine().getId()).get();
            if(!this.authenticateUtils.checkPermisionForDataOfUser(examine.getStepData().getRequestData() != null ? examine.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to save ExamineReply : {}", examineReplyDTO);
        if (examineReplyDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(examineReplyDTO));
        }


        // thêm gửi mail khi trả lời yêu cầu xoát xét
//        ExamineReplyDTO result = examineReplyService.save(examineReplyDTO);
        ExamineReplyDTO result = examineReplyCustomService.customSave(examineReplyDTO);

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(examineReplyDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /examine-replies/:id} : Updates an existing examineReply.
     *
     * @param id the id of the examineReplyDTO to save.
     * @param examineReplyDTO the examineReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineReplyDTO,
     * or with status {@code 400 (Bad Request)} if the examineReplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examineReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examine-replies/{id}")
    public ResponseEntity<IResponseMessage> updateExamineReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineReplyDTO examineReplyDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đỏi dữ liệu liên quan đén phiếu yêu cầu không
        if(examineReplyDTO.getExamine() != null){
            Examine examine = this.examineRepository.findById(examineReplyDTO.getExamine().getId()).get();
            if(this.authenticateUtils.checkPermisionForDataOfUser(examine.getStepData().getRequestData() != null ? examine.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to update ExamineReply : {}, {}", id, examineReplyDTO);
        if (examineReplyDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(examineReplyDTO));
        }
        if (!Objects.equals(id, examineReplyDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(examineReplyDTO));
        }

        if (!examineReplyRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(examineReplyDTO));
        }

        ExamineReplyDTO result = examineReplyService.save(examineReplyDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(examineReplyDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /examine-replies/:id} : Partial updates given fields of an existing examineReply, field will ignore if it is null
     *
     * @param id the id of the examineReplyDTO to save.
     * @param examineReplyDTO the examineReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examineReplyDTO,
     * or with status {@code 400 (Bad Request)} if the examineReplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examineReplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examineReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/examine-replies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateExamineReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExamineReplyDTO examineReplyDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đỏi dữ liệu liên quan đén phiếu yêu cầu không
        if(examineReplyDTO.getExamine() != null){
            Examine examine = this.examineRepository.findById(examineReplyDTO.getExamine().getId()).get();
            if(this.authenticateUtils.checkPermisionForDataOfUser(examine.getStepData().getRequestData() != null ? examine.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to partial update ExamineReply partially : {}, {}", id, examineReplyDTO);
        if (examineReplyDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(examineReplyDTO));
        }
        if (!Objects.equals(id, examineReplyDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(examineReplyDTO));
        }

        if (!examineReplyRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(examineReplyDTO));
        }

        Optional<ExamineReplyDTO> result = examineReplyService.partialUpdate(examineReplyDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(examineReplyDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /examine-replies} : get all the examineReplies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examineReplies in body.
     */
    @GetMapping("/examine-replies")
    public ResponseEntity<IResponseMessage> getAllExamineReplies(Pageable pageable) {
        log.debug("REST request to get a page of ExamineReplies");
        Page<ExamineReplyDTO> page = examineReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /examine-replies/:id} : get the "id" examineReply.
     *
     * @param id the id of the examineReplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examineReplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examine-replies/{id}")
    public ResponseEntity<IResponseMessage> getExamineReply(@PathVariable Long id) {
        log.debug("REST request to get ExamineReply : {}", id);
        Optional<ExamineReplyDTO> userInFieldDataDTO = examineReplyService.findOne(id);

        if (!userInFieldDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(userInFieldDataDTO.get()));
    }

    /**
     * {@code DELETE  /examine-replies/:id} : delete the "id" examineReply.
     *
     * @param id the id of the examineReplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examine-replies/{id}")
    public ResponseEntity<IResponseMessage> deleteExamineReply(@PathVariable Long id) {

        // kiểm tra user có quyền thay đỏi dữ liệu liên quan đén phiếu yêu cầu không
        ExamineReply examineReply = this.examineReplyRepository.findById(id).get();
        if(examineReply.getExamine() != null){
            Examine examine = examineReply.getExamine();
            if(this.authenticateUtils.checkPermisionForDataOfUser(examine.getStepData().getRequestData() != null ? examine.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to delete ExamineReply : {}", id);
        examineReplyService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/examine-replies?query=:query} : search for the examineReply corresponding
     * to the query.
     *
     * @param query the query of the examineReply search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/examine-replies")
    public ResponseEntity<List<ExamineReplyDTO>> searchExamineReplies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ExamineReplies for query {}", query);
        Page<ExamineReplyDTO> page = examineReplyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/examine-replies")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ExamineReplyDTO examineReplyDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.examineReplySearchService.simpleQuerySearch(examineReplyDTO, pageable);
        log.debug("ExamineReplyCustomRest: customSearch(IBaseSearchDTO: {}): {}", examineReplyDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/examine-replies")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ExamineReplyDTO examineReplyDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.examineReplySearchService.simpleQuerySearchWithParam(examineReplyDTO, paramMaps, pageable);
        log.debug("ExamineReplyCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", examineReplyDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
