package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RequestDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        RequestTypeMapper.class,
        RequestGroupMapper.class,
        FormMapper.class,
        TennantMapper.class,
        UserInfoMapper.class,
        TemplateFormMapper.class,
        ProcessInfoMapper.class,
    }
)
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    @Mapping(target = "requestType", source = "requestType")
    @Mapping(target = "requestGroup", source = "requestGroup")
    @Mapping(target = "form", source = "form")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "templateForms", source = "templateForms", qualifiedByName = "idSet")
    @Mapping(target = "processInfos", source = "processInfos", qualifiedByName = "idSet")
    RequestDTO toDto(Request s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoId(Request request);

    @Mapping(target = "removeTemplateForm", ignore = true)
    @Mapping(target = "removeProcessInfo", ignore = true)
    Request toEntity(RequestDTO requestDTO);
}
