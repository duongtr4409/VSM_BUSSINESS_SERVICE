package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.DeletedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.repository.CategoryDataRepository;
import com.vsm.business.service.CategoryDataService;
import com.vsm.business.service.custom.CategoryDataCustomService;
import com.vsm.business.web.rest.errors.CustomURISyntaxException;
import com.vsm.business.service.custom.search.service.CategoryDataSearchService;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.CategoryDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryDataCustomRest {
    private final Logger log = LoggerFactory.getLogger(CategoryDataCustomRest.class);

    private final CategoryDataService categoryDataService;

    private final CategoryDataRepository categoryDataRepository;

    private final CategoryDataCustomService categoryDataCustomService;

    private final CategoryDataSearchService categoryDataSearchService;

    public CategoryDataCustomRest(CategoryDataService categoryDataService, CategoryDataRepository categoryDataRepository, CategoryDataCustomService categoryDataCustomService, CategoryDataSearchService categoryDataSearchService) {
        this.categoryDataService = categoryDataService;
        this.categoryDataRepository = categoryDataRepository;
        this.categoryDataCustomService = categoryDataCustomService;
        this.categoryDataSearchService = categoryDataSearchService;
    }

    @PostMapping("/category-data")
    public ResponseEntity<IResponseMessage> createCategoryData(@Valid @RequestBody CategoryDataDTO categoryDataDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryData : {}", categoryDataDTO);
        if (categoryDataDTO.getId() != null) {
            return ResponseEntity.ok().body(FailCreateMessage.ExistId(categoryDataDTO));
        }
        CategoryDataDTO result = categoryDataService.save(categoryDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(categoryDataDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    @PutMapping("/category-data/{id}")
    public ResponseEntity<IResponseMessage> updateCategoryData(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CategoryDataDTO categoryDataDTO) throws CustomURISyntaxException {
        log.debug("REST request to update CategoryData : {}, {}", id, categoryDataDTO);
        if (categoryDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(categoryDataDTO));
        }
        if (!Objects.equals(id, categoryDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(categoryDataDTO));
        }
        if (!categoryDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(categoryDataDTO));
        }
        CategoryDataDTO result = categoryDataService.save(categoryDataDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(categoryDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    @PatchMapping(value = "/category-data/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<IResponseMessage> partialUpdateCategoryData(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody CategoryDataDTO categoryDataDTO) throws CustomURISyntaxException {
        log.debug("REST request to partial update CategoryData partially : {}, {}", id, categoryDataDTO);
        if (categoryDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(categoryDataDTO));
        }
        if (!Objects.equals(id, categoryDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(categoryDataDTO));
        }

        if (!categoryDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(categoryDataDTO));
        }

        Optional<CategoryDataDTO> result = categoryDataService.partialUpdate(categoryDataDTO);
        if (!result.isPresent()) {
            return ResponseEntity.ok().body(new FailUpdateMessage(categoryDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result.get()));
    }

    @GetMapping("/category-data")
    public ResponseEntity<IResponseMessage> getAllCategoryData(Pageable pageable) {
        log.debug("REST request to get a page of CategoryData");
        Page<CategoryDataDTO> page = categoryDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/category-data/{id}")
    public ResponseEntity<IResponseMessage> getCategoryData(@PathVariable Long id) {
        log.debug("REST request to get Step : {}", id);
        Optional<CategoryDataDTO> categoryDataDTO = categoryDataService.findOne(id);
        if (!categoryDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(categoryDataDTO.get()));
    }

    @DeleteMapping("/category-data/{id}")
    public ResponseEntity<IResponseMessage> deleteCategoryData(@PathVariable Long id) {
        log.debug("REST request to delete Step : {}", id);
        categoryDataService.delete(id);
        return ResponseEntity.ok().body(new DeletedMessage(id));
    }

    @GetMapping("/_search/category-data")
    public ResponseEntity<IResponseMessage> searchCategoryData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Steps for query {}", query);
        Page<CategoryDataDTO> page = categoryDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(new LoadedMessage(page.getContent()));
    }

    @GetMapping("/category-groups/{categoryGroupId}/_all/category-data")
    public ResponseEntity<IResponseMessage> findAllByCategoryGroupId(@PathVariable("categoryGroupId") Long categoryGroupId) {
        log.debug("REST request to findAllByCategoryGroupId CategoryDataCustomRest");
        return ResponseEntity.ok().body(new LoadedMessage(categoryDataCustomService.findAllByCategoryGroupId(categoryGroupId)));
    }

    @PostMapping("/_delete/category-data")
    public ResponseEntity<IResponseMessage> deleteAll(@RequestBody List<CategoryDataDTO> categoryDataDTOList) {
        log.debug("REST request to deleteAll({}) CategoryDataCustomRest", categoryDataDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(categoryDataCustomService.delete(categoryDataDTOList)));
    }

    @PostMapping("/_save/category-data")
    public ResponseEntity<IResponseMessage> saveAll(@RequestBody List<CategoryDataDTO> categoryDataDTOList) {
        log.debug("REST request to saveAll({}) CategoryDataCustomRest", categoryDataDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(categoryDataCustomService.saveAll(categoryDataDTOList)));
    }


    /**
     * DuowngTora custom search
     */
    @PostMapping("/search_custom/category-data")
    public ResponseEntity<IResponseMessage> customSearch(@RequestBody CategoryDataDTO categoryDataDTO, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.categoryDataSearchService.simpleQuerySearch(categoryDataDTO, pageable);
        log.debug("CategoryDataCustomRest: customSearch(IBaseSearchDTO: {}): {}", categoryDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/v2/search_custom/category-data")
    public ResponseEntity<IResponseMessage> customSearchWithParam(@RequestBody CategoryDataDTO categoryDataDTO, @RequestParam(required = false) Map<String, Object> paramMaps, Pageable pageable) throws IllegalAccessException {
        ISearchResponseDTO result = this.categoryDataSearchService.simpleQuerySearchWithParam(categoryDataDTO, paramMaps, pageable);
        log.debug("CategoryDataCustomRest: customSearchWithParam(IBaseSearchDTO: {}): {}", categoryDataDTO, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
