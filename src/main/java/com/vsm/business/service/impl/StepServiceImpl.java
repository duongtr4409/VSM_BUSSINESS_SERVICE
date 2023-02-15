package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Step;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.StepService;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.StepMapper;
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
 * Service Implementation for managing {@link Step}.
 */
@Service
@Transactional
public class StepServiceImpl implements StepService {

    private final Logger log = LoggerFactory.getLogger(StepServiceImpl.class);

    private final StepRepository stepRepository;

    private final StepMapper stepMapper;

    private final StepSearchRepository stepSearchRepository;

    public StepServiceImpl(StepRepository stepRepository, StepMapper stepMapper, StepSearchRepository stepSearchRepository) {
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
        this.stepSearchRepository = stepSearchRepository;
    }

    @Override
    public StepDTO save(StepDTO stepDTO) {
        log.debug("Request to save Step : {}", stepDTO);
        Step step = stepMapper.toEntity(stepDTO);
        step = stepRepository.save(step);
        StepDTO result = stepMapper.toDto(step);
        try{
//            stepSearchRepository.save(step);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<StepDTO> partialUpdate(StepDTO stepDTO) {
        log.debug("Request to partially update Step : {}", stepDTO);

        return stepRepository
            .findById(stepDTO.getId())
            .map(existingStep -> {
                stepMapper.partialUpdate(existingStep, stepDTO);

                return existingStep;
            })
            .map(stepRepository::save)
            .map(savedStep -> {
                stepSearchRepository.save(savedStep);

                return savedStep;
            })
            .map(stepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Steps");
        return stepRepository.findAll(pageable).map(stepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepDTO> findOne(Long id) {
        log.debug("Request to get Step : {}", id);
        return stepRepository.findById(id).map(stepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Step : {}", id);
        stepRepository.deleteById(id);
        stepSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Steps for query {}", query);
        return stepSearchRepository.search(query, pageable).map(stepMapper::toDto);
    }
}
