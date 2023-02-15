package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.GoodService;
import com.vsm.business.repository.GoodServiceRepository;
import com.vsm.business.repository.search.GoodServiceSearchRepository;
import com.vsm.business.service.GoodServiceService;
import com.vsm.business.service.dto.GoodServiceDTO;
import com.vsm.business.service.mapper.GoodServiceMapper;
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
 * Service Implementation for managing {@link GoodService}.
 */
@Service
@Transactional
public class GoodServiceServiceImpl implements GoodServiceService {

    private final Logger log = LoggerFactory.getLogger(GoodServiceServiceImpl.class);

    private final GoodServiceRepository goodServiceRepository;

    private final GoodServiceMapper goodServiceMapper;

    private final GoodServiceSearchRepository goodServiceSearchRepository;

    public GoodServiceServiceImpl(
        GoodServiceRepository goodServiceRepository,
        GoodServiceMapper goodServiceMapper,
        GoodServiceSearchRepository goodServiceSearchRepository
    ) {
        this.goodServiceRepository = goodServiceRepository;
        this.goodServiceMapper = goodServiceMapper;
        this.goodServiceSearchRepository = goodServiceSearchRepository;
    }

    @Override
    public GoodServiceDTO save(GoodServiceDTO goodServiceDTO) {
        log.debug("Request to save GoodService : {}", goodServiceDTO);
        GoodService goodService = goodServiceMapper.toEntity(goodServiceDTO);
        goodService = goodServiceRepository.save(goodService);
        GoodServiceDTO result = goodServiceMapper.toDto(goodService);
        try{
//            goodServiceSearchRepository.save(goodService);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }

        return result;
    }

    @Override
    public Optional<GoodServiceDTO> partialUpdate(GoodServiceDTO goodServiceDTO) {
        log.debug("Request to partially update GoodService : {}", goodServiceDTO);

        return goodServiceRepository
            .findById(goodServiceDTO.getId())
            .map(existingGoodService -> {
                goodServiceMapper.partialUpdate(existingGoodService, goodServiceDTO);

                return existingGoodService;
            })
            .map(goodServiceRepository::save)
            .map(savedGoodService -> {
                goodServiceSearchRepository.save(savedGoodService);

                return savedGoodService;
            })
            .map(goodServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoodServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoodServices");
        return goodServiceRepository.findAll(pageable).map(goodServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoodServiceDTO> findOne(Long id) {
        log.debug("Request to get GoodService : {}", id);
        return goodServiceRepository.findById(id).map(goodServiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoodService : {}", id);
        goodServiceRepository.deleteById(id);
        goodServiceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoodServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GoodServices for query {}", query);
        return goodServiceSearchRepository.search(query, pageable).map(goodServiceMapper::toDto);
    }
}
