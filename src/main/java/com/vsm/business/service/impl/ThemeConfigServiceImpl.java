package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ThemeConfig;
import com.vsm.business.repository.ThemeConfigRepository;
import com.vsm.business.repository.search.ThemeConfigSearchRepository;
import com.vsm.business.service.ThemeConfigService;
import com.vsm.business.service.dto.ThemeConfigDTO;
import com.vsm.business.service.mapper.ThemeConfigMapper;
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
 * Service Implementation for managing {@link ThemeConfig}.
 */
@Service
@Transactional
public class ThemeConfigServiceImpl implements ThemeConfigService {

    private final Logger log = LoggerFactory.getLogger(ThemeConfigServiceImpl.class);

    private final ThemeConfigRepository themeConfigRepository;

    private final ThemeConfigMapper themeConfigMapper;

    private final ThemeConfigSearchRepository themeConfigSearchRepository;

    public ThemeConfigServiceImpl(
        ThemeConfigRepository themeConfigRepository,
        ThemeConfigMapper themeConfigMapper,
        ThemeConfigSearchRepository themeConfigSearchRepository
    ) {
        this.themeConfigRepository = themeConfigRepository;
        this.themeConfigMapper = themeConfigMapper;
        this.themeConfigSearchRepository = themeConfigSearchRepository;
    }

    @Override
    public ThemeConfigDTO save(ThemeConfigDTO themeConfigDTO) {
        log.debug("Request to save ThemeConfig : {}", themeConfigDTO);
        ThemeConfig themeConfig = themeConfigMapper.toEntity(themeConfigDTO);
        themeConfig = themeConfigRepository.save(themeConfig);
        ThemeConfigDTO result = themeConfigMapper.toDto(themeConfig);
        try{
//            themeConfigSearchRepository.save(themeConfig);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ThemeConfigDTO> partialUpdate(ThemeConfigDTO themeConfigDTO) {
        log.debug("Request to partially update ThemeConfig : {}", themeConfigDTO);

        return themeConfigRepository
            .findById(themeConfigDTO.getId())
            .map(existingThemeConfig -> {
                themeConfigMapper.partialUpdate(existingThemeConfig, themeConfigDTO);

                return existingThemeConfig;
            })
            .map(themeConfigRepository::save)
            .map(savedThemeConfig -> {
                themeConfigSearchRepository.save(savedThemeConfig);

                return savedThemeConfig;
            })
            .map(themeConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeConfigs");
        return themeConfigRepository.findAll(pageable).map(themeConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeConfigDTO> findOne(Long id) {
        log.debug("Request to get ThemeConfig : {}", id);
        return themeConfigRepository.findById(id).map(themeConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThemeConfig : {}", id);
        themeConfigRepository.deleteById(id);
        themeConfigSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeConfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ThemeConfigs for query {}", query);
        return themeConfigSearchRepository.search(query, pageable).map(themeConfigMapper::toDto);
    }
}
