package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.DelegateInfo;
import com.vsm.business.repository.DelegateInfoRepository;
import com.vsm.business.repository.search.DelegateInfoSearchRepository;
import com.vsm.business.service.DelegateInfoService;
import com.vsm.business.service.dto.DelegateInfoDTO;
import com.vsm.business.service.mapper.DelegateInfoMapper;
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
 * Service Implementation for managing {@link DelegateInfo}.
 */
@Service
@Transactional
public class DelegateInfoServiceImpl implements DelegateInfoService {

    private final Logger log = LoggerFactory.getLogger(DelegateInfoServiceImpl.class);

    private final DelegateInfoRepository delegateInfoRepository;

    private final DelegateInfoMapper delegateInfoMapper;

    private final DelegateInfoSearchRepository delegateInfoSearchRepository;

    public DelegateInfoServiceImpl(
        DelegateInfoRepository delegateInfoRepository,
        DelegateInfoMapper delegateInfoMapper,
        DelegateInfoSearchRepository delegateInfoSearchRepository
    ) {
        this.delegateInfoRepository = delegateInfoRepository;
        this.delegateInfoMapper = delegateInfoMapper;
        this.delegateInfoSearchRepository = delegateInfoSearchRepository;
    }

    @Override
    public DelegateInfoDTO save(DelegateInfoDTO delegateInfoDTO) {
        log.debug("Request to save DelegateInfo : {}", delegateInfoDTO);
        DelegateInfo delegateInfo = delegateInfoMapper.toEntity(delegateInfoDTO);
        delegateInfo = delegateInfoRepository.save(delegateInfo);
        DelegateInfoDTO result = delegateInfoMapper.toDto(delegateInfo);
        try{
//            delegateInfoSearchRepository.save(delegateInfo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<DelegateInfoDTO> partialUpdate(DelegateInfoDTO delegateInfoDTO) {
        log.debug("Request to partially update DelegateInfo : {}", delegateInfoDTO);

        return delegateInfoRepository
            .findById(delegateInfoDTO.getId())
            .map(existingDelegateInfo -> {
                delegateInfoMapper.partialUpdate(existingDelegateInfo, delegateInfoDTO);

                return existingDelegateInfo;
            })
            .map(delegateInfoRepository::save)
            .map(savedDelegateInfo -> {
                delegateInfoSearchRepository.save(savedDelegateInfo);

                return savedDelegateInfo;
            })
            .map(delegateInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DelegateInfos");
        return delegateInfoRepository.findAll(pageable).map(delegateInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DelegateInfoDTO> findOne(Long id) {
        log.debug("Request to get DelegateInfo : {}", id);
        return delegateInfoRepository.findById(id).map(delegateInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DelegateInfo : {}", id);
        delegateInfoRepository.deleteById(id);
        delegateInfoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DelegateInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DelegateInfos for query {}", query);
        return delegateInfoSearchRepository.search(query, pageable).map(delegateInfoMapper::toDto);
    }
}
