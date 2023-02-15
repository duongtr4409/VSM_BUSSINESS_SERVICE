package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Role;
import com.vsm.business.repository.RoleRepository;
import com.vsm.business.repository.search.RoleSearchRepository;
import com.vsm.business.service.RoleService;
import com.vsm.business.service.dto.RoleDTO;
import com.vsm.business.service.mapper.RoleMapper;
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
 * Service Implementation for managing {@link Role}.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final RoleSearchRepository roleSearchRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, RoleSearchRepository roleSearchRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.roleSearchRepository = roleSearchRepository;
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        RoleDTO result = roleMapper.toDto(role);
        try{
//            roleSearchRepository.save(role);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RoleDTO> partialUpdate(RoleDTO roleDTO) {
        log.debug("Request to partially update Role : {}", roleDTO);

        return roleRepository
            .findById(roleDTO.getId())
            .map(existingRole -> {
                roleMapper.partialUpdate(existingRole, roleDTO);

                return existingRole;
            })
            .map(roleRepository::save)
            .map(savedRole -> {
                roleSearchRepository.save(savedRole);

                return savedRole;
            })
            .map(roleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleRepository.findAll(pageable).map(roleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDTO> findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        return roleRepository.findById(id).map(roleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.deleteById(id);
        roleSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Roles for query {}", query);
        return roleSearchRepository.search(query, pageable).map(roleMapper::toDto);
    }
}
