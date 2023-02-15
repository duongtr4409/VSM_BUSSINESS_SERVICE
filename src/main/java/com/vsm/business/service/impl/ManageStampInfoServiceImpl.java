package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.ManageStampInfo;
import com.vsm.business.repository.ManageStampInfoRepository;
import com.vsm.business.repository.search.ManageStampInfoSearchRepository;
import com.vsm.business.service.ManageStampInfoService;
import com.vsm.business.service.dto.ManageStampInfoDTO;
import com.vsm.business.service.mapper.ManageStampInfoMapper;
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
 * Service Implementation for managing {@link ManageStampInfo}.
 */
@Service
@Transactional
public class ManageStampInfoServiceImpl implements ManageStampInfoService {

    private final Logger log = LoggerFactory.getLogger(ManageStampInfoServiceImpl.class);

    private final ManageStampInfoRepository manageStampInfoRepository;

    private final ManageStampInfoMapper manageStampInfoMapper;

    private final ManageStampInfoSearchRepository manageStampInfoSearchRepository;

    public ManageStampInfoServiceImpl(
        ManageStampInfoRepository manageStampInfoRepository,
        ManageStampInfoMapper manageStampInfoMapper,
        ManageStampInfoSearchRepository manageStampInfoSearchRepository
    ) {
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.manageStampInfoMapper = manageStampInfoMapper;
        this.manageStampInfoSearchRepository = manageStampInfoSearchRepository;
    }

    @Override
    public ManageStampInfoDTO save(ManageStampInfoDTO manageStampInfoDTO) {
        log.debug("Request to save ManageStampInfo : {}", manageStampInfoDTO);
        ManageStampInfo manageStampInfo = manageStampInfoMapper.toEntity(manageStampInfoDTO);
        manageStampInfo = manageStampInfoRepository.save(manageStampInfo);
        ManageStampInfoDTO result = manageStampInfoMapper.toDto(manageStampInfo);
        try{
//        manageStampInfoSearchRepository.save(manageStampInfo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<ManageStampInfoDTO> partialUpdate(ManageStampInfoDTO manageStampInfoDTO) {
        log.debug("Request to partially update ManageStampInfo : {}", manageStampInfoDTO);

        return manageStampInfoRepository
            .findById(manageStampInfoDTO.getId())
            .map(existingManageStampInfo -> {
                manageStampInfoMapper.partialUpdate(existingManageStampInfo, manageStampInfoDTO);

                return existingManageStampInfo;
            })
            .map(manageStampInfoRepository::save)
            .map(savedManageStampInfo -> {
                manageStampInfoSearchRepository.save(savedManageStampInfo);

                return savedManageStampInfo;
            })
            .map(manageStampInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManageStampInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ManageStampInfos");
        return manageStampInfoRepository.findAll(pageable).map(manageStampInfoMapper::toDto);
    }

    public Page<ManageStampInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return manageStampInfoRepository.findAllWithEagerRelationships(pageable).map(manageStampInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManageStampInfoDTO> findOne(Long id) {
        log.debug("Request to get ManageStampInfo : {}", id);
        return manageStampInfoRepository.findOneWithEagerRelationships(id).map(manageStampInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManageStampInfo : {}", id);
        manageStampInfoRepository.deleteById(id);
        manageStampInfoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManageStampInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ManageStampInfos for query {}", query);
        return manageStampInfoSearchRepository.search(query, pageable).map(manageStampInfoMapper::toDto);
    }
}
