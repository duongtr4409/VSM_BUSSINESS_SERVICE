package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Organization;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.search.OrganizationSearchRepository;
import com.vsm.business.service.OrganizationService;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.mapper.OrganizationMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organization}.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final OrganizationSearchRepository organizationSearchRepository;

    public OrganizationServiceImpl(
        OrganizationRepository organizationRepository,
        OrganizationMapper organizationMapper,
        OrganizationSearchRepository organizationSearchRepository
    ) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.organizationSearchRepository = organizationSearchRepository;
    }

    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        OrganizationDTO result = organizationMapper.toDto(organization);
        try{
//            organizationSearchRepository.save(organization);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<OrganizationDTO> partialUpdate(OrganizationDTO organizationDTO) {
        log.debug("Request to partially update Organization : {}", organizationDTO);

        return organizationRepository
            .findById(organizationDTO.getId())
            .map(existingOrganization -> {
                organizationMapper.partialUpdate(existingOrganization, organizationDTO);

                return existingOrganization;
            })
            .map(organizationRepository::save)
            .map(savedOrganization -> {
                organizationSearchRepository.save(savedOrganization);

                return savedOrganization;
            })
            .map(organizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable).map(organizationMapper::toDto);
    }

    @Override
    public List<OrganizationDTO> findAllWhereLeaderIsNull() {
        return new ArrayList<OrganizationDTO>();
    }

    /**
     *  Get all the organizations where Leader is {@code null}.
     *  @return the list of entities.
     */
//    @Transactional(readOnly = true)
//    public List<OrganizationDTO> findAllWhereLeaderIsNull() {
//        log.debug("Request to get all organizations where Leader is null");
//        return StreamSupport
//            .stream(organizationRepository.findAll().spliterator(), false)
//            .filter(organization -> organization.getLeader() == null)
//            .map(organizationMapper::toDto)
//            .collect(Collectors.toCollection(LinkedList::new));
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationDTO> findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id).map(organizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
        organizationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Organizations for query {}", query);
        return organizationSearchRepository.search(query, pageable).map(organizationMapper::toDto);
    }
}
