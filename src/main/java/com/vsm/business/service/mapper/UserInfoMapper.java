package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.UserInfoDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganizationMapper.class, TennantMapper.class, RoleMapper.class, RankMapper.class })
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {
    @Mapping(target = "leader", source = "leader", qualifiedByName = "id")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "idSet")
    @Mapping(target = "ranks", source = "ranks", qualifiedByName = "idSet")
    @Mapping(target = "organizations", source = "organizations", qualifiedByName = "idSet")
    UserInfoDTO toDto(UserInfo s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserInfoDTO toDtoId(UserInfo userInfo);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<UserInfoDTO> toDtoIdSet(Set<UserInfo> userInfo);

    @Mapping(target = "removeRole", ignore = true)
    @Mapping(target = "removeRank", ignore = true)
    @Mapping(target = "removeOrganization", ignore = true)
    UserInfo toEntity(UserInfoDTO userInfoDTO);
}
