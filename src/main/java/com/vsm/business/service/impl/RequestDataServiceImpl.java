package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.search.RequestDataSearchRepository;
import com.vsm.business.service.RequestDataService;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.RequestDataMapper;

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
 * Service Implementation for managing {@link RequestData}.
 */
@Service
@Transactional
public class RequestDataServiceImpl implements RequestDataService {

    private final Logger log = LoggerFactory.getLogger(RequestDataServiceImpl.class);

    private final RequestDataRepository requestDataRepository;

    private final RequestDataMapper requestDataMapper;

    private final RequestDataSearchRepository requestDataSearchRepository;

    public RequestDataServiceImpl(
        RequestDataRepository requestDataRepository,
        RequestDataMapper requestDataMapper,
        RequestDataSearchRepository requestDataSearchRepository
    ) {
        this.requestDataRepository = requestDataRepository;
        this.requestDataMapper = requestDataMapper;
        this.requestDataSearchRepository = requestDataSearchRepository;
    }

    @Override
    public RequestDataDTO save(RequestDataDTO requestDataDTO) {
        log.debug("Request to save RequestData : {}", requestDataDTO);
        RequestData requestData = requestDataMapper.toEntity(requestDataDTO);
        requestData = requestDataRepository.save(requestData);
        RequestDataDTO result = requestDataMapper.toDto(requestData);
        try{
            requestDataSearchRepository.save(requestData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RequestDataDTO> partialUpdate(RequestDataDTO requestDataDTO) {
        log.debug("Request to partially update RequestData : {}", requestDataDTO);

        return requestDataRepository
            .findById(requestDataDTO.getId())
            .map(existingRequestData -> {
                requestDataMapper.partialUpdate(existingRequestData, requestDataDTO);

                return existingRequestData;
            })
            .map(requestDataRepository::save)
            .map(savedRequestData -> {
                requestDataSearchRepository.save(savedRequestData);

                return savedRequestData;
            })
            .map(requestDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestData");
        return requestDataRepository.findAll(pageable).map(requestDataMapper::toDto);
    }

    public Page<RequestDataDTO> findAllWithEagerRelationships(Pageable pageable) {
        return requestDataRepository.findAllWithEagerRelationships(pageable).map(requestDataMapper::toDto);
    }

//    /**
//     *  Get all the requestData where ManageStampInfo is {@code null}.
//     *  @return the list of entities.
//     */
//    @Transactional(readOnly = true)
//    public List<RequestDataDTO> findAllWhereManageStampInfoIsNull() {
//        log.debug("Request to get all requestData where ManageStampInfo is null");
//        return StreamSupport
//            .stream(requestDataRepository.findAll().spliterator(), false)
//            .filter(requestData -> requestData.getManageStampInfo() == null)
//            .map(requestDataMapper::toDto)
//            .collect(Collectors.toCollection(LinkedList::new));
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestDataDTO> findOne(Long id) {
        log.debug("Request to get RequestData : {}", id);
        return requestDataRepository.findOneWithEagerRelationships(id).map(requestDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestData : {}", id);
        requestDataRepository.deleteById(id);
        requestDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequestData for query {}", query);
        return requestDataSearchRepository.search(query, pageable).map(requestDataMapper::toDto);
    }
}
