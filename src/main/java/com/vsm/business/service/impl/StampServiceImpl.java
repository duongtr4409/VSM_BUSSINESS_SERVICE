package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Stamp;
import com.vsm.business.repository.StampRepository;
import com.vsm.business.repository.search.StampSearchRepository;
import com.vsm.business.service.StampService;
import com.vsm.business.service.dto.StampDTO;
import com.vsm.business.service.mapper.StampMapper;
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
 * Service Implementation for managing {@link Stamp}.
 */
@Service
@Transactional
public class StampServiceImpl implements StampService {

    private final Logger log = LoggerFactory.getLogger(StampServiceImpl.class);

    private final StampRepository stampRepository;

    private final StampMapper stampMapper;

    private final StampSearchRepository stampSearchRepository;

    public StampServiceImpl(StampRepository stampRepository, StampMapper stampMapper, StampSearchRepository stampSearchRepository) {
        this.stampRepository = stampRepository;
        this.stampMapper = stampMapper;
        this.stampSearchRepository = stampSearchRepository;
    }

    @Override
    public StampDTO save(StampDTO stampDTO) {
        log.debug("Request to save Stamp : {}", stampDTO);
        Stamp stamp = stampMapper.toEntity(stampDTO);
        stamp = stampRepository.save(stamp);
        StampDTO result = stampMapper.toDto(stamp);

        try{
//            stampSearchRepository.save(stamp);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<StampDTO> partialUpdate(StampDTO stampDTO) {
        log.debug("Request to partially update Stamp : {}", stampDTO);

        return stampRepository
            .findById(stampDTO.getId())
            .map(existingStamp -> {
                stampMapper.partialUpdate(existingStamp, stampDTO);

                return existingStamp;
            })
            .map(stampRepository::save)
            .map(savedStamp -> {
                stampSearchRepository.save(savedStamp);

                return savedStamp;
            })
            .map(stampMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StampDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stamps");
        return stampRepository.findAll(pageable).map(stampMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StampDTO> findOne(Long id) {
        log.debug("Request to get Stamp : {}", id);
        return stampRepository.findById(id).map(stampMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stamp : {}", id);
        stampRepository.deleteById(id);
        stampSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StampDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Stamps for query {}", query);
        return stampSearchRepository.search(query, pageable).map(stampMapper::toDto);
    }
}
