package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.OfficialDispatchHisRepository;
import com.vsm.business.service.OfficialDispatchHisService;
import com.vsm.business.service.custom.OfficialDispatchHisCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.OfficialDispatchHisSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatchHis}.
 */
@RestController
@RequestMapping("/api")
public class OfficialDispatchHisCustomRest {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchHisCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatchHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchHisService officialDispatchHisService;

    private final OfficialDispatchHisRepository officialDispatchHisRepository;

    private final OfficialDispatchHisCustomService officialDispatchHisCustomService;

    private final OfficialDispatchHisSearchService officialDispatchHisSearchService;

    public OfficialDispatchHisCustomRest(
        OfficialDispatchHisService officialDispatchHisService,
        OfficialDispatchHisRepository officialDispatchHisRepository,
        OfficialDispatchHisCustomService officialDispatchHisCustomService,
        OfficialDispatchHisSearchService officialDispatchHisSearchService
    ) {
        this.officialDispatchHisService = officialDispatchHisService;
        this.officialDispatchHisRepository = officialDispatchHisRepository;
        this.officialDispatchHisCustomService = officialDispatchHisCustomService;
        this.officialDispatchHisSearchService = officialDispatchHisSearchService;
    }

    /**
     * {@code POST  /official-dispatch-his} : Create a new officialDispatchHis.
     *
     * @param officialDispatchHisDTO the officialDispatchHisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchHisDTO, or with status {@code 400 (Bad Request)} if the officialDispatchHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatch-his")
    public ResponseEntity<IResponseMessage> createOfficialDispatchHis(@RequestBody OfficialDispatchHisDTO officialDispatchHisDTO)
        throws URISyntaxException {
        log.debug("REST request to save OfficialDispatchHis : {}", officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() != null) {
            throw new BadRequestAlertException("A new officialDispatchHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficialDispatchHisDTO result = officialDispatchHisService.save(officialDispatchHisDTO);
        if(result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(officialDispatchHisDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * {@code PUT  /official-dispatch-his/:id} : Updates an existing officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to save.
     * @param officialDispatchHisDTO the officialDispatchHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchHisDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchHisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatch-his/{id}")
    public ResponseEntity<IResponseMessage> updateOfficialDispatchHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchHisDTO officialDispatchHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatchHis : {}, {}", id, officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(officialDispatchHisDTO));
        }
        if (!Objects.equals(id, officialDispatchHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(officialDispatchHisDTO));
        }

        if (!officialDispatchHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(officialDispatchHisDTO));
        }

        OfficialDispatchHisDTO result = officialDispatchHisService.save(officialDispatchHisDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(officialDispatchHisDTO));
        }
        return ResponseEntity.ok(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /official-dispatch-his/:id} : Partial updates given fields of an existing officialDispatchHis, field will ignore if it is null
     *
     * @param id the id of the officialDispatchHisDTO to save.
     * @param officialDispatchHisDTO the officialDispatchHisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchHisDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchHisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchHisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchHisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatch-his/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateOfficialDispatchHis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchHisDTO officialDispatchHisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatchHis partially : {}, {}", id, officialDispatchHisDTO);
        if (officialDispatchHisDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(officialDispatchHisDTO));
        }
        if (!Objects.equals(id, officialDispatchHisDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(officialDispatchHisDTO));
        }

        if (!officialDispatchHisRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(officialDispatchHisDTO));
        }

        Optional<OfficialDispatchHisDTO> result = officialDispatchHisService.partialUpdate(officialDispatchHisDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(officialDispatchHisDTO));
        }
        return ResponseEntity.ok(new UpdatedMessage(result));
    }

    /**
     * {@code GET  /official-dispatch-his} : get all the officialDispatchHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatchHis in body.
     */
    @GetMapping("/official-dispatch-his")
    public ResponseEntity<IResponseMessage> getAllOfficialDispatchHis(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatchHis");
        Page<OfficialDispatchHisDTO> page = officialDispatchHisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /official-dispatch-his/:id} : get the "id" officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchHisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatch-his/{id}")
    public ResponseEntity<IResponseMessage> getOfficialDispatchHis(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatchHis : {}", id);
        Optional<OfficialDispatchHisDTO> officialDispatchHisDTO = officialDispatchHisService.findOne(id);
        if(!officialDispatchHisDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(officialDispatchHisDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(officialDispatchHisDTO));
    }

    /**
     * {@code DELETE  /official-dispatch-his/:id} : delete the "id" officialDispatchHis.
     *
     * @param id the id of the officialDispatchHisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatch-his/{id}")
    public ResponseEntity<IResponseMessage> deleteOfficialDispatchHis(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatchHis : {}", id);
        officialDispatchHisService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/official-dispatch-his?query=:query} : search for the officialDispatchHis corresponding
     * to the query.
     *
     * @param query the query of the officialDispatchHis search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatch-his")
    public ResponseEntity<List<OfficialDispatchHisDTO>> searchOfficialDispatchHis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatchHis for query {}", query);
        Page<OfficialDispatchHisDTO> page = officialDispatchHisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/official-dispatch-his")
    public ResponseEntity<IResponseMessage> getAll() {
        List<OfficialDispatchHisDTO> result = this.officialDispatchHisCustomService.getAll();
        log.debug("OfficialDispatchCustomRest: getAll() {}", result);;
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/official-dispatch-his")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<OfficialDispatchHisDTO> stepInProcessDTOS) {
        List<OfficialDispatchHisDTO> result = this.officialDispatchHisCustomService.deleteAll(stepInProcessDTOS);
        log.debug("OfficialDispatchCustomRest: deleteAll({}) {}", stepInProcessDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/official-dispatch/{officialDispatchId}/_all/official-dispatch-his")
    public ResponseEntity<IResponseMessage> getAllByProcessInfoId(@PathVariable("officialDispatchId") Long officialDispatchId) {
        List<OfficialDispatchHisDTO> result = this.officialDispatchHisCustomService.getAllByOfficalDispatch(officialDispatchId);
        log.debug("OfficialDispatchCustomRest: getAllByProcessInfoId({}) {}", officialDispatchId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/official-dispatch-his")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody OfficialDispatchHisDTO officialDispatchHisDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.officialDispatchHisSearchService.simpleQuerySearch(officialDispatchHisDTO, pageable);
        log.debug("OfficialDispatchHisCustomRest: customSearch(IBaseSearchDTO: {}): {}", officialDispatchHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/official-dispatch-his")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody OfficialDispatchHisDTO officialDispatchHisDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.officialDispatchHisSearchService.simpleQuerySearchWithParam(officialDispatchHisDTO, paramMaps, pageable);
        log.debug("OfficialDispatchHisCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", officialDispatchHisDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
