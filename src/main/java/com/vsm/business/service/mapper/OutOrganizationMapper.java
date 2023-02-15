package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OutOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OutOrganization} and its DTO {@link OutOrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OutOrganizationMapper extends EntityMapper<OutOrganizationDTO, OutOrganization> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OutOrganizationDTO toDtoId(OutOrganization outOrganization);
}
