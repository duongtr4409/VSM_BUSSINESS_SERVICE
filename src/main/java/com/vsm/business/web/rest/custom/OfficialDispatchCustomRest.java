package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.OfficialDispatchRepository;
import com.vsm.business.service.OfficialDispatchService;
import com.vsm.business.service.custom.OfficialDispatchCustomService;
import com.vsm.business.service.custom.search.service.OfficialDispatchSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OfficialDispatchDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.OfficialDispatch}.
 */
@RestController
@RequestMapping("/api")
public class OfficialDispatchCustomRest {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOfficialDispatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficialDispatchService officialDispatchService;

    private final OfficialDispatchRepository officialDispatchRepository;

    private final OfficialDispatchCustomService officialDispatchCustomService;

    private final OfficialDispatchSearchService officialDispatchSearchService;

    public OfficialDispatchCustomRest(
        OfficialDispatchService officialDispatchService,
        OfficialDispatchRepository officialDispatchRepository,
        OfficialDispatchCustomService officialDispatchCustomService,
        OfficialDispatchSearchService officialDispatchSearchService
    ) {
        this.officialDispatchService = officialDispatchService;
        this.officialDispatchRepository = officialDispatchRepository;
        this.officialDispatchCustomService = officialDispatchCustomService;
        this.officialDispatchSearchService = officialDispatchSearchService;
    }

    /**
     * {@code POST  /official-dispatches} : Create a new officialDispatch.
     *
     * @param officialDispatchDTO the officialDispatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officialDispatchDTO, or with status {@code 400 (Bad Request)} if the officialDispatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/official-dispatches")
    public ResponseEntity<IResponseMessage> createOfficialDispatch(@RequestBody OfficialDispatchDTO officialDispatchDTO)
        throws URISyntaxException {
        log.debug("REST request to save OfficialDispatch : {}", officialDispatchDTO);
        if(officialDispatchDTO.getId() != null){
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(officialDispatchDTO));
        }
        OfficialDispatchDTO result = officialDispatchService.save(officialDispatchDTO);
        if(result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(officialDispatchDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * {@code PUT  /official-dispatches/:id} : Updates an existing officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to save.
     * @param officialDispatchDTO the officialDispatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/official-dispatches/{id}")
    public ResponseEntity<IResponseMessage> updateOfficialDispatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchDTO officialDispatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficialDispatch : {}, {}", id, officialDispatchDTO);
        if (officialDispatchDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(officialDispatchDTO));
        }
        if (!Objects.equals(id, officialDispatchDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(officialDispatchDTO));
        }

        if (!officialDispatchRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(officialDispatchDTO));
        }

        OfficialDispatchDTO result = officialDispatchService.save(officialDispatchDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(officialDispatchDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /official-dispatches/:id} : Partial updates given fields of an existing officialDispatch, field will ignore if it is null
     *
     * @param id the id of the officialDispatchDTO to save.
     * @param officialDispatchDTO the officialDispatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officialDispatchDTO,
     * or with status {@code 400 (Bad Request)} if the officialDispatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officialDispatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officialDispatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/official-dispatches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateOfficialDispatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfficialDispatchDTO officialDispatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficialDispatch partially : {}, {}", id, officialDispatchDTO);
        if (officialDispatchDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(officialDispatchDTO));
        }
        if (!Objects.equals(id, officialDispatchDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(officialDispatchDTO));
        }

        if (!officialDispatchRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(officialDispatchDTO));
        }

        Optional<OfficialDispatchDTO> result = officialDispatchService.partialUpdate(officialDispatchDTO);
        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(officialDispatchDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /official-dispatches} : get all the officialDispatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officialDispatches in body.
     */
    @GetMapping("/official-dispatches")
    public ResponseEntity<IResponseMessage> getAllOfficialDispatches(Pageable pageable) {
        log.debug("REST request to get a page of OfficialDispatches");
        Page<OfficialDispatchDTO> page = officialDispatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /official-dispatches/:id} : get the "id" officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officialDispatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/official-dispatches/{id}")
    public ResponseEntity<IResponseMessage> getOfficialDispatch(@PathVariable Long id) {
        log.debug("REST request to get OfficialDispatch : {}", id);
        Optional<OfficialDispatchDTO> result = officialDispatchService.findOne(id);
        if(!result.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result.get()));
    }

    /**
     * {@code DELETE  /official-dispatches/:id} : delete the "id" officialDispatch.
     *
     * @param id the id of the officialDispatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/official-dispatches/{id}")
    public ResponseEntity<IResponseMessage> deleteOfficialDispatch(@PathVariable Long id) {
        log.debug("REST request to delete OfficialDispatch : {}", id);
        officialDispatchService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/official-dispatches?query=:query} : search for the officialDispatch corresponding
     * to the query.
     *
     * @param query the query of the officialDispatch search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/official-dispatches")
    public ResponseEntity<List<OfficialDispatchDTO>> searchOfficialDispatches(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OfficialDispatches for query {}", query);
        Page<OfficialDispatchDTO> page = officialDispatchService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/official-dispatches")
    public ResponseEntity<IResponseMessage> getAll() {
        List<OfficialDispatchDTO> result = this.officialDispatchCustomService.getAll();
        log.debug("OfficialDispatchCustomRest: getAll() {}", result);;
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/official-dispatches")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<OfficialDispatchDTO> officialDispatchDTOS) {
        List<OfficialDispatchDTO> result = this.officialDispatchCustomService.deleteAll(officialDispatchDTOS);
        log.debug("OfficialDispatchCustomRest: deleteAll({}) {}", officialDispatchDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/official-dispatches")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody OfficialDispatchDTO officialDispatchDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.officialDispatchSearchService.simpleQuerySearch(officialDispatchDTO, pageable);
        log.debug("OfficialDispatchCustomRest: customSearch(IBaseSearchDTO: {}): {}", officialDispatchDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/official-dispatches")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody OfficialDispatchDTO officialDispatchDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.officialDispatchSearchService.simpleQuerySearchWithParam(officialDispatchDTO, paramMaps, pageable);
        log.debug("OfficialDispatchCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", officialDispatchDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
