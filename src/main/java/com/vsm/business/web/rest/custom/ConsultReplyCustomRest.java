package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.Consult;
import com.vsm.business.domain.ConsultReply;
import com.vsm.business.repository.ConsultReplyRepository;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.service.ConsultReplyService;
import com.vsm.business.service.custom.ConsultReplyCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.ConsultReplySearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConsultReplyDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.ConsultReply}.
 */
@RestController
@RequestMapping("/api")
public class ConsultReplyCustomRest {

    private final Logger log = LoggerFactory.getLogger(ConsultReplyCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConsultReply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultReplyService consultReplyService;

    private final ConsultReplyRepository consultReplyRepository;

    private final ConsultReplyCustomService consultReplyCustomService;

    private final ConsultReplySearchService consultReplySearchService;

    private final ConsultRepository consultRepository;

    private final AuthenticateUtils authenticateUtils;

    public ConsultReplyCustomRest(ConsultReplyService consultReplyService, ConsultReplyRepository consultReplyRepository, ConsultReplyCustomService consultReplyCustomService, ConsultReplySearchService consultReplySearchService, AuthenticateUtils authenticateUtils, ConsultRepository consultRepository) {
        this.consultReplyService = consultReplyService;
        this.consultReplyRepository = consultReplyRepository;
        this.consultReplyCustomService = consultReplyCustomService;
        this.consultReplySearchService = consultReplySearchService;
        this.authenticateUtils = authenticateUtils;
        this.consultRepository = consultRepository;
    }

    /**
     * {@code POST  /consult-replies} : Create a new consultReply.
     *
     * @param consultReplyDTO the consultReplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultReplyDTO, or with status {@code 400 (Bad Request)} if the consultReply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consult-replies")
    public ResponseEntity<IResponseMessage> createConsultReply(@RequestBody ConsultReplyDTO consultReplyDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(consultReplyDTO.getConsult() != null){
            Consult consult = this.consultRepository.findById(consultReplyDTO.getConsult().getId()).get();
            if(!this.authenticateUtils.checkPermisionForDataOfUser(consult.getStepData().getRequestData() != null ? consult.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to save ConsultReply : {}", consultReplyDTO);
        if (consultReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultReplyDTO result = consultReplyService.save(consultReplyDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(consultReplyDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /consult-replies/:id} : Updates an existing consultReply.
     *
     * @param id the id of the consultReplyDTO to save.
     * @param consultReplyDTO the consultReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultReplyDTO,
     * or with status {@code 400 (Bad Request)} if the consultReplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consult-replies/{id}")
    public ResponseEntity<IResponseMessage> updateConsultReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultReplyDTO consultReplyDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(consultReplyDTO.getConsult() != null){
            Consult consult = this.consultRepository.findById(id).get();
            if(!this.authenticateUtils.checkPermisionForDataOfUser(consult.getStepData().getRequestData() != null ? consult.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to update ConsultReply : {}, {}", id, consultReplyDTO);
        if (consultReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultReplyDTO result = consultReplyService.save(consultReplyDTO);
        if(result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(consultReplyDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /consult-replies/:id} : Partial updates given fields of an existing consultReply, field will ignore if it is null
     *
     * @param id the id of the consultReplyDTO to save.
     * @param consultReplyDTO the consultReplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultReplyDTO,
     * or with status {@code 400 (Bad Request)} if the consultReplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consultReplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultReplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consult-replies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateConsultReply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultReplyDTO consultReplyDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(consultReplyDTO.getConsult() != null){
            Consult consult = this.consultRepository.findById(id).get();
            if(this.authenticateUtils.checkPermisionForDataOfUser(consult.getStepData().getRequestData() != null ? consult.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to partial update ConsultReply partially : {}, {}", id, consultReplyDTO);
        if (consultReplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultReplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultReplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultReplyDTO> result = consultReplyService.partialUpdate(consultReplyDTO);

        if(!result.isPresent()){
            ResponseEntity.ok().body(new FailUpdateMessage(consultReplyDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /consult-replies} : get all the consultReplies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultReplies in body.
     */
    @GetMapping("/consult-replies")
    public ResponseEntity<IResponseMessage> getAllConsultReplies(Pageable pageable) {
        log.debug("REST request to get a page of ConsultReplies");
        Page<ConsultReplyDTO> page = consultReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /consult-replies/:id} : get the "id" consultReply.
     *
     * @param id the id of the consultReplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultReplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consult-replies/{id}")
    public ResponseEntity<IResponseMessage> getConsultReply(@PathVariable Long id) {
        log.debug("REST request to get ConsultReply : {}", id);
        Optional<ConsultReplyDTO> consultReplyDTO = consultReplyService.findOne(id);
        if(!consultReplyDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(consultReplyDTO.get()));
    }

    /**
     * {@code DELETE  /consult-replies/:id} : delete the "id" consultReply.
     *
     * @param id the id of the consultReplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consult-replies/{id}")
    public ResponseEntity<IResponseMessage> deleteConsultReply(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        ConsultReply consultReply = this.consultReplyRepository.findById(id).get();
        if(consultReply.getConsult() != null){
            Consult consult = this.consultRepository.findById(id).get();
            if(this.authenticateUtils.checkPermisionForDataOfUser(consult.getStepData().getRequestData() != null ? consult.getStepData().getRequestData().getId() : null))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);
        }

        log.debug("REST request to delete ConsultReply : {}", id);
        consultReplyService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/consult-replies?query=:query} : search for the consultReply corresponding
     * to the query.
     *
     * @param query the query of the consultReply search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consult-replies")
    public ResponseEntity<List<ConsultReplyDTO>> searchConsultReplies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConsultReplies for query {}", query);
        Page<ConsultReplyDTO> page = consultReplyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/consult-replies")
    public ResponseEntity<IResponseMessage> getAll(){
        log.info("ConsultReplyCustomRest: getAll()");
        List<ConsultReplyDTO> result  = this.consultReplyCustomService.getAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/consult-replies")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ConsultReplyDTO consultReplyDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.consultReplySearchService.simpleQuerySearch(consultReplyDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", consultReplyDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/consult-replies")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ConsultReplyDTO consultReplyDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.consultReplySearchService.simpleQuerySearchWithParam(consultReplyDTO, paramMaps, pageable);
        log.debug("ConsultReplyCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", consultReplyDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
