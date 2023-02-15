package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.OTP;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.repository.search.OTPSearchRepository;
import com.vsm.business.service.OTPService;
import com.vsm.business.service.dto.OTPDTO;
import com.vsm.business.service.mapper.OTPMapper;
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
 * Service Implementation for managing {@link OTP}.
 */
@Service
@Transactional
public class OTPServiceImpl implements OTPService {

    private final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);

    private final OTPRepository oTPRepository;

    private final OTPMapper oTPMapper;

    private final OTPSearchRepository oTPSearchRepository;

    public OTPServiceImpl(OTPRepository oTPRepository, OTPMapper oTPMapper, OTPSearchRepository oTPSearchRepository) {
        this.oTPRepository = oTPRepository;
        this.oTPMapper = oTPMapper;
        this.oTPSearchRepository = oTPSearchRepository;
    }

    @Override
    public OTPDTO save(OTPDTO oTPDTO) {
        log.debug("Request to save OTP : {}", oTPDTO);
        OTP oTP = oTPMapper.toEntity(oTPDTO);
        oTP = oTPRepository.save(oTP);
        OTPDTO result = oTPMapper.toDto(oTP);

        try{
//            oTPSearchRepository.save(oTP);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<OTPDTO> partialUpdate(OTPDTO oTPDTO) {
        log.debug("Request to partially update OTP : {}", oTPDTO);

        return oTPRepository
            .findById(oTPDTO.getId())
            .map(existingOTP -> {
                oTPMapper.partialUpdate(existingOTP, oTPDTO);

                return existingOTP;
            })
            .map(oTPRepository::save)
            .map(savedOTP -> {
                oTPSearchRepository.save(savedOTP);

                return savedOTP;
            })
            .map(oTPMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OTPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OTPS");
        return oTPRepository.findAll(pageable).map(oTPMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OTPDTO> findOne(Long id) {
        log.debug("Request to get OTP : {}", id);
        return oTPRepository.findById(id).map(oTPMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OTP : {}", id);
        oTPRepository.deleteById(id);
        oTPSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OTPDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OTPS for query {}", query);
        return oTPSearchRepository.search(query, pageable).map(oTPMapper::toDto);
    }
}
