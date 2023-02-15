package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.ErrorMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.service.CategoryGroupService;
import com.vsm.business.service.custom.CategoryGroupCustomService;
import com.vsm.business.service.custom.search.service.CategoryGroupSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CategoryGroupDTO;
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

@RestController
@RequestMapping("/api")
public class CategoryGroupCustomRest {

    private final Logger log = LoggerFactory.getLogger(CategoryGroupCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceCategoryGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryGroupService categoryGroupService;

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupCustomService categoryGroupCustomService;

    private final CategoryGroupSearchService categoryGroupSearchService;

    private final String ERROR_DUPLICATE_NAME = "Trong danh mục hiện tại đã tồn tại danh mục này";

    public CategoryGroupCustomRest(CategoryGroupService categoryGroupService, CategoryGroupRepository categoryGroupRepository, CategoryGroupCustomService categoryGroupCustomService, CategoryGroupSearchService categoryGroupSearchService) {
        this.categoryGroupService = categoryGroupService;
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryGroupCustomService = categoryGroupCustomService;
        this.categoryGroupSearchService = categoryGroupSearchService;
    }

    /**
     * {@code POST  /category-groups} : Create a new categoryGroup.
     *
     * @param categoryGroupDTO the categoryGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryGroupDTO, or with status {@code 400 (Bad Request)} if the categoryGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-groups")
    public ResponseEntity<IResponseMessage> createCategoryGroup(@RequestBody CategoryGroupDTO categoryGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryGroup : {}", categoryGroupDTO);
        if (categoryGroupDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(categoryGroupDTO));
        }

//        CategoryGroupDTO result = categoryGroupService.save(categoryGroupDTO);
        CategoryGroupDTO result = null;
        try {
            result = categoryGroupCustomService.saveCustom(categoryGroupDTO);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorMessage( ERROR_DUPLICATE_NAME, categoryGroupDTO));
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(categoryGroupDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    /**
     * {@code PUT  /category-groups/:id} : Updates an existing categoryGroup.
     *
     * @param id               the id of the categoryGroupDTO to save.
     * @param categoryGroupDTO the categoryGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryGroupDTO,
     * or with status {@code 400 (Bad Request)} if the categoryGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-groups/{id}")
    public ResponseEntity<IResponseMessage> updateCategoryGroup(@PathVariable(value = "id", required = false) final Long id, @RequestBody CategoryGroupDTO categoryGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryGroup : {}, {}", id, categoryGroupDTO);
        if (categoryGroupDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(categoryGroupDTO));
        }
        if (!Objects.equals(id, categoryGroupDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(categoryGroupDTO));
        }

        if (!categoryGroupRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(categoryGroupDTO));
        }

//        CategoryGroupDTO result = categoryGroupService.save(categoryGroupDTO);
        CategoryGroupDTO result = null;
        try {
            result = categoryGroupCustomService.saveCustom(categoryGroupDTO);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorMessage( ERROR_DUPLICATE_NAME, categoryGroupDTO));
        }

        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(categoryGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    /**
     * {@code PATCH  /category-groups/:id} : Partial updates given fields of an existing categoryGroup, field will ignore if it is null
     *
     * @param id               the id of the categoryGroupDTO to save.
     * @param categoryGroupDTO the categoryGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryGroupDTO,
     * or with status {@code 400 (Bad Request)} if the categoryGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-groups/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateCategoryGroup(@PathVariable(value = "id", required = false) final Long id, @RequestBody CategoryGroupDTO categoryGroupDTO) throws URISyntaxException {
        log.debug("REST request to partial update CategoryGroup partially : {}, {}", id, categoryGroupDTO);
        if (categoryGroupDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(categoryGroupDTO));
        }
        if (!Objects.equals(id, categoryGroupDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(categoryGroupDTO));
        }

        if (!categoryGroupRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(categoryGroupDTO));
        }

        Optional<CategoryGroupDTO> result = categoryGroupService.partialUpdate(categoryGroupDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(categoryGroupDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    /**
     * {@code GET  /category-groups} : get all the categoryGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryGroups in body.
     */
    @GetMapping("/category-groups")
    public ResponseEntity<IResponseMessage> getAllCategoryGroups(Pageable pageable) {
        log.debug("REST request to get a page of CategoryGroups");
        Page<CategoryGroupDTO> page = categoryGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    /**
     * {@code GET  /category-groups/:id} : get the "id" categoryGroup.
     *
     * @param id the id of the categoryGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-groups/{id}")
    public ResponseEntity<IResponseMessage> getCategoryGroup(@PathVariable Long id) {
        log.debug("REST request to get CategoryGroup : {}", id);
        Optional<CategoryGroupDTO> categoryGroupDTO = categoryGroupService.findOne(id);
        return ResponseEntity.ok().body(new LoadedMessage(categoryGroupDTO.get()));
    }

    /**
     * {@code DELETE  /category-groups/:id} : delete the "id" categoryGroup.
     *
     * @param id the id of the categoryGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-groups/{id}")
    public ResponseEntity<IResponseMessage> deleteCategoryGroup(@PathVariable Long id) {
        log.debug("REST request to delete CategoryGroup : {}", id);
        categoryGroupService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    /**
     * {@code SEARCH  /_search/category-groups?query=:query} : search for the categoryGroup corresponding
     * to the query.
     *
     * @param query    the query of the categoryGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/category-groups")
    public ResponseEntity<IResponseMessage> searchCategoryGroups(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoryGroups for query {}", query);
        Page<CategoryGroupDTO> page = categoryGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/_all/category-groups")
    public ResponseEntity<IResponseMessage> findAll(@RequestParam(value = "ignoreField", defaultValue = "false") boolean ignoreField) {
        log.debug("REST request to findAll CategoryGroups");
        return ResponseEntity.ok().body(new LoadedMessage(categoryGroupCustomService.findAll(ignoreField)));
    }

    @PostMapping("/_delete/category-groups")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<CategoryGroupDTO> categoryGroupDTOList) {
        log.debug("REST request to deleteAll({}) CategoryGroups", categoryGroupDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(categoryGroupCustomService.delete(categoryGroupDTOList)));
    }

    @PostMapping("/_save/category-groups")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<CategoryGroupDTO> categoryGroupDTOList){
        List<CategoryGroupDTO> result = categoryGroupCustomService.saveAll(categoryGroupDTOList);
        log.debug("REST request to saveAll({})", categoryGroupDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all-child/{id}/category-groups")
    public ResponseEntity<IResponseMessage> getAllChild(@PathVariable("id") Long id){
        log.debug("REST request to CategoryGroupResource: getAllChild({})", id);
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllChild(id);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all_child_pageable/{id}/category-groups")
    public ResponseEntity<IResponseMessage> getAllChildPageable(@PathVariable("id") Long id, Pageable pageable){
        log.debug("REST request to CategoryGroupResource: getAllChild({})", id);
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllChildPageable(id, pageable);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_count_child_pageable/{id}/category-groups")
    public ResponseEntity<IResponseMessage> countAllChildPageable(@PathVariable("id") Long id){
        log.debug("REST request to CategoryGroupResource: getAllChild({})", id);
        Long result = this.categoryGroupCustomService.countAllChildPageable(id);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all_has_child/category-groups")          // API lấy tất cả danh mục cấp to nhất (không có cha)
    public ResponseEntity<IResponseMessage> getAllCategoryGroupHasChild(){
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllCategoryGroupHasChild();
        log.debug("REST request to CategoryGroupResource: getAllCategoryGroupHasChild(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all_not_has_parent/category-groups")          // API lấy tất cả danh mục cấp to nhất (không có cha)
    public ResponseEntity<IResponseMessage> getAllCategoryMaxLevel(){
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllChild(null);
        log.debug("REST request to CategoryGroupResource: getAllCategoryGroupHasChild(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all-child/{id}/search_name/category-groups")
    public ResponseEntity<IResponseMessage> getAllChildByName(@PathVariable("id") Long id, @RequestParam(value = "name", required = false, defaultValue = "") String name, Pageable pageable){
        log.debug("REST request to CategoryGroupResource: getAllChild({})", id);
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllChildByName(id, name, pageable);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/search_name/bp_cargo/category-groups")       // api lấy danh sách nhà cung cấp hàng hóa
    public ResponseEntity<IResponseMessage> getAllBpCargoByName(@RequestParam(value = "name", required = false, defaultValue = "") String name, Pageable pageable){
        log.debug("REST request to CategoryGroupResource: getAllBpCargoByName({})", name);
        List<CategoryGroupDTO> result = this.categoryGroupCustomService.getAllBpByName(name, pageable);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/category-groups")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody CategoryGroupDTO categoryGroupDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.categoryGroupSearchService.simpleQuerySearch(categoryGroupDTO, pageable);
        log.debug("CategoryGroupCustomRest: customSearch(IBaseSearchDTO: {}): {}", categoryGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/category-groups")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody CategoryGroupDTO categoryGroupDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.categoryGroupSearchService.simpleQuerySearchWithParam(categoryGroupDTO, paramMaps, pageable);
        log.debug("CategoryGroupCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", categoryGroupDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
