package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OfficialDispatchStatus;
import com.vsm.business.repository.OfficialDispatchStatusRepository;
import com.vsm.business.repository.search.OfficialDispatchStatusSearchRepository;
import com.vsm.business.service.OfficialDispatchStatusService;
import com.vsm.business.service.dto.OfficialDispatchStatusDTO;
import com.vsm.business.service.mapper.OfficialDispatchStatusMapper;
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
 * Service Implementation for managing {@link OfficialDispatchStatus}.
 */
@Service
@Transactional
public class OfficialDispatchStatusServiceImpl implements OfficialDispatchStatusService {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchStatusServiceImpl.class);

    private final OfficialDispatchStatusRepository officialDispatchStatusRepository;

    private final OfficialDispatchStatusMapper officialDispatchStatusMapper;

    private final OfficialDispatchStatusSearchRepository officialDispatchStatusSearchRepository;

    public OfficialDispatchStatusServiceImpl(
        OfficialDispatchStatusRepository officialDispatchStatusRepository,
        OfficialDispatchStatusMapper officialDispatchStatusMapper,
        OfficialDispatchStatusSearchRepository officialDispatchStatusSearchRepository
    ) {
        this.officialDispatchStatusRepository = officialDispatchStatusRepository;
        this.officialDispatchStatusMapper = officialDispatchStatusMapper;
        this.officialDispatchStatusSearchRepository = officialDispatchStatusSearchRepository;
    }

    @Override
    public OfficialDispatchStatusDTO save(OfficialDispatchStatusDTO officialDispatchStatusDTO) {
        log.debug("Request to save OfficialDispatchStatus : {}", officialDispatchStatusDTO);
        OfficialDispatchStatus officialDispatchStatus = officialDispatchStatusMapper.toEntity(officialDispatchStatusDTO);
        officialDispatchStatus = officialDispatchStatusRepository.save(officialDispatchStatus);
        OfficialDispatchStatusDTO result = officialDispatchStatusMapper.toDto(officialDispatchStatus);

        try{
//            officialDispatchStatusSearchRepository.save(officialDispatchStatus);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<OfficialDispatchStatusDTO> partialUpdate(OfficialDispatchStatusDTO officialDispatchStatusDTO) {
        log.debug("Request to partially update OfficialDispatchStatus : {}", officialDispatchStatusDTO);

        return officialDispatchStatusRepository
            .findById(officialDispatchStatusDTO.getId())
            .map(existingOfficialDispatchStatus -> {
                officialDispatchStatusMapper.partialUpdate(existingOfficialDispatchStatus, officialDispatchStatusDTO);

                return existingOfficialDispatchStatus;
            })
            .map(officialDispatchStatusRepository::save)
            .map(savedOfficialDispatchStatus -> {
                officialDispatchStatusSearchRepository.save(savedOfficialDispatchStatus);

                return savedOfficialDispatchStatus;
            })
            .map(officialDispatchStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfficialDispatchStatuses");
        return officialDispatchStatusRepository.findAll(pageable).map(officialDispatchStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficialDispatchStatusDTO> findOne(Long id) {
        log.debug("Request to get OfficialDispatchStatus : {}", id);
        return officialDispatchStatusRepository.findById(id).map(officialDispatchStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfficialDispatchStatus : {}", id);
        officialDispatchStatusRepository.deleteById(id);
        officialDispatchStatusSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfficialDispatchStatuses for query {}", query);
        return officialDispatchStatusSearchRepository.search(query, pageable).map(officialDispatchStatusMapper::toDto);
    }
}
