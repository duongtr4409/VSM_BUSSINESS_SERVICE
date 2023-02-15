package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Vendor;
import com.vsm.business.repository.VendorRepository;
import com.vsm.business.repository.search.VendorSearchRepository;
import com.vsm.business.service.VendorService;
import com.vsm.business.service.dto.VendorDTO;
import com.vsm.business.service.mapper.VendorMapper;
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
 * Service Implementation for managing {@link Vendor}.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    private final VendorSearchRepository vendorSearchRepository;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper, VendorSearchRepository vendorSearchRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
        this.vendorSearchRepository = vendorSearchRepository;
    }

    @Override
    public VendorDTO save(VendorDTO vendorDTO) {
        log.debug("Request to save Vendor : {}", vendorDTO);
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        VendorDTO result = vendorMapper.toDto(vendor);

        try{
//            vendorSearchRepository.save(vendor);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<VendorDTO> partialUpdate(VendorDTO vendorDTO) {
        log.debug("Request to partially update Vendor : {}", vendorDTO);

        return vendorRepository
            .findById(vendorDTO.getId())
            .map(existingVendor -> {
                vendorMapper.partialUpdate(existingVendor, vendorDTO);

                return existingVendor;
            })
            .map(vendorRepository::save)
            .map(savedVendor -> {
                vendorSearchRepository.save(savedVendor);

                return savedVendor;
            })
            .map(vendorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll(pageable).map(vendorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendorDTO> findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        return vendorRepository.findById(id).map(vendorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.deleteById(id);
        vendorSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VendorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vendors for query {}", query);
        return vendorSearchRepository.search(query, pageable).map(vendorMapper::toDto);
    }
}
