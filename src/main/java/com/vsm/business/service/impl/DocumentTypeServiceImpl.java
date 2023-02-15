package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DocumentType;
import com.vsm.business.repository.DocumentTypeRepository;
import com.vsm.business.repository.search.DocumentTypeSearchRepository;
import com.vsm.business.service.DocumentTypeService;
import com.vsm.business.service.dto.DocumentTypeDTO;
import com.vsm.business.service.mapper.DocumentTypeMapper;
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
 * Service Implementation for managing {@link DocumentType}.
 */
@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeMapper documentTypeMapper;

    private final DocumentTypeSearchRepository documentTypeSearchRepository;

    public DocumentTypeServiceImpl(
        DocumentTypeRepository documentTypeRepository,
        DocumentTypeMapper documentTypeMapper,
        DocumentTypeSearchRepository documentTypeSearchRepository
    ) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
        this.documentTypeSearchRepository = documentTypeSearchRepository;
    }

    @Override
    public DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO) {
        log.debug("Request to save DocumentType : {}", documentTypeDTO);
        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDTO);
        documentType = documentTypeRepository.save(documentType);
        DocumentTypeDTO result = documentTypeMapper.toDto(documentType);
        try{
//            documentTypeSearchRepository.save(documentType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<DocumentTypeDTO> partialUpdate(DocumentTypeDTO documentTypeDTO) {
        log.debug("Request to partially update DocumentType : {}", documentTypeDTO);

        return documentTypeRepository
            .findById(documentTypeDTO.getId())
            .map(existingDocumentType -> {
                documentTypeMapper.partialUpdate(existingDocumentType, documentTypeDTO);

                return existingDocumentType;
            })
            .map(documentTypeRepository::save)
            .map(savedDocumentType -> {
                documentTypeSearchRepository.save(savedDocumentType);

                return savedDocumentType;
            })
            .map(documentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepository.findAll(pageable).map(documentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get DocumentType : {}", id);
        return documentTypeRepository.findById(id).map(documentTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentType : {}", id);
        documentTypeRepository.deleteById(id);
        documentTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentTypes for query {}", query);
        return documentTypeSearchRepository.search(query, pageable).map(documentTypeMapper::toDto);
    }
}
