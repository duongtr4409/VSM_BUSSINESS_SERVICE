package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.BusinessPartnerRepository;
import com.vsm.business.service.BusinessPartnerService;
import com.vsm.business.service.custom.BusinessPartnersCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.BusinessPartnerSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.BusinessPartnerDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.BusinessPartner}.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerCustomRest {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceBusinessPartner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessPartnerService businessPartnerService;

    private final BusinessPartnerRepository businessPartnerRepository;

    private final BusinessPartnersCustomService businessPartnersCustomService;

    private final BusinessPartnerSearchService businessPartnerSearchService;

    public BusinessPartnerCustomRest(BusinessPartnerService businessPartnerService, BusinessPartnerRepository businessPartnerRepository, BusinessPartnersCustomService businessPartnersCustomService, BusinessPartnerSearchService businessPartnerSearchService) {
        this.businessPartnerService = businessPartnerService;
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnersCustomService = businessPartnersCustomService;
        this.businessPartnerSearchService = businessPartnerSearchService;
    }

    /**
     * {@code POST  /business-partners} : Create a new businessPartner.
     *
     * @param businessPartnerDTO the businessPartnerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessPartnerDTO, or with status {@code 400 (Bad Request)} if the businessPartner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-partners")
    public ResponseEntity<IResponseMessage> createBusinessPartner(@RequestBody BusinessPartnerDTO businessPartnerDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessPartner : {}", businessPartnerDTO);
        if (businessPartnerDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessPartner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessPartnerDTO result = businessPartnerService.save(businessPartnerDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(businessPartnerDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /business-partners/:id} : Updates an existing businessPartner.
     *
     * @param id the id of the businessPartnerDTO to save.
     * @param businessPartnerDTO the businessPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-partners/{id}")
    public ResponseEntity<IResponseMessage> updateBusinessPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessPartnerDTO businessPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessPartner : {}, {}", id, businessPartnerDTO);
        if (businessPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessPartnerDTO result = businessPartnerService.save(businessPartnerDTO);
        if(result == null){
            ResponseEntity.ok().body(new FailUpdateMessage(businessPartnerDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /business-partners/:id} : Partial updates given fields of an existing businessPartner, field will ignore if it is null
     *
     * @param id the id of the businessPartnerDTO to save.
     * @param businessPartnerDTO the businessPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessPartnerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-partners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateBusinessPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessPartnerDTO businessPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessPartner partially : {}, {}", id, businessPartnerDTO);
        if (businessPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessPartnerDTO> result = businessPartnerService.partialUpdate(businessPartnerDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(businessPartnerDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /business-partners} : get all the businessPartners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessPartners in body.
     */
    @GetMapping("/business-partners")
    public ResponseEntity<IResponseMessage> getAllBusinessPartners(Pageable pageable) {
        log.debug("REST request to get a page of BusinessPartners");
        Page<BusinessPartnerDTO> page = businessPartnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /business-partners/:id} : get the "id" businessPartner.
     *
     * @param id the id of the businessPartnerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessPartnerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-partners/{id}")
    public ResponseEntity<IResponseMessage> getBusinessPartner(@PathVariable Long id) {
        log.debug("REST request to get BusinessPartner : {}", id);
        Optional<BusinessPartnerDTO> businessPartnerDTO = businessPartnerService.findOne(id);
        if(!businessPartnerDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(businessPartnerDTO.get()));
    }

    /**
     * {@code DELETE  /business-partners/:id} : delete the "id" businessPartner.
     *
     * @param id the id of the businessPartnerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-partners/{id}")
    public ResponseEntity<IResponseMessage> deleteBusinessPartner(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartner : {}", id);
        businessPartnerService.delete(id);
        return ResponseEntity.ok(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/business-partners?query=:query} : search for the businessPartner corresponding
     * to the query.
     *
     * @param query the query of the businessPartner search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/business-partners")
    public ResponseEntity<List<BusinessPartnerDTO>> searchBusinessPartners(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BusinessPartners for query {}", query);
        Page<BusinessPartnerDTO> page = businessPartnerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/business-partners")
    public ResponseEntity<IResponseMessage> getAll(){
        List<BusinessPartnerDTO> result = this.businessPartnersCustomService.getAll();
        log.debug("BusinessPartnerCustomRest: getAll(): {}");
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/business-partners")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody BusinessPartnerDTO businessPartnerDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.businessPartnerSearchService.simpleQuerySearch(businessPartnerDTO, pageable);
        log.debug("BusinessPartnerCustomRest: customSearch(IBaseSearchDTO: {}): {}", businessPartnerDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/business-partners")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody BusinessPartnerDTO businessPartnerDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.businessPartnerSearchService.simpleQuerySearchWithParam(businessPartnerDTO, paramMaps, pageable);
        log.debug("BusinessPartnerCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", businessPartnerDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

}
