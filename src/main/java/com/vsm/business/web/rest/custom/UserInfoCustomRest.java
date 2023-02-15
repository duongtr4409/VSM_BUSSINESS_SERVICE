package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.config.sercurity.SecurityInterceptor;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.UserInfoService;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.UserInfoCustomService;
import com.vsm.business.service.custom.bo.SearchUserDTO;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.custom.search.service.UserInfoSearchService;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.sysn.user365.SyncUser365Schedule;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.vsm.business.domain.UserInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserInfoCustomRest {

    private final Logger log = LoggerFactory.getLogger(UserInfoCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceUserInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    public SyncUser365Schedule syncUser365Schedule;

    private final UserInfoService userInfoService;

    private final UserInfoRepository userInfoRepository;

    private UserInfoCustomService userInfoCustomService;

    private UserInfoSearchService userInfoSearchService;

    private UserUtils userUtils;

    private AuthenticateUtils authenticateUtils;

    private SecurityInterceptor securityInterceptor;

    public UserInfoCustomRest(UserInfoService userInfoService, UserInfoRepository userInfoRepository, UserInfoCustomService userInfoCustomService, UserInfoSearchService userInfoSearchService, UserUtils userUtils, AuthenticateUtils authenticateUtils, SecurityInterceptor securityInterceptor) {
        this.userInfoService = userInfoService;
        this.userInfoRepository = userInfoRepository;
        this.userInfoCustomService = userInfoCustomService;
        this.userInfoSearchService = userInfoSearchService;
        this.userUtils = userUtils;
        this.authenticateUtils = authenticateUtils;
        this.securityInterceptor = securityInterceptor;
    }

    /**
     * {@code POST  /user-infos} : Create a new userInfo.
     *
     * @param userInfoDTO the userInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInfoDTO, or with status {@code 400 (Bad Request)} if the userInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-infos")
    public ResponseEntity<IResponseMessage> createUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws Exception {
        log.debug("REST request to save UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(userInfoDTO));
        }
//        UserInfoDTO result = userInfoService.save(userInfoDTO);
        UserInfoDTO result = userInfoCustomService.customSave(userInfoDTO, true);

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(userInfoDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /user-infos/:id} : Updates an existing userInfo.
     *
     * @param id          the id of the userInfoDTO to save.
     * @param userInfoDTO the userInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-infos/{id}")
    public ResponseEntity<IResponseMessage> updateUserInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserInfoDTO userInfoDTO
    ) throws Exception {
        log.debug("REST request to update UserInfo : {}, {}", id, userInfoDTO);
        if (userInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(userInfoDTO));
        }
        if (!Objects.equals(id, userInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(userInfoDTO));
        }

        if (!userInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(userInfoDTO));
        }

        //UserInfoDTO result = userInfoService.save(userInfoDTO);
        UserInfoDTO result = userInfoCustomService.customSave(userInfoDTO, false);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /user-infos/:id} : Partial updates given fields of an existing userInfo, field will ignore if it is null
     *
     * @param id          the id of the userInfoDTO to save.
     * @param userInfoDTO the userInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-infos/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateUserInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserInfoDTO userInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserInfo partially : {}, {}", id, userInfoDTO);
        if (userInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(userInfoDTO));
        }
        if (!Objects.equals(id, userInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(userInfoDTO));
        }

        if (!userInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(userInfoDTO));
        }

        Optional<UserInfoDTO> result = userInfoService.partialUpdate(userInfoDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(userInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /user-infos} : get all the userInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInfos in body.
     */
    @GetMapping("/user-infos")
    public ResponseEntity<IResponseMessage> getAllUserInfos(Pageable pageable) {
        log.debug("REST request to get a page of UserInfos");
        Page<UserInfoDTO> page = userInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /user-infos/:id} : get the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-infos/{id}")
    public ResponseEntity<IResponseMessage> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<UserInfoDTO> userInfoDTO = userInfoService.findOne(id);

        if (!userInfoDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(userInfoDTO.get()));
    }

    /**
     * {@code DELETE  /user-infos/:id} : delete the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-infos/{id}")
    public ResponseEntity<IResponseMessage> deleteUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserInfo : {}", id);
        userInfoService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/user-infos?query=:query} : search for the userInfo corresponding
     * to the query.
     *
     * @param query    the query of the userInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-infos")
    public ResponseEntity<List<UserInfoDTO>> searchUserInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserInfos for query {}", query);
        Page<UserInfoDTO> page = userInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

//    @GetMapping("/_all/user-infos")
//    public ResponseEntity<IResponseMessage> getAll(Pageable pageable) {
//        List<UserInfoDTO> result = this.userInfoService.findAll(pageable).toList();
//        log.debug("UserInfoCustomRest: getAll() {}", result);
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }

    @GetMapping("/_all/user-infos")
    public ResponseEntity<IResponseMessage> getAll() {
        List<UserInfoDTO> result = this.userInfoCustomService.getAll();
        log.debug("UserInfoCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/user-infos")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<UserInfoDTO> userInfoDTOS) {
        List<UserInfoDTO> result = this.userInfoCustomService.deleteAll(userInfoDTOS);
        log.debug("UserInfoCustomRest: deleteAll({}) {}", userInfoDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/user-infos")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<UserInfoDTO> userInfoDTOList){
        List<UserInfoDTO> result = this.userInfoCustomService.saveAll(userInfoDTOList);
        log.debug("UserInfoCustomRest: deleteAll({}) {}", userInfoDTOList, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/organization_rank/_all/user-infos")
    public ResponseEntity<IResponseMessage> getAllByOrganizaionAndRank(@PathParam("organizationId") Long organizationId, @PathParam("rankId") Long rankId){
        List<UserInfoDTO> result = this.userInfoCustomService.getAllByOrganizationAndRank(organizationId, rankId);
        log.debug("UserInfoCustomRest: deleteAll(organizationId: {}, rankId: {})", organizationId, rankId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/microsoft/{idInMicrosoft}/_all/user-infos")
    public ResponseEntity<IResponseMessage> findALlIdInMicrosoft(@PathVariable("idInMicrosoft") String idInMicrosoft){
        List<UserInfoDTO> result = this.userInfoCustomService.findAllByIdInMicrosoft(idInMicrosoft);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/user-group/{userGroupId}/_all/user-infos")
    public ResponseEntity<IResponseMessage> getAllByUserGroup(@PathVariable("userGroupId") Long userGroupId){
        log.debug("UserInfoCustomRest: getAllByUserGroup({})", userGroupId);
        List<UserInfoDTO> result = this.userInfoCustomService.getAllByUserGroup(userGroupId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/update-data/user-infos")
    public ResponseEntity<IResponseMessage> resetPassword(@RequestBody UserInfoDTO userInfoDTO) {

        // kiểm tra user đang đăng nhập có quyền thay ADMIN hay không \\
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(!currentUser.getRolesString().stream().anyMatch(ele -> securityInterceptor.ADMIN_ROLE.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authenticateUtils.NOT_AUTHORITY_MESS);


        UserInfoDTO result = this.userInfoCustomService.resetPassword(userInfoDTO);
        log.debug("UserInfoCustomRest: resetPassword({})", userInfoDTO);
        return ResponseEntity.ok().body(new UpdatedMessage(true));
    }

    @PostMapping("/sync-user/{userId}")
    public ResponseEntity<IResponseMessage> syncUser(@PathVariable("userId") Long userId){
        boolean result = this.syncUser365Schedule.syncUserManual(userId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/search_user/user-infos")
    public ResponseEntity<IResponseMessage> searchUser(@RequestBody SearchUserDTO searchUserDTO, Pageable pageable){
        ISearchResponseDTO result = this.userInfoCustomService.searchUser(searchUserDTO, pageable);
        log.debug("UserInfoCustomRest: searchUser(SearchUserDTO: {}): {}", searchUserDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/user-infos")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody UserInfoDTO userInfoDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userInfoSearchService.simpleQuerySearch(userInfoDTO, pageable);
        log.debug("UserInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", userInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/user-infos")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody UserInfoDTO userInfoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.userInfoSearchService.simpleQuerySearchWithParam(userInfoDTO, paramMaps, pageable);
        log.debug("UserInfoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", userInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

//    @PostMapping("/v3/search_custom/user-infos")
//    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody UserInfoDTO userInfoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
//        ISearchResponseDTO result = this.userInfoSearchService.simpleQuerySearchWithParam(userInfoDTO, paramMaps, pageable);
//        log.debug("UserInfoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", userInfoDTO, result);
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }
}
