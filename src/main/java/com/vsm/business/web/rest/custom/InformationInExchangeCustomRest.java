package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.InformationInExchange;
import com.vsm.business.repository.InformationInExchangeRepository;
import com.vsm.business.service.InformationInExchangeService;
import com.vsm.business.service.custom.InformationInExchangeCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.InformationInExchangeSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.InformationInExchangeDTO;
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

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.InformationInExchange}.
 */
@RestController
@RequestMapping("/api")
public class InformationInExchangeCustomRest {

    private final Logger log = LoggerFactory.getLogger(InformationInExchangeCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceInformationInExchange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformationInExchangeService informationInExchangeService;

    private final InformationInExchangeRepository informationInExchangeRepository;

    private final InformationInExchangeCustomService informationInExchangeCustomService;

    private final InformationInExchangeSearchService informationInExchangeSearchService;

    private final AuthenticateUtils authenticateUtils;

    public InformationInExchangeCustomRest(
        InformationInExchangeService informationInExchangeService,
        InformationInExchangeRepository informationInExchangeRepository,
        InformationInExchangeCustomService informationInExchangeCustomService,
        InformationInExchangeSearchService informationInExchangeSearchService,
        AuthenticateUtils authenticateUtils
    ) {
        this.informationInExchangeService = informationInExchangeService;
        this.informationInExchangeRepository = informationInExchangeRepository;
        this.informationInExchangeCustomService = informationInExchangeCustomService;
        this.informationInExchangeSearchService = informationInExchangeSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /information-in-exchanges} : Create a new informationInExchange.
     *
     * @param informationInExchangeDTO the informationInExchangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informationInExchangeDTO, or with status {@code 400 (Bad Request)} if the informationInExchange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/information-in-exchanges")
    public ResponseEntity<IResponseMessage> createInformationInExchange(
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(informationInExchangeDTO.getRequestData() != null ? informationInExchangeDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save InformationInExchange : {}", informationInExchangeDTO);
        if (informationInExchangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new informationInExchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformationInExchangeDTO result = informationInExchangeService.save(informationInExchangeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(informationInExchangeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /information-in-exchanges/:id} : Updates an existing informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to save.
     * @param informationInExchangeDTO the informationInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the informationInExchangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informationInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/information-in-exchanges/{id}")
    public ResponseEntity<IResponseMessage> updateInformationInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(informationInExchangeDTO.getRequestData() != null ? informationInExchangeDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);


        log.debug("REST request to update InformationInExchange : {}, {}", id, informationInExchangeDTO);
        if (informationInExchangeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(informationInExchangeDTO));
        }
        if (!Objects.equals(id, informationInExchangeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(informationInExchangeDTO));
        }

        if (!informationInExchangeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(informationInExchangeDTO));
        }

        InformationInExchangeDTO result = informationInExchangeService.save(informationInExchangeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(informationInExchangeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /information-in-exchanges/:id} : Partial updates given fields of an existing informationInExchange, field will ignore if it is null
     *
     * @param id the id of the informationInExchangeDTO to save.
     * @param informationInExchangeDTO the informationInExchangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationInExchangeDTO,
     * or with status {@code 400 (Bad Request)} if the informationInExchangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the informationInExchangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the informationInExchangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/information-in-exchanges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateInformationInExchange(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationInExchangeDTO informationInExchangeDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(informationInExchangeDTO.getRequestData() != null ? informationInExchangeDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update InformationInExchange partially : {}, {}", id, informationInExchangeDTO);
        if (informationInExchangeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(informationInExchangeDTO));
        }
        if (!Objects.equals(id, informationInExchangeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(informationInExchangeDTO));
        }

        if (!informationInExchangeRepository.existsById(id)) {
            return ResponseEntity.ok().body(new FailUpdateMessage(informationInExchangeDTO));
        }

        Optional<InformationInExchangeDTO> result = informationInExchangeService.partialUpdate(informationInExchangeDTO);


        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /information-in-exchanges} : get all the informationInExchanges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informationInExchanges in body.
     */
    @GetMapping("/information-in-exchanges")
    public ResponseEntity<IResponseMessage> getAllInformationInExchanges(Pageable pageable) {
        log.debug("REST request to get a page of InformationInExchanges");
        Page<InformationInExchangeDTO> page = informationInExchangeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /information-in-exchanges/:id} : get the "id" informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informationInExchangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/information-in-exchanges/{id}")
    public ResponseEntity<IResponseMessage> getInformationInExchange(@PathVariable Long id) {
        log.debug("REST request to get InformationInExchange : {}", id);
        Optional<InformationInExchangeDTO> informationInExchangeDTO = informationInExchangeService.findOne(id);
        if (!informationInExchangeDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(informationInExchangeDTO.get()));
    }

    /**
     * {@code DELETE  /information-in-exchanges/:id} : delete the "id" informationInExchange.
     *
     * @param id the id of the informationInExchangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/information-in-exchanges/{id}")
    public ResponseEntity<IResponseMessage> deleteInformationInExchange(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không\
        InformationInExchange informationInExchange = this.informationInExchangeRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(informationInExchange.getRequestData() != null ? informationInExchange.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete InformationInExchange : {}", id);
        informationInExchangeService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/information-in-exchanges?query=:query} : search for the informationInExchange corresponding
     * to the query.
     *
     * @param query the query of the informationInExchange search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/information-in-exchanges")
    public ResponseEntity<List<InformationInExchangeDTO>> searchInformationInExchanges(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InformationInExchanges for query {}", query);
        Page<InformationInExchangeDTO> page = informationInExchangeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/_all/information-in-exchanges")
    public ResponseEntity<IResponseMessage> getAll() {
        List<InformationInExchangeDTO> result = this.informationInExchangeCustomService.getAll();
        log.debug("InformationInExchangeCustomRest: getAll() {}", result);;
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/information-in-exchanges")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<InformationInExchangeDTO> informationInExchangeDTOS) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan phiếu yêu cầu không
        if(informationInExchangeDTOS.stream().anyMatch(ele -> {
            return !this.authenticateUtils.checkPermisionForDataOfUser(ele.getRequestData() != null ? ele.getRequestData().getId() : null);
        }))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        List<InformationInExchangeDTO> result = this.informationInExchangeCustomService.deleteAll(informationInExchangeDTOS);
        log.debug("InformationInExchangeCustomRest: deleteAll({}) {}", informationInExchangeDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/information-in-exchanges")
    public ResponseEntity<IResponseMessage> getAllByRequestDataId(@PathVariable("requestDataId") Long requestDataId) {
        List<InformationInExchangeDTO> result = this.informationInExchangeCustomService.getAllByRequestDataId(requestDataId);
        log.debug("InformationInExchangeCustomRest: getAllByRequestDataId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/information-in-exchanges")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody InformationInExchangeDTO informationInExchangeDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.informationInExchangeSearchService.simpleQuerySearch(informationInExchangeDTO, pageable);
        log.debug("InformationInExchangeCustomRest: customSearch(IBaseSearchDTO: {}): {}", informationInExchangeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/information-in-exchanges")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody InformationInExchangeDTO informationInExchangeDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.informationInExchangeSearchService.simpleQuerySearchWithParam(informationInExchangeDTO, paramMaps, pageable);
        log.debug("InformationInExchangeCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", informationInExchangeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
