package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DelegateDocument;
import com.vsm.business.repository.DelegateDocumentRepository;
import com.vsm.business.repository.search.DelegateDocumentSearchRepository;
import com.vsm.business.service.DelegateDocumentService;
import com.vsm.business.service.dto.DelegateDocumentDTO;
import com.vsm.business.service.mapper.DelegateDocumentMapper;
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
 * Service Implementation for managing {@link DelegateDocument}.
 */
@Service
@Transactional
public class DelegateDocumentServiceImpl implements DelegateDocumentService {

    private final Logger log = LoggerFactory.getLogger(DelegateDocumentServiceImpl.class);

    private final DelegateDocumentRepository delegateDocumentRepository;

    private final DelegateDocumentMapper delegateDocumentMapper;

    private final DelegateDocumentSearchRepository delegateDocumentSearchRepository;

    public DelegateDocumentServiceImpl(
        DelegateDocumentRepository delegateDocumentRepository,
        DelegateDocumentMapper delegateDocumentMapper,
        DelegateDocumentSearchRepository delegateDocumentSearchRepository
    ) {
        this.delegateDocumentRepository = delegateDocumentRepository;
        this.delegateDocumentMapper = delegateDocumentMapper;
        this.delegateDocumentSearchRepository = delegateDocumentSearchRepository;
    }

    @Override
    public DelegateDocumentDTO save(DelegateDocumentDTO delegateDocumentDTO) {
        log.debug("Request to save DelegateDocument : {}", delegateDocumentDTO);
        DelegateDocument delegateDocument = delegateDocumentMapper.toEntity(delegateDocumentDTO);
        delegateDocument = delegateDocumentRepository.save(delegateDocument);
        DelegateDocumentDTO result = delegateDocumentMapper.toDto(delegateDocument);
        try{
//            delegateDocumentSearchRepository.save(delegateDocument);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<DelegateDocumentDTO> partialUpdate(DelegateDocumentDTO delegateDocumentDTO) {
        log.debug("Request to partially update DelegateDocument : {}", delegateDocumentDTO);

        return delegateDocumentRepository
            .findById(delegateDocumentDTO.getId())
            .map(existingDelegateDocument -> {
                delegateDocumentMapper.partialUpdate(existingDelegateDocument, delegateDocumentDTO);

                return existingDelegateDocument;
            })
            .map(delegateDocumentRepository::save)
            .map(savedDelegateDocument -> {
                delegateDocumentSearchRepository.save(savedDelegateDocument);

                return savedDelegateDocument;
            })
            .map(delegateDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DelegateDocuments");
        return delegateDocumentRepository.findAll(pageable).map(delegateDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DelegateDocumentDTO> findOne(Long id) {
        log.debug("Request to get DelegateDocument : {}", id);
        return delegateDocumentRepository.findById(id).map(delegateDocumentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DelegateDocument : {}", id);
        delegateDocumentRepository.deleteById(id);
        delegateDocumentSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateDocumentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DelegateDocuments for query {}", query);
        return delegateDocumentSearchRepository.search(query, pageable).map(delegateDocumentMapper::toDto);
    }
}
