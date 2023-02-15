package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RoleDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class, FeatureMapper.class })
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "features", source = "features", qualifiedByName = "idSet")
    RoleDTO toDto(Role s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleDTO toDtoId(Role role);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RoleDTO> toDtoIdSet(Set<Role> role);

    @Mapping(target = "removeFeature", ignore = true)
    Role toEntity(RoleDTO roleDTO);
}
