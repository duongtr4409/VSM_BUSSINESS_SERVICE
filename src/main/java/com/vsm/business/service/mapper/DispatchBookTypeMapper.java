package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DispatchBookTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispatchBookType} and its DTO {@link DispatchBookTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface DispatchBookTypeMapper extends EntityMapper<DispatchBookTypeDTO, DispatchBookType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    DispatchBookTypeDTO toDto(DispatchBookType s);
}
