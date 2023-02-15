package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Request;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.search.RequestSearchRepository;
import com.vsm.business.service.RequestService;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.mapper.RequestMapper;
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
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final RequestSearchRepository requestSearchRepository;

    public RequestServiceImpl(
        RequestRepository requestRepository,
        RequestMapper requestMapper,
        RequestSearchRepository requestSearchRepository
    ) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.requestSearchRepository = requestSearchRepository;
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        RequestDTO result = requestMapper.toDto(request);
        try{
//            requestSearchRepository.save(request);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(existingRequest -> {
                requestMapper.partialUpdate(existingRequest, requestDTO);

                return existingRequest;
            })
            .map(requestRepository::save)
            .map(savedRequest -> {
                requestSearchRepository.save(savedRequest);

                return savedRequest;
            })
            .map(requestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable).map(requestMapper::toDto);
    }

    public Page<RequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return requestRepository.findAllWithEagerRelationships(pageable).map(requestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestDTO> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOneWithEagerRelationships(id).map(requestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
        requestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requests for query {}", query);
        return requestSearchRepository.search(query, pageable).map(requestMapper::toDto);
    }
}
