package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ProcessInfo;
import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.repository.search.ProcessInfoSearchRepository;
import com.vsm.business.service.ProcessInfoService;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.service.mapper.ProcessInfoMapper;
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
 * Service Implementation for managing {@link ProcessInfo}.
 */
@Service
@Transactional
public class ProcessInfoServiceImpl implements ProcessInfoService {

    private final Logger log = LoggerFactory.getLogger(ProcessInfoServiceImpl.class);

    private final ProcessInfoRepository processInfoRepository;

    private final ProcessInfoMapper processInfoMapper;

    private final ProcessInfoSearchRepository processInfoSearchRepository;

    public ProcessInfoServiceImpl(
        ProcessInfoRepository processInfoRepository,
        ProcessInfoMapper processInfoMapper,
        ProcessInfoSearchRepository processInfoSearchRepository
    ) {
        this.processInfoRepository = processInfoRepository;
        this.processInfoMapper = processInfoMapper;
        this.processInfoSearchRepository = processInfoSearchRepository;
    }

    @Override
    public ProcessInfoDTO save(ProcessInfoDTO processInfoDTO) {
        log.debug("Request to save ProcessInfo : {}", processInfoDTO);
        ProcessInfo processInfo = processInfoMapper.toEntity(processInfoDTO);
        processInfo = processInfoRepository.save(processInfo);
        ProcessInfoDTO result = processInfoMapper.toDto(processInfo);
        try{
//            processInfoSearchRepository.save(processInfo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<ProcessInfoDTO> partialUpdate(ProcessInfoDTO processInfoDTO) {
        log.debug("Request to partially update ProcessInfo : {}", processInfoDTO);

        return processInfoRepository
            .findById(processInfoDTO.getId())
            .map(existingProcessInfo -> {
                processInfoMapper.partialUpdate(existingProcessInfo, processInfoDTO);

                return existingProcessInfo;
            })
            .map(processInfoRepository::save)
            .map(savedProcessInfo -> {
                processInfoSearchRepository.save(savedProcessInfo);

                return savedProcessInfo;
            })
            .map(processInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessInfos");
        return processInfoRepository.findAll(pageable).map(processInfoMapper::toDto);
    }

    public Page<ProcessInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return processInfoRepository.findAllWithEagerRelationships(pageable).map(processInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessInfoDTO> findOne(Long id) {
        log.debug("Request to get ProcessInfo : {}", id);
        return processInfoRepository.findOneWithEagerRelationships(id).map(processInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessInfo : {}", id);
        processInfoRepository.deleteById(id);
        processInfoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProcessInfos for query {}", query);
        return processInfoSearchRepository.search(query, pageable).map(processInfoMapper::toDto);
    }
}
