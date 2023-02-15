package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Status} and its DTO {@link StatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    StatusDTO toDto(Status s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatusDTO toDtoId(Status status);
}
