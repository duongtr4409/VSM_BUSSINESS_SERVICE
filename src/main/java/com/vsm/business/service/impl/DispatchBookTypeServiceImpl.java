package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DispatchBookType;
import com.vsm.business.repository.DispatchBookTypeRepository;
import com.vsm.business.repository.search.DispatchBookTypeSearchRepository;
import com.vsm.business.service.DispatchBookTypeService;
import com.vsm.business.service.dto.DispatchBookTypeDTO;
import com.vsm.business.service.mapper.DispatchBookTypeMapper;
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
 * Service Implementation for managing {@link DispatchBookType}.
 */
@Service
@Transactional
public class DispatchBookTypeServiceImpl implements DispatchBookTypeService {

    private final Logger log = LoggerFactory.getLogger(DispatchBookTypeServiceImpl.class);

    private final DispatchBookTypeRepository dispatchBookTypeRepository;

    private final DispatchBookTypeMapper dispatchBookTypeMapper;

    private final DispatchBookTypeSearchRepository dispatchBookTypeSearchRepository;

    public DispatchBookTypeServiceImpl(
        DispatchBookTypeRepository dispatchBookTypeRepository,
        DispatchBookTypeMapper dispatchBookTypeMapper,
        DispatchBookTypeSearchRepository dispatchBookTypeSearchRepository
    ) {
        this.dispatchBookTypeRepository = dispatchBookTypeRepository;
        this.dispatchBookTypeMapper = dispatchBookTypeMapper;
        this.dispatchBookTypeSearchRepository = dispatchBookTypeSearchRepository;
    }

    @Override
    public DispatchBookTypeDTO save(DispatchBookTypeDTO dispatchBookTypeDTO) {
        log.debug("Request to save DispatchBookType : {}", dispatchBookTypeDTO);
        DispatchBookType dispatchBookType = dispatchBookTypeMapper.toEntity(dispatchBookTypeDTO);
        dispatchBookType = dispatchBookTypeRepository.save(dispatchBookType);
        DispatchBookTypeDTO result = dispatchBookTypeMapper.toDto(dispatchBookType);

        try{
//            dispatchBookTypeSearchRepository.save(dispatchBookType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<DispatchBookTypeDTO> partialUpdate(DispatchBookTypeDTO dispatchBookTypeDTO) {
        log.debug("Request to partially update DispatchBookType : {}", dispatchBookTypeDTO);

        return dispatchBookTypeRepository
            .findById(dispatchBookTypeDTO.getId())
            .map(existingDispatchBookType -> {
                dispatchBookTypeMapper.partialUpdate(existingDispatchBookType, dispatchBookTypeDTO);

                return existingDispatchBookType;
            })
            .map(dispatchBookTypeRepository::save)
            .map(savedDispatchBookType -> {
                dispatchBookTypeSearchRepository.save(savedDispatchBookType);

                return savedDispatchBookType;
            })
            .map(dispatchBookTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispatchBookTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispatchBookTypes");
        return dispatchBookTypeRepository.findAll(pageable).map(dispatchBookTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispatchBookTypeDTO> findOne(Long id) {
        log.debug("Request to get DispatchBookType : {}", id);
        return dispatchBookTypeRepository.findById(id).map(dispatchBookTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DispatchBookType : {}", id);
        dispatchBookTypeRepository.deleteById(id);
        dispatchBookTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispatchBookTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DispatchBookTypes for query {}", query);
        return dispatchBookTypeSearchRepository.search(query, pageable).map(dispatchBookTypeMapper::toDto);
    }
}
