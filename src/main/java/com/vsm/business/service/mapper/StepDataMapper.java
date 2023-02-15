package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StepDataDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StepData} and its DTO {@link StepDataDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        ProcessDataMapper.class,
        RequestDataMapper.class,
        TennantMapper.class,
        UserInfoMapper.class,
        StepInProcessMapper.class,
        RankMapper.class,
        MailTemplateMapper.class,
    }
)
public interface StepDataMapper extends EntityMapper<StepDataDTO, StepData> {
    @Mapping(target = "nextStep", source = "nextStep")
    @Mapping(target = "processData", source = "processData")
    @Mapping(target = "requestData", source = "requestData")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "stepInProcess", source = "stepInProcess")
    @Mapping(target = "rank", source = "rank", qualifiedByName = "id")
    @Mapping(target = "mailTemplate", source = "mailTemplate", qualifiedByName = "id")
    @Mapping(target = "mailTemplateCustomer", source = "mailTemplateCustomer", qualifiedByName = "id")
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "idSet")
    StepDataDTO toDto(StepData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StepDataDTO toDtoId(StepData stepData);

    @Mapping(target = "removeUserInfo", ignore = true)
    StepData toEntity(StepDataDTO stepDataDTO);
}
