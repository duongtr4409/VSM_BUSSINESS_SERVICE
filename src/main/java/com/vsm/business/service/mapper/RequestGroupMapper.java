package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RequestGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestGroup} and its DTO {@link RequestGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface RequestGroupMapper extends EntityMapper<RequestGroupDTO, RequestGroup> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    RequestGroupDTO toDto(RequestGroup s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestGroupDTO toDtoId(RequestGroup requestGroup);
}
