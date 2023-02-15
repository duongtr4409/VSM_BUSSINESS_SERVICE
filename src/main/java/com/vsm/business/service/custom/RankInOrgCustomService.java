package com.vsm.business.service.custom;

import com.vsm.business.domain.RankInOrg;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.RankInOrgRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.RankInOrgSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.RankInOrgDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.RankInOrgMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankInOrgCustomService {
    private final Logger log = LoggerFactory.getLogger(RankInOrgCustomService.class);

    private RankInOrgRepository rankInOrgRepository;

    private RankInOrgSearchRepository rankInOrgSearchRepository;

    private RankInOrgMapper rankInOrgMapper;

    public RankInOrgCustomService(RankInOrgRepository rankInOrgRepository, RankInOrgSearchRepository rankInOrgSearchRepository, RankInOrgMapper rankInOrgMapper) {
        this.rankInOrgRepository = rankInOrgRepository;
        this.rankInOrgSearchRepository = rankInOrgSearchRepository;
        this.rankInOrgMapper = rankInOrgMapper;
    }

    public List<RankInOrgDTO> getAll() {
        log.debug("RankInOrgCustomService: getAll()");
        List<RankInOrgDTO> result = new ArrayList<>();
        try {
            List<RankInOrg> rankInOrgs = this.rankInOrgRepository.findAll();
            for (RankInOrg rankInOrg :
                rankInOrgs) {
                RankInOrgDTO rankInOrgDTO = rankInOrgMapper.toDto(rankInOrg);
                result.add(rankInOrgDTO);
            }
        }catch (Exception e){
            log.error("RankInOrgCustomService: getAll() {}", e);
        }
        log.debug("RankInOrgCustomService: getAll() {}", result);
        return result;
    }

    public List<RankInOrgDTO> deleteAll(List<RankInOrgDTO> rankInOrgDTOS) {
        log.debug("RankInOrgCustomService: deleteAll({})", rankInOrgDTOS);
        List<Long> ids = rankInOrgDTOS.stream().map(RankInOrgDTO::getId).collect(Collectors.toList());
        this.rankInOrgRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            rankInOrgRepository.deleteById(id);
            rankInOrgSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RankInOrgCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<RankInOrgDTO> findAllByOrganizaion(Long organizationId){
        List<RankInOrgDTO> result = rankInOrgRepository.findAllByOrganizationId(organizationId).stream().map(rankInOrgMapper::toDto).collect(Collectors.toList());
        log.debug("RankInOrgCustomService: findAllByOrganizaion({}) {}", organizationId, result);
        return result;
    }
}
