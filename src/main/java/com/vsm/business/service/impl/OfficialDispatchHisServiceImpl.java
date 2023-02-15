package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OfficialDispatchHis;
import com.vsm.business.repository.OfficialDispatchHisRepository;
import com.vsm.business.repository.search.OfficialDispatchHisSearchRepository;
import com.vsm.business.service.OfficialDispatchHisService;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;
import com.vsm.business.service.mapper.OfficialDispatchHisMapper;
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
 * Service Implementation for managing {@link OfficialDispatchHis}.
 */
@Service
@Transactional
public class OfficialDispatchHisServiceImpl implements OfficialDispatchHisService {

    private final Logger log = LoggerFactory.getLogger(OfficialDispatchHisServiceImpl.class);

    private final OfficialDispatchHisRepository officialDispatchHisRepository;

    private final OfficialDispatchHisMapper officialDispatchHisMapper;

    private final OfficialDispatchHisSearchRepository officialDispatchHisSearchRepository;

    public OfficialDispatchHisServiceImpl(
        OfficialDispatchHisRepository officialDispatchHisRepository,
        OfficialDispatchHisMapper officialDispatchHisMapper,
        OfficialDispatchHisSearchRepository officialDispatchHisSearchRepository
    ) {
        this.officialDispatchHisRepository = officialDispatchHisRepository;
        this.officialDispatchHisMapper = officialDispatchHisMapper;
        this.officialDispatchHisSearchRepository = officialDispatchHisSearchRepository;
    }

    @Override
    public OfficialDispatchHisDTO save(OfficialDispatchHisDTO officialDispatchHisDTO) {
        log.debug("Request to save OfficialDispatchHis : {}", officialDispatchHisDTO);
        OfficialDispatchHis officialDispatchHis = officialDispatchHisMapper.toEntity(officialDispatchHisDTO);
        officialDispatchHis = officialDispatchHisRepository.save(officialDispatchHis);
        OfficialDispatchHisDTO result = officialDispatchHisMapper.toDto(officialDispatchHis);

        try{
//            officialDispatchHisSearchRepository.save(officialDispatchHis);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<OfficialDispatchHisDTO> partialUpdate(OfficialDispatchHisDTO officialDispatchHisDTO) {
        log.debug("Request to partially update OfficialDispatchHis : {}", officialDispatchHisDTO);

        return officialDispatchHisRepository
            .findById(officialDispatchHisDTO.getId())
            .map(existingOfficialDispatchHis -> {
                officialDispatchHisMapper.partialUpdate(existingOfficialDispatchHis, officialDispatchHisDTO);

                return existingOfficialDispatchHis;
            })
            .map(officialDispatchHisRepository::save)
            .map(savedOfficialDispatchHis -> {
                officialDispatchHisSearchRepository.save(savedOfficialDispatchHis);

                return savedOfficialDispatchHis;
            })
            .map(officialDispatchHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchHisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfficialDispatchHis");
        return officialDispatchHisRepository.findAll(pageable).map(officialDispatchHisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficialDispatchHisDTO> findOne(Long id) {
        log.debug("Request to get OfficialDispatchHis : {}", id);
        return officialDispatchHisRepository.findById(id).map(officialDispatchHisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OfficialDispatchHis : {}", id);
        officialDispatchHisRepository.deleteById(id);
        officialDispatchHisSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfficialDispatchHisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OfficialDispatchHis for query {}", query);
        return officialDispatchHisSearchRepository.search(query, pageable).map(officialDispatchHisMapper::toDto);
    }
}
