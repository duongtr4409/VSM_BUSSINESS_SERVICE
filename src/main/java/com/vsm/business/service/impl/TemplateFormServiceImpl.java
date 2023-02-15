package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.TemplateForm;
import com.vsm.business.repository.TemplateFormRepository;
import com.vsm.business.repository.search.TemplateFormSearchRepository;
import com.vsm.business.service.TemplateFormService;
import com.vsm.business.service.dto.TemplateFormDTO;
import com.vsm.business.service.mapper.TemplateFormMapper;
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
 * Service Implementation for managing {@link TemplateForm}.
 */
@Service
@Transactional
public class TemplateFormServiceImpl implements TemplateFormService {

    private final Logger log = LoggerFactory.getLogger(TemplateFormServiceImpl.class);

    private final TemplateFormRepository templateFormRepository;

    private final TemplateFormMapper templateFormMapper;

    private final TemplateFormSearchRepository templateFormSearchRepository;

    public TemplateFormServiceImpl(
        TemplateFormRepository templateFormRepository,
        TemplateFormMapper templateFormMapper,
        TemplateFormSearchRepository templateFormSearchRepository
    ) {
        this.templateFormRepository = templateFormRepository;
        this.templateFormMapper = templateFormMapper;
        this.templateFormSearchRepository = templateFormSearchRepository;
    }

    @Override
    public TemplateFormDTO save(TemplateFormDTO templateFormDTO) {
        log.debug("Request to save TemplateForm : {}", templateFormDTO);
        TemplateForm templateForm = templateFormMapper.toEntity(templateFormDTO);
        templateForm = templateFormRepository.save(templateForm);
        TemplateFormDTO result = templateFormMapper.toDto(templateForm);
        try{
//            templateFormSearchRepository.save(templateForm);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<TemplateFormDTO> partialUpdate(TemplateFormDTO templateFormDTO) {
        log.debug("Request to partially update TemplateForm : {}", templateFormDTO);

        return templateFormRepository
            .findById(templateFormDTO.getId())
            .map(existingTemplateForm -> {
                templateFormMapper.partialUpdate(existingTemplateForm, templateFormDTO);

                return existingTemplateForm;
            })
            .map(templateFormRepository::save)
            .map(savedTemplateForm -> {
                templateFormSearchRepository.save(savedTemplateForm);

                return savedTemplateForm;
            })
            .map(templateFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateForms");
        return templateFormRepository.findAll(pageable).map(templateFormMapper::toDto);
    }

    public Page<TemplateFormDTO> findAllWithEagerRelationships(Pageable pageable) {
        return templateFormRepository.findAllWithEagerRelationships(pageable).map(templateFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateFormDTO> findOne(Long id) {
        log.debug("Request to get TemplateForm : {}", id);
        return templateFormRepository.findOneWithEagerRelationships(id).map(templateFormMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateForm : {}", id);
        templateFormRepository.deleteById(id);
        templateFormSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateFormDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TemplateForms for query {}", query);
        return templateFormSearchRepository.search(query, pageable).map(templateFormMapper::toDto);
    }
}
