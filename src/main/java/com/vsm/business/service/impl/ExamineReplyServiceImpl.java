package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ExamineReply;
import com.vsm.business.repository.ExamineReplyRepository;
import com.vsm.business.repository.search.ExamineReplySearchRepository;
import com.vsm.business.service.ExamineReplyService;
import com.vsm.business.service.dto.ExamineReplyDTO;
import com.vsm.business.service.mapper.ExamineReplyMapper;
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
 * Service Implementation for managing {@link ExamineReply}.
 */
@Service
@Transactional
public class ExamineReplyServiceImpl implements ExamineReplyService {

    private final Logger log = LoggerFactory.getLogger(ExamineReplyServiceImpl.class);

    private final ExamineReplyRepository examineReplyRepository;

    private final ExamineReplyMapper examineReplyMapper;

    private final ExamineReplySearchRepository examineReplySearchRepository;

    public ExamineReplyServiceImpl(
        ExamineReplyRepository examineReplyRepository,
        ExamineReplyMapper examineReplyMapper,
        ExamineReplySearchRepository examineReplySearchRepository
    ) {
        this.examineReplyRepository = examineReplyRepository;
        this.examineReplyMapper = examineReplyMapper;
        this.examineReplySearchRepository = examineReplySearchRepository;
    }

    @Override
    public ExamineReplyDTO save(ExamineReplyDTO examineReplyDTO) {
        log.debug("Request to save ExamineReply : {}", examineReplyDTO);
        ExamineReply examineReply = examineReplyMapper.toEntity(examineReplyDTO);
        examineReply = examineReplyRepository.save(examineReply);
        ExamineReplyDTO result = examineReplyMapper.toDto(examineReply);
        try{
//            examineReplySearchRepository.save(examineReply);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ExamineReplyDTO> partialUpdate(ExamineReplyDTO examineReplyDTO) {
        log.debug("Request to partially update ExamineReply : {}", examineReplyDTO);

        return examineReplyRepository
            .findById(examineReplyDTO.getId())
            .map(existingExamineReply -> {
                examineReplyMapper.partialUpdate(existingExamineReply, examineReplyDTO);

                return existingExamineReply;
            })
            .map(examineReplyRepository::save)
            .map(savedExamineReply -> {
                examineReplySearchRepository.save(savedExamineReply);

                return savedExamineReply;
            })
            .map(examineReplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamineReplyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamineReplies");
        return examineReplyRepository.findAll(pageable).map(examineReplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamineReplyDTO> findOne(Long id) {
        log.debug("Request to get ExamineReply : {}", id);
        return examineReplyRepository.findById(id).map(examineReplyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamineReply : {}", id);
        examineReplyRepository.deleteById(id);
        examineReplySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamineReplyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExamineReplies for query {}", query);
        return examineReplySearchRepository.search(query, pageable).map(examineReplyMapper::toDto);
    }
}
