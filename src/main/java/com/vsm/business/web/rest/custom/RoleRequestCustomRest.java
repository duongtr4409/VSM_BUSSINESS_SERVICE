package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RoleRequestRepository;
import com.vsm.business.service.RoleRequestService;
import com.vsm.business.service.custom.RoleRequestCustomService;
import com.vsm.business.service.dto.RoleRequestDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.RoleRequest}.
 */
@RestController
@RequestMapping("/api")
public class RoleRequestCustomRest {

    private final Logger log = LoggerFactory.getLogger(RoleRequestCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRoleRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleRequestService roleRequestService;

    private final RoleRequestRepository roleRequestRepository;

    private final RoleRequestCustomService roleRequestCustomService;

    public RoleRequestCustomRest(RoleRequestService roleRequestService, RoleRequestRepository roleRequestRepository, RoleRequestCustomService roleRequestCustomService) {
        this.roleRequestService = roleRequestService;
        this.roleRequestRepository = roleRequestRepository;
        this.roleRequestCustomService = roleRequestCustomService;
    }

    /**
     * {@code POST  /role-requests} : Create a new roleRequest.
     *
     * @param roleRequestDTO the roleRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleRequestDTO, or with status {@code 400 (Bad Request)} if the roleRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-requests")
    public ResponseEntity<IResponseMessage> createRoleRequest(@RequestBody RoleRequestDTO roleRequestDTO) throws URISyntaxException {
        log.debug("REST request to save RoleRequest : {}", roleRequestDTO);
        if (roleRequestDTO.getId() != null) {
            return ResponseEntity.ok().body(new FailCreateMessage(roleRequestDTO));
        }
        RoleRequestDTO result = roleRequestService.save(roleRequestDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailCreateMessage(roleRequestDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(roleRequestDTO));
    }

    /**
     * {@code PUT  /role-requests/:id} : Updates an existing roleRequest.
     *
     * @param id the id of the roleRequestDTO to save.
     * @param roleRequestDTO the roleRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleRequestDTO,
     * or with status {@code 400 (Bad Request)} if the roleRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-requests/{id}")
    public ResponseEntity<IResponseMessage> updateRoleRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleRequestDTO roleRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleRequest : {}, {}", id, roleRequestDTO);
        if (roleRequestDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleRequestDTO));
        }
        if (!Objects.equals(id, roleRequestDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(roleRequestDTO));
        }

        if (!roleRequestRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleRequestDTO));
        }

        RoleRequestDTO result = roleRequestService.save(roleRequestDTO);
        if(result == null){
            return ResponseEntity.ok().body(new FailUpdateMessage(roleRequestDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /role-requests/:id} : Partial updates given fields of an existing roleRequest, field will ignore if it is null
     *
     * @param id the id of the roleRequestDTO to save.
     * @param roleRequestDTO the roleRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleRequestDTO,
     * or with status {@code 400 (Bad Request)} if the roleRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IResponseMessage> partialUpdateRoleRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleRequestDTO roleRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleRequest partially : {}, {}", id, roleRequestDTO);
        if (roleRequestDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleRequestDTO));
        }
        if (!Objects.equals(id, roleRequestDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(roleRequestDTO));
        }

        if (!roleRequestRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleRequestDTO));
        }

        Optional<RoleRequestDTO> result = roleRequestService.partialUpdate(roleRequestDTO);
        if(!result.isPresent()){
            return ResponseEntity.ok().body(new FailUpdateMessage(roleRequestDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(roleRequestDTO));
    }

    /**
     * {@code GET  /role-requests} : get all the roleRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleRequests in body.
     */
    @GetMapping("/role-requests")
    public ResponseEntity<IResponseMessage> getAllRoleRequests(Pageable pageable) {
        log.debug("REST request to get a page of RoleRequests");
        Page<RoleRequestDTO> page = roleRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /role-requests/:id} : get the "id" roleRequest.
     *
     * @param id the id of the roleRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-requests/{id}")
    public ResponseEntity<IResponseMessage> getRoleRequest(@PathVariable Long id) {
        log.debug("REST request to get RoleRequest : {}", id);
        Optional<RoleRequestDTO> roleRequestDTO = roleRequestService.findOne(id);
        if(!roleRequestDTO.isPresent()){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(roleRequestDTO.get()));
    }

    /**
     * {@code DELETE  /role-requests/:id} : delete the "id" roleRequest.
     *
     * @param id the id of the roleRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-requests/{id}")
    public ResponseEntity<IResponseMessage> deleteRoleRequest(@PathVariable Long id) {
        log.debug("REST request to delete RoleRequest : {}", id);
        roleRequestService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/role-requests?query=:query} : search for the roleRequest corresponding
     * to the query.
     *
     * @param query the query of the roleRequest search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/role-requests")
    public ResponseEntity<List<RoleRequestDTO>> searchRoleRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RoleRequests for query {}", query);
        Page<RoleRequestDTO> page = roleRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/role-requests")
    public ResponseEntity<IResponseMessage> getAll(){
        List<RoleRequestDTO> result = this.roleRequestCustomService.getAll();
        log.debug("RoleRequestCustomRest getAll(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @DeleteMapping("/_delete/role-requests")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<RoleRequestDTO> roleRequestDTOS){
        List<RoleRequestDTO> result = this.roleRequestCustomService.deleteAll(roleRequestDTOS);
        log.debug("RoleRequestCustomRest deleteAll({}): {}", roleRequestDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/role-requests")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RoleRequestDTO> roleRequestDTOS){
        List<RoleRequestDTO> result = this.roleRequestCustomService.saveAll(roleRequestDTOS);
        log.debug("RoleRequestCustomRest saveAll({}): {}", roleRequestDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/role/{roleId}/_all/role-requests")
    public ResponseEntity<IResponseMessage> getAllByRole(@PathVariable("roleId") Long roleId){
        List<RoleRequestDTO> result = this.roleRequestCustomService.getAllByRole(roleId);
        log.debug("RoleRequestCustomRest getAllByRole({}): {}", roleId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user/{userId}/_all/role-requests")
    public ResponseEntity<IResponseMessage> getAllByUser(@PathVariable("userId") Long userId){
        List<RoleRequestDTO> result = this.roleRequestCustomService.getAllByUser(userId);
        log.debug("RoleRequestCustomRest getAllByUser({}): {}", userId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
