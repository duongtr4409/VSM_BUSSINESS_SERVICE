package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.AttachmentInStepType;
import com.vsm.business.repository.AttachmentInStepTypeRepository;
import com.vsm.business.repository.search.AttachmentInStepTypeSearchRepository;
import com.vsm.business.service.AttachmentInStepTypeService;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
import com.vsm.business.service.mapper.AttachmentInStepTypeMapper;
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
 * Service Implementation for managing {@link AttachmentInStepType}.
 */
@Service
@Transactional
public class AttachmentInStepTypeServiceImpl implements AttachmentInStepTypeService {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepTypeServiceImpl.class);

    private final AttachmentInStepTypeRepository attachmentInStepTypeRepository;

    private final AttachmentInStepTypeMapper attachmentInStepTypeMapper;

    private final AttachmentInStepTypeSearchRepository attachmentInStepTypeSearchRepository;

    public AttachmentInStepTypeServiceImpl(
        AttachmentInStepTypeRepository attachmentInStepTypeRepository,
        AttachmentInStepTypeMapper attachmentInStepTypeMapper,
        AttachmentInStepTypeSearchRepository attachmentInStepTypeSearchRepository
    ) {
        this.attachmentInStepTypeRepository = attachmentInStepTypeRepository;
        this.attachmentInStepTypeMapper = attachmentInStepTypeMapper;
        this.attachmentInStepTypeSearchRepository = attachmentInStepTypeSearchRepository;
    }

    @Override
    public AttachmentInStepTypeDTO save(AttachmentInStepTypeDTO attachmentInStepTypeDTO) {
        log.debug("Request to save AttachmentInStepType : {}", attachmentInStepTypeDTO);
        AttachmentInStepType attachmentInStepType = attachmentInStepTypeMapper.toEntity(attachmentInStepTypeDTO);
        attachmentInStepType = attachmentInStepTypeRepository.save(attachmentInStepType);
        AttachmentInStepTypeDTO result = attachmentInStepTypeMapper.toDto(attachmentInStepType);
        try{
//            attachmentInStepTypeSearchRepository.save(attachmentInStepType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<AttachmentInStepTypeDTO> partialUpdate(AttachmentInStepTypeDTO attachmentInStepTypeDTO) {
        log.debug("Request to partially update AttachmentInStepType : {}", attachmentInStepTypeDTO);

        return attachmentInStepTypeRepository
            .findById(attachmentInStepTypeDTO.getId())
            .map(existingAttachmentInStepType -> {
                attachmentInStepTypeMapper.partialUpdate(existingAttachmentInStepType, attachmentInStepTypeDTO);

                return existingAttachmentInStepType;
            })
            .map(attachmentInStepTypeRepository::save)
            .map(savedAttachmentInStepType -> {
                attachmentInStepTypeSearchRepository.save(savedAttachmentInStepType);

                return savedAttachmentInStepType;
            })
            .map(attachmentInStepTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentInStepTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentInStepTypes");
        return attachmentInStepTypeRepository.findAll(pageable).map(attachmentInStepTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentInStepTypeDTO> findOne(Long id) {
        log.debug("Request to get AttachmentInStepType : {}", id);
        return attachmentInStepTypeRepository.findById(id).map(attachmentInStepTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttachmentInStepType : {}", id);
        attachmentInStepTypeRepository.deleteById(id);
        attachmentInStepTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentInStepTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentInStepTypes for query {}", query);
        return attachmentInStepTypeSearchRepository.search(query, pageable).map(attachmentInStepTypeMapper::toDto);
    }
}
