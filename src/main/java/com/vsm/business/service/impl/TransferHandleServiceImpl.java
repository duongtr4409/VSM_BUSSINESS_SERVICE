package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.TransferHandle;
import com.vsm.business.repository.TransferHandleRepository;
import com.vsm.business.repository.search.TransferHandleSearchRepository;
import com.vsm.business.service.TransferHandleService;
import com.vsm.business.service.dto.TransferHandleDTO;
import com.vsm.business.service.mapper.TransferHandleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferHandle}.
 */
@Service
@Transactional
public class TransferHandleServiceImpl implements TransferHandleService {

    private final Logger log = LoggerFactory.getLogger(TransferHandleServiceImpl.class);

    private final TransferHandleRepository transferHandleRepository;

    private final TransferHandleMapper transferHandleMapper;

    private final TransferHandleSearchRepository transferHandleSearchRepository;

    public TransferHandleServiceImpl(
        TransferHandleRepository transferHandleRepository,
        TransferHandleMapper transferHandleMapper,
        TransferHandleSearchRepository transferHandleSearchRepository
    ) {
        this.transferHandleRepository = transferHandleRepository;
        this.transferHandleMapper = transferHandleMapper;
        this.transferHandleSearchRepository = transferHandleSearchRepository;
    }

    @Override
    public TransferHandleDTO save(TransferHandleDTO transferHandleDTO) {
        log.debug("Request to save TransferHandle : {}", transferHandleDTO);
        TransferHandle transferHandle = transferHandleMapper.toEntity(transferHandleDTO);
        transferHandle = transferHandleRepository.save(transferHandle);
        TransferHandleDTO result = transferHandleMapper.toDto(transferHandle);
        transferHandleSearchRepository.save(transferHandle);
        return result;
    }

    @Override
    public Optional<TransferHandleDTO> partialUpdate(TransferHandleDTO transferHandleDTO) {
        log.debug("Request to partially update TransferHandle : {}", transferHandleDTO);

        return transferHandleRepository
            .findById(transferHandleDTO.getId())
            .map(existingTransferHandle -> {
                transferHandleMapper.partialUpdate(existingTransferHandle, transferHandleDTO);

                return existingTransferHandle;
            })
            .map(transferHandleRepository::save)
            .map(savedTransferHandle -> {
                transferHandleSearchRepository.save(savedTransferHandle);

                return savedTransferHandle;
            })
            .map(transferHandleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferHandleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferHandles");
        return transferHandleRepository.findAll(pageable).map(transferHandleMapper::toDto);
    }

    public Page<TransferHandleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transferHandleRepository.findAllWithEagerRelationships(pageable).map(transferHandleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransferHandleDTO> findOne(Long id) {
        log.debug("Request to get TransferHandle : {}", id);
        return transferHandleRepository.findOneWithEagerRelationships(id).map(transferHandleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransferHandle : {}", id);
        transferHandleRepository.deleteById(id);
        transferHandleSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferHandleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransferHandles for query {}", query);
        return transferHandleSearchRepository.search(query, pageable).map(transferHandleMapper::toDto);
    }
}
