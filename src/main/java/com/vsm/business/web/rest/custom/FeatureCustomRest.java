package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.FeatureRepository;
import com.vsm.business.service.FeatureService;
import com.vsm.business.service.custom.FeatureCustomService;
import com.vsm.business.service.dto.FeatureDTO;
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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vsm.business.domain.Feature}.
 */
@RestController
@RequestMapping("/api")
public class FeatureCustomRest {

    private final Logger log = LoggerFactory.getLogger(FeatureCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeatureService featureService;

    private final FeatureRepository featureRepository;

    private final FeatureCustomService featureCustomService;

    public FeatureCustomRest(FeatureService featureService, FeatureRepository featureRepository, FeatureCustomService featureCustomService) {
        this.featureService = featureService;
        this.featureRepository = featureRepository;
        this.featureCustomService = featureCustomService;
    }

    /**
     * {@code POST  /features} : Create a new feature.
     *
     * @param featureDTO the featureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featureDTO, or with status {@code 400 (Bad Request)} if the feature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<IResponseMessage> createFeature(@RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", featureDTO);
        if (featureDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(featureDTO));
        }
        FeatureDTO result = featureService.save(featureDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(featureDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /features/:id} : Updates an existing feature.
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features/{id}")
    public ResponseEntity<IResponseMessage> updateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feature : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(featureDTO));
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(featureDTO));
        }

        if (!featureRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(featureDTO));
        }

        FeatureDTO result = featureService.save(featureDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(featureDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /features/:id} : Partial updates given fields of an existing feature, field will ignore if it is null
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the featureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/features/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feature partially : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(featureDTO));
        }
        if (!Objects.equals(id, featureDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(featureDTO));
        }

        if (!featureRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(featureDTO));
        }

        Optional<FeatureDTO> result = featureService.partialUpdate(featureDTO);

        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(featureDTO));
        }

        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /features} : get all the features.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public ResponseEntity<IResponseMessage> getAllFeatures(Pageable pageable) {
        log.debug("REST request to get a page of Features");
        Page<FeatureDTO> page = featureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /features/:id} : get the "id" feature.
     *
     * @param id the id of the featureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<IResponseMessage> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Optional<FeatureDTO> featureDTO = featureService.findOne(id);
        if(!featureDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(featureDTO.get()));
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" feature.
     *
     * @param id the id of the featureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<IResponseMessage> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/features?query=:query} : search for the feature corresponding
     * to the query.
     *
     * @param query the query of the feature search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/features")
    public ResponseEntity<List<FeatureDTO>> searchFeatures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Features for query {}", query);
        Page<FeatureDTO> page = featureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/features")
    public ResponseEntity<IResponseMessage> getAll(){
        List<FeatureDTO> result = this.featureCustomService.getAll();
        log.debug("FeatureCustomRest getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_delete/features")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<FeatureDTO> featureDTOS){
        List<FeatureDTO> result = this.featureCustomService.deleteAll(featureDTOS);
        log.debug("FeatureCustomRest deleteAll({}): {}", featureDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_save/features")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<FeatureDTO> featureDTOS){
        List<FeatureDTO> result = this.featureCustomService.saveAll(featureDTOS);
        log.debug("FeatureCustomRest saveAll({}): {}", featureDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/role/{roleId}/_all/features")
    public ResponseEntity<IResponseMessage> getAllByRole(@PathVariable("roleId") Long roleId){
        List<FeatureDTO> result = this.featureCustomService.getAllByRole(roleId);
        log.debug("FeatureCustomRest getAllByRole({}): {}", roleId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all/features")
    public ResponseEntity<IResponseMessage> getAllByUser(@PathVariable("userId") Long userId){
        List<FeatureDTO> result = this.featureCustomService.getAllByUser(userId);
        log.debug("FeatureCustomRest getAllByUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
