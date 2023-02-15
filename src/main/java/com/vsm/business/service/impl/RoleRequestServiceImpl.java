package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RoleRequest;
import com.vsm.business.repository.RoleRequestRepository;
import com.vsm.business.repository.search.RoleRequestSearchRepository;
import com.vsm.business.service.RoleRequestService;
import com.vsm.business.service.dto.RoleRequestDTO;
import com.vsm.business.service.mapper.RoleRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoleRequest}.
 */
@Service
@Transactional
public class RoleRequestServiceImpl implements RoleRequestService {

    private final Logger log = LoggerFactory.getLogger(RoleRequestServiceImpl.class);

    private final RoleRequestRepository roleRequestRepository;

    private final RoleRequestMapper roleRequestMapper;

    private final RoleRequestSearchRepository roleRequestSearchRepository;

    public RoleRequestServiceImpl(
        RoleRequestRepository roleRequestRepository,
        RoleRequestMapper roleRequestMapper,
        RoleRequestSearchRepository roleRequestSearchRepository
    ) {
        this.roleRequestRepository = roleRequestRepository;
        this.roleRequestMapper = roleRequestMapper;
        this.roleRequestSearchRepository = roleRequestSearchRepository;
    }

    @Override
    public RoleRequestDTO save(RoleRequestDTO roleRequestDTO) {
        log.debug("Request to save RoleRequest : {}", roleRequestDTO);
        RoleRequest roleRequest = roleRequestMapper.toEntity(roleRequestDTO);
        roleRequest = roleRequestRepository.save(roleRequest);
        RoleRequestDTO result = roleRequestMapper.toDto(roleRequest);
        roleRequestSearchRepository.save(roleRequest);
        return result;
    }

    @Override
    public Optional<RoleRequestDTO> partialUpdate(RoleRequestDTO roleRequestDTO) {
        log.debug("Request to partially update RoleRequest : {}", roleRequestDTO);

        return roleRequestRepository
            .findById(roleRequestDTO.getId())
            .map(existingRoleRequest -> {
                roleRequestMapper.partialUpdate(existingRoleRequest, roleRequestDTO);

                return existingRoleRequest;
            })
            .map(roleRequestRepository::save)
            .map(savedRoleRequest -> {
                roleRequestSearchRepository.save(savedRoleRequest);

                return savedRoleRequest;
            })
            .map(roleRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleRequests");
        return roleRequestRepository.findAll(pageable).map(roleRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleRequestDTO> findOne(Long id) {
        log.debug("Request to get RoleRequest : {}", id);
        return roleRequestRepository.findById(id).map(roleRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleRequest : {}", id);
        roleRequestRepository.deleteById(id);
        roleRequestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RoleRequests for query {}", query);
        return roleRequestSearchRepository.search(query, pageable).map(roleRequestMapper::toDto);
    }
}
