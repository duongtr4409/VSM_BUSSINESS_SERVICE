package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.PriorityLevel;
import com.vsm.business.repository.PriorityLevelRepository;
import com.vsm.business.repository.search.PriorityLevelSearchRepository;
import com.vsm.business.service.PriorityLevelService;
import com.vsm.business.service.dto.PriorityLevelDTO;
import com.vsm.business.service.mapper.PriorityLevelMapper;
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
 * Service Implementation for managing {@link PriorityLevel}.
 */
@Service
@Transactional
public class PriorityLevelServiceImpl implements PriorityLevelService {

    private final Logger log = LoggerFactory.getLogger(PriorityLevelServiceImpl.class);

    private final PriorityLevelRepository priorityLevelRepository;

    private final PriorityLevelMapper priorityLevelMapper;

    private final PriorityLevelSearchRepository priorityLevelSearchRepository;

    public PriorityLevelServiceImpl(
        PriorityLevelRepository priorityLevelRepository,
        PriorityLevelMapper priorityLevelMapper,
        PriorityLevelSearchRepository priorityLevelSearchRepository
    ) {
        this.priorityLevelRepository = priorityLevelRepository;
        this.priorityLevelMapper = priorityLevelMapper;
        this.priorityLevelSearchRepository = priorityLevelSearchRepository;
    }

    @Override
    public PriorityLevelDTO save(PriorityLevelDTO priorityLevelDTO) {
        log.debug("Request to save PriorityLevel : {}", priorityLevelDTO);
        PriorityLevel priorityLevel = priorityLevelMapper.toEntity(priorityLevelDTO);
        priorityLevel = priorityLevelRepository.save(priorityLevel);
        PriorityLevelDTO result = priorityLevelMapper.toDto(priorityLevel);
        try{
//            priorityLevelSearchRepository.save(priorityLevel);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<PriorityLevelDTO> partialUpdate(PriorityLevelDTO priorityLevelDTO) {
        log.debug("Request to partially update PriorityLevel : {}", priorityLevelDTO);

        return priorityLevelRepository
            .findById(priorityLevelDTO.getId())
            .map(existingPriorityLevel -> {
                priorityLevelMapper.partialUpdate(existingPriorityLevel, priorityLevelDTO);

                return existingPriorityLevel;
            })
            .map(priorityLevelRepository::save)
            .map(savedPriorityLevel -> {
                priorityLevelSearchRepository.save(savedPriorityLevel);

                return savedPriorityLevel;
            })
            .map(priorityLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriorityLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriorityLevels");
        return priorityLevelRepository.findAll(pageable).map(priorityLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PriorityLevelDTO> findOne(Long id) {
        log.debug("Request to get PriorityLevel : {}", id);
        return priorityLevelRepository.findById(id).map(priorityLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriorityLevel : {}", id);
        priorityLevelRepository.deleteById(id);
        priorityLevelSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriorityLevelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PriorityLevels for query {}", query);
        return priorityLevelSearchRepository.search(query, pageable).map(priorityLevelMapper::toDto);
    }
}
