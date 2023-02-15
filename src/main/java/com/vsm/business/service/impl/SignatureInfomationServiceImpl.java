package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.SignatureInfomation;
import com.vsm.business.repository.SignatureInfomationRepository;
import com.vsm.business.repository.search.SignatureInfomationSearchRepository;
import com.vsm.business.service.SignatureInfomationService;
import com.vsm.business.service.dto.SignatureInfomationDTO;
import com.vsm.business.service.mapper.SignatureInfomationMapper;
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
 * Service Implementation for managing {@link SignatureInfomation}.
 */
@Service
@Transactional
public class SignatureInfomationServiceImpl implements SignatureInfomationService {

    private final Logger log = LoggerFactory.getLogger(SignatureInfomationServiceImpl.class);

    private final SignatureInfomationRepository signatureInfomationRepository;

    private final SignatureInfomationMapper signatureInfomationMapper;

    private final SignatureInfomationSearchRepository signatureInfomationSearchRepository;

    public SignatureInfomationServiceImpl(
        SignatureInfomationRepository signatureInfomationRepository,
        SignatureInfomationMapper signatureInfomationMapper,
        SignatureInfomationSearchRepository signatureInfomationSearchRepository
    ) {
        this.signatureInfomationRepository = signatureInfomationRepository;
        this.signatureInfomationMapper = signatureInfomationMapper;
        this.signatureInfomationSearchRepository = signatureInfomationSearchRepository;
    }

    @Override
    public SignatureInfomationDTO save(SignatureInfomationDTO signatureInfomationDTO) {
        log.debug("Request to save SignatureInfomation : {}", signatureInfomationDTO);
        SignatureInfomation signatureInfomation = signatureInfomationMapper.toEntity(signatureInfomationDTO);
        signatureInfomation = signatureInfomationRepository.save(signatureInfomation);
        SignatureInfomationDTO result = signatureInfomationMapper.toDto(signatureInfomation);
        try{
//            signatureInfomationSearchRepository.save(signatureInfomation);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<SignatureInfomationDTO> partialUpdate(SignatureInfomationDTO signatureInfomationDTO) {
        log.debug("Request to partially update SignatureInfomation : {}", signatureInfomationDTO);

        return signatureInfomationRepository
            .findById(signatureInfomationDTO.getId())
            .map(existingSignatureInfomation -> {
                signatureInfomationMapper.partialUpdate(existingSignatureInfomation, signatureInfomationDTO);

                return existingSignatureInfomation;
            })
            .map(signatureInfomationRepository::save)
            .map(savedSignatureInfomation -> {
                signatureInfomationSearchRepository.save(savedSignatureInfomation);

                return savedSignatureInfomation;
            })
            .map(signatureInfomationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignatureInfomationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SignatureInfomations");
        return signatureInfomationRepository.findAll(pageable).map(signatureInfomationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SignatureInfomationDTO> findOne(Long id) {
        log.debug("Request to get SignatureInfomation : {}", id);
        return signatureInfomationRepository.findById(id).map(signatureInfomationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignatureInfomation : {}", id);
        signatureInfomationRepository.deleteById(id);
        signatureInfomationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignatureInfomationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SignatureInfomations for query {}", query);
        return signatureInfomationSearchRepository.search(query, pageable).map(signatureInfomationMapper::toDto);
    }
}
