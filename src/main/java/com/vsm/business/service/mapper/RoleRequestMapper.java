package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RoleRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleRequest} and its DTO {@link RoleRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RoleMapper.class, RequestMapper.class })
public interface RoleRequestMapper extends EntityMapper<RoleRequestDTO, RoleRequest> {
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "role", source = "role", qualifiedByName = "id")
    @Mapping(target = "request", source = "request", qualifiedByName = "id")
    RoleRequestDTO toDto(RoleRequest s);
}
