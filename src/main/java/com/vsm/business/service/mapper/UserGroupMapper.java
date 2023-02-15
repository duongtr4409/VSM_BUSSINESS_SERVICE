package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.UserGroupDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroup} and its DTO {@link UserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RoleMapper.class })
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "idSet")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "idSet")
    UserGroupDTO toDto(UserGroup s);

    @Mapping(target = "removeUserInfo", ignore = true)
    @Mapping(target = "removeRole", ignore = true)
    UserGroup toEntity(UserGroupDTO userGroupDTO);
}
