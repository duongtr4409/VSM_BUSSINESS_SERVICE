package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Field;
import com.vsm.business.repository.FieldRepository;
import com.vsm.business.repository.search.FieldSearchRepository;
import com.vsm.business.service.FieldService;
import com.vsm.business.service.dto.FieldDTO;
import com.vsm.business.service.mapper.FieldMapper;
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
 * Service Implementation for managing {@link Field}.
 */
@Service
@Transactional
public class FieldServiceImpl implements FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldServiceImpl.class);

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    private final FieldSearchRepository fieldSearchRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FieldMapper fieldMapper, FieldSearchRepository fieldSearchRepository) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
        this.fieldSearchRepository = fieldSearchRepository;
    }

    @Override
    public FieldDTO save(FieldDTO fieldDTO) {
        log.debug("Request to save Field : {}", fieldDTO);
        Field field = fieldMapper.toEntity(fieldDTO);
        field = fieldRepository.save(field);
        FieldDTO result = fieldMapper.toDto(field);
        try{
//            fieldSearchRepository.save(field);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FieldDTO> partialUpdate(FieldDTO fieldDTO) {
        log.debug("Request to partially update Field : {}", fieldDTO);

        return fieldRepository
            .findById(fieldDTO.getId())
            .map(existingField -> {
                fieldMapper.partialUpdate(existingField, fieldDTO);

                return existingField;
            })
            .map(fieldRepository::save)
            .map(savedField -> {
                fieldSearchRepository.save(savedField);

                return savedField;
            })
            .map(fieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll(pageable).map(fieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldDTO> findOne(Long id) {
        log.debug("Request to get Field : {}", id);
        return fieldRepository.findById(id).map(fieldMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Field : {}", id);
        fieldRepository.deleteById(id);
        fieldSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fields for query {}", query);
        return fieldSearchRepository.search(query, pageable).map(fieldMapper::toDto);
    }
}
