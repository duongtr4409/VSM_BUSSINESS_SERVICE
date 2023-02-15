package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.SecurityLevel;
import com.vsm.business.repository.SecurityLevelRepository;
import com.vsm.business.repository.search.SecurityLevelSearchRepository;
import com.vsm.business.service.SecurityLevelService;
import com.vsm.business.service.dto.SecurityLevelDTO;
import com.vsm.business.service.mapper.SecurityLevelMapper;
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
 * Service Implementation for managing {@link SecurityLevel}.
 */
@Service
@Transactional
public class SecurityLevelServiceImpl implements SecurityLevelService {

    private final Logger log = LoggerFactory.getLogger(SecurityLevelServiceImpl.class);

    private final SecurityLevelRepository securityLevelRepository;

    private final SecurityLevelMapper securityLevelMapper;

    private final SecurityLevelSearchRepository securityLevelSearchRepository;

    public SecurityLevelServiceImpl(
        SecurityLevelRepository securityLevelRepository,
        SecurityLevelMapper securityLevelMapper,
        SecurityLevelSearchRepository securityLevelSearchRepository
    ) {
        this.securityLevelRepository = securityLevelRepository;
        this.securityLevelMapper = securityLevelMapper;
        this.securityLevelSearchRepository = securityLevelSearchRepository;
    }

    @Override
    public SecurityLevelDTO save(SecurityLevelDTO securityLevelDTO) {
        log.debug("Request to save SecurityLevel : {}", securityLevelDTO);
        SecurityLevel securityLevel = securityLevelMapper.toEntity(securityLevelDTO);
        securityLevel = securityLevelRepository.save(securityLevel);
        SecurityLevelDTO result = securityLevelMapper.toDto(securityLevel);
        try{
//            securityLevelSearchRepository.save(securityLevel);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<SecurityLevelDTO> partialUpdate(SecurityLevelDTO securityLevelDTO) {
        log.debug("Request to partially update SecurityLevel : {}", securityLevelDTO);

        return securityLevelRepository
            .findById(securityLevelDTO.getId())
            .map(existingSecurityLevel -> {
                securityLevelMapper.partialUpdate(existingSecurityLevel, securityLevelDTO);

                return existingSecurityLevel;
            })
            .map(securityLevelRepository::save)
            .map(savedSecurityLevel -> {
                securityLevelSearchRepository.save(savedSecurityLevel);

                return savedSecurityLevel;
            })
            .map(securityLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SecurityLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityLevels");
        return securityLevelRepository.findAll(pageable).map(securityLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SecurityLevelDTO> findOne(Long id) {
        log.debug("Request to get SecurityLevel : {}", id);
        return securityLevelRepository.findById(id).map(securityLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SecurityLevel : {}", id);
        securityLevelRepository.deleteById(id);
        securityLevelSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SecurityLevelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SecurityLevels for query {}", query);
        return securityLevelSearchRepository.search(query, pageable).map(securityLevelMapper::toDto);
    }
}
