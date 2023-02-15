package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StepProcessDocDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StepProcessDoc} and its DTO {@link StepProcessDocDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfficialDispatchMapper.class })
public interface StepProcessDocMapper extends EntityMapper<StepProcessDocDTO, StepProcessDoc> {
    @Mapping(target = "officialDispatch", source = "officialDispatch", qualifiedByName = "id")
    StepProcessDocDTO toDto(StepProcessDoc s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StepProcessDocDTO toDtoId(StepProcessDoc stepProcessDoc);
}
