package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RoleOrganization;
import com.vsm.business.repository.RoleOrganizationRepository;
import com.vsm.business.repository.search.RoleOrganizationSearchRepository;
import com.vsm.business.service.RoleOrganizationService;
import com.vsm.business.service.dto.RoleOrganizationDTO;
import com.vsm.business.service.mapper.RoleOrganizationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoleOrganization}.
 */
@Service
@Transactional
public class RoleOrganizationServiceImpl implements RoleOrganizationService {

    private final Logger log = LoggerFactory.getLogger(RoleOrganizationServiceImpl.class);

    private final RoleOrganizationRepository roleOrganizationRepository;

    private final RoleOrganizationMapper roleOrganizationMapper;

    private final RoleOrganizationSearchRepository roleOrganizationSearchRepository;

    public RoleOrganizationServiceImpl(
        RoleOrganizationRepository roleOrganizationRepository,
        RoleOrganizationMapper roleOrganizationMapper,
        RoleOrganizationSearchRepository roleOrganizationSearchRepository
    ) {
        this.roleOrganizationRepository = roleOrganizationRepository;
        this.roleOrganizationMapper = roleOrganizationMapper;
        this.roleOrganizationSearchRepository = roleOrganizationSearchRepository;
    }

    @Override
    public RoleOrganizationDTO save(RoleOrganizationDTO roleOrganizationDTO) {
        log.debug("Request to save RoleOrganization : {}", roleOrganizationDTO);
        RoleOrganization roleOrganization = roleOrganizationMapper.toEntity(roleOrganizationDTO);
        roleOrganization = roleOrganizationRepository.save(roleOrganization);
        RoleOrganizationDTO result = roleOrganizationMapper.toDto(roleOrganization);
        roleOrganizationSearchRepository.save(roleOrganization);
        return result;
    }

    @Override
    public Optional<RoleOrganizationDTO> partialUpdate(RoleOrganizationDTO roleOrganizationDTO) {
        log.debug("Request to partially update RoleOrganization : {}", roleOrganizationDTO);

        return roleOrganizationRepository
            .findById(roleOrganizationDTO.getId())
            .map(existingRoleOrganization -> {
                roleOrganizationMapper.partialUpdate(existingRoleOrganization, roleOrganizationDTO);

                return existingRoleOrganization;
            })
            .map(roleOrganizationRepository::save)
            .map(savedRoleOrganization -> {
                roleOrganizationSearchRepository.save(savedRoleOrganization);

                return savedRoleOrganization;
            })
            .map(roleOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleOrganizations");
        return roleOrganizationRepository.findAll(pageable).map(roleOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleOrganizationDTO> findOne(Long id) {
        log.debug("Request to get RoleOrganization : {}", id);
        return roleOrganizationRepository.findById(id).map(roleOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleOrganization : {}", id);
        roleOrganizationRepository.deleteById(id);
        roleOrganizationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleOrganizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RoleOrganizations for query {}", query);
        return roleOrganizationSearchRepository.search(query, pageable).map(roleOrganizationMapper::toDto);
    }
}
