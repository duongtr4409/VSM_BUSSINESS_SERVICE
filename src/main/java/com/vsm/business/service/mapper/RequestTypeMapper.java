package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RequestTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestType} and its DTO {@link RequestTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestGroupMapper.class, TennantMapper.class, UserInfoMapper.class })
public interface RequestTypeMapper extends EntityMapper<RequestTypeDTO, RequestType> {
    @Mapping(target = "requestGroup", source = "requestGroup")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    RequestTypeDTO toDto(RequestType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestTypeDTO toDtoId(RequestType requestType);
}
