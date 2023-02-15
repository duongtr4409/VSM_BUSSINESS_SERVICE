package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Consult;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.repository.search.ConsultSearchRepository;
import com.vsm.business.service.ConsultService;
import com.vsm.business.service.dto.ConsultDTO;
import com.vsm.business.service.mapper.ConsultMapper;
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
 * Service Implementation for managing {@link Consult}.
 */
@Service
@Transactional
public class ConsultServiceImpl implements ConsultService {

    private final Logger log = LoggerFactory.getLogger(ConsultServiceImpl.class);

    private final ConsultRepository consultRepository;

    private final ConsultMapper consultMapper;

    private final ConsultSearchRepository consultSearchRepository;

    public ConsultServiceImpl(
        ConsultRepository consultRepository,
        ConsultMapper consultMapper,
        ConsultSearchRepository consultSearchRepository
    ) {
        this.consultRepository = consultRepository;
        this.consultMapper = consultMapper;
        this.consultSearchRepository = consultSearchRepository;
    }

    @Override
    public ConsultDTO save(ConsultDTO consultDTO) {
        log.debug("Request to save Consult : {}", consultDTO);
        Consult consult = consultMapper.toEntity(consultDTO);
        consult = consultRepository.save(consult);
        ConsultDTO result = consultMapper.toDto(consult);
        try{
//        consultSearchRepository.save(consult);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ConsultDTO> partialUpdate(ConsultDTO consultDTO) {
        log.debug("Request to partially update Consult : {}", consultDTO);

        return consultRepository
            .findById(consultDTO.getId())
            .map(existingConsult -> {
                consultMapper.partialUpdate(existingConsult, consultDTO);

                return existingConsult;
            })
            .map(consultRepository::save)
            .map(savedConsult -> {
                consultSearchRepository.save(savedConsult);

                return savedConsult;
            })
            .map(consultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consults");
        return consultRepository.findAll(pageable).map(consultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultDTO> findOne(Long id) {
        log.debug("Request to get Consult : {}", id);
        return consultRepository.findById(id).map(consultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consult : {}", id);
        consultRepository.deleteById(id);
        consultSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Consults for query {}", query);
        return consultSearchRepository.search(query, pageable).map(consultMapper::toDto);
    }
}
