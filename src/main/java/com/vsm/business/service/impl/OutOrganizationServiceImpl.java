package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OutOrganization;
import com.vsm.business.repository.OutOrganizationRepository;
import com.vsm.business.repository.search.OutOrganizationSearchRepository;
import com.vsm.business.service.OutOrganizationService;
import com.vsm.business.service.dto.OutOrganizationDTO;
import com.vsm.business.service.mapper.OutOrganizationMapper;
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
 * Service Implementation for managing {@link OutOrganization}.
 */
@Service
@Transactional
public class OutOrganizationServiceImpl implements OutOrganizationService {

    private final Logger log = LoggerFactory.getLogger(OutOrganizationServiceImpl.class);

    private final OutOrganizationRepository outOrganizationRepository;

    private final OutOrganizationMapper outOrganizationMapper;

    private final OutOrganizationSearchRepository outOrganizationSearchRepository;

    public OutOrganizationServiceImpl(
        OutOrganizationRepository outOrganizationRepository,
        OutOrganizationMapper outOrganizationMapper,
        OutOrganizationSearchRepository outOrganizationSearchRepository
    ) {
        this.outOrganizationRepository = outOrganizationRepository;
        this.outOrganizationMapper = outOrganizationMapper;
        this.outOrganizationSearchRepository = outOrganizationSearchRepository;
    }

    @Override
    public OutOrganizationDTO save(OutOrganizationDTO outOrganizationDTO) {
        log.debug("Request to save OutOrganization : {}", outOrganizationDTO);
        OutOrganization outOrganization = outOrganizationMapper.toEntity(outOrganizationDTO);
        outOrganization = outOrganizationRepository.save(outOrganization);
        OutOrganizationDTO result = outOrganizationMapper.toDto(outOrganization);
        try{
//            outOrganizationSearchRepository.save(outOrganization);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<OutOrganizationDTO> partialUpdate(OutOrganizationDTO outOrganizationDTO) {
        log.debug("Request to partially update OutOrganization : {}", outOrganizationDTO);

        return outOrganizationRepository
            .findById(outOrganizationDTO.getId())
            .map(existingOutOrganization -> {
                outOrganizationMapper.partialUpdate(existingOutOrganization, outOrganizationDTO);

                return existingOutOrganization;
            })
            .map(outOrganizationRepository::save)
            .map(savedOutOrganization -> {
                outOrganizationSearchRepository.save(savedOutOrganization);

                return savedOutOrganization;
            })
            .map(outOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OutOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OutOrganizations");
        return outOrganizationRepository.findAll(pageable).map(outOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OutOrganizationDTO> findOne(Long id) {
        log.debug("Request to get OutOrganization : {}", id);
        return outOrganizationRepository.findById(id).map(outOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OutOrganization : {}", id);
        outOrganizationRepository.deleteById(id);
        outOrganizationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OutOrganizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OutOrganizations for query {}", query);
        return outOrganizationSearchRepository.search(query, pageable).map(outOrganizationMapper::toDto);
    }
}
