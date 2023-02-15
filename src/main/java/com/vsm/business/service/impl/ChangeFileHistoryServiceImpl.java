package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ChangeFileHistory;
import com.vsm.business.repository.ChangeFileHistoryRepository;
import com.vsm.business.repository.search.ChangeFileHistorySearchRepository;
import com.vsm.business.service.ChangeFileHistoryService;
import com.vsm.business.service.dto.ChangeFileHistoryDTO;
import com.vsm.business.service.mapper.ChangeFileHistoryMapper;
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
 * Service Implementation for managing {@link ChangeFileHistory}.
 */
@Service
@Transactional
public class ChangeFileHistoryServiceImpl implements ChangeFileHistoryService {

    private final Logger log = LoggerFactory.getLogger(ChangeFileHistoryServiceImpl.class);

    private final ChangeFileHistoryRepository changeFileHistoryRepository;

    private final ChangeFileHistoryMapper changeFileHistoryMapper;

    private final ChangeFileHistorySearchRepository changeFileHistorySearchRepository;

    public ChangeFileHistoryServiceImpl(
        ChangeFileHistoryRepository changeFileHistoryRepository,
        ChangeFileHistoryMapper changeFileHistoryMapper,
        ChangeFileHistorySearchRepository changeFileHistorySearchRepository
    ) {
        this.changeFileHistoryRepository = changeFileHistoryRepository;
        this.changeFileHistoryMapper = changeFileHistoryMapper;
        this.changeFileHistorySearchRepository = changeFileHistorySearchRepository;
    }

    @Override
    public ChangeFileHistoryDTO save(ChangeFileHistoryDTO changeFileHistoryDTO) {
        log.debug("Request to save ChangeFileHistory : {}", changeFileHistoryDTO);
        ChangeFileHistory changeFileHistory = changeFileHistoryMapper.toEntity(changeFileHistoryDTO);
        changeFileHistory = changeFileHistoryRepository.save(changeFileHistory);
        ChangeFileHistoryDTO result = changeFileHistoryMapper.toDto(changeFileHistory);
        try{
//            changeFileHistorySearchRepository.save(changeFileHistory);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ChangeFileHistoryDTO> partialUpdate(ChangeFileHistoryDTO changeFileHistoryDTO) {
        log.debug("Request to partially update ChangeFileHistory : {}", changeFileHistoryDTO);

        return changeFileHistoryRepository
            .findById(changeFileHistoryDTO.getId())
            .map(existingChangeFileHistory -> {
                changeFileHistoryMapper.partialUpdate(existingChangeFileHistory, changeFileHistoryDTO);

                return existingChangeFileHistory;
            })
            .map(changeFileHistoryRepository::save)
            .map(savedChangeFileHistory -> {
                changeFileHistorySearchRepository.save(savedChangeFileHistory);

                return savedChangeFileHistory;
            })
            .map(changeFileHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChangeFileHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChangeFileHistories");
        return changeFileHistoryRepository.findAll(pageable).map(changeFileHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChangeFileHistoryDTO> findOne(Long id) {
        log.debug("Request to get ChangeFileHistory : {}", id);
        return changeFileHistoryRepository.findById(id).map(changeFileHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChangeFileHistory : {}", id);
        changeFileHistoryRepository.deleteById(id);
        changeFileHistorySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChangeFileHistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChangeFileHistories for query {}", query);
        return changeFileHistorySearchRepository.search(query, pageable).map(changeFileHistoryMapper::toDto);
    }
}
