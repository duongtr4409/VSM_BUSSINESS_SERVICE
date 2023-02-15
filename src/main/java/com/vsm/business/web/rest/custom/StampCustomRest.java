package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.StampRepository;
import com.vsm.business.service.StampService;
import com.vsm.business.service.custom.StampCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.StampSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.StampDTO;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.Stamp}.
 */
@RestController
@RequestMapping("/api")
public class StampCustomRest {

    private final Logger log = LoggerFactory.getLogger(StampCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceStamp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StampService stampService;

    private final StampRepository stampRepository;

    private final StampCustomService stampCustomService;

    private final StampSearchService stampSearchService;

    public StampCustomRest(StampService stampService, StampRepository stampRepository, StampCustomService stampCustomService, StampSearchService stampSearchService) {
        this.stampService = stampService;
        this.stampRepository = stampRepository;
        this.stampCustomService = stampCustomService;
        this.stampSearchService = stampSearchService;
    }

    /**
     * {@code POST  /stamps} : Create a new stamp.
     *
     * @param stampDTO the stampDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stampDTO, or with status {@code 400 (Bad Request)} if the stamp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stamps")
    public ResponseEntity<IResponseMessage> createStamp(@RequestBody StampDTO stampDTO) throws URISyntaxException {
        log.debug("REST request to save Stamp : {}", stampDTO);
        if (stampDTO.getId() != null) {
            throw new BadRequestAlertException("A new stamp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StampDTO result = stampService.save(stampDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(stampDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /stamps/:id} : Updates an existing stamp.
     *
     * @param id the id of the stampDTO to save.
     * @param stampDTO the stampDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stampDTO,
     * or with status {@code 400 (Bad Request)} if the stampDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stampDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stamps/{id}")
    public ResponseEntity<IResponseMessage> updateStamp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StampDTO stampDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Stamp : {}, {}", id, stampDTO);
        if (stampDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stampDTO));
        }
        if (!Objects.equals(id, stampDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stampDTO));
        }

        if (!stampRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stampDTO));
        }

        StampDTO result = stampService.save(stampDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stampDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /stamps/:id} : Partial updates given fields of an existing stamp, field will ignore if it is null
     *
     * @param id the id of the stampDTO to save.
     * @param stampDTO the stampDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stampDTO,
     * or with status {@code 400 (Bad Request)} if the stampDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stampDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stampDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stamps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateStamp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StampDTO stampDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stamp partially : {}, {}", id, stampDTO);
        if (stampDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(stampDTO));
        }
        if (!Objects.equals(id, stampDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(stampDTO));
        }

        if (!stampRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(stampDTO));
        }

        Optional<StampDTO> result = stampService.partialUpdate(stampDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(stampDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /stamps} : get all the stamps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stamps in body.
     */
    @GetMapping("/stamps")
    public ResponseEntity<IResponseMessage> getAllStamps(Pageable pageable) {
        log.debug("REST request to get a page of Stamps");
        Page<StampDTO> page = stampService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /stamps/:id} : get the "id" stamp.
     *
     * @param id the id of the stampDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stampDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stamps/{id}")
    public ResponseEntity<IResponseMessage> getStamp(@PathVariable Long id) {
        log.debug("REST request to get Stamp : {}", id);
        Optional<StampDTO> stampDTO = stampService.findOne(id);
        if (!stampDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(stampDTO.get()));
    }

    /**
     * {@code DELETE  /stamps/:id} : delete the "id" stamp.
     *
     * @param id the id of the stampDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stamps/{id}")
    public ResponseEntity<IResponseMessage> deleteStamp(@PathVariable Long id) {
        log.debug("REST request to delete Stamp : {}", id);
        stampService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/stamps?query=:query} : search for the stamp corresponding
     * to the query.
     *
     * @param query the query of the stamp search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stamps")
    public ResponseEntity<List<StampDTO>> searchStamps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Stamps for query {}", query);
        Page<StampDTO> page = stampService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/stamps")
    public ResponseEntity<IResponseMessage> getAll() {
        List<StampDTO> result = this.stampCustomService.getAll();
        log.debug("StampCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/stamps")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<StampDTO> stampDTOList) {
        List<StampDTO> result = this.stampCustomService.saveAll(stampDTOList);
        log.debug("StampCustomRest: saveAll({})", stampDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/stamps")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody StampDTO stampDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stampSearchService.simpleQuerySearch(stampDTO, pageable);
        log.debug("StampCustomRest: customSearch(IBaseSearchDTO: {}): {}", stampDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/stamps")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody StampDTO stampDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.stampSearchService.simpleQuerySearchWithParam(stampDTO, paramMaps, pageable);
        log.debug("StampCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", stampDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
