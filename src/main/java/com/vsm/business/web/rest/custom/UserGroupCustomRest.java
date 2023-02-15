package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.UserGroupRepository;
import com.vsm.business.service.UserGroupService;
import com.vsm.business.service.custom.UserGroupCustomService;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import com.vsm.business.service.custom.search.service.UserGroupSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserGroupDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.UserGroup}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupCustomRest {

    private final Logger log = LoggerFactory.getLogger(UserGroupCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupService userGroupService;

    private final UserGroupRepository userGroupRepository;

    private final UserGroupCustomService userGroupCustomService;

    private final UserGroupSearchService userGroupSearchService;

    public UserGroupCustomRest(UserGroupService userGroupService, UserGroupRepository userGroupRepository, UserGroupCustomService userGroupCustomService, UserGroupSearchService userGroupSearchService) {
        this.userGroupService = userGroupService;
        this.userGroupRepository = userGroupRepository;
        this.userGroupCustomService = userGroupCustomService;
        this.userGroupSearchService = userGroupSearchService;
    }

    /**
     * {@code POST  /user-groups} : Create a new userGroup.
     *
     * @param userGroupDTO the userGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupDTO, or with status {@code 400 (Bad Request)} if the userGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-groups")
    public ResponseEntity<IResponseMessage> createUserGroup(@RequestBody UserGroupDTO userGroupDTO) throws URISyntaxException {
        log.debug("REST request to save UserGroup : {}", userGroupDTO);
        if (userGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupDTO result = userGroupService.save(userGroupDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(userGroupDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /user-groups/:id} : Updates an existing userGroup.
     *
     * @param id the id of the userGroupDTO to save.
     * @param userGroupDTO the userGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-groups/{id}")
    public ResponseEntity<IResponseMessage> updateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGroupDTO userGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserGroup : {}, {}", id, userGroupDTO);
        if (userGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserGroupDTO result = userGroupService.save(userGroupDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /user-groups/:id} : Partial updates given fields of an existing userGroup, field will ignore if it is null
     *
     * @param id the id of the userGroupDTO to save.
     * @param userGroupDTO the userGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGroupDTO userGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserGroup partially : {}, {}", id, userGroupDTO);
        if (userGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGroupDTO> result = userGroupService.partialUpdate(userGroupDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /user-groups} : get all the userGroups.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroups in body.
     */
    @GetMapping("/user-groups")
    public ResponseEntity<IResponseMessage> getAllUserGroups(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserGroups");
        Page<UserGroupDTO> page;
        if (eagerload) {
            page = userGroupService.findAllWithEagerRelationships(pageable);
        } else {
            page = userGroupService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /user-groups/:id} : get the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-groups/{id}")
    public ResponseEntity<IResponseMessage> getUserGroup(@PathVariable Long id) {
        log.debug("REST request to get UserGroup : {}", id);
        Optional<UserGroupDTO> userGroupDTO = userGroupService.findOne(id);
        if (!userGroupDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(userGroupDTO.get()));
    }

    /**
     * {@code DELETE  /user-groups/:id} : delete the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-groups/{id}")
    public ResponseEntity<IResponseMessage> deleteUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete UserGroup : {}", id);
        userGroupService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/user-groups?query=:query} : search for the userGroup corresponding
     * to the query.
     *
     * @param query the query of the userGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-groups")
    public ResponseEntity<IResponseMessage> searchUserGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserGroups for query {}", query);
        Page<UserGroupDTO> page = userGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/_all/user-groups")
    public ResponseEntity<IResponseMessage> getAll(){
        List<UserGroupDTO> result = this.userGroupCustomService.getAll();
        log.debug("UserGroupCustomRest: getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("_save/user-groups")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<UserGroupDTO> userGroupDTOS){
        log.debug("UserGroupCustomRest: saveAll({})", userGroupDTOS);
        List<UserGroupDTO> result = this.userGroupCustomService.saveAll(userGroupDTOS);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/user-groups")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody UserGroupDTO userGroupDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userGroupSearchService.simpleQuerySearch(userGroupDTO, pageable);
        log.debug("UserGroupCustomRest: customSearch(IBaseSearchDTO: {}): {}", userGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/user-groups")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody UserGroupDTO userGroupDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userGroupSearchService.simpleQuerySearchWithParam(userGroupDTO, paramMaps, pageable);
        log.debug("UserGroupCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", userGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
