package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ResultOfStepDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultOfStep} and its DTO {@link ResultOfStepDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, StepDataMapper.class, TennantMapper.class })
public interface ResultOfStepMapper extends EntityMapper<ResultOfStepDTO, ResultOfStep> {
    @Mapping(target = "excutor", source = "excutor", qualifiedByName = "id")
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    ResultOfStepDTO toDto(ResultOfStep s);
}
