package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.TennantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tennant} and its DTO {@link TennantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TennantMapper extends EntityMapper<TennantDTO, Tennant> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TennantDTO toDtoId(Tennant tennant);
}
