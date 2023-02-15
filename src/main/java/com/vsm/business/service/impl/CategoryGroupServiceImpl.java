package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.repository.search.CategoryGroupSearchRepository;
import com.vsm.business.service.CategoryGroupService;
import com.vsm.business.service.dto.CategoryGroupDTO;
import com.vsm.business.service.mapper.CategoryGroupMapper;
import java.util.Optional;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoryGroup}.
 */
@Service
@Transactional
public class CategoryGroupServiceImpl implements CategoryGroupService {

    private final Logger log = LoggerFactory.getLogger(CategoryGroupServiceImpl.class);

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupMapper categoryGroupMapper;

    private final CategoryGroupSearchRepository categoryGroupSearchRepository;

    public CategoryGroupServiceImpl(
        CategoryGroupRepository categoryGroupRepository,
        CategoryGroupMapper categoryGroupMapper,
        CategoryGroupSearchRepository categoryGroupSearchRepository
    ) {
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryGroupMapper = categoryGroupMapper;
        this.categoryGroupSearchRepository = categoryGroupSearchRepository;
    }

    @Override
    public CategoryGroupDTO save(CategoryGroupDTO categoryGroupDTO) {
        log.debug("Request to save CategoryGroup : {}", categoryGroupDTO);
        CategoryGroup categoryGroup = categoryGroupMapper.toEntity(categoryGroupDTO);
        categoryGroup = categoryGroupRepository.save(categoryGroup);
        CategoryGroupDTO result = categoryGroupMapper.toDto(categoryGroup);
        try{
//            categoryGroupSearchRepository.save(categoryGroup);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<CategoryGroupDTO> partialUpdate(CategoryGroupDTO categoryGroupDTO) {
        log.debug("Request to partially update CategoryGroup : {}", categoryGroupDTO);

        return categoryGroupRepository
            .findById(categoryGroupDTO.getId())
            .map(existingCategoryGroup -> {
                categoryGroupMapper.partialUpdate(existingCategoryGroup, categoryGroupDTO);

                return existingCategoryGroup;
            })
            .map(categoryGroupRepository::save)
            .map(savedCategoryGroup -> {
                categoryGroupSearchRepository.save(savedCategoryGroup);

                return savedCategoryGroup;
            })
            .map(categoryGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryGroups");
        return categoryGroupRepository.findAll(pageable).map(categoryGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryGroupDTO> findOne(Long id) {
        log.debug("Request to get CategoryGroup : {}", id);
        return categoryGroupRepository.findById(id).map(categoryGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryGroup : {}", id);
        categoryGroupRepository.deleteById(id);
        categoryGroupSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoryGroups for query {}", query);
        return categoryGroupSearchRepository.search(query, pageable).map(categoryGroupMapper::toDto);
    }
}
