package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.*;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.service.ProcessInfoService;
import com.vsm.business.service.custom.ProcessInfoCustomService;
import com.vsm.business.service.custom.search.service.ProcessInfoSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ProcessInfoDTO;
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
 * REST controller for managing {@link com.vsm.business.domain.ProcessInfo}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInfoCustomRest {

    private final Logger log = LoggerFactory.getLogger(ProcessInfoCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceProcessInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInfoService processInfoService;

    private final ProcessInfoRepository processInfoRepository;

    private ProcessInfoCustomService processInfoCustomService;

    private ProcessInfoSearchService processInfoSearchService;

    private Map<String, ProcessInfoDTO> processInfoDTOMap = new HashMap<>();

    private final String ERROR_EXIST_REQUEST = "Không thể xóa quy trình đang hoạt động";

    public ProcessInfoCustomRest(ProcessInfoService processInfoService, ProcessInfoRepository processInfoRepository, ProcessInfoCustomService processInfoCustomService, ProcessInfoSearchService processInfoSearchService) {
        this.processInfoService = processInfoService;
        this.processInfoCustomService = processInfoCustomService;
        this.processInfoRepository = processInfoRepository;
        this.processInfoSearchService = processInfoSearchService;
    }

    @Scheduled(cron = "${cron.tab}")
    public void loadProcessInfo(){
        List<ProcessInfoDTO> processInfoDTOList = this.processInfoCustomService.getAllIgnoreField().stream().map(ele -> {
            ele.setRequestDTOS(new ArrayList<>());
            ele.setOrganizations(new HashSet<>());
            ele.setCreated(null);
            ele.setModified(null);
            return ele;
        }).collect(Collectors.toList());
        this.processInfoDTOMap = new HashMap<>();
        processInfoDTOList.stream().forEach(ele -> {
            this.processInfoDTOMap.put(ele.getProcessCode(), ele);
        });
    }

    @Autowired
    private GenerateCodeUtils generateCodeUtils;
    public String generateCode(ProcessInfoDTO processInfoDTO){
        if(this.processInfoDTOMap == null || this.processInfoDTOMap.size() == 0) this.loadProcessInfo();
        try {
            String code = this.generateCodeUtils.generateCode(processInfoDTO.getProcessName(), this.processInfoDTOMap, ProcessInfoDTO.class, "getProcessCode");
            processInfoDTO.setProcessCode(code);
            this.processInfoDTOMap.put(code, processInfoDTO);

            this.processInfoDTOMap = null;

            return code;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.debug("{}", e.getStackTrace());

            this.processInfoDTOMap = null;

            return null;
        }
    }

    /**
     * {@code POST  /process-infos} : Create a new processInfo.
     *
     * @param processInfoDTO the processInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInfoDTO, or with status {@code 400 (Bad Request)} if the processInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-infos")
    public ResponseEntity<IResponseMessage> createProcessInfo(@Valid @RequestBody ProcessInfoDTO processInfoDTO) throws Exception {
        log.debug("REST request to save ProcessInfo : {}", processInfoDTO);
        if (processInfoDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(processInfoDTO));
        }

        // generate code
        processInfoDTO.setProcessCode(this.generateCode(processInfoDTO));

        //ProcessInfoDTO result = processInfoService.save(processInfoDTO);
        ProcessInfoDTO result = processInfoCustomService.customSave(processInfoDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(processInfoDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /process-infos/:id} : Updates an existing processInfo.
     *
     * @param id             the id of the processInfoDTO to save.
     * @param processInfoDTO the processInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInfoDTO,
     * or with status {@code 400 (Bad Request)} if the processInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-infos/{id}")
    public ResponseEntity<IResponseMessage> updateProcessInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcessInfoDTO processInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessInfo : {}, {}", id, processInfoDTO);
        if (processInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(processInfoDTO));
        }
        if (!Objects.equals(id, processInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(processInfoDTO));
        }

        if (!processInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(processInfoDTO));
        }

//        ProcessInfoDTO result = processInfoService.save(processInfoDTO);
        ProcessInfoDTO result;
        try {
            result = this.processInfoCustomService.customSave(processInfoDTO);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, processInfoDTO));
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(processInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /process-infos/:id} : Partial updates given fields of an existing processInfo, field will ignore if it is null
     *
     * @param id             the id of the processInfoDTO to save.
     * @param processInfoDTO the processInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInfoDTO,
     * or with status {@code 400 (Bad Request)} if the processInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-infos/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateProcessInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcessInfoDTO processInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessInfo partially : {}, {}", id, processInfoDTO);
        if (processInfoDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(processInfoDTO));
        }
        if (!Objects.equals(id, processInfoDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(processInfoDTO));
        }

        if (!processInfoRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(processInfoDTO));
        }

        Optional<ProcessInfoDTO> result = processInfoService.partialUpdate(processInfoDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(processInfoDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /process-infos} : get all the processInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processInfos in body.
     */
    @GetMapping("/process-infos")
    public ResponseEntity<IResponseMessage> getAllProcessInfos(Pageable pageable) {
        log.info("REST request to get a page of ProcessInfos");
        Page<ProcessInfoDTO> page = processInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /process-infos/:id} : get the "id" processInfo.
     *
     * @param id the id of the processInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-infos/{id}")
    public ResponseEntity<IResponseMessage> getProcessInfo(@PathVariable Long id) {
        log.info("REST request to get ProcessInfo : {}", id);
//        Optional<ProcessInfoDTO> processInfoDTO = processInfoService.findOne(id);
        Optional<ProcessInfoDTO> processInfoDTO = this.processInfoCustomService.getOne(id);
        if (!processInfoDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(processInfoDTO.get()));
    }

    /**
     * {@code DELETE  /process-infos/:id} : delete the "id" processInfo.
     *
     * @param id the id of the processInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-infos/{id}")
    public ResponseEntity<IResponseMessage> deleteProcessInfo(@PathVariable Long id) {
        log.info("REST request to delete ProcessInfo : {}", id);
        if (!processInfoCustomService.delete(id)) {
            return ResponseEntity.ok().body(new FailDeleteMessage(id));
        }
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/process-infos?query=:query} : search for the processInfo corresponding
     * to the query.
     *
     * @param query    the query of the processInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/process-infos")
    public ResponseEntity<List<ProcessInfoDTO>> searchProcessInfos(@RequestParam String query, Pageable pageable) {
        log.info("REST request to search for a page of ProcessInfos for query {}", query);
        Page<ProcessInfoDTO> page = processInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/_all/process-infos")
    public ResponseEntity<IResponseMessage> getAll(@RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField) {
        List<ProcessInfoDTO> result = this.processInfoCustomService.getAll(ignoreField);
        log.debug("REST request to ProcessInfoCustomRest: getAll() {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_page/process-infos")
    public ResponseEntity<IResponseMessage> getPage(@RequestParam(value = "ignoreField", required = false, defaultValue = "false") Boolean ignoreField, Pageable pageable){
        Page<ProcessInfoDTO> result = this.processInfoCustomService.getPage(ignoreField, pageable);
        log.debug("REST request to ProcessInfoCustomRest: getPage() {}", result);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(result.getContent()));
    }

    @PostMapping("/_delete/process-infos")
    public ResponseEntity<IResponseMessage> deleteAll(@Valid @RequestBody List<ProcessInfoDTO> processInfoDTOS) {
        List<ProcessInfoDTO> result = this.processInfoCustomService.deleteAll(processInfoDTOS);
        log.debug("REST request to ProcessInfoCustomRest: deleteAll({}) {}", processInfoDTOS, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_save/process-infos")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<ProcessInfoDTO> processInfoDTOList){
        List<ProcessInfoDTO> result = new ArrayList<>();
        try {
            result = processInfoCustomService.customSaveAll(processInfoDTOList);
            log.debug("REST request to ProcessInfoCustomRest: saveAll({}) {}", processInfoDTOList, result);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ErrorMessage(this.ERROR_EXIST_REQUEST, result));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/check_code/process-infos")
    public ResponseEntity<IResponseMessage> checkCode(@RequestBody ProcessInfoDTO processInfoDTO){
        if(this.processInfoDTOMap.get(this.generateCodeUtils.getCode(processInfoDTO.getProcessName())) != null){
            return ResponseEntity.ok().body(new FailLoadMessage(processInfoDTO));
        }
        return ResponseEntity.ok().body(new LoadedMessage(processInfoDTO));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/process-infos")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody ProcessInfoDTO processInfoDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.processInfoSearchService.simpleQuerySearch(processInfoDTO, pageable);
        log.debug("ProcessInfoCustomRest: customSearch(IBaseSearchDTO: {}): {}", processInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/process-infos")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody ProcessInfoDTO processInfoDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.processInfoSearchService.simpleQuerySearchWithParam(processInfoDTO, paramMaps, pageable);
        log.debug("ProcessInfoCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", processInfoDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
