package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RoleOrganizationRepository;
import com.vsm.business.service.RoleOrganizationService;
import com.vsm.business.service.custom.RoleOrganizationCustomService;
import com.vsm.business.service.dto.RoleOrganizationDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.RoleOrganization}.
 */
@RestController
@RequestMapping("/api")
public class RoleOrganizationCustomRest {

    private final Logger log = LoggerFactory.getLogger(RoleOrganizationCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRoleOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleOrganizationService roleOrganizationService;

    private final RoleOrganizationRepository roleOrganizationRepository;

    private final RoleOrganizationCustomService roleOrganizationCustomService;

    public RoleOrganizationCustomRest(
        RoleOrganizationService roleOrganizationService,
        RoleOrganizationRepository roleOrganizationRepository,
        RoleOrganizationCustomService roleOrganizationCustomService
    ) {
        this.roleOrganizationService = roleOrganizationService;
        this.roleOrganizationRepository = roleOrganizationRepository;
        this.roleOrganizationCustomService = roleOrganizationCustomService;
    }

    /**
     * {@code POST  /role-organizations} : Create a new roleOrganization.
     *
     * @param roleOrganizationDTO the roleOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleOrganizationDTO, or with status {@code 400 (Bad Request)} if the roleOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-organizations")
    public ResponseEntity<IResponseMessage> createRoleOrganization(@RequestBody RoleOrganizationDTO roleOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save RoleOrganization : {}", roleOrganizationDTO);
        if (roleOrganizationDTO.getId() != null) {
            return ResponseEntity.ok().body(new FailCreateMessage(roleOrganizationDTO));
        }
        RoleOrganizationDTO result = roleOrganizationService.save(roleOrganizationDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(roleOrganizationDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /role-organizations/:id} : Updates an existing roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to save.
     * @param roleOrganizationDTO the roleOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the roleOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-organizations/{id}")
    public ResponseEntity<IResponseMessage> updateRoleOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleOrganizationDTO roleOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleOrganization : {}, {}", id, roleOrganizationDTO);
        if (roleOrganizationDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleOrganizationDTO));
        }
        if (!Objects.equals(id, roleOrganizationDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(roleOrganizationDTO));
        }

        if (!roleOrganizationRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleOrganizationDTO));
        }

        RoleOrganizationDTO result = roleOrganizationService.save(roleOrganizationDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(roleOrganizationDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /role-organizations/:id} : Partial updates given fields of an existing roleOrganization, field will ignore if it is null
     *
     * @param id the id of the roleOrganizationDTO to save.
     * @param roleOrganizationDTO the roleOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the roleOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateRoleOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleOrganizationDTO roleOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleOrganization partially : {}, {}", id, roleOrganizationDTO);
        if (roleOrganizationDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleOrganizationDTO));
        }
        if (!Objects.equals(id, roleOrganizationDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(roleOrganizationDTO));
        }

        if (!roleOrganizationRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleOrganizationDTO));
        }

        Optional<RoleOrganizationDTO> result = roleOrganizationService.partialUpdate(roleOrganizationDTO);
        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(roleOrganizationDTO));
        }

        return ResponseEntity.ok().body(new UpdatedMessage(roleOrganizationDTO));
    }

    /**
     * {@code GET  /role-organizations} : get all the roleOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleOrganizations in body.
     */
    @GetMapping("/role-organizations")
    public ResponseEntity<IResponseMessage> getAllRoleOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of RoleOrganizations");
        Page<RoleOrganizationDTO> page = roleOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /role-organizations/:id} : get the "id" roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-organizations/{id}")
    public ResponseEntity<IResponseMessage> getRoleOrganization(@PathVariable Long id) {
        log.debug("REST request to get RoleOrganization : {}", id);
        Optional<RoleOrganizationDTO> roleOrganizationDTO = roleOrganizationService.findOne(id);
        if(!roleOrganizationDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(roleOrganizationDTO.get()));
    }

    /**
     * {@code DELETE  /role-organizations/:id} : delete the "id" roleOrganization.
     *
     * @param id the id of the roleOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-organizations/{id}")
    public ResponseEntity<IResponseMessage> deleteRoleOrganization(@PathVariable Long id) {
        log.debug("REST request to delete RoleOrganization : {}", id);
        roleOrganizationService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/role-organizations?query=:query} : search for the roleOrganization corresponding
     * to the query.
     *
     * @param query the query of the roleOrganization search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/role-organizations")
    public ResponseEntity<List<RoleOrganizationDTO>> searchRoleOrganizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RoleOrganizations for query {}", query);
        Page<RoleOrganizationDTO> page = roleOrganizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/role-organizations")
    public ResponseEntity<IResponseMessage> getAll(){
        List<RoleOrganizationDTO> result = this.roleOrganizationCustomService.getAll();
        log.debug("RoleOrganizationCustomRest getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @DeleteMapping("/_delete/role-organizations")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<RoleOrganizationDTO> roleOrganizationDTOS){
        List<RoleOrganizationDTO> result = this.roleOrganizationCustomService.deleteAll(roleOrganizationDTOS);
        log.debug("RoleOrganizationCustomRest deleteAll({}): {}", roleOrganizationDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/role-organizations")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RoleOrganizationDTO> roleOrganizationDTOS){
        List<RoleOrganizationDTO> result = this.roleOrganizationCustomService.saveAll(roleOrganizationDTOS);
        log.debug("RoleOrganizationCustomRest saveAll({}): {}", roleOrganizationDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/role/{roleId}/_all/role-organizations")
    public ResponseEntity<IResponseMessage> getAllByRole(@PathVariable("roleId") Long roleId){
        List<RoleOrganizationDTO> result = this.roleOrganizationCustomService.getAllByRole(roleId);
        log.debug("RoleOrganizationCustomRest getAllByRole({}): {}", roleId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all/role-organizations")
    public ResponseEntity<IResponseMessage> getAllByUser(@PathVariable("userId") Long userId){
        List<RoleOrganizationDTO> result = this.roleOrganizationCustomService.getAllByUser(userId);
        log.debug("RoleOrganizationCustomRest getAllByUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
