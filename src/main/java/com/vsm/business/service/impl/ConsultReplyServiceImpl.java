package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ConsultReply;
import com.vsm.business.repository.ConsultReplyRepository;
import com.vsm.business.repository.search.ConsultReplySearchRepository;
import com.vsm.business.service.ConsultReplyService;
import com.vsm.business.service.dto.ConsultReplyDTO;
import com.vsm.business.service.mapper.ConsultReplyMapper;
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
 * Service Implementation for managing {@link ConsultReply}.
 */
@Service
@Transactional
public class ConsultReplyServiceImpl implements ConsultReplyService {

    private final Logger log = LoggerFactory.getLogger(ConsultReplyServiceImpl.class);

    private final ConsultReplyRepository consultReplyRepository;

    private final ConsultReplyMapper consultReplyMapper;

    private final ConsultReplySearchRepository consultReplySearchRepository;

    public ConsultReplyServiceImpl(
        ConsultReplyRepository consultReplyRepository,
        ConsultReplyMapper consultReplyMapper,
        ConsultReplySearchRepository consultReplySearchRepository
    ) {
        this.consultReplyRepository = consultReplyRepository;
        this.consultReplyMapper = consultReplyMapper;
        this.consultReplySearchRepository = consultReplySearchRepository;
    }

    @Override
    public ConsultReplyDTO save(ConsultReplyDTO consultReplyDTO) {
        log.debug("Request to save ConsultReply : {}", consultReplyDTO);
        ConsultReply consultReply = consultReplyMapper.toEntity(consultReplyDTO);
        consultReply = consultReplyRepository.save(consultReply);
        ConsultReplyDTO result = consultReplyMapper.toDto(consultReply);
        try{
            consultReplySearchRepository.save(consultReply);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ConsultReplyDTO> partialUpdate(ConsultReplyDTO consultReplyDTO) {
        log.debug("Request to partially update ConsultReply : {}", consultReplyDTO);

        return consultReplyRepository
            .findById(consultReplyDTO.getId())
            .map(existingConsultReply -> {
                consultReplyMapper.partialUpdate(existingConsultReply, consultReplyDTO);

                return existingConsultReply;
            })
            .map(consultReplyRepository::save)
            .map(savedConsultReply -> {
                consultReplySearchRepository.save(savedConsultReply);

                return savedConsultReply;
            })
            .map(consultReplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultReplyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultReplies");
        return consultReplyRepository.findAll(pageable).map(consultReplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultReplyDTO> findOne(Long id) {
        log.debug("Request to get ConsultReply : {}", id);
        return consultReplyRepository.findById(id).map(consultReplyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultReply : {}", id);
        consultReplyRepository.deleteById(id);
        consultReplySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultReplyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConsultReplies for query {}", query);
        return consultReplySearchRepository.search(query, pageable).map(consultReplyMapper::toDto);
    }
}
