package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.GoodServiceType;
import com.vsm.business.repository.GoodServiceTypeRepository;
import com.vsm.business.repository.search.GoodServiceTypeSearchRepository;
import com.vsm.business.service.GoodServiceTypeService;
import com.vsm.business.service.dto.GoodServiceTypeDTO;
import com.vsm.business.service.mapper.GoodServiceTypeMapper;
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
 * Service Implementation for managing {@link GoodServiceType}.
 */
@Service
@Transactional
public class GoodServiceTypeServiceImpl implements GoodServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(GoodServiceTypeServiceImpl.class);

    private final GoodServiceTypeRepository goodServiceTypeRepository;

    private final GoodServiceTypeMapper goodServiceTypeMapper;

    private final GoodServiceTypeSearchRepository goodServiceTypeSearchRepository;

    public GoodServiceTypeServiceImpl(
        GoodServiceTypeRepository goodServiceTypeRepository,
        GoodServiceTypeMapper goodServiceTypeMapper,
        GoodServiceTypeSearchRepository goodServiceTypeSearchRepository
    ) {
        this.goodServiceTypeRepository = goodServiceTypeRepository;
        this.goodServiceTypeMapper = goodServiceTypeMapper;
        this.goodServiceTypeSearchRepository = goodServiceTypeSearchRepository;
    }

    @Override
    public GoodServiceTypeDTO save(GoodServiceTypeDTO goodServiceTypeDTO) {
        log.debug("Request to save GoodServiceType : {}", goodServiceTypeDTO);
        GoodServiceType goodServiceType = goodServiceTypeMapper.toEntity(goodServiceTypeDTO);
        goodServiceType = goodServiceTypeRepository.save(goodServiceType);
        GoodServiceTypeDTO result = goodServiceTypeMapper.toDto(goodServiceType);
        try{
//            goodServiceTypeSearchRepository.save(goodServiceType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<GoodServiceTypeDTO> partialUpdate(GoodServiceTypeDTO goodServiceTypeDTO) {
        log.debug("Request to partially update GoodServiceType : {}", goodServiceTypeDTO);

        return goodServiceTypeRepository
            .findById(goodServiceTypeDTO.getId())
            .map(existingGoodServiceType -> {
                goodServiceTypeMapper.partialUpdate(existingGoodServiceType, goodServiceTypeDTO);

                return existingGoodServiceType;
            })
            .map(goodServiceTypeRepository::save)
            .map(savedGoodServiceType -> {
                goodServiceTypeSearchRepository.save(savedGoodServiceType);

                return savedGoodServiceType;
            })
            .map(goodServiceTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoodServiceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoodServiceTypes");
        return goodServiceTypeRepository.findAll(pageable).map(goodServiceTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoodServiceTypeDTO> findOne(Long id) {
        log.debug("Request to get GoodServiceType : {}", id);
        return goodServiceTypeRepository.findById(id).map(goodServiceTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoodServiceType : {}", id);
        goodServiceTypeRepository.deleteById(id);
        goodServiceTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoodServiceTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GoodServiceTypes for query {}", query);
        return goodServiceTypeSearchRepository.search(query, pageable).map(goodServiceTypeMapper::toDto);
    }
}
