package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RequestType;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.search.RequestTypeSearchRepository;
import com.vsm.business.service.RequestTypeService;
import com.vsm.business.service.dto.RequestTypeDTO;
import com.vsm.business.service.mapper.RequestTypeMapper;
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
 * Service Implementation for managing {@link RequestType}.
 */
@Service
@Transactional
public class RequestTypeServiceImpl implements RequestTypeService {

    private final Logger log = LoggerFactory.getLogger(RequestTypeServiceImpl.class);

    private final RequestTypeRepository requestTypeRepository;

    private final RequestTypeMapper requestTypeMapper;

    private final RequestTypeSearchRepository requestTypeSearchRepository;

    public RequestTypeServiceImpl(
        RequestTypeRepository requestTypeRepository,
        RequestTypeMapper requestTypeMapper,
        RequestTypeSearchRepository requestTypeSearchRepository
    ) {
        this.requestTypeRepository = requestTypeRepository;
        this.requestTypeMapper = requestTypeMapper;
        this.requestTypeSearchRepository = requestTypeSearchRepository;
    }

    @Override
    public RequestTypeDTO save(RequestTypeDTO requestTypeDTO) {
        log.debug("Request to save RequestType : {}", requestTypeDTO);
        RequestType requestType = requestTypeMapper.toEntity(requestTypeDTO);
        requestType = requestTypeRepository.save(requestType);
        RequestTypeDTO result = requestTypeMapper.toDto(requestType);
        try{
//            requestTypeSearchRepository.save(requestType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RequestTypeDTO> partialUpdate(RequestTypeDTO requestTypeDTO) {
        log.debug("Request to partially update RequestType : {}", requestTypeDTO);

        return requestTypeRepository
            .findById(requestTypeDTO.getId())
            .map(existingRequestType -> {
                requestTypeMapper.partialUpdate(existingRequestType, requestTypeDTO);

                return existingRequestType;
            })
            .map(requestTypeRepository::save)
            .map(savedRequestType -> {
                requestTypeSearchRepository.save(savedRequestType);

                return savedRequestType;
            })
            .map(requestTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestTypes");
        return requestTypeRepository.findAll(pageable).map(requestTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestTypeDTO> findOne(Long id) {
        log.debug("Request to get RequestType : {}", id);
        return requestTypeRepository.findById(id).map(requestTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestType : {}", id);
        requestTypeRepository.deleteById(id);
        requestTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequestTypes for query {}", query);
        return requestTypeSearchRepository.search(query, pageable).map(requestTypeMapper::toDto);
    }
}
