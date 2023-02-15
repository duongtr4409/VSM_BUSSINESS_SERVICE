package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Examine;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.repository.search.ExamineSearchRepository;
import com.vsm.business.service.ExamineService;
import com.vsm.business.service.dto.ExamineDTO;
import com.vsm.business.service.mapper.ExamineMapper;
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
 * Service Implementation for managing {@link Examine}.
 */
@Service
@Transactional
public class ExamineServiceImpl implements ExamineService {

    private final Logger log = LoggerFactory.getLogger(ExamineServiceImpl.class);

    private final ExamineRepository examineRepository;

    private final ExamineMapper examineMapper;

    private final ExamineSearchRepository examineSearchRepository;

    public ExamineServiceImpl(
        ExamineRepository examineRepository,
        ExamineMapper examineMapper,
        ExamineSearchRepository examineSearchRepository
    ) {
        this.examineRepository = examineRepository;
        this.examineMapper = examineMapper;
        this.examineSearchRepository = examineSearchRepository;
    }

    @Override
    public ExamineDTO save(ExamineDTO examineDTO) {
        log.debug("Request to save Examine : {}", examineDTO);
        Examine examine = examineMapper.toEntity(examineDTO);
        examine = examineRepository.save(examine);
        ExamineDTO result = examineMapper.toDto(examine);
        try{
//            examineSearchRepository.save(examine);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<ExamineDTO> partialUpdate(ExamineDTO examineDTO) {
        log.debug("Request to partially update Examine : {}", examineDTO);

        return examineRepository
            .findById(examineDTO.getId())
            .map(existingExamine -> {
                examineMapper.partialUpdate(existingExamine, examineDTO);

                return existingExamine;
            })
            .map(examineRepository::save)
            .map(savedExamine -> {
                examineSearchRepository.save(savedExamine);

                return savedExamine;
            })
            .map(examineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examines");
        return examineRepository.findAll(pageable).map(examineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamineDTO> findOne(Long id) {
        log.debug("Request to get Examine : {}", id);
        return examineRepository.findById(id).map(examineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Examine : {}", id);
        examineRepository.deleteById(id);
        examineSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Examines for query {}", query);
        return examineSearchRepository.search(query, pageable).map(examineMapper::toDto);
    }
}
