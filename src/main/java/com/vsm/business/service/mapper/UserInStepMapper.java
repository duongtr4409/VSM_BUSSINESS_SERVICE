package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.UserInStepDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInStep} and its DTO {@link UserInStepDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, StepInProcessMapper.class, TennantMapper.class })
public interface UserInStepMapper extends EntityMapper<UserInStepDTO, UserInStep> {
    @Mapping(target = "userInfo", source = "userInfo")
    @Mapping(target = "stepInProcess", source = "stepInProcess")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    UserInStepDTO toDto(UserInStep s);
}
