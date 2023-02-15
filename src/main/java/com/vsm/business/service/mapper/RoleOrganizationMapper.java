package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RoleOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleOrganization} and its DTO {@link RoleOrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RoleMapper.class, OrganizationMapper.class })
public interface RoleOrganizationMapper extends EntityMapper<RoleOrganizationDTO, RoleOrganization> {
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "role", source = "role", qualifiedByName = "id")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "id")
    RoleOrganizationDTO toDto(RoleOrganization s);
}
