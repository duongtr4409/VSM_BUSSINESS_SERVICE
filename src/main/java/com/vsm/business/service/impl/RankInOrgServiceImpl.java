package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RankInOrg;
import com.vsm.business.repository.RankInOrgRepository;
import com.vsm.business.repository.search.RankInOrgSearchRepository;
import com.vsm.business.service.RankInOrgService;
import com.vsm.business.service.dto.RankInOrgDTO;
import com.vsm.business.service.mapper.RankInOrgMapper;
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
 * Service Implementation for managing {@link RankInOrg}.
 */
@Service
@Transactional
public class RankInOrgServiceImpl implements RankInOrgService {

    private final Logger log = LoggerFactory.getLogger(RankInOrgServiceImpl.class);

    private final RankInOrgRepository rankInOrgRepository;

    private final RankInOrgMapper rankInOrgMapper;

    private final RankInOrgSearchRepository rankInOrgSearchRepository;

    public RankInOrgServiceImpl(
        RankInOrgRepository rankInOrgRepository,
        RankInOrgMapper rankInOrgMapper,
        RankInOrgSearchRepository rankInOrgSearchRepository
    ) {
        this.rankInOrgRepository = rankInOrgRepository;
        this.rankInOrgMapper = rankInOrgMapper;
        this.rankInOrgSearchRepository = rankInOrgSearchRepository;
    }

    @Override
    public RankInOrgDTO save(RankInOrgDTO rankInOrgDTO) {
        log.debug("Request to save RankInOrg : {}", rankInOrgDTO);
        RankInOrg rankInOrg = rankInOrgMapper.toEntity(rankInOrgDTO);
        rankInOrg = rankInOrgRepository.save(rankInOrg);
        RankInOrgDTO result = rankInOrgMapper.toDto(rankInOrg);
        try{
//            rankInOrgSearchRepository.save(rankInOrg);
        }catch (StackOverflowError | UncategorizedElasticsearchException  | GenericJDBCException e){

        }
        return result;
    }

    @Override
    public Optional<RankInOrgDTO> partialUpdate(RankInOrgDTO rankInOrgDTO) {
        log.debug("Request to partially update RankInOrg : {}", rankInOrgDTO);

        return rankInOrgRepository
            .findById(rankInOrgDTO.getId())
            .map(existingRankInOrg -> {
                rankInOrgMapper.partialUpdate(existingRankInOrg, rankInOrgDTO);

                return existingRankInOrg;
            })
            .map(rankInOrgRepository::save)
            .map(savedRankInOrg -> {
                rankInOrgSearchRepository.save(savedRankInOrg);

                return savedRankInOrg;
            })
            .map(rankInOrgMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RankInOrgDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RankInOrgs");
        return rankInOrgRepository.findAll(pageable).map(rankInOrgMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RankInOrgDTO> findOne(Long id) {
        log.debug("Request to get RankInOrg : {}", id);
        return rankInOrgRepository.findById(id).map(rankInOrgMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RankInOrg : {}", id);
        rankInOrgRepository.deleteById(id);
        rankInOrgSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RankInOrgDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RankInOrgs for query {}", query);
        return rankInOrgSearchRepository.search(query, pageable).map(rankInOrgMapper::toDto);
    }
}
