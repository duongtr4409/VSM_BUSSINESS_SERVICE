package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.AttachmentPermisition;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.service.AttachmentPermisitionService;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
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
 * Service Implementation for managing {@link AttachmentPermisition}.
 */
@Service
@Transactional
public class AttachmentPermisitionServiceImpl implements AttachmentPermisitionService {

    private final Logger log = LoggerFactory.getLogger(AttachmentPermisitionServiceImpl.class);

    private final AttachmentPermisitionRepository attachmentPermisitionRepository;

    private final AttachmentPermisitionMapper attachmentPermisitionMapper;

    private final AttachmentPermisitionSearchRepository attachmentPermisitionSearchRepository;

    public AttachmentPermisitionServiceImpl(
        AttachmentPermisitionRepository attachmentPermisitionRepository,
        AttachmentPermisitionMapper attachmentPermisitionMapper,
        AttachmentPermisitionSearchRepository attachmentPermisitionSearchRepository
    ) {
        this.attachmentPermisitionRepository = attachmentPermisitionRepository;
        this.attachmentPermisitionMapper = attachmentPermisitionMapper;
        this.attachmentPermisitionSearchRepository = attachmentPermisitionSearchRepository;
    }

    @Override
    public AttachmentPermisitionDTO save(AttachmentPermisitionDTO attachmentPermisitionDTO) {
        log.debug("Request to save AttachmentPermisition : {}", attachmentPermisitionDTO);
        AttachmentPermisition attachmentPermisition = attachmentPermisitionMapper.toEntity(attachmentPermisitionDTO);
        attachmentPermisition = attachmentPermisitionRepository.save(attachmentPermisition);
        AttachmentPermisitionDTO result = attachmentPermisitionMapper.toDto(attachmentPermisition);
        try{
//            attachmentPermisitionSearchRepository.save(attachmentPermisition);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<AttachmentPermisitionDTO> partialUpdate(AttachmentPermisitionDTO attachmentPermisitionDTO) {
        log.debug("Request to partially update AttachmentPermisition : {}", attachmentPermisitionDTO);

        return attachmentPermisitionRepository
            .findById(attachmentPermisitionDTO.getId())
            .map(existingAttachmentPermisition -> {
                attachmentPermisitionMapper.partialUpdate(existingAttachmentPermisition, attachmentPermisitionDTO);

                return existingAttachmentPermisition;
            })
            .map(attachmentPermisitionRepository::save)
            .map(savedAttachmentPermisition -> {
                attachmentPermisitionSearchRepository.save(savedAttachmentPermisition);

                return savedAttachmentPermisition;
            })
            .map(attachmentPermisitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentPermisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentPermisitions");
        return attachmentPermisitionRepository.findAll(pageable).map(attachmentPermisitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentPermisitionDTO> findOne(Long id) {
        log.debug("Request to get AttachmentPermisition : {}", id);
        return attachmentPermisitionRepository.findById(id).map(attachmentPermisitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttachmentPermisition : {}", id);
        attachmentPermisitionRepository.deleteById(id);
        attachmentPermisitionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentPermisitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttachmentPermisitions for query {}", query);
        return attachmentPermisitionSearchRepository.search(query, pageable).map(attachmentPermisitionMapper::toDto);
    }
}
