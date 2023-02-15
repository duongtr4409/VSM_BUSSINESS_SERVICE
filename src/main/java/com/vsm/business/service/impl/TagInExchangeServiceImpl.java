package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.TagInExchange;
import com.vsm.business.repository.TagInExchangeRepository;
import com.vsm.business.repository.search.TagInExchangeSearchRepository;
import com.vsm.business.service.TagInExchangeService;
import com.vsm.business.service.dto.TagInExchangeDTO;
import com.vsm.business.service.mapper.TagInExchangeMapper;
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
 * Service Implementation for managing {@link TagInExchange}.
 */
@Service
@Transactional
public class TagInExchangeServiceImpl implements TagInExchangeService {

    private final Logger log = LoggerFactory.getLogger(TagInExchangeServiceImpl.class);

    private final TagInExchangeRepository tagInExchangeRepository;

    private final TagInExchangeMapper tagInExchangeMapper;

    private final TagInExchangeSearchRepository tagInExchangeSearchRepository;

    public TagInExchangeServiceImpl(
        TagInExchangeRepository tagInExchangeRepository,
        TagInExchangeMapper tagInExchangeMapper,
        TagInExchangeSearchRepository tagInExchangeSearchRepository
    ) {
        this.tagInExchangeRepository = tagInExchangeRepository;
        this.tagInExchangeMapper = tagInExchangeMapper;
        this.tagInExchangeSearchRepository = tagInExchangeSearchRepository;
    }

    @Override
    public TagInExchangeDTO save(TagInExchangeDTO tagInExchangeDTO) {
        log.debug("Request to save TagInExchange : {}", tagInExchangeDTO);
        TagInExchange tagInExchange = tagInExchangeMapper.toEntity(tagInExchangeDTO);
        tagInExchange = tagInExchangeRepository.save(tagInExchange);
        TagInExchangeDTO result = tagInExchangeMapper.toDto(tagInExchange);
        try{
//            tagInExchangeSearchRepository.save(tagInExchange);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<TagInExchangeDTO> partialUpdate(TagInExchangeDTO tagInExchangeDTO) {
        log.debug("Request to partially update TagInExchange : {}", tagInExchangeDTO);

        return tagInExchangeRepository
            .findById(tagInExchangeDTO.getId())
            .map(existingTagInExchange -> {
                tagInExchangeMapper.partialUpdate(existingTagInExchange, tagInExchangeDTO);

                return existingTagInExchange;
            })
            .map(tagInExchangeRepository::save)
            .map(savedTagInExchange -> {
                tagInExchangeSearchRepository.save(savedTagInExchange);

                return savedTagInExchange;
            })
            .map(tagInExchangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagInExchangeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagInExchanges");
        return tagInExchangeRepository.findAll(pageable).map(tagInExchangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagInExchangeDTO> findOne(Long id) {
        log.debug("Request to get TagInExchange : {}", id);
        return tagInExchangeRepository.findById(id).map(tagInExchangeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagInExchange : {}", id);
        tagInExchangeRepository.deleteById(id);
        tagInExchangeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagInExchangeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TagInExchanges for query {}", query);
        return tagInExchangeSearchRepository.search(query, pageable).map(tagInExchangeMapper::toDto);
    }
}
