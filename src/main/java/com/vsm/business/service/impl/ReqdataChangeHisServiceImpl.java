package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ReqdataChangeHis;
import com.vsm.business.repository.ReqdataChangeHisRepository;
import com.vsm.business.repository.search.ReqdataChangeHisSearchRepository;
import com.vsm.business.service.ReqdataChangeHisService;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;
import com.vsm.business.service.mapper.ReqdataChangeHisMapper;
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
 * Service Implementation for managing {@link ReqdataChangeHis}.
 */
@Service
@Transactional
public class ReqdataChangeHisServiceImpl implements ReqdataChangeHisService {

    private final Logger log = LoggerFactory.getLogger(ReqdataChangeHisServiceImpl.class);

    private final ReqdataChangeHisRepository reqdataChangeHisRepository;

    private final ReqdataChangeHisMapper reqdataChangeHisMapper;

    private final ReqdataChangeHisSearchRepository reqdataChangeHisSearchRepository;

    public ReqdataChangeHisServiceImpl(
        ReqdataChangeHisRepository reqdataChangeHisRepository,
        ReqdataChangeHisMapper reqdataChangeHisMapper,
        ReqdataChangeHisSearchRepository reqdataChangeHisSearchRepository
    ) {
        this.reqdataChangeHisRepository = reqdataChangeHisRepository;
        this.reqdataChangeHisMapper = reqdataChangeHisMapper;
        this.reqdataChangeHisSearchRepository = reqdataChangeHisSearchRepository;
    }

    @Override
    public ReqdataChangeHisDTO save(ReqdataChangeHisDTO reqdataChangeHisDTO) {
        log.debug("Request to save ReqdataChangeHis : {}", reqdataChangeHisDTO);
        ReqdataChangeHis reqdataChangeHis = reqdataChangeHisMapper.toEntity(reqdataChangeHisDTO);
        reqdataChangeHis = reqdataChangeHisRepository.save(reqdataChangeHis);
        ReqdataChangeHisDTO result = reqdataChangeHisMapper.toDto(reqdataChangeHis);
        try{
//            reqdataChangeHisSearchRepository.save(reqdataChangeHis);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ReqdataChangeHisDTO> partialUpdate(ReqdataChangeHisDTO reqdataChangeHisDTO) {
        log.debug("Request to partially update ReqdataChangeHis : {}", reqdataChangeHisDTO);

        return reqdataChangeHisRepository
            .findById(reqdataChangeHisDTO.getId())
            .map(existingReqdataChangeHis -> {
                reqdataChangeHisMapper.partialUpdate(existingReqdataChangeHis, reqdataChangeHisDTO);

                return existingReqdataChangeHis;
            })
            .map(reqdataChangeHisRepository::save)
            .map(savedReqdataChangeHis -> {
                reqdataChangeHisSearchRepository.save(savedReqdataChangeHis);

                return savedReqdataChangeHis;
            })
            .map(reqdataChangeHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReqdataChangeHisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReqdataChangeHis");
        return reqdataChangeHisRepository.findAll(pageable).map(reqdataChangeHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReqdataChangeHisDTO> findOne(Long id) {
        log.debug("Request to get ReqdataChangeHis : {}", id);
        return reqdataChangeHisRepository.findById(id).map(reqdataChangeHisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReqdataChangeHis : {}", id);
        reqdataChangeHisRepository.deleteById(id);
        reqdataChangeHisSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReqdataChangeHisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReqdataChangeHis for query {}", query);
        return reqdataChangeHisSearchRepository.search(query, pageable).map(reqdataChangeHisMapper::toDto);
    }
}
