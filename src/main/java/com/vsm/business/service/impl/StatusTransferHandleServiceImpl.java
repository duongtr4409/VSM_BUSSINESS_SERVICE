package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.StatusTransferHandle;
import com.vsm.business.repository.StatusTransferHandleRepository;
import com.vsm.business.repository.search.StatusTransferHandleSearchRepository;
import com.vsm.business.service.StatusTransferHandleService;
import com.vsm.business.service.dto.StatusTransferHandleDTO;
import com.vsm.business.service.mapper.StatusTransferHandleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusTransferHandle}.
 */
@Service
@Transactional
public class StatusTransferHandleServiceImpl implements StatusTransferHandleService {

    private final Logger log = LoggerFactory.getLogger(StatusTransferHandleServiceImpl.class);

    private final StatusTransferHandleRepository statusTransferHandleRepository;

    private final StatusTransferHandleMapper statusTransferHandleMapper;

    private final StatusTransferHandleSearchRepository statusTransferHandleSearchRepository;

    public StatusTransferHandleServiceImpl(
        StatusTransferHandleRepository statusTransferHandleRepository,
        StatusTransferHandleMapper statusTransferHandleMapper,
        StatusTransferHandleSearchRepository statusTransferHandleSearchRepository
    ) {
        this.statusTransferHandleRepository = statusTransferHandleRepository;
        this.statusTransferHandleMapper = statusTransferHandleMapper;
        this.statusTransferHandleSearchRepository = statusTransferHandleSearchRepository;
    }

    @Override
    public StatusTransferHandleDTO save(StatusTransferHandleDTO statusTransferHandleDTO) {
        log.debug("Request to save StatusTransferHandle : {}", statusTransferHandleDTO);
        StatusTransferHandle statusTransferHandle = statusTransferHandleMapper.toEntity(statusTransferHandleDTO);
        statusTransferHandle = statusTransferHandleRepository.save(statusTransferHandle);
        StatusTransferHandleDTO result = statusTransferHandleMapper.toDto(statusTransferHandle);
        statusTransferHandleSearchRepository.save(statusTransferHandle);
        return result;
    }

    @Override
    public Optional<StatusTransferHandleDTO> partialUpdate(StatusTransferHandleDTO statusTransferHandleDTO) {
        log.debug("Request to partially update StatusTransferHandle : {}", statusTransferHandleDTO);

        return statusTransferHandleRepository
            .findById(statusTransferHandleDTO.getId())
            .map(existingStatusTransferHandle -> {
                statusTransferHandleMapper.partialUpdate(existingStatusTransferHandle, statusTransferHandleDTO);

                return existingStatusTransferHandle;
            })
            .map(statusTransferHandleRepository::save)
            .map(savedStatusTransferHandle -> {
                statusTransferHandleSearchRepository.save(savedStatusTransferHandle);

                return savedStatusTransferHandle;
            })
            .map(statusTransferHandleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusTransferHandleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusTransferHandles");
        return statusTransferHandleRepository.findAll(pageable).map(statusTransferHandleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusTransferHandleDTO> findOne(Long id) {
        log.debug("Request to get StatusTransferHandle : {}", id);
        return statusTransferHandleRepository.findById(id).map(statusTransferHandleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusTransferHandle : {}", id);
        statusTransferHandleRepository.deleteById(id);
        statusTransferHandleSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusTransferHandleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StatusTransferHandles for query {}", query);
        return statusTransferHandleSearchRepository.search(query, pageable).map(statusTransferHandleMapper::toDto);
    }
}
