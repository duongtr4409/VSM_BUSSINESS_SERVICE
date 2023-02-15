package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FormDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Form} and its DTO {@link FormDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class, FieldMapper.class })
public interface FormMapper extends EntityMapper<FormDTO, Form> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "fields", source = "fields", qualifiedByName = "idSet")
    FormDTO toDto(Form s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormDTO toDtoId(Form form);

    @Mapping(target = "removeField", ignore = true)
    Form toEntity(FormDTO formDTO);
}
