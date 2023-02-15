package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ExamineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Examine} and its DTO {@link ExamineDTO}.
 */
@Mapper(componentModel = "spring", uses = { StepDataMapper.class, UserInfoMapper.class })
public interface ExamineMapper extends EntityMapper<ExamineDTO, Examine> {
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    @Mapping(target = "sender", source = "sender", qualifiedByName = "id")
    @Mapping(target = "receiver", source = "receiver", qualifiedByName = "id")
    ExamineDTO toDto(Examine s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamineDTO toDtoId(Examine examine);
}
