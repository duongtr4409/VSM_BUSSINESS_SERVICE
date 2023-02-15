package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OrganizationDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "orgParent", source = "orgParent", qualifiedByName = "id")
    OrganizationDTO toDto(Organization s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoId(Organization organization);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<OrganizationDTO> toDtoIdSet(Set<Organization> organization);
}
