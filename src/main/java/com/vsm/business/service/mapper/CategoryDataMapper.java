package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.CategoryDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryData} and its DTO {@link CategoryDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryGroupMapper.class, TennantMapper.class, UserInfoMapper.class })
public interface CategoryDataMapper extends EntityMapper<CategoryDataDTO, CategoryData> {
    @Mapping(target = "categoryGroup", source = "categoryGroup")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    CategoryDataDTO toDto(CategoryData s);
}
