package com.vsm.business.service.custom;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.domain.RankInOrg;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.RankInOrgRepository;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.search.RankSearchRepository;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.RankMapper;
import com.vsm.business.utils.ObjectUtils;
import com.vsm.business.utils.UserUtils;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private RankRepository rankRepository;
    private RankSearchRepository rankSearchRepository;
    private RankMapper rankMapper;
    private RankInOrgCustomService rankInOrgCustomService;

    private OrganizationRepository organizationRepository;

    private UserUtils userUtils;

    private RankInOrgRepository rankInOrgRepository;

    private ObjectUtils objectUtils;

    public RankCustomService(RankRepository rankRepository, RankSearchRepository rankSearchRepository, RankMapper rankMapper, RankInOrgCustomService rankInOrgCustomService, ObjectUtils objectUtils, OrganizationRepository organizationRepository, UserUtils userUtils, RankInOrgRepository rankInOrgRepository) {
        this.rankRepository = rankRepository;
        this.rankSearchRepository = rankSearchRepository;
        this.rankMapper = rankMapper;
        this.rankInOrgCustomService = rankInOrgCustomService;
        this.objectUtils = objectUtils;
        this.organizationRepository = organizationRepository;
        this.userUtils = userUtils;
        this.rankInOrgRepository = rankInOrgRepository;
    }
    public List<RankDTO> getAll() {
        log.debug("RankCustomService: getAll()");
        List<Rank> ranks = this.rankRepository.findAll();
        List<RankDTO> result = new ArrayList<>();
        for (Rank rank :
            ranks) {
            RankDTO rankDTO = rankMapper.toDto(rank);
            result.add(rankDTO);
        }
        return result;
    }

    public List<RankDTO> deleteAll(List<RankDTO> rankDTOS) {
        log.debug("RankCustomService: deleteAll({})", rankDTOS);
        List<Long> ids = rankDTOS.stream().map(RankDTO::getId).collect(Collectors.toList());
        this.rankRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            rankRepository.deleteById(id);
            rankSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RankCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<RankDTO> saveAll(List<RankDTO> rankDTOList){
        List<RankDTO> result = rankRepository.saveAll(rankDTOList.stream().map(rankMapper::toEntity).collect(Collectors.toList())).stream().map(rankMapper::toDto).collect(Collectors.toList());
        log.debug("RankCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

    public List<RankDTO>findAllByOrganizationId(Long organizationId){
        List<RankDTO> result = rankInOrgCustomService.findAllByOrganizaion(organizationId).stream().map((ele) -> {return ele.getRank();}).collect(Collectors.toList()).stream().filter((ele) -> ele != null).collect(Collectors.toList());
        log.debug("RankCustomService: findAllByOrganizationId({}) {}", organizationId, result);
        return result;
    }

    public List<OrganizationDTO> findAllOrgByRankInOrg(Long rankId){
        List<OrganizationDTO> result = this.rankInOrgRepository.findAllByRankId(rankId).stream().map(ele -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            if(ele.getOrganization() != null){
                try {
                    this.objectUtils.coppySimpleType(ele.getOrganization(), organizationDTO, OrganizationDTO.class);
                } catch (IllegalAccessException e) {
                    log.error("{}", e);
                }
            }
            return organizationDTO;
        }).collect(Collectors.toList());
        log.debug("RankCustomService: findAllOrgByRankInOrg({}) {}", rankId, result);
        return result;
    }

    public List<RankDTO> getAllIgnoreField() {
        log.debug("RankCustomService: getAllIgnoreField()");
        List<Rank> ranks = this.rankRepository.findAll();
        List<RankDTO> result = new ArrayList<>();
        for (Rank rank :
            ranks) {
            if(rank != null){
                RankDTO rankDTO = new RankDTO();
                try {rankDTO = this.objectUtils.coppySimpleType(rank, rankDTO, RankDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                result.add(rankDTO);
            }
        }
        return result;
    }

    public RankDTO customSave(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        RankDTO result = rankMapper.toDto(rank);
        try{
//            rankSearchRepository.save(rank);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }

        if(rankDTO.getOrganizationIds() != null){
            if(rankDTO.getId() != null){        // nếu là update thì xoá thông tin mapping đi thêm lại
               List<Long> rankIdsDelete = this.rankInOrgRepository.findAllByRankId(rankDTO.getId()).stream().map(ele -> ele.getId()).collect(Collectors.toList());
               this.rankInOrgRepository.deleteAllById(rankIdsDelete);
            }
            Instant now = Instant.now();
            for(Long organizationId : rankDTO.getOrganizationIds()){
                if(organizationId != null){
                    try {

                        Organization organization = this.organizationRepository.findById(organizationId).get();
                        organization.setId(organizationId);
                        RankInOrg rankInOrg = new RankInOrg();
                        rankInOrg.setCreatedDate(now);
                        rankInOrg.setModifiedDate(now);
                        rankInOrg.setRank(rank);
                        rankInOrg.setRankCode(rank.getRankCode());
                        rankInOrg.setRankName(rank.getRankName());

                        rankInOrg.setOrganization(organization);
                        rankInOrg.setOrgCode(organization.getOrganizationCode());
                        rankInOrg.setOrgName(organization.getOrganizationName());

                        rankInOrg.setIsActive(true);
                        rankInOrg.setIsDelete(false);
                        rankInOrg.setCreatedName(this.userUtils.getCurrentUser().getFullName());
                        rankInOrg.setModifiedName(this.userUtils.getCurrentUser().getFullName());
                        this.rankInOrgRepository.save(rankInOrg);
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
            }
        }

        return result;
    }
}
