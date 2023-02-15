package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.UserInStep;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.repository.search.UserInStepSearchRepository;
import com.vsm.business.service.UserInStepService;
import com.vsm.business.service.dto.UserInStepDTO;
import com.vsm.business.service.mapper.UserInStepMapper;
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
 * Service Implementation for managing {@link UserInStep}.
 */
@Service
@Transactional
public class UserInStepServiceImpl implements UserInStepService {

    private final Logger log = LoggerFactory.getLogger(UserInStepServiceImpl.class);

    private final UserInStepRepository userInStepRepository;

    private final UserInStepMapper userInStepMapper;

    private final UserInStepSearchRepository userInStepSearchRepository;

    public UserInStepServiceImpl(
        UserInStepRepository userInStepRepository,
        UserInStepMapper userInStepMapper,
        UserInStepSearchRepository userInStepSearchRepository
    ) {
        this.userInStepRepository = userInStepRepository;
        this.userInStepMapper = userInStepMapper;
        this.userInStepSearchRepository = userInStepSearchRepository;
    }

    @Override
    public UserInStepDTO save(UserInStepDTO userInStepDTO) {
        log.debug("Request to save UserInStep : {}", userInStepDTO);
        UserInStep userInStep = userInStepMapper.toEntity(userInStepDTO);
        userInStep = userInStepRepository.save(userInStep);
        UserInStepDTO result = userInStepMapper.toDto(userInStep);
        userInStepSearchRepository.save(userInStep);
        return result;
    }

    @Override
    public Optional<UserInStepDTO> partialUpdate(UserInStepDTO userInStepDTO) {
        log.debug("Request to partially update UserInStep : {}", userInStepDTO);

        return userInStepRepository
            .findById(userInStepDTO.getId())
            .map(existingUserInStep -> {
                userInStepMapper.partialUpdate(existingUserInStep, userInStepDTO);

                return existingUserInStep;
            })
            .map(userInStepRepository::save)
            .map(savedUserInStep -> {
                userInStepSearchRepository.save(savedUserInStep);

                return savedUserInStep;
            })
            .map(userInStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInSteps");
        return userInStepRepository.findAll(pageable).map(userInStepMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInStepDTO> findOne(Long id) {
        log.debug("Request to get UserInStep : {}", id);
        return userInStepRepository.findById(id).map(userInStepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInStep : {}", id);
        userInStepRepository.deleteById(id);
        userInStepSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInStepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserInSteps for query {}", query);
        return userInStepSearchRepository.search(query, pageable).map(userInStepMapper::toDto);
    }
}
