package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FieldDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Field} and its DTO {@link FieldDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    FieldDTO toDto(Field s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoId(Field field);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<FieldDTO> toDtoIdSet(Set<Field> field);
}
