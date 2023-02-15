package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.FieldData;
import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.repository.search.FieldDataSearchRepository;
import com.vsm.business.service.FieldDataService;
import com.vsm.business.service.dto.FieldDataDTO;
import com.vsm.business.service.mapper.FieldDataMapper;
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
 * Service Implementation for managing {@link FieldData}.
 */
@Service
@Transactional
public class FieldDataServiceImpl implements FieldDataService {

    private final Logger log = LoggerFactory.getLogger(FieldDataServiceImpl.class);

    private final FieldDataRepository fieldDataRepository;

    private final FieldDataMapper fieldDataMapper;

    private final FieldDataSearchRepository fieldDataSearchRepository;

    public FieldDataServiceImpl(
        FieldDataRepository fieldDataRepository,
        FieldDataMapper fieldDataMapper,
        FieldDataSearchRepository fieldDataSearchRepository
    ) {
        this.fieldDataRepository = fieldDataRepository;
        this.fieldDataMapper = fieldDataMapper;
        this.fieldDataSearchRepository = fieldDataSearchRepository;
    }

    @Override
    public FieldDataDTO save(FieldDataDTO fieldDataDTO) {
        log.debug("Request to save FieldData : {}", fieldDataDTO);
        FieldData fieldData = fieldDataMapper.toEntity(fieldDataDTO);
        fieldData = fieldDataRepository.save(fieldData);
        FieldDataDTO result = fieldDataMapper.toDto(fieldData);
        try{
//            fieldDataSearchRepository.save(fieldData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FieldDataDTO> partialUpdate(FieldDataDTO fieldDataDTO) {
        log.debug("Request to partially update FieldData : {}", fieldDataDTO);

        return fieldDataRepository
            .findById(fieldDataDTO.getId())
            .map(existingFieldData -> {
                fieldDataMapper.partialUpdate(existingFieldData, fieldDataDTO);

                return existingFieldData;
            })
            .map(fieldDataRepository::save)
            .map(savedFieldData -> {
                fieldDataSearchRepository.save(savedFieldData);

                return savedFieldData;
            })
            .map(fieldDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FieldData");
        return fieldDataRepository.findAll(pageable).map(fieldDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldDataDTO> findOne(Long id) {
        log.debug("Request to get FieldData : {}", id);
        return fieldDataRepository.findById(id).map(fieldDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldData : {}", id);
        fieldDataRepository.deleteById(id);
        fieldDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FieldData for query {}", query);
        return fieldDataSearchRepository.search(query, pageable).map(fieldDataMapper::toDto);
    }
}
