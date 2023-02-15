package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.CategoryGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryGroup} and its DTO {@link CategoryGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface CategoryGroupMapper extends EntityMapper<CategoryGroupDTO, CategoryGroup> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "id")
    CategoryGroupDTO toDto(CategoryGroup s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryGroupDTO toDtoId(CategoryGroup categoryGroup);
}
