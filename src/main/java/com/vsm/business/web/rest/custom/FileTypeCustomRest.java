package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.service.FileTypeService;
import com.vsm.business.service.custom.FileTypeCustomService;
import com.vsm.business.service.custom.search.service.FileTypeSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.FileTypeDTO;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.vsm.business.domain.FileType}.
 */
@RestController
@RequestMapping("/api")
public class FileTypeCustomRest {

    private final Logger log = LoggerFactory.getLogger(FileTypeCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileTypeService fileTypeService;

    private final FileTypeRepository fileTypeRepository;

    private final FileTypeCustomService fileTypeCustomService;

    private final FileTypeSearchService fileTypeSearchService;

    public FileTypeCustomRest(FileTypeService fileTypeService, FileTypeRepository fileTypeRepository, FileTypeCustomService fileTypeCustomService, FileTypeSearchService fileTypeSearchService) {
        this.fileTypeService = fileTypeService;
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeCustomService = fileTypeCustomService;
        this.fileTypeSearchService = fileTypeSearchService;
    }

    /**
     * {@code POST  /file-types} : Create a new fileType.
     *
     * @param fileTypeDTO the fileTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileTypeDTO, or with status {@code 400 (Bad Request)} if the fileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-types")
    public ResponseEntity<IResponseMessage> createFileType(@Valid @RequestBody FileTypeDTO fileTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FileType : {}", fileTypeDTO);
        if (fileTypeDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(fileTypeDTO));
        }
        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(fileTypeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /file-types/:id} : Updates an existing fileType.
     *
     * @param id the id of the fileTypeDTO to save.
     * @param fileTypeDTO the fileTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fileTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-types/{id}")
    public ResponseEntity<IResponseMessage> updateFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FileTypeDTO fileTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileType : {}, {}", id, fileTypeDTO);
        if (fileTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fileTypeDTO));
        }
        if (!Objects.equals(id, fileTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fileTypeDTO));
        }

        if (!fileTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fileTypeDTO));
        }

        FileTypeDTO result = fileTypeService.save(fileTypeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fileTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /file-types/:id} : Partial updates given fields of an existing fileType, field will ignore if it is null
     *
     * @param id the id of the fileTypeDTO to save.
     * @param fileTypeDTO the fileTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fileTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateFileType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FileTypeDTO fileTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileType partially : {}, {}", id, fileTypeDTO);
        if (fileTypeDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(fileTypeDTO));
        }
        if (!Objects.equals(id, fileTypeDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(fileTypeDTO));
        }

        if (!fileTypeRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(fileTypeDTO));
        }

        Optional<FileTypeDTO> result = fileTypeService.partialUpdate(fileTypeDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(fileTypeDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /file-types} : get all the fileTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileTypes in body.
     */
    @GetMapping("/file-types")
    public ResponseEntity<IResponseMessage> getAllFileTypes(Pageable pageable) {
        log.debug("REST request to get a page of FileTypes");
        Page<FileTypeDTO> page = fileTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /file-types/:id} : get the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-types/{id}")
    public ResponseEntity<IResponseMessage> getFileType(@PathVariable Long id) {
        log.debug("REST request to get FileType : {}", id);
        Optional<FileTypeDTO> fileTypeDTO = fileTypeService.findOne(id);

        if (!fileTypeDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(fileTypeDTO.get()));
    }

    /**
     * {@code DELETE  /file-types/:id} : delete the "id" fileType.
     *
     * @param id the id of the fileTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-types/{id}")
    public ResponseEntity<IResponseMessage> deleteFileType(@PathVariable Long id) {
        log.debug("REST request to delete FileType : {}", id);
        fileTypeService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/file-types?query=:query} : search for the fileType corresponding
     * to the query.
     *
     * @param query the query of the fileType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/file-types")
    public ResponseEntity<List<FileTypeDTO>> searchFileTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FileTypes for query {}", query);
        Page<FileTypeDTO> page = fileTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/file-types")
    public ResponseEntity<IResponseMessage> getAll(){
        List<FileTypeDTO> result = this.fileTypeCustomService.getAll();
        log.debug("FileTypeCustomRest: getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/file-types")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody FileTypeDTO fileTypeDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fileTypeSearchService.simpleQuerySearch(fileTypeDTO, pageable);
        log.debug("FileTypeCustomRest: customSearch(IBaseSearchDTO: {}): {}", fileTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/file-types")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody FileTypeDTO fileTypeDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.fileTypeSearchService.simpleQuerySearchWithParam(fileTypeDTO, paramMaps, pageable);
        log.debug("FileTypeCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", fileTypeDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
