package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.MallAndStall;
import com.vsm.business.repository.MallAndStallRepository;
import com.vsm.business.repository.search.MallAndStallSearchRepository;
import com.vsm.business.service.MallAndStallService;
import com.vsm.business.service.dto.MallAndStallDTO;
import com.vsm.business.service.mapper.MallAndStallMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MallAndStall}.
 */
@Service
@Transactional
public class MallAndStallServiceImpl implements MallAndStallService {

    private final Logger log = LoggerFactory.getLogger(MallAndStallServiceImpl.class);

    private final MallAndStallRepository mallAndStallRepository;

    private final MallAndStallMapper mallAndStallMapper;

    private final MallAndStallSearchRepository mallAndStallSearchRepository;

    public MallAndStallServiceImpl(
        MallAndStallRepository mallAndStallRepository,
        MallAndStallMapper mallAndStallMapper,
        MallAndStallSearchRepository mallAndStallSearchRepository
    ) {
        this.mallAndStallRepository = mallAndStallRepository;
        this.mallAndStallMapper = mallAndStallMapper;
        this.mallAndStallSearchRepository = mallAndStallSearchRepository;
    }

    @Override
    public MallAndStallDTO save(MallAndStallDTO mallAndStallDTO) {
        log.debug("Request to save MallAndStall : {}", mallAndStallDTO);
        MallAndStall mallAndStall = mallAndStallMapper.toEntity(mallAndStallDTO);
        mallAndStall = mallAndStallRepository.save(mallAndStall);
        MallAndStallDTO result = mallAndStallMapper.toDto(mallAndStall);
        mallAndStallSearchRepository.save(mallAndStall);
        return result;
    }

    @Override
    public Optional<MallAndStallDTO> partialUpdate(MallAndStallDTO mallAndStallDTO) {
        log.debug("Request to partially update MallAndStall : {}", mallAndStallDTO);

        return mallAndStallRepository
            .findById(mallAndStallDTO.getId())
            .map(existingMallAndStall -> {
                mallAndStallMapper.partialUpdate(existingMallAndStall, mallAndStallDTO);

                return existingMallAndStall;
            })
            .map(mallAndStallRepository::save)
            .map(savedMallAndStall -> {
                mallAndStallSearchRepository.save(savedMallAndStall);

                return savedMallAndStall;
            })
            .map(mallAndStallMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MallAndStallDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MallAndStalls");
        return mallAndStallRepository.findAll(pageable).map(mallAndStallMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MallAndStallDTO> findOne(Long id) {
        log.debug("Request to get MallAndStall : {}", id);
        return mallAndStallRepository.findById(id).map(mallAndStallMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MallAndStall : {}", id);
        mallAndStallRepository.deleteById(id);
        mallAndStallSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MallAndStallDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MallAndStalls for query {}", query);
        return mallAndStallSearchRepository.search(query, pageable).map(mallAndStallMapper::toDto);
    }
}
