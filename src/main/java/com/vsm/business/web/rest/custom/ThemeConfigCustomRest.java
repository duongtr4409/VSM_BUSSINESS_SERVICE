package com.vsm.business.web.rest.custom;


import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.ThemeConfigRepository;
import com.vsm.business.service.ThemeConfigService;
import com.vsm.business.service.custom.search.service.ThemeConfigSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ThemeConfigDTO;
import java.net.URISyntaxException;
import java.util.Map;
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
 * ThemeConfigCustomRest
 */
@RestController
@RequestMapping("/api")
public class ThemeConfigCustomRest {

    private final Logger log = LoggerFactory.getLogger(ThemeConfigCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceThemeConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeConfigService themeConfigService;

    private final ThemeConfigRepository themeConfigRepository;

    private final ThemeConfigSearchService themeConfigSearchService;

    public ThemeConfigCustomRest(ThemeConfigService themeConfigService, ThemeConfigRepository themeConfigRepository, ThemeConfigSearchService themeConfigSearchService) {
        this.themeConfigService = themeConfigService;
        this.themeConfigRepository = themeConfigRepository;
        this.themeConfigSearchService = themeConfigSearchService;
    }

    /**
     * {@code POST  /theme-configs} : Create a new themeConfig.
     *
     * @param themeConfigDTO the themeConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new themeConfigDTO, or with status {@code 400 (Bad Request)}
     * if the themeConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-configs")
    public ResponseEntity<IResponseMessage> createThemeConfig(@RequestBody ThemeConfigDTO themeConfigDTO)
        throws URISyntaxException {
        log.debug("REST request to save ThemeConfig : {}", themeConfigDTO);
        if (themeConfigDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(themeConfigDTO));
        }
        ThemeConfigDTO result = themeConfigService.save(themeConfigDTO);
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /theme-configs/:id} : Updates an existing themeConfig.
     *
     * @param id             the id of the themeConfigDTO to save.
     * @param themeConfigDTO the themeConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated themeConfigDTO,
     * or with status {@code 400 (Bad Request)} if the themeConfigDTO is not
     * valid,
     * or with status {@code 500 (Internal Server Error)} if the
     * themeConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-configs/{id}")
    public ResponseEntity<IResponseMessage> updateThemeConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThemeConfigDTO themeConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeConfig : {}, {}", id, themeConfigDTO);
        if (themeConfigDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(themeConfigDTO));
        }
        if (!Objects.equals(id, themeConfigDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(themeConfigDTO));
        }

        if (!themeConfigRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(themeConfigDTO));
        }

        ThemeConfigDTO result = themeConfigService.save(themeConfigDTO);
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /theme-configs/:id} : Partial updates given fields of an
     * existing themeConfig, field will ignore if it is null
     *
     * @param id             the id of the themeConfigDTO to save.
     * @param themeConfigDTO the themeConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated themeConfigDTO,
     * or with status {@code 400 (Bad Request)} if the themeConfigDTO is not
     * valid,
     * or with status {@code 404 (Not Found)} if the themeConfigDTO is not
     * found,
     * or with status {@code 500 (Internal Server Error)} if the
     * themeConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/theme-configs/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateThemeConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThemeConfigDTO themeConfigDTO) throws URISyntaxException {
        log.debug("REST request to partial update ThemeConfig partially : {}, {}", id, themeConfigDTO);
        if (themeConfigDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(themeConfigDTO));
        }
        if (!Objects.equals(id, themeConfigDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(themeConfigDTO));
        }

        if (!themeConfigRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(themeConfigDTO));
        }

        Optional<ThemeConfigDTO> result = themeConfigService.partialUpdate(themeConfigDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(themeConfigDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /theme-configs} : get all the themeConfigs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of themeConfigs in body.
     */
    @GetMapping("/theme-configs")
    public ResponseEntity<IResponseMessage> getAllThemeConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ThemeConfigs");
        Page<ThemeConfigDTO> page = themeConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /theme-configs/:id} : get the "id" themeConfig.
     *
     * @param id the id of the themeConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the themeConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-configs/{id}")
    public ResponseEntity<IResponseMessage> getThemeConfig(@PathVariable Long id) {
        log.debug("REST request to get ThemeConfig : {}", id);
        Optional<ThemeConfigDTO> themeConfigDTO = themeConfigService.findOne(id);
        if (!themeConfigDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(themeConfigDTO.get()));
    }

    /**
     * {@code DELETE  /theme-configs/:id} : delete the "id" themeConfig.
     *
     * @param id the id of the themeConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-configs/{id}")
    public ResponseEntity<IResponseMessage> deleteThemeConfig(@PathVariable Long id) {
        log.debug("REST request to delete ThemeConfig : {}", id);
        themeConfigService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/theme-configs?query=:query} : search for the
     * themeConfig corresponding
     * to the query.
     *
     * @param query    the query of the themeConfig search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/theme-configs")
    public ResponseEntity<IResponseMessage> searchThemeConfigs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ThemeConfigs for query {}", query);
        Page<ThemeConfigDTO> page = themeConfigService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/theme-configs")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ThemeConfigDTO themeConfigDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.themeConfigSearchService.simpleQuerySearch(themeConfigDTO, pageable);
        log.debug("ThemeConfigCustomRest: customSearch(IBaseSearchDTO: {}): {}", themeConfigDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/theme-configs")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ThemeConfigDTO themeConfigDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.themeConfigSearchService.simpleQuerySearchWithParam(themeConfigDTO, paramMaps, pageable);
        log.debug("ThemeConfigCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", themeConfigDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
