package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.domain.ManageStampInfo;
import com.vsm.business.repository.ManageStampInfoRepository;
import com.vsm.business.service.ManageStampInfoService;
import com.vsm.business.service.custom.ManageStampCustomService;
import com.vsm.business.service.custom.search.service.ManageStampInfoSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ManageStampInfoDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.ManageStampInfo}.
 */
@RestController
@RequestMapping("/api")
public class ManageStampInfoCustomRest {

    private final Logger log = LoggerFactory.getLogger(ManageStampInfoCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceManageStampInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManageStampInfoService manageStampInfoService;

    private final ManageStampInfoRepository manageStampInfoRepository;

    private final ManageStampCustomService manageStampCustomService;

    private final ManageStampInfoSearchService manageStampInfoSearchService;

    private final AuthenticateUtils authenticateUtils;

    public ManageStampInfoCustomRest(ManageStampInfoService manageStampInfoService, ManageStampInfoRepository manageStampInfoRepository, ManageStampCustomService manageStampCustomService, ManageStampInfoSearchService manageStampInfoSearchService, AuthenticateUtils authenticateUtils) {
        this.manageStampInfoService = manageStampInfoService;
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.manageStampCustomService = manageStampCustomService;
        this.manageStampInfoSearchService = manageStampInfoSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /manage-stamp-infos} : Create a new manageStampInfo.
     *
     * @param manageStampInfoDTO the manageStampInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manageStampInfoDTO, or with status {@code 400 (Bad Request)} if the manageStampInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/manage-stamp-infos")
    public ResponseEntity<IResponseMessage> createManageStampInfo(@RequestBody ManageStampInfoDTO manageStampInfoDTO)
        throws URISyntaxException {

        // kiểm tra user có quyên thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(manageStampInfoDTO.getRequestData() != null ? manageStampInfoDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to save ManageStampInfo : {}", manageStampInfoDTO);
        if (manageStampInfoDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(manageStampInfoDTO));
        }
        ManageStampInfoDTO result = manageStampInfoService.save(manageStampInfoDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(manageStampInfoDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /manage-stamp-infos/:id} : Updates an existing manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to save.
     * @param manageStampInfoDTO the manageStampInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manageStampInfoDTO,
     * or with status {@code 400 (Bad Request)} if the manageStampInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manageStampInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<IResponseMessage> updateManageStampInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManageStampInfoDTO manageStampInfoDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyên thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(manageStampInfoDTO.getRequestData() != null ? manageStampInfoDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to update ManageStampInfo : {}, {}", id, manageStampInfoDTO);
        if (manageStampInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(manageStampInfoDTO));
        }
        if (!Objects.equals(id, manageStampInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(manageStampInfoDTO));
        }

        if (!manageStampInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(manageStampInfoDTO));
        }

        ManageStampInfoDTO result = manageStampInfoService.save(manageStampInfoDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(manageStampInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /manage-stamp-infos/:id} : Partial updates given fields of an existing manageStampInfo, field will ignore if it is null
     *
     * @param id the id of the manageStampInfoDTO to save.
     * @param manageStampInfoDTO the manageStampInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manageStampInfoDTO,
     * or with status {@code 400 (Bad Request)} if the manageStampInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the manageStampInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the manageStampInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/manage-stamp-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateManageStampInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManageStampInfoDTO manageStampInfoDTO
    ) throws URISyntaxException {

        // kiểm tra user có quyên thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        if(!this.authenticateUtils.checkPermisionForDataOfUser(manageStampInfoDTO.getRequestData() != null ? manageStampInfoDTO.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to partial update ManageStampInfo partially : {}, {}", id, manageStampInfoDTO);
        if (manageStampInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(manageStampInfoDTO));
        }
        if (!Objects.equals(id, manageStampInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(manageStampInfoDTO));
        }

        if (!manageStampInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(manageStampInfoDTO));
        }

        Optional<ManageStampInfoDTO> result = manageStampInfoService.partialUpdate(manageStampInfoDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(manageStampInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /manage-stamp-infos} : get all the manageStampInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manageStampInfos in body.
     */
    @GetMapping("/manage-stamp-infos")
    public ResponseEntity<IResponseMessage> getAllManageStampInfos(Pageable pageable) {
        log.debug("REST request to get a page of ManageStampInfos");
        Page<ManageStampInfoDTO> page = manageStampInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /manage-stamp-infos/:id} : get the "id" manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manageStampInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<IResponseMessage> getManageStampInfo(@PathVariable Long id) {
        log.debug("REST request to get ManageStampInfo : {}", id);
        Optional<ManageStampInfoDTO> manageStampInfoDTO = manageStampInfoService.findOne(id);
        if (!manageStampInfoDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(manageStampInfoDTO.get()));
    }

    /**
     * {@code DELETE  /manage-stamp-infos/:id} : delete the "id" manageStampInfo.
     *
     * @param id the id of the manageStampInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/manage-stamp-infos/{id}")
    public ResponseEntity<IResponseMessage> deleteManageStampInfo(@PathVariable Long id) {

        // kiểm tra user có quyên thay đổi dữ liệu liên quan đến phiếu yêu cầu không
        ManageStampInfo manageStampInfo = this.manageStampInfoRepository.findById(id).get();
        if(!this.authenticateUtils.checkPermisionForDataOfUser(manageStampInfo.getRequestData() != null ? manageStampInfo.getRequestData().getId() : null))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        log.debug("REST request to delete ManageStampInfo : {}", id);
        manageStampInfoService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/manage-stamp-infos?query=:query} : search for the manageStampInfo corresponding
     * to the query.
     *
     * @param query the query of the manageStampInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/manage-stamp-infos")
    public ResponseEntity<List<ManageStampInfoDTO>> searchManageStampInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ManageStampInfos for query {}", query);
        Page<ManageStampInfoDTO> page = manageStampInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/request-data/{requestDataId}/_all/manage-stamp-infos")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") Long requestDataId){
        log.debug("REST request to search for a page of ManageStampInfos for query {}", requestDataId);
        List<ManageStampInfoDTO> result = manageStampCustomService.getAllByRequestData(requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/manage-stamp-infos")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ManageStampInfoDTO manageStampInfoDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.manageStampInfoSearchService.simpleQuerySearch(manageStampInfoDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", manageStampInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/manage-stamp-infos")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ManageStampInfoDTO manageStampInfoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.manageStampInfoSearchService.simpleQuerySearchWithParam(manageStampInfoDTO, paramMaps, pageable);
        log.debug("ManageStampInfoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", manageStampInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
