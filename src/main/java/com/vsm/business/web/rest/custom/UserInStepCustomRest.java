package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.service.UserInStepService;
import com.vsm.business.service.custom.UserInStepCustomService;
import com.vsm.business.service.custom.search.service.UserInStepSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserInStepDTO;

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
 * REST controller for managing {@link com.vsm.business.domain.UserInStep}.
 */
@RestController
@RequestMapping("/api")
public class UserInStepCustomRest {

    private final Logger log = LoggerFactory.getLogger(UserInStepCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceUserInStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInStepService userInStepService;

    private final UserInStepRepository userInStepRepository;

    private final UserInStepCustomService userInStepCustomService;

    private final UserInStepSearchService userInStepSearchService;

    public UserInStepCustomRest(UserInStepService userInStepService, UserInStepRepository userInStepRepository, UserInStepCustomService userInStepCustomService, UserInStepSearchService userInStepSearchService) {
        this.userInStepService = userInStepService;
        this.userInStepRepository = userInStepRepository;
        this.userInStepCustomService = userInStepCustomService;
        this.userInStepSearchService = userInStepSearchService;
    }

    /**
     * {@code POST  /user-in-steps} : Create a new userInStep.
     *
     * @param userInStepDTO the userInStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInStepDTO, or with status {@code 400 (Bad Request)} if the userInStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-in-steps")
    public ResponseEntity<IResponseMessage> createUserInStep(@Valid @RequestBody UserInStepDTO userInStepDTO) throws URISyntaxException {
        log.debug("REST request to save UserInStep : {}", userInStepDTO);
        if (userInStepDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(userInStepDTO));
        }
        UserInStepDTO result = userInStepService.save(userInStepDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(userInStepDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /user-in-steps/:id} : Updates an existing userInStep.
     *
     * @param id            the id of the userInStepDTO to save.
     * @param userInStepDTO the userInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInStepDTO,
     * or with status {@code 400 (Bad Request)} if the userInStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-in-steps/{id}")
    public ResponseEntity<IResponseMessage> updateUserInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserInStepDTO userInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserInStep : {}, {}", id, userInStepDTO);
        if (userInStepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(userInStepDTO));
        }
        if (!Objects.equals(id, userInStepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(userInStepDTO));
        }

        if (!userInStepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(userInStepDTO));
        }

        UserInStepDTO result = userInStepService.save(userInStepDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userInStepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /user-in-steps/:id} : Partial updates given fields of an existing userInStep, field will ignore if it is null
     *
     * @param id            the id of the userInStepDTO to save.
     * @param userInStepDTO the userInStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInStepDTO,
     * or with status {@code 400 (Bad Request)} if the userInStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userInStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userInStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-in-steps/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateUserInStep(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserInStepDTO userInStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserInStep partially : {}, {}", id, userInStepDTO);
        if (userInStepDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(userInStepDTO));
        }
        if (!Objects.equals(id, userInStepDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(userInStepDTO));
        }

        if (!userInStepRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(userInStepDTO));
        }

        Optional<UserInStepDTO> result = userInStepService.partialUpdate(userInStepDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userInStepDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /user-in-steps} : get all the userInSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInSteps in body.
     */
    @GetMapping("/user-in-steps")
    public ResponseEntity<IResponseMessage> getAllUserInSteps(Pageable pageable) {
        log.debug("REST request to get a page of UserInSteps");
        Page<UserInStepDTO> page = userInStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /user-in-steps/:id} : get the "id" userInStep.
     *
     * @param id the id of the userInStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-in-steps/{id}")
    public ResponseEntity<IResponseMessage> getUserInStep(@PathVariable Long id) {
        log.debug("REST request to get UserInStep : {}", id);
        Optional<UserInStepDTO> userInStepDTO = userInStepService.findOne(id);

        if (!userInStepDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(userInStepDTO.get()));
    }

    /**
     * {@code DELETE  /user-in-steps/:id} : delete the "id" userInStep.
     *
     * @param id the id of the userInStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-in-steps/{id}")
    public ResponseEntity<IResponseMessage> deleteUserInStep(@PathVariable Long id) {
        log.debug("REST request to delete UserInStep : {}", id);
        userInStepService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/user-in-steps?query=:query} : search for the userInStep corresponding
     * to the query.
     *
     * @param query    the query of the userInStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-in-steps")
    public ResponseEntity<List<UserInStepDTO>> searchUserInSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserInSteps for query {}", query);
        Page<UserInStepDTO> page = userInStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/step-in-processes/{stepInProcessId}/_all/user-in-steps")
    public ResponseEntity<IResponseMessage> findAllByStepInProcessId(@PathVariable("stepInProcessId") Long stepInProcessId) {
        List<UserInStepDTO> result = userInStepCustomService.findAllByStepInProcessId(stepInProcessId);
        log.debug("REST request to findAllByStepInProcessId({}): {}", stepInProcessId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/user-in-steps")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody UserInStepDTO userInStepDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userInStepSearchService.simpleQuerySearch(userInStepDTO, pageable);
        log.debug("UserInStepCustomRest: customSearch(IBaseSearchDTO: {}): {}", userInStepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/user-in-steps")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody UserInStepDTO userInStepDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userInStepSearchService.simpleQuerySearchWithParam(userInStepDTO, paramMaps, pageable);
        log.debug("UserInStepCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", userInStepDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
