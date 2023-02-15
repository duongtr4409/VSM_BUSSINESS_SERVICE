package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.CategoryData;
import com.vsm.business.repository.CategoryDataRepository;
import com.vsm.business.repository.search.CategoryDataSearchRepository;
import com.vsm.business.service.CategoryDataService;
import com.vsm.business.service.dto.CategoryDataDTO;
import com.vsm.business.service.mapper.CategoryDataMapper;
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
 * Service Implementation for managing {@link CategoryData}.
 */
@Service
@Transactional
public class CategoryDataServiceImpl implements CategoryDataService {

    private final Logger log = LoggerFactory.getLogger(CategoryDataServiceImpl.class);

    private final CategoryDataRepository categoryDataRepository;

    private final CategoryDataMapper categoryDataMapper;

    private final CategoryDataSearchRepository categoryDataSearchRepository;

    public CategoryDataServiceImpl(
        CategoryDataRepository categoryDataRepository,
        CategoryDataMapper categoryDataMapper,
        CategoryDataSearchRepository categoryDataSearchRepository
    ) {
        this.categoryDataRepository = categoryDataRepository;
        this.categoryDataMapper = categoryDataMapper;
        this.categoryDataSearchRepository = categoryDataSearchRepository;
    }

    @Override
    public CategoryDataDTO save(CategoryDataDTO categoryDataDTO) {
        log.debug("Request to save CategoryData : {}", categoryDataDTO);
        CategoryData categoryData = categoryDataMapper.toEntity(categoryDataDTO);
        categoryData = categoryDataRepository.save(categoryData);
        CategoryDataDTO result = categoryDataMapper.toDto(categoryData);
        try{
//            categoryDataSearchRepository.save(categoryData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<CategoryDataDTO> partialUpdate(CategoryDataDTO categoryDataDTO) {
        log.debug("Request to partially update CategoryData : {}", categoryDataDTO);

        return categoryDataRepository
            .findById(categoryDataDTO.getId())
            .map(existingCategoryData -> {
                categoryDataMapper.partialUpdate(existingCategoryData, categoryDataDTO);

                return existingCategoryData;
            })
            .map(categoryDataRepository::save)
            .map(savedCategoryData -> {
                categoryDataSearchRepository.save(savedCategoryData);

                return savedCategoryData;
            })
            .map(categoryDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryData");
        return categoryDataRepository.findAll(pageable).map(categoryDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDataDTO> findOne(Long id) {
        log.debug("Request to get CategoryData : {}", id);
        return categoryDataRepository.findById(id).map(categoryDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryData : {}", id);
        categoryDataRepository.deleteById(id);
        categoryDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoryData for query {}", query);
        return categoryDataSearchRepository.search(query, pageable).map(categoryDataMapper::toDto);
    }
}
