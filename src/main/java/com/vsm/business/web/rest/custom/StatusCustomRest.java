package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.service.StatusService;
import com.vsm.business.service.custom.StatusCustomService;
import com.vsm.business.service.custom.search.service.StatusSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StatusDTO;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.StepData}.
 */
@RestController
@RequestMapping("/api")
public class StatusCustomRest {

    private final Logger log = LoggerFactory.getLogger(StatusCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusService statusService;

    private final StatusRepository statusRepository;

    private final StatusCustomService statusCustomService;

    private final StatusSearchService statusSearchService;

    public StatusCustomRest(StatusService statusService, StatusRepository statusRepository, StatusCustomService statusCustomService, StatusSearchService statusSearchService) {
        this.statusService = statusService;
        this.statusRepository = statusRepository;
        this.statusCustomService = statusCustomService;
        this.statusSearchService = statusSearchService;
    }

    /**
     * {@code POST  /step-data} : Create a new stepData.
     *
     * @param statusDTO the stepDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepDataDTO, or with status {@code 400 (Bad Request)} if the stepData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status")
    public ResponseEntity<IResponseMessage> createStatus(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to save Status : {}", statusDTO);
        if (statusDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(statusDTO));
        }
        StatusDTO result = statusService.save(statusDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(statusDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /step-data/:id} : Updates an existing stepData.
     *
     * @param id the id of the stepDataDTO to save.
     * @param statusDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status/{id}")
    public ResponseEntity<IResponseMessage> updateStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatusDTO statusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Status : {}, {}", id, statusDTO);
        if (statusDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(statusDTO));
        }
        if (!Objects.equals(id, statusDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(statusDTO));
        }

        if (!statusRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(statusDTO));
        }

        StatusDTO result = statusService.save(statusDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(statusDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /step-data/:id} : Partial updates given fields of an existing stepData, field will ignore if it is null
     *
     * @param id the id of the stepDataDTO to save.
     * @param statusDTO the stepDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepDataDTO,
     * or with status {@code 400 (Bad Request)} if the stepDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stepDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatusDTO statusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Status partially : {}, {}", id, statusDTO);
        if (statusDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(statusDTO));
        }
        if (!Objects.equals(id, statusDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(statusDTO));
        }

        if (!statusRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(statusDTO));
        }

        Optional<StatusDTO> result = statusService.partialUpdate(statusDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(statusDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /step-data} : get all the stepData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepData in body.
     */
    @GetMapping("/status")
    public ResponseEntity<IResponseMessage> getAllStatus(Pageable pageable) {
        log.debug("REST request to get a page of Status");
        Page<StatusDTO> page = statusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /step-data/:id} : get the "id" stepData.
     *
     * @param id the id of the stepDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status/{id}")
    public ResponseEntity<IResponseMessage> getStatus(@PathVariable Long id) {
        log.debug("REST request to get Status : {}", id);
        Optional<StatusDTO> statusDTO = statusService.findOne(id);

        if (!statusDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(statusDTO.get()));
    }

    /**
     * {@code DELETE  /step-data/:id} : delete the "id" stepData.
     *
     * @param id the id of the stepDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status/{id}")
    public ResponseEntity<IResponseMessage> deleteStatus(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        statusService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/step-data?query=:query} : search for the stepData corresponding
     * to the query.
     *
     * @param query the query of the stepData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/status")
    public ResponseEntity<List<StatusDTO>> searchStatus(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Status for query {}", query);
        Page<StatusDTO> page = statusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/status")
    public ResponseEntity<IResponseMessage> getAll() {
        List<StatusDTO> result = this.statusCustomService.getAll();
        log.debug("StatusCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/status")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody StatusDTO statusDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.statusSearchService.simpleQuerySearch(statusDTO, pageable);
        log.debug("StatusCustomRest: customSearch(IBaseSearchDTO: {}): {}", statusDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/status")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody StatusDTO statusDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.statusSearchService.simpleQuerySearchWithParam(statusDTO, paramMaps, pageable);
        log.debug("StatusCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", statusDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
