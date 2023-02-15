package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.FieldInForm;
import com.vsm.business.repository.FieldInFormRepository;
import com.vsm.business.repository.search.FieldInFormSearchRepository;
import com.vsm.business.service.FieldInFormService;
import com.vsm.business.service.dto.FieldInFormDTO;
import com.vsm.business.service.mapper.FieldInFormMapper;
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
 * Service Implementation for managing {@link FieldInForm}.
 */
@Service
@Transactional
public class FieldInFormServiceImpl implements FieldInFormService {

    private final Logger log = LoggerFactory.getLogger(FieldInFormServiceImpl.class);

    private final FieldInFormRepository fieldInFormRepository;

    private final FieldInFormMapper fieldInFormMapper;

    private final FieldInFormSearchRepository fieldInFormSearchRepository;

    public FieldInFormServiceImpl(
        FieldInFormRepository fieldInFormRepository,
        FieldInFormMapper fieldInFormMapper,
        FieldInFormSearchRepository fieldInFormSearchRepository
    ) {
        this.fieldInFormRepository = fieldInFormRepository;
        this.fieldInFormMapper = fieldInFormMapper;
        this.fieldInFormSearchRepository = fieldInFormSearchRepository;
    }

    @Override
    public FieldInFormDTO save(FieldInFormDTO fieldInFormDTO) {
        log.debug("Request to save FieldInForm : {}", fieldInFormDTO);
        FieldInForm fieldInForm = fieldInFormMapper.toEntity(fieldInFormDTO);
        fieldInForm = fieldInFormRepository.save(fieldInForm);
        FieldInFormDTO result = fieldInFormMapper.toDto(fieldInForm);
        try{
//            fieldInFormSearchRepository.save(fieldInForm);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FieldInFormDTO> partialUpdate(FieldInFormDTO fieldInFormDTO) {
        log.debug("Request to partially update FieldInForm : {}", fieldInFormDTO);

        return fieldInFormRepository
            .findById(fieldInFormDTO.getId())
            .map(existingFieldInForm -> {
                fieldInFormMapper.partialUpdate(existingFieldInForm, fieldInFormDTO);

                return existingFieldInForm;
            })
            .map(fieldInFormRepository::save)
            .map(savedFieldInForm -> {
                fieldInFormSearchRepository.save(savedFieldInForm);

                return savedFieldInForm;
            })
            .map(fieldInFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldInFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FieldInForms");
        return fieldInFormRepository.findAll(pageable).map(fieldInFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldInFormDTO> findOne(Long id) {
        log.debug("Request to get FieldInForm : {}", id);
        return fieldInFormRepository.findById(id).map(fieldInFormMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldInForm : {}", id);
        fieldInFormRepository.deleteById(id);
        fieldInFormSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldInFormDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FieldInForms for query {}", query);
        return fieldInFormSearchRepository.search(query, pageable).map(fieldInFormMapper::toDto);
    }
}
