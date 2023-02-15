package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.MailTemplate;
import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.repository.search.MailTemplateSearchRepository;
import com.vsm.business.service.MailTemplateService;
import com.vsm.business.service.dto.MailTemplateDTO;
import com.vsm.business.service.mapper.MailTemplateMapper;
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
 * Service Implementation for managing {@link MailTemplate}.
 */
@Service
@Transactional
public class MailTemplateServiceImpl implements MailTemplateService {

    private final Logger log = LoggerFactory.getLogger(MailTemplateServiceImpl.class);

    private final MailTemplateRepository mailTemplateRepository;

    private final MailTemplateMapper mailTemplateMapper;

    private final MailTemplateSearchRepository mailTemplateSearchRepository;

    public MailTemplateServiceImpl(
        MailTemplateRepository mailTemplateRepository,
        MailTemplateMapper mailTemplateMapper,
        MailTemplateSearchRepository mailTemplateSearchRepository
    ) {
        this.mailTemplateRepository = mailTemplateRepository;
        this.mailTemplateMapper = mailTemplateMapper;
        this.mailTemplateSearchRepository = mailTemplateSearchRepository;
    }

    @Override
    public MailTemplateDTO save(MailTemplateDTO mailTemplateDTO) {
        log.debug("Request to save MailTemplate : {}", mailTemplateDTO);
        MailTemplate mailTemplate = mailTemplateMapper.toEntity(mailTemplateDTO);
        mailTemplate = mailTemplateRepository.save(mailTemplate);
        MailTemplateDTO result = mailTemplateMapper.toDto(mailTemplate);
        try{
//            mailTemplateSearchRepository.save(mailTemplate);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<MailTemplateDTO> partialUpdate(MailTemplateDTO mailTemplateDTO) {
        log.debug("Request to partially update MailTemplate : {}", mailTemplateDTO);

        return mailTemplateRepository
            .findById(mailTemplateDTO.getId())
            .map(existingMailTemplate -> {
                mailTemplateMapper.partialUpdate(existingMailTemplate, mailTemplateDTO);

                return existingMailTemplate;
            })
            .map(mailTemplateRepository::save)
            .map(savedMailTemplate -> {
                mailTemplateSearchRepository.save(savedMailTemplate);

                return savedMailTemplate;
            })
            .map(mailTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MailTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MailTemplates");
        return mailTemplateRepository.findAll(pageable).map(mailTemplateMapper::toDto);
    }

    public Page<MailTemplateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mailTemplateRepository.findAllWithEagerRelationships(pageable).map(mailTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MailTemplateDTO> findOne(Long id) {
        log.debug("Request to get MailTemplate : {}", id);
        return mailTemplateRepository.findOneWithEagerRelationships(id).map(mailTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MailTemplate : {}", id);
        mailTemplateRepository.deleteById(id);
        mailTemplateSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MailTemplateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MailTemplates for query {}", query);
        return mailTemplateSearchRepository.search(query, pageable).map(mailTemplateMapper::toDto);
    }
}
