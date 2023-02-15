package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FieldDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldData} and its DTO {@link FieldDataDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        FieldMapper.class,
        FormDataMapper.class,
        RequestDataMapper.class,
        TennantMapper.class,
        UserInfoMapper.class,
        FieldInFormMapper.class,
    }
)
public interface FieldDataMapper extends EntityMapper<FieldDataDTO, FieldData> {
    @Mapping(target = "field", source = "field")
    @Mapping(target = "formData", source = "formData")
    @Mapping(target = "requestData", source = "requestData")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "fieldInForm", source = "fieldInForm")
    FieldDataDTO toDto(FieldData s);
}
