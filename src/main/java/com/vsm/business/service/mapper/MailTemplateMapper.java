package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.MailTemplateDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MailTemplate} and its DTO {@link MailTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganizationMapper.class, ProcessInfoMapper.class })
public interface MailTemplateMapper extends EntityMapper<MailTemplateDTO, MailTemplate> {
    @Mapping(target = "organizations", source = "organizations", qualifiedByName = "idSet")
    @Mapping(target = "processInfos", source = "processInfos", qualifiedByName = "idSet")
    MailTemplateDTO toDto(MailTemplate s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MailTemplateDTO toDtoId(MailTemplate mailTemplate);

    @Mapping(target = "removeOrganization", ignore = true)
    @Mapping(target = "removeProcessInfo", ignore = true)
    MailTemplate toEntity(MailTemplateDTO mailTemplateDTO);
}
