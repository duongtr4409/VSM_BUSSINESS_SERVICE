package com.vsm.business.web.rest;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.service.OTPService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.dto.OTPDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

/**
 * REST controller for managing {@link com.vsm.business.domain.OTP}.
 */
//@RestController
@RequestMapping("/api")
public class OTPResource {

    private final Logger log = LoggerFactory.getLogger(OTPResource.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOtp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OTPService oTPService;

    private final OTPRepository oTPRepository;

    public OTPResource(OTPService oTPService, OTPRepository oTPRepository) {
        this.oTPService = oTPService;
        this.oTPRepository = oTPRepository;
    }

    /**
     * {@code POST  /otps} : Create a new oTP.
     *
     * @param oTPDTO the oTPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oTPDTO, or with status {@code 400 (Bad Request)} if the oTP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otps")
    public ResponseEntity<IResponseMessage> createOTP(@RequestBody OTPDTO oTPDTO) throws URISyntaxException {
        log.debug("REST request to save OTP : {}", oTPDTO);
        if (oTPDTO.getId() != null) {
            throw new BadRequestAlertException("A new oTP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OTPDTO result = oTPService.save(oTPDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(oTPDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /otps/:id} : Updates an existing oTP.
     *
     * @param id the id of the oTPDTO to save.
     * @param oTPDTO the oTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPDTO,
     * or with status {@code 400 (Bad Request)} if the oTPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> updateOTP(@PathVariable(value = "id", required = false) final Long id, @RequestBody OTPDTO oTPDTO)
        throws URISyntaxException {
        log.debug("REST request to update OTP : {}, {}", id, oTPDTO);
        if (oTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OTPDTO result = oTPService.save(oTPDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(oTPDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /otps/:id} : Partial updates given fields of an existing oTP, field will ignore if it is null
     *
     * @param id the id of the oTPDTO to save.
     * @param oTPDTO the oTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPDTO,
     * or with status {@code 400 (Bad Request)} if the oTPDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oTPDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateOTP(@PathVariable(value = "id", required = false) final Long id, @RequestBody OTPDTO oTPDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update OTP partially : {}, {}", id, oTPDTO);
        if (oTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OTPDTO> result = oTPService.partialUpdate(oTPDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(oTPDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /otps} : get all the oTPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oTPS in body.
     */
    @GetMapping("/otps")
    public ResponseEntity<IResponseMessage> getAllOTPS(Pageable pageable) {
        log.debug("REST request to get a page of OTPS");
        Page<OTPDTO> page = oTPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /otps/:id} : get the "id" oTP.
     *
     * @param id the id of the oTPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oTPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> getOTP(@PathVariable Long id) {
        log.debug("REST request to get OTP : {}", id);
        Optional<OTPDTO> oTPDTO = oTPService.findOne(id);
        if(!oTPDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(oTPDTO.get()));
    }

    /**
     * {@code DELETE  /otps/:id} : delete the "id" oTP.
     *
     * @param id the id of the oTPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> deleteOTP(@PathVariable Long id) {
        log.debug("REST request to delete OTP : {}", id);
        oTPService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/otps?query=:query} : search for the oTP corresponding
     * to the query.
     *
     * @param query the query of the oTP search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/otps")
    public ResponseEntity<List<OTPDTO>> searchOTPS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OTPS for query {}", query);
        Page<OTPDTO> page = oTPService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
