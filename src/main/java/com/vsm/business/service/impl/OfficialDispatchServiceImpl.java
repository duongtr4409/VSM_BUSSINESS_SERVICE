package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OfficialDispatch;
import com.vsm.business.repository.OfficialDispatchRepository;
import com.vsm.business.repository.search.OfficialDispatchSearchRepository;
import com.vsm.business.service.OfficialDispatchService;
import com.vsm.business.service.dto.OfficialDispatchDTO;
import com.vsm.business.service.mapper.OfficialDispatchMapper;
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
 * Service Implementation for managing {@link OfficialDispatch}.
 */
@Service
@Transactional
public class OfficialDispatchServiceImpl implements OfficialDispatchService {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchServiceImpl.class);

    private final OfficialDispatchRepository officialDispatchRepository;

    private final OfficialDispatchMapper officialDispatchMapper;

    private final OfficialDispatchSearchRepository officialDispatchSearchRepository;

    public OfficialDispatchServiceImpl(
        OfficialDispatchRepository officialDispatchRepository,
        OfficialDispatchMapper officialDispatchMapper,
        OfficialDispatchSearchRepository officialDispatchSearchRepository
    ) {
        this.officialDispatchRepository = officialDispatchRepository;
        this.officialDispatchMapper = officialDispatchMapper;
        this.officialDispatchSearchRepository = officialDispatchSearchRepository;
    }

    @Override
    public OfficialDispatchDTO save(OfficialDispatchDTO officialDispatchDTO) {
        log.debug("Request to save OfficialDispatch : {}", officialDispatchDTO);
        OfficialDispatch officialDispatch = officialDispatchMapper.toEntity(officialDispatchDTO);
        officialDispatch = officialDispatchRepository.save(officialDispatch);
        OfficialDispatchDTO result = officialDispatchMapper.toDto(officialDispatch);

        try{
//            officialDispatchSearchRepository.save(officialDispatch);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<OfficialDispatchDTO> partialUpdate(OfficialDispatchDTO officialDispatchDTO) {
        log.debug("Request to partially update OfficialDispatch : {}", officialDispatchDTO);

        return officialDispatchRepository
            .findById(officialDispatchDTO.getId())
            .map(existingOfficialDispatch -> {
                officialDispatchMapper.partialUpdate(existingOfficialDispatch, officialDispatchDTO);

                return existingOfficialDispatch;
            })
            .map(officialDispatchRepository::save)
            .map(savedOfficialDispatch -> {
                officialDispatchSearchRepository.save(savedOfficialDispatch);

                return savedOfficialDispatch;
            })
            .map(officialDispatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfficialDispatches");
        return officialDispatchRepository.findAll(pageable).map(officialDispatchMapper::toDto);
    }

    public Page<OfficialDispatchDTO> findAllWithEagerRelationships(Pageable pageable) {
        return officialDispatchRepository.findAllWithEagerRelationships(pageable).map(officialDispatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficialDispatchDTO> findOne(Long id) {
        log.debug("Request to get OfficialDispatch : {}", id);
        return officialDispatchRepository.findOneWithEagerRelationships(id).map(officialDispatchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfficialDispatch : {}", id);
        officialDispatchRepository.deleteById(id);
        officialDispatchSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfficialDispatches for query {}", query);
        return officialDispatchSearchRepository.search(query, pageable).map(officialDispatchMapper::toDto);
    }
}
