package com.vsm.business.service.custom;

import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.repository.search.UserGroupSearchRepository;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.dto.UserGroupDTO;
import com.vsm.business.service.mapper.UserGroupMapper;
import com.vsm.business.repository.UserGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGroupCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private UserGroupRepository userGroupRepository;
    private UserGroupSearchRepository userGroupSearchRepository;
    private UserGroupMapper userGroupMapper;

    public UserGroupCustomService(UserGroupRepository userGroupRepository, UserGroupSearchRepository userGroupSearchRepository, UserGroupMapper userGroupMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupSearchRepository = userGroupSearchRepository;
        this.userGroupMapper = userGroupMapper;
    }

    public List<UserGroupDTO> getAll() {
        List<UserGroupDTO> result = this.userGroupRepository.findAll().stream().map(userGroupMapper::toDto).collect(Collectors.toList());
        log.debug("UserGroupCustomService: getAll() {}", result);
        return result;
    }

    public List<UserGroupDTO> deleteAll(List<UserGroupDTO> userGroupDTOS) {
        log.debug("UserGroupCustomService: deleteAll({})", userGroupDTOS);
        List<Long> ids = userGroupDTOS.stream().map(UserGroupDTO::getId).collect(Collectors.toList());
        this.userGroupRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            userGroupRepository.deleteById(id);
            userGroupSearchRepository.deleteById(id);
            log.debug("UserGroupCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("UserGroupCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<UserGroupDTO> saveAll(List<UserGroupDTO> userGroupDTOList){
        List<UserGroupDTO> result = userGroupRepository.saveAll(userGroupDTOList.stream().map(userGroupMapper::toEntity).collect(Collectors.toList())).stream().map(userGroupMapper::toDto).collect(Collectors.toList());
        log.debug("RankCustomService: saveAll({}): {}", userGroupDTOList, result);
        return result;
    }

}
