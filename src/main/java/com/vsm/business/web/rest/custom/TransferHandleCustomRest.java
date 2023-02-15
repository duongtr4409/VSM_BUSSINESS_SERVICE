package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.TransferHandleRepository;
import com.vsm.business.service.TransferHandleService;
import com.vsm.business.service.custom.TransferHandleCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.TransferHandleSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.TransferHandleDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.TransferHandle}.
 */
@RestController
@RequestMapping("/api")
public class TransferHandleCustomRest {

    private final Logger log = LoggerFactory.getLogger(TransferHandleCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceTransferHandle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferHandleService transferHandleService;

    private final TransferHandleRepository transferHandleRepository;

    private final TransferHandleCustomService transferHandleCustomService;

    private final TransferHandleSearchService transferHandleSearchService;

    public TransferHandleCustomRest(TransferHandleService transferHandleService, TransferHandleRepository transferHandleRepository, TransferHandleCustomService transferHandleCustomService, TransferHandleSearchService transferHandleSearchService) {
        this.transferHandleService = transferHandleService;
        this.transferHandleRepository = transferHandleRepository;
        this.transferHandleCustomService = transferHandleCustomService;
        this.transferHandleSearchService = transferHandleSearchService;
    }

    /**
     * {@code POST  /transfer-handles} : Create a new transferHandle.
     *
     * @param transferHandleDTO the transferHandleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferHandleDTO, or with status {@code 400 (Bad Request)} if the transferHandle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-handles")
    public ResponseEntity<IResponseMessage> createTransferHandle(@RequestBody TransferHandleDTO transferHandleDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferHandle : {}", transferHandleDTO);
        if (transferHandleDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferHandle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferHandleDTO result = transferHandleService.save(transferHandleDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(transferHandleDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /transfer-handles/:id} : Updates an existing transferHandle.
     *
     * @param id the id of the transferHandleDTO to save.
     * @param transferHandleDTO the transferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the transferHandleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-handles/{id}")
    public ResponseEntity<IResponseMessage> updateTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferHandleDTO transferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferHandle : {}, {}", id, transferHandleDTO);
        if (transferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferHandleDTO result = transferHandleService.save(transferHandleDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(transferHandleDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /transfer-handles/:id} : Partial updates given fields of an existing transferHandle, field will ignore if it is null
     *
     * @param id the id of the transferHandleDTO to save.
     * @param transferHandleDTO the transferHandleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferHandleDTO,
     * or with status {@code 400 (Bad Request)} if the transferHandleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferHandleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferHandleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-handles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateTransferHandle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferHandleDTO transferHandleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferHandle partially : {}, {}", id, transferHandleDTO);
        if (transferHandleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferHandleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferHandleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferHandleDTO> result = transferHandleService.partialUpdate(transferHandleDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(transferHandleDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /transfer-handles} : get all the transferHandles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferHandles in body.
     */
    @GetMapping("/transfer-handles")
    public ResponseEntity<IResponseMessage> getAllTransferHandles(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TransferHandles");
        Page<TransferHandleDTO> page;
        if (eagerload) {
            page = transferHandleService.findAllWithEagerRelationships(pageable);
        } else {
            page = transferHandleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /transfer-handles/:id} : get the "id" transferHandle.
     *
     * @param id the id of the transferHandleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferHandleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-handles/{id}")
    public ResponseEntity<IResponseMessage> getTransferHandle(@PathVariable Long id) {
        log.debug("REST request to get TransferHandle : {}", id);
        Optional<TransferHandleDTO> transferHandleDTO = transferHandleService.findOne(id);
        if(!transferHandleDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(transferHandleDTO.get()));
    }

    /**
     * {@code DELETE  /transfer-handles/:id} : delete the "id" transferHandle.
     *
     * @param id the id of the transferHandleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-handles/{id}")
    public ResponseEntity<IResponseMessage> deleteTransferHandle(@PathVariable Long id) {
        log.debug("REST request to delete TransferHandle : {}", id);
        transferHandleService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/transfer-handles?query=:query} : search for the transferHandle corresponding
     * to the query.
     *
     * @param query the query of the transferHandle search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transfer-handles")
    public ResponseEntity<List<TransferHandleDTO>> searchTransferHandles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TransferHandles for query {}", query);
        Page<TransferHandleDTO> page = transferHandleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/transfer-handles")
    public ResponseEntity<IResponseMessage> getAll() {
        List<TransferHandleDTO> result = this.transferHandleCustomService.getAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));

    }

    @GetMapping("/_all/request-data/{requestDataId}/transfer-handles")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        List<TransferHandleDTO> result = this.transferHandleCustomService.getAllByRequestData(requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/transfer-handles")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody TransferHandleDTO transferHandleDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.transferHandleSearchService.simpleQuerySearch(transferHandleDTO, pageable);
        log.debug("TransferHandleCustomRest: customSearch(IBaseSearchDTO: {}): {}", transferHandleDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/transfer-handles")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody TransferHandleDTO transferHandleDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.transferHandleSearchService.simpleQuerySearchWithParam(transferHandleDTO, paramMaps, pageable);
        log.debug("TransferHandleCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", transferHandleDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
