package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.MECargoRepository;
import com.vsm.business.service.MECargoService;
import com.vsm.business.service.custom.MECargoCustomService;
import com.vsm.business.service.custom.search.service.MECargoSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.dto.MECargoDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing {@link com.vsm.business.domain.MECargo}.
 */
@RestController
@RequestMapping("/api")
public class MECargoCustomRest {

    private final Logger log = LoggerFactory.getLogger(MECargoCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceMeCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MECargoService mECargoService;

    private final MECargoRepository mECargoRepository;

    private final MECargoCustomService meCargoCustomService;

    private final MECargoSearchService meCargoSearchService;

    private final AuthenticateUtils authenticateUtils;

    @Value("${cargo.import_file_type}")
    private String[] FILE_IMPORT_EXTENSION;

    private final String FILE_TYPE_EXCEPTION_MESS = "File Type Not Support !!!";

    public MECargoCustomRest(MECargoService mECargoService, MECargoRepository mECargoRepository, MECargoCustomService meCargoCustomService, MECargoSearchService meCargoSearchService, AuthenticateUtils authenticateUtils) {
        this.mECargoService = mECargoService;
        this.mECargoRepository = mECargoRepository;
        this.meCargoCustomService = meCargoCustomService;
        this.meCargoSearchService = meCargoSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /me-cargos} : Create a new mECargo.
     *
     * @param mECargoDTO the mECargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mECargoDTO, or with status {@code 400 (Bad Request)} if the mECargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/me-cargos")
    public ResponseEntity<IResponseMessage> createMECargo(@RequestBody MECargoDTO mECargoDTO) throws URISyntaxException {
        log.debug("REST request to save MECargo : {}", mECargoDTO);
        if (mECargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new mECargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MECargoDTO result = mECargoService.save(mECargoDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(mECargoDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /me-cargos/:id} : Updates an existing mECargo.
     *
     * @param id the id of the mECargoDTO to save.
     * @param mECargoDTO the mECargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mECargoDTO,
     * or with status {@code 400 (Bad Request)} if the mECargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mECargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/me-cargos/{id}")
    public ResponseEntity<IResponseMessage> updateMECargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MECargoDTO mECargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MECargo : {}, {}", id, mECargoDTO);
        if (mECargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mECargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mECargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MECargoDTO result = mECargoService.save(mECargoDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(mECargoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /me-cargos/:id} : Partial updates given fields of an existing mECargo, field will ignore if it is null
     *
     * @param id the id of the mECargoDTO to save.
     * @param mECargoDTO the mECargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mECargoDTO,
     * or with status {@code 400 (Bad Request)} if the mECargoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mECargoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mECargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/me-cargos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateMECargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MECargoDTO mECargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MECargo partially : {}, {}", id, mECargoDTO);
        if (mECargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mECargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mECargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MECargoDTO> result = mECargoService.partialUpdate(mECargoDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(mECargoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /me-cargos} : get all the mECargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mECargos in body.
     */
    @GetMapping("/me-cargos")
    public ResponseEntity<IResponseMessage> getAllMECargos(Pageable pageable) {
        log.debug("REST request to get a page of MECargos");
        Page<MECargoDTO> page = mECargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /me-cargos/:id} : get the "id" mECargo.
     *
     * @param id the id of the mECargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mECargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/me-cargos/{id}")
    public ResponseEntity<IResponseMessage> getMECargo(@PathVariable Long id) {
        log.debug("REST request to get MECargo : {}", id);
        Optional<MECargoDTO> mECargoDTO = mECargoService.findOne(id);
        if(!mECargoDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(mECargoDTO.get()));
    }

    /**
     * {@code DELETE  /me-cargos/:id} : delete the "id" mECargo.
     *
     * @param id the id of the mECargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/me-cargos/{id}")
    public ResponseEntity<IResponseMessage> deleteMECargo(@PathVariable Long id) {
        log.debug("REST request to delete MECargo : {}", id);
        mECargoService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/me-cargos?query=:query} : search for the mECargo corresponding
     * to the query.
     *
     * @param query the query of the mECargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/me-cargos")
    public ResponseEntity<List<MECargoDTO>> searchMECargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MECargos for query {}", query);
        Page<MECargoDTO> page = mECargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/me-cargos")
    public ResponseEntity<IResponseMessage> getAll(){
        List<MECargoDTO> result = this.meCargoCustomService.getAll();
        log.debug("MECargoCustomRest: getAll(): {}");
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_import_cargo/me-cargos")
    public ResponseEntity<IResponseMessage> importCargo(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

        if(!this.authenticateUtils.checkPermisionADMIN())       // kiểm tra xem có quyền admin ko
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        // kiểm tra định dạng file import \\
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String finalFileExtension = fileExtension.toLowerCase();
        if(!Arrays.stream(this.FILE_IMPORT_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);

        boolean result = this.meCargoCustomService.importCargo(file);
        if(result){
            return ResponseEntity.ok().body(new LoadedMessage(result));
        }else{
            return ResponseEntity.ok().body(new FailLoadMessage(result));
        }
    }

    @GetMapping("/_all_ma_hieu/me-cargos")
    public ResponseEntity<IResponseMessage> getAllMaHieu(){
        List<String> result = this.meCargoCustomService.getAllMaHieu();
        log.debug("MECargoCustomRest: getAllMaHieu(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/me-cargos")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody MECargoDTO meCargoDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.meCargoSearchService.simpleQuerySearch(meCargoDTO, pageable);
        log.debug("ConstructionCargoCustomRest: customSearch(IBaseSearchDTO: {}): {}", meCargoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/me-cargos")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody MECargoDTO meCargoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.meCargoSearchService.simpleQuerySearchWithParam(meCargoDTO, paramMaps, pageable);
        log.debug("ConstructionCargoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", meCargoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
