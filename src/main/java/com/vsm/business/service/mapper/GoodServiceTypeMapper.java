package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.GoodServiceTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoodServiceType} and its DTO {@link GoodServiceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface GoodServiceTypeMapper extends EntityMapper<GoodServiceTypeDTO, GoodServiceType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    GoodServiceTypeDTO toDto(GoodServiceType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GoodServiceTypeDTO toDtoId(GoodServiceType goodServiceType);
}
