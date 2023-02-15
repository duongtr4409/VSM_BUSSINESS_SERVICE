package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.repository.BusinessPartnerRepository;
import com.vsm.business.repository.search.BusinessPartnerSearchRepository;
import com.vsm.business.service.BusinessPartnerService;
import com.vsm.business.service.dto.BusinessPartnerDTO;
import com.vsm.business.service.mapper.BusinessPartnerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessPartner}.
 */
@Service
@Transactional
public class BusinessPartnerServiceImpl implements BusinessPartnerService {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerServiceImpl.class);

    private final BusinessPartnerRepository businessPartnerRepository;

    private final BusinessPartnerMapper businessPartnerMapper;

    private final BusinessPartnerSearchRepository businessPartnerSearchRepository;

    public BusinessPartnerServiceImpl(
        BusinessPartnerRepository businessPartnerRepository,
        BusinessPartnerMapper businessPartnerMapper,
        BusinessPartnerSearchRepository businessPartnerSearchRepository
    ) {
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnerMapper = businessPartnerMapper;
        this.businessPartnerSearchRepository = businessPartnerSearchRepository;
    }

    @Override
    public BusinessPartnerDTO save(BusinessPartnerDTO businessPartnerDTO) {
        log.debug("Request to save BusinessPartner : {}", businessPartnerDTO);
        BusinessPartner businessPartner = businessPartnerMapper.toEntity(businessPartnerDTO);
        businessPartner = businessPartnerRepository.save(businessPartner);
        BusinessPartnerDTO result = businessPartnerMapper.toDto(businessPartner);
        businessPartnerSearchRepository.save(businessPartner);
        return result;
    }

    @Override
    public Optional<BusinessPartnerDTO> partialUpdate(BusinessPartnerDTO businessPartnerDTO) {
        log.debug("Request to partially update BusinessPartner : {}", businessPartnerDTO);

        return businessPartnerRepository
            .findById(businessPartnerDTO.getId())
            .map(existingBusinessPartner -> {
                businessPartnerMapper.partialUpdate(existingBusinessPartner, businessPartnerDTO);

                return existingBusinessPartner;
            })
            .map(businessPartnerRepository::save)
            .map(savedBusinessPartner -> {
                businessPartnerSearchRepository.save(savedBusinessPartner);

                return savedBusinessPartner;
            })
            .map(businessPartnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessPartnerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessPartners");
        return businessPartnerRepository.findAll(pageable).map(businessPartnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessPartnerDTO> findOne(Long id) {
        log.debug("Request to get BusinessPartner : {}", id);
        return businessPartnerRepository.findById(id).map(businessPartnerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessPartner : {}", id);
        businessPartnerRepository.deleteById(id);
        businessPartnerSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessPartnerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BusinessPartners for query {}", query);
        return businessPartnerSearchRepository.search(query, pageable).map(businessPartnerMapper::toDto);
    }
}
