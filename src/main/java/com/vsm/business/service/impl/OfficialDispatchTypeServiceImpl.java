package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OfficialDispatchType;
import com.vsm.business.repository.OfficialDispatchTypeRepository;
import com.vsm.business.repository.search.OfficialDispatchTypeSearchRepository;
import com.vsm.business.service.OfficialDispatchTypeService;
import com.vsm.business.service.dto.OfficialDispatchTypeDTO;
import com.vsm.business.service.mapper.OfficialDispatchTypeMapper;
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
 * Service Implementation for managing {@link OfficialDispatchType}.
 */
@Service
@Transactional
public class OfficialDispatchTypeServiceImpl implements OfficialDispatchTypeService {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchTypeServiceImpl.class);

    private final OfficialDispatchTypeRepository officialDispatchTypeRepository;

    private final OfficialDispatchTypeMapper officialDispatchTypeMapper;

    private final OfficialDispatchTypeSearchRepository officialDispatchTypeSearchRepository;

    public OfficialDispatchTypeServiceImpl(
        OfficialDispatchTypeRepository officialDispatchTypeRepository,
        OfficialDispatchTypeMapper officialDispatchTypeMapper,
        OfficialDispatchTypeSearchRepository officialDispatchTypeSearchRepository
    ) {
        this.officialDispatchTypeRepository = officialDispatchTypeRepository;
        this.officialDispatchTypeMapper = officialDispatchTypeMapper;
        this.officialDispatchTypeSearchRepository = officialDispatchTypeSearchRepository;
    }

    @Override
    public OfficialDispatchTypeDTO save(OfficialDispatchTypeDTO officialDispatchTypeDTO) {
        log.debug("Request to save OfficialDispatchType : {}", officialDispatchTypeDTO);
        OfficialDispatchType officialDispatchType = officialDispatchTypeMapper.toEntity(officialDispatchTypeDTO);
        officialDispatchType = officialDispatchTypeRepository.save(officialDispatchType);
        OfficialDispatchTypeDTO result = officialDispatchTypeMapper.toDto(officialDispatchType);

        try{
//            officialDispatchTypeSearchRepository.save(officialDispatchType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<OfficialDispatchTypeDTO> partialUpdate(OfficialDispatchTypeDTO officialDispatchTypeDTO) {
        log.debug("Request to partially update OfficialDispatchType : {}", officialDispatchTypeDTO);

        return officialDispatchTypeRepository
            .findById(officialDispatchTypeDTO.getId())
            .map(existingOfficialDispatchType -> {
                officialDispatchTypeMapper.partialUpdate(existingOfficialDispatchType, officialDispatchTypeDTO);

                return existingOfficialDispatchType;
            })
            .map(officialDispatchTypeRepository::save)
            .map(savedOfficialDispatchType -> {
                officialDispatchTypeSearchRepository.save(savedOfficialDispatchType);

                return savedOfficialDispatchType;
            })
            .map(officialDispatchTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfficialDispatchTypes");
        return officialDispatchTypeRepository.findAll(pageable).map(officialDispatchTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficialDispatchTypeDTO> findOne(Long id) {
        log.debug("Request to get OfficialDispatchType : {}", id);
        return officialDispatchTypeRepository.findById(id).map(officialDispatchTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfficialDispatchType : {}", id);
        officialDispatchTypeRepository.deleteById(id);
        officialDispatchTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfficialDispatchTypes for query {}", query);
        return officialDispatchTypeSearchRepository.search(query, pageable).map(officialDispatchTypeMapper::toDto);
    }
}
