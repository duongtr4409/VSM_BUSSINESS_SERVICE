package com.vsm.business.service.custom;

import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.repository.search.CategoryGroupSearchRepository;
import com.vsm.business.service.dto.CategoryGroupDTO;
import com.vsm.business.service.mapper.CategoryGroupMapper;
import com.vsm.business.utils.ConditionUtils;
import org.elasticsearch.common.Strings;
import org.hibernate.exception.GenericJDBCException;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryGroupCustomService {
    private final Logger log = LoggerFactory.getLogger(CategoryGroupCustomService.class);

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupMapper categoryGroupMapper;

    private final CategoryGroupSearchRepository categoryGroupSearchRepository;

    @Value("${system.sync-sap.PARTNER_CODE:KHACHHANG}")
    public String CATEGORY_PARTNER_CODE;

    @Value("${system.sync-sap.PARTNER_NAME:Khách Hàng}")
    public String CATEGORY_PARTNER_NAME;

    @Autowired
    private ConditionUtils conditionUtils;

    public CategoryGroupCustomService(
        CategoryGroupRepository categoryGroupRepository,
        CategoryGroupMapper categoryGroupMapper,
        CategoryGroupSearchRepository categoryGroupSearchRepository
    ) {
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryGroupMapper = categoryGroupMapper;
        this.categoryGroupSearchRepository = categoryGroupSearchRepository;
    }

//    @CacheEvict(value = ["/_all/category-groups", "/_all-child/{id}/category-groups", "/_all_child_pageable/{id}/category-groups", "/_all_has_child/category-groups"], key = )
    public CategoryGroupDTO saveCustom(CategoryGroupDTO categoryGroupDTO) throws Exception {
        log.debug("Request to save CategoryGroup : {}", categoryGroupDTO);

            // check kiểm tra không cho phép tạo trùng dữ liệu (trùng tên trong cùng 1 folder)
        Boolean checkExit = categoryGroupRepository.findAllByNameAndParent(categoryGroupDTO.getName(), categoryGroupMapper.toEntity(categoryGroupDTO.getParent())).stream().anyMatch(ele -> {
            return !this.conditionUtils.checkTrueFalse(ele.getIsDelete()) && !ele.getId().equals(categoryGroupDTO.getId());
        });
        if(checkExit) throw new Exception("Duplicate Name");

        CategoryGroup categoryGroup = categoryGroupMapper.toEntity(categoryGroupDTO);
        categoryGroup = categoryGroupRepository.save(categoryGroup);
        CategoryGroupDTO result = categoryGroupMapper.toDto(categoryGroup);
        try {
            categoryGroupSearchRepository.save(categoryGroup);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){
            log.debug(e.getMessage());
        }

        clearCache();

        return result;

    }

    @Cacheable(value = "/_all/category-groups", key = "#ignoreField + \"\""/*, unless = "#result.followers < 12000"*/)
    public List<CategoryGroupDTO> findAll(boolean ignoreField) {
        log.debug("CategoryGroupCustomService findAll()");
        if(ignoreField){
            return categoryGroupRepository.findALlCustomIgnoreField().stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
        }else{
//        return categoryGroupRepository.findAll().stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
            return  categoryGroupRepository.findALlCustom().stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<CategoryGroupDTO> delete(List<CategoryGroupDTO> categoryGroupDTOList) {
        log.debug("CategoryGroupCustomService delete({})", categoryGroupDTOList);
        categoryGroupRepository.deleteAllById(categoryGroupDTOList.stream().map((categoryGroupDTO -> categoryGroupDTO.getId())).collect(Collectors.toList()));
        categoryGroupSearchRepository.deleteAllById(categoryGroupDTOList.stream().map((categoryGroupDTO -> categoryGroupDTO.getId())).collect(Collectors.toList()));

        clearCache();

        return categoryGroupRepository.findAll().stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
    }

    @Cacheable(value = "/_all-child/{id}/category-groups", key = "#id + \"\""/*, unless = "#result.followers < 12000"*/)
    public List<CategoryGroupDTO> getAllChild(Long id){
//        List<CategoryGroupDTO> result = this.categoryGroupRepository.findChild(id).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
//        log.info("CategoryGroupCustomService getAllChild({}): {}", id, result);
//        return result;
        List<CategoryGroupDTO> result = this.categoryGroupRepository.findAllByParentId(id).stream().filter(ele ->
                !conditionUtils.checkDelete(ele.getIsDelete())
            ).map(categoryGroupMapper::toDto).collect(Collectors.toList());
        log.debug("CategoryGroupCustomService getAllChild({}): {}", id, result);
        return result;
    }


    @Cacheable(value = "/_all-child/{id}/category-groups", key = "#id + \"\" + #name + \"\" + #pageable"/*, unless = "#result.followers < 12000"*/)
    public List<CategoryGroupDTO> getAllChildByName(Long id, String name, Pageable pageable){
        List<CategoryGroupDTO> result = new ArrayList<>();
        if(!Strings.isNullOrEmpty(name)){
            String nameSearch = "%" + name.toUpperCase() + "%";
            if(pageable.getPageSize() > 0 && pageable.getPageNumber() >= 0){
                int limit = pageable.getPageSize();
                int offset = pageable.getPageSize() * pageable.getPageNumber();
                result = this.categoryGroupRepository.getAllByParentIdAndName(id, nameSearch, limit, offset).stream().filter(ele ->
                    !conditionUtils.checkDelete(ele.getIsDelete())
                ).map(categoryGroupMapper::toDto).collect(Collectors.toList());
            }else{
                result = this.categoryGroupRepository.getAllByParentIdAndName(id, nameSearch).stream().filter(ele ->
                    !conditionUtils.checkDelete(ele.getIsDelete())
                ).map(categoryGroupMapper::toDto).collect(Collectors.toList());
            }
        }else{
            if(pageable.getPageSize() > 0 && pageable.getPageNumber() >= 0) {
                result = this.categoryGroupRepository.findAllByParentId(id, pageable).stream().filter(ele ->
                    !conditionUtils.checkDelete(ele.getIsDelete())
                ).map(categoryGroupMapper::toDto).collect(Collectors.toList());
            }else{
                result = this.categoryGroupRepository.findAllByParentId(id).stream().filter(ele ->
                    !conditionUtils.checkDelete(ele.getIsDelete())
                ).map(categoryGroupMapper::toDto).collect(Collectors.toList());
            }
        }
        log.debug("CategoryGroupCustomService getAllChild({}): {}", id, result);
        return result;
    }

    public List<CategoryGroupDTO> getAllBpByName(String name, Pageable pageable){
        CategoryGroup categoryGroup = this.categoryGroupRepository.findAllByNameAndParent(this.CATEGORY_PARTNER_NAME, null).get(0);
        List<CategoryGroupDTO> result = this.getAllChildByName(categoryGroup.getId(), name, pageable);
        log.debug("CategoryGroupCustomService getAllBpByName({}): {}", name, result);
        return result;
    }

    @Cacheable(value = "/_all_child_pageable/{id}/category-groups", key = "#id + \"\" + #pageable + \"\""/*, unless = "#result.followers < 12000"*/)
    public List<CategoryGroupDTO> getAllChildPageable(Long id, Pageable pageable){
//        List<CategoryGroupDTO> result = this.categoryGroupRepository.findChild(id).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
//        log.info("CategoryGroupCustomService getAllChild({}): {}", id, result);
//        return result;
        //List<CategoryGroupDTO> result = this.categoryGroupRepository.findAllByParentId(id, pageable).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        List<CategoryGroupDTO> result = this.categoryGroupRepository.getAllByParentId(id, offset, limit).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
        log.debug("CategoryGroupCustomService getAllChild({}): {}", id, result);
        return result;
    }

    public Long countAllChildPageable(Long id){
//        List<CategoryGroupDTO> result = this.categoryGroupRepository.findChild(id).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
//        log.info("CategoryGroupCustomService getAllChild({}): {}", id, result);
//        return result;
        //List<CategoryGroupDTO> result = this.categoryGroupRepository.findAllByParentId(id, pageable).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList())
        Long result = this.categoryGroupRepository.countAllByParentId(id);
        log.debug("CategoryGroupCustomService getAllChild({}): {}", id, result);
        return result;
    }

    @Cacheable(value = "/_all_has_child/category-groups"/*, unless = "#result.followers < 12000"*/)
    public List<CategoryGroupDTO> getAllCategoryGroupHasChild(){
//        List<CategoryGroupDTO> result = this.categoryGroupRepository.findAllByChildrenIsNotNull().stream().map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
        List<CategoryGroupDTO> result = this.categoryGroupRepository.getAllChildrenIsNotNull().stream().filter(ele ->
                !conditionUtils.checkDelete(ele.getIsDelete())
            ).map(ele -> categoryGroupMapper.toDto(ele)).collect(Collectors.toList());
        log.debug("CategoryGroupCustomService getAllCategoryGroupHasChild(): {}", result);
        return result;
    }

    public List<CategoryGroupDTO> saveAll(List<CategoryGroupDTO> categoryGroupDTOList){
        List<CategoryGroupDTO> result = new ArrayList<>();
//        List<CategoryGroupDTO> result = categoryGroupRepository.saveAll(categoryGroupDTOList.stream().map(categoryGroupMapper::toEntity).collect(Collectors.toList())).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList());
//        categoryGroupDTOList.forEach(ele -> {
        for(CategoryGroupDTO ele : categoryGroupDTOList) {
            List<CategoryGroup> listCategoryGroupsTemp = categoryGroupRepository.findChild(ele.getId()).stream().map(ele1 -> {
                ele1.setIsDelete(true);
                return ele1;
            }).collect(Collectors.toList());
            if (listCategoryGroupsTemp != null && !listCategoryGroupsTemp.isEmpty())
                result.addAll(categoryGroupRepository.saveAll(listCategoryGroupsTemp).stream().map(categoryGroupMapper::toDto).collect(Collectors.toList()));
        }
//        });

        clearCache();

        log.debug("CategoryGroupCustomService saveAll({}) {}", categoryGroupDTOList, result);
        return result;
    }


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JCacheManagerCustomizer jCacheManagerCustomizer;

    public void clearCache(){
        try {
            cacheManager.getCache("/_all/category-groups").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
        try {
            cacheManager.getCache("/_all-child/{id}/category-groups").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
        try {
            cacheManager.getCache("/_all_child_pageable/{id}/category-groups").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
        try {
            cacheManager.getCache("/_all_has_child/category-groups").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
        try {
            cacheManager.getCache("/_all_not_has_parent/category-groups").clear();
        }catch (Exception e){
            log.error("{}", e);
        }
    }
}
