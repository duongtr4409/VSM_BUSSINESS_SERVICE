package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.PriceInfo;
import com.vsm.business.repository.PriceInfoRepository;
import com.vsm.business.repository.search.PriceInfoSearchRepository;
import com.vsm.business.service.PriceInfoService;
import com.vsm.business.service.dto.PriceInfoDTO;
import com.vsm.business.service.mapper.PriceInfoMapper;
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
 * Service Implementation for managing {@link PriceInfo}.
 */
@Service
@Transactional
public class PriceInfoServiceImpl implements PriceInfoService {

    private final Logger log = LoggerFactory.getLogger(PriceInfoServiceImpl.class);

    private final PriceInfoRepository priceInfoRepository;

    private final PriceInfoMapper priceInfoMapper;

    private final PriceInfoSearchRepository priceInfoSearchRepository;

    public PriceInfoServiceImpl(
        PriceInfoRepository priceInfoRepository,
        PriceInfoMapper priceInfoMapper,
        PriceInfoSearchRepository priceInfoSearchRepository
    ) {
        this.priceInfoRepository = priceInfoRepository;
        this.priceInfoMapper = priceInfoMapper;
        this.priceInfoSearchRepository = priceInfoSearchRepository;
    }

    @Override
    public PriceInfoDTO save(PriceInfoDTO priceInfoDTO) {
        log.debug("Request to save PriceInfo : {}", priceInfoDTO);
        PriceInfo priceInfo = priceInfoMapper.toEntity(priceInfoDTO);
        priceInfo = priceInfoRepository.save(priceInfo);
        PriceInfoDTO result = priceInfoMapper.toDto(priceInfo);

        try{
//            priceInfoSearchRepository.save(priceInfo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<PriceInfoDTO> partialUpdate(PriceInfoDTO priceInfoDTO) {
        log.debug("Request to partially update PriceInfo : {}", priceInfoDTO);

        return priceInfoRepository
            .findById(priceInfoDTO.getId())
            .map(existingPriceInfo -> {
                priceInfoMapper.partialUpdate(existingPriceInfo, priceInfoDTO);

                return existingPriceInfo;
            })
            .map(priceInfoRepository::save)
            .map(savedPriceInfo -> {
                priceInfoSearchRepository.save(savedPriceInfo);

                return savedPriceInfo;
            })
            .map(priceInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriceInfos");
        return priceInfoRepository.findAll(pageable).map(priceInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PriceInfoDTO> findOne(Long id) {
        log.debug("Request to get PriceInfo : {}", id);
        return priceInfoRepository.findById(id).map(priceInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceInfo : {}", id);
        priceInfoRepository.deleteById(id);
        priceInfoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PriceInfos for query {}", query);
        return priceInfoSearchRepository.search(query, pageable).map(priceInfoMapper::toDto);
    }
}
