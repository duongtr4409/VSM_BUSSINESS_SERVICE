package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.search.AttachmentFileSearchRepository;
import com.vsm.business.service.AttachmentFileService;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttachmentFile}.
 */
@Service
@Transactional
public class AttachmentFileServiceImpl implements AttachmentFileService {

    private final Logger log = LoggerFactory.getLogger(AttachmentFileServiceImpl.class);

    private final AttachmentFileRepository attachmentFileRepository;

    private final AttachmentFileMapper attachmentFileMapper;

    private final AttachmentFileSearchRepository attachmentFileSearchRepository;

    public AttachmentFileServiceImpl(
        AttachmentFileRepository attachmentFileRepository,
        AttachmentFileMapper attachmentFileMapper,
        AttachmentFileSearchRepository attachmentFileSearchRepository
    ) {
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileMapper = attachmentFileMapper;
        this.attachmentFileSearchRepository = attachmentFileSearchRepository;
    }

    @Override
    public AttachmentFileDTO save(AttachmentFileDTO attachmentFileDTO) {
        log.debug("Request to save AttachmentFile : {}", attachmentFileDTO);
        AttachmentFile attachmentFile = attachmentFileMapper.toEntity(attachmentFileDTO);
        attachmentFile = attachmentFileRepository.save(attachmentFile);
        AttachmentFileDTO result = attachmentFileMapper.toDto(attachmentFile);
        try {
//            attachmentFileSearchRepository.save(attachmentFile);
        } catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e) {

        }
        return result;
    }

    @Override
    public Optional<AttachmentFileDTO> partialUpdate(AttachmentFileDTO attachmentFileDTO) {
        log.debug("Request to partially update AttachmentFile : {}", attachmentFileDTO);

        return attachmentFileRepository
            .findById(attachmentFileDTO.getId())
            .map(existingAttachmentFile -> {
                attachmentFileMapper.partialUpdate(existingAttachmentFile, attachmentFileDTO);

                return existingAttachmentFile;
            })
            .map(attachmentFileRepository::save)
            .map(savedAttachmentFile -> {
                attachmentFileSearchRepository.save(savedAttachmentFile);

                return savedAttachmentFile;
            })
            .map(attachmentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentFiles");
        return attachmentFileRepository.findAll(pageable).map(attachmentFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentFileDTO> findOne(Long id) {
        log.debug("Request to get AttachmentFile : {}", id);
        return attachmentFileRepository.findById(id).map(attachmentFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttachmentFile : {}", id);
        attachmentFileRepository.deleteById(id);
        attachmentFileSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentFiles for query {}", query);
        return attachmentFileSearchRepository.search(query, pageable).map(attachmentFileMapper::toDto);
    }
}
