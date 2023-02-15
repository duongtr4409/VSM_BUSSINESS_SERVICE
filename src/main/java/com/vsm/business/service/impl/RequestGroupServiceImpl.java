package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RequestGroup;
import com.vsm.business.repository.RequestGroupRepository;
import com.vsm.business.repository.search.RequestGroupSearchRepository;
import com.vsm.business.service.RequestGroupService;
import com.vsm.business.service.dto.RequestGroupDTO;
import com.vsm.business.service.mapper.RequestGroupMapper;
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
 * Service Implementation for managing {@link RequestGroup}.
 */
@Service
@Transactional
public class RequestGroupServiceImpl implements RequestGroupService {

    private final Logger log = LoggerFactory.getLogger(RequestGroupServiceImpl.class);

    private final RequestGroupRepository requestGroupRepository;

    private final RequestGroupMapper requestGroupMapper;

    private final RequestGroupSearchRepository requestGroupSearchRepository;

    public RequestGroupServiceImpl(
        RequestGroupRepository requestGroupRepository,
        RequestGroupMapper requestGroupMapper,
        RequestGroupSearchRepository requestGroupSearchRepository
    ) {
        this.requestGroupRepository = requestGroupRepository;
        this.requestGroupMapper = requestGroupMapper;
        this.requestGroupSearchRepository = requestGroupSearchRepository;
    }

    @Override
    public RequestGroupDTO save(RequestGroupDTO requestGroupDTO) {
        log.debug("Request to save RequestGroup : {}", requestGroupDTO);
        RequestGroup requestGroup = requestGroupMapper.toEntity(requestGroupDTO);
        requestGroup = requestGroupRepository.save(requestGroup);
        RequestGroupDTO result = requestGroupMapper.toDto(requestGroup);
        try{
//            requestGroupSearchRepository.save(requestGroup);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RequestGroupDTO> partialUpdate(RequestGroupDTO requestGroupDTO) {
        log.debug("Request to partially update RequestGroup : {}", requestGroupDTO);

        return requestGroupRepository
            .findById(requestGroupDTO.getId())
            .map(existingRequestGroup -> {
                requestGroupMapper.partialUpdate(existingRequestGroup, requestGroupDTO);

                return existingRequestGroup;
            })
            .map(requestGroupRepository::save)
            .map(savedRequestGroup -> {
                requestGroupSearchRepository.save(savedRequestGroup);

                return savedRequestGroup;
            })
            .map(requestGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestGroups");
        return requestGroupRepository.findAll(pageable).map(requestGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestGroupDTO> findOne(Long id) {
        log.debug("Request to get RequestGroup : {}", id);
        return requestGroupRepository.findById(id).map(requestGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestGroup : {}", id);
        requestGroupRepository.deleteById(id);
        requestGroupSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequestGroups for query {}", query);
        return requestGroupSearchRepository.search(query, pageable).map(requestGroupMapper::toDto);
    }
}
