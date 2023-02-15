package com.vsm.business.service.custom;

import com.vsm.business.domain.UserInStep;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.RoleRequestRepository;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RoleRequestSearchRepository;
import com.vsm.business.repository.search.UserInStepSearchRepository;
import com.vsm.business.service.dto.RoleRequestDTO;
import com.vsm.business.service.dto.UserInStepDTO;
import com.vsm.business.service.mapper.RoleRequestMapper;
import com.vsm.business.service.mapper.UserInStepMapper;
import org.docx4j.wml.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleRequestCustomService {
    private final Logger log = LoggerFactory.getLogger(RoleRequestCustomService.class);

    private final RoleRequestRepository roleRequestRepository;

    private final RoleRequestMapper roleRequestMapper;

    private final RoleRequestSearchRepository roleRequestSearchRepository;

    private final UserInfoRepository userInfoRepository;

    public RoleRequestCustomService(RoleRequestRepository roleRequestRepository, RoleRequestMapper roleRequestMapper, RoleRequestSearchRepository roleRequestSearchRepository, UserInfoRepository userInfoRepository) {
        this.roleRequestRepository = roleRequestRepository;
        this.roleRequestMapper = roleRequestMapper;
        this.roleRequestSearchRepository = roleRequestSearchRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public List<RoleRequestDTO> getAll(){
        List<RoleRequestDTO> result = this.roleRequestRepository.findAll().stream().map(this.roleRequestMapper::toDto).collect(Collectors.toList());
        log.debug("RoleRequestCustomService getAll(): {}", result);
        return result;
    }

    public List<RoleRequestDTO> deleteAll(List<RoleRequestDTO> roleRequestDTOS){
        log.debug("RoleRequestCustomService deleteAll({})", roleRequestDTOS);
        List<Long> ids = roleRequestDTOS.stream().map(RoleRequestDTO::getId).collect(Collectors.toList());
        this.roleRequestRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<RoleRequestDTO> saveAll(List<RoleRequestDTO> requestDTOS){
        List<RoleRequestDTO> result = this.roleRequestRepository.saveAll(requestDTOS.stream().map(this.roleRequestMapper::toEntity).collect(Collectors.toList())).stream().map(this.roleRequestMapper::toDto).collect(Collectors.toList());
        log.debug("RoleRequestCustomService saveAll({}): {}", requestDTOS, result);
        return result;
    }

    public List<RoleRequestDTO> getAllByRole(Long roleId){
        List<RoleRequestDTO> result = this.roleRequestRepository.findAllByRoleId(roleId).stream().map(this.roleRequestMapper::toDto).collect(Collectors.toList());
        log.debug("RoleRequestCustomService getAllByRole({}): {}", roleId, result);
        return result;
    }

    public List<RoleRequestDTO> getAllByUser(Long userId){
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        List<RoleRequestDTO> result = new ArrayList<>();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getRoleRequests().stream().map(this.roleRequestMapper::toDto).collect(Collectors.toList()));
        });
        log.debug("RoleRequestCustomService getAllByUser({}): {}", userId, result);
        return result;
    }
}
