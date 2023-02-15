package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.StepData;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.search.StepDataSearchRepository;
import com.vsm.business.service.StepDataService;
import com.vsm.business.service.dto.StepDataDTO;
import com.vsm.business.service.mapper.StepDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StepData}.
 */
@Service
@Transactional
public class StepDataServiceImpl implements StepDataService {

    private final Logger log = LoggerFactory.getLogger(StepDataServiceImpl.class);

    private final StepDataRepository stepDataRepository;

    private final StepDataMapper stepDataMapper;

    private final StepDataSearchRepository stepDataSearchRepository;

    public StepDataServiceImpl(
        StepDataRepository stepDataRepository,
        StepDataMapper stepDataMapper,
        StepDataSearchRepository stepDataSearchRepository
    ) {
        this.stepDataRepository = stepDataRepository;
        this.stepDataMapper = stepDataMapper;
        this.stepDataSearchRepository = stepDataSearchRepository;
    }

    @Override
    public StepDataDTO save(StepDataDTO stepDataDTO) {
        log.debug("Request to save StepData : {}", stepDataDTO);
        StepData stepData = stepDataMapper.toEntity(stepDataDTO);
        stepData = stepDataRepository.save(stepData);
        StepDataDTO result = stepDataMapper.toDto(stepData);
        try{
//            stepDataSearchRepository.save(stepData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<StepDataDTO> partialUpdate(StepDataDTO stepDataDTO) {
        log.debug("Request to partially update StepData : {}", stepDataDTO);

        return stepDataRepository
            .findById(stepDataDTO.getId())
            .map(existingStepData -> {
                stepDataMapper.partialUpdate(existingStepData, stepDataDTO);

                return existingStepData;
            })
            .map(stepDataRepository::save)
            .map(savedStepData -> {
                stepDataSearchRepository.save(savedStepData);

                return savedStepData;
            })
            .map(stepDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StepData");
        return stepDataRepository.findAll(pageable).map(stepDataMapper::toDto);
    }

    public Page<StepDataDTO> findAllWithEagerRelationships(Pageable pageable) {
        return stepDataRepository.findAllWithEagerRelationships(pageable).map(stepDataMapper::toDto);
    }

    /**
     *  Get all the stepData where PreviousStep is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StepDataDTO> findAllWherePreviousStepIsNull() {
        log.debug("Request to get all stepData where PreviousStep is null");
        return StreamSupport
            .stream(stepDataRepository.findAll().spliterator(), false)
            .filter(stepData -> stepData.getPreviousStep() == null)
            .map(stepDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepDataDTO> findOne(Long id) {
        log.debug("Request to get StepData : {}", id);
        return stepDataRepository.findOneWithEagerRelationships(id).map(stepDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StepData : {}", id);
        stepDataRepository.deleteById(id);
        stepDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StepData for query {}", query);
        return stepDataSearchRepository.search(query, pageable).map(stepDataMapper::toDto);
    }
}
