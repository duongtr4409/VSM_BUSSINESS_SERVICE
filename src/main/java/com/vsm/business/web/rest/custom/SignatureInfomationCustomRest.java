package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.SignatureInfomationRepository;
import com.vsm.business.service.SignatureInfomationService;
import com.vsm.business.service.custom.SignatureInfomationCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.SignatureInfomationSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.SignatureInfomationDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.SignatureInfomation}.
 */
@RestController
@RequestMapping("/api")
public class SignatureInfomationCustomRest {

    private final Logger log = LoggerFactory.getLogger(SignatureInfomationCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceSignatureInfomation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignatureInfomationService signatureInfomationService;

    private final SignatureInfomationRepository signatureInfomationRepository;

    private final SignatureInfomationCustomService signatureInfomationCustomService;

    private final SignatureInfomationSearchService signatureInfomationSearchService;

    public SignatureInfomationCustomRest(
        SignatureInfomationService signatureInfomationService,
        SignatureInfomationRepository signatureInfomationRepository,
        SignatureInfomationCustomService signatureInfomationCustomService,
        SignatureInfomationSearchService signatureInfomationSearchService
    ) {
        this.signatureInfomationService = signatureInfomationService;
        this.signatureInfomationRepository = signatureInfomationRepository;
        this.signatureInfomationCustomService = signatureInfomationCustomService;
        this.signatureInfomationSearchService = signatureInfomationSearchService;
    }

    /**
     * {@code POST  /signature-infomations} : Create a new signatureInfomation.
     *
     * @param signatureInfomationDTO the signatureInfomationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signatureInfomationDTO, or with status {@code 400 (Bad Request)} if the signatureInfomation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signature-infomations")
    public ResponseEntity<IResponseMessage> createSignatureInfomation(@RequestBody SignatureInfomationDTO signatureInfomationDTO)
        throws URISyntaxException {
        log.debug("REST request to save SignatureInfomation : {}", signatureInfomationDTO);
        if (signatureInfomationDTO.getId() != null) {
            throw new BadRequestAlertException("A new signatureInfomation cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        SignatureInfomationDTO result = signatureInfomationService.save(signatureInfomationDTO);
        SignatureInfomationDTO result = signatureInfomationCustomService.customSave(signatureInfomationDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(signatureInfomationDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /signature-infomations/:id} : Updates an existing signatureInfomation.
     *
     * @param id the id of the signatureInfomationDTO to save.
     * @param signatureInfomationDTO the signatureInfomationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signatureInfomationDTO,
     * or with status {@code 400 (Bad Request)} if the signatureInfomationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signatureInfomationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signature-infomations/{id}")
    public ResponseEntity<IResponseMessage> updateSignatureInfomation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignatureInfomationDTO signatureInfomationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SignatureInfomation : {}, {}", id, signatureInfomationDTO);
        if (signatureInfomationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signatureInfomationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signatureInfomationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

//        SignatureInfomationDTO result = signatureInfomationService.save(signatureInfomationDTO);
        SignatureInfomationDTO result = signatureInfomationCustomService.customSave(signatureInfomationDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(signatureInfomationDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /signature-infomations/:id} : Partial updates given fields of an existing signatureInfomation, field will ignore if it is null
     *
     * @param id the id of the signatureInfomationDTO to save.
     * @param signatureInfomationDTO the signatureInfomationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signatureInfomationDTO,
     * or with status {@code 400 (Bad Request)} if the signatureInfomationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the signatureInfomationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the signatureInfomationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/signature-infomations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateSignatureInfomation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignatureInfomationDTO signatureInfomationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SignatureInfomation partially : {}, {}", id, signatureInfomationDTO);
        if (signatureInfomationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signatureInfomationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signatureInfomationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SignatureInfomationDTO> result = signatureInfomationService.partialUpdate(signatureInfomationDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(signatureInfomationDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /signature-infomations} : get all the signatureInfomations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signatureInfomations in body.
     */
    @GetMapping("/signature-infomations")
    public ResponseEntity<IResponseMessage> getAllSignatureInfomations(Pageable pageable) {
        log.debug("REST request to get a page of SignatureInfomations");
        Page<SignatureInfomationDTO> page = signatureInfomationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /signature-infomations/:id} : get the "id" signatureInfomation.
     *
     * @param id the id of the signatureInfomationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signatureInfomationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signature-infomations/{id}")
    public ResponseEntity<IResponseMessage> getSignatureInfomation(@PathVariable Long id) {
        log.debug("REST request to get SignatureInfomation : {}", id);
        Optional<SignatureInfomationDTO> signatureInfomationDTO = signatureInfomationService.findOne(id);
        if(signatureInfomationDTO.isPresent()){
            ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(signatureInfomationDTO.get()));
    }

    /**
     * {@code DELETE  /signature-infomations/:id} : delete the "id" signatureInfomation.
     *
     * @param id the id of the signatureInfomationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signature-infomations/{id}")
    public ResponseEntity<IResponseMessage> deleteSignatureInfomation(@PathVariable Long id) {
        log.debug("REST request to delete SignatureInfomation : {}", id);
        signatureInfomationService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/signature-infomations?query=:query} : search for the signatureInfomation corresponding
     * to the query.
     *
     * @param query the query of the signatureInfomation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/signature-infomations")
    public ResponseEntity<List<SignatureInfomationDTO>> searchSignatureInfomations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SignatureInfomations for query {}", query);
        Page<SignatureInfomationDTO> page = signatureInfomationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/signature-infomations")
    public ResponseEntity<IResponseMessage> getAll(){
        List<SignatureInfomationDTO> result = this.signatureInfomationCustomService.getAll();
        log.debug("SignatureInfomationCustomRest: getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all/signature-infomations")
    public ResponseEntity<IResponseMessage> getAllByUser(@PathVariable("userId") Long userId){
        List<SignatureInfomationDTO> result = this.signatureInfomationCustomService.getAllByUser(userId);
        log.debug("SignatureInfomationCustomRest: getAllByUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/signature-infomations")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody SignatureInfomationDTO signatureInfomationDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.signatureInfomationSearchService.simpleQuerySearch(signatureInfomationDTO, pageable);
        log.debug("SignatureInfomationCustomRest: customSearch(IBaseSearchDTO: {}): {}", signatureInfomationDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/signature-infomations")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody SignatureInfomationDTO signatureInfomationDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.signatureInfomationSearchService.simpleQuerySearchWithParam(signatureInfomationDTO, paramMaps, pageable);
        log.debug("SignatureInfomationCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", signatureInfomationDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
