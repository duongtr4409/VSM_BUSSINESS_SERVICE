package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.MallAndStallRepository;
import com.vsm.business.service.MallAndStallService;
import com.vsm.business.service.custom.MallAndStallCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.MallAndStallSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.MallAndStallDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.MallAndStall}.
 */
@RestController
@RequestMapping("/api")
public class MallAndStallCustomRest {

    private final Logger log = LoggerFactory.getLogger(MallAndStallCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMallAndStall";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MallAndStallService mallAndStallService;

    private final MallAndStallRepository mallAndStallRepository;

    private final MallAndStallCustomService mallAndStallCustomService;

    private final MallAndStallSearchService mallAndStallSearchService;

    public MallAndStallCustomRest(MallAndStallService mallAndStallService, MallAndStallRepository mallAndStallRepository, MallAndStallCustomService mallAndStallCustomService, MallAndStallSearchService mallAndStallSearchService) {
        this.mallAndStallService = mallAndStallService;
        this.mallAndStallRepository = mallAndStallRepository;
        this.mallAndStallCustomService = mallAndStallCustomService;
        this.mallAndStallSearchService = mallAndStallSearchService;
    }

    /**
     * {@code POST  /mall-and-stalls} : Create a new mallAndStall.
     *
     * @param mallAndStallDTO the mallAndStallDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mallAndStallDTO, or with status {@code 400 (Bad Request)} if the mallAndStall has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mall-and-stalls")
    public ResponseEntity<IResponseMessage> createMallAndStall(@RequestBody MallAndStallDTO mallAndStallDTO) throws URISyntaxException {
        log.debug("REST request to save MallAndStall : {}", mallAndStallDTO);
        if (mallAndStallDTO.getId() != null) {
            throw new BadRequestAlertException("A new mallAndStall cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MallAndStallDTO result = mallAndStallService.save(mallAndStallDTO);
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /mall-and-stalls/:id} : Updates an existing mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to save.
     * @param mallAndStallDTO the mallAndStallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallAndStallDTO,
     * or with status {@code 400 (Bad Request)} if the mallAndStallDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mallAndStallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mall-and-stalls/{id}")
    public ResponseEntity<IResponseMessage> updateMallAndStall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MallAndStallDTO mallAndStallDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MallAndStall : {}, {}", id, mallAndStallDTO);
        if (mallAndStallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallAndStallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallAndStallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MallAndStallDTO result = mallAndStallService.save(mallAndStallDTO);
        if(result == null){
            return ResponseEntity.ok(new FailUpdateMessage(mallAndStallDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /mall-and-stalls/:id} : Partial updates given fields of an existing mallAndStall, field will ignore if it is null
     *
     * @param id the id of the mallAndStallDTO to save.
     * @param mallAndStallDTO the mallAndStallDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallAndStallDTO,
     * or with status {@code 400 (Bad Request)} if the mallAndStallDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mallAndStallDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mallAndStallDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mall-and-stalls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateMallAndStall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MallAndStallDTO mallAndStallDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MallAndStall partially : {}, {}", id, mallAndStallDTO);
        if (mallAndStallDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallAndStallDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallAndStallRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MallAndStallDTO> result = mallAndStallService.partialUpdate(mallAndStallDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(mallAndStallDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code GET  /mall-and-stalls} : get all the mallAndStalls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mallAndStalls in body.
     */
    @GetMapping("/mall-and-stalls")
    public ResponseEntity<IResponseMessage> getAllMallAndStalls(Pageable pageable) {
        log.debug("REST request to get a page of MallAndStalls");
        Page<MallAndStallDTO> page = mallAndStallService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /mall-and-stalls/:id} : get the "id" mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mallAndStallDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mall-and-stalls/{id}")
    public ResponseEntity<IResponseMessage> getMallAndStall(@PathVariable Long id) {
        log.debug("REST request to get MallAndStall : {}", id);
        Optional<MallAndStallDTO> mallAndStallDTO = mallAndStallService.findOne(id);
        if(!mallAndStallDTO.isPresent()){
            ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(mallAndStallDTO.get()));
    }

    /**
     * {@code DELETE  /mall-and-stalls/:id} : delete the "id" mallAndStall.
     *
     * @param id the id of the mallAndStallDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mall-and-stalls/{id}")
    public ResponseEntity<IResponseMessage> deleteMallAndStall(@PathVariable Long id) {
        log.debug("REST request to delete MallAndStall : {}", id);
        mallAndStallService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/mall-and-stalls?query=:query} : search for the mallAndStall corresponding
     * to the query.
     *
     * @param query the query of the mallAndStall search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/mall-and-stalls")
    public ResponseEntity<List<MallAndStallDTO>> searchMallAndStalls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MallAndStalls for query {}", query);
        Page<MallAndStallDTO> page = mallAndStallService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/mall-and-stalls")
    public ResponseEntity<IResponseMessage> getAll(){
        List<MallAndStallDTO> result = this.mallAndStallCustomService.getAll();
        log.debug("MailTemplateCustomRest: saveAll({})");
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/mall-and-stalls")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody MallAndStallDTO mallAndStallDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.mallAndStallSearchService.simpleQuerySearch(mallAndStallDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", mallAndStallDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/mall-and-stalls")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody MallAndStallDTO mallAndStallDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.mallAndStallSearchService.simpleQuerySearchWithParam(mallAndStallDTO, paramMaps, pageable);
        log.debug("MallAndStallCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", mallAndStallDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
