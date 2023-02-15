package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.AttachmentInStepTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentInStepType} and its DTO {@link AttachmentInStepTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface AttachmentInStepTypeMapper extends EntityMapper<AttachmentInStepTypeDTO, AttachmentInStepType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    AttachmentInStepTypeDTO toDto(AttachmentInStepType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentInStepTypeDTO toDtoId(AttachmentInStepType attachmentInStepType);
}
