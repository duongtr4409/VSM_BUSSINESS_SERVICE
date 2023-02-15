package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.CentralizedShopping;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.repository.search.CentralizedShoppingSearchRepository;
import com.vsm.business.service.CentralizedShoppingService;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import com.vsm.business.service.mapper.CentralizedShoppingMapper;
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
 * Service Implementation for managing {@link CentralizedShopping}.
 */
@Service
@Transactional
public class CentralizedShoppingServiceImpl implements CentralizedShoppingService {

    private final Logger log = LoggerFactory.getLogger(CentralizedShoppingServiceImpl.class);

    private final CentralizedShoppingRepository centralizedShoppingRepository;

    private final CentralizedShoppingMapper centralizedShoppingMapper;

    private final CentralizedShoppingSearchRepository centralizedShoppingSearchRepository;

    public CentralizedShoppingServiceImpl(
        CentralizedShoppingRepository centralizedShoppingRepository,
        CentralizedShoppingMapper centralizedShoppingMapper,
        CentralizedShoppingSearchRepository centralizedShoppingSearchRepository
    ) {
        this.centralizedShoppingRepository = centralizedShoppingRepository;
        this.centralizedShoppingMapper = centralizedShoppingMapper;
        this.centralizedShoppingSearchRepository = centralizedShoppingSearchRepository;
    }

    @Override
    public CentralizedShoppingDTO save(CentralizedShoppingDTO centralizedShoppingDTO) {
        log.debug("Request to save CentralizedShopping : {}", centralizedShoppingDTO);
        CentralizedShopping centralizedShopping = centralizedShoppingMapper.toEntity(centralizedShoppingDTO);
        centralizedShopping = centralizedShoppingRepository.save(centralizedShopping);
        CentralizedShoppingDTO result = centralizedShoppingMapper.toDto(centralizedShopping);
        try{
//        centralizedShoppingSearchRepository.save(centralizedShopping);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<CentralizedShoppingDTO> partialUpdate(CentralizedShoppingDTO centralizedShoppingDTO) {
        log.debug("Request to partially update CentralizedShopping : {}", centralizedShoppingDTO);

        return centralizedShoppingRepository
            .findById(centralizedShoppingDTO.getId())
            .map(existingCentralizedShopping -> {
                centralizedShoppingMapper.partialUpdate(existingCentralizedShopping, centralizedShoppingDTO);

                return existingCentralizedShopping;
            })
            .map(centralizedShoppingRepository::save)
            .map(savedCentralizedShopping -> {
                centralizedShoppingSearchRepository.save(savedCentralizedShopping);

                return savedCentralizedShopping;
            })
            .map(centralizedShoppingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CentralizedShoppingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentralizedShoppings");
        return centralizedShoppingRepository.findAll(pageable).map(centralizedShoppingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentralizedShoppingDTO> findOne(Long id) {
        log.debug("Request to get CentralizedShopping : {}", id);
        return centralizedShoppingRepository.findById(id).map(centralizedShoppingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CentralizedShopping : {}", id);
        centralizedShoppingRepository.deleteById(id);
        centralizedShoppingSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CentralizedShoppingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CentralizedShoppings for query {}", query);
        return centralizedShoppingSearchRepository.search(query, pageable).map(centralizedShoppingMapper::toDto);
    }
}
