package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ConstructionCargo;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.repository.search.ConstructionCargoSearchRepository;
import com.vsm.business.service.ConstructionCargoService;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.mapper.ConstructionCargoMapper;
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
 * Service Implementation for managing {@link ConstructionCargo}.
 */
@Service
@Transactional
public class ConstructionCargoServiceImpl implements ConstructionCargoService {

    private final Logger log = LoggerFactory.getLogger(ConstructionCargoServiceImpl.class);

    private final ConstructionCargoRepository constructionCargoRepository;

    private final ConstructionCargoMapper constructionCargoMapper;

    private final ConstructionCargoSearchRepository constructionCargoSearchRepository;

    public ConstructionCargoServiceImpl(
        ConstructionCargoRepository constructionCargoRepository,
        ConstructionCargoMapper constructionCargoMapper,
        ConstructionCargoSearchRepository constructionCargoSearchRepository
    ) {
        this.constructionCargoRepository = constructionCargoRepository;
        this.constructionCargoMapper = constructionCargoMapper;
        this.constructionCargoSearchRepository = constructionCargoSearchRepository;
    }

    @Override
    public ConstructionCargoDTO save(ConstructionCargoDTO constructionCargoDTO) {
        log.debug("Request to save ConstructionCargo : {}", constructionCargoDTO);
        ConstructionCargo constructionCargo = constructionCargoMapper.toEntity(constructionCargoDTO);
        constructionCargo = constructionCargoRepository.save(constructionCargo);
        ConstructionCargoDTO result = constructionCargoMapper.toDto(constructionCargo);
        try{
//        constructionCargoSearchRepository.save(constructionCargo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ConstructionCargoDTO> partialUpdate(ConstructionCargoDTO constructionCargoDTO) {
        log.debug("Request to partially update ConstructionCargo : {}", constructionCargoDTO);

        return constructionCargoRepository
            .findById(constructionCargoDTO.getId())
            .map(existingConstructionCargo -> {
                constructionCargoMapper.partialUpdate(existingConstructionCargo, constructionCargoDTO);

                return existingConstructionCargo;
            })
            .map(constructionCargoRepository::save)
            .map(savedConstructionCargo -> {
                constructionCargoSearchRepository.save(savedConstructionCargo);

                return savedConstructionCargo;
            })
            .map(constructionCargoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConstructionCargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConstructionCargos");
        return constructionCargoRepository.findAll(pageable).map(constructionCargoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConstructionCargoDTO> findOne(Long id) {
        log.debug("Request to get ConstructionCargo : {}", id);
        return constructionCargoRepository.findById(id).map(constructionCargoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConstructionCargo : {}", id);
        constructionCargoRepository.deleteById(id);
        constructionCargoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConstructionCargoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConstructionCargos for query {}", query);
        return constructionCargoSearchRepository.search(query, pageable).map(constructionCargoMapper::toDto);
    }
}
