package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailDeleteMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.RoleRepository;
import com.vsm.business.service.RoleService;
import com.vsm.business.service.custom.RoleCustomService;
import com.vsm.business.service.custom.search.service.RoleSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.RoleDTO;
import com.vsm.business.utils.GenerateCodeUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.Role}.
 */
@RestController
@RequestMapping("/api")
public class RoleCustomRest {

    private final Logger log = LoggerFactory.getLogger(RoleCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleService roleService;

    private final RoleRepository roleRepository;

    private RoleCustomService roleCustomService;

    private RoleSearchService roleSearchService;

    private Map<String, RoleDTO> roleDTOMap = new HashMap<>();

    public RoleCustomRest(RoleService roleService, RoleRepository roleRepository, RoleCustomService roleCustomService, RoleSearchService roleSearchService) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.roleCustomService = roleCustomService;
        this.roleSearchService = roleSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadRole(){
        List<RoleDTO> roleDTOList = this.roleCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setCreated(null);
            ele.setModified(null);
            ele.setFeatures(new HashSet<>());
            ele.setUserGroupDTOList(new ArrayList<>());
            ele.setUserInfoDTOList(new ArrayList<>());
            return ele;
        }).collect(Collectors.toList());
        this.roleDTOMap = new HashMap<>();
        roleDTOList.stream().forEach(ele -> {
            this.roleDTOMap.put(ele.getRoleCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(RoleDTO roleDTO){
        if(this.roleDTOMap == null || this.roleDTOMap.size() == 0) this.loadRole();
        try {
            String code = this.generateCodeUtils.generateCode(roleDTO.getRoleName(), this.roleDTOMap, RoleDTO.class, "getRoleCode");
            roleDTO.setRoleCode(code);
            this.roleDTOMap.put(code, roleDTO);

            this.roleDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.roleDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /roles} : Create a new role.
     *
     * @param roleDTO the roleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleDTO, or with status {@code 400 (Bad Request)} if the role has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roles")
    public ResponseEntity<IResponseMessage> createRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException, IllegalAccessException {
        log.debug("REST request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(roleDTO));
        }

        //generate code;
        roleDTO.setRoleCode(this.generateCode(roleDTO));

//        RoleDTO result = roleService.save(roleDTO);
        RoleDTO result = roleCustomService.customSave(roleDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(roleDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /roles/:id} : Updates an existing role.
     *
     * @param id      the id of the roleDTO to save.
     * @param roleDTO the roleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleDTO,
     * or with status {@code 400 (Bad Request)} if the roleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roles/{id}")
    public ResponseEntity<IResponseMessage> updateRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleDTO roleDTO
    ) throws URISyntaxException, IllegalAccessException {
        log.debug("REST request to update Role : {}, {}", id, roleDTO);
        if (roleDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleDTO));
        }
        if (!Objects.equals(id, roleDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleDTO));
        }

        if (!roleRepository.existsById(id)) {
            return ResponseEntity.ok().body(new FailUpdateMessage(roleDTO));
        }

//        RoleDTO result = roleService.save(roleDTO);
        RoleDTO result = roleCustomService.customSave(roleDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(roleDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /roles/:id} : Partial updates given fields of an existing role, field will ignore if it is null
     *
     * @param id      the id of the roleDTO to save.
     * @param roleDTO the roleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleDTO,
     * or with status {@code 400 (Bad Request)} if the roleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/roles/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleDTO roleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Role partially : {}, {}", id, roleDTO);
        if (roleDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(roleDTO));
        }
        if (!Objects.equals(id, roleDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(roleDTO));
        }

        if (!roleRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(roleDTO));
        }

        Optional<RoleDTO> result = roleService.partialUpdate(roleDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(roleDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /roles} : get all the roles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @GetMapping("/roles")
    public ResponseEntity<IResponseMessage> getAllRoles(
        Pageable pageable
    ) {
        log.debug("REST request to get a page of Roles");
        Page<RoleDTO> page;

        page = roleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /roles/:id} : get the "id" role.
     *
     * @param id the id of the roleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<IResponseMessage> getRole(@PathVariable Long id) throws IllegalAccessException {
        log.debug("REST request to get Role : {}", id);
//        Optional<RoleDTO> roleDTO = roleService.findOne(id);
//        if (!roleDTO.isPresent()) {
//            return ResponseEntity.ok().body(new FailLoadMessage(id));
//        }
//        return ResponseEntity.ok().body(new LoadedMessage(roleDTO.get()));
        RoleDTO result = roleCustomService.customFindOne(id);
        if(result == null){
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * {@code DELETE  /roles/:id} : delete the "id" role.
     *
     * @param id the id of the roleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<IResponseMessage> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        if (!roleCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/roles?query=:query} : search for the role corresponding
     * to the query.
     *
     * @param query    the query of the role search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/roles")
    public ResponseEntity<List<RoleDTO>> searchRoles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Roles for query {}", query);
        Page<RoleDTO> page = roleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/roles")
    public ResponseEntity<IResponseMessage> getAll() {
//        List<RoleDTO> result = this.roleCustomService.getAll();
        List<RoleDTO> result = roleCustomService.customFindAll();
        log.debug("RoleCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/roles")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<RoleDTO> roleDTOS) {
        List<RoleDTO> result = this.roleCustomService.deleteAll(roleDTOS);
        log.debug("RoleCustomRest: deleteAll({}) {}", roleDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/roles")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RoleDTO> roleDTOList){
        List<RoleDTO> result = this.roleCustomService.saveAll(roleDTOList);
        log.debug("RoleCustomRest: saveAll({})", roleDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/roles")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody RoleDTO roleDTO){
        if(this.roleDTOMap.get(this.generateCodeUtils.getCode(roleDTO.getRoleName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(roleDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(roleDTO));
    }

    @GetMapping("/user/{userId}/_all/roles")
    public ResponseEntity<IResponseMessage> getAllByUser(@PathVariable("userId") Long userId){
        log.info("RoleCustomRest: getAllByUser({}):", userId);
        List<RoleDTO> result = this.roleCustomService.getAllByUser(userId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user-group/{userGroupId}/_all/roles")
    public ResponseEntity<IResponseMessage> getAllByUserGroupId(@PathVariable("userGroupId") Long userGroupId){
        log.info("RoleCustomRest: getAllByUserGroupId({}):", userGroupId);
        List<RoleDTO> result = this.roleCustomService.getAllByUserGroup(userGroupId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/roles")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RoleDTO roleDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.roleSearchService.simpleQuerySearch(roleDTO, pageable);
        log.debug("RoleCustomRest: customSearch(IBaseSearchDTO: {}): {}", roleDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/roles")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RoleDTO roleDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.roleSearchService.simpleQuerySearchWithParam(roleDTO, paramMaps, pageable);
        log.debug("RoleCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", roleDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
