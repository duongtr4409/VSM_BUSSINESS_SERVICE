package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.SignData;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.service.SignDataService;
import com.vsm.business.service.custom.SignDataCustomService;
import com.vsm.business.service.custom.search.service.SignDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.SignDataDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.SignData}.
 */
@RestController
@RequestMapping("/api")
public class SignDataCustomRest {

    private final Logger log = LoggerFactory.getLogger(SignDataCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceSignData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignDataService signDataService;

    private final SignDataRepository signDataRepository;

    private final SignDataCustomService signDataCustomService;

    private final SignDataSearchService signDataSearchService;

    private final AuthenticateUtils authenticateUtils;

    public SignDataCustomRest(SignDataService signDataService, SignDataRepository signDataRepository, SignDataCustomService signDataCustomService, SignDataSearchService signDataSearchService, AuthenticateUtils authenticateUtils) {
        this.signDataService = signDataService;
        this.signDataRepository = signDataRepository;
        this.signDataCustomService = signDataCustomService;
        this.signDataSearchService = signDataSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /sign-data} : Create a new signData.
     *
     * @param signDataDTO the signDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signDataDTO, or with status {@code 400 (Bad Request)} if the signData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sign-data")
    public ResponseEntity<IResponseMessage> createSignData(@RequestBody SignDataDTO signDataDTO) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(signDataDTO.getRequestData() != null ? signDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save SignData : {}", signDataDTO);
        if (signDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(signDataDTO));
        }
        SignDataDTO result = signDataService.save(signDataDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(signDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /sign-data/:id} : Updates an existing signData.
     *
     * @param id the id of the signDataDTO to save.
     * @param signDataDTO the signDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signDataDTO,
     * or with status {@code 400 (Bad Request)} if the signDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sign-data/{id}")
    public ResponseEntity<IResponseMessage> updateSignData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignDataDTO signDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(signDataDTO.getRequestData() != null ? signDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update SignData : {}, {}", id, signDataDTO);
        if (signDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(signDataDTO));
        }
        if (!Objects.equals(id, signDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(signDataDTO));
        }

        if (!signDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(signDataDTO));
        }

        SignDataDTO result = signDataService.save(signDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(signDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /sign-data/:id} : Partial updates given fields of an existing signData, field will ignore if it is null
     *
     * @param id the id of the signDataDTO to save.
     * @param signDataDTO the signDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signDataDTO,
     * or with status {@code 400 (Bad Request)} if the signDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the signDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the signDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sign-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateSignData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignDataDTO signDataDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(signDataDTO.getRequestData() != null ? signDataDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update SignData partially : {}, {}", id, signDataDTO);
        if (signDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(signDataDTO));
        }
        if (!Objects.equals(id, signDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(signDataDTO));
        }

        if (!signDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(signDataDTO));
        }

        Optional<SignDataDTO> result = signDataService.partialUpdate(signDataDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(signDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /sign-data} : get all the signData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signData in body.
     */
    @GetMapping("/sign-data")
    public ResponseEntity<IResponseMessage> getAllSignData(Pageable pageable) {
        log.debug("REST request to get a page of SignData");
        Page<SignDataDTO> page = signDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /sign-data/:id} : get the "id" signData.
     *
     * @param id the id of the signDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sign-data/{id}")
    public ResponseEntity<IResponseMessage> getSignData(@PathVariable Long id) {
        log.debug("REST request to get SignData : {}", id);
        Optional<SignDataDTO> signDataDTO = signDataService.findOne(id);
        if (!signDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(signDataDTO.get()));
    }

    /**
     * {@code DELETE  /sign-data/:id} : delete the "id" signData.
     *
     * @param id the id of the signDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sign-data/{id}")
    public ResponseEntity<IResponseMessage> deleteSignData(@PathVariable Long id) {

        // kiểm tra user có quyền thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        SignData signData = this.signDataRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(signData.getRequestData() != null ? signData.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete SignData : {}", id);
        signDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/sign-data?query=:query} : search for the signData corresponding
     * to the query.
     *
     * @param query the query of the signData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sign-data")
    public ResponseEntity<List<SignDataDTO>> searchSignData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SignData for query {}", query);
        Page<SignDataDTO> page = signDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/sign-data")
    public ResponseEntity<IResponseMessage> getAll(){
        log.debug("SignDataCustomRest: getAll()");
        List<SignDataDTO> result = this.signDataCustomService.getAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/sign-data")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        log.debug("SignDataCustomRest: getAll()");
        List<SignDataDTO> result = this.signDataCustomService.getAllByRequetData(requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/sign-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody SignDataDTO signDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.signDataSearchService.simpleQuerySearch(signDataDTO, pageable);
        log.debug("SignDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", signDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/sign-data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody SignDataDTO signDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.signDataSearchService.simpleQuerySearchWithParam(signDataDTO, paramMaps, pageable);
        log.debug("SignDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", signDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
