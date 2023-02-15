package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StampDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stamp} and its DTO {@link StampDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StampMapper extends EntityMapper<StampDTO, Stamp> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StampDTO toDtoId(Stamp stamp);
}
