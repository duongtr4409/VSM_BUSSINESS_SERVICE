package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.BusinessPartnerType;
import com.vsm.business.repository.BusinessPartnerTypeRepository;
import com.vsm.business.repository.search.BusinessPartnerTypeSearchRepository;
import com.vsm.business.service.BusinessPartnerTypeService;
import com.vsm.business.service.dto.BusinessPartnerTypeDTO;
import com.vsm.business.service.mapper.BusinessPartnerTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessPartnerType}.
 */
@Service
@Transactional
public class BusinessPartnerTypeServiceImpl implements BusinessPartnerTypeService {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerTypeServiceImpl.class);

    private final BusinessPartnerTypeRepository businessPartnerTypeRepository;

    private final BusinessPartnerTypeMapper businessPartnerTypeMapper;

    private final BusinessPartnerTypeSearchRepository businessPartnerTypeSearchRepository;

    public BusinessPartnerTypeServiceImpl(
        BusinessPartnerTypeRepository businessPartnerTypeRepository,
        BusinessPartnerTypeMapper businessPartnerTypeMapper,
        BusinessPartnerTypeSearchRepository businessPartnerTypeSearchRepository
    ) {
        this.businessPartnerTypeRepository = businessPartnerTypeRepository;
        this.businessPartnerTypeMapper = businessPartnerTypeMapper;
        this.businessPartnerTypeSearchRepository = businessPartnerTypeSearchRepository;
    }

    @Override
    public BusinessPartnerTypeDTO save(BusinessPartnerTypeDTO businessPartnerTypeDTO) {
        log.debug("Request to save BusinessPartnerType : {}", businessPartnerTypeDTO);
        BusinessPartnerType businessPartnerType = businessPartnerTypeMapper.toEntity(businessPartnerTypeDTO);
        businessPartnerType = businessPartnerTypeRepository.save(businessPartnerType);
        BusinessPartnerTypeDTO result = businessPartnerTypeMapper.toDto(businessPartnerType);
        businessPartnerTypeSearchRepository.save(businessPartnerType);
        return result;
    }

    @Override
    public Optional<BusinessPartnerTypeDTO> partialUpdate(BusinessPartnerTypeDTO businessPartnerTypeDTO) {
        log.debug("Request to partially update BusinessPartnerType : {}", businessPartnerTypeDTO);

        return businessPartnerTypeRepository
            .findById(businessPartnerTypeDTO.getId())
            .map(existingBusinessPartnerType -> {
                businessPartnerTypeMapper.partialUpdate(existingBusinessPartnerType, businessPartnerTypeDTO);

                return existingBusinessPartnerType;
            })
            .map(businessPartnerTypeRepository::save)
            .map(savedBusinessPartnerType -> {
                businessPartnerTypeSearchRepository.save(savedBusinessPartnerType);

                return savedBusinessPartnerType;
            })
            .map(businessPartnerTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessPartnerTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessPartnerTypes");
        return businessPartnerTypeRepository.findAll(pageable).map(businessPartnerTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessPartnerTypeDTO> findOne(Long id) {
        log.debug("Request to get BusinessPartnerType : {}", id);
        return businessPartnerTypeRepository.findById(id).map(businessPartnerTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessPartnerType : {}", id);
        businessPartnerTypeRepository.deleteById(id);
        businessPartnerTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessPartnerTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BusinessPartnerTypes for query {}", query);
        return businessPartnerTypeSearchRepository.search(query, pageable).map(businessPartnerTypeMapper::toDto);
    }
}
