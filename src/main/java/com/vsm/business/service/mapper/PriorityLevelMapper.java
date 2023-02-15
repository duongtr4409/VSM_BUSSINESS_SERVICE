package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.PriorityLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriorityLevel} and its DTO {@link PriorityLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface PriorityLevelMapper extends EntityMapper<PriorityLevelDTO, PriorityLevel> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    PriorityLevelDTO toDto(PriorityLevel s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PriorityLevelDTO toDtoId(PriorityLevel priorityLevel);
}
