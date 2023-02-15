package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.TemplateFormDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateForm} and its DTO {@link TemplateFormDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class, OrganizationMapper.class })
public interface TemplateFormMapper extends EntityMapper<TemplateFormDTO, TemplateForm> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "organizations", source = "organizations", qualifiedByName = "idSet")
    TemplateFormDTO toDto(TemplateForm s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateFormDTO toDtoId(TemplateForm templateForm);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<TemplateFormDTO> toDtoIdSet(Set<TemplateForm> templateForm);

    @Mapping(target = "removeOrganization", ignore = true)
    TemplateForm toEntity(TemplateFormDTO templateFormDTO);
}
