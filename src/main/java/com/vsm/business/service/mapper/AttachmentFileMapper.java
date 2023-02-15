package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.AttachmentFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentFile} and its DTO {@link AttachmentFileDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        FileTypeMapper.class,
        RequestDataMapper.class,
        TennantMapper.class,
        UserInfoMapper.class,
        TemplateFormMapper.class,
        ReqdataProcessHisMapper.class,
        OfficialDispatchMapper.class,
        StepProcessDocMapper.class,
        MailTemplateMapper.class,
        ManageStampInfoMapper.class,
    }
)
public interface AttachmentFileMapper extends EntityMapper<AttachmentFileDTO, AttachmentFile> {
    @Mapping(target = "fileType", source = "fileType", qualifiedByName = "id")
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    @Mapping(target = "templateForm", source = "templateForm", qualifiedByName = "id")
    @Mapping(target = "reqdataProcessHis", source = "reqdataProcessHis", qualifiedByName = "id")
    @Mapping(target = "officialDispatch", source = "officialDispatch", qualifiedByName = "id")
    @Mapping(target = "stepProcessDoc", source = "stepProcessDoc", qualifiedByName = "id")
    @Mapping(target = "mailTemplate", source = "mailTemplate", qualifiedByName = "id")
    @Mapping(target = "manageStampInfo", source = "manageStampInfo", qualifiedByName = "id")
    AttachmentFileDTO toDto(AttachmentFile s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentFileDTO toDtoId(AttachmentFile attachmentFile);
}
