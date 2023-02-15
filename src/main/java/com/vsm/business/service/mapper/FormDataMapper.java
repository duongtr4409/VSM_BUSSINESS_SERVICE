package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FormDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormData} and its DTO {@link FormDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, FormMapper.class, TennantMapper.class, UserInfoMapper.class })
public interface FormDataMapper extends EntityMapper<FormDataDTO, FormData> {
    @Mapping(target = "requestData", source = "requestData")
    @Mapping(target = "form", source = "form")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    FormDataDTO toDto(FormData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormDataDTO toDtoId(FormData formData);
}
