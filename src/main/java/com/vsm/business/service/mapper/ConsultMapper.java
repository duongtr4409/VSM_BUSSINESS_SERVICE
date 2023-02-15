package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ConsultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consult} and its DTO {@link ConsultDTO}.
 */
@Mapper(componentModel = "spring", uses = { StepDataMapper.class, UserInfoMapper.class })
public interface ConsultMapper extends EntityMapper<ConsultDTO, Consult> {
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "id")
    @Mapping(target = "receiver", source = "receiver", qualifiedByName = "id")
    ConsultDTO toDto(Consult s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsultDTO toDtoId(Consult consult);
}
