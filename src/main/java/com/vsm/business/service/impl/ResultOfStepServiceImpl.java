package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ResultOfStep;
import com.vsm.business.repository.ResultOfStepRepository;
import com.vsm.business.repository.search.ResultOfStepSearchRepository;
import com.vsm.business.service.ResultOfStepService;
import com.vsm.business.service.dto.ResultOfStepDTO;
import com.vsm.business.service.mapper.ResultOfStepMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResultOfStep}.
 */
@Service
@Transactional
public class ResultOfStepServiceImpl implements ResultOfStepService {

    private final Logger log = LoggerFactory.getLogger(ResultOfStepServiceImpl.class);

    private final ResultOfStepRepository resultOfStepRepository;

    private final ResultOfStepMapper resultOfStepMapper;

    private final ResultOfStepSearchRepository resultOfStepSearchRepository;

    public ResultOfStepServiceImpl(
        ResultOfStepRepository resultOfStepRepository,
        ResultOfStepMapper resultOfStepMapper,
        ResultOfStepSearchRepository resultOfStepSearchRepository
    ) {
        this.resultOfStepRepository = resultOfStepRepository;
        this.resultOfStepMapper = resultOfStepMapper;
        this.resultOfStepSearchRepository = resultOfStepSearchRepository;
    }

    @Override
    public ResultOfStepDTO save(ResultOfStepDTO resultOfStepDTO) {
        log.debug("Request to save ResultOfStep : {}", resultOfStepDTO);
        ResultOfStep resultOfStep = resultOfStepMapper.toEntity(resultOfStepDTO);
        resultOfStep = resultOfStepRepository.save(resultOfStep);
        ResultOfStepDTO result = resultOfStepMapper.toDto(resultOfStep);
        resultOfStepSearchRepository.save(resultOfStep);
        return result;
    }

    @Override
    public Optional<ResultOfStepDTO> partialUpdate(ResultOfStepDTO resultOfStepDTO) {
        log.debug("Request to partially update ResultOfStep : {}", resultOfStepDTO);

        return resultOfStepRepository
            .findById(resultOfStepDTO.getId())
            .map(existingResultOfStep -> {
                resultOfStepMapper.partialUpdate(existingResultOfStep, resultOfStepDTO);

                return existingResultOfStep;
            })
            .map(resultOfStepRepository::save)
            .map(savedResultOfStep -> {
                resultOfStepSearchRepository.save(savedResultOfStep);

                return savedResultOfStep;
            })
            .map(resultOfStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultOfStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResultOfSteps");
        return resultOfStepRepository.findAll(pageable).map(resultOfStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultOfStepDTO> findOne(Long id) {
        log.debug("Request to get ResultOfStep : {}", id);
        return resultOfStepRepository.findById(id).map(resultOfStepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultOfStep : {}", id);
        resultOfStepRepository.deleteById(id);
        resultOfStepSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultOfStepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ResultOfSteps for query {}", query);
        return resultOfStepSearchRepository.search(query, pageable).map(resultOfStepMapper::toDto);
    }
}
