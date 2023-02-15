package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.UserInfoSearchRepository;
import com.vsm.business.service.UserInfoService;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.UserInfoMapper;
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
 * Service Implementation for managing {@link UserInfo}.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    private final UserInfoSearchRepository userInfoSearchRepository;

    public UserInfoServiceImpl(
        UserInfoRepository userInfoRepository,
        UserInfoMapper userInfoMapper,
        UserInfoSearchRepository userInfoSearchRepository
    ) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
        this.userInfoSearchRepository = userInfoSearchRepository;
    }

    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        UserInfoDTO result = userInfoMapper.toDto(userInfo);
        try{
//            userInfoSearchRepository.save(userInfo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<UserInfoDTO> partialUpdate(UserInfoDTO userInfoDTO) {
        log.debug("Request to partially update UserInfo : {}", userInfoDTO);

        return userInfoRepository
            .findById(userInfoDTO.getId())
            .map(existingUserInfo -> {
                userInfoMapper.partialUpdate(existingUserInfo, userInfoDTO);

                return existingUserInfo;
            })
            .map(userInfoRepository::save)
            .map(savedUserInfo -> {
                userInfoSearchRepository.save(savedUserInfo);

                return savedUserInfo;
            })
            .map(userInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable).map(userInfoMapper::toDto);
    }

    public Page<UserInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userInfoRepository.findAllWithEagerRelationships(pageable).map(userInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findOneWithEagerRelationships(id).map(userInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
        userInfoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserInfos for query {}", query);
        return userInfoSearchRepository.search(query, pageable).map(userInfoMapper::toDto);
    }
}
