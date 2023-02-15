package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ProcessDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessData} and its DTO {@link ProcessDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, TennantMapper.class, UserInfoMapper.class })
public interface ProcessDataMapper extends EntityMapper<ProcessDataDTO, ProcessData> {
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    ProcessDataDTO toDto(ProcessData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessDataDTO toDtoId(ProcessData processData);
}
