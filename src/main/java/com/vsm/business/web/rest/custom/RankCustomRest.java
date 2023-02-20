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
import com.vsm.business.repository.RankRepository;
import com.vsm.business.service.RankService;
import com.vsm.business.service.custom.RankCustomService;
import com.vsm.business.service.custom.search.service.RankSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.dto.RankDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.Rank}.
 */
@RestController
@RequestMapping("/api")
public class RankCustomRest {

    private final Logger log = LoggerFactory.getLogger(RankCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RankService rankService;

    private final RankRepository rankRepository;

    private RankCustomService rankCustomService;

    private RankSearchService rankSearchService;

    private Map<String, RankDTO> rankDTOMap = new HashMap<>();

    public RankCustomRest(RankService rankService, RankRepository rankRepository, RankCustomService rankCustomService, RankSearchService rankSearchService) {
        this.rankService = rankService;
        this.rankRepository = rankRepository;
        this.rankCustomService = rankCustomService;
        this.rankSearchService = rankSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadRank(){
        List<RankDTO> rankDTOList = this.rankCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setModified(null);
            ele.setCreated(null);
            return ele;
        }).collect(Collectors.toList());
        this.rankDTOMap = new HashMap<>();
        rankDTOList.stream().forEach(ele -> {
            this.rankDTOMap.put(ele.getRankCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(RankDTO rankDTO){
        if(this.rankDTOMap == null ||  this.rankDTOMap.size() == 0) this.loadRank();
        try {
            String code = this.generateCodeUtils.generateCode(rankDTO.getRankName(), this.rankDTOMap, RankDTO.class, "getRankCode");
            rankDTO.setRankCode(code);
            this.rankDTOMap.put(code, rankDTO);

            this.rankDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("{}", e);
            return null;
        }

    }

    /**
     * {@code POST  /ranks} : Create a new rank.
     *
     * @param rankDTO the rankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rankDTO, or with status {@code 400 (Bad Request)} if the rank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ranks")
    public ResponseEntity<IResponseMessage> createRank(@Valid @RequestBody RankDTO rankDTO) throws URISyntaxException {
        log.debug("REST request to save Rank : {}", rankDTO);
        if (rankDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(rankDTO));
        }

        // generate code
        rankDTO.setRankCode(this.generateCode(rankDTO));

//        RankDTO result = rankService.save(rankDTO);
        RankDTO result = rankCustomService.customSave(rankDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(rankDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /ranks/:id} : Updates an existing rank.
     *
     * @param id      the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ranks/{id}")
    public ResponseEntity<IResponseMessage> updateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rank : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(rankDTO));
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(rankDTO));
        }

        if (!rankRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(rankDTO));
        }

//        RankDTO result = rankService.save(rankDTO);
        RankDTO result = rankCustomService.customSave(rankDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(rankDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /ranks/:id} : Partial updates given fields of an existing rank, field will ignore if it is null
     *
     * @param id      the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ranks/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rank partially : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(rankDTO));
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(rankDTO));
        }

        if (!rankRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(rankDTO));
        }

        Optional<RankDTO> result = rankService.partialUpdate(rankDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(rankDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /ranks} : get all the ranks.
     *
     * @param pageable  the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ranks in body.
     */
    @GetMapping("/ranks")
    public ResponseEntity<IResponseMessage> getAllRanks(
        Pageable pageable
    ) {
        log.debug("REST request to get a page of Ranks");
        Page<RankDTO> page;

        page = rankService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /ranks/:id} : get the "id" rank.
     *
     * @param id the id of the rankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ranks/{id}")
    public ResponseEntity<IResponseMessage> getRank(@PathVariable Long id) {
        log.debug("REST request to get Rank : {}", id);
        Optional<RankDTO> rankDTO = rankService.findOne(id);

        if (!rankDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(rankDTO.get()));
    }

    /**
     * {@code DELETE  /ranks/:id} : delete the "id" rank.
     *
     * @param id the id of the rankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ranks/{id}")
    public ResponseEntity<IResponseMessage> deleteRank(@PathVariable Long id) {
        log.debug("REST request to delete Rank : {}", id);
        if (!rankCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/ranks?query=:query} : search for the rank corresponding
     * to the query.
     *
     * @param query    the query of the rank search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ranks")
    public ResponseEntity<List<RankDTO>> searchRanks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ranks for query {}", query);
        Page<RankDTO> page = rankService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/ranks")
    public ResponseEntity<IResponseMessage> getAll() {
        List<RankDTO> result = this.rankCustomService.getAll();
        log.debug("StepCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_delete/ranks")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<RankDTO> rankDTOS) {
        List<RankDTO> result = this.rankCustomService.deleteAll(rankDTOS);
        log.debug("RankCustomRest: deleteAll({}) {}", rankDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

	@PostMapping("_save/ranks")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<RankDTO> rankDTOList){
        List<RankDTO> result = rankCustomService.saveAll(rankDTOList);
        log.debug("RankCustomRest: saveAll({}): ", rankDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/organization/_all/{organiztionId}/rank")
    public ResponseEntity<IResponseMessage> getAllByOrganizaionId(@PathVariable("organiztionId") Long organiztionId){
        List<RankDTO> result = rankCustomService.findAllByOrganizationId(organiztionId);
        log.debug("StepCustomRest: getAllByOrganizaionId({}): {}", organiztionId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/organization/_all_rank_in_org/{rankId}/rank")
    public ResponseEntity<IResponseMessage> getAllOrganizaionByRankInOrg(@PathVariable("rankId") Long rankId){
        List<OrganizationDTO> result = rankCustomService.findAllOrgByRankInOrg(rankId);
        log.debug("StepCustomRest: getAllOrganizaionByRankInOrg({}): {}", rankId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/rank")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody RankDTO rankDTO){
        if(this.rankDTOMap.get(this.generateCodeUtils.getCode(rankDTO.getRankCode())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(rankDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(rankDTO));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/rank")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody RankDTO rankDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.rankSearchService.simpleQuerySearch(rankDTO, pageable);
        log.debug("RankCustomRest: customSearch(IBaseSearchDTO: {}): {}", rankDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/rank")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody RankDTO rankDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.rankSearchService.simpleQuerySearchWithParam(rankDTO, paramMaps, pageable);
        log.debug("RankCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", rankDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
