package com.vsm.business.service.custom;

import com.vsm.business.domain.RoleOrganization;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.RoleOrganizationRepository;
import com.vsm.business.repository.RoleRequestRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RoleOrganizationSearchRepository;
import com.vsm.business.repository.search.RoleRequestSearchRepository;
import com.vsm.business.service.dto.RoleOrganizationDTO;
import com.vsm.business.service.dto.RoleRequestDTO;
import com.vsm.business.service.mapper.RoleOrganizationMapper;
import com.vsm.business.service.mapper.RoleRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleOrganizationCustomService {
    private final Logger log = LoggerFactory.getLogger(RoleOrganizationCustomService.class);

    private final RoleOrganizationRepository roleOrganizationRepository;

    private final RoleOrganizationMapper roleOrganizationMapper;

    private final RoleOrganizationSearchRepository roleOrganizationSearchRepository;

    private final UserInfoRepository userInfoRepository;

    public RoleOrganizationCustomService(RoleOrganizationRepository roleOrganizationRepository, RoleOrganizationMapper roleOrganizationMapper, RoleOrganizationSearchRepository roleOrganizationSearchRepository, UserInfoRepository userInfoRepository) {
        this.roleOrganizationRepository = roleOrganizationRepository;
        this.roleOrganizationMapper = roleOrganizationMapper;
        this.roleOrganizationSearchRepository = roleOrganizationSearchRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public List<RoleOrganizationDTO> getAll(){
        List<RoleOrganizationDTO> result = this.roleOrganizationRepository.findAll().stream().map(this.roleOrganizationMapper::toDto).collect(Collectors.toList());
        log.debug("RoleOrganizationCustomService getAll(): {}", result);
        return result;
    }

    public List<RoleOrganizationDTO> deleteAll(List<RoleOrganizationDTO> roleOrganizationDTOS){
        log.debug("RoleOrganizationCustomService deleteAll({})", roleOrganizationDTOS);
        List<Long> ids = roleOrganizationDTOS.stream().map(RoleOrganizationDTO::getId).collect(Collectors.toList());
        this.roleOrganizationRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<RoleOrganizationDTO> saveAll(List<RoleOrganizationDTO> requestDTOS){
        List<RoleOrganizationDTO> result = this.roleOrganizationRepository.saveAll(requestDTOS.stream().map(this.roleOrganizationMapper::toEntity).collect(Collectors.toList())).stream().map(this.roleOrganizationMapper::toDto).collect(Collectors.toList());
        log.debug("RoleOrganizationCustomService saveAll({}): {}", requestDTOS, result);
        return result;
    }

    public List<RoleOrganizationDTO> getAllByRole(Long roleId){
        List<RoleOrganizationDTO> result = this.roleOrganizationRepository.findAllByRoleId(roleId).stream().map(this.roleOrganizationMapper::toDto).collect(Collectors.toList());
        log.debug("RoleOrganizationCustomService getAllByRole({}): {}", roleId, result);
        return result;
    }

    public List<RoleOrganizationDTO> getAllByUser(Long userId){
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        List<RoleOrganizationDTO> result = new ArrayList<>();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getRoleOrganizations().stream().map(this.roleOrganizationMapper::toDto).collect(Collectors.toList()));
        });
        log.debug("RoleOrganizationCustomService getAllByUser({}): {}", userId, result);
        return result;
    }
}
