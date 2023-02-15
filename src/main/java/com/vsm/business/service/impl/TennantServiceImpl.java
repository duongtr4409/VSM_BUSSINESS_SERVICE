package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Tennant;
import com.vsm.business.repository.TennantRepository;
import com.vsm.business.repository.search.TennantSearchRepository;
import com.vsm.business.service.TennantService;
import com.vsm.business.service.dto.TennantDTO;
import com.vsm.business.service.mapper.TennantMapper;
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
 * Service Implementation for managing {@link Tennant}.
 */
@Service
@Transactional
public class TennantServiceImpl implements TennantService {

    private final Logger log = LoggerFactory.getLogger(TennantServiceImpl.class);

    private final TennantRepository tennantRepository;

    private final TennantMapper tennantMapper;

    private final TennantSearchRepository tennantSearchRepository;

    public TennantServiceImpl(
        TennantRepository tennantRepository,
        TennantMapper tennantMapper,
        TennantSearchRepository tennantSearchRepository
    ) {
        this.tennantRepository = tennantRepository;
        this.tennantMapper = tennantMapper;
        this.tennantSearchRepository = tennantSearchRepository;
    }

    @Override
    public TennantDTO save(TennantDTO tennantDTO) {
        log.debug("Request to save Tennant : {}", tennantDTO);
        Tennant tennant = tennantMapper.toEntity(tennantDTO);
        tennant = tennantRepository.save(tennant);
        TennantDTO result = tennantMapper.toDto(tennant);
        try{
//            tennantSearchRepository.save(tennant);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<TennantDTO> partialUpdate(TennantDTO tennantDTO) {
        log.debug("Request to partially update Tennant : {}", tennantDTO);

        return tennantRepository
            .findById(tennantDTO.getId())
            .map(existingTennant -> {
                tennantMapper.partialUpdate(existingTennant, tennantDTO);

                return existingTennant;
            })
            .map(tennantRepository::save)
            .map(savedTennant -> {
                tennantSearchRepository.save(savedTennant);

                return savedTennant;
            })
            .map(tennantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TennantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tennants");
        return tennantRepository.findAll(pageable).map(tennantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TennantDTO> findOne(Long id) {
        log.debug("Request to get Tennant : {}", id);
        return tennantRepository.findById(id).map(tennantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tennant : {}", id);
        tennantRepository.deleteById(id);
        tennantSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TennantDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tennants for query {}", query);
        return tennantSearchRepository.search(query, pageable).map(tennantMapper::toDto);
    }
}
