package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.CurrencyUnit;
import com.vsm.business.repository.CurrencyUnitRepository;
import com.vsm.business.repository.search.CurrencyUnitSearchRepository;
import com.vsm.business.service.CurrencyUnitService;
import com.vsm.business.service.dto.CurrencyUnitDTO;
import com.vsm.business.service.mapper.CurrencyUnitMapper;
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
 * Service Implementation for managing {@link CurrencyUnit}.
 */
@Service
@Transactional
public class CurrencyUnitServiceImpl implements CurrencyUnitService {

    private final Logger log = LoggerFactory.getLogger(CurrencyUnitServiceImpl.class);

    private final CurrencyUnitRepository currencyUnitRepository;

    private final CurrencyUnitMapper currencyUnitMapper;

    private final CurrencyUnitSearchRepository currencyUnitSearchRepository;

    public CurrencyUnitServiceImpl(
        CurrencyUnitRepository currencyUnitRepository,
        CurrencyUnitMapper currencyUnitMapper,
        CurrencyUnitSearchRepository currencyUnitSearchRepository
    ) {
        this.currencyUnitRepository = currencyUnitRepository;
        this.currencyUnitMapper = currencyUnitMapper;
        this.currencyUnitSearchRepository = currencyUnitSearchRepository;
    }

    @Override
    public CurrencyUnitDTO save(CurrencyUnitDTO currencyUnitDTO) {
        log.debug("Request to save CurrencyUnit : {}", currencyUnitDTO);
        CurrencyUnit currencyUnit = currencyUnitMapper.toEntity(currencyUnitDTO);
        currencyUnit = currencyUnitRepository.save(currencyUnit);
        CurrencyUnitDTO result = currencyUnitMapper.toDto(currencyUnit);

        try{
//            currencyUnitSearchRepository.save(currencyUnit);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<CurrencyUnitDTO> partialUpdate(CurrencyUnitDTO currencyUnitDTO) {
        log.debug("Request to partially update CurrencyUnit : {}", currencyUnitDTO);

        return currencyUnitRepository
            .findById(currencyUnitDTO.getId())
            .map(existingCurrencyUnit -> {
                currencyUnitMapper.partialUpdate(existingCurrencyUnit, currencyUnitDTO);

                return existingCurrencyUnit;
            })
            .map(currencyUnitRepository::save)
            .map(savedCurrencyUnit -> {
                currencyUnitSearchRepository.save(savedCurrencyUnit);

                return savedCurrencyUnit;
            })
            .map(currencyUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurrencyUnits");
        return currencyUnitRepository.findAll(pageable).map(currencyUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyUnitDTO> findOne(Long id) {
        log.debug("Request to get CurrencyUnit : {}", id);
        return currencyUnitRepository.findById(id).map(currencyUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrencyUnit : {}", id);
        currencyUnitRepository.deleteById(id);
        currencyUnitSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyUnitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CurrencyUnits for query {}", query);
        return currencyUnitSearchRepository.search(query, pageable).map(currencyUnitMapper::toDto);
    }
}
