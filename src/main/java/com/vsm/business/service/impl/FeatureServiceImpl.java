package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Feature;
import com.vsm.business.repository.FeatureRepository;
import com.vsm.business.repository.search.FeatureSearchRepository;
import com.vsm.business.service.FeatureService;
import com.vsm.business.service.dto.FeatureDTO;
import com.vsm.business.service.mapper.FeatureMapper;
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
 * Service Implementation for managing {@link Feature}.
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureServiceImpl.class);

    private final FeatureRepository featureRepository;

    private final FeatureMapper featureMapper;

    private final FeatureSearchRepository featureSearchRepository;

    public FeatureServiceImpl(
        FeatureRepository featureRepository,
        FeatureMapper featureMapper,
        FeatureSearchRepository featureSearchRepository
    ) {
        this.featureRepository = featureRepository;
        this.featureMapper = featureMapper;
        this.featureSearchRepository = featureSearchRepository;
    }

    @Override
    public FeatureDTO save(FeatureDTO featureDTO) {
        log.debug("Request to save Feature : {}", featureDTO);
        Feature feature = featureMapper.toEntity(featureDTO);
        feature = featureRepository.save(feature);
        FeatureDTO result = featureMapper.toDto(feature);
        try{
//        featureSearchRepository.save(feature);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<FeatureDTO> partialUpdate(FeatureDTO featureDTO) {
        log.debug("Request to partially update Feature : {}", featureDTO);

        return featureRepository
            .findById(featureDTO.getId())
            .map(existingFeature -> {
                featureMapper.partialUpdate(existingFeature, featureDTO);

                return existingFeature;
            })
            .map(featureRepository::save)
            .map(savedFeature -> {
                featureSearchRepository.save(savedFeature);

                return savedFeature;
            })
            .map(featureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAll(pageable).map(featureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeatureDTO> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id).map(featureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
        featureSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeatureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Features for query {}", query);
        return featureSearchRepository.search(query, pageable).map(featureMapper::toDto);
    }
}
