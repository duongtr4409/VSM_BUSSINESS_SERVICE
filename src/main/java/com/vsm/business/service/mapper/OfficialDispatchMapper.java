package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OfficialDispatchDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfficialDispatch} and its DTO {@link OfficialDispatchDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        DispatchBookMapper.class,
        OrganizationMapper.class,
        UserInfoMapper.class,
        OfficialDispatchTypeMapper.class,
        DocumentTypeMapper.class,
        PriorityLevelMapper.class,
        SecurityLevelMapper.class,
        OfficialDispatchStatusMapper.class,
        OutOrganizationMapper.class,
    }
)
public interface OfficialDispatchMapper extends EntityMapper<OfficialDispatchDTO, OfficialDispatch> {
    @Mapping(target = "dispatchBook", source = "dispatchBook", qualifiedByName = "id")
    @Mapping(target = "releaseOrg", source = "releaseOrg", qualifiedByName = "id")
    @Mapping(target = "composeOrg", source = "composeOrg", qualifiedByName = "id")
    @Mapping(target = "ownerOrg", source = "ownerOrg", qualifiedByName = "id")
    @Mapping(target = "signer", source = "signer", qualifiedByName = "id")
    @Mapping(target = "officialDispatchType", source = "officialDispatchType", qualifiedByName = "id")
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "id")
    @Mapping(target = "priorityLevel", source = "priorityLevel", qualifiedByName = "id")
    @Mapping(target = "securityLevel", source = "securityLevel", qualifiedByName = "id")
    @Mapping(target = "officialDispatchStatus", source = "officialDispatchStatus", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "outOrganization", source = "outOrganization", qualifiedByName = "id")
    @Mapping(target = "offDispatchUserReads", source = "offDispatchUserReads", qualifiedByName = "idSet")
    OfficialDispatchDTO toDto(OfficialDispatch s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfficialDispatchDTO toDtoId(OfficialDispatch officialDispatch);

    @Mapping(target = "removeOffDispatchUserRead", ignore = true)
    OfficialDispatch toEntity(OfficialDispatchDTO officialDispatchDTO);
}
