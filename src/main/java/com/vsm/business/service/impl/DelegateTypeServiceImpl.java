package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DelegateType;
import com.vsm.business.repository.DelegateTypeRepository;
import com.vsm.business.repository.search.DelegateTypeSearchRepository;
import com.vsm.business.service.DelegateTypeService;
import com.vsm.business.service.dto.DelegateTypeDTO;
import com.vsm.business.service.mapper.DelegateTypeMapper;
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
 * Service Implementation for managing {@link DelegateType}.
 */
@Service
@Transactional
public class DelegateTypeServiceImpl implements DelegateTypeService {

    private final Logger log = LoggerFactory.getLogger(DelegateTypeServiceImpl.class);

    private final DelegateTypeRepository delegateTypeRepository;

    private final DelegateTypeMapper delegateTypeMapper;

    private final DelegateTypeSearchRepository delegateTypeSearchRepository;

    public DelegateTypeServiceImpl(
        DelegateTypeRepository delegateTypeRepository,
        DelegateTypeMapper delegateTypeMapper,
        DelegateTypeSearchRepository delegateTypeSearchRepository
    ) {
        this.delegateTypeRepository = delegateTypeRepository;
        this.delegateTypeMapper = delegateTypeMapper;
        this.delegateTypeSearchRepository = delegateTypeSearchRepository;
    }

    @Override
    public DelegateTypeDTO save(DelegateTypeDTO delegateTypeDTO) {
        log.debug("Request to save DelegateType : {}", delegateTypeDTO);
        DelegateType delegateType = delegateTypeMapper.toEntity(delegateTypeDTO);
        delegateType = delegateTypeRepository.save(delegateType);
        DelegateTypeDTO result = delegateTypeMapper.toDto(delegateType);
        try{
//            delegateTypeSearchRepository.save(delegateType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<DelegateTypeDTO> partialUpdate(DelegateTypeDTO delegateTypeDTO) {
        log.debug("Request to partially update DelegateType : {}", delegateTypeDTO);

        return delegateTypeRepository
            .findById(delegateTypeDTO.getId())
            .map(existingDelegateType -> {
                delegateTypeMapper.partialUpdate(existingDelegateType, delegateTypeDTO);

                return existingDelegateType;
            })
            .map(delegateTypeRepository::save)
            .map(savedDelegateType -> {
                delegateTypeSearchRepository.save(savedDelegateType);

                return savedDelegateType;
            })
            .map(delegateTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DelegateTypes");
        return delegateTypeRepository.findAll(pageable).map(delegateTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DelegateTypeDTO> findOne(Long id) {
        log.debug("Request to get DelegateType : {}", id);
        return delegateTypeRepository.findById(id).map(delegateTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DelegateType : {}", id);
        delegateTypeRepository.deleteById(id);
        delegateTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DelegateTypes for query {}", query);
        return delegateTypeSearchRepository.search(query, pageable).map(delegateTypeMapper::toDto);
    }
}
