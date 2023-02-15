package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.*;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.service.OTPService;
import com.vsm.business.service.custom.OTPCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.OTPSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OTPDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.OTP}.
 */
@RestController
@RequestMapping("/api")
public class OTPCustomRest {

    private final Logger log = LoggerFactory.getLogger(OTPCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOpt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OTPService otpService;

    private final OTPRepository otpRepository;

    private final OTPCustomService optCustomService;

    private final OTPSearchService otpSearchService;

    private final AuthenticateUtils authenticateUtils;


    public OTPCustomRest(OTPService oPTService, OTPRepository otpRepository, OTPCustomService optCustomService, OTPSearchService otpSearchService, AuthenticateUtils authenticateUtils) {
        this.otpService = oPTService;
        this.otpRepository = otpRepository;
        this.optCustomService = optCustomService;
        this.otpSearchService = otpSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /opts} : Create a new oPT.
     *
     * @param OTPDTO the oPTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oPTDTO, or with status {@code 400 (Bad Request)} if the oPT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otps")
    public ResponseEntity<IResponseMessage> createOPT(@RequestBody OTPDTO OTPDTO) throws URISyntaxException {
        log.debug("REST request to save OPT : {}", OTPDTO);
        if (OTPDTO.getId() != null) {
            throw new BadRequestAlertException("A new oPT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OTPDTO result = otpService.save(OTPDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(OTPDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /opts/:id} : Updates an existing oPT.
     *
     * @param id the id of the oPTDTO to save.
     * @param otp the oPTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oPTDTO,
     * or with status {@code 400 (Bad Request)} if the oPTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oPTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> updateOPT(@PathVariable(value = "id", required = false) final Long id, @RequestBody OTPDTO oTPDTO)
        throws URISyntaxException {
        log.debug("REST request to update OPT : {}, {}", id, oTPDTO);
        if (oTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OTPDTO result = otpService.save(oTPDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(oTPDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /opts/:id} : Partial updates given fields of an existing oPT, field will ignore if it is null
     *
     * @param id the id of the oPTDTO to save.
     * @param otpdto the oPTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oPTDTO,
     * or with status {@code 400 (Bad Request)} if the oPTDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oPTDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oPTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateOPT(@PathVariable(value = "id", required = false) final Long id, @RequestBody OTPDTO otpdto)
        throws URISyntaxException {
        log.debug("REST request to partial update OPT partially : {}, {}", id, otpdto);
        if (otpdto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otpdto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OTPDTO> result = otpService.partialUpdate(otpdto);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(otpdto));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /opts} : get all the oPTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oPTS in body.
     */
    @GetMapping("/otps")
    public ResponseEntity<IResponseMessage> getAllOPTS(Pageable pageable) {
        log.debug("REST request to get a page of OPTS");
        Page<OTPDTO> page = otpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /opts/:id} : get the "id" oPT.
     *
     * @param id the id of the oPTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oPTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> getOPT(@PathVariable Long id) {
        log.debug("REST request to get OPT : {}", id);
        Optional<OTPDTO> otpdto = otpService.findOne(id);
        if(!otpdto.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(otpdto.get()));
    }

    /**
     * {@code DELETE  /opts/:id} : delete the "id" oTP.
     *
     * @param id the id of the oPTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otps/{id}")
    public ResponseEntity<IResponseMessage> deleteOPT(@PathVariable Long id) {
        log.debug("REST request to delete OTP : {}", id);
        otpService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/opts?query=:query} : search for the oPT corresponding
     * to the query.
     *
     * @param query the query of the oPT search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/otps")
    public ResponseEntity<List<OTPDTO>> searchOPTS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OTPS for query {}", query);
        Page<OTPDTO> page = otpService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/otps")
    public ResponseEntity<IResponseMessage> getAll(){

        // dữ liệu đặc thù -> check riêng \\
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);   // nếu không có quyền ADMIN -> lỗi

        List<OTPDTO> result = this.optCustomService.getAll();
        log.debug("OTPCustomRest: getll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/otps")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        List<OTPDTO> result = this.optCustomService.getAllByRequestData(requestDataId);
        log.debug("OTPCustomRest: getAllByRequestData({})", requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_generate/{requestDataId}/otps")
    public ResponseEntity<IResponseMessage> generateOTP(@PathVariable("requestDataId") Long requestDataId, @RequestParam(required = false) String description){
        OTPDTO result = this.optCustomService.generateOTP(requestDataId, description);
        log.debug("OTPCustomRest: generateOTP({})", requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/otps")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<OTPDTO> otpdtoList){
        List<OTPDTO> result = this.optCustomService.saveAll(otpdtoList);
        log.debug("OTPCustomRest: saveAll({})", otpdtoList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_check/otps")
    public ResponseEntity<IResponseMessage> checkOTPCode(@RequestParam("opt") String otp, @RequestParam("requestDataId") Long requestDataId){
        log.debug("OTPCustomRest: checkOTPCode({}, {}): {}", otp, requestDataId);
        Boolean result = this.optCustomService.checkOtpCode(otp, requestDataId);
        if(!result){
            return ResponseEntity.ok().body(new FailLoadMessage("NOT ACCESS"));
        }
        return ResponseEntity.ok().body(new LoadedMessage("SUCESS"));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/otps")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody OTPDTO otpdto, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.otpSearchService.simpleQuerySearch(otpdto, pageable);
        log.debug("OTPCustomRest: customSearch(IBaseSearchDTO: {}): {}", otpdto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/otps")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody OTPDTO otpdto, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.otpSearchService.simpleQuerySearchWithParam(otpdto, paramMaps, pageable);
        log.debug("UserInfoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", otpdto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
