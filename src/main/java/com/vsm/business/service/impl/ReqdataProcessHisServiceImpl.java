package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ReqdataProcessHis;
import com.vsm.business.repository.ReqdataProcessHisRepository;
import com.vsm.business.repository.search.ReqdataProcessHisSearchRepository;
import com.vsm.business.service.ReqdataProcessHisService;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;
import com.vsm.business.service.mapper.ReqdataProcessHisMapper;
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
 * Service Implementation for managing {@link ReqdataProcessHis}.
 */
@Service
@Transactional
public class ReqdataProcessHisServiceImpl implements ReqdataProcessHisService {

    private final Logger log = LoggerFactory.getLogger(ReqdataProcessHisServiceImpl.class);

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;

    private final ReqdataProcessHisMapper reqdataProcessHisMapper;

    private final ReqdataProcessHisSearchRepository reqdataProcessHisSearchRepository;

    public ReqdataProcessHisServiceImpl(
        ReqdataProcessHisRepository reqdataProcessHisRepository,
        ReqdataProcessHisMapper reqdataProcessHisMapper,
        ReqdataProcessHisSearchRepository reqdataProcessHisSearchRepository
    ) {
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
        this.reqdataProcessHisMapper = reqdataProcessHisMapper;
        this.reqdataProcessHisSearchRepository = reqdataProcessHisSearchRepository;
    }

    @Override
    public ReqdataProcessHisDTO save(ReqdataProcessHisDTO reqdataProcessHisDTO) {
        log.debug("Request to save ReqdataProcessHis : {}", reqdataProcessHisDTO);
        ReqdataProcessHis reqdataProcessHis = reqdataProcessHisMapper.toEntity(reqdataProcessHisDTO);
        reqdataProcessHis = reqdataProcessHisRepository.save(reqdataProcessHis);
        ReqdataProcessHisDTO result = reqdataProcessHisMapper.toDto(reqdataProcessHis);
        try{
//            reqdataProcessHisSearchRepository.save(reqdataProcessHis);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ReqdataProcessHisDTO> partialUpdate(ReqdataProcessHisDTO reqdataProcessHisDTO) {
        log.debug("Request to partially update ReqdataProcessHis : {}", reqdataProcessHisDTO);

        return reqdataProcessHisRepository
            .findById(reqdataProcessHisDTO.getId())
            .map(existingReqdataProcessHis -> {
                reqdataProcessHisMapper.partialUpdate(existingReqdataProcessHis, reqdataProcessHisDTO);

                return existingReqdataProcessHis;
            })
            .map(reqdataProcessHisRepository::save)
            .map(savedReqdataProcessHis -> {
                reqdataProcessHisSearchRepository.save(savedReqdataProcessHis);

                return savedReqdataProcessHis;
            })
            .map(reqdataProcessHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReqdataProcessHisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReqdataProcessHis");
        return reqdataProcessHisRepository.findAll(pageable).map(reqdataProcessHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReqdataProcessHisDTO> findOne(Long id) {
        log.debug("Request to get ReqdataProcessHis : {}", id);
        return reqdataProcessHisRepository.findById(id).map(reqdataProcessHisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReqdataProcessHis : {}", id);
        reqdataProcessHisRepository.deleteById(id);
        reqdataProcessHisSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReqdataProcessHisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReqdataProcessHis for query {}", query);
        return reqdataProcessHisSearchRepository.search(query, pageable).map(reqdataProcessHisMapper::toDto);
    }
}
