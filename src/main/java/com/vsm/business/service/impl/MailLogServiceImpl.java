package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.MailLog;
import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.repository.search.MailLogSearchRepository;
import com.vsm.business.service.MailLogService;
import com.vsm.business.service.dto.MailLogDTO;
import com.vsm.business.service.mapper.MailLogMapper;
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
 * Service Implementation for managing {@link MailLog}.
 */
@Service
@Transactional
public class MailLogServiceImpl implements MailLogService {

    private final Logger log = LoggerFactory.getLogger(MailLogServiceImpl.class);

    private final MailLogRepository mailLogRepository;

    private final MailLogMapper mailLogMapper;

    private final MailLogSearchRepository mailLogSearchRepository;

    public MailLogServiceImpl(
        MailLogRepository mailLogRepository,
        MailLogMapper mailLogMapper,
        MailLogSearchRepository mailLogSearchRepository
    ) {
        this.mailLogRepository = mailLogRepository;
        this.mailLogMapper = mailLogMapper;
        this.mailLogSearchRepository = mailLogSearchRepository;
    }

    @Override
    public MailLogDTO save(MailLogDTO mailLogDTO) {
        log.debug("Request to save MailLog : {}", mailLogDTO);
        MailLog mailLog = mailLogMapper.toEntity(mailLogDTO);
        mailLog = mailLogRepository.save(mailLog);
        MailLogDTO result = mailLogMapper.toDto(mailLog);
        try{
//            mailLogSearchRepository.save(mailLog);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<MailLogDTO> partialUpdate(MailLogDTO mailLogDTO) {
        log.debug("Request to partially update MailLog : {}", mailLogDTO);

        return mailLogRepository
            .findById(mailLogDTO.getId())
            .map(existingMailLog -> {
                mailLogMapper.partialUpdate(existingMailLog, mailLogDTO);

                return existingMailLog;
            })
            .map(mailLogRepository::save)
            .map(savedMailLog -> {
                mailLogSearchRepository.save(savedMailLog);

                return savedMailLog;
            })
            .map(mailLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MailLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MailLogs");
        return mailLogRepository.findAll(pageable).map(mailLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MailLogDTO> findOne(Long id) {
        log.debug("Request to get MailLog : {}", id);
        return mailLogRepository.findById(id).map(mailLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MailLog : {}", id);
        mailLogRepository.deleteById(id);
        mailLogSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MailLogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MailLogs for query {}", query);
        return mailLogSearchRepository.search(query, pageable).map(mailLogMapper::toDto);
    }
}
