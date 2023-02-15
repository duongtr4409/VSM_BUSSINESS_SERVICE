package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.MECargo;
import com.vsm.business.repository.MECargoRepository;
import com.vsm.business.repository.search.MECargoSearchRepository;
import com.vsm.business.service.MECargoService;
import com.vsm.business.service.dto.MECargoDTO;
import com.vsm.business.service.mapper.MECargoMapper;
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
 * Service Implementation for managing {@link MECargo}.
 */
@Service
@Transactional
public class MECargoServiceImpl implements MECargoService {

    private final Logger log = LoggerFactory.getLogger(MECargoServiceImpl.class);

    private final MECargoRepository mECargoRepository;

    private final MECargoMapper mECargoMapper;

    private final MECargoSearchRepository mECargoSearchRepository;

    public MECargoServiceImpl(
        MECargoRepository mECargoRepository,
        MECargoMapper mECargoMapper,
        MECargoSearchRepository mECargoSearchRepository
    ) {
        this.mECargoRepository = mECargoRepository;
        this.mECargoMapper = mECargoMapper;
        this.mECargoSearchRepository = mECargoSearchRepository;
    }

    @Override
    public MECargoDTO save(MECargoDTO mECargoDTO) {
        log.debug("Request to save MECargo : {}", mECargoDTO);
        MECargo mECargo = mECargoMapper.toEntity(mECargoDTO);
        mECargo = mECargoRepository.save(mECargo);
        MECargoDTO result = mECargoMapper.toDto(mECargo);
        try{
//        mECargoSearchRepository.save(mECargo);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<MECargoDTO> partialUpdate(MECargoDTO mECargoDTO) {
        log.debug("Request to partially update MECargo : {}", mECargoDTO);

        return mECargoRepository
            .findById(mECargoDTO.getId())
            .map(existingMECargo -> {
                mECargoMapper.partialUpdate(existingMECargo, mECargoDTO);

                return existingMECargo;
            })
            .map(mECargoRepository::save)
            .map(savedMECargo -> {
                mECargoSearchRepository.save(savedMECargo);

                return savedMECargo;
            })
            .map(mECargoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MECargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MECargos");
        return mECargoRepository.findAll(pageable).map(mECargoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MECargoDTO> findOne(Long id) {
        log.debug("Request to get MECargo : {}", id);
        return mECargoRepository.findById(id).map(mECargoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MECargo : {}", id);
        mECargoRepository.deleteById(id);
        mECargoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MECargoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MECargos for query {}", query);
        return mECargoSearchRepository.search(query, pageable).map(mECargoMapper::toDto);
    }
}
