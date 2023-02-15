package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.DispatchBookRepository;
import com.vsm.business.service.DispatchBookService;
import com.vsm.business.service.custom.DispathBookCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.DispatchBookSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.DispatchBookDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.DispatchBook}.
 */
@RestController
@RequestMapping("/api")
public class DispatchBookCustomRest {

    private final Logger log = LoggerFactory.getLogger(DispatchBookCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceDispatchBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispatchBookService dispatchBookService;

    private final DispatchBookRepository dispatchBookRepository;

    private final DispathBookCustomService dispathBookCustomService;

    private final DispatchBookSearchService dispatchBookSearchService;

    public DispatchBookCustomRest(DispatchBookService dispatchBookService, DispatchBookRepository dispatchBookRepository, DispathBookCustomService dispathBookCustomService, DispatchBookSearchService dispatchBookSearchService) {
        this.dispatchBookService = dispatchBookService;
        this.dispatchBookRepository = dispatchBookRepository;
        this.dispathBookCustomService = dispathBookCustomService;
        this.dispatchBookSearchService = dispatchBookSearchService;
    }

    /**
     * {@code POST  /dispatch-books} : Create a new dispatchBook.
     *
     * @param dispatchBookDTO the dispatchBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispatchBookDTO, or with status {@code 400 (Bad Request)} if the dispatchBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispatch-books")
    public ResponseEntity<IResponseMessage> createDispatchBook(@RequestBody DispatchBookDTO dispatchBookDTO) throws URISyntaxException {
        log.debug("REST request to save DispatchBook : {}", dispatchBookDTO);
        if (dispatchBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispatchBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispatchBookDTO result = dispatchBookService.save(dispatchBookDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(dispatchBookDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(dispatchBookDTO));
    }

    /**
     * {@code PUT  /dispatch-books/:id} : Updates an existing dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to save.
     * @param dispatchBookDTO the dispatchBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispatch-books/{id}")
    public ResponseEntity<IResponseMessage> updateDispatchBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookDTO dispatchBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DispatchBook : {}, {}", id, dispatchBookDTO);
        if (dispatchBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DispatchBookDTO result = dispatchBookService.save(dispatchBookDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(dispatchBookDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(dispatchBookDTO));
    }

    /**
     * {@code PATCH  /dispatch-books/:id} : Partial updates given fields of an existing dispatchBook, field will ignore if it is null
     *
     * @param id the id of the dispatchBookDTO to save.
     * @param dispatchBookDTO the dispatchBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatchBookDTO,
     * or with status {@code 400 (Bad Request)} if the dispatchBookDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispatchBookDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispatchBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dispatch-books/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateDispatchBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispatchBookDTO dispatchBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DispatchBook partially : {}, {}", id, dispatchBookDTO);
        if (dispatchBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispatchBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispatchBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispatchBookDTO> result = dispatchBookService.partialUpdate(dispatchBookDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(dispatchBookDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(dispatchBookDTO));
    }

    /**
     * {@code GET  /dispatch-books} : get all the dispatchBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispatchBooks in body.
     */
    @GetMapping("/dispatch-books")
    public ResponseEntity<IResponseMessage> getAllDispatchBooks(Pageable pageable) {
        log.debug("REST request to get a page of DispatchBooks");
        Page<DispatchBookDTO> page = dispatchBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /dispatch-books/:id} : get the "id" dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispatchBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispatch-books/{id}")
    public ResponseEntity<IResponseMessage> getDispatchBook(@PathVariable Long id) {
        log.debug("REST request to get DispatchBook : {}", id);
        Optional<DispatchBookDTO> dispatchBookDTO = dispatchBookService.findOne(id);
        if(!dispatchBookDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(dispatchBookDTO.get()));
    }

    /**
     * {@code DELETE  /dispatch-books/:id} : delete the "id" dispatchBook.
     *
     * @param id the id of the dispatchBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispatch-books/{id}")
    public ResponseEntity<IResponseMessage> deleteDispatchBook(@PathVariable Long id) {
        log.debug("REST request to delete DispatchBook : {}", id);
        dispatchBookService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/dispatch-books?query=:query} : search for the dispatchBook corresponding
     * to the query.
     *
     * @param query the query of the dispatchBook search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dispatch-books")
    public ResponseEntity<List<DispatchBookDTO>> searchDispatchBooks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DispatchBooks for query {}", query);
        Page<DispatchBookDTO> page = dispatchBookService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/dispatch-books")
    public ResponseEntity<IResponseMessage> getAll(){
        log.info("DispatchBookCustomRest: getAll()");
        List<DispatchBookDTO> result = this.dispathBookCustomService.getAll();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/dispatch-books")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<DispatchBookDTO> dispatchBookDTOS){
        log.debug("DispatchBookCustomRest: saveAll({})", dispatchBookDTOS);
        List<DispatchBookDTO> result = this.dispathBookCustomService.saveAll(dispatchBookDTOS);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/dispatch-books")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody DispatchBookDTO dispatchBookDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.dispatchBookSearchService.simpleQuerySearch(dispatchBookDTO, pageable);
        log.debug("DispatchBookCustomRest: customSearch(IBaseSearchDTO: {}): {}", dispatchBookDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/dispatch-books")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody DispatchBookDTO dispatchBookDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.dispatchBookSearchService.simpleQuerySearchWithParam(dispatchBookDTO, paramMaps, pageable);
        log.debug("DispatchBookCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", dispatchBookDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
