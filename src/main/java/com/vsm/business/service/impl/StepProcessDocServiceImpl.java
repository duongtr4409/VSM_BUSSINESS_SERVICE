package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.StepProcessDoc;
import com.vsm.business.repository.StepProcessDocRepository;
import com.vsm.business.repository.search.StepProcessDocSearchRepository;
import com.vsm.business.service.StepProcessDocService;
import com.vsm.business.service.dto.StepProcessDocDTO;
import com.vsm.business.service.mapper.StepProcessDocMapper;
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
 * Service Implementation for managing {@link StepProcessDoc}.
 */
@Service
@Transactional
public class StepProcessDocServiceImpl implements StepProcessDocService {

    private final Logger log = LoggerFactory.getLogger(StepProcessDocServiceImpl.class);

    private final StepProcessDocRepository stepProcessDocRepository;

    private final StepProcessDocMapper stepProcessDocMapper;

    private final StepProcessDocSearchRepository stepProcessDocSearchRepository;

    public StepProcessDocServiceImpl(
        StepProcessDocRepository stepProcessDocRepository,
        StepProcessDocMapper stepProcessDocMapper,
        StepProcessDocSearchRepository stepProcessDocSearchRepository
    ) {
        this.stepProcessDocRepository = stepProcessDocRepository;
        this.stepProcessDocMapper = stepProcessDocMapper;
        this.stepProcessDocSearchRepository = stepProcessDocSearchRepository;
    }

    @Override
    public StepProcessDocDTO save(StepProcessDocDTO stepProcessDocDTO) {
        log.debug("Request to save StepProcessDoc : {}", stepProcessDocDTO);
        StepProcessDoc stepProcessDoc = stepProcessDocMapper.toEntity(stepProcessDocDTO);
        stepProcessDoc = stepProcessDocRepository.save(stepProcessDoc);
        StepProcessDocDTO result = stepProcessDocMapper.toDto(stepProcessDoc);
        try{
//            stepProcessDocSearchRepository.save(stepProcessDoc);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<StepProcessDocDTO> partialUpdate(StepProcessDocDTO stepProcessDocDTO) {
        log.debug("Request to partially update StepProcessDoc : {}", stepProcessDocDTO);

        return stepProcessDocRepository
            .findById(stepProcessDocDTO.getId())
            .map(existingStepProcessDoc -> {
                stepProcessDocMapper.partialUpdate(existingStepProcessDoc, stepProcessDocDTO);

                return existingStepProcessDoc;
            })
            .map(stepProcessDocRepository::save)
            .map(savedStepProcessDoc -> {
                stepProcessDocSearchRepository.save(savedStepProcessDoc);

                return savedStepProcessDoc;
            })
            .map(stepProcessDocMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepProcessDocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StepProcessDocs");
        return stepProcessDocRepository.findAll(pageable).map(stepProcessDocMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepProcessDocDTO> findOne(Long id) {
        log.debug("Request to get StepProcessDoc : {}", id);
        return stepProcessDocRepository.findById(id).map(stepProcessDocMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StepProcessDoc : {}", id);
        stepProcessDocRepository.deleteById(id);
        stepProcessDocSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepProcessDocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StepProcessDocs for query {}", query);
        return stepProcessDocSearchRepository.search(query, pageable).map(stepProcessDocMapper::toDto);
    }
}
