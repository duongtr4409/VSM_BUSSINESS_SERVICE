package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OfficialDispatchStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfficialDispatchStatus} and its DTO {@link OfficialDispatchStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface OfficialDispatchStatusMapper extends EntityMapper<OfficialDispatchStatusDTO, OfficialDispatchStatus> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    OfficialDispatchStatusDTO toDto(OfficialDispatchStatus s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfficialDispatchStatusDTO toDtoId(OfficialDispatchStatus officialDispatchStatus);
}
