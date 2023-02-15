package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Form;
import com.vsm.business.repository.FormRepository;
import com.vsm.business.repository.search.FormSearchRepository;
import com.vsm.business.service.FormService;
import com.vsm.business.service.dto.FormDTO;
import com.vsm.business.service.mapper.FormMapper;
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
 * Service Implementation for managing {@link Form}.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    private final FormRepository formRepository;

    private final FormMapper formMapper;

    private final FormSearchRepository formSearchRepository;

    public FormServiceImpl(FormRepository formRepository, FormMapper formMapper, FormSearchRepository formSearchRepository) {
        this.formRepository = formRepository;
        this.formMapper = formMapper;
        this.formSearchRepository = formSearchRepository;
    }

    @Override
    public FormDTO save(FormDTO formDTO) {
        log.debug("Request to save Form : {}", formDTO);
        Form form = formMapper.toEntity(formDTO);
        form = formRepository.save(form);
        FormDTO result = formMapper.toDto(form);
        try{
//            formSearchRepository.save(form);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FormDTO> partialUpdate(FormDTO formDTO) {
        log.debug("Request to partially update Form : {}", formDTO);

        return formRepository
            .findById(formDTO.getId())
            .map(existingForm -> {
                formMapper.partialUpdate(existingForm, formDTO);

                return existingForm;
            })
            .map(formRepository::save)
            .map(savedForm -> {
                formSearchRepository.save(savedForm);

                return savedForm;
            })
            .map(formMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Forms");
        return formRepository.findAll(pageable).map(formMapper::toDto);
    }

    public Page<FormDTO> findAllWithEagerRelationships(Pageable pageable) {
        return formRepository.findAllWithEagerRelationships(pageable).map(formMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormDTO> findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        return formRepository.findOneWithEagerRelationships(id).map(formMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        formRepository.deleteById(id);
        formSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Forms for query {}", query);
        return formSearchRepository.search(query, pageable).map(formMapper::toDto);
    }
}
