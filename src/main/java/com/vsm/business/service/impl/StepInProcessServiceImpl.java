package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.StepInProcess;
import com.vsm.business.repository.StepInProcessRepository;
import com.vsm.business.repository.search.StepInProcessSearchRepository;
import com.vsm.business.service.StepInProcessService;
import com.vsm.business.service.dto.StepInProcessDTO;
import com.vsm.business.service.mapper.StepInProcessMapper;
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
 * Service Implementation for managing {@link StepInProcess}.
 */
@Service
@Transactional
public class StepInProcessServiceImpl implements StepInProcessService {

    private final Logger log = LoggerFactory.getLogger(StepInProcessServiceImpl.class);

    private final StepInProcessRepository stepInProcessRepository;

    private final StepInProcessMapper stepInProcessMapper;

    private final StepInProcessSearchRepository stepInProcessSearchRepository;

    public StepInProcessServiceImpl(
        StepInProcessRepository stepInProcessRepository,
        StepInProcessMapper stepInProcessMapper,
        StepInProcessSearchRepository stepInProcessSearchRepository
    ) {
        this.stepInProcessRepository = stepInProcessRepository;
        this.stepInProcessMapper = stepInProcessMapper;
        this.stepInProcessSearchRepository = stepInProcessSearchRepository;
    }

    @Override
    public StepInProcessDTO save(StepInProcessDTO stepInProcessDTO) {
        log.debug("Request to save StepInProcess : {}", stepInProcessDTO);
        StepInProcess stepInProcess = stepInProcessMapper.toEntity(stepInProcessDTO);
        stepInProcess = stepInProcessRepository.save(stepInProcess);
        StepInProcessDTO result = stepInProcessMapper.toDto(stepInProcess);
        try{
//            stepInProcessSearchRepository.save(stepInProcess);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<StepInProcessDTO> partialUpdate(StepInProcessDTO stepInProcessDTO) {
        log.debug("Request to partially update StepInProcess : {}", stepInProcessDTO);

        return stepInProcessRepository
            .findById(stepInProcessDTO.getId())
            .map(existingStepInProcess -> {
                stepInProcessMapper.partialUpdate(existingStepInProcess, stepInProcessDTO);

                return existingStepInProcess;
            })
            .map(stepInProcessRepository::save)
            .map(savedStepInProcess -> {
                stepInProcessSearchRepository.save(savedStepInProcess);

                return savedStepInProcess;
            })
            .map(stepInProcessMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepInProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StepInProcesses");
        return stepInProcessRepository.findAll(pageable).map(stepInProcessMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepInProcessDTO> findOne(Long id) {
        log.debug("Request to get StepInProcess : {}", id);
        return stepInProcessRepository.findById(id).map(stepInProcessMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StepInProcess : {}", id);
        stepInProcessRepository.deleteById(id);
        stepInProcessSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepInProcessDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StepInProcesses for query {}", query);
        return stepInProcessSearchRepository.search(query, pageable).map(stepInProcessMapper::toDto);
    }
}
