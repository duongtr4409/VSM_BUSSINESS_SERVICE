package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailDeleteMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.service.StepService;
import com.vsm.business.service.custom.StepCustomService;
import com.vsm.business.web.rest.errors.CustomURISyntaxException;
import com.vsm.business.service.custom.search.service.StepSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.utils.GenerateCodeUtils;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StepCustomRest {
    private final Logger log = LoggerFactory.getLogger(StepCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepService stepService;

    private final StepRepository stepRepository;

    private StepCustomService stepCustomService;

    private StepSearchService stepSearchService;

    private Map<String, StepDTO> stepDTOMap = new HashMap<>();

    public StepCustomRest(StepService stepService, StepRepository stepRepository, StepCustomService stepCustomService, StepSearchService stepSearchService) {
        this.stepService = stepService;
        this.stepCustomService = stepCustomService;
        this.stepRepository = stepRepository;
        this.stepSearchService = stepSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadstepMap(){
        List<StepDTO> stepDTOList = this.stepCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setCreated(null);
            ele.setModified(null);
            ele.setMailTemplate(null);
            ele.setMailTemplateCustomer(null);
            ele.setOrganization(null);
            return ele;
        }).collect(Collectors.toList());
        this.stepDTOMap = new HashMap<>();
        stepDTOList.stream().forEach(ele -> {
            stepDTOMap.put(ele.getStepCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(StepDTO stepDTO){
        if(this.stepDTOMap == null || this.stepDTOMap.size() == 0) this.loadstepMap();
        try {
            String code = generateCodeUtils.generateCode(stepDTO.getStepName(), this.stepDTOMap, StepDTO.class, "getStepCode");
            stepDTO.setStepCode(code);
            this.stepDTOMap.put(code, stepDTO);

            this.stepDTOMap = null;

            return code;
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            log.debug("{}", e);

            this.stepDTOMap = null;

            return null;
        }
    }


    /**
     * {@code POST  /steps} : Create a new step.
     *
     * @param stepDTO the stepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepDTO, or with status {@code 400 (Bad Request)} if the step has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/steps")
    public ResponseEntity<IResponseMessage> createStep(@Valid @RequestBody StepDTO stepDTO) throws URISyntaxException {
        log.debug("REST request to save Step : {}", stepDTO);
        if (stepDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(stepDTO));
        }
        // generate code
        stepDTO.setStepCode(this.generateCode(stepDTO));

        StepDTO result = stepService.save(stepDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(stepDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /steps/:id} : Updates an existing step.
     *
     * @param id      the id of the stepDTO to save.
     * @param stepDTO the stepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDTO,
     * or with status {@code 400 (Bad Request)} if the stepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/steps/{id}")
    public ResponseEntity<IResponseMessage> updateStep(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StepDTO stepDTO
    ) throws CustomURISyntaxException {
        log.debug("REST request to update Step : {}, {}", id, stepDTO);
        if (stepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepDTO));
        }
        if (!Objects.equals(id, stepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepDTO));
        }
        if (!stepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepDTO));
        }
        StepDTO result = stepService.save(stepDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /steps/:id} : Partial updates given fields of an existing step, field will ignore if it is null
     *
     * @param id      the id of the stepDTO to save.
     * @param stepDTO the stepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDTO,
     * or with status {@code 400 (Bad Request)} if the stepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/steps/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateStep(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StepDTO stepDTO
    ) throws CustomURISyntaxException {
        log.debug("REST request to partial update Step partially : {}, {}", id, stepDTO);
        if (stepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stepDTO));
        }
        if (!Objects.equals(id, stepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stepDTO));
        }

        if (!stepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stepDTO));
        }

        Optional<StepDTO> result = stepService.partialUpdate(stepDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /steps} : get all the steps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of steps in body.
     */
    @GetMapping("/steps")
    public ResponseEntity<IResponseMessage> getAllSteps(Pageable pageable) {
        log.debug("REST request to get a page of Steps");
        Page<StepDTO> page = stepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /steps/:id} : get the "id" step.
     *
     * @param id the id of the stepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/steps/{id}")
    public ResponseEntity<IResponseMessage> getStep(@PathVariable Long id) {
        log.debug("REST request to get Step : {}", id);
        Optional<StepDTO> stepDTO = stepService.findOne(id);
        if (!stepDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(stepDTO.get()));
    }

    /**
     * {@code DELETE  /steps/:id} : delete the "id" step.
     *
     * @param id the id of the stepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/steps/{id}")
    public ResponseEntity<IResponseMessage> deleteStep(@PathVariable Long id) {
        log.debug("REST request to delete Step : {}", id);
        if (!stepCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/steps?query=:query} : search for the step corresponding
     * to the query.
     *
     * @param query    the query of the step search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/steps")
    public ResponseEntity<IResponseMessage> searchSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Steps for query {}", query);
        Page<StepDTO> page = stepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/_all/steps")
    public ResponseEntity<IResponseMessage> getAll() {
        List<StepDTO> result = this.stepCustomService.getAll();
        log.debug("StepCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/steps")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<StepDTO> stepDTOS) {
        List<StepDTO> result = this.stepCustomService.deleteAll(stepDTOS);
        log.debug("StepCustomRest: deleteAll({}) {}", stepDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/steps")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<StepDTO> stepDTOList){
        List<StepDTO> result = this.stepCustomService.saveAll(stepDTOList);
        log.debug("StepCustomRest: deleteAll({})", stepDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/steps")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody StepDTO stepDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepSearchService.simpleQuerySearch(stepDTO, pageable);
        log.debug("StepCustomRest: customSearch(IBaseSearchDTO: {}): {}", stepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/steps")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody StepDTO stepDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stepSearchService.simpleQuerySearchWithParam(stepDTO, paramMaps, pageable);
        log.debug("StepCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", stepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
