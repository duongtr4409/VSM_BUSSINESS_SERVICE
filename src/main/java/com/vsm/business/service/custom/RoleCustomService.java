package com.vsm.business.service.custom;

import com.vsm.business.domain.Role;
import com.vsm.business.domain.UserGroup;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.RoleRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.RoleSearchRepository;
import com.vsm.business.service.dto.FeatureDTO;
import com.vsm.business.service.dto.RoleDTO;
import com.vsm.business.service.dto.UserGroupDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.RoleMapper;
import com.vsm.business.repository.UserGroupRepository;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class RoleCustomService {
    private final Logger log = LoggerFactory.getLogger(RoleCustomService.class);
    private RoleRepository roleRepository;
    private RoleSearchRepository roleSearchRepository;
    private RoleMapper roleMapper;
    private UserInfoRepository userInfoRepository;
    private UserGroupRepository userGroupRepository;

    private RequestRepository requestRepository;


    public RoleCustomService(RoleRepository roleRepository, RoleSearchRepository roleSearchRepository, RoleMapper roleMapper, UserInfoRepository userInfoRepository, UserGroupRepository userGroupRepository, RequestRepository requestRepository) {
        this.roleRepository = roleRepository;
        this.roleSearchRepository = roleSearchRepository;
        this.roleMapper = roleMapper;
        this.userInfoRepository = userInfoRepository;
        this.userGroupRepository = userGroupRepository;
        this.requestRepository = requestRepository;
    }

    public List<RoleDTO> getAll() {
        log.debug("RoleCustomService: getAll()");
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDTO> result = new ArrayList<>();
        for (Role role :
            roles) {
            RoleDTO roleDTO = roleMapper.toDto(role);
            result.add(roleDTO);
        }
        return result;
    }

    public List<RoleDTO> deleteAll(List<RoleDTO> roleDTOS) {
        log.debug("RoleCustomService: deleteAll({})", roleDTOS);
        List<Long> ids = roleDTOS.stream().map(RoleDTO::getId).collect(Collectors.toList());
        this.roleRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            roleRepository.deleteById(id);
            roleSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RoleCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<RoleDTO> saveAll(List<RoleDTO> roleDTOList){
        List<RoleDTO> result = roleRepository.saveAll(roleDTOList.stream().map(roleMapper::toEntity).collect(Collectors.toList())).stream().map(roleMapper::toDto).collect(Collectors.toList());
        log.error("RoleCustomService: saveAll({}) {}", roleDTOList, result);
        return result;
    }

    public List<RoleDTO> getAllByUser(Long userId){
        List<RoleDTO> result = this.userInfoRepository.findById(userId).get().getRoles().stream().map(roleMapper::toDto).collect(Collectors.toList());
        log.error("RoleCustomService: getAllByUser({}) {}", userId, result);
        return result;
    }

    public List<RoleDTO> getAllByUserGroup(Long userGroupId){
        List<RoleDTO> result = this.userGroupRepository.findById(userGroupId).get().getRoles().stream().map(roleMapper::toDto).collect(Collectors.toList());
        log.error("RoleCustomService: getAllByUserGroup({}) {}", userGroupId, result);
        return result;
    }

    public RoleDTO customFindOne(Long id) throws IllegalAccessException {
        Role role = this.roleRepository.findById(id).get();
        RoleDTO result = this.convertToDTO(role);
        log.error("RoleCustomService: customFindOne({}): {}",id, result);
        return result;
    }

    public List<RoleDTO> customFindAll(){
        List<Role> roleList = this.roleRepository.findAll();
        List<RoleDTO> result = this.convertToDTO(roleList);
        log.debug("RoleCustomService: customFindAll(): {}", result);
        return result;
    }

    public RoleDTO customSave(RoleDTO roleDTO) throws IllegalAccessException {
        Role role = this.converToEntity(roleDTO);
        role = this.roleRepository.save(role);
        role = this.roleRepository.findById(role.getId()).get();
        // save UserGroup và UserInfo tương ứng
        Role finalRole = role;
//        List<UserInfo> userInfoList = new ArrayList<>();
//        List<UserGroup> userGroupList = new ArrayList<>();
//        roleDTO.getUserGroupDTOList().forEach(ele -> {
//            UserGroup userGroup = this.userGroupRepository.findById(ele.getId()).get();
//            Set<Role> roleSet = userGroup.getRoles() == null ? new HashSet<>() : userGroup.getRoles();
//            roleSet.add(finalRole);
//            userGroup.setRoles(roleSet);
//            userGroupList.add(userGroup);
//            this.userGroupRepository.save(userGroup);
//        });
//        this.userGroupRepository.saveAll(userGroupList);
//        roleDTO.getUserInfoDTOList().forEach(ele -> {
//            UserInfo userInfo = this.userInfoRepository.findById(ele.getId()).get();
//            Set<Role> roleSet = userInfo.getRoles() == null ? new HashSet<>() : userInfo.getRoles();
//            roleSet.add(finalRole);
//            userInfo.setRoles(roleSet);
//            this.userInfoRepository.save(userInfo);
//        });
//        this.userInfoRepository.saveAll(userInfoList);
        List<UserInfo> userInfosNotExistNewRole = role.getUserInfos().stream().filter(ele -> !roleDTO.getUserInfoDTOList().stream().anyMatch(ele1 -> ele1.getId().equals(ele.getId()))).collect(Collectors.toList());   // lấy danh sách nhưng UserInfo cũ trong DB mà ko có trong dl mà frontend truyền nên -> nhưng UserInfo này cần bỏ kết nối đến role này đi
        userInfosNotExistNewRole.forEach(ele -> {
            Set<Role> roleSet = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
            roleSet.removeIf(ele1 -> ele1.getId().equals(finalRole.getId()));
            this.userInfoRepository.save(ele);
        });
        roleDTO.getUserInfoDTOList().forEach(ele -> {
            UserInfo userInfo = this.userInfoRepository.findById(ele.getId()).get();
            Set<Role> roleSet = userInfo.getRoles() == null ? new HashSet<>() : userInfo.getRoles();
            roleSet.add(finalRole);
            userInfo.setRoles(roleSet);
            this.userInfoRepository.save(userInfo);
        });

        List<UserGroup> userGroupsNotExistNewRole = role.getUserGroups().stream().filter(ele -> !roleDTO.getUserGroupDTOList().stream().anyMatch(ele1 -> ele1.getId().equals(ele.getId()))).collect(Collectors.toList());
        userGroupsNotExistNewRole.forEach(ele -> {
            Set<Role> roleSet = ele.getRoles() == null ? new HashSet<>() : ele.getRoles();
            roleSet.removeIf(ele1 -> ele1.getId().equals(finalRole.getId()));
            this.userGroupRepository.save(ele);
        });
        roleDTO.getUserGroupDTOList().forEach(ele -> {
            UserGroup userGroup = this.userGroupRepository.findById(ele.getId()).get();
            Set<Role> roleSet = userGroup.getRoles() == null ? new HashSet<>() : userGroup.getRoles();
            roleSet.add(finalRole);
            userGroup.setRoles(roleSet);
            this.userGroupRepository.save(userGroup);
        });

        role = this.roleRepository.findById(role.getId()).get();
        RoleDTO result = this.convertToDTO(role);
        log.debug("RoleCustomService: customSave({}): {}",roleDTO, result);
        return result;
    }


    // utils \\
    @Autowired
    private ObjectUtils objectUtils;
    private RoleDTO convertToDTO(Role role) throws IllegalAccessException {
        if(role == null) return null;
        RoleDTO roleDTO = new RoleDTO();
        roleDTO = this.objectUtils.copyproperties(role, roleDTO, RoleDTO.class);
        if(role.getFeatures() != null){
            roleDTO.setFeatures(role.getFeatures().stream().map(ele -> {
                FeatureDTO featureDTO = new FeatureDTO();
                featureDTO.setId(ele.getId());
                return featureDTO;
            }).collect(Collectors.toSet()));
        }
        if(role.getUserInfos() != null){
            roleDTO.setUserInfoDTOList(role.getUserInfos().stream().map(ele -> {
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                try {
                    userInfoDTO = objectUtils.copyproperties(ele, userInfoDTO, UserInfoDTO.class);
                } catch (IllegalAccessException e) {}
                return userInfoDTO;
            }).collect(Collectors.toList()));
        }
        if(role.getUserGroups() != null){
            roleDTO.setUserGroupDTOList(role.getUserGroups().stream().map(ele -> {
                UserGroupDTO userGroupDTO = new UserGroupDTO();
                try {
                    userGroupDTO = objectUtils.copyproperties(ele, userGroupDTO, UserGroupDTO.class);
                } catch (IllegalAccessException e) {}
                return userGroupDTO;
            }).collect(Collectors.toList()));
        }
//        if(role.getRequests() != null){
//            roleDTO.setRequestDTOList(role.getRequests().stream().map(ele -> {
//                RequestDTO requestDTO = new RequestDTO();
//                try {
//                    requestDTO = objectUtils.copyproperties(ele, requestDTO, RequestDTO.class);
//                } catch (IllegalAccessException e) {}
//                return requestDTO;
//            }).collect(Collectors.toList()));
//        }
        return roleDTO;
    }

    private List<RoleDTO> convertToDTO(List<Role> roleList){
        if(roleList == null) return null;
        List<RoleDTO> result = new ArrayList<>();
        roleList.forEach(ele -> {
            try {
                result.add(this.convertToDTO(ele));
            } catch (IllegalAccessException e) {};
        });
        return result;
    }

    private Role converToEntity(RoleDTO roleDTO) throws IllegalAccessException {
        if(roleDTO == null) return null;
        Role result = new Role();
//        result = this.objectUtils.copyproperties(roleDTO, result, Role.class);
        result = this.roleMapper.toEntity(roleDTO);
        if(roleDTO.getUserInfoDTOList() != null){
            result.setUserInfos(roleDTO.getUserInfoDTOList().stream().map(ele -> {
                UserInfo userInfo = this.userInfoRepository.findById(ele.getId()).get();
                return userInfo;
            }).collect(Collectors.toSet()));
        }
        if(roleDTO.getUserGroupDTOList() != null){
            result.setUserGroups(roleDTO.getUserGroupDTOList().stream().map(ele -> {
                UserGroup userGroup = this.userGroupRepository.findById(ele.getId()).get();
                return userGroup;
            }).collect(Collectors.toSet()));
        }
//        if(roleDTO.getRequestDTOList() != null){
//            result.setRequests(roleDTO.getRequestDTOList().stream().map(ele -> {
//                Request request = this.requestRepository.findById(ele.getId()).get();
//                return request;
//            }).collect(Collectors.toSet()));
//        }
        return result;
    }

    public List<RoleDTO> getAllIgnoreField() {
        log.debug("RoleCustomService: getAllIgnoreField()");
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDTO> result = new ArrayList<>();
        for (Role role :
            roles) {
            if(role != null){
                RoleDTO roleDTO = new RoleDTO();
                try {roleDTO = this.objectUtils.coppySimpleType(role, roleDTO, RoleDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                result.add(roleDTO);
            }
        }
        return result;
    }
}
