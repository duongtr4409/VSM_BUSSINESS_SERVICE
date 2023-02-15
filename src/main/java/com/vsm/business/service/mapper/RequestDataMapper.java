package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RequestDataDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestData} and its DTO {@link RequestDataDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        RequestMapper.class,
        StatusMapper.class,
        TennantMapper.class,
        RequestTypeMapper.class,
        RequestGroupMapper.class,
        OrganizationMapper.class,
        UserInfoMapper.class,
    }
)
public interface RequestDataMapper extends EntityMapper<RequestDataDTO, RequestData> {
    @Mapping(target = "request", source = "request")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "requestType", source = "requestType", qualifiedByName = "id")
    @Mapping(target = "requestGroup", source = "requestGroup", qualifiedByName = "id")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "subStatus", source = "subStatus")
    @Mapping(target = "reqDataConcerned", source = "reqDataConcerned", qualifiedByName = "id")
    @Mapping(target = "approver", source = "approver", qualifiedByName = "id")
    @Mapping(target = "revoker", source = "revoker", qualifiedByName = "id")
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "idSet")
    RequestDataDTO toDto(RequestData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDataDTO toDtoId(RequestData requestData);

    @Mapping(target = "removeUserInfo", ignore = true)
    RequestData toEntity(RequestDataDTO requestDataDTO);
}
