package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DispatchBookDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispatchBook} and its DTO {@link DispatchBookDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganizationMapper.class, UserInfoMapper.class })
public interface DispatchBookMapper extends EntityMapper<DispatchBookDTO, DispatchBook> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "dispatchBookOrgs", source = "dispatchBookOrgs", qualifiedByName = "idSet")
    DispatchBookDTO toDto(DispatchBook s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispatchBookDTO toDtoId(DispatchBook dispatchBook);

    @Mapping(target = "removeDispatchBookOrg", ignore = true)
    DispatchBook toEntity(DispatchBookDTO dispatchBookDTO);
}
