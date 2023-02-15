package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FieldInFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldInForm} and its DTO {@link FieldInFormDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class, FieldMapper.class, FormMapper.class })
public interface FieldInFormMapper extends EntityMapper<FieldInFormDTO, FieldInForm> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "field", source = "field")
    @Mapping(target = "form", source = "form")
    FieldInFormDTO toDto(FieldInForm s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldInFormDTO toDtoId(FieldInForm fieldInForm);
}
