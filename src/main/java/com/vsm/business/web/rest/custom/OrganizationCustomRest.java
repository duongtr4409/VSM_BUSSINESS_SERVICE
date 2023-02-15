package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.service.OrganizationService;
import com.vsm.business.service.custom.OrganizationCustomService;
import com.vsm.business.service.custom.search.service.OrganizationSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.utils.GenerateCodeUtils;
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

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrganizationCustomRest {
    private final Logger log = LoggerFactory.getLogger(OrganizationCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationService organizationService;

    private final OrganizationRepository organizationRepository;

    private final OrganizationCustomService organizationCustomService;

    private final OrganizationSearchService organizationSearchService;

    private Map<String, OrganizationDTO> organizationDTOMap = new HashMap<>();

    public OrganizationCustomRest(OrganizationService organizationService, OrganizationRepository organizationRepository, OrganizationCustomService organizationCustomService, OrganizationSearchService organizationSearchService) {
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
        this.organizationCustomService = organizationCustomService;
        this.organizationSearchService = organizationSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadOrganization(){
        List<OrganizationDTO> organizationDTOList = this.organizationCustomService.findAllIgnoreField().stream().map(ele ->{
            ele.setChildOrganization(new ArrayList<>());
            ele.setOrgParent(null);
            return ele;
        }).collect(Collectors.toList());
        this.organizationDTOMap = new HashMap<>();
        organizationDTOList.stream().forEach(ele -> {
            this.organizationDTOMap.put(ele.getOrganizationCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(OrganizationDTO organizationDTO){
        if(this.organizationDTOMap == null || this.organizationDTOMap.size() == 0) this.loadOrganization();
        try {
            String code = this.generateCodeUtils.generateCode(organizationDTO.getOrganizationName(), this.organizationDTOMap, OrganizationDTO.class, "getOrganizationCode");
            organizationDTO.setOrganizationCode(code);
            this.organizationDTOMap.put(code, organizationDTO);

            this.organizationDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.organizationDTOMap = null;

            return null;
        }

    }

    /**
     * {@code POST  /organizations} : Create a new organization.
     *
     * @param organizationDTO the organizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationDTO, or with status {@code 400 (Bad Request)} if the organization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organizations")
    public ResponseEntity<IResponseMessage> createOrganization(@RequestBody OrganizationDTO organizationDTO) throws Exception {
        log.debug("REST request to save Organization : {}", organizationDTO);
        if (organizationDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(organizationDTO));
        }

        // generate code
        organizationDTO.setOrganizationCode(this.generateCode(organizationDTO));

        //OrganizationDTO result = organizationService.save(organizationDTO);
        OrganizationDTO result = organizationCustomService.customSave(organizationDTO);

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(organizationDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /organizations/:id} : Updates an existing organization.
     *
     * @param id              the id of the organizationDTO to save.
     * @param organizationDTO the organizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationDTO,
     * or with status {@code 400 (Bad Request)} if the organizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organizations/{id}")
    public ResponseEntity<IResponseMessage> updateOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationDTO organizationDTO
    ) throws Exception {
        log.debug("REST request to update Organization : {}, {}", id, organizationDTO);
        if (organizationDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(organizationDTO));
        }
        if (!Objects.equals(id, organizationDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(organizationDTO));
        }

        if (!organizationRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(organizationDTO));
        }

//        OrganizationDTO result = organizationService.save(organizationDTO);
        OrganizationDTO result = organizationCustomService.customSave(organizationDTO);

        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(organizationDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /organizations/:id} : Partial updates given fields of an existing organization, field will ignore if it is null
     *
     * @param id              the id of the organizationDTO to save.
     * @param organizationDTO the organizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationDTO,
     * or with status {@code 400 (Bad Request)} if the organizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organizations/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationDTO organizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organization partially : {}, {}", id, organizationDTO);
        if (organizationDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(organizationDTO));
        }
        if (!Objects.equals(id, organizationDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(organizationDTO));
        }

        if (!organizationRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(organizationDTO));
        }

        Optional<OrganizationDTO> result = organizationService.partialUpdate(organizationDTO);

        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(organizationDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /organizations} : get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizations in body.
     */
    @GetMapping("/organizations")
    public ResponseEntity<IResponseMessage> getAllOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of Organizations");
        Page<OrganizationDTO> page = organizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /organizations/:id} : get the "id" organization.
     *
     * @param id the id of the organizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organizations/{id}")
    public ResponseEntity<IResponseMessage> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        Optional<OrganizationDTO> organizationDTO = organizationService.findOne(id);
        if (!organizationDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(organizationDTO.get()));
    }

    /**
     * {@code DELETE  /organizations/:id} : delete the "id" organization.
     *
     * @param id the id of the organizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<IResponseMessage> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/organizations?query=:query} : search for the organization corresponding
     * to the query.
     *
     * @param query    the query of the organization search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/organizations")
    public ResponseEntity<IResponseMessage> searchOrganizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Organizations for query {}", query);
        Page<OrganizationDTO> page = organizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/_all/organizations")
    public ResponseEntity<IResponseMessage> findAll(@RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField){
        return ResponseEntity.ok().body(new LoadedMessage(organizationCustomService.findAll(ignoreField)));
    }

    @GetMapping("/_all-child-org/{id}/organizations")
    public ResponseEntity<IResponseMessage> getAllChildOrganization(@PathVariable("id") Long id){
        log.info("OrganizationCustomRest: getAllChildOrganization({})", id);
        return ResponseEntity.ok().body(new LoadedMessage(this.organizationCustomService.customGetALlChildOrganization(id)));
    }

    @GetMapping("/_child-org/{id}/organizations")
    public ResponseEntity<IResponseMessage> getChildOrganization(@PathVariable("id") Long id){
        log.info("OrganizationCustomRest: getChildOrganization({})", id);
        List<OrganizationDTO> result = this.organizationCustomService.getAllOrganization(id);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/organizations")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<OrganizationDTO> organizationDTOList){
        log.debug("REST request to OrganizationCustomRest: saveAll({})", organizationDTOList);
        List<OrganizationDTO> result = this.organizationCustomService.saveAll(organizationDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/organizations")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody OrganizationDTO organizationDTO){
        if(this.organizationDTOMap.get(this.generateCodeUtils.getCode(organizationDTO.getOrganizationName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(organizationDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(organizationDTO));
    }

    @GetMapping("/_custom_all/organizations")
    public ResponseEntity<IResponseMessage> customGetAll(){
        List<OrganizationDTO> result = this.organizationCustomService.customGetAllOrganization();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/organizations")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody OrganizationDTO organizationDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.organizationSearchService.simpleQuerySearch(organizationDTO, pageable);
        log.debug("OrganizationCustomRest: customSearch(IBaseSearchDTO: {}): {}", organizationDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/organizations")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody OrganizationDTO organizationDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.organizationSearchService.simpleQuerySearchWithParam(organizationDTO, paramMaps, pageable);
        log.debug("OrganizationCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", organizationDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
