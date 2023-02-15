package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.ReqdataChangeHisRepository;
import com.vsm.business.service.ReqdataChangeHisService;
import com.vsm.business.service.custom.search.service.ReqdataChangeHisSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.ReqdataChangeHis}.
 */
@RestController
@RequestMapping("/api")
public class ReqdataChangeHisCustomRest {

    private final Logger log = LoggerFactory.getLogger(ReqdataChangeHisCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceReqdataChangeHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqdataChangeHisService reqdataChangeHisService;

    private final ReqdataChangeHisRepository reqdataChangeHisRepository;

    private final ReqdataChangeHisSearchService reqdataChangeHisSearchService;

    public ReqdataChangeHisCustomRest(
        ReqdataChangeHisService reqdataChangeHisService,
        ReqdataChangeHisRepository reqdataChangeHisRepository,
        ReqdataChangeHisSearchService reqdataChangeHisSearchService
    ) {
        this.reqdataChangeHisService = reqdataChangeHisService;
        this.reqdataChangeHisRepository = reqdataChangeHisRepository;
        this.reqdataChangeHisSearchService = reqdataChangeHisSearchService;
    }

    /**
     * {@code POST  /reqdata-change-his} : Create a new reqdataChangeHis.
     *
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqdataChangeHisDTO, or with status {@code 400 (Bad Request)} if the reqdataChangeHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reqdata-change-his")
    public ResponseEntity<IResponseMessage> createReqdataChangeHis(@Valid @RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReqdataChangeHis : {}", reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(reqdataChangeHisDTO));
        }
        ReqdataChangeHisDTO result = reqdataChangeHisService.save(reqdataChangeHisDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(reqdataChangeHisDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /reqdata-change-his/:id} : Updates an existing reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to save.
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataChangeHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataChangeHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqdataChangeHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reqdata-change-his/{id}")
    public ResponseEntity<IResponseMessage> updateReqdataChangeHis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReqdataChangeHis : {}, {}", id, reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(reqdataChangeHisDTO));
        }
        if (!Objects.equals(id, reqdataChangeHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(reqdataChangeHisDTO));
        }

        if (!reqdataChangeHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(reqdataChangeHisDTO));
        }

        ReqdataChangeHisDTO result = reqdataChangeHisService.save(reqdataChangeHisDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(reqdataChangeHisDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /reqdata-change-his/:id} : Partial updates given fields of an existing reqdataChangeHis, field will ignore if it is null
     *
     * @param id the id of the reqdataChangeHisDTO to save.
     * @param reqdataChangeHisDTO the reqdataChangeHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqdataChangeHisDTO,
     * or with status {@code 400 (Bad Request)} if the reqdataChangeHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reqdataChangeHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reqdataChangeHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reqdata-change-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateReqdataChangeHis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReqdataChangeHis partially : {}, {}", id, reqdataChangeHisDTO);
        if (reqdataChangeHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(reqdataChangeHisDTO));
        }
        if (!Objects.equals(id, reqdataChangeHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(reqdataChangeHisDTO));
        }

        if (!reqdataChangeHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(reqdataChangeHisDTO));
        }

        Optional<ReqdataChangeHisDTO> result = reqdataChangeHisService.partialUpdate(reqdataChangeHisDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(reqdataChangeHisDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /reqdata-change-his} : get all the reqdataChangeHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqdataChangeHis in body.
     */
    @GetMapping("/reqdata-change-his")
    public ResponseEntity<IResponseMessage> getAllReqdataChangeHis(Pageable pageable) {
        log.debug("REST request to get a page of ReqdataChangeHis");
        Page<ReqdataChangeHisDTO> page = reqdataChangeHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /reqdata-change-his/:id} : get the "id" reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqdataChangeHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reqdata-change-his/{id}")
    public ResponseEntity<IResponseMessage> getReqdataChangeHis(@PathVariable Long id) {
        log.debug("REST request to get ReqdataChangeHis : {}", id);
        Optional<ReqdataChangeHisDTO> reqdataChangeHisDTO = reqdataChangeHisService.findOne(id);

        if (!reqdataChangeHisDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(reqdataChangeHisDTO.get()));
    }

    /**
     * {@code DELETE  /reqdata-change-his/:id} : delete the "id" reqdataChangeHis.
     *
     * @param id the id of the reqdataChangeHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reqdata-change-his/{id}")
    public ResponseEntity<IResponseMessage> deleteReqdataChangeHis(@PathVariable Long id) {
        log.debug("REST request to delete ReqdataChangeHis : {}", id);
        reqdataChangeHisService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/reqdata-change-his?query=:query} : search for the reqdataChangeHis corresponding
     * to the query.
     *
     * @param query the query of the reqdataChangeHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reqdata-change-his")
    public ResponseEntity<List<ReqdataChangeHisDTO>> searchReqdataChangeHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReqdataChangeHis for query {}", query);
        Page<ReqdataChangeHisDTO> page = reqdataChangeHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/reqdata-change-his")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.reqdataChangeHisSearchService.simpleQuerySearch(reqdataChangeHisDTO, pageable);
        log.debug("ReqdataChangeHisCustomRest: customSearch(IBaseSearchDTO: {}): {}", reqdataChangeHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/reqdata-change-his")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ReqdataChangeHisDTO reqdataChangeHisDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.reqdataChangeHisSearchService.simpleQuerySearchWithParam(reqdataChangeHisDTO, paramMaps, pageable);
        log.debug("ReqdataChangeHisCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", reqdataChangeHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
