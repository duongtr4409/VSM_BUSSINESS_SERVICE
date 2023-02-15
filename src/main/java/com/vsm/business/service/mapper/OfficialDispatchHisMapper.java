package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfficialDispatchHis} and its DTO {@link OfficialDispatchHisDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfficialDispatchMapper.class, UserInfoMapper.class })
public interface OfficialDispatchHisMapper extends EntityMapper<OfficialDispatchHisDTO, OfficialDispatchHis> {
    @Mapping(target = "officialDispatch", source = "officialDispatch", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    OfficialDispatchHisDTO toDto(OfficialDispatchHis s);
}
