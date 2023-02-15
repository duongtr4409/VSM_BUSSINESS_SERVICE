package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.Rank;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.search.RankSearchRepository;
import com.vsm.business.service.RankService;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.mapper.RankMapper;
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
 * Service Implementation for managing {@link Rank}.
 */
@Service
@Transactional
public class RankServiceImpl implements RankService {

    private final Logger log = LoggerFactory.getLogger(RankServiceImpl.class);

    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    private final RankSearchRepository rankSearchRepository;

    public RankServiceImpl(RankRepository rankRepository, RankMapper rankMapper, RankSearchRepository rankSearchRepository) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
        this.rankSearchRepository = rankSearchRepository;
    }

    @Override
    public RankDTO save(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        RankDTO result = rankMapper.toDto(rank);
        try{
//            rankSearchRepository.save(rank);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RankDTO> partialUpdate(RankDTO rankDTO) {
        log.debug("Request to partially update Rank : {}", rankDTO);

        return rankRepository
            .findById(rankDTO.getId())
            .map(existingRank -> {
                rankMapper.partialUpdate(existingRank, rankDTO);

                return existingRank;
            })
            .map(rankRepository::save)
            .map(savedRank -> {
                rankSearchRepository.save(savedRank);

                return savedRank;
            })
            .map(rankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ranks");
        return rankRepository.findAll(pageable).map(rankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RankDTO> findOne(Long id) {
        log.debug("Request to get Rank : {}", id);
        return rankRepository.findById(id).map(rankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rank : {}", id);
        rankRepository.deleteById(id);
        rankSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RankDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ranks for query {}", query);
        return rankSearchRepository.search(query, pageable).map(rankMapper::toDto);
    }
}
