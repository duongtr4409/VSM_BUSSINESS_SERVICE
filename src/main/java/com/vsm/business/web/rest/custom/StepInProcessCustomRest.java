package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.service.StepInProcessService;
import com.vsm.business.service.custom.StepInProcessCustomService;
import com.vsm.business.service.custom.UserInStepCustomService;
import com.vsm.business.service.custom.search.service.StepInProcessSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepInProcessDTO;
import com.vsm.business.service.dto.UserInStepDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.StepInProcess}.
 */
@RestController
@RequestMapping("/api")
public class StepInProcessCustomRest {

    private final Logger log = LoggerFactory.getLogger(StepInProcessCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStepInProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepInProcessService stepInProcessService;

    private final StepInProcessRepository stepInProcessRepository;

    private final StepInProcessCustomService stepInProcessCustomService;

    private final UserInStepCustomService userInStepCustomService;

    private final StepInProcessSearchService stepInProcessSearchService;

    public StepInProcessCustomRest(StepInProcessService stepInProcessService, StepInProcessRepository stepInProcessRepository, StepInProcessCustomService stepInProcessCustomService, UserInStepCustomService userInStepCustomService, StepInProcessSearchService stepInProcessSearchService) {
        this.stepInProcessService = stepInProcessService;
        this.stepInProcessRepository = stepInProcessRepository;
        this.stepInProcessCustomService = stepInProcessCustomService;
        this.userInStepCustomService = userInStepCustomService;
        this.stepInProcessSearchService = stepInProcessSearchService;
    }

    /**
     * {@code POST  /step-in-processes} : Create a new stepInProcess.
     *
     * @param stepInProcessDTO the stepInProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepInProcessDTO, or with status {@code 400 (Bad Request)} if the stepInProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-in-processes")
    public ResponseEntity<IResponseMessage> createStepInProcess(@Valid @RequestBody StepInProcessDTO stepInProcessDTO)
        throws URISyntaxException {
        log.debug("REST request to save StepInProcess : {}", stepInProcessDTO);
        if (stepInProcessDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(stepInProcessDTO));
        }
        StepInProcessDTO result = stepInProcessService.save(stepInProcessDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(stepInProcessDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /step-in-processes/:id} : Updates an existing stepInProcess.
     *
     * @param id               the id of the stepInProcessDTO to save.
     * @param stepInProcessDTO the stepInProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepInProcessDTO,
     * or with status {@code 400 (Bad Request)} if the stepInProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepInProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-in-processes/{id}")
    public ResponseEntity<IResponseMessage> updateStepInProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StepInProcessDTO stepInProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StepInProcess : {}, {}", id, stepInProcessDTO);
        if (stepInProcessDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepInProcessDTO));
        }
        if (!Objects.equals(id, stepInProcessDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepInProcessDTO));
        }

        if (!stepInProcessRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepInProcessDTO));
        }

        StepInProcessDTO result = stepInProcessService.save(stepInProcessDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepInProcessDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /step-in-processes/:id} : Partial updates given fields of an existing stepInProcess, field will ignore if it is null
     *
     * @param id               the id of the stepInProcessDTO to save.
     * @param stepInProcessDTO the stepInProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepInProcessDTO,
     * or with status {@code 400 (Bad Request)} if the stepInProcessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepInProcessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepInProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-in-processes/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateStepInProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StepInProcessDTO stepInProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepInProcess partially : {}, {}", id, stepInProcessDTO);
        if (stepInProcessDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepInProcessDTO));
        }
        if (!Objects.equals(id, stepInProcessDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepInProcessDTO));
        }

        if (!stepInProcessRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepInProcessDTO));
        }

        Optional<StepInProcessDTO> result = stepInProcessService.partialUpdate(stepInProcessDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepInProcessDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /step-in-processes} : get all the stepInProcesses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepInProcesses in body.
     */
    @GetMapping("/step-in-processes")
    public ResponseEntity<IResponseMessage> getAllStepInProcesses(Pageable pageable) {
        log.debug("REST request to get a page of StepInProcesses");
        Page<StepInProcessDTO> page = stepInProcessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /step-in-processes/:id} : get the "id" stepInProcess.
     *
     * @param id the id of the stepInProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepInProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-in-processes/{id}")
    public ResponseEntity<IResponseMessage> getStepInProcess(@PathVariable Long id) {
        log.debug("REST request to get StepInProcess : {}", id);
        Optional<StepInProcessDTO> stepInProcessDTO = stepInProcessService.findOne(id);

        if (!stepInProcessDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        List<UserInStepDTO> userInStepDTOS = userInStepCustomService.findAllByStepInProcessId(stepInProcessDTO.get().getId());
        stepInProcessDTO.get().setUserInStepDTOs(userInStepDTOS);
        return ResponseEntity.ok().body(new LoadedMessage(stepInProcessDTO.get()));
    }

    /**
     * {@code DELETE  /step-in-processes/:id} : delete the "id" stepInProcess.
     *
     * @param id the id of the stepInProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-in-processes/{id}")
    public ResponseEntity<IResponseMessage> deleteStepInProcess(@PathVariable Long id) {
        log.debug("REST request to delete StepInProcess : {}", id);
        //stepInProcessService.delete(id);
        stepInProcessCustomService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/step-in-processes?query=:query} : search for the stepInProcess corresponding
     * to the query.
     *
     * @param query    the query of the stepInProcess search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/step-in-processes")
    public ResponseEntity<List<StepInProcessDTO>> searchStepInProcesses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StepInProcesses for query {}", query);
        Page<StepInProcessDTO> page = stepInProcessService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/step-in-processes")
    public ResponseEntity<IResponseMessage> getAll() {
        List<StepInProcessDTO> result = this.stepInProcessCustomService.getAll();
        log.debug("StepInProcessCustomRest: getAll() {}", result);;
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/step-in-processes")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<StepInProcessDTO> stepInProcessDTOS) {
        List<StepInProcessDTO> result = this.stepInProcessCustomService.deleteAll(stepInProcessDTOS);
        log.debug("StepInProcessCustomRest: deleteAll({}) {}", stepInProcessDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/process-infos/{processInfoId}/_all/step-in-processes")
    public ResponseEntity<IResponseMessage> getAllByProcessInfoId(@PathVariable("processInfoId") Long processInfoId) {
        List<StepInProcessDTO> result = this.stepInProcessCustomService.getAllByProcessInfoId(processInfoId);
        result.forEach(ele -> {
            List<UserInStepDTO> userInStepDTOS = userInStepCustomService.findAllByStepInProcessId(ele.getId());
            ele.setUserInStepDTOs(userInStepDTOS);
        });
        log.debug("StepInProcessCustomRest: getAllByProcessInfoId({}) {}", processInfoId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/step-in-processes")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody StepInProcessDTO stepInProcessDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepInProcessSearchService.simpleQuerySearch(stepInProcessDTO, pageable);
        log.debug("StepInProcessCustomRest: customSearch(IBaseSearchDTO: {}): {}", stepInProcessDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/step-in-processes")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody StepInProcessDTO stepInProcessDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepInProcessSearchService.simpleQuerySearchWithParam(stepInProcessDTO, paramMaps, pageable);
        log.debug("StepInProcessCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", stepInProcessDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
