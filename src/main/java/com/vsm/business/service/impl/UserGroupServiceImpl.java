package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.UserGroup;
import com.vsm.business.repository.UserGroupRepository;
import com.vsm.business.repository.search.UserGroupSearchRepository;
import com.vsm.business.service.UserGroupService;
import com.vsm.business.service.dto.UserGroupDTO;
import com.vsm.business.service.mapper.UserGroupMapper;
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
 * Service Implementation for managing {@link UserGroup}.
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    private final Logger log = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    private final UserGroupRepository userGroupRepository;

    private final UserGroupMapper userGroupMapper;

    private final UserGroupSearchRepository userGroupSearchRepository;

    public UserGroupServiceImpl(
        UserGroupRepository userGroupRepository,
        UserGroupMapper userGroupMapper,
        UserGroupSearchRepository userGroupSearchRepository
    ) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupMapper = userGroupMapper;
        this.userGroupSearchRepository = userGroupSearchRepository;
    }

    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        log.debug("Request to save UserGroup : {}", userGroupDTO);
        UserGroup userGroup = userGroupMapper.toEntity(userGroupDTO);
        userGroup = userGroupRepository.save(userGroup);
        UserGroupDTO result = userGroupMapper.toDto(userGroup);
        try{
//            userGroupSearchRepository.save(userGroup);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<UserGroupDTO> partialUpdate(UserGroupDTO userGroupDTO) {
        log.debug("Request to partially update UserGroup : {}", userGroupDTO);

        return userGroupRepository
            .findById(userGroupDTO.getId())
            .map(existingUserGroup -> {
                userGroupMapper.partialUpdate(existingUserGroup, userGroupDTO);

                return existingUserGroup;
            })
            .map(userGroupRepository::save)
            .map(savedUserGroup -> {
                userGroupSearchRepository.save(savedUserGroup);

                return savedUserGroup;
            })
            .map(userGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroups");
        return userGroupRepository.findAll(pageable).map(userGroupMapper::toDto);
    }

    public Page<UserGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userGroupRepository.findAllWithEagerRelationships(pageable).map(userGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserGroupDTO> findOne(Long id) {
        log.debug("Request to get UserGroup : {}", id);
        return userGroupRepository.findOneWithEagerRelationships(id).map(userGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGroup : {}", id);
        userGroupRepository.deleteById(id);
        userGroupSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserGroups for query {}", query);
        return userGroupSearchRepository.search(query, pageable).map(userGroupMapper::toDto);
    }
}
