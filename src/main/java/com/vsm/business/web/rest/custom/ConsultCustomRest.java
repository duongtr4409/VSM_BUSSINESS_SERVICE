package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.service.ConsultService;
import com.vsm.business.service.custom.ConsultCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.ConsultSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConsultDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.Consult}.
 */
@RestController
@RequestMapping("/api")
public class ConsultCustomRest {

    private final Logger log = LoggerFactory.getLogger(ConsultCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConsult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultService consultService;

    private final ConsultRepository consultRepository;

    private final ConsultCustomService consultCustomService;

    private final ConsultSearchService consultSearchService;

    private final StepDataRepository stepDataRepository;

    private final AuthenticateUtils authenticateUtils;

    public ConsultCustomRest(ConsultService consultService, ConsultRepository consultRepository, ConsultCustomService consultCustomService, ConsultSearchService consultSearchService, StepDataRepository stepDataRepository, AuthenticateUtils authenticateUtils) {
        this.consultService = consultService;
        this.consultRepository = consultRepository;
        this.consultCustomService = consultCustomService;
        this.consultSearchService = consultSearchService;
        this.stepDataRepository = stepDataRepository;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /consults} : Create a new consult.
     *
     * @param consultDTO the consultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultDTO, or with status {@code 400 (Bad Request)} if the consult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consults")
    public ResponseEntity<IResponseMessage> createConsult(@RequestBody ConsultDTO consultDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        StepData stepData = this.stepDataRepository.findById(consultDTO.getStepData().getId()).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save Consult : {}", consultDTO);
        if (consultDTO.getId() != null) {
            throw new BadRequestAlertException("A new consult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultDTO result = consultService.save(consultDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(consultDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /consults/:id} : Updates an existing consult.
     *
     * @param id the id of the consultDTO to save.
     * @param consultDTO the consultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultDTO,
     * or with status {@code 400 (Bad Request)} if the consultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consults/{id}")
    public ResponseEntity<IResponseMessage> updateConsult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultDTO consultDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        StepData stepData = this.stepDataRepository.findById(consultDTO.getStepData().getId()).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update Consult : {}, {}", id, consultDTO);
        if (consultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultDTO result = consultService.save(consultDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(consultDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /consults/:id} : Partial updates given fields of an existing consult, field will ignore if it is null
     *
     * @param id the id of the consultDTO to save.
     * @param consultDTO the consultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultDTO,
     * or with status {@code 400 (Bad Request)} if the consultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consults/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateConsult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultDTO consultDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        StepData stepData = this.stepDataRepository.findById(consultDTO.getStepData().getId()).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update Consult partially : {}, {}", id, consultDTO);
        if (consultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultDTO> result = consultService.partialUpdate(consultDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(consultDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /consults} : get all the consults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consults in body.
     */
    @GetMapping("/consults")
    public ResponseEntity<IResponseMessage> getAllConsults(Pageable pageable) {
        log.debug("REST request to get a page of Consults");
        Page<ConsultDTO> page = consultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /consults/:id} : get the "id" consult.
     *
     * @param id the id of the consultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consults/{id}")
    public ResponseEntity<IResponseMessage> getConsult(@PathVariable Long id) {
        log.debug("REST request to get Consult : {}", id);
        Optional<ConsultDTO> consultDTO = consultService.findOne(id);
        if(!consultDTO.isPresent()){
            ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(consultDTO.get()));
    }

    /**
     * {@code DELETE  /consults/:id} : delete the "id" consult.
     *
     * @param id the id of the consultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consults/{id}")
    public ResponseEntity<IResponseMessage> deleteConsult(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        StepData stepData = this.stepDataRepository.findById(this.consultRepository.findById(id).get().getStepData().getId()).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(stepData.getRequestData() != null ? stepData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete Consult : {}", id);
        consultService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/consults?query=:query} : search for the consult corresponding
     * to the query.
     *
     * @param query the query of the consult search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consults")
    public ResponseEntity<List<ConsultDTO>> searchConsults(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Consults for query {}", query);
        Page<ConsultDTO> page = consultService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/consults")
    public ResponseEntity<IResponseMessage> getAll(){
        log.debug("ConsultCustomRest: getAll()");
        List<ConsultDTO> result = this.consultCustomService.getAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/request-data/{requestDataId}/_all/consults")
    public ResponseEntity<IResponseMessage> getAllByUserAndRequestData(@PathVariable("userId") Long userId, @PathVariable("requestDataId") Long requestDataId){
        List<ConsultDTO> result = this.consultCustomService.getAllByUserAndRequestData(userId, requestDataId);
        log.info("ConsultCustomRest: getAllByUserAndRequestData(userId: {}, requestDataId: {})", userId, requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/consults")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ConsultDTO consultDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.consultSearchService.simpleQuerySearch(consultDTO, pageable);
        log.debug("ConsultCustomRest: customSearch(IBaseSearchDTO: {}): {}", consultDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/consults")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ConsultDTO consultDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.consultSearchService.simpleQuerySearchWithParam(consultDTO, paramMaps, pageable);
        log.debug("ConsultCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", consultDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
