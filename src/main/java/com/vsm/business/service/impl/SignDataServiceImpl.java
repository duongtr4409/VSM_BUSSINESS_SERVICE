package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.SignData;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.repository.search.SignDataSearchRepository;
import com.vsm.business.service.SignDataService;
import com.vsm.business.service.dto.SignDataDTO;
import com.vsm.business.service.mapper.SignDataMapper;
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
 * Service Implementation for managing {@link SignData}.
 */
@Service
@Transactional
public class SignDataServiceImpl implements SignDataService {

    private final Logger log = LoggerFactory.getLogger(SignDataServiceImpl.class);

    private final SignDataRepository signDataRepository;

    private final SignDataMapper signDataMapper;

    private final SignDataSearchRepository signDataSearchRepository;

    public SignDataServiceImpl(
        SignDataRepository signDataRepository,
        SignDataMapper signDataMapper,
        SignDataSearchRepository signDataSearchRepository
    ) {
        this.signDataRepository = signDataRepository;
        this.signDataMapper = signDataMapper;
        this.signDataSearchRepository = signDataSearchRepository;
    }

    @Override
    public SignDataDTO save(SignDataDTO signDataDTO) {
        log.debug("Request to save SignData : {}", signDataDTO);
        SignData signData = signDataMapper.toEntity(signDataDTO);
        signData = signDataRepository.save(signData);
        SignDataDTO result = signDataMapper.toDto(signData);
        try{
//            signDataSearchRepository.save(signData);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<SignDataDTO> partialUpdate(SignDataDTO signDataDTO) {
        log.debug("Request to partially update SignData : {}", signDataDTO);

        return signDataRepository
            .findById(signDataDTO.getId())
            .map(existingSignData -> {
                signDataMapper.partialUpdate(existingSignData, signDataDTO);

                return existingSignData;
            })
            .map(signDataRepository::save)
            .map(savedSignData -> {
                signDataSearchRepository.save(savedSignData);

                return savedSignData;
            })
            .map(signDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SignData");
        return signDataRepository.findAll(pageable).map(signDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SignDataDTO> findOne(Long id) {
        log.debug("Request to get SignData : {}", id);
        return signDataRepository.findById(id).map(signDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignData : {}", id);
        signDataRepository.deleteById(id);
        signDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SignData for query {}", query);
        return signDataSearchRepository.search(query, pageable).map(signDataMapper::toDto);
    }
}
