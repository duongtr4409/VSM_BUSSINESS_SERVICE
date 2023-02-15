package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReqdataChangeHis} and its DTO {@link ReqdataChangeHisDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, TennantMapper.class, UserInfoMapper.class })
public interface ReqdataChangeHisMapper extends EntityMapper<ReqdataChangeHisDTO, ReqdataChangeHis> {
    @Mapping(target = "requestData", source = "requestData")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    ReqdataChangeHisDTO toDto(ReqdataChangeHis s);
}
