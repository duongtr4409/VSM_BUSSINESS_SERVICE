package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DispatchBook;
import com.vsm.business.repository.DispatchBookRepository;
import com.vsm.business.repository.search.DispatchBookSearchRepository;
import com.vsm.business.service.DispatchBookService;
import com.vsm.business.service.dto.DispatchBookDTO;
import com.vsm.business.service.mapper.DispatchBookMapper;
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
 * Service Implementation for managing {@link DispatchBook}.
 */
@Service
@Transactional
public class DispatchBookServiceImpl implements DispatchBookService {

    private final Logger log = LoggerFactory.getLogger(DispatchBookServiceImpl.class);

    private final DispatchBookRepository dispatchBookRepository;

    private final DispatchBookMapper dispatchBookMapper;

    private final DispatchBookSearchRepository dispatchBookSearchRepository;

    public DispatchBookServiceImpl(
        DispatchBookRepository dispatchBookRepository,
        DispatchBookMapper dispatchBookMapper,
        DispatchBookSearchRepository dispatchBookSearchRepository
    ) {
        this.dispatchBookRepository = dispatchBookRepository;
        this.dispatchBookMapper = dispatchBookMapper;
        this.dispatchBookSearchRepository = dispatchBookSearchRepository;
    }

    @Override
    public DispatchBookDTO save(DispatchBookDTO dispatchBookDTO) {
        log.debug("Request to save DispatchBook : {}", dispatchBookDTO);
        DispatchBook dispatchBook = dispatchBookMapper.toEntity(dispatchBookDTO);
        dispatchBook = dispatchBookRepository.save(dispatchBook);
        DispatchBookDTO result = dispatchBookMapper.toDto(dispatchBook);

        try{
//            dispatchBookSearchRepository.save(dispatchBook);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<DispatchBookDTO> partialUpdate(DispatchBookDTO dispatchBookDTO) {
        log.debug("Request to partially update DispatchBook : {}", dispatchBookDTO);

        return dispatchBookRepository
            .findById(dispatchBookDTO.getId())
            .map(existingDispatchBook -> {
                dispatchBookMapper.partialUpdate(existingDispatchBook, dispatchBookDTO);

                return existingDispatchBook;
            })
            .map(dispatchBookRepository::save)
            .map(savedDispatchBook -> {
                dispatchBookSearchRepository.save(savedDispatchBook);

                return savedDispatchBook;
            })
            .map(dispatchBookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispatchBookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispatchBooks");
        return dispatchBookRepository.findAll(pageable).map(dispatchBookMapper::toDto);
    }

    public Page<DispatchBookDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dispatchBookRepository.findAllWithEagerRelationships(pageable).map(dispatchBookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispatchBookDTO> findOne(Long id) {
        log.debug("Request to get DispatchBook : {}", id);
        return dispatchBookRepository.findOneWithEagerRelationships(id).map(dispatchBookMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DispatchBook : {}", id);
        dispatchBookRepository.deleteById(id);
        dispatchBookSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispatchBookDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DispatchBooks for query {}", query);
        return dispatchBookSearchRepository.search(query, pageable).map(dispatchBookMapper::toDto);
    }
}
