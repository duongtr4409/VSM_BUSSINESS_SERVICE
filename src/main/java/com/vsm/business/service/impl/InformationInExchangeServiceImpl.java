package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.InformationInExchange;
import com.vsm.business.repository.InformationInExchangeRepository;
import com.vsm.business.repository.search.InformationInExchangeSearchRepository;
import com.vsm.business.service.InformationInExchangeService;
import com.vsm.business.service.dto.InformationInExchangeDTO;
import com.vsm.business.service.mapper.InformationInExchangeMapper;
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
 * Service Implementation for managing {@link InformationInExchange}.
 */
@Service
@Transactional
public class InformationInExchangeServiceImpl implements InformationInExchangeService {

    private final Logger log = LoggerFactory.getLogger(InformationInExchangeServiceImpl.class);

    private final InformationInExchangeRepository informationInExchangeRepository;

    private final InformationInExchangeMapper informationInExchangeMapper;

    private final InformationInExchangeSearchRepository informationInExchangeSearchRepository;

    public InformationInExchangeServiceImpl(
        InformationInExchangeRepository informationInExchangeRepository,
        InformationInExchangeMapper informationInExchangeMapper,
        InformationInExchangeSearchRepository informationInExchangeSearchRepository
    ) {
        this.informationInExchangeRepository = informationInExchangeRepository;
        this.informationInExchangeMapper = informationInExchangeMapper;
        this.informationInExchangeSearchRepository = informationInExchangeSearchRepository;
    }

    @Override
    public InformationInExchangeDTO save(InformationInExchangeDTO informationInExchangeDTO) {
        log.debug("Request to save InformationInExchange : {}", informationInExchangeDTO);
        InformationInExchange informationInExchange = informationInExchangeMapper.toEntity(informationInExchangeDTO);
        informationInExchange = informationInExchangeRepository.save(informationInExchange);
        InformationInExchangeDTO result = informationInExchangeMapper.toDto(informationInExchange);
        try{
//            informationInExchangeSearchRepository.save(informationInExchange);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<InformationInExchangeDTO> partialUpdate(InformationInExchangeDTO informationInExchangeDTO) {
        log.debug("Request to partially update InformationInExchange : {}", informationInExchangeDTO);

        return informationInExchangeRepository
            .findById(informationInExchangeDTO.getId())
            .map(existingInformationInExchange -> {
                informationInExchangeMapper.partialUpdate(existingInformationInExchange, informationInExchangeDTO);

                return existingInformationInExchange;
            })
            .map(informationInExchangeRepository::save)
            .map(savedInformationInExchange -> {
                informationInExchangeSearchRepository.save(savedInformationInExchange);

                return savedInformationInExchange;
            })
            .map(informationInExchangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationInExchangeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InformationInExchanges");
        return informationInExchangeRepository.findAll(pageable).map(informationInExchangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InformationInExchangeDTO> findOne(Long id) {
        log.debug("Request to get InformationInExchange : {}", id);
        return informationInExchangeRepository.findById(id).map(informationInExchangeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InformationInExchange : {}", id);
        informationInExchangeRepository.deleteById(id);
        informationInExchangeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationInExchangeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InformationInExchanges for query {}", query);
        return informationInExchangeSearchRepository.search(query, pageable).map(informationInExchangeMapper::toDto);
    }
}
