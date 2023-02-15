package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.AttachmentInStepDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentInStep} and its DTO {@link AttachmentInStepDTO}.
 */
@Mapper(componentModel = "spring", uses = { AttachmentInStepTypeMapper.class, UserInfoMapper.class, StepDataMapper.class })
public interface AttachmentInStepMapper extends EntityMapper<AttachmentInStepDTO, AttachmentInStep> {
    @Mapping(target = "attachmentInStepType", source = "attachmentInStepType", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    AttachmentInStepDTO toDto(AttachmentInStep s);
}
