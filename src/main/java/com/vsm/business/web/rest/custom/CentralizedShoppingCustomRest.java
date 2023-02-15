package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.service.CentralizedShoppingService;
import com.vsm.business.service.custom.CentralizedShoppingDTOCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.CentralizedShoppingSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.CentralizedShopping}.
 */
@RestController
@RequestMapping("/api")
public class CentralizedShoppingCustomRest {

    private final Logger log = LoggerFactory.getLogger(CentralizedShoppingCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCentralizedShopping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentralizedShoppingService centralizedShoppingService;

    private final CentralizedShoppingRepository centralizedShoppingRepository;

    private final CentralizedShoppingDTOCustomService centralizedShoppingDTOCustomService;

    private final CentralizedShoppingSearchService centralizedShoppingSearchService;

    public CentralizedShoppingCustomRest(
        CentralizedShoppingService centralizedShoppingService,
        CentralizedShoppingRepository centralizedShoppingRepository,
        CentralizedShoppingDTOCustomService centralizedShoppingDTOCustomService,
        CentralizedShoppingSearchService centralizedShoppingSearchService
    ) {
        this.centralizedShoppingService = centralizedShoppingService;
        this.centralizedShoppingRepository = centralizedShoppingRepository;
        this.centralizedShoppingDTOCustomService = centralizedShoppingDTOCustomService;
        this.centralizedShoppingSearchService = centralizedShoppingSearchService;
    }

    /**
     * {@code POST  /centralized-shoppings} : Create a new centralizedShopping.
     *
     * @param centralizedShoppingDTO the centralizedShoppingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centralizedShoppingDTO, or with status {@code 400 (Bad Request)} if the centralizedShopping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centralized-shoppings")
    public ResponseEntity<IResponseMessage> createCentralizedShopping(@RequestBody CentralizedShoppingDTO centralizedShoppingDTO)
        throws URISyntaxException {
        log.debug("REST request to save CentralizedShopping : {}", centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() != null) {
            throw new BadRequestAlertException("A new centralizedShopping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentralizedShoppingDTO result = centralizedShoppingService.save(centralizedShoppingDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(centralizedShoppingDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /centralized-shoppings/:id} : Updates an existing centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to save.
     * @param centralizedShoppingDTO the centralizedShoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralizedShoppingDTO,
     * or with status {@code 400 (Bad Request)} if the centralizedShoppingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centralizedShoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centralized-shoppings/{id}")
    public ResponseEntity<IResponseMessage> updateCentralizedShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentralizedShoppingDTO centralizedShoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CentralizedShopping : {}, {}", id, centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralizedShoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralizedShoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CentralizedShoppingDTO result = centralizedShoppingService.save(centralizedShoppingDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(centralizedShoppingDTO));
        }

        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /centralized-shoppings/:id} : Partial updates given fields of an existing centralizedShopping, field will ignore if it is null
     *
     * @param id the id of the centralizedShoppingDTO to save.
     * @param centralizedShoppingDTO the centralizedShoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralizedShoppingDTO,
     * or with status {@code 400 (Bad Request)} if the centralizedShoppingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centralizedShoppingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centralizedShoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/centralized-shoppings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateCentralizedShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentralizedShoppingDTO centralizedShoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentralizedShopping partially : {}, {}", id, centralizedShoppingDTO);
        if (centralizedShoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralizedShoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralizedShoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentralizedShoppingDTO> result = centralizedShoppingService.partialUpdate(centralizedShoppingDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok(new FailUpdateMessage(centralizedShoppingDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /centralized-shoppings} : get all the centralizedShoppings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centralizedShoppings in body.
     */
    @GetMapping("/centralized-shoppings")
    public ResponseEntity<IResponseMessage> getAllCentralizedShoppings(Pageable pageable) {
        log.debug("REST request to get a page of CentralizedShoppings");
        Page<CentralizedShoppingDTO> page = centralizedShoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /centralized-shoppings/:id} : get the "id" centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centralizedShoppingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centralized-shoppings/{id}")
    public ResponseEntity<IResponseMessage> getCentralizedShopping(@PathVariable Long id) {
        log.debug("REST request to get CentralizedShopping : {}", id);
        Optional<CentralizedShoppingDTO> centralizedShoppingDTO = centralizedShoppingService.findOne(id);
        if(!centralizedShoppingDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(centralizedShoppingDTO.get()));
    }

    /**
     * {@code DELETE  /centralized-shoppings/:id} : delete the "id" centralizedShopping.
     *
     * @param id the id of the centralizedShoppingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centralized-shoppings/{id}")
    public ResponseEntity<IResponseMessage> deleteCentralizedShopping(@PathVariable Long id) {
        log.debug("REST request to delete CentralizedShopping : {}", id);
        centralizedShoppingService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/centralized-shoppings?query=:query} : search for the centralizedShopping corresponding
     * to the query.
     *
     * @param query the query of the centralizedShopping search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/centralized-shoppings")
    public ResponseEntity<List<CentralizedShoppingDTO>> searchCentralizedShoppings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CentralizedShoppings for query {}", query);
        Page<CentralizedShoppingDTO> page = centralizedShoppingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/centralized-shoppings")
    public ResponseEntity<IResponseMessage> getAll(){
        List<CentralizedShoppingDTO> result = this.centralizedShoppingDTOCustomService.getAll();
        log.debug("CentralizedShoppingCustomRest(): {}");
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/centralized-shoppings")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody CentralizedShoppingDTO centralizedShoppingDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.centralizedShoppingSearchService.simpleQuerySearch(centralizedShoppingDTO, pageable);
        log.debug("CentralizedShoppingCustomRest: customSearch(IBaseSearchDTO: {}): {}", centralizedShoppingDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/centralized-shoppings")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody CentralizedShoppingDTO centralizedShoppingDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.centralizedShoppingSearchService.simpleQuerySearchWithParam(centralizedShoppingDTO, paramMaps, pageable);
        log.debug("CentralizedShoppingCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", centralizedShoppingDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
