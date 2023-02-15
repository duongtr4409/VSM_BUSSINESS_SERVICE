package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.AttachmentInStep;
import com.vsm.business.repository.AttachmentInStepRepository;
import com.vsm.business.repository.search.AttachmentInStepSearchRepository;
import com.vsm.business.service.AttachmentInStepService;
import com.vsm.business.service.dto.AttachmentInStepDTO;
import com.vsm.business.service.mapper.AttachmentInStepMapper;
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
 * Service Implementation for managing {@link AttachmentInStep}.
 */
@Service
@Transactional
public class AttachmentInStepServiceImpl implements AttachmentInStepService {

    private final Logger log = LoggerFactory.getLogger(AttachmentInStepServiceImpl.class);

    private final AttachmentInStepRepository attachmentInStepRepository;

    private final AttachmentInStepMapper attachmentInStepMapper;

    private final AttachmentInStepSearchRepository attachmentInStepSearchRepository;

    public AttachmentInStepServiceImpl(
        AttachmentInStepRepository attachmentInStepRepository,
        AttachmentInStepMapper attachmentInStepMapper,
        AttachmentInStepSearchRepository attachmentInStepSearchRepository
    ) {
        this.attachmentInStepRepository = attachmentInStepRepository;
        this.attachmentInStepMapper = attachmentInStepMapper;
        this.attachmentInStepSearchRepository = attachmentInStepSearchRepository;
    }

    @Override
    public AttachmentInStepDTO save(AttachmentInStepDTO attachmentInStepDTO) {
        log.debug("Request to save AttachmentInStep : {}", attachmentInStepDTO);
        AttachmentInStep attachmentInStep = attachmentInStepMapper.toEntity(attachmentInStepDTO);
        attachmentInStep = attachmentInStepRepository.save(attachmentInStep);
        AttachmentInStepDTO result = attachmentInStepMapper.toDto(attachmentInStep);
        try{
//            attachmentInStepSearchRepository.save(attachmentInStep);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<AttachmentInStepDTO> partialUpdate(AttachmentInStepDTO attachmentInStepDTO) {
        log.debug("Request to partially update AttachmentInStep : {}", attachmentInStepDTO);

        return attachmentInStepRepository
            .findById(attachmentInStepDTO.getId())
            .map(existingAttachmentInStep -> {
                attachmentInStepMapper.partialUpdate(existingAttachmentInStep, attachmentInStepDTO);

                return existingAttachmentInStep;
            })
            .map(attachmentInStepRepository::save)
            .map(savedAttachmentInStep -> {
                attachmentInStepSearchRepository.save(savedAttachmentInStep);

                return savedAttachmentInStep;
            })
            .map(attachmentInStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentInStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentInSteps");
        return attachmentInStepRepository.findAll(pageable).map(attachmentInStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentInStepDTO> findOne(Long id) {
        log.debug("Request to get AttachmentInStep : {}", id);
        return attachmentInStepRepository.findById(id).map(attachmentInStepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttachmentInStep : {}", id);
        attachmentInStepRepository.deleteById(id);
        attachmentInStepSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentInStepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentInSteps for query {}", query);
        return attachmentInStepSearchRepository.search(query, pageable).map(attachmentInStepMapper::toDto);
    }
}
