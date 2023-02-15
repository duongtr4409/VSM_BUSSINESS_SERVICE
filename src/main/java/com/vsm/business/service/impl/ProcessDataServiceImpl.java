package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.repository.ProcessDataRepository;
import com.vsm.business.repository.search.ProcessDataSearchRepository;
import com.vsm.business.service.ProcessDataService;
import com.vsm.business.service.dto.ProcessDataDTO;
import com.vsm.business.service.mapper.ProcessDataMapper;
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
 * Service Implementation for managing {@link ProcessData}.
 */
@Service
@Transactional
public class ProcessDataServiceImpl implements ProcessDataService {

    private final Logger log = LoggerFactory.getLogger(ProcessDataServiceImpl.class);

    private final ProcessDataRepository processDataRepository;

    private final ProcessDataMapper processDataMapper;

    private final ProcessDataSearchRepository processDataSearchRepository;

    public ProcessDataServiceImpl(
        ProcessDataRepository processDataRepository,
        ProcessDataMapper processDataMapper,
        ProcessDataSearchRepository processDataSearchRepository
    ) {
        this.processDataRepository = processDataRepository;
        this.processDataMapper = processDataMapper;
        this.processDataSearchRepository = processDataSearchRepository;
    }

    @Override
    public ProcessDataDTO save(ProcessDataDTO processDataDTO) {
        log.debug("Request to save ProcessData : {}", processDataDTO);
        ProcessData processData = processDataMapper.toEntity(processDataDTO);
        processData = processDataRepository.save(processData);
        ProcessDataDTO result = processDataMapper.toDto(processData);
        try{
//            processDataSearchRepository.save(processData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<ProcessDataDTO> partialUpdate(ProcessDataDTO processDataDTO) {
        log.debug("Request to partially update ProcessData : {}", processDataDTO);

        return processDataRepository
            .findById(processDataDTO.getId())
            .map(existingProcessData -> {
                processDataMapper.partialUpdate(existingProcessData, processDataDTO);

                return existingProcessData;
            })
            .map(processDataRepository::save)
            .map(savedProcessData -> {
                processDataSearchRepository.save(savedProcessData);

                return savedProcessData;
            })
            .map(processDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessData");
        return processDataRepository.findAll(pageable).map(processDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessDataDTO> findOne(Long id) {
        log.debug("Request to get ProcessData : {}", id);
        return processDataRepository.findById(id).map(processDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessData : {}", id);
        processDataRepository.deleteById(id);
        processDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProcessData for query {}", query);
        return processDataSearchRepository.search(query, pageable).map(processDataMapper::toDto);
    }
}
