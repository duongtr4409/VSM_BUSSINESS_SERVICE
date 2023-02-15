package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.service.ConstructionCargoService;
import com.vsm.business.service.custom.ConstructionCargoCustomService;
import com.vsm.business.service.custom.search.service.ConstructionCargoSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import com.vsm.business.service.dto.ConstructionCargoDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.ConstructionCargo}.
 */
@RestController
@RequestMapping("/api")
public class ConstructionCargoCustomRest {

    private final Logger log = LoggerFactory.getLogger(ConstructionCargoCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceConstructionCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConstructionCargoService constructionCargoService;

    private final ConstructionCargoRepository constructionCargoRepository;

    private final ConstructionCargoCustomService constructionCargoCustomService;

    private final ConstructionCargoSearchService constructionCargoSearchService;

    private final AuthenticateUtils authenticateUtils;

    @Value("${cargo.import_file_type}")
    private String[] FILE_IMPORT_EXTENSION;

    private final String FILE_TYPE_EXCEPTION_MESS = "File Type Not Support !!!";

    public ConstructionCargoCustomRest(ConstructionCargoService constructionCargoService, ConstructionCargoRepository constructionCargoRepository, ConstructionCargoCustomService constructionCargoCustomService, ConstructionCargoSearchService constructionCargoSearchService, AuthenticateUtils authenticateUtils) {
        this.constructionCargoService = constructionCargoService;
        this.constructionCargoRepository = constructionCargoRepository;
        this.constructionCargoCustomService = constructionCargoCustomService;
        this.constructionCargoSearchService = constructionCargoSearchService;
        this.authenticateUtils = authenticateUtils;
    }

    /**
     * {@code POST  /construction-cargos} : Create a new constructionCargo.
     *
     * @param constructionCargoDTO the constructionCargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new constructionCargoDTO, or with status {@code 400 (Bad Request)} if the constructionCargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/construction-cargos")
    public ResponseEntity<IResponseMessage> createConstructionCargo(@RequestBody ConstructionCargoDTO constructionCargoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConstructionCargo : {}", constructionCargoDTO);
        if (constructionCargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new constructionCargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConstructionCargoDTO result = constructionCargoService.save(constructionCargoDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(constructionCargoDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /construction-cargos/:id} : Updates an existing constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to save.
     * @param constructionCargoDTO the constructionCargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constructionCargoDTO,
     * or with status {@code 400 (Bad Request)} if the constructionCargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the constructionCargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/construction-cargos/{id}")
    public ResponseEntity<IResponseMessage> updateConstructionCargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConstructionCargoDTO constructionCargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConstructionCargo : {}, {}", id, constructionCargoDTO);
        if (constructionCargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, constructionCargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!constructionCargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConstructionCargoDTO result = constructionCargoService.save(constructionCargoDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(constructionCargoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /construction-cargos/:id} : Partial updates given fields of an existing constructionCargo, field will ignore if it is null
     *
     * @param id the id of the constructionCargoDTO to save.
     * @param constructionCargoDTO the constructionCargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constructionCargoDTO,
     * or with status {@code 400 (Bad Request)} if the constructionCargoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the constructionCargoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the constructionCargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/construction-cargos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateConstructionCargo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConstructionCargoDTO constructionCargoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConstructionCargo partially : {}, {}", id, constructionCargoDTO);
        if (constructionCargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, constructionCargoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!constructionCargoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConstructionCargoDTO> result = constructionCargoService.partialUpdate(constructionCargoDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(constructionCargoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /construction-cargos} : get all the constructionCargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of constructionCargos in body.
     */
    @GetMapping("/construction-cargos")
    public ResponseEntity<IResponseMessage> getAllConstructionCargos(Pageable pageable) {
        log.debug("REST request to get a page of ConstructionCargos");
        Page<ConstructionCargoDTO> page = constructionCargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /construction-cargos/:id} : get the "id" constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the constructionCargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/construction-cargos/{id}")
    public ResponseEntity<IResponseMessage> getConstructionCargo(@PathVariable Long id) {
        log.debug("REST request to get ConstructionCargo : {}", id);
        Optional<ConstructionCargoDTO> constructionCargoDTO = constructionCargoService.findOne(id);
        if(!constructionCargoDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(constructionCargoDTO.get()));
    }

    /**
     * {@code DELETE  /construction-cargos/:id} : delete the "id" constructionCargo.
     *
     * @param id the id of the constructionCargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/construction-cargos/{id}")
    public ResponseEntity<IResponseMessage> deleteConstructionCargo(@PathVariable Long id) {
        log.debug("REST request to delete ConstructionCargo : {}", id);
        constructionCargoService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/construction-cargos?query=:query} : search for the constructionCargo corresponding
     * to the query.
     *
     * @param query the query of the constructionCargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/construction-cargos")
    public ResponseEntity<List<ConstructionCargoDTO>> searchConstructionCargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConstructionCargos for query {}", query);
        Page<ConstructionCargoDTO> page = constructionCargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/construction-cargos")
    public ResponseEntity<IResponseMessage> getAll(){
        List<ConstructionCargoDTO> result = this.constructionCargoCustomService.getAll();
        log.debug("ConstructionCargoCustomRest: getAll(): {}");
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_import_cargo/construction-cargos")
    public ResponseEntity<IResponseMessage> importCargo(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
        if(!this.authenticateUtils.checkPermisionADMIN())       // kiểm tra xem có quyền admin ko
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

            // kiểm tra định dạng file import \\
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String finalFileExtension = fileExtension.toLowerCase();
        if(!Arrays.stream(this.FILE_IMPORT_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);

        boolean result = this.constructionCargoCustomService.importCargo(file);
        if(result){
            return ResponseEntity.ok().body(new LoadedMessage(result));
        }else{
            return ResponseEntity.ok().body(new FailLoadMessage(result));
        }
    }

    @GetMapping("/_all_ma_hieu/construction-cargos")
    public ResponseEntity<IResponseMessage> getAllMaHieu(){
        log.debug("ConstructionCargoCustomRest: getAllMaHieu()");
        List<String> result = this.constructionCargoCustomService.getAllMaHieu();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/construction-cargos")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ConstructionCargoDTO constructionCargoDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.constructionCargoSearchService.simpleQuerySearch(constructionCargoDTO, pageable);
        log.debug("ConstructionCargoCustomRest: customSearch(IBaseSearchDTO: {}): {}", constructionCargoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/construction-cargos")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ConstructionCargoDTO constructionCargoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.constructionCargoSearchService.simpleQuerySearchWithParam(constructionCargoDTO, paramMaps, pageable);
        log.debug("ConstructionCargoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", constructionCargoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
