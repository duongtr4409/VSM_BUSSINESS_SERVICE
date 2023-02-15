package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OfficialDispatchTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfficialDispatchType} and its DTO {@link OfficialDispatchTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface OfficialDispatchTypeMapper extends EntityMapper<OfficialDispatchTypeDTO, OfficialDispatchType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    OfficialDispatchTypeDTO toDto(OfficialDispatchType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfficialDispatchTypeDTO toDtoId(OfficialDispatchType officialDispatchType);
}
