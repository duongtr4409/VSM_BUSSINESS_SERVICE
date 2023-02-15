package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Pattern;
import com.vsm.business.repository.PatternRepository;
import com.vsm.business.repository.search.PatternSearchRepository;
import com.vsm.business.service.PatternService;
import com.vsm.business.service.dto.PatternDTO;
import com.vsm.business.service.mapper.PatternMapper;
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
 * Service Implementation for managing {@link Pattern}.
 */
@Service
@Transactional
public class PatternServiceImpl implements PatternService {

    private final Logger log = LoggerFactory.getLogger(PatternServiceImpl.class);

    private final PatternRepository patternRepository;

    private final PatternMapper patternMapper;

    private final PatternSearchRepository patternSearchRepository;

    public PatternServiceImpl(
        PatternRepository patternRepository,
        PatternMapper patternMapper,
        PatternSearchRepository patternSearchRepository
    ) {
        this.patternRepository = patternRepository;
        this.patternMapper = patternMapper;
        this.patternSearchRepository = patternSearchRepository;
    }

    @Override
    public PatternDTO save(PatternDTO patternDTO) {
        log.debug("Request to save Pattern : {}", patternDTO);
        Pattern pattern = patternMapper.toEntity(patternDTO);
        pattern = patternRepository.save(pattern);
        PatternDTO result = patternMapper.toDto(pattern);
        try{
//            patternSearchRepository.save(pattern);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<PatternDTO> partialUpdate(PatternDTO patternDTO) {
        log.debug("Request to partially update Pattern : {}", patternDTO);

        return patternRepository
            .findById(patternDTO.getId())
            .map(existingPattern -> {
                patternMapper.partialUpdate(existingPattern, patternDTO);

                return existingPattern;
            })
            .map(patternRepository::save)
            .map(savedPattern -> {
                patternSearchRepository.save(savedPattern);

                return savedPattern;
            })
            .map(patternMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatternDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patterns");
        return patternRepository.findAll(pageable).map(patternMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatternDTO> findOne(Long id) {
        log.debug("Request to get Pattern : {}", id);
        return patternRepository.findById(id).map(patternMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pattern : {}", id);
        patternRepository.deleteById(id);
        patternSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatternDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Patterns for query {}", query);
        return patternSearchRepository.search(query, pageable).map(patternMapper::toDto);
    }
}
