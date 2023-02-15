package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StepInProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StepInProcess} and its DTO {@link StepInProcessDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        StepMapper.class,
        ProcessInfoMapper.class,
        TennantMapper.class,
        UserInfoMapper.class,
        RankMapper.class,
        OrganizationMapper.class,
        MailTemplateMapper.class,
    }
)
public interface StepInProcessMapper extends EntityMapper<StepInProcessDTO, StepInProcess> {
    @Mapping(target = "step", source = "step")
    @Mapping(target = "processInfo", source = "processInfo")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "rank", source = "rank")
    @Mapping(target = "organization", source = "organization")
    @Mapping(target = "mailTemplate", source = "mailTemplate", qualifiedByName = "id")
    @Mapping(target = "mailTemplateCustomer", source = "mailTemplateCustomer", qualifiedByName = "id")
    StepInProcessDTO toDto(StepInProcess s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StepInProcessDTO toDtoId(StepInProcess stepInProcess);
}
