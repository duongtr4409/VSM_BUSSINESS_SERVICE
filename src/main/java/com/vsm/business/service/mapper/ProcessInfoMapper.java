package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ProcessInfoDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessInfo} and its DTO {@link ProcessInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class, OrganizationMapper.class })
public interface ProcessInfoMapper extends EntityMapper<ProcessInfoDTO, ProcessInfo> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "organizations", source = "organizations", qualifiedByName = "idSet")
    ProcessInfoDTO toDto(ProcessInfo s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessInfoDTO toDtoId(ProcessInfo processInfo);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ProcessInfoDTO> toDtoIdSet(Set<ProcessInfo> processInfo);

    @Mapping(target = "removeOrganization", ignore = true)
    ProcessInfo toEntity(ProcessInfoDTO processInfoDTO);
}
