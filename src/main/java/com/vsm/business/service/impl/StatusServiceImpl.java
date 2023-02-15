package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Status;
import com.vsm.business.repository.StatusRepository;
import com.vsm.business.repository.search.StatusSearchRepository;
import com.vsm.business.service.StatusService;
import com.vsm.business.service.dto.StatusDTO;
import com.vsm.business.service.mapper.StatusMapper;
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
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    private final StatusSearchRepository statusSearchRepository;

    public StatusServiceImpl(StatusRepository statusRepository, StatusMapper statusMapper, StatusSearchRepository statusSearchRepository) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
        this.statusSearchRepository = statusSearchRepository;
    }

    @Override
    public StatusDTO save(StatusDTO statusDTO) {
        log.debug("Request to save Status : {}", statusDTO);
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        StatusDTO result = statusMapper.toDto(status);
        try{
//            statusSearchRepository.save(status);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<StatusDTO> partialUpdate(StatusDTO statusDTO) {
        log.debug("Request to partially update Status : {}", statusDTO);

        return statusRepository
            .findById(statusDTO.getId())
            .map(existingStatus -> {
                statusMapper.partialUpdate(existingStatus, statusDTO);

                return existingStatus;
            })
            .map(statusRepository::save)
            .map(savedStatus -> {
                statusSearchRepository.save(savedStatus);

                return savedStatus;
            })
            .map(statusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll(pageable).map(statusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusDTO> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id).map(statusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
        statusSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Statuses for query {}", query);
        return statusSearchRepository.search(query, pageable).map(statusMapper::toDto);
    }
}
