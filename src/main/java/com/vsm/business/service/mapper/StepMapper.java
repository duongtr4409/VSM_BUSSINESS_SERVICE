package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StepDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Step} and its DTO {@link StepDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { TennantMapper.class, UserInfoMapper.class, RankMapper.class, OrganizationMapper.class, MailTemplateMapper.class }
)
public interface StepMapper extends EntityMapper<StepDTO, Step> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    @Mapping(target = "rank", source = "rank")
    @Mapping(target = "organization", source = "organization")
    @Mapping(target = "mailTemplate", source = "mailTemplate", qualifiedByName = "id")
    @Mapping(target = "mailTemplateCustomer", source = "mailTemplateCustomer", qualifiedByName = "id")
    StepDTO toDto(Step s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StepDTO toDtoId(Step step);
}
