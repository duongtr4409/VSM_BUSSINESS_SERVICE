package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.FormData;
import com.vsm.business.repository.FormDataRepository;
import com.vsm.business.repository.search.FormDataSearchRepository;
import com.vsm.business.service.FormDataService;
import com.vsm.business.service.dto.FormDataDTO;
import com.vsm.business.service.mapper.FormDataMapper;
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
 * Service Implementation for managing {@link FormData}.
 */
@Service
@Transactional
public class FormDataServiceImpl implements FormDataService {

    private final Logger log = LoggerFactory.getLogger(FormDataServiceImpl.class);

    private final FormDataRepository formDataRepository;

    private final FormDataMapper formDataMapper;

    private final FormDataSearchRepository formDataSearchRepository;

    public FormDataServiceImpl(
        FormDataRepository formDataRepository,
        FormDataMapper formDataMapper,
        FormDataSearchRepository formDataSearchRepository
    ) {
        this.formDataRepository = formDataRepository;
        this.formDataMapper = formDataMapper;
        this.formDataSearchRepository = formDataSearchRepository;
    }

    @Override
    public FormDataDTO save(FormDataDTO formDataDTO) {
        log.debug("Request to save FormData : {}", formDataDTO);
        FormData formData = formDataMapper.toEntity(formDataDTO);
        formData = formDataRepository.save(formData);
        FormDataDTO result = formDataMapper.toDto(formData);
        try{
//            formDataSearchRepository.save(formData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FormDataDTO> partialUpdate(FormDataDTO formDataDTO) {
        log.debug("Request to partially update FormData : {}", formDataDTO);

        return formDataRepository
            .findById(formDataDTO.getId())
            .map(existingFormData -> {
                formDataMapper.partialUpdate(existingFormData, formDataDTO);

                return existingFormData;
            })
            .map(formDataRepository::save)
            .map(savedFormData -> {
                formDataSearchRepository.save(savedFormData);

                return savedFormData;
            })
            .map(formDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormData");
        return formDataRepository.findAll(pageable).map(formDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormDataDTO> findOne(Long id) {
        log.debug("Request to get FormData : {}", id);
        return formDataRepository.findById(id).map(formDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormData : {}", id);
        formDataRepository.deleteById(id);
        formDataSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormData for query {}", query);
        return formDataSearchRepository.search(query, pageable).map(formDataMapper::toDto);
    }
}
